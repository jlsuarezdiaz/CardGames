/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Games.Broom.Model;

import java.util.ArrayList;
import Model.*;

/**
 *
 * @author Javier
 */
public class Player {
    private String name;
    private int total_points;
    protected ArrayList<SpanishCard> cards;
    protected ArrayList<SpanishCard> heap;
    private int brooms;
    
    public Player(String name){
        this.name = name;
        total_points = 0;
        brooms = 0;
        cards = new ArrayList<>();
        heap = new ArrayList<>();
    }

    
    public void play(int eleccion,SpanishCard carta,ArrayList<SpanishCard> tabla){
        if (eleccion == 0){ ///Jugamos a mover.
            goodMove(carta,tabla);
        }
        else{ // No tenemos o no sabemos movimiento.
            setNewCard(carta,tabla);
        }
    }
    
    public boolean goodMove(SpanishCard c,ArrayList<SpanishCard> table){
        boolean good;        
        int sum = 0;
        
        for (SpanishCard card: table){
            
            sum += Integer.valueOf(card.getValue()) >= 10 ? Integer.valueOf(card.getValue())-2 : Integer.valueOf(card.getValue());
        }
        
        sum += Integer.valueOf(c.getValue()) >= 10 ? Integer.valueOf(c.getValue())-2 : Integer.valueOf(c.getValue());
      
        if (sum == 15){
            good = true;
            brooms++;
        }
        else
            good = false;
        
        return good;
    }
    
    public void setNewCard(SpanishCard c,ArrayList<SpanishCard> table){
        table.add(c);
    }
    
    public Prize estimatePoints(){
        int numberCards = heap.size();
        boolean found = found7Oros();
        int numberOros = calcNumberOros();
        int numberSevens = calcNumberSevens();
        
        return new Prize(numberCards,numberOros,numberSevens,found);
    }
    
    //Recuento de las cartas del jugador.
    private int calcNumberSevens(){
        int number = 0;
        
        for (SpanishCard c: heap){
            if (Integer.valueOf(c.getValue()) == 7){
                number = number + 1; 
            }
        }
        
        return number;
    }
    
    private int calcNumberOros(){
        int number = 0;
        
        for (SpanishCard c: heap){
            if (c.getSuit() == SpanishSuit.COINS){
                number = number + 1; 
            }
        }
        
        return number;
    }
    
    private boolean found7Oros(){
        boolean found = false;
        
        for(Card c: heap){
            if (c.getSuit() == SpanishSuit.COINS && Integer.valueOf(c.getValue()) == 7){
                found = true;
            }
        }
        
        return found;
    }
    
    public void setScore(int score){
        total_points = total_points + score;
    }
    
    public String getName() {
        return name;
    }

    public int getTotalPoints() {
        return total_points;
    }
    
    public int getBrooms(){
        return brooms;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<SpanishCard> getCards() {
        return (ArrayList<SpanishCard>)cards.clone();
    }
    
    public void addCard(SpanishCard c){
        cards.add(c);
    }
    
    public void addCardHeap(SpanishCard c){
        heap.add(c);
    }
    
    public void discardCard(SpanishCard c){
        cards.remove(c);
    }
    
    public boolean validState(){
        return cards.isEmpty();
    }
    
    public ArrayList<SpanishCard> getHeap(){
        return heap;
    }
    
    public void resetHeap(){
        heap.clear();
    }
}
