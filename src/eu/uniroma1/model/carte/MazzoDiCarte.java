package eu.uniroma1.model.carte;

import java.util.Iterator;
import java.util.*;

public class MazzoDiCarte implements Iterable<Carte>
{
	private Carte[] mazzo;
	
	@Override
	public String toString() 
	{
		StringBuffer strB = new StringBuffer();
		
		strB.append("[");
		for (Carte c : this)
			strB.append(c + ", \n");
		strB.replace(strB.length() - 2, strB.length() - 2, "");
		strB.append("]");
		return strB.toString();
	}
	
	@Override
	public Iterator<Carte> iterator() 
	{
		return new Iterator<Carte>() 
		{
			private int indice;
			
			@Override
			public Carte next()
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
	
	private MazzoDiCarte(Carte[] mazzo)
	{
		this.mazzo = mazzo;
	}
	
	public static class MazzoDiCarteBuilder
	{
		private Carte[] mazzo;
		
		public MazzoDiCarte build()
		{
			return new MazzoDiCarte(mazzo);
		}
		
		public MazzoDiCarteBuilder mischia()
		{
			Random random=new Random();
			int j;
			Carte tmp;
			
			for (int i = 0; i < mazzo.length; i++)
			{
				j = random.nextInt(mazzo.length);
				tmp = mazzo[i];
				mazzo[i] = mazzo[j];
				mazzo[j] = tmp;
			}
			return this;
		} 
		
		public MazzoDiCarteBuilder combina(MazzoDiCarte newMazzoDiCarte)
		{
		    Carte[] result = Arrays.copyOf(mazzo, mazzo.length + newMazzoDiCarte.mazzo.length);
		    
		    System.arraycopy(newMazzoDiCarte.mazzo, 0, result, mazzo.length, newMazzoDiCarte.mazzo.length);
		    mazzo = result;
		    return this;
		}
		
		public MazzoDiCarteBuilder()
		{
			mazzo = Carte.values();
		}
	}
}
