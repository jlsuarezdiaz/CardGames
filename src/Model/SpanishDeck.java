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
 * @author Juan Luis
 */
public class SpanishDeck extends CardDeck{

    private ArrayList<SpanishCard> cards;
            
    @Override
    protected void initCards() {
        cards = new ArrayList();
        String[] values = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        SpanishSuit[] ordinarySuits = {SpanishSuit.CLUBS,SpanishSuit.COINS,SpanishSuit.CUPS,SpanishSuit.SWORDS};
        for(SpanishSuit suit : ordinarySuits){
            for(String val : values){
                cards.add(new SpanishCard(val, suit));
            }
        }
    }

    public SpanishDeck(){
        initCards();
    }
    
    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }

    @Override
    public SpanishCard nextCard() {
        return (cards.isEmpty())?null:cards.remove(0);
    }

    @Override
    public void giveCardBack(Card card) {
        cards.add((SpanishCard) card);
    }

    @Override
    protected ArrayList<Card> removeJokers() {
         return null;
    }
    
    @Override
    public int size(){
        return cards.size();
    }
    
    @Override
    public boolean isEmpty(){
        return size() == 0;
    }    
    
    public ArrayList<SpanishCard> remove8And9s(){
        ArrayList<SpanishCard> cards89 = new ArrayList();
        for(int i = 0; i < cards.size(); i++){
            if(cards.get(i).isValue("8") || cards.get(i).isValue("9")){
                cards89.add(cards.remove(i));
                i--;
            }
        }
        return cards89;
    }
}
