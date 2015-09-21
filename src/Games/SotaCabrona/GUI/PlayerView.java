/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.SotaCabrona.GUI;

import GUI.FrenchCardBack;
import Games.SotaCabrona.Model.Player;
import Model.Card;
import Model.FrenchCard;
import Model.FrenchSuit;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 *
 * @author Juan Luis
 */
public class PlayerView extends javax.swing.JPanel {

    Player playerModel;
    
    private String dropAddress;
    private String touchAddress;
    
    private ArrayList<JLabel> lifeLabs;
    /**
     * Creates new form PlayerView
     */
    public PlayerView() {
        initComponents();
      
        
        timeBar.setStringPainted(true);
        timeBar.setString("");
        timeBar.setSize(timeBar.getPreferredSize());
        timeBar.setForeground(Color.RED);
        cardSizeBar.setMaximum(52);
        dropAddress = touchAddress = null;
        
        lifeLabs = new ArrayList();
        lifeLabs.add(lifeLab1);
        lifeLabs.add(lifeLab2);
        lifeLabs.add(lifeLab3);
        lifeLabs.add(lifeLab4);
        lifeLabs.add(lifeLab5);
    }

    public void setPlayer(Player p){
        this.playerModel = p;
        cardView.setCard(new FrenchCard("A", FrenchSuit.SPADES), 
                (p.getMyCards().size() > 0)?FrenchCardBack.BLUE:FrenchCardBack.RED, true);
        nameLab.setText(p.getName());
        cardSizeText.setText(Integer.toString(p.getMyCards().size()));
        cardSizeBar.setValue(p.getMyCards().size());
        timeBar.setMaximum(Player.getPlayerTime());
        timeBar.setValue(p.getTimerCount());
        this.setBackground((p.isMyTurn())?new Color(0xFACC2E):new Color(0xF0F0F0));
        
        String newDropAddress = null, newTouchAddress = null;
        if(p.isDropFlag())
            newDropAddress = "/Media/yellow_light_xs.png";
        else if(p.isErrorDropFlag())
            newDropAddress = "/Media/red_light_xs.png";
        else if(p.isTimeOutFlag())
            newDropAddress = "/Media/purple_light_xs.png";
        else
            newDropAddress = "/Media/transparent_light_xs.png";
        
        if(p.isSameValueFlag())
            newTouchAddress = "/Media/blue_light_xs.png";
        else if(p.isSandwichFlag())
            newTouchAddress = "/Media/green_light_xs.png";
        else if(p.isErrorTouchFlag())
            newTouchAddress = "/Media/red_light_xs.png";
        else
            newTouchAddress = "/Media/transparent_light_xs.png";
        
        if(dropAddress == null || !dropAddress.equals(newDropAddress)){
            dropAddress = newDropAddress;
            dropLab.setIcon(new javax.swing.ImageIcon(getClass().getResource(dropAddress)));
        }
        if(touchAddress == null || !touchAddress.equals(newTouchAddress)){
            touchAddress = newTouchAddress;
            touchLab.setIcon(new javax.swing.ImageIcon(getClass().getResource(touchAddress)));
        }
        
        if(p.getMyCards().size() == 0){
            int rev = p.getReviveOpportunities();
            for(int i = 0; i < lifeLabs.size(); i++){
                lifeLabs.get(i).setVisible(true);
                if(i < rev)
                    lifeLabs.get(i).setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/blue_light_xs.png")));
                else
                    lifeLabs.get(i).setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/transparent_light_xs.png")));
            }
            if(rev == 0){
                this.setBackground(Color.DARK_GRAY);
                this.setEnabled(false);
            }
        }
        else{
            for(int i = 0; i < lifeLabs.size(); i++)
                lifeLabs.get(i).setVisible(false);
        }
        
        this.repaint();
        this.revalidate();
    }
    
    public void addLightChangeListener(PropertyChangeListener e){
        this.dropLab.addPropertyChangeListener("icon", e);
        this.touchLab.addPropertyChangeListener("icon", e);
    }
    
