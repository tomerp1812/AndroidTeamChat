const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Messages = new Schema({
    receiver: {
        type: String,
        required: true
    },
    id: {
        type: Number,
        require: true
    },
    created: {
        type: Date,
        default: Date.now
    },
    sender: {
        username: {
            type: String,
            require: true
        },
        displayName: {
            type: String,
            require: true
        },
        profilePic: {
            type: String,
            require: true
        }

    },
    content: {
        type: String,
        require: true
    }

});

module.exports = mongoose.model('Messages', Messages);