package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.stream.Stream;

enum Properties {
    Red,
    Yellow,
    Green,
    Blue,
    Wild
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


}
