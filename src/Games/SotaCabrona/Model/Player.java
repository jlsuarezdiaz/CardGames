/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.SotaCabrona.Model;

import Model.FrenchCard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.Timer;

/**
 * Sota Cabrona player.
 * @author Juan Luis
 */
public class Player {
    private ArrayList<FrenchCard> myCards;
    
    private String name;
    
    protected SotaEnvironment game;
    
    private Timer playerTimer;
    private int timerCount;
    private static final int timerTick = 50;
    private static final int playerTime = 5000;
    
    private void initTimer(){
        timerCount = 0;
        playerTimer = new Timer(timerTick, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.nextIfNoCards();
                if(isMyTurn() && game.isPlaying()){
                    timerCount += timerTick;
                
                    if(timerCount >= playerTime){
                        punish();
                        timerCount = 0;
                    }
                }
                else{
                    timerCount = 0;
                }
            }
        });
        playerTimer.start();
    }
    public Player(String name, SotaCabrona game){
        myCards = new ArrayList();
        this.name = name;
        this.game = new SotaEnvironment(game);
        initTimer();
    }
    
    public Player(Player p){
        this.myCards = p.myCards;
        this.name = p.name;
        this.game = p.game;
        initTimer();
    }
    
    public void receiveCard(FrenchCard c){
        myCards.add(c);
    }
    
    public void takeHeap(ArrayList<FrenchCard> cards){
        myCards.addAll(cards);
    }
    
    public void shuffleMyCards(){
        Collections.shuffle(myCards);
    }
    
    public void dropNextCard(){
        if(isMyTurn()){
            if(!myCards.isEmpty())
                game.dropCard(myCards.remove(0));
        }
        else{
            punish();
        }
        timerCount = 0;
    }
    
    public boolean touchHeap(){
        if(game.touchHeap()){
            takeHeap(game.takeHeap());
            return true;
        }
        else{
            punish();
            return false;
        }
    }
    
    public void punish(){
        ArrayList<FrenchCard> punish = new ArrayList();
        int punishSize = Integer.min(5, myCards.size());
        for(int i = 0; i < punishSize; i++){
            punish.add(myCards.remove(0));
        }
        game.putOnHeap(punish);
    }
    
    public boolean isMyTurn(){
        return game.isTurnOf(this);
    }

    public String getName() {
        return name;
    }

    public ArrayList<FrenchCard> getMyCards() {
        return myCards;
    } 

    public int getTimerCount() {
        return timerCount;
    }

    public static int getPlayerTime() {
        return playerTime;
    }
    
    
    
}
