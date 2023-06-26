const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Contact = new Schema({
    username: {
        type: String,
        required: true
    },
});

const User = new Schema({
    username: {
        type: String,
        required: true
    },
    password: {
        type: String,
        required: true
    },

    displayName: {
        type: String,
        required: true
    },
    profilePic: {
        type: String,
        required: true
    },
    id: {
        type: Number,
        required: true
    },
    token: {
        type: String
    },
    contacts: [Contact] // Array of Contact objects

});

module.exports = mongoose.model('User', User);
