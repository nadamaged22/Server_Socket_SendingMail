# OVERVIEW

This repository contains the source code for a simplified email client-server application implemented in Java. The application allows multiple clients to communicate with a server simultaneously, exchanging messages between two users, "Dave" and "Karen". Each user has a mailbox on the server that can hold up to 10 messages.

# Files

- EmailClient.java: This file contains the source code for the email client application. It allows users to send messages to the other user or read their own messages from the server.
- EmailServer.java: This file contains the source code for the email server application. It manages the communication between clients, handling message sending and reading, and maintaining message counts in user mailboxes.

# Client-Server Communication 

- When a client wants to send a message, it sends the user's name, the word 'send', and the message itself to the server.
- When a client requests to read messages, it sends the user's name and the word 'read' to the server.
The server processes incoming messages, adds them to the appropriate user's mailbox if there is room, and ignores them if the mailbox is full.
- When a read request is received, the server sends an integer indicating the number of messages available followed by the messages themselves. After sending messages, it reduces the message count to zero.

# Notes

- This application is a simplified version of an email client-server system and may not provide all functionalities of a real-world email system.
- Ensure proper error handling and exception management for robustness in a production environment.
