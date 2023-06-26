const messageService = require('../services/messages.js');
const Messages = require('../models/messages.js');
const jwt = require('jsonwebtoken');
const key = "secret";
const sendNotification = require('../firebase.js');
const User = require('../models/user.js');

//add new message to the db
const addMessage = async (req, res) => {
    // If the request has an authorization header
    if (req.headers.authorization) {
        // Extract the token from that header
        const data = req.headers.authorization.split(" ")[1];
        const parsedData = JSON.parse(data);
        const token = parsedData.token;
        try {
            // Verify the token is valid
            if (validateToken(token)) {
                const { msg } = req.body;
                userId = req.params.id;
                const message = await messageService.addMessage(msg, token, userId);
                const notificationMessage = {
                    id: message.id, created: message.created,
                    sender: message.sender.username, content: message.content
                }

                const receiver = await User.findOne({ id: userId });
                const contact = {
                    id: userId,
                    lastMessage: {
                        content: message.content,
                        created: message.created,
                        id: message.id,
                    },
                    user: {
                        displayName: receiver.displayName,
                        profilePic: receiver.profilePic,
                        username: receiver.username,
                    },
                }

                const messageContact = {
                    id: message.id,
                    created: message.created,
                    sender: {
                        username: message.sender.username,
                        displayName: message.sender.displayName,
                        profilePic: message.sender.profilePic,
                    },
                    content: message.content,
                }
                const messagePackage = {
                    message: messageContact,
                    contact: contact
                }

                sendNotification(messagePackage, notificationMessage, message.receiver);
                res.status(200).json({
                    id: message.id, created: message.created,
                    sender: message.sender, content: message.content
                });
            } else {
                res.status(401).json({ error: 'Invalid token' });
            }
        } catch (error) {
            console.log(error);
            res.status(500).json({ error: 'Something went wrong' });
        }
    }
    else
        return res.status(403).send('Token required');

};

//get user messages- with all his contacts
const getUserMessages = async (req, res) => {
    // If the request has an authorization header
    if (req.headers.authorization) {
        // Extract the token from that header
        const data = req.headers.authorization.split(" ")[1];
        const parsedData = JSON.parse(data);
        const token = parsedData.token;

        try {
            // Verify the token is valid
            if (validateToken(token)) {
                userId = req.params.id;
                const messages = await messageService.getUserMessages(userId, token);
                res.status(200).json(messages);
            } else {
                res.status(401).json({ error: 'Invalid token' });

            }
        } catch (error) {
            res.status(500).json({ error: 'Something went wrong' });
        }
    }
    else
        return res.status(403).send('Token required');
};

//delete contact from user list chat
const deleteContact = async (req, res) => {
    // If the request has an authorization header
    if (req.headers.authorization) {
        // Extract the token from that header
        const data = req.headers.authorization.split(" ")[1];
        const parsedData = JSON.parse(data);
        const token = parsedData.token;
        try {
            // Verify the token is valid
            if (validateToken(token)) {
                contactId = req.params.id;
                await messageService.deleteContact(token, contactId);
                res.status(200).json();
            } else {
                res.status(401).json({ error: 'Invalid token' });
            }
        } catch (error) {
            res.status(500).json({ error: 'Something went wrong' });
        }
    }
    else
        return res.status(403).send('Token required');
}

//get all the user messages- unclude last message from each contact
const getUsers = async (req, res) => {
    // If the request has an authorization header
    if (req.headers.authorization) {
        // Extract the token from that header
        const data = req.headers.authorization.split(" ")[1];
        const parsedData = JSON.parse(data);
        const token = parsedData.token;
        try {
            // Verify the token is valid
            if (validateToken(token)) {
                const users = await messageService.getUsers(token);
                res.status(200).json(users);
            } else {
                console.log("error2");
                res.status(401).json({ error: 'Invalid token' });
            }
        } catch (error) {
            res.status(500).json({ error: 'Something went wrong' });
        }
    }
    else
        return res.status(403).send('Token required');
}

//validate Token
function validateToken(token) {
    try {
        const decoded = jwt.verify(token, key);
        return decoded !== undefined;
    } catch (error) {
        return false;
    }
}

module.exports = { addMessage, getUserMessages, getUsers, deleteContact };