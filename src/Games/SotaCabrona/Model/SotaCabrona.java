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
    
    private boolean playing;
    
    private Player heapPlayer;
    
    private void dealCards(){
        FrenchDeck deck = new FrenchDeck();
        deck.removeJokers();
        deck.shuffle();
        int i = currentPlayerIndex;
        while(!deck.isEmpty()){
            players.get(i).receiveCard(deck.nextCard());
            i = (i+1)%players.size();
        }
    }
    
    private void setRemainingCardsCount(){
        remainingCardsCount--;
        switch(cardHeap.get(cardHeap.size()-1).getValue()){
            case "A":
                remainingCardsCount = 4;
                heapPlayer = currentPlayer;
                break;
            case "K":
                remainingCardsCount = 3;
                heapPlayer = currentPlayer;
                break;
            case "Q":
                remainingCardsCount = 2;
                heapPlayer = currentPlayer;
                break;
            case "J":
                remainingCardsCount = 1;
                heapPlayer = currentPlayer;
                break;
            default:
                if(remainingCardsCount < 0) remainingCardsCount = -1;
                heapPlayer = null;
                break;
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
        remainingCardsCount = -1;
        
        playing = false;
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
        if(!playing) return false;
        stop();
        return isSandwich() || isSameValue();
    }
    
    public void dropCard(FrenchCard c){
        play();
        cardHeap.add(c);
        setRemainingCardsCount();
        if(remainingCardsCount == 0){
            heapPlayer.takeHeap(cardHeap);
            heapPlayer = null;
        }
        else if(remainingCardsCount < 0){
            next();
        }
    }
    
    public void stop(){
        playing = false;
    }
    
    public void play(){
        playing = true;
    }
    
    public void next(){
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    
    public void putOnHeap(ArrayList<FrenchCard> cards){
        cardHeap.addAll(0, cards);
    }
    
    
}
