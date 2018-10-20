/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileserver;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author Prashant
 */
public class FileServer {

   public  static int SOCKET_PORT = 13267;
    
    public static void main(String[] args) throws IOException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        FileWriter fw = null;
        ServerSocket servsock = null;
        Socket sock = null;
        BufferedWriter bw = null;
        PrintWriter out = null;
        SOCKET_PORT = Integer.parseInt(args[0]);
        // path of shared resource
        String filePath = args[1];
        try 
        {
            servsock = new ServerSocket(SOCKET_PORT);
            System.out.println("File server is running on "+SOCKET_PORT);
            while (true) 
            {
                System.out.println("Waiting...");
                try 
                {
                    sock = servsock.accept();
                    DataInputStream dis = new DataInputStream(sock.getInputStream());
                    byte[] buffer = new byte[4096];
                    int read = dis.read(buffer, 0,4096);
                    String data = new String(buffer,0,read);
                    System.out.println("data = " + data);
                    
                    fw = new FileWriter(filePath, true);
                    
                    bw = new BufferedWriter(fw);
                    out = new PrintWriter(bw);
                    out.print(data);
                    out.close();
                   // System.out.println("Done.");
                }
                finally 
                {
                    if (bis != null) bis.close();
                    if (os != null) os.close();
                    if (sock!=null) sock.close();
                    if (fw != null) fw.close();
                }
            }
        }
        finally 
        {
            if (servsock != null) try {
                servsock.close();
            } catch (IOException ex) {
                
            }
        }
    }
    
}
