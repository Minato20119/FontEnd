package com.minato.springboot.config;

public class Configure {
	
	public static final String NAME_ADDRESS_MYSQL = "localhost";
	
	public static final String NAME_PORT_MYSQL = "3306";
	
	public static final String USERNAME_MYSQL = "";
	
	public static final String PASSWORD_MYSQL = "";
	
	public static final String NAME_SCHEMA = "mydb";
	
	public static final String NAME_DATABASE = "dataform";

	public static final String PATH = "C:\\Users\\Minato\\eclipse-workspace\\FrontEndSpringBoot\\src\\main\\resources\\static\\upload\\";
	
	public static final String NAME_SCHEMA_DATABASE = NAME_SCHEMA + "." + NAME_DATABASE;

	public static final String SQL_DROP = "DROP TABLE IF EXISTS " + NAME_SCHEMA_DATABASE + ";";
	
	public static final String CLASS_FOR_NAME_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	public static final String JDBC_DRIVER = "jdbc:mysql://" + NAME_ADDRESS_MYSQL + ":" + NAME_PORT_MYSQL + "/" + NAME_SCHEMA;
}
