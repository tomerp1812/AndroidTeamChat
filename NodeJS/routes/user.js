const userController = require('../controllers/user.js')
const messagesController = require('../controllers/messages.js');
const express = require('express');
var router = express.Router();

//registration- /api/Users
router.route('/').post(userController.createUser);

//get user details- /api/Users/{username}
router.route('/:username').get(userController.getUserDetails);

//login - api/Token
router.route('/Tokens').post(userController.login);


module.exports = router;