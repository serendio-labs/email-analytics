package serendio.dataset.mail.text;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.mail.Address;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import serendio.dataset.process.emailDoc;

public class EmailDocReader 
{
	private String PathForDoc;
	
	public EmailDocReader()
	{
		setPathForDoc(null);
	}
	public EmailDocReader(String Path)
	{
		setPathForDoc(Path);
	}
	
	@SuppressWarnings("null")
	public HashSet<String> addresslistToHashset(Address[] address)
	{
		HashSet<String> set = new HashSet();
		if(!(address == null))
		for(Address add:address)
		{
			//System.out.println(add);
			//if(!add.equals(null))
			set.add(add.toString());
		}
		return set;
	}
	
	public emailDoc processDoc() throws MessagingException, IOException
	{
		if(getPathForDoc().equals(null))
		{
			System.out.println("Please set the EmailDocPath");
			return null;
		}
		
		File mailFile = new File(getPathForDoc());
		String host = "host.com";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);
		FileInputStream fileStream = new FileInputStream(mailFile);
		MimeMessage email = new MimeMessage(session,fileStream);
		
		emailDoc mailDoc = new emailDoc();
		mailDoc.setName(getNameFromHeader(email));
		mailDoc.setFrom(email.getFrom()[0]+"");
		mailDoc.setSubject(email.getSubject());
		mailDoc.setMessage_ID(email.getMessageID());
		mailDoc.setDate(email.getSentDate().toString());
		mailDoc.setContent(email.getContent().toString().trim());
		mailDoc.setTo(addresslistToHashset(email.getRecipients(Message.RecipientType.TO)));
		mailDoc.setBcc(addresslistToHashset(email.getRecipients(Message.RecipientType.BCC)));		
		mailDoc.setCc(addresslistToHashset(email.getRecipients(Message.RecipientType.CC)));
		
		mailDoc.printMailObject();
		
		return mailDoc;
	}
	
	private String getNameFromHeader(MimeMessage email) throws MessagingException {
		// TODO Auto-generated method stub
		Enumeration headers = email.getAllHeaders();
		while(headers.hasMoreElements())
		{
			Header h = (Header) headers.nextElement();
			if(h.getName().contains("X-From"))
				return h.getValue();
		}
		
		return null;
	}
	public String getPathForDoc() {
		return PathForDoc;
	}

	public void setPathForDoc(String pathForDoc) {
		PathForDoc = pathForDoc;
	}
	
}
