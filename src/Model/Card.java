/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2020B
  Assessment: Final Project
  Created date: dd/mm/yyyy (e.g. 31/03/2019)
  By: Your name (Your studen id)
  Last modified: dd/mm/yyyy (e.g. 05/04/2019)
  By: Your name (Your studen id)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/
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