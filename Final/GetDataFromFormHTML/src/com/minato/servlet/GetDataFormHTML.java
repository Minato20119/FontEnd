package com.minato.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetDataFormHTML
 */
@WebServlet("/reportServlet")
public class GetDataFormHTML extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(GetDataFormHTML.class.getName());
	private static String querry = "INSERT INTO html.dataForm (";
	private static String value = "VALUES (";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetDataFormHTML() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Statement statement = connectDataBase();

		String sqlDrop = "DROP TABLE IF EXISTS html.dataForm;";
		String sqlCreate = "CREATE TABLE html.dataForm (id int unsigned auto_increment not null, ";

		// drop database if exist
//		try {
//			dropDatabase(statement, sqlDrop);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

		// get data from HTML form
		sqlCreate = getDataHTML(request, statement, sqlCreate);
		LOGGER.info("Done get data!");

		// create database if not exist
//		try {
//			LOGGER.info("Sql create: " + sqlCreate);
//			createDatabase(statement, sqlCreate);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		// insert database
		try {
			insertDatabase(statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Connect database mysql
	private static Statement connectDataBase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			LOGGER.warning("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
		}

		LOGGER.info("MySQL JDBC Driver Registered!");
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/html", "root", "Gango2405");

		} catch (SQLException e) {
			LOGGER.info("Connection Failed! Check output console");
			e.printStackTrace();
		}

		if (connection != null) {
			LOGGER.info("You made it, take control your database now!");
		} else {
			LOGGER.info("Failed to make connection!");
		}

		Statement st = null;

		try {
			st = connection.createStatement();
		} catch (SQLException e) {
			LOGGER.info("Error create database!");
			e.printStackTrace();
		}

		return st;
	}

	// get data from HTML form
	private static String getDataHTML(HttpServletRequest request, Statement st, String sql) {
		Enumeration<?> en = request.getParameterNames();

		String[] values = new String[100];

		for (int i = 1; en.hasMoreElements(); i++) {
			String param = (String) en.nextElement();

			// System.out.println(param);

			values[i] = request.getParameter(param);

			if (!values[i].equals("")) {

				querry += param + ",";

				if (values[i].matches("[0-9]*")) {

					sql += param + " int, ";
					value += values[i] + ", ";

				} else {
					sql += param + " nvarchar(50), ";
					value += "\"" + values[i] + "\", ";
				}
			}
			continue;

		}

		sql += "primary key (id));";
		sql = sql.replaceAll("-", "_");

		return sql;
	}

	// Insert data
	private static void insertDatabase(Statement st) throws SQLException {

		if (querry.contains(",")) {

			querry = querry.substring(0, querry.length() - 1) + ") ";

			if (value.contains(",")) {
				value = value.substring(0, value.length() - 2) + ");";
			}

			querry = querry.replaceAll("-", "_");

			querry = querry + value;

			st.executeUpdate(querry);
			LOGGER.info(querry);
			LOGGER.info("Done insert data!");
		}
	}

	// Create database
	private static void createDatabase(Statement st, String sql) throws SQLException {

		st.executeUpdate(sql);

		LOGGER.info("Done create database!");
	}

	// Drop database
	private static void dropDatabase(Statement st, String sqlDrop) throws SQLException {
		st.executeUpdate(sqlDrop);
		LOGGER.info("Done drop database!");
	}

}
