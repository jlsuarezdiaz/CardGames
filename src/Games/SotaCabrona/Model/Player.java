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
    
    private boolean sandwichFlag;
    private boolean sameValueFlag;
    private boolean errorDropFlag;
    private boolean errorTouchFlag;
    private boolean dropFlag;
    private boolean timeOutFlag;
    private static final int flagDeactivateTime = 1000;
    private int sandwichFlagCount;
    private int sameValueFlagCount;
    private int errorDropFlagCount;
    private int errorTouchFlagCount;
    private int dropFlagCount;
    private int timeOutFlagCount;
    
    private void initFlags(){
        sandwichFlag = false;
        sameValueFlag = false;
        errorDropFlag = false;
        errorTouchFlag = false;
        dropFlag = false;
        timeOutFlag = false;
        sandwichFlagCount = -1;
        sameValueFlagCount = -1;
        errorDropFlagCount = -1;
        errorTouchFlagCount = -1;
        dropFlagCount = -1;
        timeOutFlagCount = -1;
    }
    
    private void updateFlags(){
        if(sandwichFlagCount >= 0) sandwichFlagCount+=timerTick;
        if(sameValueFlagCount >= 0) sameValueFlagCount+=timerTick;
        if(errorDropFlagCount >= 0) errorDropFlagCount+=timerTick;
        if(errorTouchFlagCount >= 0) errorTouchFlagCount+=timerTick;
        if(dropFlagCount >= 0) dropFlagCount+=timerTick;
        if(timeOutFlagCount >= 0) timeOutFlagCount+=timerTick;
        if(sandwichFlagCount >= flagDeactivateTime){
            sandwichFlag = false;
            sandwichFlagCount = -1;
        }
        if(sameValueFlagCount >= flagDeactivateTime){
            sameValueFlag = false;
            sameValueFlagCount = -1;
        }
        if(errorDropFlagCount >= flagDeactivateTime){
            errorDropFlag = false;
            errorDropFlagCount = -1;
        }
        if(errorTouchFlagCount >= flagDeactivateTime){
            errorTouchFlag = false;
            errorTouchFlagCount = -1;
        }
        if(dropFlagCount >= flagDeactivateTime){
            dropFlag = false;
            dropFlagCount = -1;
        }
        if(timeOutFlagCount >= flagDeactivateTime){
            timeOutFlag = false;
            timeOutFlagCount = -1;
        }
    }
    
    private void initSandwichFlag(){
        sandwichFlag = true;
        sandwichFlagCount = 0;
    }
    private void initSameValueFlag(){
        sameValueFlag = true;
        sameValueFlagCount = 0;
    }
    private void initErrorDropFlag(){
        errorDropFlag = true;
        errorDropFlagCount = 0;
    }
    private void initErrorTouchFlag(){
        errorTouchFlag = true;
        errorTouchFlagCount = 0;
    }
    private void initDropFlag(){
        dropFlag = true;
        dropFlagCount = 0;
    }
    private void initTimeOutFlag(){
        timeOutFlag = true;
        timeOutFlagCount = 0;
    }
    
    
    private void initTimer(){
        initFlags();
        timerCount = 0;
        playerTimer = new Timer(timerTick, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.nextIfNoCards();
                updateFlags();
                if(isMyTurn() && game.isPlaying() && !game.isWinHeapState()){
                    timerCount += timerTick;
                
                    if(timerCount >= playerTime){
                        punish();
                        initTimeOutFlag();
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
    
    protected boolean isSandwich(){
        int size = game.getHeap().size();
        if(size < 3) return false;
        else return (game.getHeap().get(size-1).isValue(game.getHeap().get(size-3).getValue()));
    }
    
    protected boolean isSameValue(){
        int size = game.getHeap().size();
        if(size < 2) return false;
        else return (game.getHeap().get(size-1).isValue(game.getHeap().get(size-2).getValue()));
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
        if(isMyTurn() && !game.isWinHeapState()){
            if(!myCards.isEmpty()){
                game.dropCard(myCards.remove(0));
                initDropFlag();
            }
        }
        else{
            punish();
            initErrorDropFlag();
        }
        timerCount = 0;
    }
    
    public boolean touchHeap(){
        if(game.touchHeap(this)){
            if(isSandwich()) initSandwichFlag();
            if(isSameValue()) initSameValueFlag();
            takeHeap(game.takeHeap());
            return true;
        }
        else{
            if(game.isPlaying()){
                punish();
                initErrorTouchFlag();
            }
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

    public boolean isDropFlag() {
        return dropFlag;
    }

    public boolean isErrorDropFlag() {
        return errorDropFlag;
    }

    public boolean isErrorTouchFlag() {
        return errorTouchFlag;
    }

    public boolean isSameValueFlag() {
        return sameValueFlag;
    }

    public boolean isSandwichFlag() {
        return sandwichFlag;
    }

    public boolean isTimeOutFlag() {
        return timeOutFlag;
    }
    
    
}
