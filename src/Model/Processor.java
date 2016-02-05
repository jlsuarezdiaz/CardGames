/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Model;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread processor
 * @author 
 */
public class Processor extends Thread{
    //Referencia al servidor
    private ServerData serverData;
    
    // Referencia a un socket para enviar/recibir las peticiones/respuestas
    private Socket socketServicio;
    // stream de lectura (por aquí se recibe lo que envía el cliente)
    //private BufferedReader inputStream;
    private Scanner inputStream;
    // stream de escritura (por aquí se envía los datos al cliente)
    private OutputStreamWriter outputStream;
    // Last update of client.
    private Date lastUpdate;
    
    /**
     * Indicates whether this processor thread is still alive.
     */
    private boolean running;
    
    // Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
    public Processor(Socket socketServicio,ServerData s) {
        this.socketServicio=socketServicio;
        this.serverData = s;
        this.lastUpdate = new Date();
            
            // Obtiene los flujos de escritura/lectura
        try{
            inputStream=new Scanner(socketServicio.getInputStream(),"UTF-8");
            outputStream=new OutputStreamWriter(socketServicio.getOutputStream(),"UTF-8");
            inputStream.useDelimiter(String.valueOf(ServerData.FS));
        }
        catch(Exception ex){
            System.err.println("Error al crear el flujo E/S: "+ex.getMessage());
        }
        this.running=true;
    }
    
    public void run(){
        process();
    }
    
    public void sendMessage(MessageKind msg, String[] args){
        try {
            outputStream.write(new Message(msg,args).toMessage());
            outputStream.flush();
        } catch (Exception ex) {
            System.err.println("Error al enviar mensaje: "+ex.getMessage());
        }
    }
    
    public void receiveMessage(MessageKind msg, String[] args){
        while(!inputStream.hasNext() && running){}
    }
    
    public void process(){
        MessageKind receivedMsg = null;
        String[] info = null;
        int remoteId = -1;
        
        //Nueva conexión. Registramos el usuario y enviamos el mensaje de saludo.
        System.out.println("["+Server.getDateFormat().format(new Date())+"] New connection.");
        remoteId =  serverData.register(this);
        
        if(remoteId == -1)
            sendMessage(MessageKind.HELO,new String[]{Integer.toString(remoteId)});
        else
            sendMessage(MessageKind.ERR,new String[]{"No es posible acceder en estos momentos. Inténtelo más tarde."});
        
        do{
            try {
                //Leemos un nuevo mensaje.
                receiveMessage(receivedMsg,info);
                if(!running) break;
                
                //Acción para cada tipo de mensaje.
                switch(receivedMsg){
                    case PLAY: // {PLAY,date,Game,Name}
                        
                        break;
                    case JOIN:
                        
                        break;
                    case IMALIVE:
                        this.lastUpdate = new Date();
                        break;
                    default:
                        System.out.println("["+info[1]+"] Error: Wrong command received.");
                        break;
                }
                
            }
            catch(Exception ex){
                System.out.println("Error al procesar: " + ex.getMessage());
            }
        }
        while(true);
    }
    
    public void kill(){
        try{
            socketServicio.close();
        }
        catch(Exception ex){}
        this.running=false;
    }
    
    public Socket getSocket(){
        return socketServicio;
    }

    public OutputStreamWriter getOutputStream() {
        return outputStream;
    }

    public Scanner getInputStream() {
        return inputStream;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }
}
