/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.Poker.Model;

import Model.FrenchCard;
import Model.FrenchSuit;
import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Juan Luis
 */
public class PokerPlayer {
    
    protected ArrayList<FrenchCard> playerHand;
    
    protected boolean dealer;
    
    protected boolean smallBlind;
    
    protected boolean bigBlind;
    
    protected String name;
    
    protected int bet;
    
    protected int chips;
    
    protected boolean allInSituation;
    
    protected boolean folded;
    
    private boolean lost;
    
    protected PokerEnvironment environment;
    
    private ArrayList<FrenchCard> discardedCards;
    
    public PokerPlayer(String name, int chips, Poker p){
        this.name = name;
        this.chips = chips;
        this.dealer = false;
        this.smallBlind = false;
        this.bigBlind = false;
        this.bet = 0;
        this.allInSituation = false;
        this.playerHand = new ArrayList();
        this.folded = false;
        this.lost = false;
        this.environment = new PokerEnvironment(p);
        this.discardedCards = null;
    }
    
    public PokerPlayer(PokerPlayer p){
        this.name = p.name;
        this.chips = p.chips;
        this.dealer = p.dealer;
        this.smallBlind  = p.smallBlind;
        this.bigBlind = p.bigBlind;
        this.bet = p.bet;
        this.allInSituation = p.allInSituation;
        this.playerHand = p.playerHand;
        this.folded = p.folded;
        this.lost = p.lost;
        this.environment = p.environment;
        this.discardedCards = p.discardedCards;
    }
    
    public void receive(FrenchCard c){
        playerHand.add(c);
    }
    
    public void discard(ArrayList<FrenchCard> cards){
        if(cards == playerHand){
            discardedCards = playerHand;
            playerHand = new ArrayList();
        }
        else{
            for(FrenchCard c : cards){
                playerHand.remove(c);                
            }
            discardedCards = cards;
        }
    }

    public PokerEnvironment getEnvironment() {
        return environment;
    }
    
    
    public ArrayList<FrenchCard> takeDiscards(){
        ArrayList<FrenchCard> discards = discardedCards;
        discardedCards = null;
        return discards;
    }
    
    public boolean isDealer(){
        return dealer;
    }
    
    public boolean isSmallBlind(){
        return smallBlind;
    }
    
    public boolean isBigBlind(){
        return bigBlind;
    }
    
    public void setDealer(boolean b){
        this.dealer = b;
    }
    
    public void setSmallBlind(boolean b){
        this.smallBlind = b;
    }
    
    public void setBigBlind(boolean b){
        this.bigBlind = b;
    }
    
    public boolean isAllInSituation(){
        return allInSituation;
    }
    
    public boolean hasFolded(){
        return folded;
    }
    
    public boolean hasLost(){
        return lost;
    }
    
    public String getName(){
        return name;
    }
    
    public int getBet(){
        return bet;
    }
    
    public int getChips(){
        return chips;
    }
    
    public ArrayList<FrenchCard> getPlayerHand(){
        return playerHand;
    }
    
    public void bet(int bet){
        if(bet - this.bet >= this.chips){
            allInSituation = true;
            bet = chips + this.bet;
        }
        this.chips = this.chips - bet + this.bet;
        this.bet = bet;
    }
    
    public void call(){
        this.bet(environment.getCurrentCallBet());
    }
    
    public void raise(int raise){
        /*if(raise >= this.chips){
            allInSituation = true;
            raise = chips;
        }
        this.chips -= raise;
        this.bet += raise;*/
        this.bet(environment.getCurrentCallBet() + raise);
    }
    
    public void allIn(){
        bet(this.chips + this.bet);
    }
    
    public int takeBet(){
        int bet = this.bet;
        this.bet = 0;
        return bet;
    }
    
    public int takeBet(int bet){
        if(bet > this.bet){
            bet = this.bet;
        }
        this.bet -= bet;
        return bet;
    }
    
    public void fold(){
        this.folded = true;
        discard(playerHand);
    }
    
    private void lose(){
        lost = true;
    }
    
    public void loseIfNoChips(){
        if(chips == 0) lose();
    }
    
    public void newRound(){
        this.folded = false;
        removeAllInSituation();
        loseIfNoChips();
    }
    
    public void win(int pot){
        this.chips += pot;
    }
    
    public boolean canIBet(){
        return isMyTurn() && environment.getRoundKind() == PokerRoundKind.BETTING && !isAllInSituation()
                && !(environment.getBettingRoundNomber() == 1 && (isBigBlind() || isSmallBlind()) && getBet() == 0)
                && !environment.isEndOfRound();
    }
    
    public boolean canIRaise(){
       return canIBet() && environment.getCurrentCallBet() < (getChips() + getBet());  
    }
    
    public boolean canICall(){
        return canIBet();
    }
    
    public boolean canIFold(){
        return canIBet() && getBet() != getEnvironment().getCurrentCallBet();
    }
    
    public boolean isMyTurn(){
        return environment.getCurrentPlayer() == this;
    }
    
