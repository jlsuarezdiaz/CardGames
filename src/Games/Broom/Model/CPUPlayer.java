/*
 * Author: Juan Luis Suï¿½rez Dï¿½az
 * September, 2015
 * Card Games
 */
package Games.Broom.Model;

import Model.SpanishCard;
import Model.SpanishSuit;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @authors Javier, Joserichi
 */
public class CPUPlayer extends Player implements Cloneable {
    private class Jugada {
        public SpanishCard hand;
        public ArrayList<SpanishCard> table = new ArrayList<>();
        public boolean escoba = false;

        public Jugada(){}
                
        public Jugada(Jugada otra){
            this.hand = otra.hand;
            this.table = new ArrayList<>(otra.table);
        }
                
                
        public int puntuacion() {
            int sum = 0;
            for (SpanishCard card: table) {
                sum += Integer.valueOf(card.getValue()) >= 10 ? Integer.valueOf(card.getValue())-2 : Integer.valueOf(card.getValue());
            }
            sum += Integer.valueOf(hand.getValue()) >= 10 ? Integer.valueOf(hand.getValue())-2 : Integer.valueOf(hand.getValue());
            return sum;
        }
        
        public boolean hasSevenCoin() {
            if (hand.isCard("7", SpanishSuit.COINS)) 
                return true;
	
            for (SpanishCard c : table) {
                if (c.isCard("7", SpanishSuit.COINS)) 
                    return true;
            }
		
            return false;
        }
		
	public boolean hasSeven() {
            if (hand.isValue("7")) 
                return true;
	
            for (SpanishCard c : table) {
		if (c.isValue("7")) 
                    return true;
            }
		
            return false;
	}
		
	public boolean hasCoins() {
	    if (hand.isSuit(SpanishSuit.COINS)) 
                return true;
		
            for (SpanishCard c : table) {
                if (c.isSuit(SpanishSuit.COINS)) 
                    return true;
            }
		
            return false;
	}
		
	public int getNumCards() {
            return table.size() + 1;
	}
    }
    
    private String name;
 
    public CPUPlayer(String name) {
        super(name);
    }
	
    private ArrayList<Jugada> generaJugadas(ArrayList<SpanishCard> table) {
        ArrayList<Jugada> jugadas = new ArrayList<>();
	
        for (SpanishCard c : cards) {
            Jugada j = new Jugada();
            j.hand = c;
            generaJugadasRecursivo(table, jugadas, j);
	}
	
        return jugadas;
    }
	
    private void generaJugadasRecursivo(ArrayList<SpanishCard> mesa, ArrayList<Jugada> jugadas, Jugada actual) {
	for (SpanishCard c : mesa) {
            ArrayList<SpanishCard> cpmesa = new ArrayList<>(mesa);
            cpmesa.remove(c);
            Jugada cpjug = new Jugada(actual);
	
            cpjug.table.add(c);
            
            if (cpjug.puntuacion() < 15) {
                generaJugadasRecursivo(cpmesa, jugadas, cpjug);
            } 
            else if (cpjug.puntuacion() == 15) {
                if(cpmesa.isEmpty()) {
                    cpjug.escoba = true;
		}
		
                jugadas.add(cpjug);
            }
	}
    }
       
    public void playCPU(ArrayList<SpanishCard> table){
        ArrayList<Jugada> jugadas = generaJugadas(table);
            if (jugadas.isEmpty()) {
                ArrayList<SpanishCard> b, c;
                b = new ArrayList<>(cards);
                c = new ArrayList<>(cards);
                
                for (Iterator<SpanishCard> it = c.iterator(); it.hasNext();) {
                        SpanishCard card = it.next();
                        if (card.isCard("7", SpanishSuit.COINS)) it.remove();
                }
                
                if (!c.isEmpty()) {
                    b = new ArrayList<>(c);
                    
                    for (Iterator<SpanishCard> it = c.iterator(); it.hasNext();) {
                        SpanishCard card = it.next();
                        
                        if (card.isValue("7")) 
                            it.remove();
                    }
                    
                    if (!c.isEmpty()) {
                        b = new ArrayList<>(c);
                        
                        for (Iterator<SpanishCard> it = c.iterator(); it.hasNext();) {
                            SpanishCard card = it.next();
                            
                            if (card.isSuit(SpanishSuit.COINS)) 
                                it.remove();
                        }
                        
                        if (!c.isEmpty()) {
                            b = c;
                        }
                    }
                }
                //Vale "Azar = Primera"?
            cards.remove(b.get(0)); //XXX El setNewCard no la elimina del player (creo)
            setNewCard(b.get(0), table);
        } else {
            Jugada jugada = null;
            int cards = 0, value = 0;
            
            for (Jugada j : jugadas) {
                    if (j.escoba) {
                            jugada = j;
                            break;
                    } else if(j.hasSevenCoin()) {
                            jugada = j;
                            value = 4;
                    } else if(value < 3 && j.hasSeven()) {
                            jugada = j;
                            value = 3;
                    } else if(value < 2 && j.hasCoins()) {
                            jugada = j;
                            value = 2;
                    } else if (value <= 1 && j.getNumCards() > cards) {
                            cards = j.getNumCards();
                            jugada = j;
                            value = 1;
                    }
            }
            

            checkBroom(jugada,table);
            this.cards.remove(jugada.hand);
            this.heap.add(jugada.hand);
            for (SpanishCard c : jugada.table) {
                    table.remove(c);
                    heap.add(c);
            }
        }
    }
    
    private void checkBroom(Jugada jugada, ArrayList<SpanishCard> table){
         if(jugada.table.size() == table.size())
             this.addBrooms();
    }
        
}
