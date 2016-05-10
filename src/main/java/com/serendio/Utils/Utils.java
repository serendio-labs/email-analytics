package com.serendio.Utils;

import com.google.gson.Gson;
import org.apache.james.mime4j.dom.address.AddressList;
import com.serendio.dataset.domain.EmailDoc;

import javax.mail.Address;
import java.util.HashSet;
import java.util.Iterator;

public class Utils {

	public static String ToJson(EmailDoc emailObject) {
		Gson gson = new Gson();
		String json = gson.toJson(emailObject);
		return json;
	}

	public static HashSet<String> addressArrayToHashset(Address[] address) {
		HashSet<String> set = new HashSet<String>();
		set.clear();
		if (!(address == null))
			for (Address add : address) {
				set.add(add.toString());
			}
		return set;
	}

	public static HashSet<String> addressListToHashset(AddressList address) {
		HashSet<String> set = new HashSet<String>();
		set.clear();
		Iterator<org.apache.james.mime4j.dom.address.Address> adr = address
				.iterator();
		while (adr.hasNext()) {
			set.add(adr.next().toString());
		}
		return set;
	}
}
