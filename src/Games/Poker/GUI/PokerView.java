/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.Poker.GUI;

import GUI.NarratorView;
import Games.Poker.Model.AllInPot;
import Games.Poker.Model.CPUPlayer;
import Games.Poker.Model.Poker;
import Games.Poker.Model.PokerHand;
import Games.Poker.Model.PokerPlayer;
import Games.Poker.Model.PokerRoundKind;
import Model.FrenchCard;
import Model.FrenchDeck;
import Model.FrenchSuit;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Luis
 */
public class PokerView extends javax.swing.JFrame {

    private Poker pokerModel;
    
    private int roundMarker;
    
    private PokerRoundKind roundKindMarker;

    private void disableAfterEndOfGame() {
        for(Component c : this.getComponents()){
            c.setEnabled(false);
        }
        btContinue.setVisible(true);
        btContinue.setText("TERMINAR");
        btContinue.setBackground(Color.RED);
    }
    
    private void updateRivalPanel(){
        int startIndex = pokerModel.getPlayers().indexOf(pokerModel.getMyPlayer());
        //for(int i = 1; i < pokerModel.getPlayersNumber(); i++){
        //    ((RivalPlayerView)rivalPanel.getComponent(i-1)).setPlayer(pokerModel.getPlayers().get((startIndex+i)%pokerModel.getPlayersNumber()));
        //}
        if(startIndex == -1){
            for(int i = 0; i < pokerModel.getPlayersNumber(); i++){
                ((RivalPlayerView)rivalPanel.getComponent(i)).setPlayer(pokerModel.getPlayers().get(i));                             
            }
        }
        else{
            int panelIndex = 0;
            for(int i = startIndex + 1; i != startIndex; i = (i+1)%pokerModel.getPlayersNumber()){
                ((RivalPlayerView)rivalPanel.getComponent(panelIndex)).setPlayer(pokerModel.getPlayers().get(i));              
                panelIndex++;
            }
        }
    }
    
    private void fillRivalPanel(){
        rivalPanel.removeAll();
        int startIndex = pokerModel.getPlayers().indexOf(pokerModel.getMyPlayer());
        //for(int i = 1; i < pokerModel.getPlayersNumber(); i++){
        //    RivalPlayerView rv = new RivalPlayerView();
        //    rivalPanel.add(rv);
        //    rivalPanel.repaint();
        //    rivalPanel.revalidate();
        //    rv.setPlayer(pokerModel.getPlayers().get((startIndex+i)%pokerModel.getPlayersNumber()));
            
        //}
        if(startIndex == -1){
            for(int i = 0; i < pokerModel.getPlayersNumber(); i++){
                RivalPlayerView rv = new RivalPlayerView();
                rivalPanel.add(rv);
                forcePainting();
                rv.setPlayer(pokerModel.getPlayers().get(i));
            }  
        }
        else{
            for(int i = startIndex + 1; i != startIndex; i = (i+1)%pokerModel.getPlayersNumber()){
                RivalPlayerView rv = new RivalPlayerView();
                rivalPanel.add(rv);
                forcePainting();
                rv.setPlayer(pokerModel.getPlayers().get(i));
            }
        }
    }
    
    private void fillAllInPanel(){
        allInPanel.removeAll();
        for(AllInPot a : pokerModel.getAllInPots()){
            ChipsView cv = new ChipsView();
            allInPanel.add(cv);
            cv.setChips(a.getPot());
            cv.setToolTipText("<html> ");
            for(PokerPlayer p : a.getPlayersInvolved()){
                cv.setToolTipText(cv.getToolTipText() + p.getName() + "<br>");
            }
            cv.setToolTipText(cv.getToolTipText() + " </html>");
        }
        allInPanel.setVisible(!pokerModel.getAllInPots().isEmpty());
        jLabel2.setVisible(!pokerModel.getAllInPots().isEmpty());
        allInScrollPane.setVisible(!pokerModel.getAllInPots().isEmpty());
        allInPanel.repaint();
        
    }
    
