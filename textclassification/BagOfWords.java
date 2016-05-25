package textclassification;

import java.util.HashMap;
import java.util.Collection;

/**
 * This class represents a bag of words generated from a text.
 * 
 * @author Johan Hagelbäck (johan.hagelback@gmail.com)
 */
public class BagOfWords 
{
    /** Hashmap containing the words found in the training texts. */
    private final HashMap<String, WordCount> words;
    /** Number of instances for each class value */
    private final int[] classCount;
    /** Total number of instances */
    private int total;
    /** Number of classes in the data set */
    private final int noClasses;
    /** Laplace smoothing factors */
    private final int[] laplace;
    
    private Settings settings;
    
    /**
     * Creates a new bag of words with the specified number of classes.
     * 
     * @param noClasses Number of classes
     * @param settings 
     */
    public BagOfWords(int noClasses, Settings settings)
    {
        words = new HashMap<>();
        this.noClasses = noClasses;
        classCount = new int[noClasses];
        laplace = new int[2];
        this.settings = settings;
        if (settings.USE_LAPLACE_SMOOTHING)
        {
            laplace[0] = settings.LAPLACE_SMOOTHING[0];
            laplace[1] = settings.LAPLACE_SMOOTHING[1];
        }
    }
    
    /**
     * Returns the most probable class (the class with most occurences) 
     * in the dataset.
     * 
     * @return Class value
     */
    public int mostProbableClass()
    {
        int max = classCount[0];
        int index = 0;
        
        for (int i = 1; i < classCount.length; i++)
        {
            if (classCount[i] > max)
            {
                max = classCount[i];
                index = i;
            }
        }
        
        return index;
    }
    
    /**
     * Returns the least probable class (the class with least occurences) 
     * in the dataset.
     * 
     * @return Class value
     */
    public int leastProbableClass()
    {
        int min = classCount[0];
        int index = 0;
        
        for (int i = 1; i < classCount.length; i++)
        {
            if (classCount[i] < min)
            {
                min = classCount[i];
                index = i;
            }
        }
        
        return index;
    }
    
    /**
     * Appends a new word to the bag of words. If the word doesn't exist in the
     * bag, it is added.
     * 
     * @param word The word
     * @param classValue Class value of the instance the word was found in
     */
    public void append(String word, int classValue)
    {
        //Increment class counter
        classCount[classValue]++;
        total++;
        
        //Check if found in the hashmap
        WordCount c = words.get(word);
        if (c != null)
        {
            //Increment number of occurences
            c.increment(classValue);
        }
        else
        {
            //Not found. Add it.
            c = new WordCount(word, noClasses);
            //Increment number of occurences
            c.increment(classValue);
            words.put(word, c);
        }
    }
    
    /**
     * Returns the probability for a class.
     * 
     * @param classValue The class
     * @return Probability for the class
     */
    public double getProbability(int classValue)
    {
        return (double)classCount[classValue] / (double)total;
    }
    
    /**
     * Returns the probability for a word in the specified class.
     * 
     * @param word The word
     * @param classValue The class
     * @return Probability for the word being in the class
     */
    public double getProbability(String word, int classValue)
    {
        WordCount c = words.get(word);
        if (c != null)
        {
            //Optional: Don't count words with too few occurences
            //if (c.getOccurences() <= 2) return 1;
            
            double p = (double)(c.getCount(classValue) + laplace[0]) / (double)(classCount[classValue] + laplace[1]);
            if (settings.USE_MISSING_WORD_PENALTY && c.getCount(classValue) == 0) 
            {
                //Word not found for specified class. Penalize.
                //Different penalize factors affect the result in different ways.
                p = settings.MISSING_WORD_PENALTY / (double)classCount[classValue];
            }
            return p;
        }
        
        //Unknown word. 
        return 1.0;
    }
    
    /**
     * Returns the number of words in the bag of words.
     * 
     * @return Number of words
     */
    public int getSize()
    {
        return words.size();
    }
    
    /**
     * Outputs the bag of words.
     * 
     * @return Bag of words as string
     */
    @Override
    public String toString()
    {
        String str = "BagOfWords size: " + words.size();
        Collection<WordCount> collection = words.values();
        for (WordCount c:collection)
        {
            str += c.toString();
        }
        return str;
    }
}
