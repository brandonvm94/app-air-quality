const express = require('express');
const { addRole } = require('../controllers/roleController');

const router = express.Router();

router.post('/role', addRole);

module.exports = {
  routes: router
}