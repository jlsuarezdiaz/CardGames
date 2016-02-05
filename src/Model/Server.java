/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Model;

import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Server's main class
 * @author Juan Luis
 */
public class Server {
    private static final String configName = ".configs";
    
    //Puerto de escucha
    private static final int port = readPort();
    
    private static ServerSocket serverSocket = null;
    
    /**
     * Date Format.
     */
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    
    private static final int readPort(){
        int rPort = 8928; //Default port.
                String rHost = "localhost"; //Default host.
        File file = new File(configName);
        if(file.exists()){
            try{
                String txt = String.join("\n", Files.readAllLines(Paths.get(configName)));
                String[] data = txt.split("\n*(HOST\\s*=\\s*|PORT\\s*=\\s*)");
                rPort = Integer.valueOf(data[1]);
            }
            catch(Exception ex){}
        }
        return rPort;
    }
    
    
    public static void main(String[] args){
        System.out.println(Data.Txt.EDITION + " Server");
        
        System.out.println(Data.Txt.VERSION + "\t\t" + Data.Txt.COPYRIGHT);
        System.out.println("\n");
        
        
        
        try {
            sleep(3000);
        } catch (InterruptedException ex) {}
        
        //Inicialización de los datos del servidor.
        ServerData serverData = new ServerData();   
        
        //Declaraciones
	ServerSocket serverSocket = null;
        Socket socketServicio = null;
        
        try {
            
            serverSocket=new ServerSocket(port);
            System.out.println("["+Server.getDateFormat().format(new Date())+"] Server started.");
            Server.serverSocket = serverSocket;
            
            do {
                try{
                    socketServicio=serverSocket.accept();
                }
                catch(IOException e){
                    System.err.println("Error: no se pudo aceptar la conexión solicitada");
                }
                Processor processor = new Processor(socketServicio,serverData);
                processor.start();
            }
            while(true);          
        }
        catch(Exception ex){
            System.err.println("Error en Server: " + ex.getMessage());
        }
    }

    public static DateFormat getDateFormat() {
        return df;
    }
    
    
    
}
