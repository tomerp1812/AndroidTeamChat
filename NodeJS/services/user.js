const User = require('../models/user.js');
const jwt = require('jsonwebtoken');
const key = "secret";



//registration
const createUser = async (username1, password1, displayName1, profilePic1) => {
    try {
        // Fetch the latest idCounter from the database or other storage
        const latestIdCounter = await User.findOne().sort({ id: -1 }).limit(1).select('id');
        const idCounter = latestIdCounter ? latestIdCounter.id + 1 : 1;
        const user = new User({
            username: username1,
            password: password1,
            displayName: displayName1,
            profilePic: profilePic1,
            id: idCounter
        });

        return await user.save();
    } catch (error) {
        console.log('error');
    }
};

//get user details
const getUserDetails = async (username) => {
    const user = await User.findOne({ username });
    return user;
};

//login
const login = async (username1, password) => {
    let user = await User.findOne({ username: username1, password });
    if (user != null) {
        // create token for the user
        const createToken = jwt.sign({ username: username1 }, key);
        // Find the user by their ID and update the token
        user = await User.findOneAndUpdate(
            { username: username1 },
            { token: createToken },
            { new: true }
        );
    }
    return user;
};


//returns details about a specific user
const postUserDetails = async (username1, token) => {
    const newUser = await User.findOne({ username: username1 });
    const user = await User.findOne({ token });

    if(user.username === newUser.username){
        return null;
    }


    const userHasContact = await User.findOne({
        username: user.username,
        contacts: { $elemMatch: { username: newUser.username } }
    });

    if (userHasContact) {
        return null;
    }
    const newContact = {
        username: username1
    };
    user.contacts.push(newContact);

    await user.save(); // Save the updated user document
    return newUser;

};

module.exports = { createUser, getUserDetails, login, postUserDetails };