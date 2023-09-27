package eu.uniroma1.model.cards;

/**
 * Cards enumerate 
 */
public enum Card
{
	C1(Suit.HEARTS, Value.ACE, CardColor.RED),
	Q1(Suit.DIAMONDS, Value.ACE, CardColor.RED),
	F1(Suit.CLUBS, Value.ACE, CardColor.BLACK),
	P1(Suit.SPADES, Value.ACE, CardColor.BLACK),
	
	C2(Suit.HEARTS, Value.TWO, CardColor.RED),
	Q2(Suit.DIAMONDS, Value.TWO, CardColor.RED),
	F2(Suit.CLUBS, Value.TWO, CardColor.BLACK),
	P2(Suit.SPADES, Value.TWO, CardColor.BLACK),
	
	C3(Suit.HEARTS, Value.THREE, CardColor.RED),
	Q3(Suit.DIAMONDS, Value.THREE, CardColor.RED),
	F3(Suit.CLUBS, Value.THREE, CardColor.BLACK),
	P3(Suit.SPADES, Value.THREE, CardColor.BLACK),
	
	C4(Suit.HEARTS, Value.FOUR, CardColor.RED),
	Q4(Suit.DIAMONDS, Value.FOUR, CardColor.RED),
	F4(Suit.CLUBS, Value.FOUR, CardColor.BLACK),
	P4(Suit.SPADES, Value.FOUR, CardColor.BLACK),
	
	C5(Suit.HEARTS, Value.FIVE, CardColor.RED),
	Q5(Suit.DIAMONDS, Value.FIVE, CardColor.RED),
	F5(Suit.CLUBS, Value.FIVE, CardColor.BLACK),
	P5(Suit.SPADES, Value.FIVE, CardColor.BLACK),
	
	C6(Suit.HEARTS, Value.SIX, CardColor.RED),
	Q6(Suit.DIAMONDS, Value.SIX, CardColor.RED),
	F6(Suit.CLUBS, Value.SIX, CardColor.BLACK),
	P6(Suit.SPADES, Value.SIX, CardColor.BLACK),
	
	C7(Suit.HEARTS, Value.SEVEN, CardColor.RED),
	Q7(Suit.DIAMONDS, Value.SEVEN, CardColor.RED),
	F7(Suit.CLUBS, Value.SEVEN, CardColor.BLACK),
	P7(Suit.SPADES, Value.SEVEN, CardColor.BLACK),
	
	C8(Suit.HEARTS, Value.EIGHT, CardColor.RED),
	Q8(Suit.DIAMONDS, Value.EIGHT, CardColor.RED),
	F8(Suit.CLUBS, Value.EIGHT, CardColor.BLACK),
	P8(Suit.SPADES, Value.EIGHT, CardColor.BLACK),
	
	C9(Suit.HEARTS, Value.NINE, CardColor.RED),
	Q9(Suit.DIAMONDS, Value.NINE, CardColor.RED),
	F9(Suit.CLUBS, Value.NINE, CardColor.BLACK),
	P9(Suit.SPADES, Value.NINE, CardColor.BLACK),
	
	C10(Suit.HEARTS, Value.TEN, CardColor.RED),
	Q10(Suit.DIAMONDS, Value.TEN, CardColor.RED),
	F10(Suit.CLUBS, Value.TEN, CardColor.BLACK),
	P10(Suit.SPADES, Value.TEN, CardColor.BLACK),
	
	CJ(Suit.HEARTS, Value.JACK, CardColor.RED),
	QJ(Suit.DIAMONDS, Value.JACK, CardColor.RED),
	FJ(Suit.CLUBS, Value.JACK, CardColor.BLACK),
	PJ(Suit.SPADES, Value.JACK, CardColor.BLACK),
	
	CQ(Suit.HEARTS, Value.QUEEN, CardColor.RED),
	QQ(Suit.DIAMONDS, Value.QUEEN, CardColor.RED),
	FQ(Suit.CLUBS, Value.QUEEN, CardColor.BLACK),
	PQ(Suit.SPADES, Value.QUEEN, CardColor.BLACK),
	
	CK(Suit.HEARTS, Value.KING, CardColor.RED),
	QK(Suit.DIAMONDS, Value.KING, CardColor.RED),
	FK(Suit.CLUBS, Value.KING, CardColor.BLACK),
	PK(Suit.SPADES, Value.KING, CardColor.BLACK),
	
	RED_JOLLY(Suit.NOTHING, Value.JOLLY, CardColor.RED),
	BLACK_JOLLY(Suit.NOTHING, Value.JOLLY, CardColor.BLACK);
	
	Value value;
	Suit suit;
	CardColor colour;
	
	@Override
	public String toString()
	{
		return suit.toString() + value.toString();
	}
	
	/**
	 * Get colour of card
	 * @return card colour
	 */
	public CardColor getColour()
	{
		return colour;
	}
	
	/**
	 * Get value of card
	 * @return card value
	 */
	public Value getValue()
	{
		return value;
	}
	
	/**
	 * Card builder
	 * @param suit suit of card
	 * @param value value of card
	 * @param colour colour of card 
	 */
	Card(Suit suit, Value value, CardColor colour)
	{
		this.suit = suit;
		this.value = value;
		this.colour = colour;
	}
}
