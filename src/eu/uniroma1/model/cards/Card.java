package eu.uniroma1.model.cards;

/**
 * Cards enumerate 
 */
public enum Card
{
	/**
	 * Ace of hearts
	 */
	H1(Suit.HEARTS, Value.ACE, CardColor.RED),
	/**
	 * Ace of diamonds
	 */
	D1(Suit.DIAMONDS, Value.ACE, CardColor.RED),
	/**
	 * Ace of clubs
	 */
	C1(Suit.CLUBS, Value.ACE, CardColor.BLACK),
	/**
	 * Ace of spades
	 */
	S1(Suit.SPADES, Value.ACE, CardColor.BLACK),
	
	/**
	 * Two of hearts
	 */
	H2(Suit.HEARTS, Value.TWO, CardColor.RED),
	/**
	 * Two of diamonds
	 */
	D2(Suit.DIAMONDS, Value.TWO, CardColor.RED),
	/**
	 * Two of clubs
	 */
	C2(Suit.CLUBS, Value.TWO, CardColor.BLACK),
	/**
	 * Two of spades
	 */
	S2(Suit.SPADES, Value.TWO, CardColor.BLACK),
	
	/**
	 * Three of hearts
	 */
	H3(Suit.HEARTS, Value.THREE, CardColor.RED),
	/**
	 * Three of diamonds
	 */
	D3(Suit.DIAMONDS, Value.THREE, CardColor.RED),
	/**
	 * Three of clubs
	 */
	C3(Suit.CLUBS, Value.THREE, CardColor.BLACK),
	/**
	 * Three of spades
	 */
	S3(Suit.SPADES, Value.THREE, CardColor.BLACK),
	
	/**
	 * Four of hearts
	 */
	H4(Suit.HEARTS, Value.FOUR, CardColor.RED),
	/**
	 * Four of diamonds
	 */
	D4(Suit.DIAMONDS, Value.FOUR, CardColor.RED),
	/**
	 * Four of clubs
	 */
	C4(Suit.CLUBS, Value.FOUR, CardColor.BLACK),
	/**
	 * Four of spades
	 */
	S4(Suit.SPADES, Value.FOUR, CardColor.BLACK),
	
	/**
	 * Five of hearts
	 */
	H5(Suit.HEARTS, Value.FIVE, CardColor.RED),
	/**
	 * Five of diamonds
	 */
	D5(Suit.DIAMONDS, Value.FIVE, CardColor.RED),
	/**
	 * Five of clubs
	 */
	C5(Suit.CLUBS, Value.FIVE, CardColor.BLACK),
	/**
	 * Five of spades
	 */
	S5(Suit.SPADES, Value.FIVE, CardColor.BLACK),
	
	/**
	 * Six of hearts
	 */
	H6(Suit.HEARTS, Value.SIX, CardColor.RED),
	/**
	 * Six of diamonds
	 */
	D6(Suit.DIAMONDS, Value.SIX, CardColor.RED),
	/**
	 * Six of clubs
	 */
	C6(Suit.CLUBS, Value.SIX, CardColor.BLACK),
	/**
	 * Six of spades
	 */
	S6(Suit.SPADES, Value.SIX, CardColor.BLACK),
	
	/**
	 * Seven of hearts
	 */
	H7(Suit.HEARTS, Value.SEVEN, CardColor.RED),
	/**
	 * Seven of diamonds
	 */
	D7(Suit.DIAMONDS, Value.SEVEN, CardColor.RED),
	/**
	 * Seven of clubs
	 */
	C7(Suit.CLUBS, Value.SEVEN, CardColor.BLACK),
	/**
	 * Seven of spades
	 */
	S7(Suit.SPADES, Value.SEVEN, CardColor.BLACK),
	
	/**
	 * Eight of hearts
	 */
	H8(Suit.HEARTS, Value.EIGHT, CardColor.RED),
	/**
	 * Eight of diamonds
	 */
	D8(Suit.DIAMONDS, Value.EIGHT, CardColor.RED),
	/**
	 * Eight of clubs
	 */
	C8(Suit.CLUBS, Value.EIGHT, CardColor.BLACK),
	/**
	 * Eight of spades
	 */
	S8(Suit.SPADES, Value.EIGHT, CardColor.BLACK),
	
	/**
	 * Nine of hearts
	 */
	H9(Suit.HEARTS, Value.NINE, CardColor.RED),
	/**
	 * Nine of diamonds
	 */
	D9(Suit.DIAMONDS, Value.NINE, CardColor.RED),
	/**
	 * Nine of clubs
	 */
	C9(Suit.CLUBS, Value.NINE, CardColor.BLACK),
	/**
	 * Nine of spades
	 */
	S9(Suit.SPADES, Value.NINE, CardColor.BLACK),
	
	/**
	 * Ten of hearts
	 */
	H10(Suit.HEARTS, Value.TEN, CardColor.RED),
	/**
	 * Ten of diamonds
	 */
	D10(Suit.DIAMONDS, Value.TEN, CardColor.RED),	
	/**
	 * Ten of clubs
	 */
	C10(Suit.CLUBS, Value.TEN, CardColor.BLACK),
	/**
	 * Ten of spades
	 */
	S10(Suit.SPADES, Value.TEN, CardColor.BLACK),
	
	/**
	 * Jack of hearts
	 */
	HJ(Suit.HEARTS, Value.JACK, CardColor.RED),
	/**
	 * Jack of diamonds
	 */
	DJ(Suit.DIAMONDS, Value.JACK, CardColor.RED),
	/**
	 * Jack of clubs
	 */
	CJ(Suit.CLUBS, Value.JACK, CardColor.BLACK),
	/**
	 * Jack of spades
	 */
	SJ(Suit.SPADES, Value.JACK, CardColor.BLACK),
	
	/**
	 * Queen of hearts
	 */
	HQ(Suit.HEARTS, Value.QUEEN, CardColor.RED),
	/**
	 * Queen of diamonds
	 */
	DQ(Suit.DIAMONDS, Value.QUEEN, CardColor.RED),
	/**
	 * Queen of clubs
	 */
	CQ(Suit.CLUBS, Value.QUEEN, CardColor.BLACK),
	/**
	 * Queen of spades
	 */
	SQ(Suit.SPADES, Value.QUEEN, CardColor.BLACK),
	
	/**
	 * King of hearts
	 */
	HK(Suit.HEARTS, Value.KING, CardColor.RED),
	/**
	 * King of diamonds
	 */
	DK(Suit.DIAMONDS, Value.KING, CardColor.RED),
	/**
	 * King of clubs
	 */
	CK(Suit.CLUBS, Value.KING, CardColor.BLACK),
	/**
	 * King of spades
	 */
	SK(Suit.SPADES, Value.KING, CardColor.BLACK),
	
	/**
	 * Red jolly
	 */
	RED_JOLLY(Suit.NOTHING, Value.JOLLY, CardColor.RED),
	/**
	 * Black jolly
	 */
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
	 * Get color of card
	 * @return card color
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
