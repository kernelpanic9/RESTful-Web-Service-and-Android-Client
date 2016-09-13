/**
 * TNS
 */

package com.tns;


import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author root
 *
 */

@Path("/computerVision")
public class AndroidWebService {

	
	public AndroidWebService(){
		
	}
	
	
	
	@GET
	@Path("/usage/{sessionid}/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getBill( @PathParam("sessionid") String sessionid, @PathParam("username") String username ){
		System.out.println( sessionid + ":" +username );
		DatabaseHelper dbh = new DatabaseHelper();
		dbh.createConnection();
		if( dbh.isValidRequest(username, sessionid) ){
			String usage;
			usage = dbh.getUsage( username );
			return usage;
		}
		else{
			return "invalid-session";
		}
		
		//return Response.status(200).entity("User: " + id).build();
		
	}
	
	
	
	@PUT
	@Path("/usage/{sessionid}/{username}/{usage}")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateUsage( @PathParam("sessionid")String sessionId, @PathParam("username")String username, @PathParam("usage")String usage ){
		DatabaseHelper dbh = new DatabaseHelper();
		dbh.createConnection();
		
		if( !dbh.isValidRequest(username, sessionId) ){
			return "invalid-session";
		}
		
		System.out.println(sessionId + ":" + username + ":" + usage);
		boolean flag = dbh.updateUsage(username, usage);
		System.out.println(username + ":" + usage + flag);
		if( flag ){
			return "success";
		}
		else{
			return "failure";
		}
	}

	
	
	
}


