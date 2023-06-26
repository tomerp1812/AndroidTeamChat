const firebaseTokenMap = require('../firebaseTokenMap.js')
const userService = require('../services/user.js');
const User = require('../models/user.js');
const jwt = require('jsonwebtoken');

const key = "secret";

// registration
const createUser = async (req, res) => {
    try {
        const { username, password, displayName, profilePic } = req.body;

        //username is already registered
        const existingUser = await User.findOne({ username });
        if (existingUser) {
            res.status(409).json({ error: 'User already exists' });
            return;
        }
        const user = await userService.createUser(username, password, displayName, profilePic);
        // Extract the desired fields from the user object
        const { username: userUsername, displayName: userDisplayName, profilePic: userProfilePic } = user;
        res.status(200).json({ username: userUsername, displayName: userDisplayName, profilePic: userProfilePic });

    } catch (error) {
        res.status(500).json({ error: 'Error registering user' });
    }
};

//get user details
const getUserDetails = async (req, res) => {

    try {
        const { username } = req.params;
        const user = await userService.getUserDetails(username);
        if (!user) {
            return res.status(404).json({ error: 'User not found' });
        }
        // Extract the desired fields from the user object
        const { username: userUsername, displayName: userDisplayName, profilePic: userProfilePic } = user;
        res.status(200).json({ username: userUsername, displayName: userDisplayName, profilePic: userProfilePic });
    } catch (error) {
        res.status(500).json({ error: 'Something went wrong' });
    }
};

//login
const login = async (req, res) => {
    try {
        const firebaseToken = req.headers.firebasetoken;
        const { username, password } = req.body;

        // update Token map
        if(firebaseToken != undefined){
            firebaseTokenMap[firebaseToken] = username;
        }
        const user = await userService.login(username, password);
        if (!user) {
            return res.status(404).json({ error: 'Invalid username or password' });
        }
    
        res.status(200).json({ token: user.token });
    } catch (error) {
        res.status(500).json({ error: 'Something went wrong' });
    }
};

//add new contact to user list
const postUserDetails = async (req, res) => {
    // If the request has an authorization header
    if (req.headers.authorization) {
        // Extract the token from that header
        const data = req.headers.authorization.split(" ")[1];
        const parsedData = JSON.parse(data);
        const token = parsedData.token;
        try {
            // Verify the token is valid
            if (validateToken(token)) {
                const { username } = req.body;
                const user = await userService.postUserDetails(username, token);
                if (user == null) {
                    return res.status(404).json({ error: 'Invalid username' });
                }
                res.status(200).json({ id: user.id, user: { username: user.username, displayName: user.displayName, profilePic: user.profilePic } });
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

//validate Token
function validateToken(token) {
    try {
        const decoded = jwt.verify(token, key);
        return decoded !== undefined;
    } catch (error) {
        return false;
    }
}

module.exports = { createUser, getUserDetails, login, postUserDetails };