package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends ImageView{
    private String properties;
    private String value;

    public Card (String url, String properties, String value)  {
        super();
        this.setImage(new Image(url));
        this.properties = properties;
        this.value = value;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
