/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.SotaCabrona.Model;

import Model.FrenchCard;
import Model.FrenchDeck;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    private boolean winHeapState;
    
    private Player winner;
    
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
                next();
                break;
            case "K":
                remainingCardsCount = 3;
                heapPlayer = currentPlayer;
                next();
                break;
            case "Q":
                remainingCardsCount = 2;
                heapPlayer = currentPlayer;
                next();
                break;
            case "J":
                remainingCardsCount = 1;
                heapPlayer = currentPlayer;
                next();
                break;
            default:
                if(remainingCardsCount < 0) remainingCardsCount = -1;
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
        winHeapState = false;
        
        winner = null;
        
    }
    
    public void setCPUPlayers(ArrayList<Player> players, int reflexesRate, int speedRate, int errorRate){
        for(int i = 0; i < this.players.size(); i++){
            if(players.contains(this.players.get(i))){
                this.players.set(i, new CPUPlayer(this.players.get(i), reflexesRate, speedRate, errorRate));
            }
        }
        this.currentPlayer = this.players.get(currentPlayerIndex);
    }
    
    public void setUsualCPUPlayers(int reflexesRate, int speedRate, int errorRate){
        this.myPlayer = players.get(0);
        ArrayList<Player> players = (ArrayList<Player>)this.players.clone();
        players.remove(myPlayer);
        setCPUPlayers(players, reflexesRate, speedRate, errorRate);
    }
    
    public ArrayList<FrenchCard> getHeap(){
        return cardHeap;
    }
    
    public ArrayList<FrenchCard> takeHeap(){
        ArrayList<FrenchCard> heap = cardHeap;
        cardHeap = new ArrayList();
        return heap;
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
        
        if(isSandwich() || isSameValue()){
            stop();
            remainingCardsCount = -1;
            return true;
        }
        else return false;
    }
    
    public void dropCard(FrenchCard c){
        play();
        cardHeap.add(c);
        setRemainingCardsCount();
        if(remainingCardsCount == 0){
            winHeapState = true;
        }
        else if(remainingCardsCount < 0){
            next();
        }
        
        if(remainingCardsCount >= 0 && currentPlayer.getMyCards().isEmpty()) next();
    }
    
    public void stop(){
        playing = false;
    }
    
    public void play(){
        playing = true;
    }

    public boolean isPlaying() {
        return playing;
    }
    
    public void next(){
        if(isEndOfGame()){
            stop();
        }
        do{
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            currentPlayer = players.get(currentPlayerIndex);
        }while(currentPlayer.getMyCards().isEmpty());
    }
    
    public void nextIfNoCards(){
        if(currentPlayer.getMyCards().isEmpty()) next();
    }
    
    public boolean isEndOfGame(){
        boolean end = false;
        for(Player p : players){
            if(p.getMyCards().size() == 52){
                winner = p;
                end = true;
                break;
            }
        }
        return end;
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
    
    public void winHeap(){
        heapPlayer.takeHeap(takeHeap());
        heapPlayer = null;
        winHeapState = false;
        stop();
    }
    
    public boolean isWinHeapState(){
        return winHeapState;
    }
    
    public Player getWinner(){
        return winner;
    }
    
    public Player getHeapPlayer(){
        return heapPlayer;
    }
}
