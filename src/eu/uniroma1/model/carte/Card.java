package eu.uniroma1.model.carte;

public enum Card
{
	C1(Suit.CUORI, Value.ASSO, CardColor.ROSSO),
	Q1(Suit.QUADRI, Value.ASSO, CardColor.ROSSO),
	F1(Suit.FIORI, Value.ASSO, CardColor.NERO),
	P1(Suit.PICCHE, Value.ASSO, CardColor.NERO),
	
	C2(Suit.CUORI, Value.DUE, CardColor.ROSSO),
	Q2(Suit.QUADRI, Value.DUE, CardColor.ROSSO),
	F2(Suit.FIORI, Value.DUE, CardColor.NERO),
	P2(Suit.PICCHE, Value.DUE, CardColor.NERO),
	
	C3(Suit.CUORI, Value.TRE, CardColor.ROSSO),
	Q3(Suit.QUADRI, Value.TRE, CardColor.ROSSO),
	F3(Suit.FIORI, Value.TRE, CardColor.NERO),
	P3(Suit.PICCHE, Value.TRE, CardColor.NERO),
	
	C4(Suit.CUORI, Value.QUATTRO, CardColor.ROSSO),
	Q4(Suit.QUADRI, Value.QUATTRO, CardColor.ROSSO),
	F4(Suit.FIORI, Value.QUATTRO, CardColor.NERO),
	P4(Suit.PICCHE, Value.QUATTRO, CardColor.NERO),
	
	C5(Suit.CUORI, Value.CINQUE, CardColor.ROSSO),
	Q5(Suit.QUADRI, Value.CINQUE, CardColor.ROSSO),
	F5(Suit.FIORI, Value.CINQUE, CardColor.NERO),
	P5(Suit.PICCHE, Value.CINQUE, CardColor.NERO),
	
	C6(Suit.CUORI, Value.SEI, CardColor.ROSSO),
	Q6(Suit.QUADRI, Value.SEI, CardColor.ROSSO),
	F6(Suit.FIORI, Value.SEI, CardColor.NERO),
	P6(Suit.PICCHE, Value.SEI, CardColor.NERO),
	
	C7(Suit.CUORI, Value.SETTE, CardColor.ROSSO),
	Q7(Suit.QUADRI, Value.SETTE, CardColor.ROSSO),
	F7(Suit.FIORI, Value.SETTE, CardColor.NERO),
	P7(Suit.PICCHE, Value.SETTE, CardColor.NERO),
	
	C8(Suit.CUORI, Value.OTTO, CardColor.ROSSO),
	Q8(Suit.QUADRI, Value.OTTO, CardColor.ROSSO),
	F8(Suit.FIORI, Value.OTTO, CardColor.NERO),
	P8(Suit.PICCHE, Value.OTTO, CardColor.NERO),
	
	C9(Suit.CUORI, Value.NOVE, CardColor.ROSSO),
	Q9(Suit.QUADRI, Value.NOVE, CardColor.ROSSO),
	F9(Suit.FIORI, Value.NOVE, CardColor.NERO),
	P9(Suit.PICCHE, Value.NOVE, CardColor.NERO),
	
	C10(Suit.CUORI, Value.DIECI, CardColor.ROSSO),
	Q10(Suit.QUADRI, Value.DIECI, CardColor.ROSSO),
	F10(Suit.FIORI, Value.DIECI, CardColor.NERO),
	P10(Suit.PICCHE, Value.DIECI, CardColor.NERO),
	
	CJ(Suit.CUORI, Value.JACK, CardColor.ROSSO),
	QJ(Suit.QUADRI, Value.JACK, CardColor.ROSSO),
	FJ(Suit.FIORI, Value.JACK, CardColor.NERO),
	PJ(Suit.PICCHE, Value.JACK, CardColor.NERO),
	
	CQ(Suit.CUORI, Value.QUEEN, CardColor.ROSSO),
	QQ(Suit.QUADRI, Value.QUEEN, CardColor.ROSSO),
	FQ(Suit.FIORI, Value.QUEEN, CardColor.NERO),
	PQ(Suit.PICCHE, Value.QUEEN, CardColor.NERO),
	
	CK(Suit.CUORI, Value.KING, CardColor.ROSSO),
	QK(Suit.QUADRI, Value.KING, CardColor.ROSSO),
	FK(Suit.FIORI, Value.KING, CardColor.NERO),
	PK(Suit.PICCHE, Value.KING, CardColor.NERO),
	
	JOLLY_ROSSO(Suit.NESSUNO, Value.JOLLY, CardColor.ROSSO),
	JOLLY_NERO(Suit.NESSUNO, Value.JOLLY, CardColor.NERO);
	
	Value valore;
	Suit seme;
	CardColor colore;
	
	@Override
	public String toString()
	{
		return seme.toString() + valore.toString();
	}
	
	public CardColor getColore()
	{
		return colore;
	}
	
	public Value getValore()
	{
		return valore;
	}
	
	Card(Suit seme, Value valore, CardColor colore)
	{
		this.seme = seme;
		this.valore = valore;
		this.colore = colore;
	}
}
