/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Model;

import cardgames.GameSelection;
import java.util.ArrayList;

/**
 *
 * @author Juan Luis
 */
public abstract class GameMonitor {
    private GameSelection game;
    
    GameMonitor(GameSelection game){
        this.game=game;
    }
    
    public abstract ArrayList<Processor> getPlayers();
}
