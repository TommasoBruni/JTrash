package eu.uniroma1.model.carte;

public enum Carte
{
	C1(Seme.CUORI, Valori.ASSO),
	Q1(Seme.QUADRI, Valori.ASSO),
	F1(Seme.FIORI, Valori.ASSO),
	P1(Seme.PICCHE, Valori.ASSO),
	
	C2(Seme.CUORI, Valori.DUE),
	Q2(Seme.QUADRI, Valori.DUE),
	F2(Seme.FIORI, Valori.DUE),
	P2(Seme.PICCHE, Valori.DUE),
	
	C3(Seme.CUORI, Valori.TRE),
	Q3(Seme.QUADRI, Valori.TRE),
	F3(Seme.FIORI, Valori.TRE),
	P3(Seme.PICCHE, Valori.TRE),
	
	C4(Seme.CUORI, Valori.QUATTRO),
	Q4(Seme.QUADRI, Valori.QUATTRO),
	F4(Seme.FIORI, Valori.QUATTRO),
	P4(Seme.PICCHE, Valori.QUATTRO),
	
	C5(Seme.CUORI, Valori.CINQUE),
	Q5(Seme.QUADRI, Valori.CINQUE),
	F5(Seme.FIORI, Valori.CINQUE),
	P5(Seme.PICCHE, Valori.CINQUE),
	
	C6(Seme.CUORI, Valori.SEI),
	Q6(Seme.QUADRI, Valori.SEI),
	F6(Seme.FIORI, Valori.SEI),
	P6(Seme.PICCHE, Valori.SEI),
	
	C7(Seme.CUORI, Valori.SETTE),
	Q7(Seme.QUADRI, Valori.SETTE),
	F7(Seme.FIORI, Valori.SETTE),
	P7(Seme.PICCHE, Valori.SETTE),
	
	C8(Seme.CUORI, Valori.OTTO),
	Q8(Seme.QUADRI, Valori.OTTO),
	F8(Seme.FIORI, Valori.OTTO),
	P8(Seme.PICCHE, Valori.OTTO),
	
	C9(Seme.CUORI, Valori.NOVE),
	Q9(Seme.QUADRI, Valori.NOVE),
	F9(Seme.FIORI, Valori.NOVE),
	P9(Seme.PICCHE, Valori.NOVE),
	
	C10(Seme.CUORI, Valori.DIECI),
	Q10(Seme.QUADRI, Valori.DIECI),
	F10(Seme.FIORI, Valori.DIECI),
	P10(Seme.PICCHE, Valori.DIECI),
	
	CJ(Seme.CUORI, Valori.JACK),
	QJ(Seme.QUADRI, Valori.JACK),
	FJ(Seme.FIORI, Valori.JACK),
	PJ(Seme.PICCHE, Valori.JACK),
	
	CQ(Seme.CUORI, Valori.QUEEN),
	QQ(Seme.QUADRI, Valori.QUEEN),
	FQ(Seme.FIORI, Valori.QUEEN),
	PQ(Seme.PICCHE, Valori.QUEEN),
	
	CK(Seme.CUORI, Valori.KING),
	QK(Seme.QUADRI, Valori.KING),
	FK(Seme.FIORI, Valori.KING),
	PK(Seme.PICCHE, Valori.KING);
	
	
	Valori valore;
	Seme seme;
	
	Carte(Seme seme, Valori valore)
	{
		this.seme=seme;
		this.valore=valore;
	}
	
	@Override
	public String toString()
	{
		return seme.toString()+"_"+valore.toString();
	}
}
