package com.serendio.Utils;
import java.io.File;
import java.util.HashSet;


public class Utils 
{
	public static String[] EmployeeList=new String[1000];
	public static int EmployeeCount = 0;
	
	public static int getEmployeeCount() {
		return EmployeeCount;
	}

	public static void setEmployeeCount(int count) 
	{
		EmployeeCount = count;
	}

	public static String[] getEmployeeList() {
		return EmployeeList;
	}
	
	public static void setEmployeeList(String Path)
	{
	//	System.out.println(Path);
		int index = 0;
		File folder = new File(Path);
		File[] listOfFile = folder.listFiles();
		for(File file:listOfFile)
			if(file.isDirectory())
			{
			//	System.out.println(file.getName());
				EmployeeList[index] = file.getName();
				index++;
			}
		setEmployeeCount(index);
	}
	
	public static HashSet<String> getFileList(String Path) throws Exception
	{
		
		File folder = new File(Path);
		
		File[] listOfFile = folder.listFiles();
		HashSet<String> fileSet = new HashSet<String>();
		fileSet.clear();
		//if(listOfFile.length > 0)
		for(File file:listOfFile)
		{
			if(file.isFile())
			{
				fileSet.add(file.getName());
			}
		}
		return fileSet;
	}
	
	public static void printEmployeeList()
	{
		for(int i = 0; i < getEmployeeCount(); i++)
			System.out.println(EmployeeList[i]);
		System.out.println("Total:" + getEmployeeCount());
	}
}
