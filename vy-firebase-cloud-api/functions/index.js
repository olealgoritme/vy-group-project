'use strict';

const functions = require('firebase-functions');
const admin = require('firebase-admin');
const express = require('express');
const app = express();

admin.initializeApp();
const firestore = admin.firestore();
const cors = require('cors')({origin: true});
app.use(cors);

app.post('/api/boarding', (req, res) => {
    const email = req.body.email;
    const boardingUuid = req.body.boarding_uuid;
    const stationUuid = req.body.station_uuid;
    const stationName = req.body.station_name;
    const timestamp = Date.now() + 7200000; // +2 hours for Norwegian timezone - terrible solution but works for now

    firestore.collection("users").doc(email).collection("tickets").add({
        startStation: stationName,
        startTimestamp: timestamp,
        endStation: "",
        endTimestamp: 0,
        price: 0,
        boardingUuid: boardingUuid
    }).then((doc) => {
        firestore.collection("users").doc(email).set({email: email});
        firestore.collection("vy_beacon_list").doc(boardingUuid).update({"numBoarded": admin.firestore.FieldValue.increment(1)});

        return res.json({
            ticketId: doc.id,
            trainDestination: "Kongsvinger"
        })
    }).catch((error) => {
        console.log(error);
        return res.status(400).json({"message": "Unable to connect to Firestore."});
    });
});

app.post('/api/disembarking', (req, res) => {
    const email = req.body.email;
    const ticketId = req.body.ticket_id;
    const beacon_uuid = req.body.beacon_uuid;

    const price = 50.0; // Double
    const endTimestamp = Date.now() + 7200000; // +2 hours for Norwegian timezone - terrible solution but works for now
    const ticketDocRef = firestore.collection("users").doc(email).collection("tickets").doc(ticketId);
    const beaconDocRef = firestore.collection("vy_beacon_list").doc(beacon_uuid);

    firestore.runTransaction(transaction => {
        return transaction.getAll(ticketDocRef, beaconDocRef).then(docs => {
            const ticketDoc = docs[0];
            const beaconDoc = docs[1];

            transaction.update(ticketDocRef, {
                endStation: beaconDoc.data().station,
                endTimestamp: endTimestamp,
                price: price
            });

            firestore.collection("vy_beacon_list").doc(ticketDoc.data().boardingUuid).update({"numBoarded": admin.firestore.FieldValue.increment(-1)})

            return res.json({
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

    collection.get().then((querySnapshot) => {
        const users = querySnapshot.docs.map(doc => {return ({"email": doc.id});});

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
                    let boardTimestamp = new Date(doc.data().startTimestamp);
                    let disembarkTimestamp = new Date(doc.data().endTimestamp);
                    let diff = disembarkTimestamp - boardTimestamp;
                    let minutesTaken = Math.floor((diff / 1000) / 60);

                    let endStation = doc.data().endStation;
                    let price = doc.data().price + " NOK";
                    email = doc.ref.parent.parent.id;

                    boardTimestamp = new Date(boardTimestamp).toLocaleString("no-NO");
                    disembarkTimestamp = new Date(disembarkTimestamp).toLocaleString("no-NO");

                    if (endStation === "") {
                        minutesTaken = "N/A";
                        price = "N/A";
                        disembarkTimestamp = "N/A";
                        endStation = "N/A";
                    } else {
                        minutesTaken = minutesTaken + " minutes";
                    }

                    tickets.push({
                        "email": email,
                        "startingStation": doc.data().startStation,
                        "endingStation": endStation,
                        "timeTaken": minutesTaken,
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

app.get("/api/capacity", (req, res) => {
    const beaconCollection = firestore.collection("vy_beacon_list").where("type", "==", "boarding");

    beaconCollection.get().then(querySnapshot => {
        const boardingBeacons = querySnapshot.docs.filter(doc => doc.data().type === "boarding");
        const trainNumbers = boardingBeacons.map(doc => doc.data().trainNumber).filter((trainNumber, index, self) => self.indexOf(trainNumber) === index);

        const trainCapacities = trainNumbers.map(trainNumber => {
            const trainBeacons = boardingBeacons.filter(doc => doc.data().trainNumber === trainNumber);
            const maxCapacity = trainBeacons.map(doc => doc.data().carriageCapacity).reduce((sum, current) => sum + current, 0);
            const usedCapacity = trainBeacons.map(doc => doc.data().numBoarded).reduce((sum, current) => sum + current, 0);

            const carriageBeaconInfo = trainBeacons.map(doc => {
                return ({
                    "uuid": doc.data().uuid,
                    "carriageCapacity": doc.data().carriageCapacity,
                    "carriageNumber": doc.data().trainCarriageNumber,
                    "numBoarded": doc.data().numBoarded
                })
            })

            return ({
                "trainNumber": trainNumber,
                "maxCapacity": maxCapacity,
                "usedCapacity": usedCapacity,
                "trainBeacons": carriageBeaconInfo
            })
        })

        return res.json(trainCapacities);
    }).catch(error => {
        console.log(error);
        return res.json(error);
    })
});

app.get('/api/beacons', (req, res) => {
    void (req);
    const beaconCollection = firestore.collection("vy_beacon_list");

    beaconCollection.get().then(querySnapshot => {
        const beacons = querySnapshot.docs.map(doc => {
            return ({
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
        console.log(error);
        return res.status(400).json({"message": error});
    })
});

exports.api = functions.https.onRequest(app);
