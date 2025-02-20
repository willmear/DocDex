'use client'

import { error } from "console";
import { notFound, redirect } from "next/navigation";
import { useState } from "react";


interface Completion {
    id: number
    text: string
    pages: number[]
}

interface Conversation {
  id: number
}


export default function Chat() {

  let chatLog: Completion[] = [];


  const [question, setQuestion] = useState("");


  async function createConversation() {


    const data = await fetch("http://localhost:8080/api/v1/conversation/create", {
      method: "POST",
      body: JSON.stringify({
        text: question,
        conversationId: null
      }),
      headers: {
        "Content-Type": "application/json; charset=UTF-8"
      },
    })

    const conversation: Conversation = await data.json();
    console.log(conversation);
    console.log(conversation.id);

    if (data.ok) {
      redirect(`/c/${conversation.id}`)
    } else {
      console.log("error posting conversation");
    }

  
  };

    

  


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

          <div className="flex w-3/4">
          Text: 
          </div>

          

        </div>


        <div className="h-1/4 flex justify-center">

          <div className="flex flex-col justify-end w-3/4">

          <textarea className="rounded-3xl shadow-lg h-full max-h-full text-left p-5 bg-[#323232] opacity-85"
                value={question}
                onChange={(e) => setQuestion(e.target.value)}
          />
          <button className="h-1/4" onClick={createConversation}>Send</button>
          
          </div>

          
        </div>

        
      </div>
    
      

      


      
      
    
    </div>
  );
}
