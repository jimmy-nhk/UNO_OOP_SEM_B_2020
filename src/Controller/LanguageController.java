package Controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.Callable;

public class LanguageController {

    public enum Language {ENGLISH, VIETNAMESE}
    /** the current selected Locale. */
    private static final ObjectProperty<Locale> locale;

    static {
        locale = new SimpleObjectProperty<>(getDefaultLocale());
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }

    /**
     * get the supported Locales.
     *
     * @return List of Locale objects.
     */
    public static List<Locale> getSupportedLocales() {
        return new ArrayList<>(Arrays.asList(Locale.ENGLISH, Locale.GERMAN));
    }

    /**
     * get the default locale. This is the systems default if contained in the supported locales, english otherwise.
     *
     * @return
     */
    public static Locale getDefaultLocale() {
        Locale sysDefault = Locale.getDefault();
        return getSupportedLocales().contains(sysDefault) ? sysDefault : Locale.ENGLISH;
    }

    public static Locale getLocale() {
        return locale.get();
    }

    public static void setLocale(Locale locale) {
        localeProperty().set(locale);
        Locale.setDefault(locale);
    }

    public static ObjectProperty<Locale> localeProperty() {
        return locale;
    }
    //get messages keys from bundle:
    public static String get(final String key, final Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle("lang", getLocale());
        return MessageFormat.format(bundle.getString(key), args);
    }

    public static StringBinding createStringBinding(final String key, Object... args) {
        return Bindings.createStringBinding(() -> get(key, args), locale);
    }

    public static StringBinding createStringBinding(Callable<String> func) {
        return Bindings.createStringBinding(func, locale);
    }

    // setup label text
    public static void setUpLabelText(Label label, final String key, final Object... args) {
        label.textProperty().bind(createStringBinding(key));
    }

    // setup button text
    public static void setUpButtonText(Button button, final String key, final Object... args) {
        button.textProperty().bind(createStringBinding(key));
    }

    // setup radio button text
    public static void setUpRadioButtonText(RadioButton button, final String key, final Object... args) {
        button.textProperty().bind(createStringBinding(key));
    }

    // Get the Locale object of a language
    public static Locale getLanguageLocale(Language language) {
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

    // switch language
    public static void switchLanguage(Locale locale) {
        setLocale(locale);
    }

    // check if current locale is US
    public static boolean isEnglish(){
        return locale.get().getDisplayCountry().equals("United States");
    }
}