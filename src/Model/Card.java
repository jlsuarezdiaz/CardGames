/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Model;

/**
 *
 * @author Juan Luis
 */
public abstract class Card {
    /**
     * Sets the card data.
     * @param val Card value.
     * @param suit  Card suit.
     */
    protected abstract void set(String val, Suitable suit);
    
    /**
     * Checks whether the card is the given by the function parameters.
     * @param val Value to check.
     * @param suit Suitable to check.
     * @return True if and only if the card has the given values.
     */
    public abstract boolean isCard(String val, Suitable suit);
    
    /**
     * Checks the card suit.
     * @param suit Suitable to check.
     * @return True if and only if the card has the given suit.
     */
    public abstract boolean isSuit(Suitable suit);
    
    /**
     * Checks the card value.
     * @param val Value to check.
     * @return True if and only if the card has the given value.
     */
    public abstract boolean isValue(String val);
    
    /**
     * Gets the card value.
     * @return card value. 
     */
    public abstract String getValue();
    
    /**
     * Gets the card suit.
     * @return card suit.
     */
    public abstract Suitable getSuit();
}
