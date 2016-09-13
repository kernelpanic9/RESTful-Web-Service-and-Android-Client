package com.tns;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/auth")
public class Authentication {

	
	@GET
	@Path("/new/{user}/{pass}")
	@Produces(MediaType.TEXT_PLAIN)
	public String authenticateUser( @PathParam("user")String user, @PathParam("pass")String pass){
		DatabaseHelper dbh = new DatabaseHelper();
		System.out.println(user + ":" + pass);
		String sessionId;
		dbh.createConnection();
		sessionId = dbh.createSession(user, pass);
		System.out.println(sessionId);
		return sessionId;
	}
	
	
}



