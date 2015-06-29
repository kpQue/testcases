package org.kp.msg.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;

public class SmackTest {
	public static String username = "admin";
	public static String password = "admin";
	public static String server = "52.11.76.63";
	public static String domain = "localhost";
	public static String sendTo = "apptest@localhost";
	
	public static void main(String[] args){
		try {
			testConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testConnection() throws SmackException, IOException, XMPPException, NoSuchAlgorithmException, KeyManagementException, InterruptedException{
		// Create a connection to the jabber.org server on a specific port.
		 
		//SSL context
		 SSLContext sc = SSLContext.getInstance("TLS");
		 TrustManager tm = new X509TrustManager() {

	            public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
	                throws CertificateException {
	            }


	            public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
	                throws CertificateException {
	            }


	            public X509Certificate[] getAcceptedIssuers() {
	                return new X509Certificate[0];
	            }
	        };
	        
	      sc.init(null, new TrustManager[] {tm}, null);
	     
	      //xmpp config
		  XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
		  .setUsernameAndPassword(username, password)
		  .setCustomSSLContext(sc)
		  .setServiceName(domain)
		  .setHost(server)
		  .setHostnameVerifier(new HostnameVerifier(){
			  public boolean verify(String arg0, SSLSession arg1){
				  	return true;
			  }}
		   )
		  .setPort(5222)
		  .setDebuggerEnabled(true)
		  .build();

		//xmpp connection
		AbstractXMPPConnection conn2 = new XMPPTCPConnection(config);
		conn2.connect();
		conn2.login(username, password);
		
		//Chat Manager
		ChatManager chatmanager = ChatManager.getInstanceFor(conn2);
		
		//Chat
		Chat newChat = chatmanager.createChat(sendTo);
		//Chat newChat = chatmanager.createChat("sikangping@gmail.com");

		newChat.addMessageListener(new customMsgListener());
		newChat.sendMessage("Hello app");	
		
		System.out.println("Msg sent !!");
		//Thread.sleep(60000);

		String newUser = "testreg";
		String pw = "testreg";
		
		AccountManager accountManager=AccountManager.getInstance(conn2);
		try {
		    //accountManager.createAccount(newUser,pw);
			System.out.println(accountManager.supportsAccountCreation());
			  Map<String,String> attributes=new HashMap<String,String>();
			  attributes.put("username",newUser);
			  attributes.put("password",pw);
		    accountManager.createAccount(newUser, pw, attributes);
		} catch (XMPPException e1) {
		    System.out.println(e1.toString());
		}
		
		System.out.println("New User Registered !!");
		
		conn2.disconnect();
		System.out.println(conn2);
	}
}

class customMsgListener implements ChatMessageListener{

	@Override
	public void processMessage(Chat arg0, Message msg) {
		System.out.println("Received message: " + msg.getBody());
		
	}
	
}