    public Color getDropLightColor(){
       if(dropAddress == null) return null;
       switch(dropAddress){
           case "/Media/yellow_light_xs.png":
               return Color.YELLOW;
           case "/Media/red_light_xs.png":
               return Color.RED;
           case "/Media/purple_light_xs.png":
               return Color.PINK;
           case "/Media/transparent_light_xs.png":
           default:
               return null;
       }
    }
    
    public Color getTouchLightColor(){
        if(touchAddress == null) return null;
        switch(touchAddress){
            case "/Media/blue_light_xs.png":
                return Color.BLUE;
            case "/Media/green_light_xs.png":
                return Color.GREEN;
            case "/Media/red_light_xs.png":
                return Color.RED;
            case "/Media/transparent_light_xs.png":
            default:
                return null;
        }
    }
    
    public Player getPlayer(){
        return playerModel;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLab = new javax.swing.JLabel();
        cardView = new GUI.CardView();
        timeBar = new javax.swing.JProgressBar();
        cardSizeBar = new javax.swing.JProgressBar();
        cardSizeText = new javax.swing.JTextField();
        dropLab = new javax.swing.JLabel();
        touchLab = new javax.swing.JLabel();
        lifeLab1 = new javax.swing.JLabel();
        lifeLab2 = new javax.swing.JLabel();
        lifeLab3 = new javax.swing.JLabel();
        lifeLab4 = new javax.swing.JLabel();
        lifeLab5 = new javax.swing.JLabel();

        nameLab.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        nameLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameLab.setText("Nombre");

        cardSizeBar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cardSizeBarStateChanged(evt);
            }
        });

        cardSizeText.setEditable(false);
        cardSizeText.setBackground(new java.awt.Color(255, 255, 255));
        cardSizeText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cardSizeText.setText("0");

        dropLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/transparent_light_xs.png"))); // NOI18N

        touchLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/transparent_light_xs.png"))); // NOI18N

        lifeLab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/transparent_light_xs.png"))); // NOI18N

        lifeLab2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/transparent_light_xs.png"))); // NOI18N

        lifeLab3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/transparent_light_xs.png"))); // NOI18N

        lifeLab4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/transparent_light_xs.png"))); // NOI18N

        lifeLab5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/transparent_light_xs.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLab, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cardView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(dropLab, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6)
                                        .addComponent(touchLab, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cardSizeText, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cardSizeBar, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(timeBar, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lifeLab1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(lifeLab2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(lifeLab3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(lifeLab4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(lifeLab5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(nameLab, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dropLab, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(touchLab, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addComponent(cardSizeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(cardSizeBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(timeBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lifeLab1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lifeLab2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lifeLab3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lifeLab4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lifeLab5, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cardView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cardSizeBarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cardSizeBarStateChanged
        double frac = ((double)cardSizeBar.getValue())/((double)cardSizeBar.getMaximum());
        cardSizeBar.setStringPainted(true);
        //cardSizeBar.setString((playerModel==null)?"":Integer.toString(playerModel.getMyCards().size()));
        cardSizeBar.setString("");
        cardSizeBar.setSize(cardSizeBar.getPreferredSize());
        if(frac < 0.25){
            cardSizeBar.setForeground(new Color(0xF5A9A9));
        }
        else if(frac < 0.5){
            cardSizeBar.setForeground(new Color(0xF2F5A9));
        }
        else if(frac < 0.75){
            cardSizeBar.setForeground(new Color(0xA9F5A9));
        }
        else{
            cardSizeBar.setForeground(new Color(0xA9F5F2));
        }
    }//GEN-LAST:event_cardSizeBarStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar cardSizeBar;
    private javax.swing.JTextField cardSizeText;
    private GUI.CardView cardView;
    private javax.swing.JLabel dropLab;
    private javax.swing.JLabel lifeLab1;
    private javax.swing.JLabel lifeLab2;
    private javax.swing.JLabel lifeLab3;
    private javax.swing.JLabel lifeLab4;
    private javax.swing.JLabel lifeLab5;
    private javax.swing.JLabel nameLab;
    private javax.swing.JProgressBar timeBar;
    private javax.swing.JLabel touchLab;
    // End of variables declaration//GEN-END:variables
}
