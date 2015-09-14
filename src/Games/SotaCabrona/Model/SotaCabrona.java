/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.SotaCabrona.Model;

import Model.FrenchCard;
import Model.FrenchDeck;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Juan Luis
 */
public class SotaCabrona {
    
    private ArrayList<FrenchCard> cardHeap;
    
    private ArrayList<Player> players;
    
    private Player currentPlayer;
    
    private int currentPlayerIndex;
    
    private int remainingCardsCount;
    
    private Player myPlayer;
    
    private void dealCards(){
        FrenchDeck deck = new FrenchDeck();
        deck.shuffle();
        int i = currentPlayerIndex;
        while(!deck.isEmpty()){
            players.get(i).receiveCard(deck.nextCard());
            i = (i+1)%players.size();
        }
    }
    
    public SotaCabrona(String[] playerNames){
        players = new ArrayList();
        for(String s : playerNames){
            players.add(new Player(s, this));
        }
        this.currentPlayerIndex = new Random().nextInt(players.size());
        this.currentPlayer = players.get(currentPlayerIndex);
        this.myPlayer = players.get(0);
        
        dealCards();
        
        cardHeap = new ArrayList();
        remainingCardsCount = 0;
    }
    
    public ArrayList<FrenchCard> getHeap(){
        return cardHeap;
    }
    
    private boolean isSandwich(){
        int size = cardHeap.size();
        if(size < 3) return false;
        else return (cardHeap.get(size-1).isValue(cardHeap.get(size-3).getValue()));
    }
    
    private boolean isSameValue(){
        int size = cardHeap.size();
        if(size < 2) return false;
        else return (cardHeap.get(size-1).isValue(cardHeap.get(size-2).getValue()));
    }
    
    public boolean touchHeap(){
        return isSandwich() || isSameValue();
    }
    
    public void dropCard(FrenchCard c){
        cardHeap.add(c);
    }
}
