/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.SotaCabrona.GUI;


import GUI.FrenchCardBack;
import GUI.NarratorView;
import Games.SotaCabrona.Model.SotaCabrona;
import Model.Card;
import Model.FrenchCard;
import Model.FrenchSuit;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.scene.input.KeyCode;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 *
 * @author Juan Luis
 */
public class SotaCabronaView extends javax.swing.JFrame {

    private SotaCabrona sotaCabronaModel;
    
    private Timer timerManager;
    
    private ActionListener taskManager;
    
    private KeyAdapter keyEvent; 
    
    private int heapWinCount;
    private static final int heapWinWait = 2000;
    
    private static final int timerTick = 10;
    
    private SotaCabronaView thisView;
    
    private boolean lightListenerAdded;
    
    private void fillRivalPanel(){
        rivalPanel.removeAll();
        int startIndex = sotaCabronaModel.getPlayers().indexOf(sotaCabronaModel.getMyPlayer());

        if(startIndex == -1){
            for(int i = 0; i < sotaCabronaModel.getPlayers().size(); i++){
                PlayerView rv = new PlayerView();
                rivalPanel.add(rv);
                rv.setPlayer(sotaCabronaModel.getPlayers().get(i));
            }  
        }
        else{
            for(int i = startIndex + 1; i != startIndex; i = (i+1)%sotaCabronaModel.getPlayers().size()){
                PlayerView rv = new PlayerView();
                rivalPanel.add(rv);
                rv.setPlayer(sotaCabronaModel.getPlayers().get(i));
            }
        }
        addLightListeners();
    }
    