    public boolean canIDiscard(){
        return isMyTurn() && environment.getRoundKind() == PokerRoundKind.DISCARDING
                && !environment.isEndOfRound();
    }
    
    private void removeAllInSituation(){
        if(chips > 0) allInSituation = false;
    }
    
    public ArrayList<FrenchCard> removeHand(){
        ArrayList<FrenchCard> hand = new ArrayList();
        for(int i = playerHand.size()-1; i >= 0; i--){
            hand.add(playerHand.remove(i));
        }
        return hand;
    }
    
    protected static Comparator<FrenchCard> comparator = new Comparator<FrenchCard>() {
        @Override
        public int compare(FrenchCard c1, FrenchCard c2) {
            if(c1.isSuit(FrenchSuit.JOKER) && c2.isSuit(FrenchSuit.JOKER)) return 0;
            if(c1.isSuit(FrenchSuit.JOKER)) return 1;
            if(c2.isSuit(FrenchSuit.JOKER)) return -1;
            if(c1.getValue().equals(c2.getValue())) return 0;
            
            if(!c1.isValue("J") && !c1.isValue("Q") && !c1.isValue("K") && !c1.isValue("A")){
                if(c2.isValue("J") || c2.isValue("Q") || c2.isValue("K") || c2.isValue("A")){
                    return -1;
                }
                else{
                    return Integer.compare(Integer.parseInt(c1.getValue()), Integer.parseInt(c2.getValue()));
                }
            }
            else{
                if(!c2.isValue("J") && !c2.isValue("Q") && !c2.isValue("K") && !c2.isValue("A")){
                    return 1;
                }
                else{
                    if(c1.isValue("J")) return -1;
                    else if(c2.isValue("J")) return 1;
                    else if(c1.isValue("Q")) return -1;
                    else if(c2.isValue("Q")) return 1;
                    else if(c1.isValue("K")) return -1;
                    else return 1;
                    
                }
            }
        }
    };
    
    
    /**
     * Gets the kind of the poker player hand.
     * @return The first element of the array will be the poker hand kind.
     *  The following elements of the array represent the value or values that determines
     *  the quality of the poker hand.
     *  The rest of values will represent the card values left, used to solve draws.
     *  If player doesn't have the five hands the method will return null.
     * @example The hand [A A A K K] will return {PokerHand.FULL, A, K}.
     *  The hand [Q Q 10 9 5] will return {PokerHand.PAIR, Q, 10, 9, 5}.
     */
    public ArrayList<Object> getPokerHand(){
        if(playerHand.size() < 5) return null;
        Collections.sort(playerHand, comparator);
        
        
        int num_jokers = 0;
        for(FrenchCard c : playerHand){
            if(c.isSuit(FrenchSuit.JOKER)) num_jokers++;
        }
        
        if(num_jokers == 5){
            return new ArrayList(Arrays.asList(PokerHand.REPOKER,"A"));
        }
        
        Integer frequencies[] = {0,0,0,0,0,0,0,0,0,0,0,0,0};
        String values[] = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
        
        ArrayList<String> cardValues = new ArrayList();
        for(FrenchCard c : playerHand){
            if(!c.isSuit(FrenchSuit.JOKER)) cardValues.add(c.getValue());
        }
        
        for(int i = 0; i < 13; i++){
            for(FrenchCard c : playerHand){
                frequencies[i] += c.isValue(values[i])?1:0;
            }
            frequencies[i]+= num_jokers;
        }
        
        ArrayList<Object> pokerHand = new ArrayList();
        PokerHand hand = null;
        int reps = Collections.max(Arrays.asList(frequencies));
        switch(reps){
            case 5:
                hand = PokerHand.REPOKER;
                pokerHand.add(hand);
                pokerHand.add(values[Arrays.asList(frequencies).indexOf(5)]);
                break;
            case 4:
                hand = PokerHand.POKER;  // 5 A A A A  // 3 3 3 3 5
                pokerHand.add(hand);
                pokerHand.add(values[Arrays.asList(frequencies).lastIndexOf(4)]);
                cardValues.removeAll(Collections.singleton(values[Arrays.asList(frequencies).lastIndexOf(4)]));
                pokerHand.add(cardValues.get(0));
                break;
            case 3:
                if(Collections.frequency(Arrays.asList(frequencies), 2+num_jokers) >= num_jokers+1){
                    hand = PokerHand.FULL;
                    pokerHand.add(hand);
                    pokerHand.add(values[Arrays.asList(frequencies).lastIndexOf(3)]);
                    cardValues.removeAll(Collections.singleton(values[Arrays.asList(frequencies).lastIndexOf(3)]));
                  
                    pokerHand.add(cardValues.get(0));
                }
                else{
                    hand = PokerHand.THREESOME;
                    pokerHand.add(hand);
                    pokerHand.add(values[Arrays.asList(frequencies).lastIndexOf(3)]);
                    cardValues.removeAll(Collections.singleton(values[Arrays.asList(frequencies).lastIndexOf(3)]));
                    pokerHand.add(cardValues.get(1));
                    pokerHand.add(cardValues.get(0));
                }
                break;
            case 2:
                if(Collections.frequency(Arrays.asList(frequencies), 2) == 2){
                    hand = PokerHand.TWO_PAIR;
                    pokerHand.add(hand);
                    pokerHand.add(values[Arrays.asList(frequencies).lastIndexOf(2)]);
                    pokerHand.add(values[Arrays.asList(frequencies).indexOf(2)]);
                    pokerHand.add(values[Arrays.asList(frequencies).indexOf(1)]);
                }
                else{
                    hand = PokerHand.PAIR;
                    pokerHand.add(hand);
                    pokerHand.add(values[Arrays.asList(frequencies).lastIndexOf(2)]);
                    cardValues.removeAll(Collections.singleton(values[Arrays.asList(frequencies).lastIndexOf(2)]));
                    pokerHand.add(cardValues.get(2));
                    pokerHand.add(cardValues.get(1));
                    pokerHand.add(cardValues.get(0));
                }
                break;
        }
        
        boolean isFlush = true;
        FrenchSuit flushSuit = null;
        for(int i = 0; i < 5 && isFlush; i++){
            if(flushSuit != null && !playerHand.get(i).isSuit(flushSuit) && !playerHand.get(i).isSuit(FrenchSuit.JOKER)){
                isFlush = false;
            }
            if(flushSuit == null && !playerHand.get(i).isSuit(FrenchSuit.JOKER)){
                flushSuit = playerHand.get(i).getSuit();
            }
        }
        
        boolean isStraight = true;
        int straightFirstIndex = Arrays.asList(values).indexOf(playerHand.get(0).getValue());
        int currentIndex = straightFirstIndex;
        
        for(int i = 1; i < 5 && isStraight; i++){
            if(playerHand.get(i).getSuit() != FrenchSuit.JOKER &&
                    (Arrays.asList(values).indexOf(playerHand.get(i).getValue()) == currentIndex ||
                    Arrays.asList(values).indexOf(playerHand.get(i).getValue()) - straightFirstIndex > 4)){
                        isStraight = false;
            }
            currentIndex = Arrays.asList(values).indexOf(playerHand.get(i).getValue());
        }
        if(isStraight && straightFirstIndex > 8) straightFirstIndex = 8; // Para Jokers, Q, K, A
        
        if(isStraight && isFlush && reps < 5){
            pokerHand.clear();
            if(values[straightFirstIndex] == "10"){
                pokerHand.add(PokerHand.ROYAL_FLUSH);
            }
            else{
                pokerHand.add(PokerHand.STRAIGHT_FLUSH);
                pokerHand.add(values[straightFirstIndex+4]);
            }
        }
        else if(isFlush && reps < 4){
            pokerHand.clear();
            pokerHand.add(PokerHand.FLUSH);
            for(int i = 4; i >= 0; i--){
                if(playerHand.get(i).isSuit(FrenchSuit.JOKER)) pokerHand.add("A");
                else pokerHand.add(playerHand.get(i).getValue());
            }
        }
        else if(isStraight && reps < 4 && hand != PokerHand.FULL){
            pokerHand.clear();
            pokerHand.add(PokerHand.STRAIGHT);
            pokerHand.add(values[straightFirstIndex+4]);
        }
        else if(pokerHand.isEmpty()){
            pokerHand.add(PokerHand.HIGHCARD);
            pokerHand.add(playerHand.get(4).getValue());
            pokerHand.add(playerHand.get(3).getValue());
            pokerHand.add(playerHand.get(2).getValue());
            pokerHand.add(playerHand.get(1).getValue());
            pokerHand.add(playerHand.get(0).getValue());
        }
        return pokerHand;
    }
    
    private int getNumericalValue(String cardValue){
        switch(cardValue){
            case "2":
                return 0;    
            case "3":
                return 1;
            case "4":
                return 2;
            case "5":
                return 3;
            case "6":
                return 4;
            case "7":
                return 5;
            case "8":
                return 6;
            case "9":
                return 7;
            case "10":
                return 8;
            case "J":
                return 9;
            case "Q":
                return 10;
            case "K":
                return 11;
            case "A":
                return 12;
            default:
                return -1;
        }
    }
    /**
     * Computes 
     * @return 
     */
    public int computeHandValue(){
        ArrayList<Object> pokerHand = getPokerHand();
        if(pokerHand == null) return -1;
        final int LAST_ITEM = 5;
        final int POW_13[] = {1,13,169,2197,28561,371293};
        int handValue = 0;
        handValue += ((PokerHand)pokerHand.get(0)).getRankingValue() * POW_13[LAST_ITEM];
        for(int i = 1; i < pokerHand.size(); i++){
            handValue += getNumericalValue(((String)pokerHand.get(i))) * POW_13[LAST_ITEM-i];
        }
        return handValue;
    }
}
