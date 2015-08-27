package com.serendio.dataset.mail.pst;

import com.pff.PSTException;
import com.pff.PSTFile;
import com.pff.PSTFolder;
import com.pff.PSTMessage;
import org.apache.james.mime4j.MimeException;
import com.serendio.dataset.domain.EmailDoc;
import com.serendio.graphdb.neo4jEmbd.EmailDocToNeo4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Vector;

public class PstToNeo4j {

	/*
	 * public static void main(String[] args) { String filename;
	 * filename="/Users/vartikatewari/Documents/Outlook Files/backup1.pst";
	 * ConstantVariables
	 * .setDbPath("C:/Users/vartikatewari/Documents/Neo4j/pst.graphdb"); new
	 * readpst(filename);
	 * 
	 * }
	 */
	public PstToNeo4j() {
		System.out.println("PST MAIL INGEST STARTED:");
		System.out.println("=========================");
	}

	public void ingestPst(String inputPath) throws FileNotFoundException,
			IOException, MimeException {
		try {
			PSTFile pstFile = new PSTFile(inputPath);
			processFolder(pstFile.getRootFolder());
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	public void processFolder(PSTFolder folder) throws PSTException,
			java.io.IOException {
		// go through the folders...

		
		if (folder.hasSubfolders()) 
		{
			Vector<PSTFolder> childFolders = folder.getSubFolders();
			for (PSTFolder childFolder : childFolders) 
			{
				processFolder(childFolder);
			}
		}

		// and now the emails for this folder
		if (folder.getContentCount() > 0) {

			PSTMessage email = (PSTMessage) folder.getNextChild();

			while (email != null) {
				EmailDoc emailObject = new EmailDoc();
				// emailObject.printMailObject();

				HashSet<String> To = new HashSet<>();
				To.add(email.getReceivedByAddress());

				HashSet<String> Cc = new HashSet<>();
				if (email.getOriginalDisplayCc().contains("@"))
					Cc.add(email.getOriginalDisplayCc());

				HashSet<String> Bcc = new HashSet<>();
				if (email.getOriginalDisplayBcc().contains("@"))
					Bcc.add(email.getOriginalDisplayBcc());

				emailObject.setName(email.getSenderName());
				emailObject.setMessage_ID(email.getInternetMessageId());
				emailObject.setFrom(email.getSenderEmailAddress());
				System.out.println("TO:" + To.size());
				emailObject.setTo(To);
				if (Cc.size() > 0)
					emailObject.setCc(Cc);
				if (Bcc.size() > 0)
					emailObject.setBcc(Bcc);
				emailObject.setDate(email.getCreationTime().toString());
				emailObject.setSubject(email.getSubject());
				emailObject.setContent(email.getBody());
				emailObject.setReplyMessage_ID(email.getInReplyToId());

				emailObject.printMailObject();
				EmailDocToNeo4j interfaceNeo4j = new EmailDocToNeo4j();
				interfaceNeo4j.pushToNeo4j(emailObject);
				interfaceNeo4j.closedb();
				email = (PSTMessage) folder.getNextChild();
			}

		}
	}

}
