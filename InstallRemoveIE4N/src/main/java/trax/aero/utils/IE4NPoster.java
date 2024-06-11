package trax.aero.utils;

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
import trax.aero.pojo.MT_TRAX_SND_I43_4076_REQ;



public class IE4NPoster 
{
	final String ID = System.getProperty("Post_ID");
	final String Password = System.getProperty("Post_Password");
	Logger logger = LogManager.getLogger("InstallRemove_I20");
	private String body = null;
	
	public boolean postIE4N(MT_TRAX_SND_I43_4076_REQ data , String URL)
	{
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
				
				
				
				logger.info("POSTING Reference Order: " + data.getReference_Order() + " to URL " + URL);
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

















