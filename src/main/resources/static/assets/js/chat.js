'use strict';


const chatPage = document.querySelector('#chat-page');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const connectingElement = document.querySelector('.connecting');
const chatArea = document.querySelector('#chat-messages');

let stompClient = null;
let selectedUserId = null;


var recipientId = localStorage.getItem('recipientId');
var sender = document.querySelector('#senderId');
//open connect web socket
function connect(event) {

        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
        event.preventDefault();
}


function onConnected() {
    stompClient.subscribe(`/user/${sender}/queue/messages`, onMessageReceived);
    stompClient.subscribe(`/user/public`, onMessageReceived);

}

// async function findAndDisplayConnectedUsers() {
//     const connectedUsersResponse = await fetch('/users');
//     let connectedUsers = await connectedUsersResponse.json();
//     connectedUsers = connectedUsers.filter(user => user.nickName !== nickname);
//     const connectedUsersList = document.getElementById('connectedUsers');
//     connectedUsersList.innerHTML = '';
//
//     connectedUsers.forEach(user => {
//         appendUserElement(user, connectedUsersList);
//         if (connectedUsers.indexOf(user) < connectedUsers.length - 1) {
//             const separator = document.createElement('li');
//             separator.classList.add('separator');
//             connectedUsersList.appendChild(separator);
//         }
//     });
// }




function displayMessage(senderId, content) {
    const messageContainer = document.createElement('div');
    messageContainer.classList.add('message');
    if (senderId === sender) {
        messageContainer.classList.add('sender');
    } else {
        messageContainer.classList.add('receiver');
    }
    const message = document.createElement('p');
    message.textContent = content;
    messageContainer.appendChild(message);
    chatArea.appendChild(messageContainer);
}

async function fetchAndDisplayUserChat() {
    const userChatResponse = await fetch(`/messages/${sender}/${recipientId}`);
    const userChat = await userChatResponse.json();
    chatArea.innerHTML = '';
    userChat.forEach(chat => {
        displayMessage(chat.sender, chat.content);
    });
    chatArea.scrollTop = chatArea.scrollHeight;
}


function onError() {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


// function sendMessage(event) {
//     const messageContent = messageInput.value.trim();
//     if (messageContent && stompClient) {
//         const chatMessage = {
//             senderId: sender,
//             recipientId: recipientId,
//             content: messageInput.value.trim(),
//
//             timestamp: new Date()
//         };
//         stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
//         displayMessage(sender, messageInput.value.trim());
//         messageInput.value = '';
//     }
//     chatArea.scrollTop = chatArea.scrollHeight;
//     event.preventDefault();
// }

function sendMessage(event) {
    event.preventDefault();
    // Lấy giá trị của input
    const messageContent = messageInput.value.trim();
    const chatMessage = {
        senderId: Number(sender.value.trim()),
        recipientId: Number(recipientId),
        content: messageContent,
        timestamp: new Date()
    };

    stompClient.send("/app/chat", {}, chatMessage);
    displayMessage(sender, messageInput.value.trim());
    messageInput.value = '';
}


async function onMessageReceived(payload) {
    console.log('Message received', payload);
    const message = JSON.parse(payload.body);
    if (recipientId && recipientId === message.senderId) {
        displayMessage(message.senderId, message.content);
        chatArea.scrollTop = chatArea.scrollHeight;
    }

    if (recipientId) {
        document.querySelector(`#${recipientId}`).classList.add('active');
    } else {
        messageForm.classList.add('hidden');
    }

    const notifiedUser = document.querySelector(`#${message.senderId}`);
    if (notifiedUser && !notifiedUser.classList.contains('active')) {
        const nbrMsg = notifiedUser.querySelector('.nbr-msg');
        nbrMsg.classList.remove('hidden');
        nbrMsg.textContent = '';
    }
}

messageForm.addEventListener('submit', sendMessage, true);
console.log(messageForm);