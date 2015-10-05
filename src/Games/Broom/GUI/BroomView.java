/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.Broom.GUI;

import GUI.CardView;
import GUI.NarratorView;
import GUI.SpanishCardBack;
import Games.Broom.Model.Broom;
import Games.Broom.Model.Player;
import Games.Poker.Model.CPUPlayer;
import Model.Card;
import Model.SpanishCard;
import java.awt.Color;
import java.awt.Component;
import static java.lang.System.exit;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Javier
 */
public class BroomView extends javax.swing.JFrame {
    private Broom escobaModel;
    private Player currentPlayer;
    
    public void setBroom(Broom escobaModel){
        this.escobaModel = escobaModel;
        player1.setPlayer(escobaModel.getMyPlayer());
        CPU1.setPlayer(escobaModel.getPlayers().get(1));
        CPU2.setPlayer(escobaModel.getPlayers().get(2));       
        
        
        currentPlayer = escobaModel.getCurrentPlayer(); 
        
        paint();
        
        this.table.removeAll();
        this.table.addSelectionAtMouseListening();
        this.table.add((ArrayList<Card>)(ArrayList<? extends Card>)escobaModel.getTableCards(), SpanishCardBack.RED, false);

        play.setEnabled(true);
        repaint();
        revalidate();
    }   
    
    private void paint(){
        if (currentPlayer == escobaModel.getMyPlayer()){
            player1.setBackground(Color.RED);
            CPU1.setBackground(Color.darkGray);
            CPU2.setBackground(Color.darkGray);
        }
        else if (currentPlayer == escobaModel.getPlayers().get(1)){
            player1.setBackground(Color.darkGray);
            CPU2.setBackground(Color.darkGray);
            CPU1.setBackground(Color.RED);
        }
        else{
            player1.setBackground(Color.darkGray);
            CPU1.setBackground(Color.darkGray);
            CPU2.setBackground(Color.RED);
        }   
    }

    public void showView(){
        this.setVisible(true);
        (new NarratorView(this)).showDialog("JUGAR", "Bienvenido a esta partida de la escoba.", "Pulsa el botón para empezar a jugar.", null);
    }

    private void nextPerform(){
        escobaModel.nextTurn();
        
        if (escobaModel.endOfGame()){
            ArrayList<Player> winners = escobaModel.tellTheWinner();
            String w = "";
                
            for (Player p: winners)
                w += p.getName();
            
            (new NarratorView(this)).showDialog("El ganador de la partida es...",w,"Gracias por jugar", null);
        }
    }
    
    /**
     * Creates new form BroomView
     */
    public BroomView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        play = new javax.swing.JButton();
        table = new GUI.Hand();
        player1 = new Games.Broom.GUI.PlayerView();
        CPU1 = new Games.Broom.GUI.CPUPlayerView();
        CPU2 = new Games.Broom.GUI.CPUPlayerView();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        play.setText("Jugar");
        play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playActionPerformed(evt);
            }
        });

        table.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CPU1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87)
                        .addComponent(CPU2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(player1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(229, 229, 229)
                                .addComponent(play, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(table, javax.swing.GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE))
                        .addGap(0, 224, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CPU1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CPU2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(table, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(player1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(play, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(104, 104, 104))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
   
    
    private void playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playActionPerformed
        if (escobaModel.getCurrentPlayer() != escobaModel.getMyPlayer()){
            escobaModel.playCPU();
        }
        else{
            ArrayList<SpanishCard> selected = (ArrayList<SpanishCard>)(ArrayList<? extends Card>)this.table.getSelectedCards();
            ArrayList<SpanishCard> s = player1.getSelectedCard(player1.getCards());

            boolean goodmove = escobaModel.goodMove(s.get(0),selected);

            player1.getPlayer().discardCard(s.get(0));

            if (!goodmove){
                escobaModel.getTableCards().add(s.get(0));
            }
            else{
                for (SpanishCard c:selected){
                    player1.getPlayer().addCardHeap(c);
                    escobaModel.discardTableCard(c);
                }

                player1.getPlayer().addCardHeap(s.get(0));
            }
        }
            
        nextPerform();
        setBroom(escobaModel);
        
        repaint();
        revalidate();
    }//GEN-LAST:event_playActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BroomView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BroomView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BroomView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BroomView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BroomView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Games.Broom.GUI.CPUPlayerView CPU1;
    private Games.Broom.GUI.CPUPlayerView CPU2;
    private javax.swing.JButton play;
    private Games.Broom.GUI.PlayerView player1;
    private GUI.Hand table;
    // End of variables declaration//GEN-END:variables
}
