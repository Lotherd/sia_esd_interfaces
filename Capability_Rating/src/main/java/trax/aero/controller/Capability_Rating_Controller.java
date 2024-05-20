package trax.aero.controller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import trax.aero.logger.LogManager;
import trax.aero.pojo.DATA;
import trax.aero.pojo.DATARequest;
import trax.aero.utils.TraxHostNameVerifier;
import trax.aero.utils.TraxX509TrustManager;

public class Capability_Rating_Controller {
	
	private static final String REALM = "trax";
	private static final String AUTHENTICATION_SCHEME = "Bearer";

	
	
	public static boolean auth(String auth) {

		
		// Get the Authorization header from the request
		String authorizationHeader =
				auth;

		// Validate the Authorization header
		if (!isTokenBasedAuthentication(authorizationHeader)) {
			 System.out.println("1");
			return false;
		}

		// Extract the token from the Authorization header
		String token = authorizationHeader
				.substring(AUTHENTICATION_SCHEME.length()).trim();
		 //System.out.println(token);

		try {

			// Validate the token
			if ( ! validateToken(token) ) {
				 System.out.println("2");
				return false;
			}
		} catch (Exception e) {
			 System.out.println("3");
			return false;
		}
		return true;
	}

	private static boolean isTokenBasedAuthentication(String authorizationHeader) {

		// Check if the Authorization header is valid
		// It must not be null and must be prefixed with "Bearer" plus a whitespace
		// The authentication scheme comparison must be case-insensitive
		return authorizationHeader != null && authorizationHeader.toLowerCase()
				.startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
	}

	



	private static boolean validateToken(String token) throws Exception {
		//check DB for user name and password
		try {
					
			//POST TO ENDPOINT TO VALIDATE TOKEN 
			 String url = System.getProperty("TraxAuth_URL");
			if(validateKey(url,token))
				return true;
		} catch (Exception e) {
				
			e.printStackTrace();
		}
		finally
		{
			
		}
		
		return false;
	}
	
	
	public static boolean validateKey(String url, String bearerAuth) {
		Client client = null;
		Response response = null;

		try {
			
			if (url.startsWith("https")) {
				client = getRestSSLClient(MediaType.APPLICATION_XML + ";charset=utf-8" + ";charset=utf-8", null);
			} else
				client = getRestHttpClient(MediaType.APPLICATION_XML + ";charset=utf-8" + ";charset=utf-8", null);
								
			WebTarget webTarget = client.target(url);
			Builder builder = webTarget.request();
			builder = builder.header("Content-type", MediaType.APPLICATION_JSON);
			builder = builder.header(HttpHeaders.AUTHORIZATION, "Bearer "+ bearerAuth);
			
			System.out.println("GET" +" to URL "+ url);		
			response = builder.get();
			
			if (response.getStatus() == 200 || response.getStatus() == 201) {
				return true;

			};	
			String error = response.readEntity(String.class);
			System.out.println(error);
			return false;
		}
		 catch (Exception exc) {
			 System.out.println(exc.toString());
		} finally {
			if (response != null)
				response.close();

			if (client != null)
				client.close();
		}
		return false;
		
	}
	
	
	private static Client getRestHttpClient(String accept, String contentType) {

		Client client = null;
		try {
			client = ClientBuilder.newClient();
			if (contentType != null)
				client.property("Content-Type", contentType);

			if (accept != null)
				client.property("accept", accept);

		} catch (Exception exc) {
			//logger.info("Error: TraxClientInterface.getRestHttpClient() -> " + exc.getMessage());
		}
		return client;
	}

	private static Client getRestSSLClient(String accept, String contentType) {

		Client client = null;
		try {

			ClientBuilder clientBuilder = ClientBuilder.newBuilder();
			clientBuilder = clientBuilder.sslContext(getSSLContext());
			clientBuilder.connectTimeout(60, TimeUnit.SECONDS);
			clientBuilder.readTimeout(60, TimeUnit.SECONDS);
			clientBuilder = clientBuilder.hostnameVerifier(new TraxHostNameVerifier());
			client = clientBuilder.build();

			if (contentType != null)
				client.property("Content-Type", contentType);

			if (accept != null)
				client.property("accept", accept);

		} catch (Exception exc) {

			//logger.info("Error: TraxClientInterface.getRestSSLClient() -> " + exc.getMessage());
		}
		return client;
	}

	
	

	
	
	/**
	 * Gets the SSL context.
	 *
	 * @return the SSL context
	 */
	private static SSLContext getSSLContext() {

		SSLContext context = null;
		try {
			context = SSLContext.getInstance("SSL");
		} catch (NoSuchAlgorithmException exc) {
			//logger.info("Error: TraxClientInterface.getSSLContext() -> " + exc.getMessage());
		}
		try {
			TraxX509TrustManager trustMger = new TraxX509TrustManager();
			context.init(null, new TrustManager[] { trustMger }, new SecureRandom());
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		return context;
	}
}
