'use strict';

const firebase = require('../db');
const User = require('../models/user');
const firebaseAuth = firebase.auth();
const firestore = firebase.firestore();

const USERS_ENDPOINT = 'users';

// CREATE user
const addUser = (req, res, next) => {
  const { name = '', email = '', password = '', roleId } = req.body;
  let currentUser = firebaseAuth.currentUser;

  firebaseAuth.createUserWithEmailAndPassword(email, password)
    .then(data => {
      const user = new User(data.user.uid, name, data.user.email, roleId);        
      firestore.collection(USERS_ENDPOINT).doc(firebaseAuth.currentUser.uid).set({ ...user })
        .then(() => res.json({message: 'Usuario creado correctamente.'}))
        .catch(error => res.status(400).json({ message: error.message }))
        .finally(() => firebaseAuth.updateCurrentUser(currentUser));
    })
    .catch(error => res.status(400).json({ message: error.message }));
}

// GET User
const getUser = (req, res, next) => {  
  const userId = req.params.id;
  firestore.collection(USERS_ENDPOINT).doc(userId).get()
    .then(response => res.json(response.data()))
    .catch(error => res.status(400).json({ message: error.message }));
}

// GET All Users
const getUsers = (req, res, next) => {  
  firestore.collection(USERS_ENDPOINT).get()
    .then(data => {
      let users = [];
      data.forEach(doc => {
        const { id, name, email, roleId } = doc.data();
        const user = new User(id, name, email, roleId);
        users = [ ...users, { ...user } ];
      });
      res.json(users);
    })
    .catch(error => res.status(400).json({ message: error.message }));
}

// UPDATE User
const updateUser = (req, res, next) => {  
  const userId = req.params.id;
  const { name, email, roleId } = req.body;
  const user = new User(userId, name, email, roleId);

  firestore.collection(USERS_ENDPOINT).doc(user.id).update({ ...user })
    .then(() => res.json({ message: 'Usuario actualizado correctamente.' }))
    .catch(error => res.status(404).send(error.message));
}

// DELETE User
const deleteUser = (req, res, next) => {
  const userId = req.params.id;

  firebaseAuth.delete(userId)
    .then(() => {    
      firestore.collection(USERS_ENDPOINT).doc(userId).delete()
        .then(() => res.json({message: 'Usuario eliminado correctamente.'}))
        .catch(error => res.status(400).json({ message: error.message }));
    })
    .catch(error => res.status(400).json({ message: error.message }));
}

module.exports = {
  addUser,
  deleteUser,
  getUser,
  getUsers,
  updateUser,
}