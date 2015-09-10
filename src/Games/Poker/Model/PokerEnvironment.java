/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.Poker.Model;

/**
 * Class PokerEnvironment.
 * A class with all necessary information for any player in a poker game,
 * hiding private information such as opponent cards.
 * @author Juan Luis
 */
public class PokerEnvironment {
    private Poker game;
    
    public PokerEnvironment(Poker p){
        game = p;
    }
    /*public int getOpponentNumberOfCards(PokerPlayer player){
        return 0;
    }*/

    int getBettingRoundNomber() {
        return game.getBettingRoundNumber();
    }
    
    public int getCurrentCallBet(){
        return game.getCurrentCallBet();
    }
    
    public boolean everybodyHasAllCards(){
        return game.everybodyHasAllCards();
    }
    
    public boolean everybodyHasBetted(){
        return game.everyBodyHasBet();
    }
    
    public PokerPlayer getCurrentPlayer(){
        return game.getCurrentPlayer();
    }
    
    public PokerRoundKind getRoundKind(){
        return game.getRoundKind();
    }
    
    public int getSmallBlindBet(){
        return game.getSmallBlindBet();
    }
    
    public int getBigBlindBet(){
        return game.getBigBlindBet();
    }
    
    public int getPlayersAlreadyBetInCurrentRound(){
        int players = 0;
        for(PokerPlayer p : game.getPlayers()){
            if(p.getBet() != 0){
                players++;
            }
        }
        return players;
    }
    
    public boolean isEndOfRound(){
        return game.isEndOfRound();
    }
}
