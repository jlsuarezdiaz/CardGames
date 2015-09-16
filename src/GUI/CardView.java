/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package GUI;

import Model.Card;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Juan Luis
 */
public class CardView extends javax.swing.JPanel {
    
    private Card cardModel;

    private String card_address;
    
    private CardBack color_deck;
    
    private boolean selected;
    
    private boolean covered;
    
    private static final int selectionBorderSize = 4;
    
    private void setIcon(Icon icon){
        cardLabel.setIcon(icon);
        
        this.setSize(icon.getIconWidth(),icon.getIconHeight());
        cardLabel.setSize(icon.getIconWidth(),icon.getIconHeight());
        
        int selectionPlus = (isSelected())?selectionBorderSize*2:0;
        this.setMaximumSize(new Dimension(icon.getIconWidth()+selectionPlus, icon.getIconHeight()+selectionPlus));
        this.setMinimumSize(new Dimension(icon.getIconWidth()+selectionPlus, icon.getIconHeight()+selectionPlus));
        this.setPreferredSize(new Dimension(icon.getIconWidth()+selectionPlus, icon.getIconHeight()+selectionPlus));
    }
    
    /**
     * Creates new form CardView
     */
    public CardView() {
        initComponents();
    }
    
    public void setCard(Card c, CardBack color_deck, boolean covered){
        this.cardModel = c;
        
        card_address = "/Media/" + c.getClass().getSimpleName()+ "_" + c.getValue() + "_" + c.getSuit().toString().toUpperCase()+".png";
        //System.out.println(card_address);
        this.color_deck = color_deck;
                
        cover(covered);
        select(false);
        
        repaint();
        revalidate();
    }
    
    public void setCard(Card c, CardBack color_deck){
        setCard(c,color_deck,false);
    }
    
    public void select(boolean selected){
        this.selected = selected;
        if(selected){
            //this.setBorder(new BevelBorder(BevelBorder.RAISED));
            this.setBorder(new LineBorder(new Color(0xFACC2E),selectionBorderSize));
            //this.setMaximumSize(new Dimension(cardLabel.getIcon().getIconWidth()+2*selectionBorderSize,cardLabel.getIcon().getIconHeight()+2*selectionBorderSize));
            //this.setMinimumSize(new Dimension(cardLabel.getIcon().getIconWidth()+2*selectionBorderSize,cardLabel.getIcon().getIconHeight()+2*selectionBorderSize));
            //this.setPreferredSize(new Dimension(cardLabel.getIcon().getIconWidth()+2*selectionBorderSize,cardLabel.getIcon().getIconHeight()+2*selectionBorderSize));
        }
        else{
            //this.setBorder(new LineBorder(Color.BLACK));
            this.setBorder(null);
            //this.setMaximumSize(new Dimension(cardLabel.getIcon().getIconWidth(),cardLabel.getIcon().getIconHeight()));
            //this.setMinimumSize(new Dimension(cardLabel.getIcon().getIconWidth(),cardLabel.getIcon().getIconHeight()));
            //this.setPreferredSize(new Dimension(cardLabel.getIcon().getIconWidth(),cardLabel.getIcon().getIconHeight()));
        }
        setIcon(cardLabel.getIcon());
        this.repaint();
        this.revalidate();
    }
    
    public boolean isSelected(){
        return selected;
    }
    
    public void cover(boolean covered){
        this.covered = covered;
        if(!covered){
            setIcon(new javax.swing.ImageIcon(getClass().getResource(card_address)));
        }
        else{
            setIcon(new javax.swing.ImageIcon(getClass().getResource(
                "/Media/" + cardModel.getClass().getSimpleName() + "_" + color_deck.toString()+"_BACK_"+Orientation.VERTICAL.toString()+".png")));
        }
    }
    
    public boolean isCovered(){
        return covered;
    }
    
    public Card getCard(){
        return this.cardModel;
    }
    
    public void addSelectionAtMouseListening(){
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                //super.mouseClicked(me); //To change body of generated methods, choose Tools | Templates.
                select(!isSelected());
            }
        };
        //System.out.println(this.getCard().getValue()+this.getCard().getSuit().toString());
        this.addMouseListener(ma);
        cardLabel.addMouseListener(ma);
    }
    
    public void addCoveringAtMouseListening(){
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                //super.mouseClicked(me); //To change body of generated methods, choose Tools | Templates.
                cover(!isCovered());
            }
        };
        this.addMouseListener(ma);
        cardLabel.addMouseListener(ma);        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardLabel = new javax.swing.JLabel();

        cardLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/FrenchCard_A_SPADES.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cardLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cardLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cardLabel;
    // End of variables declaration//GEN-END:variables
}
