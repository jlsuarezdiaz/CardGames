/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Games.Broom.GUI;
import Games.Broom.Model.Broom;
import java.util.ArrayList;
/**
 *
 * @author Javier
 */
public class GameMain {
    
    public static void main (String[] args){
        ArrayList<String> names = new ArrayList<>();
        
        Broom escobaModel = Broom.getInstance();
        BroomView escobaView = new BroomView();
        
        PlayerNamesCapture namesCapture = new PlayerNamesCapture(escobaView,true);
        
        
        names = namesCapture.getNames();
        escobaModel.initGame(names);
        escobaView.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        escobaView.setBroom(escobaModel);
        
        escobaView.showView();
    }
}
