////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class message. Contains all the information relative to a message.
 * @author Juan Luis
 */
public class Message {
        
    /**
     * Message contents.
     */
    private String[] messageData;
    
    /**
     * Indicates de message modality.
     */
    private MessageKind kind;
    
    /**
     * Message date.
     */
    private Date date;
    
    /**
     * Date Format.
     */
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    /**
     * Input/Output delimiter.
     */
    private static final String IO_LIM = "\0";
    
    /**
     * Private method to set message attributes.
     * @param sender
     * @param text
     * @param isPublic 
     */
    private void set(MessageKind kind, String[] msgData){
        this.kind = kind;
        this.date = new Date();
        this.messageData=msgData;
    }
    
    /**
     * Default constructor.
     */
    public Message(){
        set(MessageKind.ERR,null);
    }
    /**
     * Constructor.
     * @param sender
     * @param text
     * @param isPublic 
     */
    public Message(MessageKind kind,String[] msgData){
        set(kind,msgData);
    }
    
    /**
     * Constructor. By default, a message is public.
     * @param sender
     * @param text 
     */
/*    public Message(String sender, String text){
        set(sender,text,MessageKind.PUBLIC);
    }*/
    
    /**
     * 
     * @return sender 
     */
/*    public String getSender(){
        return sender;
    }*/
    
    /**
     * 
     * @return text. 
     */
/*    public String getText(){
        return text;
    }*/
    
    /**
     * 
     * @return isPublic
     */
    public MessageKind getKind(){
        return kind;
    }
    
    /**
     * 
     * @return date 
     */
    public Date getDate(){
        return date;
    }
    
    public String[] getMessageData(){
        return messageData;
    }
    
    /**
     * 
     * @return Date format. 
     */
    public static DateFormat getDateFormat(){
        return df;
    }
    

 
    
    public String toMessage(){
        String str = kind.toString()+ServerData.GS+getDateFormat().format(getDate())+ServerData.GS;
        if(messageData!=null)
            for(String s:messageData){
                str += s+ServerData.GS;
            }
        return str+ServerData.FS;
    }
    

}
