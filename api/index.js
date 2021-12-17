'use strict';
const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const config = require('./config');

const authRoutes = require('./routes/auth-routes');
const roleRoutes = require('./routes/role-routes');
const sensorsRoutes = require('./routes/sensor-routes');
const myProfileRoutes = require('./routes/my-profile-routes');
const usersRoutes = require('./routes/users-routes');
const detailsRoutes = require('./routes/details-routes');
const app = express();

app.use(express.json());
app.use(cors());
app.use(bodyParser.json());

app.use('/api', authRoutes.routes);
app.use('/api', myProfileRoutes.routes);
app.use('/api', usersRoutes.routes);
app.use('/api', roleRoutes.routes);
app.use('/api', sensorsRoutes.routes);
app.use('/api', detailsRoutes.routes);

app.listen(config.port, () => console.log('App is listening on url http://localhost:' + config.port));
