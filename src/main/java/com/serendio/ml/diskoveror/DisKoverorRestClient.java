package com.serendio.ml.diskoveror;

import com.serendio.ml.diskoveror.restApi.Services;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class DisKoverorRestClient {
  public static final String DISKOVEROR_REST_URL = "http://5.9.115.14:9000/api";
  public static enum ML_Action
  {
    Sentiment,Topic;
  }
  public String restCallToDiskoverorTA(DisKoverorRestClient.ML_Action analysis,String text) throws ClientProtocolException, IOException
  {
    Services restService = new Services();
    String query = "analysis=" + analysis.toString() + "&inputtext=" + encodeUrlText(text);
    Map parameters = null;
    String response_text = null;
    HttpResponse response = restService.doPost(DISKOVEROR_REST_URL, query, parameters);
    response_text = restService.getResponseBody(response);
    return jsonResultToString(response_text);
  }
  public String jsonResultToString(String text)
  {
    JsonObject  reader = new JsonParser().parse(text).getAsJsonObject();
    reader = reader.getAsJsonObject("text_information");
    Set<Entry<String,JsonElement>> set = reader.entrySet();
    for(Iterator<Entry<String,JsonElement>> it = set.iterator();it.hasNext();)
    {
      Entry<String,JsonElement> en = it.next();
      text = en.getValue().toString();
    }
    return text;
  }
  public String encodeUrlText(String text) throws UnsupportedEncodingException
  {
    return URLEncoder.encode(text,"UTF-8");
  }
  public String getSentiment(String text) throws ClientProtocolException, IOException
  {
    return restCallToDiskoverorTA(DisKoverorRestClient.ML_Action.Sentiment,text);
  }
  public String getTopics(String text) throws ClientProtocolException, IOException
  {
    return restCallToDiskoverorTA(DisKoverorRestClient.ML_Action.Topic,text);
  }

}
