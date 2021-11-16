const express = require('express');
const { getCurrentSessionUser, checkSessionActive, signUpUser, signInUser, signOut } = require('../controllers/authController');

const router = express.Router();

router.post('/signup', signUpUser);
router.post('/signin', signInUser);
router.post('/signout', signOut);
router.get('/sessionActive', getCurrentSessionUser);

module.exports = {
  routes: router
}