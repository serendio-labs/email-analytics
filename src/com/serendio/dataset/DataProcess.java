package com.serendio.dataset;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import com.serendio.Utils.AppRunner;
import com.serendio.Utils.Utils;

public class DataProcess {

	public void ProcessData() throws Exception
	{
		String[] empFolders = Utils.getEmployeeList();
		int empCount = Utils.getEmployeeCount();
		for(int i = 0; i < empCount; i++)
		{
			System.out.println(empFolders[i]);
			System.out.println("============");
			File path =new File(AppRunner.getEmailCorpPath()+"/"+empFolders[i]+"/sent");
			if(path.exists() && path.isDirectory())
			{
				HashSet<String> fileSet = Utils.getFileList(AppRunner.getEmailCorpPath()+"/"+empFolders[i]+"/sent");
				for(String file:fileSet)
				{
					System.out.println(file+"\n=======");
					EmailDoc doc =new EmailDoc();
					doc.processDoc(AppRunner.getEmailCorpPath()+"/"+empFolders[i]+"/sent/"+file);
				}
			}
		}
		
	}
}
