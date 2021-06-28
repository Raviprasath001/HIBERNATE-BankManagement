package firstPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RaviClass {
	
	public static void main(String[] args) {
		  String DB_URL = "jdbc:sqlserver://ec2-54-191-44-79.us-west-2.compute.amazonaws.com;databaseName=Raviprasath";
		  String USER = "rmurugan";
		  String PASS = "Prasath@123";
		  String QUERY = "SELECT top 10* FROM Employees";
		  
		try (Connection conn=DriverManager.getConnection(DB_URL,USER,PASS);
				Statement stmt=conn.createStatement();
				ResultSet rs=stmt.executeQuery(QUERY)){
			while(rs.next()) {
			System.out.println("EmployeeId: "+ rs.getString("Employeeid")
			+" | "+"LastName: "+ rs.getString("LastName")
			+" | "+"FirstName: "+ rs.getString("FirstName")
			+" | "+"Title: "+ rs.getString("Title")
			+" | "+"TitleOfCourtesy: "+ rs.getString("TitleOfCourtesy")
			+" | "+"BirthDate: "+ rs.getString("BirthDate")
			+" | "+"HireDate: "+ rs.getString("HireDate")
			+" | "+"City: "+ rs.getString("City")
			+" | "+"Region: "+ rs.getString("Region")
			+" | "+"PostalCode: "+ rs.getString("PostalCode")
			+" | "+"Country: "+ rs.getString("Country")
			);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
