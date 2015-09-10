/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.Poker.Model;

import Model.Card;
import Model.FrenchCard;
import Model.FrenchDeck;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author Juan Luis
 */
public class Poker {
    private ArrayList<PokerPlayer> players;
    
    private int playersNumber;
    
    private PokerPlayer currentPlayer;
    
    private int currentPlayerIndex;
    
    private PokerPlayer dealer;
    
    private int dealerIndex;
    
    private int gameRound;
    
    private FrenchDeck pokerDeck;
    
    private int pot;
    
    private ArrayList<AllInPot> allInPots;
    
    private int bettingRoundNumber;
    
    private PokerRoundKind roundKind;
    
    private int totalRounds;
    
    private int smallBlindBet;
    
    private int bigBlindBet;
    
    private ArrayList<FrenchCard> discards;
    
    private PokerPlayer myPlayer;
    
    boolean endOfRound;
    
    public boolean everybodyHasAllCards(){
        for(PokerPlayer p : players){
            if(p.getPlayerHand().size() < 5){
                return false;
            }
        }
        return true;
    }
    
    public boolean everyBodyHasBet() {
        int bet = getCurrentCallBet();
        for(PokerPlayer p : players){
            if(!p.hasFolded() && !p.isAllInSituation() && p.getBet() != bet){
                return false;
            }
        }
        
        if(bet == bigBlindBet && !currentPlayer.isBigBlind() && this.bettingRoundNumber == 1) return false;
        if(this.bettingRoundNumber != 1 && bet == 0 && currentPlayer != dealer) return false;
        return true;
    }
    
    private FrenchCard nextCard(){
        if(pokerDeck.isEmpty()){
            takeDiscards();
            while(!discards.isEmpty()){
                pokerDeck.giveCardBack(discards.remove(0));
            }
            pokerDeck.shuffle();
        }
        return pokerDeck.nextCard();
    }
    
    public Poker(String[] names, int initialChips, int totalDiscardingRounds, int smallBlindBet, int bigBlindBet, int num_jokers){
        players = new ArrayList();
        for(String name : names){
            players.add(new PokerPlayer(name, initialChips,this));
        }
        this.
        playersNumber = players.size();
        //currentPlayerIndex = (new Random()).nextInt(players.size());
        //currentPlayer = players.get(currentPlayerIndex);
        
        dealerIndex = (new Random()).nextInt(players.size());
        dealer = players.get(dealerIndex);
        
        this.gameRound = 0;
        this.pot = 0;
        
        this.totalRounds = totalDiscardingRounds;
        
        this.pokerDeck = new FrenchDeck();
        ArrayList<Card> jokers = pokerDeck.removeJokers();
        for(int i = 1; i <= num_jokers && !pokerDeck.isEmpty(); i++){
            pokerDeck.giveCardBack(jokers.remove(0));
        }
        
        this.pot = 0;
        
        this.allInPots = new ArrayList();
        
        this.roundKind = PokerRoundKind.DEALING;
        
        this.bettingRoundNumber = 0;
        
        this.smallBlindBet = smallBlindBet;
        this.bigBlindBet = bigBlindBet;
        
        this.myPlayer = players.get(0);
        for(int i = 0; i < playersNumber; i++){
            if(players.get(i) != myPlayer){
                players.set(i, new CPUPlayer(players.get(i)));
            }
        }
            
        this.discards = new ArrayList();
        endOfRound = false;
    }

    public void newRound(){
        //1- Devolver cartas a la baraja.
        //2- Retirar dealer y ciegas.
        //3- Retirar eliminados
        //4- Asignar dealer y ciegas.
        //5- Barajar
        //6- Actualizar parámetros.
        
        //-- 1 --//
        takeDiscards();
        for(PokerPlayer p : players){
            for(FrenchCard c : p.removeHand()){
                pokerDeck.giveCardBack(c);
            }
        }
        
        while(!discards.isEmpty()){
            pokerDeck.giveCardBack(discards.remove(0));
        }
        
        // -- 2 -- //
        
        dealer.setDealer(false);
        players.get((dealerIndex+1)%playersNumber).setSmallBlind(false);
        players.get((dealerIndex+2)%playersNumber).setBigBlind(false);
        
        //-- 3 --//
        
        for(PokerPlayer p : players){
            p.newRound();
        }
        removeLosers();
        
        
        //-- 4 --//
         
        dealerIndex = (dealerIndex+1)%playersNumber;
        dealer = players.get(dealerIndex);
        dealer.setDealer(true);
        players.get((dealerIndex+1)%playersNumber).setSmallBlind(true);
        players.get((dealerIndex+2)%playersNumber).setBigBlind(true);
        
        currentPlayer = dealer;
        currentPlayerIndex = dealerIndex;
        
        // -- 5 -- //
        
        pokerDeck.shuffle();
        
        // -- 6 -- //
        if(playersNumber == 1){
            this.roundKind = PokerRoundKind.ENDOFGAME;
        }
        else{
            this.roundKind = PokerRoundKind.DEALING;
        }
        
        this.bettingRoundNumber = 0; 
        this.gameRound++;
    }
    
