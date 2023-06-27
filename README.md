# AndroidTeamChat
A friendly chat whatsup like project in android studio 

## Table of Contents
- [Description](#description)
- [Screens](#screens)
  - [Login](#login)
  - [Registration](#registration)
  - [Chat](#chat)
  - [Contact List](#contact-list)
  - [Settings](#settings)
  - [Add Contact](#add-contact)
- [Technologies Used](#technologies-used)
- [Remarks](#remarks)
- [Installation and Execution](#installation-and-execution)
- [Authors](#authors) 

## Description
AndroidTeamChat is a feature-rich chat application developed in Android Studio that closely resembles the functionality of WhatsApp. This project utilizes various technologies to provide seamless communication and a user-friendly experience. It incorporates Room for local storage, Firebase for notifications, and a Node.js server for backend support.
The project leverages Room, a local storage solution, to store chat-related data efficiently on the user's device. Firebase is employed to handle real-time notifications, keeping users informed about new messages even when the application is in the background. The Node.js server acts as the backend, enabling seamless communication between users and facilitating a smooth chat experience.

AndroidTeamChat provides a visually appealing and intuitive user interface, ensuring a delightful chatting experience. With its robust feature set and seamless integration of essential technologies, this project serves as an excellent foundation for building a WhatsApp-like chat application in Android Studio.

## screens

### login
The login screen is a simple yet crucial component, designed to allow registered users to securely log in with their credentials. This user-friendly screen provides immediate feedback to the user, indicating whether the entered credentials are correct or incorrect. Additionally, it offers convenient navigation options for accessing the settings and registration screens.

<img width="250" height="420" alt="chat Screen" src="https://github.com/IditMedizada/AndroidTeamChat/assets/110912180/3c6d18db-23ed-4d74-a66f-bcaefd24f562">

### registration
The registration screen is designed to facilitate the user registration process effectively. It offers a user-friendly interface with fields for entering essential information such as username, password, confirm password, display name, and picture. The screen provides immediate feedback to the user, ensuring the correctness of the entered credentials and guiding them through the registration process.

<img width="250" height="420" alt="chat Screen" src="https://github.com/IditMedizada/AndroidTeamChat/assets/110912180/5933b243-bbd9-44b1-91e0-db4b15923768">

### Chat
The chat screen serves as a platform for users to engage in real-time conversations with their contacts. It offers a straightforward and user-friendly interface that enables seamless communication through sending and receiving messages. Additionally, the screen provides convenient options for deleting the contact and navigating back to the contact list.

<img width="250" height="420" alt="chat Screen" src="https://github.com/IditMedizada/AndroidTeamChat/assets/110912180/398b10fd-cafe-42e7-9f1a-e08d2a7c7c01">

### Contact List
The contact list screen serves as a central hub for users to manage and interact with their contacts. It provides an organized and user-friendly interface that displays all the user's contacts, offers options to initiate chats, add new contacts, and search for specific contacts. Additionally, the screen showcases the last message exchanged and the corresponding time between the user and each contact, providing valuable context and quick access to recent conversations.

<img width="250" height="420" alt="chat Screen" src="https://github.com/IditMedizada/AndroidTeamChat/assets/110912180/a1e84fd7-a79b-4170-ac8e-b8102ae69a5c">

### Settings
The settings screen provides users with customization options and essential functionalities to personalize their experience and manage their account. It offers three distinct options, including the ability to switch between dark mode and light mode, change the server URL, and a logout button. Additionally, the screen ensures smooth navigation by returning the user to the screen they were previously on when pressing the back button.

<img width="250" height="420" alt="chat Screen" src="https://github.com/IditMedizada/AndroidTeamChat/assets/110912180/7aeea0fb-34b9-4a7e-b708-efb9f31af8ab">

### Add Contact
The addContact screen allows users to add new contacts to their contact list. It provides a simple and intuitive interface where users can enter the details of the contact they wish to add. The screen communicates with the server to verify the existence of the contact before adding them to the user's contact list. Once the contact is successfully added, the screen seamlessly navigates back to the contact list screen.

<img width="250" height="420" alt="chat Screen" src="https://github.com/IditMedizada/AndroidTeamChat/assets/110912180/e261e580-dc97-46a4-91aa-3a2cbfce3fff">

## Technologies Used
The project uses the following technologies:

* Java- in android-studio
* NodeJs- in the server
* Firebase- for notifications
* Room- for local storage
  
## Remarks
* It is not possible to return from the contact list page
* User must enter in the url option a format of http://xxx.xxx.xxx.xxx:5000/api/ or http://xxx.xxx.xxx.xxx:5000
* In the notifications, only the last message contact sent is shown for each member
* By clicking on logout, all the information of messages and contacts are deleted from the room

## Installation and Execution
To clone and run this application, you'll need [Git](https://git-scm.com) installed on your computer.
  
From your command line:
 
```bash
# Clone this repository.
$ git clone https://github.com/IditMedizada/AndroidTeamChat.git

# Go into the repository.
$ cd AndroidTeamChat

# Server side
$ cd NodeJs
$ npm install
$ npm start

# Client side - android
# Open project in android studio
# sync project with Gradle files
# run the app

# Client side - web
# Open the browser- "http://localhost:5000"
```

## Authors
- [Idit Medizada](https://github.com/IditMedizada)
- [Tomer Peisikov](https://github.com/tomerp1812)
