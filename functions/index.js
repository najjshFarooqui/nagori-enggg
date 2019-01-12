const functions = require('firebase-functions');
// imports firebase-admin module
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);
/* Listens for new messages added to /messages/:pushId and sends a notification to subscribed users */
exports.pushNotification = functions.database.ref('/messages/{pushId}').onWrite(( change,context) => {

var valueObject = change.after.val();
console.log('Push notification event triggered');

console.log(valueObject.message);

	const payload = {
        data: {
            'message': valueObject.message,
			'displayName': valueObject.displayName,
			'timeStamp': valueObject.timeStamp,
			'userId': valueObject.userId
        }
    };
	
	const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24 //24 hours
    };

return admin.messaging().sendToTopic("notifications", payload, options);
});

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
