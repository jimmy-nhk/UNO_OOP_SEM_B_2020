package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.stream.Stream;

enum Properties {
    RED,
    YELLOW,
    GREEN,
    BLUE,
    WILD
}

enum Values {
    ZERO ,
    ONE ,
    TWO ,
    THREE ,
    FOUR,
    FIVE ,
    SIX ,
    SEVEN ,
    EIGHT ,
    NINE ,
    SKIP ,
    REVERSE ,
    PLUS_TWO ,
    PLUS_ZERO ,
    PLUS_FOUR
}

public class Card extends ImageView{


    public static Properties[] properties = Properties.values();
    public static Values[] values = Values.values();


    private Properties property;
    private Values value;

    public Card (String url, Values value, Properties property)  {
        super();
        this.setImage(new Image(url));
        this.property = property;
        this.value = value;
    }

    public void setProperties(Properties properties) {
        this.property = properties;
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
        } else
            return false;
    }
}
