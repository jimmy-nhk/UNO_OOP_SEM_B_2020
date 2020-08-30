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
    TEN ,
    SKIP ,
    REVERSE ,
    PLUS_TWO ,
    PLUS_ZERO ,
    PLUS_FOUR
}

public class Card extends ImageView{


    public static Properties[] properties = Properties.values();
    public static Values[] values = Values.values();


    private String property;
    private String value;

    public Card (String url, String value, String property)  {
        super();
        this.setImage(new Image(url));
        this.property = property;
        this.value = value;
    }

    public void setProperties(String properties) {
        this.property = properties;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
