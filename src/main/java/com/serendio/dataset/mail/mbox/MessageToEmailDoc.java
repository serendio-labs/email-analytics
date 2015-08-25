package com.serendio.dataset.mail.mbox;
/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

import org.apache.james.mime4j.MimeException;
import org.apache.james.mime4j.dom.*;
import org.apache.james.mime4j.message.DefaultMessageBuilder;
import com.serendio.Utils.Utils;
import com.serendio.dataset.domain.EmailDoc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * Simple example of how to use Apache Mime4j Mbox Iterator. We split one mbox file file into
 * individual email messages.
 */

public class MessageToEmailDoc 
{

	    public String getTxtPart(Body body) throws IOException {    
	        TextBody tb = (TextBody) body;  
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	        tb.writeTo(baos);  
	        return new String(baos.toByteArray());  
	    }  
	 
	    /**
	     * Parse a message and return a simple {@link String} representation of some important fields.
	     *
	     * @param messageBytes the message as {@link java.io.InputStream}
	     * @return String
	     * @throws IOException
	     * @throws MimeException
	     */
	    
	    public String getNameFromHeader(Message message)
	    {
			String Name = null;	
				if(message.getHeader().getField("From") != null)
				{
					String listName[] = message.getHeader().getField("From").getBody().split("<");
					if(listName[0].length()>1)
						Name = listName[0];
				}
			return Name;
	    }
	    //TODO: Keep consistent pattern of space between operators. like either keep x=5 or x = 5.    
	    public String getInReplyTo(Message message)
	    {
	    	String replyto=null;
	    	
	    	if(message.getHeader().getField("In-Reply-To")!= null)
			{
				System.out.println("RESPONSE EMAIL DETECTED");
				replyto=message.getHeader().getField("In-Reply-To").getBody();
			}
			return replyto;
	    }
	    
	    public EmailDoc messageToemailDoc(InputStream messageBytes) throws MimeException, IOException
	    {
	    
	    	MessageBuilder builder = new DefaultMessageBuilder();
	        Message message = builder.parseMessage(messageBytes);
	        EmailDoc emailObject = new EmailDoc();
	    
	        emailObject.setMessage_ID(message.getMessageId());
	        emailObject.setName(getNameFromHeader(message));
	        emailObject.setFrom(message.getFrom().get(0).getAddress().toString());
	        emailObject.setSubject(message.getSubject());
	        emailObject.setDate(message.getDate().toString());
	        emailObject.setEpochTimeStamp(message.getDate().getTime());
	        emailObject.setReplyMessage_ID(getInReplyTo(message));
	        if(message.getBcc() != null)
	        {
	        	emailObject.setBcc(Utils.addressListToHashset(message.getBcc()));
	        }
	        
	        if(message.getCc() != null)
	        {
	        	emailObject.setCc(Utils.addressListToHashset(message.getCc()));
	        }
	        
	        if(message.getTo() != null)
	        {
	        	emailObject.setTo(Utils.addressListToHashset(message.getTo()));
	        }
	
	      
	        if(message.isMultipart())
	        {
	        	Multipart multipart = (Multipart) message.getBody();
	        	emailObject = parseBodyParts(multipart, emailObject);    	
	        }
	        else
	        {
	        	String text = getTextPart(message);
	        	emailObject.setContent(text);
	        }
	        
	    	return emailObject;
	    }

		
		private EmailDoc parseBodyParts(Multipart multipart, EmailDoc emailObject) throws IOException 
		{
	    	List<Entity> list = multipart.getBodyParts();
	    	for(Entity e:list)
	    	{
	    		String s = e.getMimeType();
	    		if(s.equals("text/plain"))
	    		{
	    			emailObject.setContent(emailObject.getContent() + getTextPart(e));
	    		}
	    		if(s.equals("text/html"))
	    		{
	    			emailObject.setContent(emailObject.getContent() + getTextPart(e));
	    		}
	    		if(e.getDispositionType() != null)
	    		{
	    			 emailObject.setAttachment(emailObject.getAttachment() + e.getFilename());
	    		}
			}
			return emailObject;
		}
		
		private String getTextPart(Entity part) throws IOException 
		{
			TextBody tb = (TextBody) part.getBody();  
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		        tb.writeTo(baos);  
		        return new String(baos.toByteArray()); 
		}
}
