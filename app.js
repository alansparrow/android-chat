const Config = require('./config');
const LogUtil = require('./utilities/log-util');
const express = require('express');
const app = express();
const http = require('http');
const server = http.createServer(app);

const TAG = require('path').basename(__filename);

app.get('/', (req, res) => {
    res.send('<h1>Hello, World!</h1>');
});

server.listen(Config.PORT, () => {
    LogUtil.log(TAG, 'Listening on port: ' + Config.PORT);
});