const Config = require('./config');
const LogUtil = require('./utilities/log-util');
const express = require('express');
const app = express();
const http = require('http');
const server = http.createServer(app);
const { Server } = require('socket.io');
const io = new Server(server);

const TAG = require('path').basename(__filename);

app.get('/', (req, res) => {
    const file = __dirname + '/index.html';
    LogUtil.log(TAG, file)
    res.sendFile(file);
});

io.on('connection', (socket) => {
    LogUtil.log(TAG, socket.id + ' connected');
    socket.broadcast.emit('chat message', socket.id + ' has joined');

    socket.on('chat message', (msg) => {
        LogUtil.log(TAG, msg);
        io.emit('chat message', socket.id + ": " + msg);
    });

    socket.on('disconnect', () => {
        LogUtil.log(TAG, socket.id + ' disconnected');
        socket.broadcast.emit('chat message', socket.id + ' has left');
    });
})

server.listen(Config.PORT, () => {
    LogUtil.log(TAG, 'Listening on port: ' + Config.PORT);
});