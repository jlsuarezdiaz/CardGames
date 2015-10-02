/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.Broom.GUI;

import Games.Broom.Model.Broom;
import Games.Broom.Model.Player;
import java.util.ArrayList;

/**
 *
 * @author Javier
 */
public class BroomView extends javax.swing.JFrame {
    private Broom escobaModel;
    private static int RONDAS = 0;
    private Player currentPlayer;
    private void setRondas(){
        RONDAS++;
    }
    public void setBroom(Broom escobaModel){
        this.escobaModel = escobaModel;
        player1.setPlayer(escobaModel.getMyPlayer());
        
        if (CPU1.getCards().getComponentCount() == 0){
            //relleno el panel.
        }
        else{
            //actualizo.
        }
        
        //CPU1.setPlayer();
        
        
        /*
        currentPlayer.setBroom(escobaModel);        
        fillCardPanel(table,escobaModel.getTableCards());
        
        ArrayList<Integer> punt = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        
        for (Player p:escobaModel.getPlayers()){
            punt.add(p.getTotalPoints());
            names.add(p.getName());
        }
        
        fillScorePanel(scores,punt,names);
        
        
        set.setEnabled(true);
        play.setEnabled(true);
        nextTurn.setEnabled(false);
        currentPlayer.setVisible(true);*/
        repaint();
        revalidate();
    }
    /*
    private void nextTurnActionPerformed(java.awt.event.ActionEvent evt) {                                         
        boolean next = escobaModel.nextTurn();
        
        if (
    
    
    
    
        if (!next){ //ha habido reparto nuevo.
            escobaModel.resetGame();
            
            boolean endOfGame = escobaModel.endOfGame();
            setRondas();
           
            if (endOfGame){
                ArrayList<Player> winners = escobaModel.tellTheWinner();
                String cadena;
               
                if (winners.size() == 1)
                    cadena = "Winner -->";
                else
                    cadena = "Winners -->";
                    
                for (Player p: winners){
                    cadena += (p.getName() + " ");
                }
                
                mensaje.setText(cadena);
            }
        }
        
        setBroom(escobaModel);
        
        set.setEnabled(true);
        play.setEnabled(true);
        nextTurn.setEnabled(false);
        showCards.setEnabled(true);
        
        repaint();
        revalidate();
    }                                        

    private void setActionPerformed(java.awt.event.ActionEvent evt) {                                    
        ArrayList<Card> s = currentPlayer.getSelectedCard(currentPlayer.getCards());
        escobaModel.getTableCards().add(s.get(0));
        currentPlayer.getPlayer().discardCard(s.get(0));
        
        setBroom(escobaModel);
        
        set.setEnabled(false);
        play.setEnabled(false);
        nextTurn.setEnabled(false);
        
        repaint();
        revalidate();
    }                                   

    private void playActionPerformed(java.awt.event.ActionEvent evt) {                                     
        ArrayList<Card> selected = getSelectedTableCards(table);        
        ArrayList<Card> s = currentPlayer.getSelectedCard(currentPlayer.getCards());
            
        boolean goodmove = escobaModel.goodMove(s.get(0),selected);
        
        currentPlayer.getPlayer().discardCard(s.get(0));
            
        if (!goodmove){
            escobaModel.getTableCards().add(s.get(0));
        }
        else{
            if (selected.size() == escobaModel.getTableCards().size()){
                currentPlayer.getPlayer().setScore(1);
                    
                for (Card c:selected){
                    currentPlayer.getPlayer().addCardHeap(c);
                    escobaModel.discardTableCard(c);
                }
                
                currentPlayer.getPlayer().addCardHeap(s.get(0));
            }
            else{
                for (Card c:selected){
                    currentPlayer.getPlayer().addCardHeap(c);
                    escobaModel.discardTableCard(c);
                }
                
                currentPlayer.getPlayer().addCardHeap(s.get(0));
           }
        }
        
        
        setBroom(escobaModel);
        
        set.setEnabled(false);
        play.setEnabled(false);
        nextTurn.setEnabled(false);
        
        
        repaint();
        revalidate();
    }                                    

    private void showCardsActionPerformed(java.awt.event.ActionEvent evt) {                                          
        if(currentPlayer.isVisible())
            currentPlayer.setVisible(false);
        else
            currentPlayer.setVisible(true);
        
        nextTurn.setEnabled(true);
        showCards.setEnabled(false);
        repaint();
        revalidate();
        
    }                                         

    public void showView() {
        this.setVisible(true);
    }
    
    public void fillScorePanel (JPanel aPanel,ArrayList <Integer> aList,ArrayList<String> names){
        aPanel.removeAll();
        
        for(int i = 0; i < names.size(); i++){
            ScoreView aView = new ScoreView();
            aView.setScore(aList.get(i),names.get(i));
            aView.setVisible(true);
            aPanel.add(aView);
        }
        
        aPanel.repaint();
        aPanel.revalidate();
    }
    
    public void fillCardPanel (JPanel aPanel,ArrayList <Card> aList){
        aPanel.removeAll();
        
        for(Card t: aList){
            CardView aCardView = new CardView();
            aCardView.setCard(t);
            aCardView.setVisible(true);
            aPanel.add(aCardView);
        }
        
        aPanel.repaint();
        aPanel.revalidate();
    }
    
    
    public ArrayList<Card> getSelectedTableCards(JPanel aPanel){
        CardView tv;
        ArrayList<Card> output = new ArrayList<>();
        
        for (Component c: aPanel.getComponents()){
            tv = (CardView)c;
            
            if (tv.isSelected())
                output.add(tv.getCard());
        }
        
        return output;
    }
*/
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

        table = new javax.swing.JPanel();
        player1 = new Games.Broom.GUI.PlayerView();
        CPU1 = new Games.Broom.GUI.CPUPlayerView();
        CPU2 = new Games.Broom.GUI.CPUPlayerView();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        table.setBorder(javax.swing.BorderFactory.createTitledBorder("Mesa"));
        table.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(CPU1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83)
                        .addComponent(CPU2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(player1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 310, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(CPU2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(CPU1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(table, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(player1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    private Games.Broom.GUI.PlayerView player1;
    private javax.swing.JPanel table;
    // End of variables declaration//GEN-END:variables
}
