'use strict';

const firebase = require('../db');
const firestore = firebase.firestore();
const firebaseAuth = firebase.auth();

const User = require('../models/user');

const signUpUser = (req, res, next) => {  
  const { name = '', email = '', password = '' } = req.body;

  firebaseAuth.createUserWithEmailAndPassword(email, password)
    .then(data => {
      const user = new User(data.user.uid, name, data.user.email, 2);        
      firestore.collection('users').doc(firebaseAuth.currentUser.uid).set({ ...user })
        .then(() => res.json({message: 'Usuario registrado correctamente'}))
        .catch(error => res.status(400).send(error.message))
        .finally(() => firebaseAuth.signOut());
    })
    .catch(error => res.status(404).send(error.message));
}

const signInUser = (req, res, next) => {  
  const { email = '', password = '' } = req.body;
  firebaseAuth.signInWithEmailAndPassword(email, password)
    .then(() => {
      firestore.collection('users').doc(firebaseAuth.currentUser.uid).get()
        .then(doc => {
          const { id, email, name, roleId } = doc.data();
          const user = new User(id, name, email, roleId);
          res.json({ ...user });
        })
        .catch(error => res.status(404).json({ message: error.message }));
    })
    .catch(error => res.status(404).json({ message: error.message }));
}

const signOut = (req, res, next) => {
  firebaseAuth.signOut()
    .then(() => res.json({ message: 'Usuario ha cerrado sesion.' }))
    .catch(error => res.status(404).send(error.message));
}

const getCurrentSessionUser = (req, res, next) => {
  if (firebaseAuth.currentUser) {
    firestore.collection('users').doc(firebaseAuth.currentUser.uid).get()
      .then(doc => {
        const { id, email, name, roleId } = doc.data();
        const user = new User(id, name, email, roleId);
        res.json({ ...user });
      })
      .catch(error => res.status(404).json({ message: error.message }));
  } else {
    res.status(404).json({ ...new User() });
  }  
}

const checkSessionActive = (req, res, next) => {
  res.json({ isCurrentSession: firebaseAuth.currentUser !== null });
}

module.exports = {
  getCurrentSessionUser,
  checkSessionActive,
  signUpUser,
  signInUser,
  signOut,
}