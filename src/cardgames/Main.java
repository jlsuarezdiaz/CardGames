/*
 * Author: Juan Luis Suárez Díaz
 * September, 2015
 * Card Games
 */
package cardgames;

import Games.Poker.GUI.PokerIntro;
import Games.Poker.GUI.PokerView;
import Games.SotaCabrona.GUI.SotaCabronaIntro;
import Games.SotaCabrona.GUI.SotaCabronaView;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Luis
 */
public class Main {

    private static void showNotImplementedMessage(){
        JOptionPane.showMessageDialog(null,"Error: este juego no ha sido implementado todavía. Inténtelo de nuevo en futuras versiones.",
                "NOT IMPLEMENTED ERROR",JOptionPane.ERROR_MESSAGE);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ProgramStart start = new ProgramStart(null, false);
        start.showView(5000);
        runGameSelection();
    }
    
    public static void runGameSelection(){
        IntroGames intro = new IntroGames(null, true);
        GameSelection game = intro.getGame();
        switch(game){
            case POKER:
                PokerView pokerView = new PokerView();
                PokerIntro pokerIntro = new PokerIntro(pokerView, true);
                pokerView.setPoker(pokerIntro.getPoker());
                pokerView.showView();
                break;    
            case UNO:
                showNotImplementedMessage();
                runGameSelection();
                break; 
            case SOTA_CABRONA:
                SotaCabronaView sotaView = new SotaCabronaView();
                SotaCabronaIntro sotaIntro = new SotaCabronaIntro(sotaView, true);
                sotaView.setSotaCabrona(sotaIntro.getSotaCabrona());
                sotaView.showView();
                break;
            case HEARTS:
                showNotImplementedMessage();
                runGameSelection();
                break;
            case TEXAS_HOLDEM:
                showNotImplementedMessage();
                runGameSelection();
                break;
            case BROOM:
                showNotImplementedMessage();
                runGameSelection();
                break;
            case SOLITAIRE:
                showNotImplementedMessage();
                runGameSelection();
                break;
            case ERROR:
            default:
                JOptionPane.showMessageDialog(null, "Se ha producido un error al elegir el juego.",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                break;
        }
        //System.out.println(game.toString());
    }
}
