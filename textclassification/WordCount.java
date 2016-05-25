package textclassification;


/**
 * This class holds the number of occurences of a word
 * for a specific class in the training data set.
 * 
 * @author Johan Hagelbäck (johan.hagelback@gmail.com)
 */
public class WordCount 
{
    //The word
    private final String word;
    //Number of occurences per class
    private final int[] count;
    
    /**
     * New word in the bag of words.
     * 
     * @param word The word
     * @param noClasses Number of classes in the classification task
     */
    public WordCount(String word, int noClasses)
    {
        this.word = word;
        count = new int[noClasses];
    }
    
    /**
     * Returns the total number of occurences (for all classes) for
     * this word.
     * 
     * @return Total number of occurences
     */
    public int getOccurences()
    {
        int tot = 0;
        for (int o:count)
        {
            tot += o;
        }
        return tot;
    }
    
    /**
     * Increments the occurences counter of this word
     * for the specified class.
     * 
     * @param classValue Class value
     */
    public void increment(int classValue)
    {
        count[classValue]++;
    }
    
    /**
     * Returns the number of occurences of this word
     * for the specified class.
     * 
     * @param classValue Class value
     * @return Number of occurences
     */
    public int getCount(int classValue)
    {
        return count[classValue];
    }

    @Override
    public String toString()
    {
        String str = word + " [";
        
        for (int c = 0; c < count.length; c++)
        {
            str += "C" + c + "=" + count[c];
            if (c < count.length - 1) str += ", ";
        }
        
        str += "]\n";
        
        return str;
    }
}
