const express = require('express');
const { getAllSensors } = require('../controllers/sensorController');

const router = express.Router();

router.get('/sensors', getAllSensors);

module.exports = {
  routes: router
}