    /**
     * Creates new form PokerView
     */
    public PokerView() {
        initComponents();
        roundMarker = 0;
        this.rivalScrollPane.getHorizontalScrollBar().setUnitIncrement(555);
        allInPanel.setVisible(false);
        jLabel2.setVisible(false);
        allInScrollPane.setVisible(false);
        btContinue.setVisible(false);
    }
    
    public void setPoker(Poker p){
        this.pokerModel = p;
        this.myPlayerView.setPlayer(p.getMyPlayer());
        if(rivalPanel.getComponentCount() != p.getPlayersNumber()-(pokerModel.getMyPlayer().hasLost()?0:1)){
            fillRivalPanel();
        }
        else{
            updateRivalPanel();
        }
        this.potView.setChips(pokerModel.getPot());
        fillAllInPanel();
        repaint();
        revalidate();
    }
    
    public void showView() {
        this.setVisible(true);
        (new NarratorView(this)).showDialog("JUGAR", "Bienvenido a esta partida de poker.", "Pulsa el botón para empezar a jugar.", null);
        pokerModel.newRound();
        setPoker(pokerModel);
        gameManager();
    }
    
    public void gameManager(){
        if(!pokerModel.isEndOfRound()){
        switch(pokerModel.getRoundKind()){
            case DEALING:
                if(roundMarker != pokerModel.getGameRound()){
                    roundMarker = pokerModel.getGameRound();
                    this.roundKindMarker = pokerModel.getRoundKind();
                    new NarratorView(this).showDialog(getRealTime(2000), "RONDA " + Integer.toString(roundMarker), null, null);
                }
                else{
                    pokerModel.giveCard();
                }
                performNext();
                break;
            
            case BETTING:
                if(roundKindMarker != pokerModel.getRoundKind()){
                    roundKindMarker = pokerModel.getRoundKind();
                    new NarratorView(this).showDialog(getRealTime(2000), "Ronda de apuestas.",null,null);
                }
                if(pokerModel.getBettingRoundNumber() == 1 && pokerModel.getCurrentPlayer().isSmallBlind() && pokerModel.getCurrentPlayer().getBet() == 0){
                    pokerModel.getCurrentPlayer().bet(pokerModel.getSmallBlindBet());
                    new NarratorView(this).showDialog(getRealTime(2000), pokerModel.getCurrentPlayer().getName(), 
                        "Te toca poner la ciega chica.", null);
                    performNext();
                }
                else if(pokerModel.getBettingRoundNumber() == 1 && pokerModel.getCurrentPlayer().isBigBlind() && pokerModel.getCurrentPlayer().getBet() == 0){
                    pokerModel.getCurrentPlayer().bet(pokerModel.getBigBlindBet());
                    new NarratorView(this).showDialog(getRealTime(2000), pokerModel.getCurrentPlayer().getName(), 
                        "Te toca poner la ciega grande.", null);
                    performNext();
                }
                else{
                    new NarratorView(this).showDialog(getRealTime(2000), "Turno de " + pokerModel.getCurrentPlayer().getName(), null, null);
                    if(pokerModel.getCurrentPlayer() instanceof CPUPlayer){
                        boolean isBet = pokerModel.getCurrentPlayer().getBet() < pokerModel.getCurrentCallBet();
                        switch(((CPUPlayer)pokerModel.getCurrentPlayer()).takeBettingDecision()){
                            case CALL:
                                new NarratorView(this).showDialog(getRealTime(2000), pokerModel.getCurrentPlayer().getName(), isBet?"Lo veo":"Paso", null);
                                break;
                            case FOLD:
                                new NarratorView(this).showDialog(getRealTime(2000), pokerModel.getCurrentPlayer().getName(), "No voy", null);
                                break;
                            case RAISE:
                                new NarratorView(this).showDialog(getRealTime(2000), pokerModel.getCurrentPlayer().getName(), (isBet?"Subo a ":"Apuesto ") + pokerModel.getCurrentPlayer().getBet(), null);
                                break;
                        }
                        performNext();
                    }
                }
                break;
            case DISCARDING:
                if(roundKindMarker != pokerModel.getRoundKind()){
                    roundKindMarker = pokerModel.getRoundKind();
                    new NarratorView(this).showDialog(getRealTime(2000), "Ronda de descartes.",null,null);
                }
                new NarratorView(this).showDialog(getRealTime(2000), "Turno de " + pokerModel.getCurrentPlayer().getName(), null, null);
                if(pokerModel.getCurrentPlayer() instanceof CPUPlayer){
                    new NarratorView(this).showDialog(getRealTime(2000), pokerModel.getCurrentPlayer().getName(),
                            "Me descarto de " + Integer.toString(((CPUPlayer)pokerModel.getCurrentPlayer()).takeDiscardingDecision()), null);
                    performNext();
                }
                break;
            case RETRIEVING:
                if(roundKindMarker != pokerModel.getRoundKind()){
                    roundKindMarker = pokerModel.getRoundKind();
                }
                pokerModel.fillPlayerCards();
                performNext();
                break;
            case SHOWDOWN:
                if(roundKindMarker != pokerModel.getRoundKind()){
                    roundKindMarker = pokerModel.getRoundKind();
                    new NarratorView(this).showDialog(getRealTime(3000), "FIN DE LA RONDA","Llegó el momento de mostrar las cartas.",null);
                }
                new NarratorView(this).showDialog(getRealTime(2000), "Turno de " + pokerModel.getCurrentPlayer().getName(), null, null);
                discoverCards();
                performNext();
                break;
            case ENDOFGAME:
                new NarratorView(this).showDialog("FIN", pokerModel.getPlayers().get(0).getName() + ", has ganado la partida.", "¡¡ENHORABUENA!!", null);
                disableAfterEndOfGame();
                break;
            case EVERYBODYFOLDED:
                new NarratorView(this).showDialog(5000,"GANADOR DE LA RONDA", 
                        pokerModel.winAfterEverybodyFolding().getName(), null);
                
                noPerformNext();
                break;
        }
        }
        else{
            switch(pokerModel.getRoundKind()){
                case DEALING:
                    performNext();
                    break;
                case BETTING:
                    if(pokerModel.getRemainingPlayersInRound().size() == 1){
                        performNext();
                    }
                    else noPerformNext();
                    break;
                case RETRIEVING:
                    performNext();
                    break;
                case SHOWDOWN:
                    ArrayList<PokerPlayer> winners = pokerModel.getRoundWinners();
                    String winnerNames = "<html>";
                    for(PokerPlayer p : winners){
                        winnerNames += (p.getName() + ((p.isAllInSituation())?" (All-In)":"") + "<br>");
                    }
                    winnerNames += "</html>";
                    
                    new NarratorView(this).showDialog(5000,(winners.size()>1)?"GANADORES DE LA RONDA":"GANADOR DE LA RONDA",
                            winnerNames, null);
                    this.setPoker(pokerModel);
                    noPerformNext();
                    break;
                default:
                    noPerformNext();
                    break;
            }
        }
    }
    
