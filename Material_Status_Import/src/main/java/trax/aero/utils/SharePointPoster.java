package trax.aero.utils;


import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import trax.aero.logger.LogManager;
import trax.aero.pojo.Token;

public class SharePointPoster 
{
	Logger logger = LogManager.getLogger("MaterialMovement_I42&I44");
	private byte[] body = null;
	
	
	public boolean postSharePoint(String URL, String token)
	{
		

		{

			Client client = null;
			Response response = null;

			
			
			try {

				
				
				String auth = token;
				  
				  
				String url = URL;

				if (url == null) {
					
					return false;
				}

				if (url.startsWith("https")) {
					client = getRestSSLClient(MediaType.APPLICATION_XML + ";charset=utf-8" + ";charset=utf-8", null);
				} else
					client = getRestHttpClient(MediaType.APPLICATION_XML + ";charset=utf-8" + ";charset=utf-8", null);
		
				
				WebTarget webTarget = client.target(url);
			
				Builder builder = webTarget.request();
				builder = builder.header(HttpHeaders.AUTHORIZATION, "Bearer "+ auth);
				
				logger.info("GET to URL: " + url);
				
		
				response = builder.get();
				
				
						
				body = null;
				
				if (response.getStatus() == 200 || response.getStatus() == 202) {
					
					logger.info("Response: " + response.getStatus());	
					
					InputStream is = response.readEntity(InputStream.class);
							
					body = IOUtils.toByteArray(is);
										
					// GET RESULTS (GET OR POST)
					return true;
					};
					logger.info("Response: " + response.getStatus() + " Response Boby: " + response.readEntity(String.class));	
					return false;
				}
			 catch (Exception exc) {
				 logger.severe(exc.toString());
				 exc.printStackTrace();
				
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

				String host = System.getProperty("proxyHost");
				String port = System.getProperty("proxyPort");
				
				ClientBuilder clientBuilder = ClientBuilder.newBuilder();
				if(host != null && !host.isEmpty() && port !=null && !port.isEmpty()) {
					
					ClientConfig config = new ClientConfig();
					config = config.property(ClientProperties.PROXY_URI, host+":"+port);  
					clientBuilder = clientBuilder.withConfig(config);
					
				}
				
				clientBuilder = clientBuilder.sslContext(getSSLContext());
				clientBuilder.connectTimeout(10, TimeUnit.SECONDS);
				clientBuilder.readTimeout(10, TimeUnit.SECONDS);
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

		public byte[] getBody() {
			return body;
		}

		public void setBody(byte[] body) {
			this.body = body;
		}

		public String getToken(String clientID, String clientSecret, String tenantId, String resource, String urlToken) {
			{
				Client client = null;
				Response response = null;
			
				try {
					  
					String url = urlToken;

					if (url == null) {
						
						return null;
					}

					if (url.startsWith("https")) {
						client = getRestSSLClient(MediaType.APPLICATION_XML + ";charset=utf-8" + ";charset=utf-8", null);
					} else
						client = getRestHttpClient(MediaType.APPLICATION_XML + ";charset=utf-8" + ";charset=utf-8", null);


					Form form = new Form();
					form.param("grant_type", "client_credentials");
					form.param("client_id", clientID);
					form.param("client_secret", clientSecret);
					form.param("resource", resource);
					
					
					WebTarget webTarget = client.target(url);

					
					Builder builder = webTarget.request();
										
					logger.info("POST to URL: " + url);
					
			
					response = builder.post(Entity.form(form), Response.class);
					
					String b = response.readEntity(String.class); 
					
					ObjectMapper Obj = new ObjectMapper();
					Token token = new Token();
					
					
					
					logger.info("Response: " + response.getStatus() + " Response Boby: " + b);
					
					
							
					
					
					if (response.getStatus() == 200 || response.getStatus() == 202) {
						
						try {
							token = Obj.readValue(b, Token.class);
			           	}catch(Exception e) {
			           		
			           		String exceuted ="Parseing JSON ERROR";
			           		logger.severe(exceuted);
			           		logger.severe(e.toString());
			           		
			           	}
						
						
						
						
						
						return token.getAccess_token();
						};
						return null;
					}
				 catch (Exception exc) {
					 logger.severe(exc.toString());
					 exc.printStackTrace();
					
				} finally {
					if (response != null)
						response.close();
					
					
					if (client != null)
						client.close();
								
				}
			return null;
		}
	}
	
	
	
	
	
	


	
	
	
}

















