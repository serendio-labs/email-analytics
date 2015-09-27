package com.serendio.dataset.domain;

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
	private String Subject;
	private String Content;
	private String Date;
	private String Attachment;
	private long EpochTimeStamp;
	private String Sentiment;
	private String Topic;

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
		setReplyMessage_ID(null);
		setFrom(null);
		setTo(null);
		setCc(null);
		setBcc(null);
		setSubject(null);
		setContent("");
		setDate(null);
		setSentiment("");
		setTopic("");
	}

	public void printMailObject()
	{
		System.out.println("Name: "+getName());
		System.out.println("Message_Id: "+getMessage_ID());
		System.out.println("From: "+getFrom());
		System.out.println("In-Reply-To:"+getReplyMessage_ID());
		System.out.println("To: "+getTo());
		System.out.println("Cc: "+getCc());
		System.out.println("Bcc: "+getBcc());
		System.out.println("Date: "+getDate());
		System.out.println("Epoch Time: "+getEpochTimeStamp());
		System.out.println("Subject: "+getSubject());
		System.out.println("Content: "+getContent());
		System.out.println("Attachment: "+getContent());
		System.out.println("Topic: "+getTopic());
		System.out.println("Sentiment: "+getSentiment());
	}
	public void printTopicSentiment(){
		System.out.println("Topic: "+getTopic());
		System.out.println("Sentiment: "+getSentiment());
	}
	public String getName() {
		return this.Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public String getMessage_ID() {
		return this.Message_ID;
	}

	public void setMessage_ID(String message_ID) {
		this.Message_ID = message_ID;
	}

	public String getFrom() {
		return this.From;
	}

	public void setFrom(String from) {
		this.From = from;
	}

	public HashSet<String> getTo() {
		return this.To;
	}

	public void setTo(HashSet<String> to) {
		this.To = to;
	}

	public HashSet<String> getCc() {
		return this.Cc;
	}

	public void setCc(HashSet<String> cc) {
		this.Cc = cc;
	}

	public HashSet<String> getBcc() {
		return this.Bcc;
	}

	public void setBcc(HashSet<String> bcc) {
		this.Bcc = bcc;
	}

	public String getReplyMessage_ID() {
		return this.ReplyMessage_ID;
	}

	public void setReplyMessage_ID(String replyMessage_ID) {
		this.ReplyMessage_ID = replyMessage_ID;
	}
	public String getSubject() {
		return this.Subject;
	}

	public void setSubject(String subject) {
		this.Subject = subject;
	}

	public String getContent() {
		return this.Content;
	}

	public void setContent(String content) {
		this.Content = content;
	}

	public String getDate() {
		return this.Date;
	}

	public void setDate(String date) {
		this.Date = date;
	}

	public String getAttachment() {
		return this.Attachment;
	}

	public void setAttachment(String attachment) {
		this.Attachment = attachment;
	}
	public long getEpochTimeStamp() {
		return this.EpochTimeStamp;
	}

	public void setEpochTimeStamp(long l) {
		this.EpochTimeStamp = l;
	}

	public String getSentiment() {
		return Sentiment;
	}

	public void setSentiment(String sentiment) {
		Sentiment = sentiment;
	}

	public String getTopic() {
		return Topic;
	}

	public void setTopic(String topic) {
		Topic = topic;
	}


}