    public void forcePainting(){
        this.paintAll(this.getGraphics());
        //this.rivalScrollPane.paintAll(this.getGraphics());
        //this.myPlayerView.forcePainting();
        //this.paint(this.getGraphics());
    }
    
    private void discoverCards(){
        if(pokerModel.getCurrentPlayer() != pokerModel.getMyPlayer()){
            for(Component c : rivalPanel.getComponents()){
                if(((RivalPlayerView)c).getPlayer() == pokerModel.getCurrentPlayer()){
                    ((RivalPlayerView)c).discoverHand();
                }
            }
        }
        this.paintAll(this.getGraphics());
        ArrayList<Object> playerHand = pokerModel.getCurrentPlayer().getPokerHand();
        switch((PokerHand)playerHand.get(0)){
            case REPOKER:
                new NarratorView(this).showDialog(getRealTime(5000), pokerModel.getCurrentPlayer().getName(), 
                        "¡¡¡REPOKER DE " + (String)playerHand.get(1) + "!!!", "/Media/fireworks_icon.png");
                break;
            case ROYAL_FLUSH:
                new NarratorView(this).showDialog(getRealTime(5000), pokerModel.getCurrentPlayer().getName(), 
                        "¡¡¡ESCALERA REAL!!!", "/Media/fireworks_icon.png");
                break;
            case STRAIGHT_FLUSH:
                new NarratorView(this).showDialog(getRealTime(4000), pokerModel.getCurrentPlayer().getName(), 
                        "¡¡ESCALERA DE COLOR!!", "/Media/fireworks_icon.png");
                break;
            case POKER:
                new NarratorView(this).showDialog(getRealTime(4000), pokerModel.getCurrentPlayer().getName(), 
                        "¡¡POKER DE " + (String)playerHand.get(1) + "!!", "/Media/balloons_icon.png");
                break;
            case FULL:
                new NarratorView(this).showDialog(getRealTime(4000), pokerModel.getCurrentPlayer().getName(), 
                        "¡¡FULL DE " + (String)playerHand.get(1) + " Y " + (String)playerHand.get(2) + "!!", "/Media/balloons_icon.png");
                break;
            case FLUSH:
                new NarratorView(this).showDialog(getRealTime(3000), pokerModel.getCurrentPlayer().getName(), 
                        "¡COLOR!", "/Media/few_fireworks.png");
                break;
            case STRAIGHT:
                new NarratorView(this).showDialog(getRealTime(3000), pokerModel.getCurrentPlayer().getName(), 
                        "¡ESCALERA!", "/Media/few_fireworks.png");
                break;
            case THREESOME:
                new NarratorView(this).showDialog(getRealTime(2500), pokerModel.getCurrentPlayer().getName(), 
                        "¡TRÍO DE " + (String)playerHand.get(1) + "!", "/Media/few_fireworks.png");
                break;
            case TWO_PAIR:
                new NarratorView(this).showDialog(getRealTime(2000), pokerModel.getCurrentPlayer().getName(), 
                        "DOBLE PAREJA DE  " + (String)playerHand.get(1) + " Y " + (String)playerHand.get(2) , null);
                break;
            case PAIR:
                new NarratorView(this).showDialog(getRealTime(2000), pokerModel.getCurrentPlayer().getName(), 
                    "PAREJA DE " + (String)playerHand.get(1), null);
                break;
            case HIGHCARD:
                new NarratorView(this).showDialog(getRealTime(2000), pokerModel.getCurrentPlayer().getName(), 
                        "CARTA ALTA: " + (String)playerHand.get(1), null);
                break;
                
        }
    }
    
