/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.SotaCabrona.Model;

import Model.FrenchCard;
import java.util.ArrayList;

/**
 *
 * @author Juan Luis
 */
public class SotaEnvironment {
    private SotaCabrona sota;
    
    public SotaEnvironment(SotaCabrona s){
        this.sota = s;
    }
    
    public ArrayList<FrenchCard> getHeap(){
        return sota.getHeap();
    }
    
    public ArrayList<FrenchCard> takeHeap(){
        return sota.takeHeap();
    }
    
    public boolean touchHeap(){
        return sota.touchHeap();
    }
    
    public void dropCard(FrenchCard c){
        sota.dropCard(c);
    }
    
    public boolean isTurnOf(Player p){
        return p == sota.getCurrentPlayer();
    }
    
    public void putOnHeap(ArrayList<FrenchCard> cards){
        sota.putOnHeap(cards);
    }
    
    public boolean isPlaying(){
        return sota.isPlaying();
    }
    
    public boolean isEndOfGame(){
        return sota.isEndOfGame();
    }
    
    public void nextIfNoCards(){
        sota.nextIfNoCards();
    }
}
