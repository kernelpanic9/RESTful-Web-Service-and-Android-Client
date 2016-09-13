/**
 * 
 */
package com.tns;


import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


/**
 * @author root
 *
 */
public class DatabaseHelper {

	private String userName, password, url;
	private Connection conn;
	private Statement stmt;
	
	
	public DatabaseHelper(){
		this.userName = "root";
		this.password = "";
		this.url = "jdbc:mysql://localhost:3306/img_processing";
	}
	
	
	
	public void createConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, userName, password);
			stmt = conn.createStatement();
		}
		catch( Exception e ){
			System.out.println( e.getMessage() );
		}
	}
	
	
	public String getUsage( String username ) {
		ResultSet rs;
		String result = username;
		int id;
		try {
			rs = stmt.executeQuery("select id from authentication where username='" + username + "'");
			rs.next();
			id = rs.getInt("id");
			
			rs = stmt.executeQuery("select reading from bill where id='" + id + "'" );
			//String date = rs.getString("updated");
			if (rs.next()){
				Float reading = rs.getFloat("reading");
				result = Float.toString( reading ); // + ":" + date;					
			}
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println( e.getMessage() );
			e.printStackTrace();
			return "database-eror";
		}
		
	}
	
	
	
	public boolean updateUsage( String username, String usage ) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();

		try {
			ResultSet rs = stmt.executeQuery("select id from authentication where username='" + username + "'");
			rs.next();
			int id = rs.getInt("id");
			
			stmt.executeUpdate("update bill set reading=" + usage + ", updated='" + dateFormat.format(date) + "' where id='" + id + "'" );
			return true;
		} catch (SQLException e) {
			System.out.println( e.getMessage() );
			return false;
		}
	}
	
	
	
	public boolean isValidRequest( String username, String sessionId ){
		boolean flag = false;
		try{
			int id = 0;
			ResultSet idSet = stmt.executeQuery("select id from authentication where username='" + username + "'");
			if( idSet.next() ){
				id = idSet.getInt("id");
			}
			System.out.println("id: " + id);
			String query = "select sessionid from session where id='" + id + "'";
			ResultSet rs = stmt.executeQuery(query);
			if( rs.next() ){
				if( rs.getString("sessionid").equals(sessionId) ){
					flag = true;
				}
			}
			return flag;
		}
		catch( Exception e ){
			System.out.println( e.getMessage() );
			return flag;
		}
	}
	
	
	
	public String createSession(String user, String password){
		try{
			//checking password
			ResultSet rs = stmt.executeQuery("select * from authentication where username='" + user + "'");
			
			if( rs.next() ){
				if( !rs.getString("secretkey").equals(password) ){
					return "failure";
				}
				int id = rs.getInt("id");
				
				String sesId = "restses" + DatabaseHelper.generateString(new Random(), user, 10);
				stmt.executeUpdate("delete from session where id=" + id);
				
				String query = "insert into session values('" + id + "', '" + sesId + "')";
				stmt.executeUpdate(query);
				return sesId;
			}
			
			return "failure";
		}
		catch( Exception e ){
			System.out.println( e.getMessage() );
			return "failure";
		}
	}
	
	
	private static String generateString(Random rng, String characters, int length)
	{
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++){
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
	
	
	
	
	
	
	
	
	
	
	public static void main( String args[] ){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		System.out.println( dateFormat.format(date) );
		
		DatabaseHelper dbh = new DatabaseHelper();
		try {
			System.out.println( dbh.getUsage("2") );
		} catch (Exception e) {
			System.out.println( e.getMessage() );
		}
	}
	
}