    private void removeLosers(){
        for(int i = players.size()-1; i >= 0; i--){
            if(players.get(i).hasLost()){
                players.remove(i);
            }
        }
        playersNumber = players.size();
    }
    
    public void next(){
        boolean changeRound = endOfRound;
        endOfRound = false;
        switch(this.roundKind){
            case DEALING:
                if(everybodyHasAllCards() && !changeRound){
                    endOfRound = true;
                }
                else if(changeRound){
                    this.roundKind = PokerRoundKind.BETTING;
                    currentPlayerIndex = (dealerIndex+1)%playersNumber;
                    currentPlayer = players.get(currentPlayerIndex);
                    this.bettingRoundNumber++;
                }
                else{
                    currentPlayerIndex = (currentPlayerIndex + 1) % playersNumber;
                    currentPlayer = players.get(currentPlayerIndex);
                }
                break;
                
            case BETTING:
                if((everyBodyHasBet()|| getRemainingPlayersInRound().size() == 1) && !changeRound){
                    endOfRound = true;
                    takeBets();
                }
                else if(changeRound){
                    if(getRemainingPlayersInRound().size() == 1){
                        this.roundKind = PokerRoundKind.EVERYBODYFOLDED;
                    }
                    else if(this.bettingRoundNumber <= this.totalRounds){
                        this.roundKind = PokerRoundKind.DISCARDING;
                    }
                    else{
                        this.roundKind = PokerRoundKind.SHOWDOWN;
                    }
                    
                    currentPlayerIndex = (dealerIndex+1)%playersNumber;
                    currentPlayer = players.get(currentPlayerIndex);
                }
                else{
                    currentPlayerIndex = (currentPlayerIndex + 1) % playersNumber;
                    currentPlayer = players.get(currentPlayerIndex);
                }
                
                if(!endOfRound && !changeRound && (currentPlayer.hasFolded() || currentPlayer.isAllInSituation()) 
                ||(changeRound && this.roundKind != PokerRoundKind.EVERYBODYFOLDED && currentPlayer.hasFolded())) next();
                break;
            case DISCARDING:
            case RETRIEVING:
                if(currentPlayer.isDealer() && !changeRound){
                    endOfRound = true;
                    if(roundKind == PokerRoundKind.DISCARDING){
                        this.bettingRoundNumber++;
                        takeDiscards();
                    }
                }
                else{
                    if(changeRound){
                        if(roundKind==PokerRoundKind.DISCARDING) this.roundKind = PokerRoundKind.RETRIEVING;
                        else if(roundKind==PokerRoundKind.RETRIEVING) this.roundKind = PokerRoundKind.BETTING;
                    }
                    currentPlayerIndex = (currentPlayerIndex + 1) % playersNumber;
                    currentPlayer = players.get(currentPlayerIndex);                   
                }
                if(!endOfRound && !changeRound && currentPlayer.hasFolded() ||   
                        (changeRound && (currentPlayer.hasFolded()
                        || (roundKind == PokerRoundKind.BETTING && currentPlayer.isAllInSituation())))) next();
                break;
            case SHOWDOWN:
                if(currentPlayer.isDealer()&& !changeRound){
                    endOfRound = true;
                }
                else if(changeRound){
                    newRound();
                }
                else{
                    currentPlayerIndex = (currentPlayerIndex + 1) % playersNumber;
                    currentPlayer = players.get(currentPlayerIndex);
                }
                if(!endOfRound && !changeRound && currentPlayer.hasFolded()) next();
                break;
                
            case EVERYBODYFOLDED:
                newRound();
                break;
                
        }
    }
    
    public void takeDiscards(){
        for(PokerPlayer p : players){
            ArrayList<FrenchCard> playerDiscards = p.takeDiscards();
            if(playerDiscards != null)
                for(FrenchCard c : playerDiscards){
                    discards.add(c);
                }
        }
    }
    
    public void giveCard(){
        currentPlayer.receive(nextCard());
    }
    
    public void fillPlayerCards(){
        int cardsToFill = 5 - currentPlayer.getPlayerHand().size();
        for(int i = 1; i <= cardsToFill; i++){
            giveCard();
        }
    }

