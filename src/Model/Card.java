package Model;

public class Card
{
	private Property property;
	private Color color;
	private int value;	
	
	public Card(Property property, Color color, int value)
	{
		this.property = property;
		this.color = color;
		this.value = value;
	}

	public Property getProperty()
	{
		return property;
	}

	public Color getColor()
	{
		return color;
	}

	public int getValue()
	{
		return value;
	}
	
	public boolean equals(Card other)
	{
		return property.equals(other.getProperty()) && color.equals(other.getColor());
	}		

	@Override
	public String toString()
	{
		return "(" + property + ", " + color + ", value=" + value + ")\n";
	}	
}