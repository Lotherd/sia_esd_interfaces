package trax.aero.utils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import com.ibm.msg.client.wmq.compat.jms.internal.JMSC;

public class MqUtilities {
	
	private static final String HOST = System.getProperty("CapabilityRating_Host");// Host name or IP address
	private static final int PORT = Integer.valueOf(System.getProperty("CapabilityRating_Port")).intValue(); // Listener port for your queue manager
    private static final String CHANNEL = System.getProperty("CapabilityRating_Channel"); // Channel name
    private static final String QMGR = System.getProperty("CapabilityRating_qmgr"); // Queue manager name
    private static final String APP_USER = System.getProperty("CapabilityRating_user"); // User name that application uses to connect to MQ
    private static final String APP_PASSWORD = System.getProperty("CapabilityRating_password"); // Password that the application uses to connect to MQ
    //private static final String QUEUE_NAME_SENDER = System.getProperty("CapabilityRating_send"); // Queue that the application uses to put and get messages to and from
    private static final String QUEUE_NAME_RECEIVE = System.getProperty("CapabilityRating_receive");
    private static final String CIPHER_SUITE = System.getProperty("CapabilityRating_CipherSuite"); // Cipher suite for SSL/TLS
    
    public static MQQueueConnectionFactory createMQQueueConnectionFactory() throws JMSException {
    	MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
	    mqQueueConnectionFactory.setHostName(HOST);
	    mqQueueConnectionFactory.setChannel(CHANNEL);//communications link
	    mqQueueConnectionFactory.setPort(PORT);
	    mqQueueConnectionFactory.setQueueManager(QMGR);//service provider 
        mqQueueConnectionFactory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
       
        mqQueueConnectionFactory.setStringProperty(WMQConstants.USERID, APP_USER); 
        mqQueueConnectionFactory.setStringProperty(WMQConstants.PASSWORD,APP_PASSWORD );
        mqQueueConnectionFactory.setStringProperty(WMQConstants.WMQ_SSL_CIPHER_SUITE, CIPHER_SUITE); // Set the cipher suite
	    return mqQueueConnectionFactory;
    }
    
    public static String receiveMqText() throws JMSException {
    	QueueReceiver queueReceiver = null;
		QueueConnection queueConnection = null;
		QueueSession queueSession = null;
		
		try {
            /*MQ Configuration*/
			MQQueueConnectionFactory mqQueueConnectionFactory = MqUtilities.createMQQueueConnectionFactory();
            /*Create Connection */
            queueConnection = mqQueueConnectionFactory.createQueueConnection();
            queueConnection.start();

            /*Create session */
            queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            /*Create response queue */
            Queue queue = queueSession.createQueue(QUEUE_NAME_RECEIVE);
            
            /*Within the session we have to create queue reciver */
             queueReceiver = queueSession.createReceiver(queue);


            /*Receive the message from*/
            Message message = queueReceiver.receive(60*1000);
            if(message != null ) {
            	String responseMsg = ((TextMessage) message).getText();
                System.out.println("responseMsg "+ responseMsg);
               
                return responseMsg;
            }
		} catch (JMSException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
             queueReceiver.close();
             queueSession.close();
             queueConnection.close();
        }
		return null;
    }
}
