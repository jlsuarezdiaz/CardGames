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
        if(!myCards.isEmpty())
            game.dropCard(myCards.remove(0));
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
        for(int i = 1; i <= 5; i++)
            dropNextCard();
    }
    
    protected boolean isMyTurn(){
        return game.isTurnOf(this);
    }

    public String getName() {
        return name;
    }
    
    
}
