package serendio.dataset.process;

import java.util.HashSet;

public class EmailDoc 
{
	private String Name;
	private String Message_ID;
	private String From;
	private String ReplyMessage_ID;
	private HashSet<String> To;
	private HashSet<String> Cc;
	private HashSet<String> Bcc;
	private HashSet<String> Response;
	private String Subject;
	private String Content;
	private String Date;
	private String Attachment;
	private long EpochTimeStamp;
	public long getEpochTimeStamp() {
		return EpochTimeStamp;
	}

	public void setEpochTimeStamp(long l) {
		EpochTimeStamp = l;
	}

	public EmailDoc()
	{
		initEmailDoc();
	}
	
	public EmailDoc(String Name)
	{
		initEmailDoc();
		setName(Name);
	}

	private void initEmailDoc() 
	{
		setName(null);
		setMessage_ID(null);
		setFrom(null);
		setTo(null);
		setCc(null);
		setBcc(null);
		setReplyTo(null);
		setSubject(null);
		setContent(null);
		setDate(null);
	}

	public void printMailObject()
	{
		System.out.println("Name: "+getName());
		System.out.println("Message_Id: "+getMessage_ID());
		System.out.println("From: "+getFrom());
		System.out.println("To: "+getTo());
		System.out.println("Cc: "+getCc());
		System.out.println("Bcc: "+getBcc());
		System.out.println("Response: "+getReplyTo());
		System.out.println("Date: "+getDate());
		System.out.println("Epoch Time: "+getEpochTimeStamp());
		System.out.println("Subject: "+getSubject());
		System.out.println("Content: "+getContent());
		System.out.println("Attachment: "+getContent());
	}
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getMessage_ID() {
		return Message_ID;
	}

	public void setMessage_ID(String message_ID) {
		Message_ID = message_ID;
	}

	public String getFrom() {
		return From;
	}

	public void setFrom(String from) {
		From = from;
	}

	public HashSet<String> getTo() {
		return To;
	}

	public void setTo(HashSet<String> to) {
		To = to;
	}

	public HashSet<String> getCc() {
		return Cc;
	}

	public void setCc(HashSet<String> cc) {
		Cc = cc;
	}

	public HashSet<String> getBcc() {
		return Bcc;
	}

	public void setBcc(HashSet<String> bcc) {
		Bcc = bcc;
	}
	
	public HashSet<String> getReplyTo() {
		return Response;
	}

	public void setReplyTo(HashSet<String> response) {
		Response = response;
	}

	public String getReplyMessage_ID() {
		return ReplyMessage_ID;
	}

	public void setReplyMessage_ID(String replyMessage_ID) {
		ReplyMessage_ID = replyMessage_ID;
	}
	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getAttachment() {
		return Attachment;
	}

	public void setAttachment(String attachment) {
		Attachment = attachment;
	}
	
}
