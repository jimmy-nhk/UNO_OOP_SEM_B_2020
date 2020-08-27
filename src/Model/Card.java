package Model;

public abstract class Card {
    private String property;

    public Card(String property) {
        this.property = property;

    }

    public abstract void setProperty(String property);
    public abstract String getProperty();
}
