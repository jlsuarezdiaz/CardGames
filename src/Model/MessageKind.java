/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Model;

/**
 *
 * @author Juan Luis
 */
public enum MessageKind {
    //Basic commands:
    HELO, OK, ERR,
    
    //"I'm alive"
    IMALIVE,
    //Disconnect a user.
    DISC,
    
    //Starting commands:
    PLAY, //Starts a new game.
    JOIN, //Request for joining an existing game.
}
