/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Games.SotaCabrona.GUI;


import GUI.FrenchCardBack;
import Games.SotaCabrona.Model.SotaCabrona;
import Model.Card;
import Model.FrenchCard;
import Model.FrenchSuit;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javafx.scene.input.KeyCode;
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
    }
    /**
     * Creates new form SotaCabronaView
     */
    public SotaCabronaView() {
        try {
             UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex){ System.out.println(ex.getMessage());}
        initComponents();
        
        timerManager = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                setSotaCabrona(sotaCabronaModel);
            }
        });
        
        timerManager.start();
        
        keyEvent = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                gameKeyEvent(ke);
            } 
        };
    }
    
    public void setSotaCabrona(SotaCabrona s){
        
        
        this.sotaCabronaModel = s;
        fillRivalPanel();
        
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
        //for(Component c : this.getComponents()){
        //    c.setFocusable(true);
        //    c.addKeyListener(keyEvent);
        //}
    }
    static int i = 0;
    private void gameKeyEvent(KeyEvent ke){
        if(ke.getKeyCode() == KeyEvent.VK_ENTER){
            sotaCabronaModel.getMyPlayer().touchHeap();
        }
        else if(ke.getKeyCode() == KeyEvent.VK_SPACE){
            sotaCabronaModel.getMyPlayer().dropNextCard();
        }
        
        i++;
        System.out.println(i);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(rivalPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(309, 309, 309)
                .addComponent(lastHeapView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(256, Short.MAX_VALUE)
                .addComponent(myPlayerView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(252, 252, 252))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lastHeapView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(myPlayerView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                sv.setSotaCabrona(s);
                s.getPlayers().get(0).takeHeap(s.getPlayers().get(1).getMyCards());
                sv.setSotaCabrona(s);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private GUI.CardView lastHeapView;
    private Games.SotaCabrona.GUI.PlayerView myPlayerView;
    private javax.swing.JPanel rivalPanel;
    // End of variables declaration//GEN-END:variables
}
