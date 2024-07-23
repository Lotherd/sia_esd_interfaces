package trax.aero.utils;

import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.ws.rs.client.Invocation.Builder;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import trax.aero.logger.LogManager;
import trax.aero.pojo.INT6_SND;
import trax.aero.pojo.OperationSND;
import trax.aero.pojo.OrderSND;

public class Poster {

	final String ID = System.getProperty("Post_ID");
	final String Password = System.getProperty("Post_Password");
	Logger logger = LogManager.getLogger("ImportTC_MHR");
	private String body = null;
	
	private Map<String, String> requestStates = new HashMap<>();
	
	public boolean post(INT6_SND data, String URL) {
	    final int MAX_RETRIES = 3;  // Número máximo de reintentos
	    final int RETRY_DELAY_MS = 5 * 60 * 1000;  // 5 minutos en milisegundos
	    int attempt = 0;
	    boolean success = false;

	    if (!isValidURL(URL)) {
	        logger.severe("Invalid URL: " + URL);
	        return false;
	    }

	    while (attempt < MAX_RETRIES && !success) {
	        attempt++;
	        Client client = null;
	        Response response = null;

	        try {
	            final HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder()
	                    .credentials(System.getProperty("Post_ID"), System.getProperty("Post_Password")).build();
	            String auth = ID + ":" + Password;

	            if (URL == null || URL.isEmpty()) {
	                return false;
	            }

	            if (URL.startsWith("https")) {
	                client = getRestSSLClient(MediaType.APPLICATION_XML + ";charset=utf-8", null);
	            } else {
	                client = getRestHttpClient(MediaType.APPLICATION_XML + ";charset=utf-8", null);
	            }

	            client = client.register(feature);

	            WebTarget webTarget = client.target(URL);
	            Builder builder = webTarget.request();

	            builder = builder.header("Content-type", MediaType.APPLICATION_XML + ";charset=utf-8");
	            builder = builder.header("Accept", MediaType.APPLICATION_XML + ";charset=utf-8");
	            builder = builder.header(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.getEncoder().encode(auth.getBytes())));

	            String requests = "";
	            for (OrderSND r : data.getOrder()) {
	                for (OperationSND req : r.getOperations()) {
	                    requests = requests + " (Task Card: " + req.getTcNumber() + ", WO: " + r.getTraxWO() + "),";
	                }
	            }

	            String requestKey = URL + data.hashCode();
	            if ("accepted".equals(requestStates.get(requestKey))) {
	                logger.info("Request already accepted, skipping: " + requests);
	                return true;
	            }

	            logger.info("POSTING Requests: " + requests + " to URL: " + URL);
	            body = null;

	            JAXBContext jc = JAXBContext.newInstance(INT6_SND.class);
	            Marshaller marshaller = jc.createMarshaller();
	            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	            StringWriter sw = new StringWriter();
	            marshaller.marshal(data, sw);
	            String xmlContent = sw.toString();

	            response = builder.post(Entity.entity(xmlContent, MediaType.APPLICATION_XML + ";charset=utf-8"));
	            body = response.readEntity(String.class);
	            logger.info("Response: " + response.getStatus() + " Response Body: " + body);

	            if (response.getStatus() == 200 || response.getStatus() == 202) {
	                requestStates.put(requestKey, "accepted");
	                success = true;
	            } else if (response.getStatus() == 500 || response.getStatus() == 404) {
	                logger.warning("Communication failure, attempt " + attempt + " of " + MAX_RETRIES);
	                if (attempt < MAX_RETRIES) {
	                    logger.info("Waiting for " + (RETRY_DELAY_MS / 1000 / 60) + " minutes before retrying...");
	                    Thread.sleep(RETRY_DELAY_MS);  // Esperar 5 minutos antes de reintentar
	                } else {
	                    body = null;
	                    return false;
	                }
	            } else {
	                body = null;
	                return false;
	            }
	        } catch (Exception exc) {
	            logger.severe(exc.toString());
	            if (attempt >= MAX_RETRIES) {
	                return false;
	            }
	        } finally {
	            if (response != null)
	                response.close();
	            if (client != null)
	                client.close();
	        }
	    }

	    return success;
	}


	private boolean isValidURL(String urlStr) {
	    try {
	        new URL(urlStr);
	        return true;
	    } catch (MalformedURLException e) {
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
			} catch(NoSuchAlgorithmException exc) {
				exc.printStackTrace();
			} try {
				TraxX509TrustManager trustMger = new TraxX509TrustManager();
				context.init(null, new TrustManager[] { trustMger }, new SecureRandom());
			} catch(KeyManagementException e) {
				e.printStackTrace();
			}
			
			return context;
		}
		
		private Client getRestHttpClient(String accept, String contentType) {
			Client client = null;
			
			try {
				client = ClientBuilder.newClient();
				if(contentType != null)
					client.property("Content-Type", contentType);
				if(accept != null)
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
