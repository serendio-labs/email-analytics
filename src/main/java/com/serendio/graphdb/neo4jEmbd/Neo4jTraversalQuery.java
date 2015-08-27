package com.serendio.graphdb.neo4jEmbd;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class Neo4jTraversalQuery {

//	
//	public void mailSentCountperUser()
//	{
//		DBConnection connection = new DBConnection();
//		connection.createEmbDb(ConstantVariables.getDbPath());
//		String query = null;
//		query = "match (n:USER) -[r:Link {Relation: 'FROM'}]->(c) return n.Email, count(*) as Sent_Mail";
//			 connection.getDbService().execute(query);
//                connection.dbShutdown();			        			
//	}
        ////////////////Query Executers////////////////////////
	public String ExecuteQuery(String query)
	{
		String result;
		DBConnection connection = new DBConnection();
		connection.createEmbDb(ConstantVariables.getDbPath());
		result=connection.getDbService().execute(query).resultAsString();
                connection.dbShutdown();
		return(result);
	}
	
	public String ExecuteQuery(String query, Map<String,Object> parameters)
	{
		String result;
		DBConnection connection = new DBConnection();
		connection.createEmbDb(ConstantVariables.getDbPath());
		result=connection.getDbService().execute(query,parameters).resultAsString();
                connection.dbShutdown();
		return(result);
	}
        /////////////////Single Value Result Queries///////////////////////
        public String TotalEmailProcessed()
        {
            String query = "match (mail : Email) return count(mail)";
            String result= ExecuteQuery(query);
            result = removeTableForSingleResult(result);
            return result;
        }
        public String AvgMailSentByEmployees(String CompanyId)
	{
                String sub=".*"+CompanyId+"$";
		String query = "match (n:USER),(n)-[r:Link]->(p:Email) WHERE n.Email =~ {sub} with n.Email as email, count(r) as count return sum(count)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("sub", sub);
		String result = ExecuteQuery(query,parameters);
                result = removeTableForSingleResult(result);
                float resultf = Float.parseFloat(result)/Float.parseFloat(CompanyUsersCount(CompanyId));
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
		return df.format(resultf);
	}
        
        public String AvgMailReceivedByEmployees(String CompanyId)
	{
                String sub=".*"+CompanyId+"$";
		String query = "match (n:USER),()-[r:Link]->(n) where n.Email =~ {sub} AND r.Relation IN ['CC','BCC','TO'] return count(r);";
		Map<String, Object> parameters = new HashMap<>();
                parameters.put("sub", sub);
		String result = ExecuteQuery(query,parameters);
                result = removeTableForSingleResult(result);
                float resultf = Float.parseFloat(result)/Float.parseFloat(CompanyUsersCount(CompanyId));
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
		return df.format(resultf);
	}
        
        public String CompanyUsersCount(String CompanyId)
	{
		String sub=".*"+CompanyId+"$";
		String query="match (user: USER) WHERE user.Email =~ {sub} RETURN count(user)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("sub", sub);
		String result = ExecuteQuery(query,parameters);
                result = removeTableForSingleResult(result);
		return result;
	}
        
        
        
        
      
        ////////////////////////////query for table////////////////////////
        public String[] DisplayCompanyUsers(String CompanyId)
	{
		String sub=".*"+CompanyId+"$";
		String query="match (user: USER) WHERE user.Email =~ {sub} RETURN user.Email,user.Name";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("sub", sub);
		String result = ExecuteQuery(query,parameters);
                ArrayList<String> mylist = removeTableForMultipleResult(result);
                String[] resultout = new String[mylist.size()];
                resultout = mylist.toArray(resultout);
                //System.out.println(mylist.toString());
		return resultout;
	}
       
         public String[] DisplayAllUsers()
	{
            String query = "match (user : USER) return user.Email,user.Name";
	    String result= ExecuteQuery(query);   
	    ArrayList<String> mylist = removeTableForMultipleResult(result);
                String[] resultout = new String[mylist.size()];
                resultout = mylist.toArray(resultout);
                //System.out.println(mylist.toString());
		return resultout;

	}
        
        /////////////////////////////UserWise Report////////////////////// 
         public String UserMailSentCount(String Email)
	{
		String query="MATCH (user:USER)-[:Link {Relation:'FROM'}]->(email) WHERE user.Email = {Email} RETURN count(*)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("Email", Email);
		String result = ExecuteQuery(query,parameters);
		return removeTableForSingleResult(result);
	}

	public String UserMailReceivedCount(String Email)
	{
		String query = "MATCH (email:Email)-[:Link {Relation:'TO'}]->(user) WHERE user.Email = {Email} RETURN count(*)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("Email", Email);
		String result = ExecuteQuery(query,parameters);
		return removeTableForSingleResult(result);
	}
         
        /////////////////////////////Backlogs/////////////////////////////
       
        
        
	
	
	
	
	public String WhoToWhomCount(String Email)
	{
		String query="match (a:USER),(b:USER), (a)-[r:Link {Relation: 'FROM'}]-(c:Email)-[s:Link {Relation: 'TO'}]->(b) where a.Email={Email} return b.Email,count(*)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("Email", Email);
		String result = ExecuteQuery(query,parameters);
                return result;
		//return removeTableForMultipleResult(result).toString();
	}
          //////////////////////Helper Methods/////////////////////////
        public String removeTableForSingleResult(String input)
        {
            String result = null;
            String newlines[] = input.split("\n");
            String output[] = newlines[3].split("\\|");
            result = output[1].trim();
            return result;
        }
        public ArrayList<String> removeTableForMultipleResult(String input)
        {
            ArrayList<String> mylist = new ArrayList<String>();
            String result = null;
            int counter = 0;
            String lines[] = input.split("\n");
            for(int i = 3; i < lines.length-2; i++)
            {
                String values[] = lines[i].split("\\|");
                result = "";
                for(int j=1; j<values.length;j++)
                {
                    result = values[j].replace('"',' ').trim()+ "," + result;
                    counter++;
                }
               // System.out.println();
                mylist.add(result.substring(0, result.length()-1));
                
            }
            return mylist;
        }
      
}
