package eu.uniroma1.model.carte;

import java.util.Iterator;

import eu.uniroma1.model.exceptions.DeckFinishedException;

import java.util.*;

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
	
	public Card nextCard() throws DeckFinishedException
	{
		if (currentIndex + 1 >= cards.length)
			throw new DeckFinishedException();
		return cards[currentIndex++];
	}
	
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
	
	public static class DeckBuilder
	{
		private Card[] deck;
		
		public Deck build()
		{
			return new Deck(deck);
		}
		
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
		
		public DeckBuilder join(Deck newDeck)
		{
		    Card[] result = Arrays.copyOf(deck, deck.length + newDeck.cards.length);
		    
		    System.arraycopy(newDeck.cards, 0, result, deck.length, newDeck.cards.length);
		    deck = result;
		    return this;
		}
		
		public DeckBuilder()
		{
			deck = Card.values();
		}
	}
}
