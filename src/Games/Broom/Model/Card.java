/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Games.Broom.Model;

/*
 *
 * @author Javier
 */

public class Card {
    private CardType type;
    private String name;
    private int value;
    
    public Card(CardType type, String name){
        this.type = type;
        this.name = name;
        setValueOf(name);
    }
    
    private void setValueOf(String name){
        switch(name){
            case "AS":
                this.value = 1;
                break;
            case "SOTA":
                this.value = 8;
                break;
            case "CABALLO":
                this.value = 9;
                break;
            case "REY":
                this.value = 10;
                break;
            default:
                this.value = Integer.valueOf(name);
                break;
        }
    }

    public CardType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
    
    @Override
    public String toString(){
        String cadena = " (" + name  + "," + type.toString() + "," + Integer.toString(value) +  ")";
        return cadena;
    }
}
