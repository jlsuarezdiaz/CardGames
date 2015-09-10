/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.Poker.Model;

import Model.FrenchCard;
import Model.FrenchSuit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

enum CPUBetStrategy{
    TIRARSEUNFAROL,  ALLOUT, GOCALM, JUSTCALL, NOTENGONADA
}

enum CPUDiscardStrategy{
    LOOKINGFORREPS, LOOKINGFORSTRAIGHT, LOOKINGFORFLUSH, LOOKINGFORROYAL,
    NOTENGONADA, PERFECTHAND
}

/**
 *
 * @author Juan Luis
 */
public class CPUPlayer extends PokerPlayer{
    
    private static Random decider = new Random();
    
    private CPUBetStrategy betStrategy;
    
    private CPUDiscardStrategy discardStrategy;
    
    /**
     * Provides a random integer in [min,max).
     * @param min
     * @param max
     * @return 
     */
    private static int nextRand(int min, int max){
        return decider.nextInt(max - min) + min;
    }
    
    private int getNumberJokers(){
        int numJokers = 0;
        for(FrenchCard c : playerHand){
            if(c.isSuit(FrenchSuit.JOKER)) numJokers++;
        }
        return numJokers;
    }
    
    private boolean isVeryBigCurrentCall(){
        return (environment.getCurrentCallBet() > nextRand(3,5) * environment.getBigBlindBet()) || 
                (environment.getCurrentCallBet() != 0 &&this.chips / environment.getCurrentCallBet() < 3);
    }
    
    private boolean isVeryLowCurrentCall(){
        return (environment.getCurrentCallBet() <= nextRand(0,3) * environment.getCurrentCallBet());
    }
    
    private ArrayList<String> mapCardValues(){
        ArrayList<String> values = new ArrayList();
        for(FrenchCard c : playerHand){
            if(!c.isSuit(FrenchSuit.JOKER))
                values.add(c.getValue());
        }
        return values;
    }
    
    private ArrayList<FrenchSuit> mapCardSuits(){
        ArrayList<FrenchSuit> suits = new ArrayList();
        for(FrenchCard c : playerHand){
            if(!c.isSuit(FrenchSuit.JOKER))
                    suits.add(c.getSuit());
        }
        return suits;
    }
    
    private ArrayList<FrenchCard> getUnrepeatedCards(){
        Collections.sort(playerHand, comparator);
        ArrayList<String> values = mapCardValues();
        ArrayList<FrenchCard> unrepeated = new ArrayList();
        for(int i = 0; i < values.size(); i++){
            if(Collections.frequency(values, values.get(i)) == 1){
                unrepeated.add(playerHand.get(i));
            }
        }
        return unrepeated;
    }
    
    private ArrayList<FrenchCard> getLessSuitRepeatedCards(){
        ArrayList<FrenchCard> lessSuit = new ArrayList();
        Collections.sort(playerHand, comparator);
        ArrayList<FrenchSuit> handSuits = mapCardSuits();
        ArrayList<FrenchSuit> officialSuits = new ArrayList(Arrays.asList(
            FrenchSuit.SPADES,FrenchSuit.CLUBS,FrenchSuit.DIAMONDS,FrenchSuit.HEARTS));
        ArrayList<Integer> frequencies = new ArrayList();
        for(FrenchSuit s : officialSuits){
            frequencies.add(Collections.frequency(handSuits, s));
        }
        FrenchSuit mainSuit = officialSuits.get(frequencies.indexOf(Collections.max(frequencies)));
        for(int i = 0; i < handSuits.size(); i++){
            if(handSuits.get(i) != mainSuit) lessSuit.add(playerHand.get(i));
        }
        return lessSuit;
    }
    
