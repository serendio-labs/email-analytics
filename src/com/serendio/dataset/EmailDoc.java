package com.serendio.dataset;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import com.serendio.graphdb.neo4j.DBConnection;

public class EmailDoc {

	public String Source = null;
	public HashSet<String> Desti = new HashSet<String>();
	
	
	
	public void processDoc(String Path) throws IOException
	{
		BufferedReader buffer = new BufferedReader(new FileReader(Path));
		String line;
		while(!(line=buffer.readLine()).startsWith("From"));
		//System.out.println(line);
		Source = line;
		Source = Source.replace("From:","").trim();
		
		while(!(line=buffer.readLine()).startsWith("Subject"))
		{
			String Emails[]=line.split(",");
			for(String s:Emails)
			{		
				if(s.contains("@"))
				{ s = s.replace("To:","").trim();
					Desti.add(s);
				}
			}
		}
		System.out.println(Source);
		DBConnection.createNode(Source);
		System.out.println(Desti);
	}
}
