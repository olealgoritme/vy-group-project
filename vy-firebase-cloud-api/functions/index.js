'use strict';

const functions = require('firebase-functions');
const admin = require('firebase-admin');
const faker = require('faker');
const express = require('express');
const app = express();

admin.initializeApp();
const firestore = admin.firestore();
const cors = require('cors')({origin: true});
app.use(cors);

const mockUser = require('./mock-responses/user');
const mockPhotos = require('./mock-responses/photos');

app.get('/say/hello', (req, res) => {
    console.log('Request Query Params: ', req.query);

    if (req.query.hasOwnProperty('name') && req.query.name !== "") {
        return res.status(200)
            .json({"message": "Hello " + req.query.name + "! Welcome to VY mock server."});
    }

    return res.status(200)
        .json({"message": "Hello there... Welcome to VY mock server."});
});

app.get('/photos', (req, res) => {
    void (req);
    return res.status(200).json(mockPhotos);
});

app.get('/photos/:photoId', (req, res) => {
    void (req);
    return res.status(200).json(mockPhotos.photos[0]);
});

app.get('/users/:userId', (req, res) => {
    console.log('Request Query Params: ', req.query);
    console.log('Request URL Params: ', req.params);

    if (!req.params.hasOwnProperty('userId')) {
        return res.status(400)
            .json({"message": "The `userId` is required."});
    }

    return res.status(200)
        .json({
            "userId": req.params.userId,
            "name": faker.name.findName(),
            "email": faker.internet.email()
        })
});

app.post('/register', (req, res) => {
    console.log('Request Body Params: ', req.body);

    if (req.body.hasOwnProperty('userId') && req.body.userId === "taken") {
        return res.status(400)
            .json(mockUser.registerFailedUsernameExists);
    }

    var response = mockUser.registerSuccess
    response.requestData = req.body

    return res.status(200)
        .json(response);
});



app.get('/vy_beacon_list/:beacon', (req, res) => {
    var docRef = firestore.collection("vy_beacon_list").doc(req.params.beacon);

    docRef.get().then((doc) => {
        if (doc.exists) {
            return res.status(200).json(doc.data());
        } else {
            return res.status(400).json({"message": "VY Beacon not found."});
        }

    }).catch((error) => {
        void (error)
        return res.status(400).json({"message": "Unable to connect to Firestore."});
    });
});


app.get('/vy_beacon_list', (req, res) => {
    void (req);
    var beaconList = firestore.collection("vy_beacon_list");
    const allDocs = beaconList.get()
    if (allDocs.exists) {
        return res.status(200).json(allDocs.data());
    } else {
        return res.status(400).json({"message": "VY Beacon list empty."});
    }
});


exports.api = functions.https.onRequest(app);
