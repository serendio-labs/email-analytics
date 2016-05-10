package com.serendio.dataset.mail.mbox;

import org.apache.james.mime4j.MimeException;
import com.serendio.dataset.domain.EmailDoc;
import com.serendio.graphdb.neo4jServer.EmailDocToNeo4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class MboxToNeo4j {

	private final static CharsetEncoder ENCODER = Charset.forName("UTF-8")
			.newEncoder();

	public MboxToNeo4j() {
		System.out.println("MBOX MAIL INGEST STARTED:");
		System.out.println("=========================");
	}

	public void ingestMbox(String inputPath) throws FileNotFoundException,
			IOException, MimeException {
		File mbox = new File(inputPath);
		MessageToEmailDoc emailObjectConverter = new MessageToEmailDoc();
		EmailDoc emailObject = new EmailDoc();
		int mailCounter = 0;
		EmailDocToNeo4j neo4jInterface = new EmailDocToNeo4j();
		for (CharBufferWrapper message : MboxIterator.fromFile(mbox)
				.charset(ENCODER.charset()).build()) {
			mailCounter++;
			System.out.println("Processing Email: " + mailCounter);
			emailObject = emailObjectConverter.messageToemailDoc(message
					.asInputStream(ENCODER.charset()));
			neo4jInterface.pushToNeo4j(emailObject);
		}
	}
}
