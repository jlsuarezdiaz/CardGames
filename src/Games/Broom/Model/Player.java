/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Games.Broom.Model;

import java.util.ArrayList;

/**
 *
 * @author Javier
 */
public class Player {
    private String name;
    private int total_points;
    private ArrayList<Card> cards;
    private ArrayList<Card> heap;
    
    public Player(String name){
        this.name = name;
        total_points = 0;
        cards = new ArrayList<>();
        heap = new ArrayList<>();
    }

    
    public void play(int eleccion, Card carta,ArrayList<Card> tabla){
        if (eleccion == 0){ ///Jugamos a mover.
            goodMove(carta,tabla);
        }
        else{ // No tenemos o no sabemos movimiento.
            setNewCard(carta,tabla);
        }
    }
    
    public boolean goodMove(Card c,ArrayList<Card> table){
        boolean good = false;        
        int sum = 0;
        
        for (Card card: table){
            sum += card.getValue();
        }
        
        sum += c.getValue();
        
        if (sum == 15)
            good = true;
        else
            good = false;
        
        return good;
    }
    
    public void setNewCard(Card c,ArrayList<Card> table){
        table.add(c);
    }
    
    public Prize estimatePoints(){
        int numberCards = heap.size();
        boolean found = found7Oros();
        int numberOros = calcNumberOros();
        int numberSevens = calcNumberSevens();
        
        return new Prize(numberCards,numberOros,numberSevens,found);
    }
    
    private int calcNumberSevens(){
        int number = 0;
        
        for (Card c: heap){
            if (c.getValue() == 7){
                number = number + 1; 
            }
        }
        
        return number;
    }
    
    private int calcNumberOros(){
        int number = 0;
        
        for (Card c: heap){
            if (c.getType() == CardType.OROS){
                number = number + 1; 
            }
        }
        
        return number;
    }
    
    private boolean found7Oros(){
        boolean found = false;
        
        for(Card c: heap){
            if (c.getType() == CardType.OROS && c.getValue() == 7){
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

    @SuppressWarnings("unchecked")
    public ArrayList<Card> getCards() {
        return (ArrayList<Card>)cards.clone();
    }
    
    public void addCard(Card c){
        cards.add(c);
    }
    
    public void addCardHeap(Card c){
        heap.add(c);
    }
    
    public void discardCard(Card c){
        cards.remove(c);
    }
    
    public boolean validState(){
        return cards.isEmpty();
    }
    
    public ArrayList<Card> getHeap(){
        return heap;
    }
    
    public void resetHeap(){
        heap.clear();
    }
}
