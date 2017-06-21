'use strict';

const functions = require('firebase-functions');


// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
const nodemailer = require('nodemailer');
// firebase functions:config:set gmail.email="poolindecode@gmail.com" 
// firebase functions:config:set gmail.password="7Pineapple"
const gmailEmail = encodeURIComponent("poolindecode@gmail.com" );
const gmailPassword = encodeURIComponent("7Pineapple");

const mailTransport = nodemailer.createTransport(
 `smtps://${gmailEmail}:${gmailPassword}@smtp.gmail.com`);

 // Your company name to include in the emails
 // TODO: Change this to your app or company name to customize the email sent.
 const APP_NAME = 'POOL-IN';

 // [START sendWelcomeEmail]
 /**
  * Sends a welcome email to new user.
  */
 // [START onCreateTrigger]
 exports.sendWelcomeEmail = functions.auth.user().onCreate(event => {
 // [END onCreateTrigger]
   // [START eventAttributes]
   const user = event.data; // The Firebase user.

   const email = user.email; // The email of the user.
   const displayName = user.displayName; // The display name of the user.
   // [END eventAttributes]

   return sendWelcomeEmail(email, displayName);
 });
 // [END sendWelcomeEmail]

 // [START sendByeEmail]
 /**
  * Send an account deleted email confirmation to users who delete their accounts.
  */
 // [START onDeleteTrigger]
 exports.sendByeEmail = functions.auth.user().onDelete(event => {
 // [END onDeleteTrigger]
 const user = event.data;

 const email = user.email;
 const displayName = user.displayName;

 return sendGoodbyEmail(email, displayName);
});
 // [END sendByeEmail]

 // Sends a welcome email to the given user.
 function sendWelcomeEmail(email, displayName) {
   const mailOptions = {
     from: `${APP_NAME} <noreply@firebase.com>`,
     to: email
   };

   // The user subscribed to the newsletter.
   mailOptions.subject = `Welcome to ${APP_NAME}!`;
   mailOptions.text = `Hey ${displayName || ''}! Welcome to ${APP_NAME}. We hope you will enjoy our service.`;
   return mailTransport.sendMail(mailOptions).then(() => {
     console.log('New welcome email sent to:', email);
   });
 }

 // Sends a goodbye email to the given user.
 function sendGoodbyEmail(email, displayName) {
   const mailOptions = {
     from: `${APP_NAME} <noreply@firebase.com>`,
     to: email
   };

   // The user unsubscribed to the newsletter.
   mailOptions.subject = `Bye!`;
   mailOptions.text = `Hey ${displayName || ''}!, We confirm that we have deleted your ${APP_NAME} account.`;
   return mailTransport.sendMail(mailOptions).then(() => {
     console.log('Account deletion confirmation email sent to:', email);
   });
 }









 // Filtering Bad Words and Capitalization

 const capitalizeSentence = require('capitalize-sentence');
 const Filter = require('bad-words');
 const badWordsFilter = new Filter();

 exports.moderator = functions.database
 .ref('/posts/{postId}/comments/{commentId}/body').onWrite(event => {
  var message = event.data.val();

  console.log('Message is: ', message);

  if (message && !message.sanitized) {
        // Retrieved the message values.
        console.log('Retrieved message content: ', message);

        // Run moderation checks on on the message and moderate if needed.
        var moderatedMessage = moderateMessage(message);

        // Update the Firebase DB with checked message.
        console.log('Message has been moderated. Saving to DB: ', moderatedMessage);
        return event.data.adminRef.update({
          text: moderatedMessage,
          sanitized: true,
          moderated: message.text !== moderatedMessage
        });
      }
    });

 exports.moderator = functions.database
 .ref('/posts/{postId}/body').onWrite(event => {
  var message = event.data.val();

  console.log('Message is: ', message);

  if (message && !message.sanitized) {
        // Retrieved the message values.
        console.log('Retrieved message content: ', message);

        // Run moderation checks on on the message and moderate if needed.
        var moderatedMessage = moderateMessage(message);

        // Update the Firebase DB with checked message.
        console.log('Message has been moderated. Saving to DB: ', moderatedMessage);
        return event.data.adminRef.update({
          text: moderatedMessage,
          sanitized: true,
          moderated: message.text !== moderatedMessage
        });
      }
    });


// Moderates the given message if appropriate.

function moderateMessage(message) {
  // Re-capitalize if the user is Shouting.
  if (isShouting(message)) {
    console.log('User is shouting. Fixing sentence case...');
    message = stopShouting(message);
  }

  // Moderate if the user uses SwearWords.
  if (containsSwearwords(message)) {
    console.log('User is swearing. moderating...');
    message = moderateSwearwords(message);
  }

  return message;
}

// Returns true if the string contains swearwords.
function containsSwearwords(message) {
  return message !== badWordsFilter.clean(message);
}

// Hide all swearwords. e.g: Crap => ****.
function moderateSwearwords(message) {
  return badWordsFilter.clean(message);
}

// Detect if the current message is shouting. i.e. there are too many Uppercase
// characters or exclamation points.
function isShouting(message) {
  console.log('hey hey ', message);
  return message.replace(/[^A-Z]/g, '').length > message.length / 2 || message.replace(/[^!]/g, '').length >= 3;
}

// Correctly capitalize the string as a sentence (e.g. uppercase after dots)
// and remove exclamation points.
function stopShouting(message) {
  return capitalizeSentence(message.toLowerCase()).replace(/!+/g, '.');
}