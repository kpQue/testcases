package org.kp.msg.test;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

 
 
public class TestXmpp {
 
    public static void main(String[] args){
        try {
            AbstractXMPPConnection conn = new XMPPTCPConnection("sikangping", "20090806", "gmail.com");
 
            conn.connect();
            conn.login();
 
            ChatManager chatManager = ChatManager.getInstanceFor(conn);
            
            Chat chat = chatManager.createChat("renee.ruiguo@gmail.com");
            chat.sendMessage("Hello, world!");
            chat.close();
 
            conn.disconnect();
        } catch(Exception e) {
            System.err.println(e);
        }
    }
}