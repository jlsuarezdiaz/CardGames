/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.Broom.Model;

import Model.SpanishCard;
import java.util.ArrayList;

/**
 *
 * @author Javier
 */
public class CPUPlayer extends Player {
    private class Jugada {
        public SpanishCard hand;
        public ArrayList<SpanishCard> table = new ArrayList<>();
        
        public int puntuacion() {
            int sum = 0;
            for (SpanishCard card: table) {
                sum += Integer.valueOf(card.getValue()) >= 10 ? Integer.valueOf(card.getValue())-2 : Integer.valueOf(card.getValue());
            }
            sum += Integer.valueOf(hand.getValue()) >= 10 ? Integer.valueOf(hand.getValue())-2 : Integer.valueOf(hand.getValue());
        return sum;
        }
        
        //Contains 7o, 7, o
        //NumCards
    }
    
    private String name;
 
    public CPUPlayer(String name) {
        super(name);
    }
       
    public void playCPU(ArrayList<SpanishCard> table){
        ArrayList<Jugada> jugadas = new ArrayList<>();
        for (SpanishCard c : cards) {
            Jugada jugada = new Jugada();
            jugada.hand = c;
            /*int i = 0;
            while (i < table.size() && jugada.puntuacion() < 15) {
                
            }*/
        }
        
    }
}
