package serendio.Utils;

import java.util.HashSet;
import java.util.Iterator;

import javax.mail.Address;

import org.apache.james.mime4j.dom.address.AddressList;

import com.google.gson.Gson;

import serendio.dataset.domain.EmailDoc;


public class Utils {
	
	public static String ToJson(EmailDoc emailObject)
	{
		Gson gson = new Gson();
		String json = gson.toJson(emailObject);
		//System.out.println(json);
		return json;
	}
	
	@SuppressWarnings("null")
	public static HashSet<String> addressArrayToHashset(Address[] address)
	{
		HashSet<String> set = new HashSet<String>();
		set.clear();
		if(!(address == null))
		for(Address add:address)
		{
			//System.out.println(add);
			//if(!add.equals(null))
			set.add(add.toString());
		}
		return set;
	}
	public static HashSet<String> addressListToHashset(AddressList address)
	{
		HashSet<String> set = new HashSet<String>();
		set.clear();
	//	Address [] addressArray = address.toArray(new Address[address.size()]);
		
		Iterator<org.apache.james.mime4j.dom.address.Address> adr = address.iterator();
		while(adr.hasNext())
		{
			//System.out.println(adr.next().toString());
			set.add(adr.next().toString());
		}
		
		/*
		if(!(address == null))
		for(Address add: addressArray)
		{
			//System.out.println(add);
			//if(!add.equals(null))
			set.add(add.toString());
		}*/
		return set;
	}
}
