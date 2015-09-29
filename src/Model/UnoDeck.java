/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Model;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Javier
 */
public class UnoDeck extends CardDeck {
    private ArrayList <UnoCard> cards;
    private ArrayList <UnoCard> junk;
    
    @Override
    protected void initCards(){
        cards = new ArrayList<>();
        junk = new ArrayList<>();
        String[] values = {"PLUS2","1","2","3","4","5","6","7","8","9","CHANGEDIRECTION","JUMP"};
        UnoSuit[] ordinarySuits = {UnoSuit.RED, UnoSuit.GREEN, UnoSuit.YELLOW, UnoSuit.BLUE};
        
        for(UnoSuit suit : ordinarySuits){
            for(String val : values){
                cards.add(new UnoCard(val,suit)); //Se meten dos.
                cards.add(new UnoCard(val,suit));
            }
        }
        
        for(UnoSuit suit : ordinarySuits){
            cards.add(new UnoCard("0", suit)); //Se meten dos.
        }
        
        for(int i = 0; i < 4; i++){
            cards.add(new UnoCard("CHANGECOLOR", UnoSuit.CHANGECOLOR)); //Se meten dos.
            cards.add(new UnoCard("PLUS4", UnoSuit.PLUS4));
        }
        
    } 
    
    public UnoDeck(){
        initCards();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }

    @Override
    public UnoCard nextCard() {
        if (cards.isEmpty()){
            ArrayList<UnoCard> aux = new ArrayList<>(junk);
            junk = cards;
            cards = aux;
            shuffle();
        }
        
        return cards.remove(0);
    }

    @Override
    public void giveCardBack(Card card) {
        junk.add((UnoCard)card);
    }

    @Override
    protected ArrayList<Card> removeJokers() {
        return null;
    }

    @Override
    protected int size() {
        return cards.size();
    }

    @Override
    protected boolean isEmpty() {
        return size() == 0;
    }
    
    
    
}
