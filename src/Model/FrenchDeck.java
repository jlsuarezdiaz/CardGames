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
public class FrenchDeck extends CardDeck{

    private ArrayList<FrenchCard> cards;
            
    @Override
    protected void initCards() {
        cards = new ArrayList();
        String[] values = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        FrenchSuit[] ordinarySuits = {FrenchSuit.CLUBS,FrenchSuit.SPADES,FrenchSuit.HEARTS,FrenchSuit.DIAMONDS};
        for(FrenchSuit suit : ordinarySuits){
            for(String val : values){
                cards.add(new FrenchCard(val, suit));
            }
        }
        cards.add(new FrenchCard("BLACK", FrenchSuit.JOKER));
        cards.add(new FrenchCard("RED", FrenchSuit.JOKER));
    }

    public FrenchDeck(){
        initCards();
    }
    
    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }

    @Override
    public FrenchCard nextCard() {
        return (cards.isEmpty())?null:cards.remove(0);
    }

    @Override
    public void giveCardBack(Card card) {
        cards.add((FrenchCard) card);
    }

    @Override
    public ArrayList<Card> removeJokers() {
        ArrayList<FrenchCard> jokers = new ArrayList();
        for(int i = 0; i < cards.size(); i++){
            if(cards.get(i).isSuit(FrenchSuit.JOKER)){
                jokers.add(cards.remove(i));
                i--;
            }
        }
        return (ArrayList<Card>) (ArrayList<? extends Card>) jokers;
    }
    
    @Override
    public int size(){
        return cards.size();
    }
    
    @Override
    public boolean isEmpty(){
        return size() == 0;
    }
    
}
