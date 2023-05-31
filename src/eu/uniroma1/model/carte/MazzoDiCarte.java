package eu.uniroma1.model.carte;

import java.util.Iterator;
import java.util.*;

public class MazzoDiCarte implements Iterable<Carte>
{
	private Carte[] mazzo;
	
	public MazzoDiCarte()
	{
		mazzo = Carte.values();
	}
	
	public void mischia()
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
	}
	
	public void combina(MazzoDiCarte nuovoMazzo)
	{
	    Carte[] result = Arrays.copyOf(mazzo, mazzo.length + nuovoMazzo.mazzo.length);
	    
	    System.arraycopy(nuovoMazzo.mazzo, 0, result, mazzo.length, nuovoMazzo.mazzo.length);
	    nuovoMazzo.mazzo = result;		
	}
	
	@Override
	public String toString() 
	{
		StringBuffer strB = new StringBuffer();
		
		strB.append("[");
		for (Carte c : this)
			strB.append(c + ", ");
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
}
