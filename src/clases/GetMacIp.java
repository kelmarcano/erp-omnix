/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

/**
 *
 * @author riky1_000
 */
public class GetMacIp {
    String MAC = "";
    String IP = "";
    //int resp = Integer.parseInt("");
        
    
    //public void GetMacIp(){
    public Object[][] GetMacIp(){
        Object MacIp[][]= null;
        MacIp = new Object[1][2];
                
        
        System.out.println("IP MAC: " + MacIp);
        
        InetAddress ip;  
        try {
		ip = InetAddress.getLocalHost();
		System.out.println("Current IP address : " + ip.getHostAddress());
 
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);

		byte[] mac = network.getHardwareAddress();
                StringBuilder sb = new StringBuilder();

                if(MacIp==null){
                   for (int i = 0; i < mac.length; i++) {
                                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));	
                        }      
                }
                    MAC = sb.toString();
                    IP = ip.getHostAddress();

                    MacIp[0][0] = MAC;
                    MacIp[0][1] = IP;
        }
        catch (UnknownHostException e) {
		e.printStackTrace();
	} 
        catch (SocketException e){
		e.printStackTrace();
	}
        
        return MacIp;
    }             
}