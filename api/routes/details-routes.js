const express = require('express');
const { getAllDetails, getDetail } = require('../controllers/detailsController');

const router = express.Router();

router.get('/details', getAllDetails);
router.get('/details/:date', getDetail);

module.exports = {
    routes: router
}