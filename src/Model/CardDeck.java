/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Juan Luis
 */
public abstract class CardDeck {
    
    protected abstract void initCards();
        
    public abstract void shuffle();
    
    public abstract Card nextCard();
    
    public abstract void giveCardBack(Card card);
    
    protected abstract ArrayList<Card> removeJokers();
    
    protected abstract int size();
    
    protected abstract boolean isEmpty();
}