    private void updateRivalPanel(){
        int startIndex = sotaCabronaModel.getPlayers().indexOf(sotaCabronaModel.getMyPlayer());
        
        if(startIndex == -1){
            for(int i = 0; i < sotaCabronaModel.getPlayers().size(); i++){
                ((PlayerView)rivalPanel.getComponent(i)).setPlayer(sotaCabronaModel.getPlayers().get(i));                             
            }
        }
        else{
            int panelIndex = 0;
            for(int i = startIndex + 1; i != startIndex; i = (i+1)%sotaCabronaModel.getPlayers().size()){
                ((PlayerView)rivalPanel.getComponent(panelIndex)).setPlayer(sotaCabronaModel.getPlayers().get(i));              
                panelIndex++;
            }
        }
    }
    /**
     * Creates new form SotaCabronaView
     */
    public SotaCabronaView() {
        try {
             UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex){}
        initComponents();
        
        thisView = this;
        timerManager = new Timer(timerTick, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                setSotaCabrona(sotaCabronaModel);
                if(sotaCabronaModel.isWinHeapState()){
                    heapWinCount+= timerTick;
                    if(heapWinCount >= heapWinWait){
                        //narratorTxt.setText(narratorTxt.getText() + "\n" + sotaCabronaModel.getHeapPlayer().getName() + " se lleva el montón.");
                        addToNarration(sotaCabronaModel.getHeapPlayer().getName() + " se lleva el montón.");
                        heapWinCount = 0;
                        sotaCabronaModel.winHeap();
                    }
                }
                if(sotaCabronaModel.getWinner() != null){
                    new NarratorView(thisView).showDialog("FIN", "FIN DE LA PARIDA", 
                        sotaCabronaModel.getWinner().getName() + ", has ganado la partida. ¡ENHORABUENA!", "/Media/fireworks_icon.png");
                    addToNarration(sotaCabronaModel.getWinner().getName() + " ha ganado la partida.");
                    timerManager.stop();
                }
                
            }
        });
        
        timerManager.start();
        
        keyEvent = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                gameKeyEvent(ke);
            } 
        };
        heapWinCount = 0;
        lightListenerAdded = false;
        narratorScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    
    public void setSotaCabrona(SotaCabrona s){
        if(s != null){
            this.sotaCabronaModel = s;
            
            if(rivalPanel.getComponentCount() == 0)
                fillRivalPanel();
            else
                updateRivalPanel();

            if(!s.getHeap().isEmpty())
                lastHeapView.setCard(s.getHeap().get(s.getHeap().size()-1), FrenchCardBack.BLUE);
            else
                lastHeapView.setCard(new FrenchCard("A", FrenchSuit.SPADES), FrenchCardBack.RED, true);

            
            this.myPlayerView.setPlayer(s.getMyPlayer());
            this.repaint();
            this.revalidate();

            this.removeKeyListener(keyEvent);
            this.addKeyListener(keyEvent);
            this.setFocusable(true);
            narratorTxt.setFocusable(false);
        //for(Component c : this.getComponents()){
        //    c.setFocusable(true);
        //    c.addKeyListener(keyEvent);
        //}
            
                
            
        }
    }
    
    private void addLightListeners(){
        if(!lightListenerAdded){
            myPlayerView.addLightChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent pce) {
                    if(myPlayerView.getDropLightColor() == Color.YELLOW)
                        addToNarration("Turno de " + sotaCabronaModel.getCurrentPlayer().getName());
                    if(myPlayerView.getDropLightColor() == Color.RED)
                        addToNarration(myPlayerView.getPlayer().getName() + ", has soltado la carta fuera de tu turno. Pierdes 5 cartas.");
                    if(myPlayerView.getDropLightColor() == Color.PINK)
                        addToNarration(myPlayerView.getPlayer().getName() + ", has excedido el tiempo de soltar la carta. Pierdes 5 cartas.");
                    if(myPlayerView.getTouchLightColor() == Color.BLUE)
                        addToNarration(myPlayerView.getPlayer().getName() + ", ¡has tocado el montón con dos cartas del mismo valor! Te llevas el montón.");
                    if(myPlayerView.getTouchLightColor() == Color.GREEN)
                        addToNarration(myPlayerView.getPlayer().getName() + ", ¡has tocado el montón cuando hay un sandwich! Te llevas el montón.");
                    if(myPlayerView.getTouchLightColor() == Color.RED)
                        addToNarration(myPlayerView.getPlayer().getName() + ", has tocado el montón cuando y no hay nada. Pierdes 5 cartas.");
                }
            });
            lightListenerAdded = true;
        }
        
        for(Component c : rivalPanel.getComponents()){
            PlayerView pv = (PlayerView)c;
            pv.addLightChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent pce) {
                    if(pv.getDropLightColor() == Color.YELLOW)
                        addToNarration("Turno de " + sotaCabronaModel.getCurrentPlayer().getName());
                    if(pv.getDropLightColor() == Color.RED)
                        addToNarration(pv.getPlayer().getName() + ", has soltado la carta fuera de tu turno. Pierdes 5 cartas.");
                    if(pv.getDropLightColor() == Color.PINK)
                        addToNarration(pv.getPlayer().getName() + ", has excedido el tiempo de soltar la carta. Pierdes 5 cartas.");
                    if(pv.getTouchLightColor() == Color.BLUE)
                        addToNarration(pv.getPlayer().getName() + ", ¡has tocado el montón con dos cartas del mismo valor! Te llevas el montón.");
                    if(pv.getTouchLightColor() == Color.GREEN)
                        addToNarration(pv.getPlayer().getName() + ", ¡has tocado el montón cuando hay un sandwich! Te llevas el montón.");
                    if(pv.getTouchLightColor() == Color.RED)
                        addToNarration(pv.getPlayer().getName() + ", has tocado el montón cuando y no hay nada. Pierdes 5 cartas.");
                }
            });
        }
    }
    
    private void addToNarration(String text){
        this.narratorTxt.setText(narratorTxt.getText() + text + "\n\n");
    }
            
    //static int i = 0;
    private void gameKeyEvent(KeyEvent ke){
        if(ke.getKeyCode() == KeyEvent.VK_ENTER){
            sotaCabronaModel.getMyPlayer().touchHeap();
        }
        else if(ke.getKeyCode() == KeyEvent.VK_SPACE){
            sotaCabronaModel.getMyPlayer().dropNextCard();
        }
        
        //i++;
        //System.out.println(i);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lastHeapView = new GUI.CardView();
        myPlayerView = new Games.SotaCabrona.GUI.PlayerView();
        jScrollPane1 = new javax.swing.JScrollPane();
        rivalPanel = new javax.swing.JPanel();
        narratorScroll = new javax.swing.JScrollPane();
        narratorTxt = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jScrollPane1.setViewportView(rivalPanel);

        narratorTxt.setEditable(false);
        narratorTxt.setColumns(20);
        narratorTxt.setFont(new java.awt.Font("Calibri", 0, 13)); // NOI18N
        narratorTxt.setLineWrap(true);
        narratorTxt.setRows(5);
        narratorScroll.setViewportView(narratorTxt);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(narratorScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addComponent(myPlayerView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(242, 242, 242))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addComponent(lastHeapView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lastHeapView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(myPlayerView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(narratorScroll))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
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
            java.util.logging.Logger.getLogger(SotaCabronaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SotaCabronaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SotaCabronaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SotaCabronaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SotaCabronaView sv = new SotaCabronaView();
                sv.setVisible(true);
                SotaCabrona s = new SotaCabrona(new String[]{"PEPE","PEPA","PEPO"});
                s.setUsualCPUPlayers(2000, 2000, 0);
                sv.setSotaCabrona(s);
                //s.getPlayers().get(0).takeHeap(s.getPlayers().get(1).getMyCards());
                //sv.setSotaCabrona(s);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private GUI.CardView lastHeapView;
    private Games.SotaCabrona.GUI.PlayerView myPlayerView;
    private javax.swing.JScrollPane narratorScroll;
    private javax.swing.JTextArea narratorTxt;
    private javax.swing.JPanel rivalPanel;
    // End of variables declaration//GEN-END:variables
}
