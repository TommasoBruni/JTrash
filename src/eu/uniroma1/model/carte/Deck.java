package eu.uniroma1.model.carte;

import java.util.Iterator;

import eu.uniroma1.model.exceptions.DeckFinishedException;

import java.util.*;

public class Deck implements Iterable<Card>
{
	private Card[] mazzo;
	private int indiceCorrente;
	
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
	
	public Card prossimaCarta() throws DeckFinishedException
	{
		if (indiceCorrente + 1 >= mazzo.length)
			throw new DeckFinishedException();
		return mazzo[indiceCorrente++];
	}
	
	@Override
	public Iterator<Card> iterator() 
	{
		return new Iterator<Card>() 
		{
			private int indice;
			
			@Override
			public Card next()
			{
				return mazzo[indice++];
			}
			
			@Override
			public boolean hasNext() 
			{
				return indice < mazzo.length;
			}
		};
	}
	
	private Deck(Card[] mazzo)
	{
		this.mazzo = mazzo;
	}
	
	public static class MazzoDiCarteBuilder
	{
		private Card[] mazzo;
		
		public Deck build()
		{
			return new Deck(mazzo);
		}
		
		public MazzoDiCarteBuilder mischia()
		{
			Random random=new Random();
			int j;
			Card tmp;
			
			for (int i = 0; i < mazzo.length; i++)
			{
				j = random.nextInt(mazzo.length);
				tmp = mazzo[i];
				mazzo[i] = mazzo[j];
				mazzo[j] = tmp;
			}
			return this;
		} 
		
		public MazzoDiCarteBuilder combina(Deck newMazzoDiCarte)
		{
		    Card[] result = Arrays.copyOf(mazzo, mazzo.length + newMazzoDiCarte.mazzo.length);
		    
		    System.arraycopy(newMazzoDiCarte.mazzo, 0, result, mazzo.length, newMazzoDiCarte.mazzo.length);
		    mazzo = result;
		    return this;
		}
		
		public MazzoDiCarteBuilder()
		{
			mazzo = Card.values();
		}
	}
}
