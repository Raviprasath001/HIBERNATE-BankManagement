package firstPackage;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PreparedStatement {

	public static void main(String[] args) {
		String dataBaseUrl = "jdbc:sqlserver://ec2-54-191-44-79.us-west-2.compute.amazonaws.com;databaseName=Raviprasath";
		String userName = "rmurugan";
		String password = "Prasath@123";
		String Query = "SELECT top 10* FROM Employees WHERE Department='HR'";

		try (Connection conn = DriverManager.getConnection(dataBaseUrl, userName, password);
				java.sql.PreparedStatement stmt = conn.prepareStatement(Query);
				ResultSet result = stmt.executeQuery()) {
			while (result.next()) {
				System.out.println("EmployeeId: " + result.getString("Employeeid") + " | " + "LastName: "
						+ result.getString("LastName") + " | " + "FirstName: " + result.getString("FirstName") + " | "
						+ "Department: " + result.getString("Department") + " | " + "TitleOfCourtesy: "
						+ result.getString("TitleOfCourtesy") + " | " + "BirthDate: " + result.getString("BirthDate")
						+ " | " + "HireDate: " + result.getString("HireDate") + " | " + "City: "
						+ result.getString("City") + " | " + "Region: " + result.getString("Region") + " | "
						+ "PostalCode: " + result.getString("PostalCode") + " | " + "Country: "
						+ result.getString("Country"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
