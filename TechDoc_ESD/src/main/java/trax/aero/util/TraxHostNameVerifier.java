package trax.aero.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class TraxHostNameVerifier implements HostnameVerifier {

	@Override
	public boolean verify(String hostname, SSLSession session) {
		
		return true;
	}

}
