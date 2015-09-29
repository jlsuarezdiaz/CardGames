/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.Uno.Model;

import Model.UnoCard;
import java.util.ArrayList;
import Model.UnoSuit;

/**
 *
 * @author Javier
 */
public class Player {
    private String name;
    private ArrayList<UnoCard> playerHand;
    
    public Player(String name){
        this.name = name;
        this.playerHand = new ArrayList<>();
    }
    
    public Player(Player p){
        this.name = p.name;
        this.playerHand = p.playerHand;
    }
    
    public void receiveCard(UnoCard c){
        playerHand.add(c);
    }
    public void discardCard(UnoCard c){
        playerHand.remove(c);
    }
    
    
    
    
    
}
