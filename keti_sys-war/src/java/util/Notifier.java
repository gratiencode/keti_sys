/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author eroot
 */
public class Notifier {

    private OnNotifyListener notifyListener;
    public void setNotifyListener(OnNotifyListener listener){
        this.notifyListener=listener;
    }
    
    public void sendNotification(final String s){
        new Thread(new Runnable() {
            @Override
            public void run() {
                notifyNow(s);
            }
        }).start();
    }
    
    private void notifyNow(String s){
        if(this.notifyListener!=null){
            this.notifyListener.onNotify(s);
        }
    }
    
}
