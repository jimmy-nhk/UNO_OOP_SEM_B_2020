package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.stream.Stream;

public class Card extends ImageView{


    public static Properties[] properties = Properties.values();
    public static Values[] values = Values.values();
    public static final String BACKIMAGE = "resources/Card/UNO-BACK-card.png";

    private Properties property;
    private Values value;
    private String frontImage;
    private boolean ifSelected ;



    public Card(){}

    public Card (String url, Values value, Properties property)  {
        super(""+url);
        this.property = property;
        this.value = value;
        this.frontImage = url;
        this.ifSelected = false;
        setImage();
    }

    // Set property in this case is set the width and height of the image
    public void setProperty(Properties property) {
        this.property = property;
    }

    public void setImage (){
        super.setFitHeight(120);
        super.setFitWidth(150);
        super.setPreserveRatio(true);
        super.setSmooth(true);
    }

    public void setIfSelected(boolean ifSelected) {
        this.ifSelected = ifSelected;
    }

    public boolean getIfSelected (){
        return ifSelected;
    }

    public void setValue(Values value) {
        this.value = value;
    }

    public Values getValue() {
        return value;
    }

    public Properties getProperty() {
        return property;
    }

    //    isCardPlayable method
    public boolean isCardPlayable(Card previousCard) {
        if (this.getValue().equals(previousCard.getValue()) || this.getProperty().equals(previousCard.getProperty())) {
            return true;
        } else if (this.getProperty().equals(Properties.WILD)) {
            return true;
        } else if (previousCard == null){
            return true;
        } else
            return false;
    }

    // Set back image
    public void setBackImage (){
        super.setImage(new Image(BACKIMAGE+""));
    }

    // Set front image

    public void setFrontImage() {
        super.setImage(new Image(frontImage+""));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return property == card.property &&
                value == card.value;
    }

}
