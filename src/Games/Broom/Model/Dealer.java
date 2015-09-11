/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Games.Broom.Model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
/**
 *
 * @author Javier
 */
public class Dealer {
    private static Dealer instance = new Dealer();
    private ArrayList<Card> deck;
    
    private Dealer(){
        deck = new ArrayList<>();
    }
    
    public static Dealer getInstance(){
        return instance;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
    
    
    private void initCardDeck(){
        //Ases
        
        deck.add(new Card(CardType.BASTOS,"AS"));
        deck.add(new Card(CardType.OROS,"AS"));
        deck.add(new Card(CardType.ESPADAS,"AS"));
        deck.add(new Card(CardType.COPAS,"AS"));
    
        //Doses
        
        deck.add(new Card(CardType.BASTOS,"2"));
        deck.add(new Card(CardType.OROS,"2"));
        deck.add(new Card(CardType.ESPADAS,"2"));
        deck.add(new Card(CardType.COPAS,"2"));
        
        // Tres
        
        deck.add(new Card(CardType.BASTOS,"3"));
        deck.add(new Card(CardType.OROS,"3"));
        deck.add(new Card(CardType.ESPADAS,"3"));
        deck.add(new Card(CardType.COPAS,"3"));
        
        // Cuatro
        
        deck.add(new Card(CardType.BASTOS,"4"));
        deck.add(new Card(CardType.OROS,"4"));
        deck.add(new Card(CardType.ESPADAS,"4"));
        deck.add(new Card(CardType.COPAS,"4"));
        
        //Cincos
        
        deck.add(new Card(CardType.BASTOS,"5"));
        deck.add(new Card(CardType.OROS,"5"));
        deck.add(new Card(CardType.ESPADAS,"5"));
        deck.add(new Card(CardType.COPAS,"5"));
        
        // Seis
        
        deck.add(new Card(CardType.BASTOS,"6"));
        deck.add(new Card(CardType.OROS,"6"));
        deck.add(new Card(CardType.ESPADAS,"6"));
        deck.add(new Card(CardType.COPAS,"6"));
        
        // Sietes
        
        deck.add(new Card(CardType.BASTOS,"7"));
        deck.add(new Card(CardType.OROS,"7"));
        deck.add(new Card(CardType.ESPADAS,"7"));
        deck.add(new Card(CardType.COPAS,"7"));
        
        // Sotas
        
        deck.add(new Card(CardType.BASTOS,"SOTA"));
        deck.add(new Card(CardType.OROS,"SOTA"));
        deck.add(new Card(CardType.ESPADAS,"SOTA"));
        deck.add(new Card(CardType.COPAS,"SOTA"));        
        
        //Caballos
        
        deck.add(new Card(CardType.BASTOS,"CABALLO"));
        deck.add(new Card(CardType.OROS,"CABALLO"));
        deck.add(new Card(CardType.ESPADAS,"CABALLO"));
        deck.add(new Card(CardType.COPAS,"CABALLO"));
        
        // Reyes
        
        deck.add(new Card(CardType.BASTOS,"REY"));
        deck.add(new Card(CardType.OROS,"REY"));
        deck.add(new Card(CardType.ESPADAS,"REY"));
        deck.add(new Card(CardType.COPAS,"REY"));
    }
    
    
    private void shuffleDeck(){
        Collections.shuffle(deck);
    }
    
    
    public Card nextCard(){
        Card c; 
               
        if (deck.isEmpty())
            return null;
        
        c = deck.get(0);
        deck.remove(c);
               
        return c;
    }
    
    public void initCards(){
        initCardDeck();
        shuffleDeck();
    }
}
