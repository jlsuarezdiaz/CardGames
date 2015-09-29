/*
 * Author: Juan Luis Su�rez D�az
 * September, 2015
 * Card Games
 */
package Model;

/**
 *
 * @author Javier
 */
public class UnoCard extends Card{
    
    private String value;
    
    private UnoSuit suit;
    
    @Override
    protected void set(String val, Suitable suit) {
        this.value = val;
        this.suit = (UnoSuit)suit;
    }

    public UnoCard(String val, UnoSuit suit){
        set(val,suit);
    }
    @Override
    public boolean isCard(String val, Suitable suit) {
        return isSuit(suit) && isValue(val);
    }

    @Override
    public boolean isSuit(Suitable suit) {
        return this.suit.equals(suit);
    }

    @Override
    public boolean isValue(String val) {
        return this.value.equals(val);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Suitable getSuit() {
        return suit;
    }
    
}
