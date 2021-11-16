const express = require('express');
const { changePassword, changeMyPersonalInfo } = require('../controllers/myProfileController');

const router = express.Router();

router.post('/changePassword', changePassword);
router.post('/changeMyInfo', changeMyPersonalInfo);

module.exports = {
  routes: router
}