package trax.aero.util;


import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import trax.aero.logger.LogManager;
import trax.aero.pojo.I74_Request;

public class TaskCardPoster 
{
	Logger logger = LogManager.getLogger("UpdateTaskCardStatus_I74");
	private String body = null;
	
	
	public boolean postTaskCard(I74_Request data , String URL)
	{
		 String ID = System.getProperty("Post_ID");
		 String Password = System.getProperty("Post_Password");

		{

			Client client = null;
			Response response = null;

			
			
			try {

				
				
				final HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials(System.getProperty("Post_ID"), System.getProperty("Post_Password")).build();
				String auth = ID + ":" + Password;
				  
				  
				String url = URL;

				if (url == null) {
					
					return false;
				}

				if (url.startsWith("https")) {
					client = getRestSSLClient(MediaType.APPLICATION_XML + ";charset=utf-8" + ";charset=utf-8", null);
				} else
					client = getRestHttpClient(MediaType.APPLICATION_XML + ";charset=utf-8" + ";charset=utf-8", null);


				client = client.register(feature);
				
				WebTarget webTarget = client.target(url);

				
				Builder builder = webTarget.request();
				
				builder = builder.header("Content-type", MediaType.APPLICATION_XML + ";charset=utf-8");
				builder = builder.header("Accept", MediaType.APPLICATION_XML + ";charset=utf-8" + ";charset=utf-8");
				builder = builder.header(HttpHeaders.AUTHORIZATION, "Basic "+ new String(Base64.getEncoder().encode(auth.getBytes())));
				
				logger.info("POSTING Order Number:" + data.getOrderNumber() +" Order STATUS: " +data.getTRAXStatus()+ " to URL: " + url);
				
		
				response = builder.post(Entity.entity(data, MediaType.APPLICATION_XML + ";charset=utf-8"));
				
				body = response.readEntity(String.class);
				logger.info("Response: " + response.getStatus() + " Response Boby: " + body);
				
				
						
				
				
				if (response.getStatus() == 200 || response.getStatus() == 202) {
					// GET RESULTS (GET OR POST)
					return true;
					};
					return false;
				}
			 catch (Exception exc) {
				 logger.severe(exc.toString());
				
			} finally {
				if (response != null)
					response.close();
				
				
				if (client != null)
					client.close();
							
			}
		return false;
	}
	}
		
		private Client getRestSSLClient(String accept, String contentType) {

			Client client = null;
			try {

				
				
				ClientBuilder clientBuilder = ClientBuilder.newBuilder();
				clientBuilder = clientBuilder.sslContext(getSSLContext());
				clientBuilder = clientBuilder.hostnameVerifier(new TraxHostNameVerifier());
				client = clientBuilder.build();

				if (contentType != null)
					client.property("Content-Type", contentType);

				if (accept != null)
					client.property("accept", accept);

			} catch (Exception exc) {
				exc.printStackTrace();
			}
			return client;
		}

		/**
		 * Gets the SSL context.
		 *
		 * @return the SSL context
		 */
		private SSLContext getSSLContext() {

			SSLContext context = null;
			try {
				context = SSLContext.getInstance("SSL");
			} catch (NoSuchAlgorithmException exc) {
				exc.printStackTrace();
			}
			try {
				TraxX509TrustManager trustMger = new TraxX509TrustManager();
				context.init(null, new TrustManager[] { trustMger }, new SecureRandom());
			} catch (KeyManagementException e) {
				e.printStackTrace();
			}
			return context;
		}
		
		private Client getRestHttpClient(String accept, String contentType) {

			Client client = null;
			try {
				client = ClientBuilder.newClient();
				if (contentType != null)
					client.property("Content-Type", contentType);

				if (accept != null)
					client.property("accept", accept);

			} catch (Exception exc) {
				exc.printStackTrace();
			}
			return client;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}
	
	
	
	
	
	


	
	
	
}

















