/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.Timer;

/**
 * Main monitor for processor threads.
 * Each game should have its own sub-monitor to compute the game separately.
 * @author Juan Luis
 */
public class ServerData {
    //Delimiters for information in messages
    public static final char US = 0x1F; //Unit separator (each piece of information)
    public static final char RS = 0x1E; //Record separator (groups of same information)
    public static final char GS = 0x1D; //Group separator (groups of complete information)
    public static final char FS = 0x1C; //File separator  (ends a complete message)
    
    /**
     * Max users allowed.
     */
    private static final int MAX_USERS = 28;
    
    private Processor processors[] = new Processor[MAX_USERS];
    
    private int numUsers;
    
    /**
     * Maximum period of inactivity available before removing a user (in s).
     */
    private static final int MAX_INACTIVE_PERIOD = 30;
    
    private Timer userChecker;
    
    /**
     * Period the server checks inactive users.
     */
    private static final int CHECKER_PERIOD = 30000;
    
    /**
     * Game sub-monitors.
     */
    private ArrayList<GameMonitor> gameData;

    
    /**
     * Utility to get time difference between two dates.
     * @param d1
     * @param d2
     * @return 
     */
    private static long getTimeDifference(Date d1, Date d2){
        long diff = d1.getTime() - d2.getTime();
        return diff/1000;
    }
    
    public ServerData(){
        numUsers = 0;
        
        this.userChecker = new Timer(CHECKER_PERIOD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                checkUsers();
            }
        });
        
        this.userChecker.start();
        this.gameData = new ArrayList();
    }


    
    // ---------- GET METHODS ---------- //
    
    public int getNumUsers(){
        return numUsers;
    }
    
    private static int getMAX_USERS() {
        return MAX_USERS;
    }
    
    // ---------- MONITOR METHODS (SYNCHRONIZED) ---------- //

    public synchronized int register(Processor pr) {
        if(numUsers==getMAX_USERS()){
            return -1;
        }
        else{
            for(int i = 0; i < ServerData.getMAX_USERS(); i++){
                if(processors[i] != null){
                    processors[i] = pr;
                    numUsers++;
                }
            }
            return -1;
        }
    }
    
    public synchronized void remove(int id){
        numUsers--;
        processors[id] = null;
    }
    
    public synchronized void checkUsers(){
        Date d = new Date();
        String name = "";
        System.out.println("["+Server.getDateFormat().format(d)+"] USER CHECKING Started.");
        for(int i = 0; i < MAX_USERS; i++){
            if(processors[i] != null &&
               getTimeDifference(d,processors[i].getLastUpdate()) > MAX_INACTIVE_PERIOD){
                //Si un usuario no ha dado señales de vida en cierto tiempo lo eliminamos.
                //sendTo(i, new Message(MessageKind.DISC, null).toMessage());
                processors[i].sendMessage(MessageKind.DISC, null);
                processors[i].kill();
                processors[i] = null;
                numUsers--;
                System.out.println("- USER "+ Integer.toString(i) +" KILLED.");
            }
        }
    }
    
}
