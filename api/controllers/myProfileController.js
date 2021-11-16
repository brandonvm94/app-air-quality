'use strict';

const firebase = require('../db');
const firestore = firebase.firestore();
const firebaseAuth = firebase.auth();

// Change current session user password
const changePassword = (req, res, next) => {  
  const currentUser = firebaseAuth.currentUser;
  const { newPassword } = req.body;

  currentUser.updatePassword(newPassword).
    then(() => res.json({ message: 'La contraseña ha sido actualizada correctamente.' }))
    .catch(error => res.status(404).send(error.message));
}

// Change current session user information (email and name)
const changeMyPersonalInfo = (req, res, next) => {  
  const currentUser = firebaseAuth.currentUser;
  const { email } = req.body;

  currentUser.updateEmail(email)
    .then(() => {
      firestore.collection('users').doc(currentUser.uid).update({ ...req.body })
        .then(() => res.json({ message: 'La información de usuario ha sido actualizado correctamente.' }))
        .catch(error => res.status(404).send(error.message));
    })
    .catch(error => res.status(404).send(error.message));
}

module.exports = {
  changeMyPersonalInfo,
  changePassword,
}