package trax.aero.client;

import java.io.StringWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Logger;

import javax.ejb.Stateless;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import trax.aero.inbound.MT_TRAX_SND_I10_4110;
import trax.aero.logger.LogManager;

public class ServiceClient {
	
	final String ID = System.getProperty("Post_ID");
	final String Password = System.getProperty("Post_Password");
	Logger logger = LogManager.getLogger("MaterialDemand_I10");
	
	public boolean callSap(MT_TRAX_SND_I10_4110 data) {
		   final int MAX_ATTEMPTS = Integer.parseInt(System.getProperty("MD_MAX_ATTEMPTS", "-1"));
		   final long RETRY_INTERVAL = Long.parseLong(System.getProperty("MD_RETRY_INTERVAL", "180")) * 1000;

		   int attempt = 1;
		   while (MAX_ATTEMPTS == -1 || attempt <= MAX_ATTEMPTS) {
		       Client client = null;
		       Response response = null;

		       try {
		           String url = System.getProperty("MD_url");
		           if (url == null) {
		               // Log XML content when URL is null 
		               logXmlContent(data, attempt);
		               logger.warning("Attempt " + attempt + ": URL is null. Retrying...");
		               Thread.sleep(RETRY_INTERVAL);
		               attempt++;
		               continue;
		           }

		           final HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder()
		               .credentials(System.getProperty("Post_ID"), System.getProperty("Post_Password"))
		               .build();
		           String auth = ID + ":" + Password;

		           client = url.startsWith("https") 
		               ? getRestSSLClient(MediaType.APPLICATION_XML + ";charset=utf-8", null)
		               : getRestHttpClient(MediaType.APPLICATION_XML + ";charset=utf-8", null);

		           client = client.register(feature);
		           WebTarget webTarget = client.target(url);
		           Builder builder = webTarget.request()
		               .header("Content-type", MediaType.APPLICATION_XML + ";charset=utf-8")
		               .header("Accept", MediaType.APPLICATION_XML + ";charset=utf-8")
		               .header(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.getEncoder().encode(auth.getBytes())));
		           
		           // Log XML before each attempt
		           logXmlContent(data, attempt);
		           
		           response = builder.post(Entity.entity(data, MediaType.APPLICATION_XML + ";charset=utf-8"));

		           logger.info("Response: " + response.getStatus() + " Response Body: " + response.readEntity(String.class));

		           if (response.getStatus() == 200 || response.getStatus() == 202) {
		               logger.info("Successful response on attempt " + attempt);
		               return true;
		           }
		           
		           logger.warning("Attempt " + attempt + ": Status code " + response.getStatus());
		           if (MAX_ATTEMPTS != -1 && attempt >= MAX_ATTEMPTS) {
		               logger.severe("Maximum retry attempts reached.");
		               break;
		           }
		           
		           logger.info("Retrying in " + (RETRY_INTERVAL / 1000) + " seconds...");
		           Thread.sleep(RETRY_INTERVAL);
		           attempt++;
		           
		       } catch (Exception exc) {
		           logger.severe("Attempt " + attempt + " failed: " + exc.getMessage());
		           exc.printStackTrace();
		           
		           if (MAX_ATTEMPTS != -1 && attempt >= MAX_ATTEMPTS) {
		               logger.severe("Maximum retry attempts reached.");
		               break;
		           }
		           
		           try {
		               logger.info("Retrying in " + (RETRY_INTERVAL / 1000) + " seconds...");
		               Thread.sleep(RETRY_INTERVAL);
		           } catch (InterruptedException e) {
		               Thread.currentThread().interrupt();
		           }
		           attempt++;
		       } finally {
		           if (response != null) response.close();
		           if (client != null) client.close();
		       }
		   }
		   
		   logger.severe("Failed to send data after " + (MAX_ATTEMPTS == -1 ? "infinite" : MAX_ATTEMPTS) + " attempts.");
		   return false;
		}

		private void logXmlContent(MT_TRAX_SND_I10_4110 data, int attempt) {
		   try {
		       JAXBContext jc = JAXBContext.newInstance(MT_TRAX_SND_I10_4110.class);
		       Marshaller marshaller = jc.createMarshaller();
		       marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		       StringWriter sw = new StringWriter();
		       marshaller.marshal(data, sw);
		       logger.info("XML Content for Attempt " + attempt + ": " + sw.toString());
		   } catch (Exception e) {
		       logger.severe("Error logging XML content: " + e.getMessage());
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
}
