package com.serendio.ml.diskoveror.restApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class Services {
	public HttpResponse doPost(String url, String query, Map<String,String> parameters) throws ClientProtocolException, IOException
	{
		HttpClient client = null;
		HttpPost httpPost = null;
		if(query!=null)
		{
			url = url +'?'+ query;
		}
		client = HttpClientBuilder.create().build();
		httpPost = new HttpPost(url);
		if(parameters!=null)
		{
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : parameters.entrySet())
			{
				urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
		}
		return client.execute(httpPost);
	}
	public String getResponseBody(HttpResponse response) throws ParseException, IOException
	{
		return EntityUtils.toString(response.getEntity());
	}
}