    private void performNext(){
        pokerModel.next();
        setPoker(pokerModel);
        this.btContinue.setVisible(false);
        forcePainting();
        try {
            sleep(getRealTime(500));
        } catch (InterruptedException ex) {}
        scrollToRival();
        forcePainting();
             
        gameManager();
        
    }
    
    private int getRealTime(int ms){
        return (pokerModel.getMyPlayer().hasFolded() || pokerModel.getMyPlayer().hasLost())?ms/2:ms;
    }
    
    private void noPerformNext(){
        if((pokerModel.getMyPlayer().hasFolded() || pokerModel.getMyPlayer().hasLost()) && 
                !(pokerModel.getGameRound() % 10 == 0 && (pokerModel.getRoundKind() == PokerRoundKind.SHOWDOWN && pokerModel.isEndOfRound() 
                    || pokerModel.getRoundKind() == PokerRoundKind.EVERYBODYFOLDED))){
            performNext();
        }
        else{
            setPoker(pokerModel);
            this.btContinue.setVisible(true);
        }
    }
    
    private void scrollToRival(){
        if(pokerModel.getCurrentPlayer() != pokerModel.getMyPlayer()){
            int myPlayerIndex = pokerModel.getPlayers().indexOf(pokerModel.getMyPlayer());
            rivalScrollPane.getHorizontalScrollBar().setValue(rivalPanel.getComponent(0).getWidth() *
                    (pokerModel.getCurrentPlayerIndex()-((pokerModel.getCurrentPlayerIndex() < myPlayerIndex ||
                            myPlayerIndex == -1)?0:1)));
            rivalScrollPane.repaint();
            rivalScrollPane.revalidate();
            this.repaint();
            this.revalidate();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rivalScrollPane = new javax.swing.JScrollPane();
        rivalPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        allInScrollPane = new javax.swing.JScrollPane();
        allInPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btContinue = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        rivalScrollPane.setViewportView(rivalPanel);

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("BOTE");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        allInScrollPane.setBorder(null);
        allInScrollPane.setViewportView(allInPanel);

        jLabel2.setFont(new java.awt.Font("Calibri", 3, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 255));
        jLabel2.setText("ALL-IN");

        btContinue.setBackground(new java.awt.Color(0, 0, 255));
        btContinue.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btContinue.setForeground(new java.awt.Color(255, 255, 255));
        btContinue.setText("CONTINUAR");
        btContinue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btContinueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btContinue)
                        .addGap(46, 46, 46)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(allInScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(rivalScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(rivalScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(allInScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(293, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btContinue, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(305, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(352, Short.MAX_VALUE))))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
    /*   
        FrenchDeck deck = new FrenchDeck();
        deck.shuffle();
        
        Poker p = new Poker(new String[]{"PEPE","PEPA","PEPO"},300, 1, 5, 10,2);
        p.getPlayers().get(0).setSmallBlind(true);
        p.getPlayers().get(1).setBigBlind(true);
        p.getPlayers().get(2).setDealer(true);
        
        for(int i = 1; i <= 5; i++){
            //p.getPlayers().get(0).receive(deck.nextCard());
            p.getPlayers().get(1).receive(deck.nextCard());
            p.getPlayers().get(2).receive(deck.nextCard());
        }
        p.getPlayers().get(0).receive(new FrenchCard("10", FrenchSuit.SPADES));
        p.getPlayers().get(0).receive(new FrenchCard("J", FrenchSuit.CLUBS));
        p.getPlayers().get(0).receive(new FrenchCard("Q", FrenchSuit.DIAMONDS));
        p.getPlayers().get(0).receive(new FrenchCard("K", FrenchSuit.HEARTS));
        p.getPlayers().get(0).receive(new FrenchCard("9", FrenchSuit.SPADES));
        //p.getPlayers().get(0).receive(new FrenchCard("BLACK", FrenchSuit.JOKER));
        //p.getPlayers().get(0).receive(new FrenchCard("RED", FrenchSuit.JOKER));
        //p.getPlayers().get(0).receive(new FrenchCard("BLACK", FrenchSuit.JOKER));
        //p.getPlayers().get(0).receive(new FrenchCard("RED", FrenchSuit.JOKER));
        //p.getPlayers().get(0).receive(new FrenchCard("BLACK", FrenchSuit.JOKER));
        
        this.setPoker(p);
        
        try {
            sleep(500);
        } catch (InterruptedException ex) {}
        
        p.getPlayers().get(0).bet(28);
        p.getPlayers().get(1).bet(29);
        p.getPlayers().get(2).bet(30);
        p.takeBets();
        p.getPlayers().get(0).bet(28);
        p.getPlayers().get(1).bet(29);
        p.getPlayers().get(2).bet(30);
        
        p.getPlayers().get(1).fold();
        this.setPoker(p);
        JOptionPane.showMessageDialog(this, p.getMyPlayer().getPokerHand().toString() + "\n" + p.getMyPlayer().computeHandValue());
        this.setPoker(p);
        */
    }//GEN-LAST:event_jLabel1MouseClicked

    private void myPlayerViewMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_myPlayerViewMouseReleased
        Component c = evt.getComponent();
        if(c instanceof javax.swing.JButton && c.isEnabled()){
            performNext();
        }
    }//GEN-LAST:event_myPlayerViewMouseReleased

    private void btContinueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btContinueActionPerformed
        if(pokerModel.getRoundKind() == PokerRoundKind.ENDOFGAME){
            System.exit(0);
        }
        performNext();
    }//GEN-LAST:event_btContinueActionPerformed

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
            java.util.logging.Logger.getLogger(PokerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PokerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PokerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PokerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PokerView().setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel allInPanel;
    private javax.swing.JScrollPane allInScrollPane;
    private javax.swing.JButton btContinue;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel rivalPanel;
    private javax.swing.JScrollPane rivalScrollPane;
    // End of variables declaration//GEN-END:variables


}
