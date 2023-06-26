
const users = require('./routes/user');
const chats = require('./routes/chats');
const mongoose = require('mongoose');
const cors = require('cors');
const bodyParser = require('body-parser');
const express = require('express');
const connectedUsers = require('./ConnectedUsers');

//create the Express app
var app = express();
app.use(cors());
app.use(express.static('public'));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.json());
const http = require('http');
const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server, {
  cors: {
    origin: '*',
    methods: ['GET', 'POST']
  }
});

const { set, get } = require('./SocketIo.js');
set(io);

const customEnv = require('custom-env');
customEnv.env(process.env.NODE_ENV, './config');


//connect to MongoDB
mongoose.connect(process.env.CONNECTION_STRING, {
  useNewUrlParser: true,
  useUnifiedTopology: true
}).then(() => {
  console.log("Connected to MongoDB");
}).catch((error) => {
  console.log("Error connecting to MongoDB:", error);
});


// Set up routes
app.use('/api/Users', users);
app.use('/api/Chats', chats);
app.use('/api', users);

//each time a new tab is opened.
io.on('connection', (socket) => {
  //when a user logins.
  socket.on('login', (username) => {
    //if its first tab that the user opened
    if (!connectedUsers.hasOwnProperty(username)) {
      connectedUsers[username] = [];
    }
    //might be more than 1 tab the user is logined.
    connectedUsers[username].push(socket.id);
  });

  //when a user sends a message
  socket.on('message', (messagePackage) => {
    const desiredUser = Object.keys(connectedUsers).find((user) => user === messagePackage.contact.user.username);

    if (desiredUser) {
      const socketIds = connectedUsers[desiredUser];
      socketIds.forEach((id) => {
        io.to(id).emit('receive', messagePackage);
      });
    }
  });



  //when a user is logging out
  socket.on('logout', (disconnecter) => {
    if (connectedUsers.hasOwnProperty(disconnecter)) {
      const userSocketIds = connectedUsers[disconnecter];
      for (var i = 0; i < userSocketIds.length; i++) {
        if (userSocketIds[i] === socket.id) {
          userSocketIds.splice(i, 1);
          if (userSocketIds.length === 0) {
            delete connectedUsers[disconnecter];
          }
        }
      }
    }
  });


});

//start the server
server.listen(process.env.PORT);