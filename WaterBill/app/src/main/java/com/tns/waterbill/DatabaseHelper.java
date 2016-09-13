package com.tns.waterbill;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by root on 9/12/16.
 */
public class DatabaseHelper {


    public String getSessionId( String username ){

        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:water_bill.db");
            Statement stmt = con.createStatement();
            String query = "create table if not exist auth( username varchar(15), sessionid varchar(40) )";
            stmt.executeQuery( query );

            query = "select sessionid from auth";// where username='" + username + "'";
            ResultSet rs = stmt.executeQuery( query ); //.executeUpdate( query );

            if( rs.next() ){
                String sessionId = rs.getString("sessionid");
                return sessionId;
            }

        } catch (SQLException e) {
            Log.i("*********", e.getMessage());
            e.printStackTrace();
            return "exception";
        } catch (ClassNotFoundException e) {
            Log.i("*********", e.getMessage());
            e.printStackTrace();
            return "exception";
        }

        return "no-user";
    }


    public boolean saveAuthInfo( String username, String sessionId ){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:water_bill.db");
            Statement stmt = conn.createStatement();
            String query = "create table auth( username varchar(15), sessionid varchar(40) )";
            stmt.executeQuery( query );
            query = "insert into auth values('" + username + "', '" + sessionId + "')";
            boolean flag = stmt.execute( query );
            if( flag ){
                Log.i( "database", "user-added" );
            }
            return flag;
        }
        catch( Exception e ){
            return false;
        }
    }



}
