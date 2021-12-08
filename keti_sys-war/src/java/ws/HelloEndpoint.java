/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author eroot
 */
@ServerEndpoint("/hello")
public class HelloEndpoint {
    
    private Session session;
    private static final Logger LOG = Logger.getLogger(HelloEndpoint.class.getName());
    
    
    @OnOpen
    public void init(Session session){
        this.session=session;
    }
    
    @OnMessage
    public void onMessage(String message){
        LOG.log(Level.INFO, "KETI WS Message {0}", message);
        if(this.session != null && this.session.isOpen()){
            try {
                this.session.getBasicRemote().sendText("From server : "+message);
            } catch (IOException ex) {
                Logger.getLogger(HelloEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
