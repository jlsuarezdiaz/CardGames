/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Games.Broom.Model;

import java.util.ArrayList;

/**
 *
 * @author Javier
 */
public class Prize {
    private int numberCards;
    private int numberOros;
    private int numberSeven;
    private boolean hasSevenOros;
    
    public Prize(int nC, int nO,int nS, boolean hSO){
        numberCards = nC;
        numberOros = nO;
        numberSeven = nS;
        hasSevenOros = hSO;
    }

    public int getNumberCards() {
        return numberCards;
    }

    public int getNumberOros() {
        return numberOros;
    }

    public int getNumberSeven() {
        return numberSeven;
    }

    public boolean hasSevenOros() {
        return hasSevenOros;
    }

    
    
}
