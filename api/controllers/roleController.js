'use strict';

const firebase = require('../db');
const Role = require('../models/role');
const firestore = firebase.firestore();

const ROLE_COLLECTION = "roles";

const addRole = (req, res, next) => {
  const data = req.body;
  firestore.collection(ROLE_COLLECTION).doc().set(data)
    .then(() => res.json({ message: 'Role creado correctamente!'}))
    .catch(error => res.status(400).json({ message: error.message }));
}

module.exports = {
  addRole,
}