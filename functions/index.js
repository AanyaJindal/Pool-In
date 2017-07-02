'use strict';
let functions = require('firebase-functions');
let admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


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

  if (message) {
        // Retrieved the message values.
        console.log('Retrieved message content: ', message);

        // Run moderation checks on on the message and moderate if needed.
        var moderatedMessage = moderateMessage(message);
        var body = moderatedMessage;

        // Update the Firebase DB with checked message.
        console.log('Message has been moderated. Saving to DB: ', moderatedMessage);
        return event.data.adminRef.set(
          body
        );
      }
    });

 exports.moderator1 = functions.database
 .ref('/posts/{postId}/body').onWrite(event => {
  var message = event.data.val();

  console.log('Message is: ', message);

  if (message) {
        // Retrieved the message values.
        console.log('Retrieved message content: ', message);

        // Run moderation checks on on the message and moderate if needed.
        var moderatedMessage = moderateMessage(message);
        var body = moderatedMessage;

        // Update the Firebase DB with checked message.
        console.log('Message has been moderated. Saving to DB: ', moderatedMessage);
        return event.data.adminRef.set(
          body
        );
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








//  Handling Images

const mkdirp = require('mkdirp-promise');
const gcs = require('@google-cloud/storage')();
const exec = require('child-process-promise').exec;
const LOCAL_TMP_FOLDER = '/tmp/';
var im = require('imagemagick');
const spawn = require('child-process-promise').spawn;

// File extension for the created JPEG files.
const JPEG_EXTENSION = '.jpg';

/**
 * When an image is uploaded in the Storage bucket it is converted to JPEG automatically using
 * ImageMagick.
 */
 exports.resizeImage = functions.storage.object().onChange(event => {
  const object = event.data;
  const fileBucket = object.bucket;
  const filePath = object.name;
  const contentType = object.contentType; // File content type.
const resourceState = object.resourceState; // The resourceState is 'exists' or 'not_exists' (for file/folder deletions).
const metageneration = object.metageneration; // Number of times metadata has been generat
const fileName = filePath.split('/').pop();
console.log('jrodi   ', filePath);


  // Exit if this is triggered on a file that is not an image.
  if (!contentType.startsWith('image/')) {
    console.log('This is not an image.');
    return;
  }

  // Exit if this is a move or deletion event.
  if (resourceState === 'not_exists') {
    console.log('This is a deletion event.');
    return;
  }

  // Download file from bucket.
  const bucket = gcs.bucket(fileBucket);
  const tempFilePath = `/tmp/${fileName}`;
  return bucket.file(filePath).download({
    destination: tempFilePath
  }).then(() => {
    console.log('Image downloaded locally to', tempFilePath);
  // Generate a thumbnail using ImageMagick.
  return spawn('convert', [tempFilePath, '-thumbnail', '200x200>', tempFilePath]).then(() => {
    console.log('Thumbnail created at', tempFilePath);
    // We add a 'thumb_' prefix to thumbnails file name. That's where we'll upload the thumbnail.
    // Uploading the thumbnail.
    return bucket.upload(tempFilePath, {
      destination: filePath+JPEG_EXTENSION
    }).then(() =>{
      return bucket.file(filePath).delete();
    });
  });
});
});







//// Push Notifications

  exports.sendPush = functions.database.ref('/posts/{postId}/comments/{commentId}').onWrite(event => {

  console.log('Push notification event triggered');
  let projectStateChanged = false;
  let projectCreated = false;
  let projectData = event.data.val(); 

  if (!event.data.previous.exists()) {
    projectCreated = true;
  }

  if (!projectCreated && event.data.changed()) {
  projectStateChanged = true;
  }

  let msg = 'A comment was modified';

  if (projectCreated) {
    msg = `A new comment was added to the platform by: ${projectData.author}`;
  }

  return loadUsers().then(posts => {
        let tokens = [];
        for (let author of posts) {
            tokens.push(posts.authorId);
        }
        let payload = {
            notification: {
                title: 'New Post Added',
                body: msg,
                sound: 'default',
                badge: '1'
            }     
        };
        return admin.messaging().sendToDevice(tokens, payload);
    });

});

  function loadUsers() {
  let dbRef = admin.database().ref('/posts');
  let defer = new Promise((resolve, reject) => {
    dbRef.once('value', (snap) => {
      let data = snap.val();
      let users = [];
      for (var property in data) {
        users.push(data[property]);
      }
      resolve(users);
    }, (err) => {
      reject(err);
    });
  });
  return defer;
}

