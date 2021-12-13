'use strict';

const firebase = require('../db');
const Sensor = require('../models/sensor');
const firestore = firebase.firestore();

const SENSORS_ENDPOINT = 'sensors';

const getAllSensors = (req, res, next) => {  
  firestore.collection(SENSORS_ENDPOINT).get()
    .then(data => {
      let sensors = [];
      data.forEach(doc => {
        const { id, name, lat, long } = doc.data();
        const sensor = new Sensor(id, name, lat, long);
        sensors = [ ...sensors, { ...sensor } ];
      });
      res.json(sensors);
    })
    .catch(error => res.status(400).json({ message: error.message }));
}

module.exports = {
  getAllSensors,
}