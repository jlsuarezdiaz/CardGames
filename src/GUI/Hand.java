/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package GUI;

import Model.Card;
import com.sun.corba.se.spi.oa.OADefault;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;

/**
 *
 * @author Juan Luis
 */
public class Hand extends javax.swing.JPanel {

    private Orientation orientation;
    
    /**
     * Action to perform when clicked a card.
     */
    //private MouseListener clickAction;
    boolean selectionAtMouseListening;
    boolean coveringAtMouseListening;
    
    
    private void set(Orientation orientation){
        setOrientation(orientation);
        selectionAtMouseListening = false;
        coveringAtMouseListening = false;
    }
    /**
     * Creates new form Hand
     */
    public Hand() {
        initComponents();
        set(Orientation.HORIZONTAL);
    }

    /**
     * Creates new form Hand
     */
    public Hand(Orientation orientation) {
        initComponents();
        set(orientation);
    }
    
    public void setOrientation(Orientation orientation){
        this.orientation = orientation;
        switch(orientation){
            case HORIZONTAL:
                this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS ));
                break;
            case VERTICAL:
                this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
                break;
        }
    }
    
    public Orientation getOrientation(){
        return orientation;
    }
    
    public void add(Card c, CardBack color_deck){
        add(c,color_deck,false);
    }
    
    public void add(CardView cv){
        if(selectionAtMouseListening) cv.addSelectionAtMouseListening();
        if(coveringAtMouseListening) cv.addCoveringAtMouseListening();
        super.add(cv);
        this.repaint();
        this.revalidate();
    }
    
    public void add(Card c, CardBack color_deck, boolean covered){
        CardView cv = new CardView();
        cv.setCard(c, color_deck, covered);
        add(cv);
    }
    
    public void add(ArrayList<Card> cards, CardBack color_deck, boolean covered){
        for(Card c : cards){
            add(c, color_deck, covered);
        }
    }
    
    public void add(ArrayList<Card> cards, CardBack color_deck){
        add(cards, color_deck, false);
    }
    
    public ArrayList<Card> getCards(){
        ArrayList<Card> cards = new ArrayList();
        for(CardView c : this.getCardViews()){
           cards.add(c.getCard());
        }
        return cards;
    }
    
    public ArrayList<CardView> getCardViews(){
        ArrayList<CardView> views = new ArrayList();
        for(Component c : this.getComponents()){
            views.add((CardView)c);
        }
        return views;
    }
    
    public ArrayList<Card> getSelectedCards(){
        ArrayList<Card> cards = new ArrayList();
        for(CardView cv : getCardViews()){
            if(cv.isSelected()){
                cards.add(cv.getCard());
            }
        }
        return cards;
    }
    
    public Card getSelectedCard(){
        ArrayList<Card> selected = getSelectedCards();
        return (selected.isEmpty())?null:selected.get(0);
    }
    
    public ArrayList<CardView> getSelectedCardViews(){
        ArrayList<CardView> views = new ArrayList();
        for(CardView cv : getCardViews()){
            if(cv.isSelected()){
                views.add(cv);
            }
        }
        return views;        
    }
    
    public CardView getSelectedCardView(){
        ArrayList<CardView> selected = getSelectedCardViews();
        return (selected.isEmpty())?null:selected.get(0);
    }
    
    public ArrayList<Card> getCoveredCards(){
        ArrayList<Card> cards = new ArrayList();
        for(CardView cv : getCardViews()){
            if(cv.isCovered()){
                cards.add(cv.getCard());
            }
        }
        return cards;
    }
    
    public ArrayList<CardView> getCoveredCardViews(){
        ArrayList<CardView> views = new ArrayList();
        for(CardView cv : getCardViews()){
            if(cv.isCovered()){
                views.add(cv);
            }
        }
        return views;          
    }
    
    public ArrayList<Card> getUncoveredCards(){
        ArrayList<Card> cards = new ArrayList();
        for(CardView cv : getCardViews()){
            if(!cv.isCovered()){
                cards.add(cv.getCard());
            }
        }
        return cards;
    }
    
    public ArrayList<CardView> getUncoveredCardViews(){
        ArrayList<CardView> views = new ArrayList();
        for(CardView cv : getCardViews()){
            if(!cv.isCovered()){
                views.add(cv);
            }
        }
        return views;  
    }
    
    public void remove(Card c){
        this.remove(getCards().indexOf(c));
    }
    
    public void remove(ArrayList<Card> cards){
        for(Card c : cards){
            remove(c);
        }
    }
    
    public void remove(CardView c){
        super.remove(c);
    }
    
    public void removeViews(ArrayList<CardView> views){
        for(CardView cv : views){
            this.remove(cv);
        }
    }
    
/*    @Override
    public void addMouseListener(MouseListener ml){
        this.clickAction = ml;
        for(CardView cv : this.getCardViews()){
            cv.addMouseListener(this.clickAction);
        }
    }*/
    
    public void addSelectionAtMouseListening(){
        selectionAtMouseListening = true;
        for(CardView cv : this.getCardViews()){
            cv.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent me) {
                    cv.addSelectionAtMouseListening();
                }
            }); 
        }
    }
    
    public void addCoveringAtMouseListening(){
        coveringAtMouseListening = true;
        for(CardView cv : this.getCardViews()){
            cv.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent me) {
                    cv.addCoveringAtMouseListening();
                }
            }); 
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

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
