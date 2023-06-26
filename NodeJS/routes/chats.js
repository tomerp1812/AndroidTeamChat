const userController = require('../controllers/user.js')
const messagesController = require('../controllers/messages.js');
const express = require('express');
var router = express.Router();


//add new contact to user list - /api/Chats
router.route('/').post(userController.postUserDetails);

//add new message to the DB -  api/Chats/{id}/Messages
router.route('/:id/Messages'). post(messagesController.addMessage);

//get user messages - api/Chats/{id}/Messages
router.route('/:id/Messages').get(messagesController.getUserMessages);
router.route('/:id').get(messagesController.getUserMessages);

//get users - /api/Chats
router.route('/').get(messagesController.getUsers);

//delete contact user from the list
router.route('/:id').delete(messagesController.deleteContact);

module.exports = router;