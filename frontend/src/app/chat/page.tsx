'use client'

import { notFound } from "next/navigation";
import { useEffect, useState } from "react";
import { io } from "socket.io-client";
import { Client } from "@stomp/stompjs";


interface Chat {
    id: number
    text: string
    pages: number[]
}


// async function getChat(question:string) {

//     const res = await fetch(`http://localhost:8080/api/v1/chat-completion/question/${question}`, {
//         cache: 'no-cache',    
//     })
//     const chat: Chat = await res.json();

//     if (!chat) notFound();

//     return chat;    
// }

// const socket = io("http://localhost:8080/websocket");

export default function Chat() {

  // const [chats, setChats] = useState<Record<string, Chat>>({})
  // const [message, setMessage] = useState('');


  // useEffect(() => {

  //   socket.on("connect", () => {
  //     console.log("Connected to WebSocket");
  //   });

  //   socket.on("/topic/questions", (data: Chat) => {
  //     console.log("subscribed")
  //     setChats(prevChats => ({
  //       ...prevChats,
  //       [data.id]: {
  //         ...data,
  //         timestamp: Date.now()
  //       }
  //     }));
  //   });


  //   return () => {
  //     socket.disconnect()
  //   }


  // }, []);

  // const sendMessage = () => {
  //   socket.emit('/app/question', "what is autoconfiguration");
  //   console.log("sent")
  //   setMessage('');
  // }

  const [message, setMessage] = useState("");
  const [question, setQuestion] = useState("");
  const [stompClient, setStompClient] = useState<Client | null>(null);

  useEffect(() => {
      const client = new Client({
          brokerURL: "http://localhost:8080/websocket", // Connect to Spring Boot WebSocket
          reconnectDelay: 5000, // Auto-reconnect after 5 seconds if disconnected
          onConnect: () => {
              console.log("Connected to WebSocket");
              // Subscribe to the topic
              client.subscribe("/topic/questions", (msg) => {
                  setMessage(msg.body);
              });
          },
          onDisconnect: () => {
              console.log("Disconnected from WebSocket");
          },
      });
      client.activate(); // Connect
      setStompClient(client);
      return () => {
          client.deactivate(); // Clean up on unmount
      };
  }, []);


  const sendMessage = () => {
      if (stompClient && stompClient.connected) {
          stompClient.publish({
              destination: "/app/question",
              body: question,
          });
          setQuestion("");
      }
  };


  return (
    <>
    chat page
    <div>
      {/* {Object.entries(chats).map(([id, chat]) => (
        <div
        key={id}
        className="flex">
          {chat.text}
        </div>
      ))} */}
      <p>{message}</p>


      <input
            type="text"
            value={question}
            onChange={(e) => setQuestion(e.target.value)}
      />
      <button onClick={sendMessage}>Send</button>
    </div>
    
    
    </>
  );
}
