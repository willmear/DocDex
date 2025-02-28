'use client'

import { notFound, redirect, useParams } from "next/navigation";
import { useEffect, useState } from "react";
import { Client } from "@stomp/stompjs";
import { error } from "console";
import React from "react";


interface Completion {
    id: number
    text: string
    sentAt: Date
    senderType: senderType
    pages: number[]
}
interface Conversation {
  id: number
  messages: Completion[]
  name: string
}

enum senderType {
  USER,
  SYSTEM
}


export default function Chat() {

  const params = useParams()


  const [message, setMessage] = useState("");
  const [question, setQuestion] = useState("");
  const [log, setLog] = useState<Completion[]>([]);
  const [stompClient, setStompClient] = useState<Client | null>(null);

  
  useEffect(() => {

    const fetchConversation = async () => {
      try {
        const res = await fetch(`http://localhost:8080/api/v1/conversation/${params.conversationId}`);
        if (!res.ok) throw new Error("Conversation not found");

        const conversationData: Conversation = await res.json();
        setLog(conversationData.messages);
        console.log(conversationData)
        console.log(log)
      } catch (error) {
        console.error("Error fetching conversation:", error);
      }
    };

    if (params.conversationId) fetchConversation();
  }, [params.conversationId]);

  useEffect(() => {
      const client = new Client({
          brokerURL: "http://localhost:8080/websocket", // Connect to Spring Boot WebSocket
          reconnectDelay: 5000, // Auto-reconnect after 5 seconds if disconnected
          onConnect: () => {
              console.log("Connected to WebSocket");
              // Subscribe to the topic
              client.subscribe("/topic/questions", (msg) => {
                const parsedData: Completion = JSON.parse(msg.body);
                console.log(parsedData);
                // setMessage(parsedData.text);
                setLog((prevMessages) =>   [...prevMessages, parsedData]);
                  

                  // setMessage(msg.body);
                  // setLog((prevMessages) =>   [...prevMessages, msg.body]);
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
              body: JSON.stringify({
                "text":question,
                "conversationId": params.conversationId
              }),
          });
          // setLog((prevMessages) =>   [...prevMessages, question]);
          setQuestion("");
      }
  };
  interface Completion {
    id: number
    text: string
    sentAt: Date
    senderType: senderType
    pages: number[]
}
  


  return (
    <div className="flex min-h-screen w-screen text-gray-100">


      <div className="min-h-full w-1/5 min-w-1/5 left-0 bg-[#171717] text-gray-100 shadow-sm">

        Conversations
      </div>



      <div className="flex-1 min-h-full flex flex-col bg-[#212121] opacity-90">


        <div className="w-full flex flex-col h-1/5 top-0 bg-[#323232] py-2 px-4 shadow-2xs">

          <span className="text-xl font-medium">
            Documentation
          </span>

          <div className="flex flex-1 py-2 gap-6">
            <div className="border">Hello</div>
            <div className="border">Hello</div>
            <div className="border">Hello</div>
            
          </div>

        </div>

        <div className="flex-1 flex justify-center text-xl">

          <div className="flex flex-col w-3/4">
          {log.map((msg) => (
              <div key={msg.id} className="p-2 border-b">
                <strong>{msg.senderType === senderType.USER ? "You" : "System"}:</strong> {msg.text}
              </div>
            ))}
          
          </div>

          

        </div>


        <div className="h-1/4 flex justify-center">

          <div className="flex flex-col justify-end w-3/4">

          <textarea className="rounded-3xl shadow-lg h-full max-h-full text-left p-5 bg-[#323232] opacity-85"
                value={question}
                onChange={(e) => setQuestion(e.target.value)}
          />
          <button className="h-1/4" onClick={sendMessage}>Send</button>
          
          </div>

          
        </div>

        
      </div>
    
      

      


      
      
    
    </div>
  );
}