    public ArrayList<AllInPot> getAllInPots() {
        return allInPots;
    }

    public int getBettingRoundNumber() {
        return bettingRoundNumber;
    }

    public int getBigBlindBet() {
        return bigBlindBet;
    }

    public PokerPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public PokerPlayer getDealer() {
        return dealer;
    }

    public int getDealerIndex() {
        return dealerIndex;
    }

    public int getGameRound() {
        return gameRound;
    }

    public ArrayList<PokerPlayer> getPlayers() {
        return players;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public int getPot() {
        return pot;
    }

    public PokerRoundKind getRoundKind() {
        return roundKind;
    }

    public int getSmallBlindBet() {
        return smallBlindBet;
    }

    public int getTotalRounds() {
        return totalRounds;
    }
    
    public ArrayList<PokerPlayer> getRemainingPlayersInRound(){
        ArrayList<PokerPlayer> remaining = new ArrayList();
        for(PokerPlayer p : players){
            if(!p.hasFolded()){
                remaining.add(p);
            }
        }
        return remaining;
    }
    
    public int getCurrentCallBet(){
        int max = 0;
        for(PokerPlayer p : players){
            if(p.getBet() > max){
                max = p.getBet();
            }
        }
        return max;
    }
    
    private ArrayList<PokerPlayer> getPartialWinners(ArrayList<PokerPlayer> playerList, boolean checkAllIn){
        int maxPlayerScore = 0;
        ArrayList<PokerPlayer> winners = new ArrayList();
        for(PokerPlayer p : playerList){
            int playerScore = p.computeHandValue();
            if(!p.hasFolded() && (!p.isAllInSituation() || !checkAllIn) && playerScore > maxPlayerScore){
                winners.clear();
                maxPlayerScore = playerScore;
            }
            if(!p.hasFolded() && (!p.isAllInSituation() || !checkAllIn) && playerScore == maxPlayerScore){
                winners.add(p);
            }
        }
        return winners;
    }
    
    public ArrayList<PokerPlayer> getRoundWinners(){
        ArrayList<PokerPlayer> winners = getPartialWinners(players,true);
        for(PokerPlayer p : winners){
            p.win(pot / winners.size());
            pot -= pot / winners.size();
        }
        
        for(AllInPot aip : allInPots){
            ArrayList<PokerPlayer> allInWinners = getPartialWinners(aip.getPlayersInvolved(), false);
            for(PokerPlayer ap : allInWinners){
                ap.win(aip.getPot() / allInWinners.size());
                aip.setPot(aip.getPot() - aip.getPot() / allInWinners.size());
                pot += aip.getPot();
            }
            winners.addAll(allInWinners);
        }
        allInPots.clear();
        return winners;
    }
    
    public PokerPlayer winAfterEverybodyFolding(){
        PokerPlayer winner = getRemainingPlayersInRound().get(0);
        winner.win(pot);
        pot = 0;
        for(AllInPot a : allInPots){
            winner.win(a.getPot());
        }
        allInPots.clear();
        
        return winner;
    }
    
    private boolean allInSort(PokerPlayer p1, PokerPlayer p2){
        return p1.getBet() < p2.getBet();
    }
    
    public ArrayList<PokerPlayer> getSortedAllInSituationPlayers(){
        ArrayList<PokerPlayer> allInPlayers = new ArrayList();
        for(PokerPlayer p : players){
            if(p.isAllInSituation()){
                allInPlayers.add(p);
            }
        }
        Collections.sort(allInPlayers,new Comparator<PokerPlayer>(){
            @Override
            public int compare(PokerPlayer p1, PokerPlayer p2){
                if(p1.getBet() > p2.getBet()) return 1;
                if(p1.getBet() < p2.getBet()) return -1;
                return 0;
            }   
        });
        return allInPlayers;
    }
    
    public void takeBets(){
        ArrayList<PokerPlayer> allInPlayers = getSortedAllInSituationPlayers();
        int allInBet;
        for(PokerPlayer p : allInPlayers){
            AllInPot allIn = new AllInPot();
            allInPots.add(allIn);
            allInBet = p.getBet();
            
            allIn.setPot(pot);
            pot = 0;
            
            for(PokerPlayer q : players){
                if(q.getBet() != 0){
                    allIn.add(q.takeBet(allInBet), q);
                }
            }
            if(allIn.getPot() == 0) allInPots.remove(allIn);
        }
        
        for(PokerPlayer p : players){
            this.pot += p.takeBet();
        }
    }

    public PokerPlayer getMyPlayer() {
        return myPlayer;
    }

    public boolean isEndOfRound() {
        return endOfRound;
    }
    
}