    private ArrayList<FrenchCard> getUnstraightCards(){
        ArrayList<FrenchCard> unStraight = new ArrayList();
        int numJokers = getNumberJokers();
        Collections.sort(playerHand,comparator);
        ArrayList<String> values = mapCardValues();
        ArrayList<String> orderedValues = new ArrayList(
            Arrays.asList("2","3","4","5","6","7","8","9","10","J","Q","K","A"));
        
        //for(int i = 0; i < values.size(); i++){
        //    for(int j = 1; i < values.size(); j++){
        //        if(orderedValues.indexOf(values.get(j)) - orderedValues.indexOf(values.get(i)) < j-i + numJokers){
                    
        //        }
        //    }
        //}
        PokerHand myHand = (PokerHand)getPokerHand().get(0);
        if(myHand == PokerHand.STRAIGHT || myHand == PokerHand.STRAIGHT_FLUSH || myHand == PokerHand.ROYAL_FLUSH)
            return new ArrayList();
        
        return playerHand;//unStraight;
    }
    
    private CPUBetStrategy getBettingStrategy(){
        int rand = decider.nextInt(100);
        CPUBetStrategy betStrategy = null;
        switch((PokerHand)getPokerHand().get(0)){
            case REPOKER:
            case ROYAL_FLUSH:
            case STRAIGHT_FLUSH:
            case POKER:
            case FULL:
                if(isVeryBigCurrentCall()){
                    if(rand < 20)
                        betStrategy = CPUBetStrategy.GOCALM;
                    else
                        betStrategy = CPUBetStrategy.ALLOUT;
                
                }
                else if(isVeryLowCurrentCall()){
                    if(rand < 30)
                        betStrategy = CPUBetStrategy.ALLOUT;
                    else
                        betStrategy = CPUBetStrategy.GOCALM;
                }
                else{
                   if(rand < 50)
                        betStrategy = CPUBetStrategy.ALLOUT;
                    else
                        betStrategy = CPUBetStrategy.GOCALM; 
                
                }
            case FLUSH:
            case STRAIGHT:
                if(isVeryBigCurrentCall()){
                    if(rand < 15)
                        betStrategy = CPUBetStrategy.GOCALM;
                    else
                        betStrategy = CPUBetStrategy.JUSTCALL;
                
                }
                else if(isVeryLowCurrentCall()){
                    if(rand < 50)
                        betStrategy = CPUBetStrategy.ALLOUT;
                    else
                        betStrategy = CPUBetStrategy.GOCALM;
                }
                else{
                   if(rand < 45)
                        betStrategy = CPUBetStrategy.JUSTCALL;
                   else if(rand < 85) 
                        betStrategy = CPUBetStrategy.GOCALM; 
                   else
                       betStrategy = CPUBetStrategy.ALLOUT;                
                }
                break;
            case THREESOME:
            case TWO_PAIR:
                if(isVeryBigCurrentCall()){
                    if(rand < 10)
                        betStrategy = CPUBetStrategy.NOTENGONADA;
                    else
                        betStrategy = CPUBetStrategy.JUSTCALL;
                
                }
                else if(isVeryLowCurrentCall()){
                    if(rand < 20)
                        betStrategy = CPUBetStrategy.ALLOUT;
                    else
                        betStrategy = CPUBetStrategy.GOCALM;
                }
                else{
                   if(rand < 60)
                        betStrategy = CPUBetStrategy.GOCALM;
                   else if(rand < 90) 
                        betStrategy = CPUBetStrategy.JUSTCALL; 
                   else
                       betStrategy = CPUBetStrategy.ALLOUT;                
                }
                break;
            case PAIR:
            case HIGHCARD:
            default:
                if(isVeryBigCurrentCall()){
                    if(rand < 85)
                        betStrategy = CPUBetStrategy.NOTENGONADA;
                    else
                        betStrategy = CPUBetStrategy.JUSTCALL;
                
                }
                else if(isVeryLowCurrentCall()){
                    if(rand < 40)
                        betStrategy = CPUBetStrategy.TIRARSEUNFAROL;
                    else if(rand < 80 && getBet() == 0)
                        betStrategy = CPUBetStrategy.NOTENGONADA;
                    else if(rand < 90)
                        betStrategy = CPUBetStrategy.JUSTCALL;
                    else
                        betStrategy = CPUBetStrategy.GOCALM;
                }
                else{
                    if(rand < 60)
                         betStrategy = CPUBetStrategy.NOTENGONADA;
                    else if(rand < 70) 
                         betStrategy = CPUBetStrategy.JUSTCALL;
                    else if(rand < 80)
                        betStrategy = CPUBetStrategy.GOCALM;    
                    else
                        betStrategy = CPUBetStrategy.TIRARSEUNFAROL;
                }
                break;
                
        }
        return betStrategy;
    }
    
