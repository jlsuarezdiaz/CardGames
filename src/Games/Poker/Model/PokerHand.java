/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.Poker.Model;

/**
 *
 * @author Juan Luis
 */
public enum PokerHand {
    HIGHCARD, PAIR, TWO_PAIR, THREESOME, STRAIGHT, FLUSH, FULL, POKER, STRAIGHT_FLUSH, ROYAL_FLUSH, REPOKER;
    
    public int getRankingValue(){
        switch(this){
            case HIGHCARD:
                return 1;
            case PAIR:
                return 2;
            case TWO_PAIR:
                return 3;
            case THREESOME:
                return 4;
            case STRAIGHT: //Escalera
                return 5;
            case FLUSH: //Color
                return 6;
            case FULL:
                return 7;
            case POKER:
                return 8;
            case STRAIGHT_FLUSH:
                return 9;
            case ROYAL_FLUSH:
                return 10;
            case REPOKER:
                return 11;
            default:
                return 0;
        }
    }
}
