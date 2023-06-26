const firebaseTokenMap = require('./firebaseTokenMap');
const admin = require('firebase-admin');
const serviceAccount = require('./ServiceAccountKey.json');
const connectedUsers = require('./ConnectedUsers.js');
const { set, get } = require('./SocketIo');

// Initialize the Firebase Admin SDK
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount)
});

function sendNotification(messagePackage, notificationMessage, receiver) {
    for (const [firebaseToken, username] of Object.entries(firebaseTokenMap)) {
        if (username === receiver) {
            // Define the notification payload
            const payload = {
                notification: {
                    title: 'New Message',
                    body: JSON.stringify(notificationMessage)
                },
            };
            // Send the push notification
            return admin.messaging().send({
                token: firebaseToken,
                ...payload
            }).then(response => {
                // Handle the response
                console.log('Notification sent successfully:', response);
                return response;
            })
                .catch(error => {
                    // Handle the error
                    console.error('Error sending notification:', error);
                    throw error;
                });
        }
    }

    const desiredUser = Object.keys(connectedUsers).find((user) => user === receiver);

    if (desiredUser) {
        const socketIds = connectedUsers[desiredUser];
        socketIds.forEach((id) => {
            get().to(id).emit('receive', messagePackage);
        });
    }



}

module.exports = sendNotification;