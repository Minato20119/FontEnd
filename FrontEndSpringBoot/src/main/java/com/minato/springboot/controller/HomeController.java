package com.minato.springboot.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.minato.springboot.config.Configure;

@Controller
public class HomeController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	private static String querry = "INSERT INTO " + Configure.NAME_SCHEMA_DATABASE + " (";
	private static String value = "VALUES (";

	@RequestMapping("/")
	String home(ModelMap modal) {
		System.out.println("This is Home");
		return "index";
	}

	@PostMapping("reportServlet")
	@ResponseBody
	public String home(HttpServletRequest request) {

		Statement statement = connectDataBase();

		String SQL_CREATE = "CREATE TABLE " + Configure.NAME_SCHEMA_DATABASE
				+ " (id int unsigned auto_increment not null, ";

		// DROP database if exist
		// try {
		// dropDatabase(statement);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		// get data from HTML form
		// SQL_CREATE = getDataHTML(request, SQL_CREATE);
		// LOGGER.info("Done get data!");
		//
		// SQL_CREATE = SQL_CREATE.toLowerCase();

		// CREATE database if not exist
		// try {
		// LOGGER.info("Sql create: " + SQL_CREATE);
		// createDatabase(statement, SQL_CREATE);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		// get data to insert
		try {
			String passPort = getDataHtmltoInsert(request);

			String querry1 = "SELECT form12_passport FROM " + Configure.NAME_SCHEMA_DATABASE;

			try {
				ResultSet resultSet = statement.executeQuery(querry1);

				String passPortFromDB = "";

				while (resultSet.next()) {
					passPortFromDB += resultSet.getString("form12_passport") + " : ";
				}

				if (passPortFromDB.contains(passPort)) {

					LOGGER.info("Don't save!");

					deleteImageToFolderUpload();

					querry = "INSERT INTO " + Configure.NAME_SCHEMA_DATABASE + " (";
					value = "VALUES (";

					return "The Passport is already exists!";

				} else {
					// INSERT database

					LOGGER.info("Save!");

					String imagePath = saveImageToFolder(passPort);

					try {

						insertDatabase(statement, imagePath);
						querry = "INSERT INTO " + Configure.NAME_SCHEMA_DATABASE + " (";
						value = "VALUES (";

					} catch (SQLException e) {

						deleteFolderPassPort(passPort);
						e.printStackTrace();

						return "Error insert form!";

					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		return "Success!!!";
	}

	// Connect database mysql
	private static Statement connectDataBase() {
		try {
			Class.forName(Configure.CLASS_FOR_NAME_JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.warn("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
		}

		LOGGER.info("MySQL JDBC Driver Registered!");
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(Configure.JDBC_DRIVER, Configure.USERNAME_MYSQL,
					Configure.PASSWORD_MYSQL);

		} catch (SQLException e) {
			LOGGER.error("Connection Failed! Check output console");
			e.printStackTrace();
		}

		if (connection != null) {
			LOGGER.info("You made it, take control your database now!");
		} else {
			LOGGER.error("Failed to make connection!");
		}

		Statement st = null;

		try {
			st = connection.createStatement();
		} catch (SQLException e) {
			LOGGER.error("Error create database!");
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
					sql += "form18_Accompanying_children" + " nvarchar(500), ";
					value += values[i] + ", ";

				}
				indexForm18++;
				continue;
			}

			querry += param + ",";
			sql += param + " nvarchar(50), ";
			value += values[i] + ", ";

		}

		sql += "image nvarchar(30), created_at BIGINT, updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP, primary key (id));";

		sql = sql.replaceAll("-", "_");

		return sql;
	}

	// get data insert
	private static String getDataHtmltoInsert(HttpServletRequest request) throws JSONException {

		String passPort = "";

		Enumeration<?> en = request.getParameterNames();

		JSONObject jsonForm11 = new JSONObject();
		JSONArray jsonArrayForm11 = new JSONArray();

		JSONObject jsonForm18 = new JSONObject();
		JSONArray jsonArrayForm18 = new JSONArray();

		String[] values = new String[100];
		int indexForm11 = 0;
		int indexForm18 = 0;

		for (int i = 1; en.hasMoreElements(); i++) {

			String param = (String) en.nextElement();

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

				value += "'" + jsonArrayForm11.toString() + "', ";

				// Get passPort
				passPort = values[i];

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

				if (indexForm18 == 3) {

					jsonForm18.put("birthday", values[i]);

					jsonForm18.put("image1", passPort + getImagePathUpload("image1", ""));

					jsonArrayForm18.put(jsonForm18);

					indexForm18++;
					continue;
				}

				if (indexForm18 == 7) {

					jsonForm18.put("birthday", values[i]);
					jsonForm18.put("image2", passPort + getImagePathUpload("image2", ""));

					jsonArrayForm18.put(jsonForm18);

					indexForm18++;
					continue;
				}

			}

			if (param.contains("form19-Applying-visa")) {

				value += "'" + jsonArrayForm18.toString() + "', ";
			}

			querry += param + ", ";
			value += "\"" + values[i] + "\", ";

		}

		return passPort;

	}

	// Insert data
	private static void insertDatabase(Statement st, String imagePath) throws SQLException, ParseException {

		if (querry.contains(",")) {

			querry = querry.substring(0, querry.length()) + " image, " + "created_at) ";

			if (value.contains(",")) {
				value = value.substring(0, value.length()) + "\"" + imagePath + "\"" + ", \""
						+ System.currentTimeMillis() + "\");";
			}

			querry = querry.replaceAll("-", "_").toLowerCase();

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
	private static void dropDatabase(Statement st) throws SQLException {
		st.executeUpdate(Configure.SQL_DROP);
		LOGGER.info("Done drop database!");
	}

	// Delete file image temp
	private static void deleteImageToFolderUpload() {

		File filePath = new File(Configure.PATH);

		for (File file : filePath.listFiles())
			if (!file.isDirectory())
				file.delete();

		LOGGER.info("Done delete temp image!");
	}

	// Delete file image temp
	private static void deleteFolderPassPort(String namePassPort) {

		File filePath = new File(Configure.PATH + namePassPort);

		for (File file : filePath.listFiles())
			if (file.isDirectory())
				file.delete();

		LOGGER.info("Done delete directory passport!");
	}

	private static String saveImageToFolder(String namePassPort) throws IOException {

		// Create directory name is passport
		new File(Configure.PATH + namePassPort).mkdir();

		LOGGER.info("Done create directory!");

		// Move image from upload to passport
		File filePath = new File(Configure.PATH);

		File[] allSubFiles = filePath.listFiles();

		for (File file : allSubFiles) {

			if (file.isFile()) {

				String f = file.getAbsolutePath();

				File sourceFile = new File(file.getAbsolutePath());

				System.out.println(file.getAbsolutePath());

				sourceFile.renameTo(
						new File(Configure.PATH + namePassPort + "\\" + f.substring(f.lastIndexOf("\\"), f.length())));

			}
		}

		LOGGER.info("Done move file!");

		String filterImage = "image.";

		String imagePath = getImagePathUpload(filterImage, namePassPort);
		
		if (imagePath.contains("null") ) {
			imagePath = "";
		}

		System.out.println("Image Path: " + imagePath);

		return namePassPort + "\\" + imagePath;
	}

	// get image path
	private static String getImagePathUpload(String filterImage, String namePassPort) {

		System.out.println("Filter image: " + filterImage);

		File filePath = new File(Configure.PATH);

		if (filterImage.equals("image.")) {
			filePath = new File(Configure.PATH + namePassPort);
		}

		File[] allSubFiles = filePath.listFiles();

		try {

			for (File file : allSubFiles) {

				if (file.isFile()) {

					String f = file.getAbsolutePath();

					System.out.println("f: " + f);

					if (f.contains(filterImage)) {

						System.out.println("F: " + f.substring(f.lastIndexOf("\\"), f.length()));

						return f.substring(f.lastIndexOf("\\"), f.length());
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "All sub files is null!";
	}
}
