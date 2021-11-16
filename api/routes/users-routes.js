const express = require('express');
const { addUser, getUser, getUsers, deleteUser, updateUser } = require('../controllers/usersController');

const router = express.Router();

router.post('/user', addUser);
router.get('/user/:id', getUser);
router.get('/users', getUsers);
router.put('/user/:id', updateUser);
router.delete('/user/:id', deleteUser);

module.exports = {
  routes: router
}