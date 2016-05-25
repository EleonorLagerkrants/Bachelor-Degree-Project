package textclassification;


import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * This class converts a text to a bag of words. It has some optional options:
 * - Remove unwanted words (a, an, and, or, ...)
 * - Remove special characters (dots, commas, ...)
 * - Apply N-gram
 * 
 * @author Johan Hagelbäck (johan.hagelback@gmail.com)
 */
public class TextConverter 
{
    /** Contains a list of unwanted words */
    private final HashMap<String,Object> unwanted;
   
    private Settings settings;
    /**
     * Constructor.
     * @param settings 
     */
    public TextConverter(Settings settings)
    {
    	this.settings = settings;
        unwanted = new HashMap<>();
        //Copy the list to a hashmap (for improved query times)
        for (String w:settings.UNWANTED_WORDS)
        {
            unwanted.put(w, null);
        }
    }
    
    /**
     * Fixes a text by changing new lines to white spaces and remove
     * special characters. All replacements are specified in the Settings
     * class.
     * 
     * @param text The text
     * @return Fixed text
     */
    private String fixText(String text)
    {
        text = text.toLowerCase();
        
        if (settings.REPLACE_CHARS)
        {
            for (String[] REPLACE_CHARS_LIST : settings.REPLACE_CHARS_LIST) 
            {
                text = text.replace(REPLACE_CHARS_LIST[0], REPLACE_CHARS_LIST[1]);
            }
        }
        
        return text;
    }
    
    /**
     * Checks if a word is valid by matching against the unwanted
     * words list and some basic rules (no empty words).
     * 
     * @param word The word
     * @return True if the word is valid, false otherwise
     */
    private boolean valid(String word)
    {
        if (word.equals("")) 
        {
            //Empty word not allowed
            return false;
        }
        if (settings.REMOVE_UNWANTED_WORDS)
        {
            //Remove unwanted words
            if (unwanted.containsKey(word)) 
            {
                return false;
            }
        }
        if (settings.REMOVE_NUMBERS)
        {
            if (isNumber(word))
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if a word is a number or not.
     * 
     * @param word The word
     * @return True if word is a number, false otherwise
     */
    private boolean isNumber(String word)
    {
        try
        {
            Double.parseDouble(word);
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
        return true;
    }
    
    /**
     * Converts a text to a bag of words.
     * 
     * @param text The text
     * @return Bag of words
     */
    public List<String> tokenize(String text)
    {
        //Remove new lines and special characters
        text = fixText(text);
        
        //Split the text into words
        List list = new ArrayList<>();
        String[] tokens = text.split(" ");
        for (String t:tokens)
        {
            t = t.trim();
            //Check if the word is valid...
            if (valid(t))
            {
                //... and if so, add it to the list
                list.add(t);
            }
        }
        
        //Apply N-gram (optional)
        list = applyNgram(list, settings.NGRAM);
        
        return list;
    }
    
    /**
     * Applies N-gram to a tokenized text. N-gram groups N words into one feature instead
     * of having each single word as a feature. Example: "The company", "company develops", 
     * "develops software", ... 
     * 
     * @param tokens Tokenized text
     * @param n N factor (1 = no N-gram)
     * @return Tokenized text with N-gram applied
     */
    private List applyNgram(List tokens, int n)
    {
        //If N is 1 or below, do nothing
        if (n <= 1) return tokens;
        
        //Apply N-gram to the list of words
        List newT = new ArrayList<>(tokens.size());
        for (int i = 0; i < tokens.size() - n; i++)
        {
            String t = "";
            
            for (int j = 0; j < n; j++)
            {
                t += tokens.get(i+j) + " ";
            }
            t = t.trim();
            newT.add(t);
            
        }
        return newT;
    }
}
