'use strict';


const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const chatArea = document.querySelector('#chat-messages');

let sender = document.querySelector('#senderId').value;

let selectedUser = localStorage.getItem('recipientId');

let stompClient = null;



function connectToChat() {

    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}
console.log(sender);
console.log(selectedUser);
function onConnected() {
    // Register event listener for receiving messages
    stompClient.subscribe(`/user/${sender}/queue/messages`, onMessageReceived);

}


function sendMessage(event) {
    const messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        const chatMessage = {
            sender: sender,
            recipient: selectedUser,
            content: messageContent,
            sentAt: new Date()
        };
        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        displayMessage(sender, messageContent);
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    const message = JSON.parse(payload.body);
    if (selectedUser && selectedUser === message.sender) {
        displayMessage(message.sender, message.content); // Display received message in chat area
    }

    // Your code to handle notification of new messages and update UI accordingly
}

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
    chatArea.scrollTop = chatArea.scrollHeight;
}

function onError() {
    console.log("e")
}



// Event listeners
messageForm.addEventListener('submit', sendMessage, true);
