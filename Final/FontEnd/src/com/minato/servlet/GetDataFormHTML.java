package com.minato.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		// try {
		// dropDatabase(statement, sqlDrop);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		// get data from HTML form
		// sqlCreate = getDataHTML(request, sqlCreate);
		// LOGGER.info("Done get data!");
		//
		// sqlCreate = sqlCreate.toLowerCase();

		// create database if not exist
		// try {
		// LOGGER.info("Sql create: " + sqlCreate);
		// createDatabase(statement, sqlCreate);
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// get data to insert
		getDataHtmltoInsert(request);

		// insert database
		try {
			try {
				insertDatabase(statement);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	private static String getDataHTML(HttpServletRequest request, String sql) {
		Enumeration<?> en = request.getParameterNames();

		String[] values = new String[100];
		int indexForm11 = 0;
		int indexForm18 = 0;

		for (int i = 1; en.hasMoreElements(); i++) {
			String param = (String) en.nextElement();

			// System.out.println("Param: " + param);

			values[i] = request.getParameter(param);

			if (param.contains("file")) {
				continue;
			}

			if (param.contains("form3-birthday") || param.contains("form12-day") || param.contains("form13-day")
					|| param.contains("form14-day") || param.contains("form19-startDay")
					|| param.contains("form19-endDay") || param.contains("form21-day")) {

				querry += param + ",";
				sql += param + " DATE, ";
				value += values[i] + ", ";
				continue;
			}

			// table 11
			if (param.contains("form11")) {

				if (indexForm11 == 0) {

					querry += param + ",";
					sql += "form11_Family_members" + " nvarchar(600), ";
					value += values[i] + ", ";

				}
				indexForm11++;
				continue;
			}

			// table 18
			if (param.contains("form18")) {
				if (indexForm18 == 0) {

					querry += param + ",";
					sql += "form18_Accompanying_children" + " nvarchar(200), ";
					value += values[i] + ", ";

				}
				indexForm18++;
				continue;
			}

			querry += param + ",";
			sql += param + " nvarchar(30), ";
			value += values[i] + ", ";

		}

		sql += "image nvarchar(200), start_time DATE, current_time DATE, primary key (id));";

		sql = sql.replaceAll("-", "_");

		return sql;
	}

	// get data insert
	private static void getDataHtmltoInsert(HttpServletRequest request) {
		Enumeration<?> en = request.getParameterNames();

		String[] values = new String[100];
		int indexForm11 = 0;
		int indexForm18 = 0;
		String jsonForm11 = "\"[";
		String jsonForm18 = "\"[";
		boolean checkForm11 = true;
		boolean checkForm18 = true;

		for (int i = 1; en.hasMoreElements(); i++) {
			String param = (String) en.nextElement();

			// System.out.println("Param: " + param);

			values[i] = request.getParameter(param);

			if (param.contains("file")) {
				continue;
			}

			// table 11
			if (param.contains("form11")) {
				
				System.out.println("form11" + values[i]);

				if (checkForm11) {
					
					if (values[i] == "" && !jsonForm11.contains(",")) {
						value += jsonForm11 + "]\"";
						checkForm11 = false;
						continue;
					}
					
					if (indexForm11 == 0) {
						querry += "form11_family_members" + ", ";
					}
					
					if (indexForm11 % 6 == 0) {
						jsonForm11 += "{\"relationship\": \"" + values[i] + "\", ";
						indexForm11++;
						continue;
					}
					
					if (indexForm11 % 6 == 1) {
						jsonForm11 += "\"full_name\": \"" + values[i] + "\", ";
						indexForm11++;
						continue;
					}
					
					if (indexForm11 % 6 == 2) {
						jsonForm11 += "\"sex\": \"" + values[i] + "\", ";
						indexForm11++;
						continue;
					}
					
					if (indexForm11 % 6 == 3) {
						jsonForm11 += "\"birthday\": \"" + values[i] + "\", ";
						indexForm11++;
						continue;
					}
					
					if (indexForm11 % 6 == 4) {
						jsonForm11 += "\"nationality\": \"" + values[i] + "\", ";
						indexForm11++;
						continue;
					}
					
					if (indexForm11 % 6 == 5) {
						jsonForm11 += "\"address\": \"" + values[i] + "\",},";
					}
					
					if (values[i] == "" && jsonForm11.contains(",") || indexForm11 == 35) {
						jsonForm11 = jsonForm11.substring(0, jsonForm11.length() - 1);
						value += jsonForm11 + "]\"";
						checkForm11 = false;
						continue;
					}

					indexForm11++;
					continue;
				}
			}

			// table 18
			if (param.contains("form18")) {

				if (checkForm18) {

					if (values[i] == "" && !jsonForm18.contains(",")) {
						value += jsonForm18 + "]\"";
						checkForm18 = false;
						continue;
					}

					if (indexForm18 == 0) {
						querry += "form18_accompanying_children" + ", ";
					}

					if (indexForm18 == 0 || indexForm18 == 4) {

						jsonForm18 += "{\"no\": \"" + values[i] + "\", ";
						indexForm18++;
						continue;
					}

					if (indexForm18 == 1 || indexForm18 == 5) {

						jsonForm18 += "\"full_name\": \"" + values[i] + "\", ";
						indexForm18++;
						continue;
					}

					if (indexForm18 == 2 || indexForm18 == 6) {

						jsonForm18 += "\"sex\": \"" + values[i] + "\", ";
						indexForm18++;
						continue;
					}

					if (indexForm18 == 3 || indexForm18 == 7) {

						jsonForm18 += "\"birthday\": \"" + values[i] + "\",},";
						
					}

					if (values[i] == "" && jsonForm18.contains(",") || indexForm18 == 7) {
						jsonForm18 = jsonForm18.substring(0, jsonForm18.length() - 1);
						value += jsonForm18 + "]\"";
						checkForm18 = false;
						continue;
					}

					indexForm18++;
					continue;
				}
			}

			querry += param + ",";
			value += "\"" + values[i] + "\", ";

		}

	}

	// Insert data
	private static void insertDatabase(Statement st) throws SQLException, ParseException {

		Date starttime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
		String strDate = formatter.format(starttime);

		if (querry.contains(",")) {

			querry = querry.substring(0, querry.length()) + " start_time) ";

			if (value.contains(",")) {
				value = value.substring(0, value.length()) + "\"" + strDate + "\");";
			}

			querry = querry.replaceAll("-", "_");

			querry = querry + value;

			System.out.println("Querry: " + querry);

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