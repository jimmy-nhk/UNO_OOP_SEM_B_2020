package Controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageController {
    //data field:
    public enum Language {ENGLISH, VIETNAMESE}

    private static final ObjectProperty<Locale> locale;

    static {
        locale = new SimpleObjectProperty<>(getLanguage(Language.ENGLISH));
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }

    //get the value of current locale:
    public static Locale getLocale() {
        return locale.get();
    }

    //get properties of current locale:
    public static ObjectProperty<Locale> localeProperty() {
        return locale;
    }

//    public static void setLocale(Locale locale) {
//        localeProperty().set(locale);
//        Locale.setDefault(locale);
//    }


    public static void setLocale(Locale locale) {
        LanguageController.locale.set(locale);
    }

    //get resources and get format from the file .properties:
    public static String getResource(String keyword, Object args) {
        ResourceBundle rb = ResourceBundle.getBundle("lang", getLocale());
        return MessageFormat.format(rb.getString(keyword), args);
    }

    //Bind the string:
    public static StringBinding stringBinding(String key, Object... args) {
        return Bindings.createStringBinding(() -> getResource(key, args), locale);
    }

    //Label texts and texts in button need to be set in case switching lang:
    //set label texts:
    public static void setUpLabelText(Label label, String key) {
        label.textProperty().bind(stringBinding(key));
    }
    //set button texts:
    public static void setButtonText(Button button, String key) {
        button.textProperty().bind(stringBinding(key));
    }

    // Get the Locale object of a language
    public static Locale getLanguage(Language language) {
        switch (language) {
            case ENGLISH: {
                return new Locale("en", "US");
            }
            case VIETNAMESE: {
                return new Locale("vi", "VN");
            }
            default:
                return new Locale("en", "US");
        }
    }

    //Change the language:
    public static void changeLanguage(Locale locale) {
        setLocale(locale);
    }

    // check if current locale is US
    public static boolean isEnglish(){
        return locale.get().getDisplayCountry().equals("United States");
    }
}