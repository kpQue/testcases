package org.kp.msg.test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

 
 
public class TestXmpp {
 
    public static void main(String[] args){
    	test();
    }
    
    public static void test(){
		try {
		    XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		    config.setServerURL(new URL("http://52.11.76.63:4560/RPC2"));
		    XmlRpcClient client = new XmlRpcClient();
		    client.setConfig(config);

		    /* Command string */
		    String command = "register";

		    /* Parameters as struct */
		    Map<String,String> struct = new HashMap<String,String>();
		    struct.put("user", "testreg");
		    struct.put("host", "localhost");
		    struct.put("password", "testreg");

		    Object[] params = new Object[]{struct};
		    Map response = (Map) client.execute(command, params);
		    int res = (int) response.get("res");
		    
		    if(res == 0){
		    	System.out.println("success !!");
		    }
		    else{
		    	System.out.println("fail !!");
		    }

		} catch (Exception e) {
			System.out.println(e);
		}
    }
}