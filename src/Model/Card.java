/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2020B
  Assessment: Final Project
 Team members:
        1. Nguyen Dang Huynh Chau - s3777214
        2. Nguyen Hoang Khang - s3802040
        3. Nguyen Dinh Dang Nguyen - s3759957
        4. Bui Thanh Huy - s3740934
        5. Nguyen Phuoc Nhu Phuc - s3819660
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/
package Model;

public class Card {
    private Property property;
    private Color color;
    private int value;

    public Card(Property property, Color color, int value) {
        this.property = property;
        this.color = color;
        this.value = value;
    }

    public Property getProperty() {
        return property;
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public boolean equals(Card other) {
        return property.equals(other.getProperty()) && color.equals(other.getColor());
    }

    @Override
    public String toString() {
        return "(" + property + ", " + color + ", value=" + value + ")\n";
    }
}