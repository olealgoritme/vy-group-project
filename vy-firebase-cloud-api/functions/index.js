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

app.post('/api/boarding', (req, res) => {
    const email = req.body.email;
    const beacon_uuid = req.body.beacon_uuid;
    const timestamp = Date.now();

    firestore.collection("users").doc(email).collection("tickets").add({
        startStation: beacon_uuid,
        startTimestamp: timestamp,
        endStation: "",
        endTimestamp: 0,
        price: 0
    }).then((doc) => {
        firestore.collection("users").doc(email).set({email: email});
        return res.json({
            ticketId: doc.id,
            trainDestination: "Kristiansand",
            departureTime: timestamp
        })
    }).catch((error) => {
        return res.status(400).json({"message": "Unable to connect to Firestore."});
    });
});

app.post('/api/disembarking', (req, res) => {
    const email = req.body.email;
    const ticketId = req.body.ticket_id;
    const beacon_uuid = req.body.beacon_uuid;

    const price = 50.0; // Double
    const endTimestamp = Date.now();
    const ticketDocRef = firestore.collection("users").doc(email).collection("tickets").doc(ticketId);
    const beaconDocRef = firestore.collection("vy_beacon_list").doc(beacon_uuid);

    firestore.runTransaction(transaction => {
        return transaction.getAll(ticketDocRef, beaconDocRef).then(docs => {
            const ticketDoc = docs[0];
            const beaconDoc = docs[1];

            transaction.update(ticketDocRef, {
                end_station: beaconDoc.data().station,
                end_timestamp: endTimestamp,
                price: price
            });

            const startTimestamp = ticketDoc.data().start_timestamp;
            const duration = endTimestamp - startTimestamp;

            return res.json({
                duration: duration,
                price: price
            })
        });
    }).catch((error) => {
        console.log(error);
        return res.status(400).json({"message": error});
    });
});

app.get("/api/users", (req, res) => {
    const collection = firestore.collection("users");
    let users = [];

    collection.get().then((querySnapshot) => {
        querySnapshot.forEach(doc => {
            users.push({"email": doc.id});
        });

        return res.json({"users": users});
    }).catch((error) => {
        return res.status(400).json({"message": "Unable to connect to Firestore."});
    });
});

app.get("/api/tickets", (req, res) => {
    const userCollection = firestore.collection("users");
    const promises = [];
    let tickets = [];
    let email = "";
    
    const promise = userCollection.get();

    promise.then(querySnapshot => {
        querySnapshot.forEach(doc => {
            email = doc.id;
            const ticketCollection = firestore.collection("users").doc(email).collection("tickets");
            const promise = ticketCollection.get();
            
            promises.push(promise);
        });

        // eslint-disable-next-line promise/no-nesting
        return Promise.all(promises).then(results => {
            results.forEach(querySnapshot => {
                querySnapshot.forEach(doc => {
                    const boardTimestamp = new Date(doc.data().start_timestamp).toLocaleString("no-NO");
                    let disembarkTimestamp = new Date(doc.data().end_timestamp).toLocaleString("no-NO");
                    let endStation = doc.data().end_station;
                    let price = doc.data().price + " NOK";
                    let timeTaken = 0;
                    email = doc.ref.parent.parent.id;

                    if (endStation === "") {
                        timeTaken = "N/A";
                        price = "N/A";
                        disembarkTimestamp = "N/A";
                        endStation = "N/A";
                    }

                    tickets.push({
                        "email": email,
                        "startingStation": doc.data().start_station,
                        "endingStation": endStation,
                        "time_taken": timeTaken,
                        "boarded": boardTimestamp,
                        "disembarked": disembarkTimestamp,
                        "price": price
                    });
                });
            });

            return res.json({"tickets": tickets});
        })
    }).catch((error) => {
        return res.status(400).json({"message": "Unable to connect to Firestore."});
    });
});

app.get('/api/photos', (req, res) => {
    void (req);
    return res.status(200).json(mockPhotos);
});

app.get('/api/photos/:photoId', (req, res) => {
    void (req);
    return res.status(200).json(mockPhotos.photos[0]);
});

app.get('/api/users/:userId', (req, res) => {
    console.log('Request Query Params: ', req.query);
    console.log('Request URL Params: ', req.params);

    if (!req.params.hasOwnProperty('userId')) {
        return res.status(400).json({"message": "The `userId` is required."});
    }

    return res.status(200).json({
            "userId": req.params.userId,
            "name": faker.name.findName(),
            "email": faker.internet.email()
        }
    )
});

app.post('/api/register', (req, res) => {
    console.log('Request Body Params: ', req.body);

    if (req.body.hasOwnProperty('userId') && req.body.userId === "taken") {
        return res.status(400)
            .json(mockUser.registerFailedUsernameExists);
    }

    var response = mockUser.registerSuccess
    response.requestData = req.body

    return res.status(200).json(response);
});

app.get('/api/vy_beacon_list/:beacon', (req, res) => {
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

app.get('/api/beacons', (req, res) => {
    void (req);
    const beaconCollection = firestore.collection("vy_beacon_list");
    const beacons = [];

    beaconCollection.get().then(querySnapshot => {
        querySnapshot.forEach(doc => {
            beacons.push({
                "uuid": doc.data().uuid,
                "trainCarriageNumber": doc.data().trainCarriageNumber,
                "trainNumber": doc.data().trainNumber,
                "geolocation": doc.data().geolocation,
                "station": doc.data().station,
                "type": doc.data().type
            })
        })

        return res.json(beacons)
    }).catch(error => {
        return res.status(400).json({"message": error});
    })
});

exports.api = functions.https.onRequest(app);
