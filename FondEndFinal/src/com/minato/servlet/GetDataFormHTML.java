package com.minato.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class GetDataFormHTML
 */
@WebServlet("/reportServlet")
public class GetDataFormHTML extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(GetDataFormHTML.class.getName());
	private static String querry = "INSERT INTO html.dataform (";
	private static String value = "VALUES (";

	public static final String PATH = "C:\\Users\\Minato\\eclipse-workspace2\\FondEndFinal\\WebContent\\WEB-INF\\upload\\";

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

		String sqlDrop = "DROP TABLE IF EXISTS html.dataform;";
		String sqlCreate = "CREATE TABLE html.dataform (id int unsigned auto_increment not null, ";

		// DROP database if exist
//		 try {
//		 dropDatabase(statement, sqlDrop);
//		 } catch (SQLException e) {
//		 e.printStackTrace();
//		 }

		// get data from HTML form
//		 sqlCreate = getDataHTML(request, sqlCreate);
//		 LOGGER.info("Done get data!");
//		
//		 sqlCreate = sqlCreate.toLowerCase();

		// CREATE database if not exist
//		 try {
//		 LOGGER.info("Sql create: " + sqlCreate);
//		 createDatabase(statement, sqlCreate);
//		 } catch (SQLException e) {
//		 e.printStackTrace();
//		 }

		// get data to insert
		try {
			String passPort = getDataHtmltoInsert(request);
			System.out.println("passPort: " + passPort);

			String querry = "SELECT form12_passport FROM html.dataform";

			try {
				ResultSet resultSet = statement.executeQuery(querry);

				String passPortFromDB = "";

				while (resultSet.next()) {
					passPortFromDB += resultSet.getString("form12_passport") + " : ";
				}

				System.out.println("passPortFromDB: " + passPortFromDB);

				if (passPortFromDB.contains(passPort)) {

					System.out.println("Don't save!");
					deleteImageToFolderUpload();

				} else {
					// INSERT database

					System.out.println("Save!");

					String imageJson = saveImageToFolder(passPort);

					try {
						try {
							insertDatabase(statement, imageJson);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (JSONException e1) {
			e1.printStackTrace();
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
					sql += "form11_Family_members" + " nvarchar(1200), ";
					value += values[i] + ", ";

				}
				indexForm11++;
				continue;
			}

			// table 18
			if (param.contains("form18")) {
				if (indexForm18 == 0) {

					querry += param + ",";
					sql += "form18_Accompanying_children" + " nvarchar(300), ";
					value += values[i] + ", ";

				}
				indexForm18++;
				continue;
			}

			querry += param + ",";
			sql += param + " nvarchar(50), ";
			value += values[i] + ", ";

		}

		sql += "image nvarchar(500), created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, primary key (id));";

		sql = sql.replaceAll("-", "_");

		return sql;
	}

	// get data insert
	private static String getDataHtmltoInsert(HttpServletRequest request) throws JSONException {

		String passPort = "";

		Enumeration<?> en = request.getParameterNames();

		JSONObject jsonForm11 = new JSONObject();
		JSONArray jsonArrayForm11 = new JSONArray();
		JSONObject mainObjectForm11 = new JSONObject();

		JSONObject jsonForm18 = new JSONObject();
		JSONArray jsonArrayForm18 = new JSONArray();
		JSONObject mainObjectForm18 = new JSONObject();

		String[] values = new String[100];
		int indexForm11 = 0;
		int indexForm18 = 0;

		for (int i = 1; en.hasMoreElements(); i++) {
			String param = (String) en.nextElement();

			System.out.println("Param: " + param);

			values[i] = request.getParameter(param);

			if (param.contains("fileImage")) {
				continue;
			}

			// table 11
			if (param.contains("form11")) {

				if (values[i] == "") {
					continue;
				}

				if (indexForm11 == 0) {
					querry += "form11_family_members" + ", ";
				}

				if (indexForm11 % 6 == 0) {

					jsonForm11 = new JSONObject();

					jsonForm11.put("relationship", values[i]);

					indexForm11++;
					continue;
				}

				if (indexForm11 % 6 == 1) {
					jsonForm11.put("full_name", values[i]);

					indexForm11++;
					continue;
				}

				if (indexForm11 % 6 == 2) {
					jsonForm11.put("sex", values[i]);

					indexForm11++;
					continue;
				}

				if (indexForm11 % 6 == 3) {
					jsonForm11.put("birthday", values[i]);

					indexForm11++;
					continue;
				}

				if (indexForm11 % 6 == 4) {
					jsonForm11.put("nationality", values[i]);

					indexForm11++;
					continue;
				}

				if (indexForm11 % 6 == 5) {
					jsonForm11.put("address", values[i]);

					jsonArrayForm11.put(jsonForm11);

					indexForm11++;
					continue;
				}

			}

			if (param.contains("form12-Passport")) {

				mainObjectForm11.put("form11_family_members", jsonArrayForm11);
				value += "'" + mainObjectForm11.toString() + "', ";

				// Get passPort
				passPort = values[i];

				System.out.println("passport: " + passPort);

			}

			// table 18
			if (param.contains("form18")) {

				if (values[i] == "") {
					continue;
				}

				if (indexForm18 == 0) {
					querry += "form18_accompanying_children" + ", ";
				}

				if (indexForm18 % 4 == 0) {

					jsonForm18 = new JSONObject();

					jsonForm18.put("no", values[i]);

					indexForm18++;
					continue;
				}

				if (indexForm18 % 4 == 1) {
					jsonForm18.put("full_name", values[i]);

					indexForm18++;
					continue;
				}

				if (indexForm18 % 4 == 2) {
					jsonForm18.put("sex", values[i]);

					indexForm18++;
					continue;
				}

				if (indexForm18 % 4 == 3) {
					jsonForm18.put("birthday", values[i]);

					jsonArrayForm18.put(jsonForm18);

					indexForm18++;
					continue;
				}

			}

			if (param.contains("form19-Applying-visa")) {
				mainObjectForm18.put("form18_accompanying_children", jsonArrayForm18);
				value += "'" + mainObjectForm18.toString() + "', ";
			}

			querry += param + ", ";
			value += "\"" + values[i] + "\", ";

		}

		System.out.println("passport out: " + passPort);

		return passPort;

	}

	// Insert data
	private static void insertDatabase(Statement st, String imageJson) throws SQLException, ParseException {


		Timestamp created_at = new Timestamp(System.currentTimeMillis());

		if (querry.contains(",")) {

			querry = querry.substring(0, querry.length()) + " image, " + "created_at) ";

			if (value.contains(",")) {
				value = value.substring(0, value.length()) + "'" + imageJson + "'" + ", \"" + created_at + "\");";
			}

			querry = querry.replaceAll("-", "_").toLowerCase();

			querry = querry + value;

			System.out.println("Querry insert: " + querry);

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

	private static String saveImageToFolder(String passPortFolder) throws IOException, JSONException {

		// Create directory name is passport
		new File(PATH + passPortFolder).mkdir();

		System.out.println("Done create directory!");

		// Move image from upload to passport
		File filePath = new File(PATH);

		File[] allSubFiles = filePath.listFiles();

		for (File file : allSubFiles) {
			if (file.isFile()) {

				String f = file.getAbsolutePath();

				File sourceFile = new File(file.getAbsolutePath());

				sourceFile.renameTo(
						new File(PATH + passPortFolder + "\\" + f.substring(f.lastIndexOf("\\"), f.length())));
			}
		}

		System.out.println("Done move file!");

		String imageJson = getImageJsonPath(PATH + passPortFolder);

		return imageJson;
	}

	private static void deleteImageToFolderUpload() {

		File filePath = new File(PATH);

		for (File file : filePath.listFiles())
			if (!file.isDirectory())
				file.delete();

		System.out.println("Done delete file!");
	}

	// get image json path
	private static String getImageJsonPath(String passPortFolder) throws JSONException {

		File filePath = new File(passPortFolder);

		JSONObject jsonImage = new JSONObject();
		JSONArray jsonImageArray = new JSONArray();

		File[] allSubFiles = filePath.listFiles();

		int i = 1;
		for (File file : allSubFiles) {
			if (file.isFile()) {

				String f = file.getAbsolutePath();

				jsonImage.put("image" + i++, f);

			}
		}
		jsonImageArray.put(jsonImage);

		return jsonImageArray.toString();
	}

}