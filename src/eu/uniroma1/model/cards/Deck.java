package eu.uniroma1.model.cards;

import java.util.Iterator;

import eu.uniroma1.model.exceptions.DeckFinishedException;

import java.util.*;

/**
 * Deck class 
 */
public class Deck implements Iterable<Card>
{
	private Card[] cards;
	private int currentIndex;
	
	@Override
	public String toString() 
	{
		StringBuffer strB = new StringBuffer();
		
		strB.append("[");
		for (Card c : this)
			strB.append(c + ", \n");
		strB.replace(strB.length() - 2, strB.length() - 2, "");
		strB.append("]");
		return strB.toString();
	}
	
	/**
	 * Provide next card if available
	 * @throws DeckFinishedException if there is no more cards
	 * @return next card 
	 */
	public Card nextCard() throws DeckFinishedException
	{
		if (currentIndex + 1 >= cards.length)
			throw new DeckFinishedException();
		return cards[currentIndex++];
	}
	
	/**
	 * Specify to the deck to restore the old one card
	 */
	public void backupCard()
	{
		if (currentIndex > 0)
			currentIndex -= 1;
	}
	
	@Override
	public Iterator<Card> iterator() 
	{
		return new Iterator<Card>() 
		{
			private int index;
			
			@Override
			public Card next()
			{
				return cards[index++];
			}
			
			@Override
			public boolean hasNext() 
			{
				return index < cards.length;
			}
		};
	}
	
	private Deck(Card[] cards)
	{
		this.cards = cards;
	}
	
	/**
	 * Deck class builder 
	 */
	public static class DeckBuilder
	{
		private Card[] deck;
		
		/**
		 * Build the deck
		 * @return new instance of deck 
		 */
		public Deck build()
		{
			return new Deck(deck);
		}
		
		/**
		 * Shuffle the current deck
		 * @return a new shuffled deck builder 
		 */
		public DeckBuilder shuffle()
		{
			Random random = new Random();
			int j;
			Card tmp;
			
			for (int i = 0; i < deck.length; i++)
			{
				j = random.nextInt(deck.length);
				tmp = deck[i];
				deck[i] = deck[j];
				deck[j] = tmp;
			}
			return this;
		} 
		
		/**
		 * Aggregate a new deck to the current one
		 * @param newDeck new deck to aggregate with the current one
		 * @return new deck builder joined with another deck
		 */
		public DeckBuilder join(Deck newDeck)
		{
		    Card[] result = Arrays.copyOf(deck, deck.length + newDeck.cards.length);
		    
		    System.arraycopy(newDeck.cards, 0, result, deck.length, newDeck.cards.length);
		    deck = result;
		    return this;
		}
		
		/**
		 * Deck builder constructor 
		 */
		public DeckBuilder()
		{
			deck = Card.values();
		}
	}
}
