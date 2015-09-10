/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.Poker.Model;

import java.util.ArrayList;

/**
 *
 * @author Juan Luis
 */
public class AllInPot {
    private int pot;
    
    private ArrayList<PokerPlayer> playersInvolved;

    
    public AllInPot(){
        this.pot = 0;
        this.playersInvolved = new ArrayList();
    }
    
    public void add(int pot, PokerPlayer player){
        this.pot += pot;
        playersInvolved.add(player);
    }

    public ArrayList<PokerPlayer> getPlayersInvolved() {
        return playersInvolved;
    }

    public int getPot() {
        return pot;
    }

    public void setPlayersInvolved(ArrayList<PokerPlayer> playersInvolved) {
        this.playersInvolved = playersInvolved;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }
    
    
    
}
