/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Games.Broom.Model;

import java.util.ArrayList;
import java.util.Random;
import Model.*;

/**
 *
 * @author Javier
 */

public class Broom {
    private static Broom instance = new Broom();
    private int currentPlayerIndex;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private ArrayList<SpanishCard> cards;
    private int currentServerPlayerIndex;
    private int lastPlayer; //ultimo jugador que cogio.
    private SpanishDeck deck;
    
    public Broom(){
        currentPlayerIndex = -1;
        currentServerPlayerIndex = -1;
        lastPlayer = -1;
        currentPlayer = null;
        cards = new ArrayList<>();
        players = new ArrayList<>();
        deck = new SpanishDeck();
    }
    
    public Broom(ArrayList<String> names){
        currentPlayerIndex = -1;
        currentServerPlayerIndex = -1;
        lastPlayer = -1;
        currentPlayer = null;
        cards = new ArrayList<>();
        players = new ArrayList<>();
        deck = new SpanishDeck();
        initGame(names);
    }
    
    private void initPlayers(ArrayList<String> names){
        if (!names.isEmpty()){
            for (String n: names){
                players.add(new Player(n));
            }
        }
    }
    
    private Player nextPlayer(){
        Random rnd = new Random();
        int index;
        
        if (currentPlayerIndex == -1){
            index = rnd.nextInt(players.size());   
        }
        else{
            if (currentPlayerIndex == players.size()-1)
                index = 0;
            else
                index = currentPlayerIndex + 1;
        }
        
        currentPlayerIndex = index;
        currentPlayer = players.get(index);
        return currentPlayer;
    }
    
    private void setNewServer(){
        if (currentPlayerIndex == 0){
            currentServerPlayerIndex = players.size()-1;
        }
        else{
            currentServerPlayerIndex = currentPlayerIndex - 1;
        }
    }    
    
    private boolean giveCardsToPlayers(SpanishDeck deck){
        boolean state;
         
        ArrayList<SpanishCard> c = new ArrayList<>();
      
        if (!deck.isEmpty()){
            state = true;
            
            for (int i = 0; i < 9; i++){
                c.add(deck.nextCard());
            }

            int j = (currentServerPlayerIndex+1)%players.size();

            while (!c.isEmpty()){
                SpanishCard card = c.get(0);
                players.get(j).addCard(card);
                c.remove(card);

                j = (j+1)%players.size();
            }
        }
        else{ // se acabaron todas las cartas.
            resetGame();
            initCardsTable();
            
            currentServerPlayerIndex = currentPlayerIndex;
            currentPlayerIndex = (currentServerPlayerIndex+1)%players.size();
            currentPlayer = players.get(currentPlayerIndex);
            
            state = false;
        }
        
        return state;
    }
    
   
    private SpanishDeck initCardsTable(){
        SpanishDeck deck1 = new SpanishDeck();
        deck1.remove8And9s();
        deck1.shuffle();
        this.deck = deck1;  
        
        for (int i = 0; i < 4; i++)
            cards.add(deck.nextCard());
        
        return deck;
    
    }
    
    public static Broom getInstance(){
        return instance;
    }
    
    public void initGame(ArrayList<String> players){
        initPlayers(players);
        initCardsTable();
        nextTurn();
    }
    
    public void resetGame(){
        ArrayList<SpanishCard> copia = new ArrayList<>(cards);
        
        System.out.println(Integer.toString(lastPlayer));
        
        for (SpanishCard c: copia){
            players.get(lastPlayer).addCard(c);
        }
        
        cards.clear();
        
        initCardsTable();
        obtainPoints();
        
        for (Player p:players){
            p.resetHeap();
        }
    }
    
    public boolean nextTurn(){        
        boolean state;
        
        if (nextDealAllowed()){    
            currentPlayer = nextPlayer();   
 
            state = giveCardsToPlayers(this.deck);
        }
        else{
            if (currentServerPlayerIndex == -1)
                setNewServer();
            
            currentPlayer = nextPlayer();

            state = true;
        }
        
        return state;
    }
    
    public boolean nextDealAllowed(){
        for (Player p: players){
            if (!p.validState()){
                return false;
            }
        }
        
        return true;
    }
     
    
    
    //////////////////////////
    public boolean endOfGame(){
        boolean winner = false;
        
        for (int i = 0; i < players.size() && !winner; i++){
            if (players.get(i).getTotalPoints() >= 15){
                winner = true;
            }
        }
        
        return winner;
    }
    
