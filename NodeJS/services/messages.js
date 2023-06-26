const Message = require('../models/messages.js');
const User = require('../models/user.js');

//add new message to the DB
const addMessage = async (msg, token, userId) => {
    const sender = await User.findOne({ token: token });
    const receiver = await User.findOne({ id: userId });
    const latestMessageCounter = await Message.findOne().sort({ id: -1 }).limit(1).select('id');
    const idCounter = latestMessageCounter ? latestMessageCounter.id + 1 : 1;
    const message = new Message({
        receiver: receiver.username,
        id: idCounter,
        sender: {
            username: sender.username,
            displayName: sender.displayName,
            profilePic: sender.profilePic
        },
        content: msg
    });
    return await message.save();
};


//get user messages
const getUserMessages = async (userId, token) => {
    const receiver = await User.findOne({ id: userId });
    const sender = await User.findOne({ token: token });
    const messages = await Message.find({
        $or: [
            {
                $and: [
                    { 'sender.username': sender.username },
                    { receiver: receiver.username },
                ]
            },
            {
                $and: [
                    { 'sender.username': receiver.username },
                    { receiver: sender.username },
                ]
            }
        ]
    });
    return messages;

}

//delete contact from user chat list
const deleteContact = async (token, contactId) => {
    const user = await User.findOne({ token });
    const contactToRemove = await User.findOne({ id: contactId });
    if (contactToRemove) {
        user.contacts = user.contacts.filter(contact => contact.username !== contactToRemove.username);
        await user.save();
      }

};

//get all user contacts including last message
const getUsers = async (token) => {
    const usersArray = [];
    const user = await User.findOne({ token });
    const userContacts = user.contacts;
    for (const contact of userContacts) {
        const newContact = await User.findOne({ username: contact.username });
        const lastMessage = await Message.findOne({
            $or: [
              {
                $and: [
                  { 'sender.username': user.username },
                  { receiver: newContact.username },
                ]
              },
              {
                $and: [
                  { 'sender.username': newContact.username },
                  { receiver: user.username },
                ]
              }
            ]
          })
            .sort({ created: -1 })
            .select('content id created');
        const userWithLastMessage = {
            id: newContact.id,
            user: {
                username: newContact.username,
                displayName: newContact.displayName,
                profilePic: newContact.profilePic
            },
            lastMessage: lastMessage ? {
                id: lastMessage.id,
                created: lastMessage.created,
                content: lastMessage.content
            } : null
        };

        usersArray.push(userWithLastMessage);
    }
    return usersArray;
};
module.exports = { addMessage, getUserMessages, getUsers,deleteContact };