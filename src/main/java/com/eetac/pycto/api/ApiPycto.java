package com.eetac.pycto.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.eetac.pycto.managers.ServerBallotBox;
import com.eetac.pycto.managers.ServerCACR;
import com.eetac.pycto.models.CA_CR;
import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;


@Path("/api")
public class ApiPycto {
	
	private static final String SERVER_UPLOAD_LOCATION_FOLDER = "C:/Users/Victorz/";

		@GET
		@Path("/prova/{param}")
		public Response getMsg(@PathParam("param") String msg) {
	 
			String output = "Jersey say : " + msg;
	 
			return Response.status(200).entity(output).build();
	 
		}

		@GET
		@Path("/login/{user}/{password}")
		public Response login(
				@PathParam("user") String user,
				@PathParam("password") String pass,
				@Context HttpServletRequest request) {
			
		    HttpSession session = request.getSession(true);

		    String output;
		    ServerCACR um = new ServerCACR();
		    
		    if(um.login(user, pass)==true)
		    {
		    	session.setAttribute("user", user);
		    	output = "Login correcto!";
		    }
		    else
		    {
		    	output = "Usuario y/o password incorrecto!";
		    }
		    
			return Response.status(200).entity(output).build();
	 
		}

		@GET
		@Path("/register/{user_json}")
		public Response  register(
				@PathParam("user_json") String user_json,
				@Context HttpServletRequest request) {

			Gson g = new Gson();
			
		    String output;
		    ServerCACR um = new ServerCACR();
		    HttpSession session = request.getSession(true);
		    System.out.println(" sssssssssssss00"+user_json);
		    CA_CR user = new Gson().fromJson(user_json, CA_CR.class);

		    if(um.register(user)==true)
		    {
		    	session.setAttribute("user", user);
		    	output = "Register correcto!";
		    }
		    else
		    {
		    	output = "Registro no correcto!";
		    }
		    
			return Response.status(200).entity(output).build();
	 
		}
		
		/**
		 * Firma de certificado
		 */
		@GET
		@Path("/signcertificate/{blindcsr}")
		public Response signcertificate(
				@PathParam("blindcsr") BigInteger blindcsr,
				@Context HttpServletRequest request) {
			
	    	HttpSession session= request.getSession(true);
	    	Object user = session.getAttribute("user");
	    	
	    	String output = null;
	    	
	    	if (user!=null) {
			    ServerCACR um = new ServerCACR();
			    BigInteger certificate = um.certificate(blindcsr);	
				return Response.status(200).entity(certificate.toString()).build();
	    	}
	    	else
	    	{
	    		output = "No estas logueado, no puedes firmar el CSR";
				return Response.status(200).entity(output).build();
	    	}
	    	
	    }	
		
		/**
		 * Votar
		 */
		@GET
		@Path("/vote/{string_pepina}")
		public Response vote(
				@PathParam("string_pepina") String string_pepina,
				@Context HttpServletRequest request) {
			
	    	HttpSession session= request.getSession(true);
	    	Object user = session.getAttribute("user");
	    	
	    	String output = null;
	    	
	    	if (user!=null) {
	    		ServerBallotBox m = new ServerBallotBox();
	    		boolean result = m.vote(string_pepina);
			    
				return Response.status(200).entity(result).build();
	    	}
	    	else
	    	{
	    		output = "No estas logueado, no puedes firmar el CSR";
				return Response.status(200).entity(output).build();
	    	}
	    	
	    }	
		
		/**
		 * Upload a File
		 */

		@POST
		@Path("/uploadimage")
		@Consumes(MediaType.MULTIPART_FORM_DATA)
		public Response uploadFile(
				@FormDataParam("file") InputStream fileInputStream,
				@FormDataParam("file") FormDataContentDisposition contentDispositionHeader,
				@Context HttpServletRequest request) {
			
	    	HttpSession session= request.getSession(true);
	    	Object user = session.getAttribute("user");
	    	
	    	String output;
	    	
	    	if (user!=null) {
	    		
		    	String filePath = SERVER_UPLOAD_LOCATION_FOLDER	+ contentDispositionHeader.getFileName();
		    	// save the file to the server
		    	saveFile(fileInputStream, filePath);

		    	output = "Imagen subida correctamente";
		    }
		    else{
		    	output = "No has iniciado session";
		    }

			return Response.status(200).entity(output).build();

		}

		// save uploaded file to a defined location on the server
		private void saveFile(InputStream uploadedInputStream,
				String serverLocation) {

			try {
				OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
				int read = 0;
				byte[] bytes = new byte[1024];

				outpuStream = new FileOutputStream(new File(serverLocation));
				while ((read = uploadedInputStream.read(bytes)) != -1) {
					outpuStream.write(bytes, 0, read);
				}
				outpuStream.flush();
				outpuStream.close();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
	 
}