    private ArrayList<FrenchCard> getDiscardingStrategy(){
        if(getNumberJokers() >= 4){
            this.discardStrategy = CPUDiscardStrategy.PERFECTHAND;
            return new ArrayList();
        }
        ArrayList<FrenchCard> noRep = getUnrepeatedCards();
        ArrayList<FrenchCard> noSuit = getLessSuitRepeatedCards();
        ArrayList<FrenchCard> noStraight = getUnstraightCards();
        
        if(noRep.size() == 0 || noSuit.size() == 0 || noStraight.size() == 0){
            this.discardStrategy = CPUDiscardStrategy.PERFECTHAND;
            return new ArrayList();
        }
        else if(noSuit.size() == 1 && noStraight.size() == 1 && noSuit.get(0) == noStraight.get(0)){
            this.discardStrategy = CPUDiscardStrategy.LOOKINGFORROYAL;
            return noSuit;
        }
        else if(noSuit.size() == 1){
            this.discardStrategy = CPUDiscardStrategy.LOOKINGFORFLUSH;
            return noSuit;
        }
        else if(noStraight.size() == 1){
            this.discardStrategy = CPUDiscardStrategy.LOOKINGFORSTRAIGHT;
            return noStraight;
        }
        else if(noRep.size() <= 3){
            this.discardStrategy = CPUDiscardStrategy.LOOKINGFORREPS;
            return noRep;
        }
        else{
            if(nextRand(0, 2) == 0){
                noRep.remove(noRep.size()-1);
            }
            this.discardStrategy = CPUDiscardStrategy.NOTENGONADA;
            return noRep;
        }
    }
    
    public CPUPlayer(String name, int chips, Poker p){
        super(name, chips,p);
        this.betStrategy = null;
        this.discardStrategy = null;
    }
    
    public CPUPlayer(PokerPlayer p){
        super(p);
        this.betStrategy = null;
        this.discardStrategy = null;
    }
    
    public PokerCommand takeBettingDecision(){
        switch(getBettingStrategy()){
            case TIRARSEUNFAROL:
            case ALLOUT:
                if(canIRaise()){
                    raise((getEnvironment().getCurrentCallBet()>0)?getEnvironment().getCurrentCallBet():getEnvironment().getBigBlindBet() * nextRand(1,4));
                    return PokerCommand.RAISE;
                }
                else if(canICall()){
                    call();
                    return PokerCommand.CALL;
                }
                else{
                    fold();
                    return PokerCommand.FOLD;
                }
            case GOCALM:
                if(canIRaise()){
                    raise(getEnvironment().getBigBlindBet() * nextRand(1,3));
                    return PokerCommand.RAISE;
                }
                else if(canICall()){
                    call();
                    return PokerCommand.CALL;
                }
                else{
                    fold();
                    return PokerCommand.FOLD;
                }
            case JUSTCALL:
                if(canICall()){
                    call();
                    return PokerCommand.CALL;
                }
                else if(canIFold()){
                    fold();
                    return PokerCommand.FOLD;
                }
                else{
                    return null;
                }
            case NOTENGONADA:
                if(canIFold()){
                    fold();
                    return PokerCommand.FOLD;
                }
                else if(canICall()){
                    call();
                    return PokerCommand.CALL;
                }
                else{
                    return null;
                }
            default:
                return null;
        }
    }
    
    public int takeDiscardingDecision(){
        //ArrayList<FrenchCard> toDiscard = new ArrayList();
        //toDiscard.add(playerHand.get(0));
        //toDiscard.add(playerHand.get(1));
        //toDiscard.add(playerHand.get(2));
        //this.discard(toDiscard);
        //return 0;
        ArrayList<FrenchCard> toDiscard = getDiscardingStrategy();
        discard(toDiscard);
        return toDiscard.size();
    }
}
