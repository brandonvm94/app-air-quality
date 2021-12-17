'use strict'

const firebase = require('../db')
const Details = require('../models/details')
const firestore = firebase.firestore();

const DETAILS_ENDPOINT = "details"

const getAllDetails = (req, res, next) => {
    firestore.collection(DETAILS_ENDPOINT).get()
    .then(data => {
        let details = [];
        data.forEach(doc=> {
            const {sensorId, date, temperature, humidity, no2, o3, no, so2, pm1, pm25, pm10, co, h2s, ambientTemperature, ambientHumidity, ambientPressure} = doc.data();
            const detail = new Details(sensorId, date, temperature, humidity, no2, o3, no, so2, pm1, pm25, pm10, co, h2s, ambientTemperature, ambientHumidity, ambientPressure);
            details = [...details, {...detail}];
        });
        res.json(details)
    })
    .catch(error => res.status(400).json({ message: error.message }));
}
const getDetail = (req, res, next) => {  
    const sensorDate = req.params.date;
    const sensorId = req.params.sensorId;

    firestore.collection(DETAILS_ENDPOINT)
        .where('sensorId', '==', sensorId)
        .where('date', '==', sensorDate)
        .get()
        .then(querySnapshot => {
            if (!querySnapshot.empty) {
                return res.json(querySnapshot.docs[0].data())
            } else {
                return res.status(400).json({ message: 'No hay detalles de este sensor' });
            }
        })
        .catch(error => res.status(400).json({ message: error.message }));
  }
  module.exports = {
    getAllDetails,
    getDetail
  }