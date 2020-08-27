package Model;

public class DigitCard extends Card {

    private double value;

    public DigitCard(String property, double value) {
        super(property);
        this.value = value;
    }

    @Override
    public void setProperty(String property) {
         //
    }

    @Override
    public String getProperty() {
        return null;
    }

}
