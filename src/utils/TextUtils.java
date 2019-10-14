package utils;

public class TextUtils {
    private static final TextUtils text = new TextUtils();

    private TextUtils(){}

    public static final TextUtils getInstance(){
        return text;
    }

    public String[] textFilter(String word){
        return word.replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+");
    }

    public boolean wordCheck(String word){
        return word.length() > 1;
    }
}
