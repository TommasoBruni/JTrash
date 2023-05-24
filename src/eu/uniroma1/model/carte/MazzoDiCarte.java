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
	
	public void shuffle()
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
