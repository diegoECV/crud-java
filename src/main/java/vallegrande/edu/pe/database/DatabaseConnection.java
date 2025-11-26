package vallegrande.edu.pe.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static final String DB_HOST = "wawalu-aws.c506266wsgbx.us-east-1.rds.amazonaws.com";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "diego1416";
	private static final String DB_NAME = "wawalu_db";
	private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":3306/" + DB_NAME + "?useSSL=false&serverTimezone=UTC";

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new SQLException("No se encontró el driver de MySQL", e);
		}
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}
}
