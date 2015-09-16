/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.SotaCabrona.Model;

import Model.FrenchCard;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Sota Cabrona player.
 * @author Juan Luis
 */
public class Player {
    private ArrayList<FrenchCard> myCards;
    
    private String name;
    
    protected SotaEnvironment game;
    
    public Player(String name, SotaCabrona game){
        myCards = new ArrayList();
        this.name = name;
        this.game = new SotaEnvironment(game);
    }
    
    public void receiveCard(FrenchCard c){
        myCards.add(c);
    }
    
    public void takeHeap(ArrayList<FrenchCard> cards){
        myCards.addAll(cards);
        cards = new ArrayList();
    }
    
    public void shuffleMyCards(){
        Collections.shuffle(myCards);
    }
    
    public void dropNextCard(){
        if(isMyTurn()){
            if(!myCards.isEmpty())
                game.dropCard(myCards.remove(0));
        }
        else{
            punish();
        }
    }
    
    public boolean touchHeap(){
        if(game.touchHeap()){
            takeHeap(game.getHeap());
            return true;
        }
        else{
            punish();
            return false;
        }
    }
    
    public void punish(){
        ArrayList<FrenchCard> punish = new ArrayList();
        int punishSize = Integer.min(5, myCards.size());
        for(int i = 0; i < punishSize; i++){
            punish.add(myCards.remove(0));
        }
        game.putOnHeap(punish);
    }
    
    public boolean isMyTurn(){
        return game.isTurnOf(this);
    }

    public String getName() {
        return name;
    }

    public ArrayList<FrenchCard> getMyCards() {
        return myCards;
    } 
    
}
