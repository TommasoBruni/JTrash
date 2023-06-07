package eu.uniroma1.model.carte;

public enum Carte
{
	C1(Seme.CUORI, Valori.ASSO, Colore.ROSSO),
	Q1(Seme.QUADRI, Valori.ASSO, Colore.ROSSO),
	F1(Seme.FIORI, Valori.ASSO, Colore.NERO),
	P1(Seme.PICCHE, Valori.ASSO, Colore.NERO),
	
	C2(Seme.CUORI, Valori.DUE, Colore.ROSSO),
	Q2(Seme.QUADRI, Valori.DUE, Colore.ROSSO),
	F2(Seme.FIORI, Valori.DUE, Colore.NERO),
	P2(Seme.PICCHE, Valori.DUE, Colore.NERO),
	
	C3(Seme.CUORI, Valori.TRE, Colore.ROSSO),
	Q3(Seme.QUADRI, Valori.TRE, Colore.ROSSO),
	F3(Seme.FIORI, Valori.TRE, Colore.NERO),
	P3(Seme.PICCHE, Valori.TRE, Colore.NERO),
	
	C4(Seme.CUORI, Valori.QUATTRO, Colore.ROSSO),
	Q4(Seme.QUADRI, Valori.QUATTRO, Colore.ROSSO),
	F4(Seme.FIORI, Valori.QUATTRO, Colore.NERO),
	P4(Seme.PICCHE, Valori.QUATTRO, Colore.NERO),
	
	C5(Seme.CUORI, Valori.CINQUE, Colore.ROSSO),
	Q5(Seme.QUADRI, Valori.CINQUE, Colore.ROSSO),
	F5(Seme.FIORI, Valori.CINQUE, Colore.NERO),
	P5(Seme.PICCHE, Valori.CINQUE, Colore.NERO),
	
	C6(Seme.CUORI, Valori.SEI, Colore.ROSSO),
	Q6(Seme.QUADRI, Valori.SEI, Colore.ROSSO),
	F6(Seme.FIORI, Valori.SEI, Colore.NERO),
	P6(Seme.PICCHE, Valori.SEI, Colore.NERO),
	
	C7(Seme.CUORI, Valori.SETTE, Colore.ROSSO),
	Q7(Seme.QUADRI, Valori.SETTE, Colore.ROSSO),
	F7(Seme.FIORI, Valori.SETTE, Colore.NERO),
	P7(Seme.PICCHE, Valori.SETTE, Colore.NERO),
	
	C8(Seme.CUORI, Valori.OTTO, Colore.ROSSO),
	Q8(Seme.QUADRI, Valori.OTTO, Colore.ROSSO),
	F8(Seme.FIORI, Valori.OTTO, Colore.NERO),
	P8(Seme.PICCHE, Valori.OTTO, Colore.NERO),
	
	C9(Seme.CUORI, Valori.NOVE, Colore.ROSSO),
	Q9(Seme.QUADRI, Valori.NOVE, Colore.ROSSO),
	F9(Seme.FIORI, Valori.NOVE, Colore.NERO),
	P9(Seme.PICCHE, Valori.NOVE, Colore.NERO),
	
	C10(Seme.CUORI, Valori.DIECI, Colore.ROSSO),
	Q10(Seme.QUADRI, Valori.DIECI, Colore.ROSSO),
	F10(Seme.FIORI, Valori.DIECI, Colore.NERO),
	P10(Seme.PICCHE, Valori.DIECI, Colore.NERO),
	
	CJ(Seme.CUORI, Valori.JACK, Colore.ROSSO),
	QJ(Seme.QUADRI, Valori.JACK, Colore.ROSSO),
	FJ(Seme.FIORI, Valori.JACK, Colore.NERO),
	PJ(Seme.PICCHE, Valori.JACK, Colore.NERO),
	
	CQ(Seme.CUORI, Valori.QUEEN, Colore.ROSSO),
	QQ(Seme.QUADRI, Valori.QUEEN, Colore.ROSSO),
	FQ(Seme.FIORI, Valori.QUEEN, Colore.NERO),
	PQ(Seme.PICCHE, Valori.QUEEN, Colore.NERO),
	
	CK(Seme.CUORI, Valori.KING, Colore.ROSSO),
	QK(Seme.QUADRI, Valori.KING, Colore.ROSSO),
	FK(Seme.FIORI, Valori.KING, Colore.NERO),
	PK(Seme.PICCHE, Valori.KING, Colore.NERO),
	
	JOLLY_ROSSO(Seme.NESSUNO, Valori.JOLLY, Colore.ROSSO),
	JOLLY_NERO(Seme.NESSUNO, Valori.JOLLY, Colore.NERO);
	
	Valori valore;
	Seme seme;
	Colore colore;
	
	@Override
	public String toString()
	{
		return seme.toString() + valore.toString();
	}
	
	public Colore getColore()
	{
		return colore;
	}
	
	public Valori getValore()
	{
		return valore;
	}
	
	Carte(Seme seme, Valori valore, Colore colore)
	{
		this.seme = seme;
		this.valore = valore;
		this.colore = colore;
	}
}
