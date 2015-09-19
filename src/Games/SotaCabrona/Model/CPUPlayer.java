/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.SotaCabrona.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

/**
 *
 * @author Juan Luis
 */
public class CPUPlayer extends Player{
    private Timer CPUBrain;
    
    private int reflexesRate;
    
    private int speedRate;
    
    private int errorRate;
    
    private static final int minReflexesRate = 200;
    private static final int maxReflexesRate = 5000;
    
    private static final int minSpeedRate = 500;
    private static final int maxSpeedRate = 6000;
    
    private static final int minErrorRate = 0;
    private static final int maxErrorRate = 10;
    
    private static final int brainTick =  50;
    
    private static final Random decider = new Random();
    
    private int reflexesCount;
    private int reflexesGoal;
    
    private int speedCount;
    private int speedGoal;
    

    
    private int nextRand(int min, int max){
        return decider.nextInt(max-min+1) + min;
    }
    
    private void initializeBrain(){
        this.reflexesCount = 0;
        this.reflexesGoal = -1;
        
        this.speedCount = 0;
        this.speedGoal = -1;
        
        this.
        
        CPUBrain = new Timer(brainTick, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if((isSandwich() || isSameValue()) && game.isPlaying()){
                    if(reflexesGoal == -1){
                        reflexesGoal = nextRand(minReflexesRate,reflexesRate);
                        reflexesCount = 0;
                    }
                    
                    reflexesCount += brainTick;
                    //System.out.println(reflexesCount);
                    if(reflexesCount >= reflexesGoal){
                        touchHeap();
                        //System.out.println(getName() + " touchHeap " + reflexesCount + " " + reflexesGoal);
                        reflexesGoal = -1;
                    }
                }
                else{
                    reflexesGoal = -1;
                    if(nextRand(0,100) < errorRate){
                        touchHeap();
                    }
                }
                
                if(isMyTurn() && !game.isEndOfGame() && !game.isWinHeapState()){
                    if(speedGoal == -1){
                        speedGoal = nextRand(minSpeedRate, speedRate);
                        speedCount = 0;
                    }
                    
                    speedCount += brainTick;
                    
                    if(speedCount >= speedGoal + (game.isPlaying()?0:2000)){
                        dropNextCard();
                        //System.out.println(getName() + " dropNextCard " + speedCount + " " + speedGoal);
                        speedGoal = -1;
                    }
                }
            }
        });
        CPUBrain.start();
    }
    
    private void adjustStatsIfOutOfBounds(){
        if(reflexesRate < minReflexesRate) reflexesRate = minReflexesRate;
        if(reflexesRate > maxReflexesRate) reflexesRate = maxReflexesRate;
        if(speedRate < minSpeedRate) speedRate = minSpeedRate;
        if(speedRate > maxSpeedRate) speedRate = maxSpeedRate;
        if(errorRate < minErrorRate) errorRate = minErrorRate;
        if(errorRate > maxErrorRate) errorRate = maxErrorRate;
    }
    
    public void setStats(int reflexesRate, int speedRate, int errorRate){
        this.reflexesRate = reflexesRate;
        this.speedRate = speedRate;
        this.errorRate = errorRate;
        adjustStatsIfOutOfBounds();
    }
    
    
    public void setDefaultStats(){
        setStats(500,1000,1);
    }
    
    public CPUPlayer(String name, SotaCabrona game){
        super(name, game);
        setDefaultStats();
        initializeBrain();
    }
    
    public CPUPlayer(String name, SotaCabrona game, int reflexesRate, int speedRate, int errorRate){
        super(name,game);
        setStats(reflexesRate,speedRate,errorRate);
        initializeBrain();
    }
    
    public CPUPlayer(Player p){
        super(p);
        setDefaultStats();
        initializeBrain();
    }
    
    public CPUPlayer(Player p, int reflexesRate, int speedRate, int errorRate){
        super(p);
        setStats(reflexesRate, speedRate, errorRate);
        initializeBrain();
    }

    public int getErrorRate() {
        return errorRate;
    }

    public int getReflexesRate() {
        return reflexesRate;
    }

    public int getSpeedRate() {
        return speedRate;
    }

    public static int getMinErrorRate() {
        return minErrorRate;
    }

    public static int getMinReflexesRate() {
        return minReflexesRate;
    }

    public static int getMinSpeedRate() {
        return minSpeedRate;
    }

    public static int getMaxErrorRate() {
        return maxErrorRate;
    }

    public static int getMaxReflexesRate() {
        return maxReflexesRate;
    }

    public static int getMaxSpeedRate() {
        return maxSpeedRate;
    }
    
    
}