    public ArrayList<Player> tellTheWinner(){        
        int max = -1, index = -1;
        ArrayList<Player> winners = new ArrayList<>();
        
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getTotalPoints() > max){
                max = players.get(i).getTotalPoints();
                index = i;
            }
        }
        
        winners.add(players.get(index));
        
        for (int i = 0; i < players.size(); i++){
            if (index != i)
                if (players.get(i).getTotalPoints() == max){
                    winners.add(players.get(i));
                }
        }
        
        return winners;
    }
    
    public void obtainPoints(){
        ArrayList<SpanishCard> copia = new ArrayList<>(cards);
                
        for (SpanishCard c: copia){
            players.get(lastPlayer).addCard(c);
        }
        
        
        ArrayList<Prize> prizes = new ArrayList<>();
        
        
        for (Player p: players){
            prizes.add(p.estimatePoints());
        }
        
        //vemos quien ha ganado la ronda.
        compareScore(prizes);
        
        for (Player p: players){
            p.setScore(p.getBrooms());
        }  
    }
    
    private void compareScore(ArrayList<Prize> pr){        
        //Comparamos primero el numero de cartas.
        ArrayList<Integer> nc = new ArrayList<>();
        
        for (Prize p: pr){
            nc.add(p.getNumberCards());
        }
        
        compareNumberCards(nc);
        
        //Comparamos ahora el numero de oros.
        
        ArrayList<Integer> no = new ArrayList<>();
        
        for (Prize p: pr){
            no.add(p.getNumberOros());
        }
        
        compareNumberOros(no);
        
        //Comparamos ahora el numero de sietes.
        
        ArrayList<Integer> ns = new ArrayList<>();
        
        for (Prize p: pr){
            ns.add(p.getNumberSeven());
        }
        
        compareNumberSevens(ns);
        
        //Vemos quien tiene el siete de oros.
        
        for (int i = 0; i < pr.size(); i++){
            if (pr.get(i).hasSevenOros()){
                players.get(i).setScore(1);
            }
        }
    }
    
    private void compareNumberCards(ArrayList<Integer> nc){
        int index = -1, max = -1;
        boolean found = false;

        //Vemos el maximo.
        for (int i = 0; i < nc.size(); i++){
            if (nc.get(i) > max){
                max = nc.get(i);
                index = i;
                found = true;
            }
        }
        
        //Vemos si hay otra persona con el mismo numero de cartas.
        for (int i = 0; i < nc.size(); i++){
            if (i != index){
                if (max == nc.get(i)){
                    found = false;
                }
            }
        }
        
        //Actualizamos las puntuaciones.
        if (found){
            for (int i = 0; i < players.size(); i++){
                if (i == index)
                    players.get(i).setScore(1);
            }
        }
    }
    
    private void compareNumberOros(ArrayList<Integer> no){
        int index = -1, max = -1;
        boolean found = false;
        
        //Vemos el maximo.
        for (int i = 0; i < no.size(); i++){
            if (no.get(i) > max){
                max = no.get(i);
                index = i;
                found = true;
            }
        }
        
        for (int i = 0; i < no.size(); i++){
            if (i != index){
                if (max == no.get(i)){
                    found = false;
                }
            }
        }
        
        //Actualizamos las puntuaciones.
        if (found){
            for (int i = 0; i < players.size(); i++){
                if (i == index)
                    players.get(i).setScore(1);
            }
        }
    }
    
    private void compareNumberSevens(ArrayList<Integer> ns){
        int index = -1, max = -1;
        boolean found = false;

        //Vemos el maximo.
        for (int i = 0; i < ns.size(); i++){
            if (ns.get(i) > max){
                max = ns.get(i);
                index = i;
                found = true;
            }
        }
        
        //Vemos si hay otra persona con el mismo numero de cartas.
        for (int i = 0; i < ns.size(); i++){
            if (i != index){
                if (max == ns.get(i)){
                    found = false;
                }
            }
        }
        
        //Actualizamos las puntuaciones.
        if (found){
            for (int i = 0; i < players.size(); i++){
                if (i == index)
                    players.get(i).setScore(1);
            }
        }
    }
    
    public void discardCard(SpanishCard c){
        currentPlayer.discardCard(c);
    }
    
    public ArrayList<SpanishCard> getHeap(){
        return currentPlayer.getHeap();
    }
    
    public ArrayList<SpanishCard> getCards(){
        return currentPlayer.getCards();
    }
    
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<SpanishCard> getTableCards() {
        return cards;
    }

    public int getCurrentServerPlayerIndex() {
        return currentServerPlayerIndex;
    }
    
    public boolean goodMove(SpanishCard c, ArrayList<SpanishCard> selected){
        if (currentPlayer instanceof CPUPlayer){
            ((CPUPlayer)currentPlayer).playCPU(cards);
            return true;
        }
        else{
            boolean good = currentPlayer.goodMove(c,selected);
        
            if (good){
                lastPlayer = currentPlayerIndex;
            }
        
            return good;
        }
    }
    
    public void setNewCard(SpanishCard c){
        currentPlayer.setNewCard(c, cards);
    }
    
    public void discardTableCard(Card c){
        cards.remove(c);
    }
    
    public Player getMyPlayer(){
        return players.get(0);
    }
    
    public SpanishDeck getDeck(){
        return deck;
    }
}
