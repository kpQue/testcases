package org.kp.msg.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;

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

public class AppTestClient {
	public static String username = "apptest";
	public static String password = "apptest";
	public static String domain = "xmpp.jp";
	
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
	        
		  XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
		  //.setUsernameAndPassword(username, password)
		  .setCustomSSLContext(sc)
		  .setServiceName(domain)
		  .setHost(domain)
		  .setHostnameVerifier(new HostnameVerifier(){
			  public boolean verify(String arg0, SSLSession arg1){
				  	return true;
			  }}
		   )
		  .setPort(5222)
		  .setDebuggerEnabled(true)
		  .build();

		AbstractXMPPConnection conn2 = new XMPPTCPConnection(config);
		conn2.connect();
		conn2.login(username,password);
		
		ChatManager chatmanager = ChatManager.getInstanceFor(conn2);
		Chat newChat = chatmanager.createChat("kptest@xmpp.jp");

		newChat.addMessageListener(new appMsgListener());
		newChat.sendMessage("Hello kp");
		Thread.sleep(10000);
		
		
		conn2.disconnect();
		
		System.out.println(conn2);
	}
}

class appMsgListener implements ChatMessageListener{

	@Override
	public void processMessage(Chat arg0, Message msg) {
		System.out.println("Received message: " + msg);
		
	}
	
}
