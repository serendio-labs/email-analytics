package com.serendio.Utils;
import java.io.IOException;

import com.serendio.dataset.DataProcess;
import com.serendio.graphdb.neo4j.DBConnection;


public class AppRunner {

	private static String EmailCorpPath=null;

	public static String getEmailCorpPath() {
		return EmailCorpPath;
	}

	public static void setEmailCorpPath(String emailCorpPath) {
		EmailCorpPath = emailCorpPath;
	}

	public static void main(String args[]) throws Exception
	{
		setEmailCorpPath("/home/nishant/Serendio/Enron Email/enron_mail_20110402/dump");		
	//	Utils.setEmployeeList(getEmailCorpPath());
	//	Utils.printEmployeeList();
		DataProcess dp = new DataProcess();
		DBConnection.createConnection();
		dp.ProcessData();
		//DBConnection.init();
	}
}
