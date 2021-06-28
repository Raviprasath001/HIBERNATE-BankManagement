package firstPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CallableStatement {


		public static void main(String[] args) {
			String dataBaseUrl = "jdbc:sqlserver://ec2-54-191-44-79.us-west-2.compute.amazonaws.com;databaseName=Raviprasath";
			String userName = "rmurugan";
			String password = "Prasath@123";
			String Query = "{Call employeeprocedure(?)}";

			try  {
				Connection conn = DriverManager.getConnection(dataBaseUrl, userName, password);
			 
				java.sql.CallableStatement stmt=conn.prepareCall(Query);
		        stmt.setString(1, "Owner");
		        
		        ResultSet result = stmt.executeQuery();
		       
		
		        while(result.next()) { 
		        	System.out.println("CustomerId : "+result.getString("customerid")+" | " +
		        			"CompanyName : "+result.getString("companyname")+" | " +
		        			"ContactName : "+result.getString("ContactName")+" | " +
		        			"ContactTitle : "+result.getString("ContactTitle")+" | " +
		        			"City : "+result.getString("City")+" | " +
		        			"Region : "+result.getString("Region")+" | ");
		        }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	

}
