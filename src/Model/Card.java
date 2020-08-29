package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Card extends ImageView{
    private String properties;
    private String value;
    public Card (String url, String properties, String value) throws MalformedURLException {
        super(url);
        this.properties = properties;
        this.value = value;
    }

    public void setProperties(int preferLength, int preferWidth){
        super(preferLength, preferWidth);
    }
}
