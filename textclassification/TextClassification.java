package textclassification;


import weka.classifiers.*;
import weka.core.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * This is an implementation of a Naive Bayes classifier for
 * text classification using the bag of words approach. It is
 * based on the WEKA framework.
 * 
 * @author Johan Hagelbäck (johan.hagelback@gmail.com)
 */
public class TextClassification extends Classifier
{
    //Bag of words
    private BagOfWords words;
    //Text tokenizer
    private TextConverter conv;
    
    public static Settings settings;
    
    /**
     * Runs a classification experiment.
     * The train and test data sets can be input in two ways:
     * - Send as command line arguments (first argument is training set, second is test set)
     * - Set in the Settings class
     * 
     * If no test data set is specified, 10-fold cross validation of the
     * training data set will be used.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	List<String> datasets = new ArrayList<String>();
    	/*
    	datasets.addAll(Arrays.asList(
			"F:\\java_kurser\\TextClassification\\data\\JESPER-50-TEXT.arff", 
			"F:\\java_kurser\\TextClassification\\data\\JESPER-100-TEXT.arff",
			"F:\\java_kurser\\TextClassification\\data\\JESPER-150-TEXT.arff",
			"F:\\java_kurser\\TextClassification\\data\\JESPER-200-TEXT.arff",
			"F:\\java_kurser\\TextClassification\\data\\ELEONOR-50-TEXT.arff", 
			"F:\\java_kurser\\TextClassification\\data\\ELEONOR-100-TEXT.arff",
			"F:\\java_kurser\\TextClassification\\data\\ELEONOR-150-TEXT.arff",
			"F:\\java_kurser\\TextClassification\\data\\ELEONOR-200-TEXT.arff"));
			*/
    	datasets.addAll(Arrays.asList(
    			"F:\\java_kurser\\TextClassification\\data\\ELEONOR-TAG.arff", 
    			"F:\\java_kurser\\TextClassification\\data\\JESPER-TAG.arff"));
	
	for(String dataset : datasets){
		settings = new Settings();
		String experimentName = "BASELINE";
		settings.setEXPERIMENT_NAME(experimentName);
		settings.setTRAINING_DATA(dataset);
		settings.setUSE_ZERO_R_BASELINE(true);
		if(dataset.contains("JESPER"))
			settings.setDATASET_AUTHOR("JESPER");
		else
			settings.setDATASET_AUTHOR("ELEONOR");
		
		if(dataset.contains("-50-"))
			settings.setDATASET_SIZE(50);
		if(dataset.contains("-100-"))
			settings.setDATASET_SIZE(100);
		if(dataset.contains("-150-"))
			settings.setDATASET_SIZE(150);
		if(dataset.contains("-200-"))
			settings.setDATASET_SIZE(200);
		if(dataset.contains("-TAG"))
			settings.setDATASET_SIZE(60);
		
		doTextClassification();
		}
	
	for (int ngram = 0; ngram < 2; ngram++) { 								//NGRAM: 1 OR 2
		for (int missingword = 0; missingword < 2; missingword++) {			//MISSING WORD PENALTY: ON OR OFF
			for (int removeword = 0; removeword < 2; removeword++) {		//REMOVE UNWANTED WORDS: ON OR OFF
				for (int laplace = 0; laplace < 2; laplace++) {				//USE LAPLACE SMOOTHING: ON OR OFF
					for(String dataset : datasets){
						settings = new Settings();
						String experimentName = "";
						if(ngram == 1){
							settings.setNGRAM(2);
							experimentName += "NGRAM2-";
						}
						if(missingword == 1){
							settings.setUSE_MISSING_WORD_PENALTY(true);
							experimentName += "MISSINGWORD-";
						}
						if(removeword == 1){
							settings.setREMOVE_UNWANTED_WORDS(true);
							experimentName += "REMOVEWORD-";
						}
						if(laplace == 1){
							settings.setUSE_LAPLACE_SMOOTHING(true);
							experimentName += "LAPLACE-";
						}
						if(dataset.contains("-50-"))
							settings.setDATASET_SIZE(50);
						if(dataset.contains("-100-"))
							settings.setDATASET_SIZE(100);
						if(dataset.contains("-150-"))
							settings.setDATASET_SIZE(150);
						if(dataset.contains("-200-"))
							settings.setDATASET_SIZE(200);
						if(dataset.contains("-TAG"))
							settings.setDATASET_SIZE(60);
						
						settings.setEXPERIMENT_NAME(experimentName);
						settings.setTRAINING_DATA(dataset);
						if(dataset.contains("JESPER"))
							settings.setDATASET_AUTHOR("JESPER");
						else
							settings.setDATASET_AUTHOR("ELEONOR");
						
						System.out.println("C:\\Users\\Jesper\\Desktop\\examensarbete\\resultat2\\" + settings.DATASET_AUTHOR + "-" + settings.EXPERIMENT_NAME + settings.DATASET_SIZE + ".txt");
						doTextClassification();
					}
				}
			}
		}
	}
	
    }
    
    public static void doTextClassification(){

        try 
        {
        	
        	String settingsString = getSettingsString();
        	System.out.println(settingsString);
        	
                String[] args = new String[2];
                args[0] = "-t";
                //Training and test set
                args[1] = settings.getTRAINING_DATA();
            
            //Check if file(s) exist
                System.out.println(settings.getTRAINING_DATA());
            File fTrain = new File(settings.getTRAINING_DATA());
            if (!fTrain.exists())
            {
                System.out.println("Training data set doesn't exist. Exiting program.");
                System.exit(1);
            } 
            if (settings.TEST_DATA != null && !settings.TEST_DATA.equals(""))
            {
                File fTest = new File(settings.TEST_DATA);
                if (!fTest.exists())
                {
                    System.out.println("Test data set doesn't exist. Exiting program.");
                    System.exit(1);
                } 
            }
            
            long start = System.currentTimeMillis();
            String result = Evaluation.evaluateModel(new TextClassification(), args);
            long elapsed = System.currentTimeMillis() - start;
            System.out.println(result);
            System.out.println("Total execution time: " + elapsed + " ms");   
            
            settingsString += "\n" + result;
            
            if(settings.SAVE_RESULT){
            	String path = "C:\\Users\\Jesper\\Desktop\\examensarbete\\tagtest\\" + settings.DATASET_AUTHOR + "-" + settings.EXPERIMENT_NAME + settings.DATASET_SIZE + ".txt";
            	Files.write( Paths.get(path), settingsString.getBytes());
            }
        }
        catch (FileAlreadyExistsException ex)
        {
            System.err.println("This experiment name has already been used. Results were NOT saved.");
        }
        catch (Exception ex)
        {
            //Shall not happen... but you never know
            ex.printStackTrace();
        }
    }
    
    public static String getSettingsString(){
    	//Save all settings to string
    	String settingsString = "DATASET_AUTHOR:" + settings.DATASET_AUTHOR + "\n";
    	settingsString += "DATASET_SIZE:" + settings.DATASET_SIZE + "\n";
    	settingsString += "EXPERIMENT_NAME:" + settings.EXPERIMENT_NAME + "\n";
    	settingsString += "\n";
    	settingsString += "NGRAM:" + settings.NGRAM + "\n";
    	settingsString += "USE_MISSING_WORD_PENALTY:" + settings.USE_MISSING_WORD_PENALTY + "\n";
    	settingsString += "REMOVE_UNWANTED_WORDS:" + settings.REMOVE_UNWANTED_WORDS + "\n";
    	settingsString += "USE_LAPLACE_SMOOTHING:" + settings.USE_LAPLACE_SMOOTHING + "\n";
    	settingsString += "USE_ZERO_R_BASELINE:" + settings.USE_ZERO_R_BASELINE + "\n";
    	return settingsString;
    }
    
    @Override
    public void buildClassifier(Instances mData) throws Exception
    {
        if (mData.numAttributes() != 2) throw new weka.core.WekaException("Only two attributes {text,classValue} is supported");
        if (!mData.attribute(0).isString()) throw new weka.core.WekaException("First attribute must be of type string");
        if (!mData.classAttribute().isNominal()) throw new weka.core.WekaException("Class attribute must be nominal");
        
        long start = System.currentTimeMillis();
        
        //Create new bag of words
        words = new BagOfWords(mData.numClasses(), settings);
        //Init text converter object
        conv = new TextConverter(settings);
        
        //Iterate through all instances
        for (int i = 0; i < mData.numInstances(); i++)
        {
            //Train the classifier
            Instance mInst = mData.instance(i);
            train(mInst);
        }
        
        long elapsed = System.currentTimeMillis() - start;
        double avg = Math.round((double)elapsed / (double)mData.numInstances() * 100.0) / 100.0;
        System.out.println("Classifier build time: " + elapsed + " ms on " + mData.numInstances() + " instances (" + avg + " ms per instance)");
        
        //Dumps the bag of words
        //System.out.println(words.toString());
    }
    
    /**
     * Trains the classifier with the specified instance.
     * The text is tokenized and each word is appended
     * to the bag of words.
     * 
     * @param mInst The instance
     */
    private void train(Instance mInst)
    {
        //Class for this instance
        int classValue = (int)mInst.classValue();
        
        //Convert the text to word tokens
        List<String> tokens = conv.tokenize(mInst.stringValue(0));
        //Add all words to the bag of words
        for (String s:tokens)
        {
            words.append(s, classValue);
        }
    }
    
    @Override
    public double classifyInstance(Instance mInst) throws Exception
    {
        if (!mInst.attribute(0).isString()) throw new weka.core.WekaException("First attribute must be of type string");
        
        //Baseline classifier: Zero-R
        //Zero-R returns the most probable class in the
        //training data set.
        if (settings.USE_ZERO_R_BASELINE)
        {
            return words.mostProbableClass();
        }
        
        //Naive Bayes classifier
        double[] probs = new double[mInst.numClasses()];
        //Probabilities per class
        for (int c = 0; c < mInst.numClasses(); c++)
        {
            probs[c] = words.getProbability(c);
        }
        
        //Convert the text to word tokens
        List<String> tokens = conv.tokenize(mInst.stringValue(0));
        
        //Iterate through all words
        for (String s:tokens)
        {
            //Probabilities for the word per class
            for (int c = 0; c < mInst.numClasses(); c++)
            {
                probs[c] *= words.getProbability(s, c);
            }
            
            //To avoid the product of probabilities from 
            //being too low.
            doubleSafeCheck(probs);
        }
        
        //Find which class has the highest probability...
        double highestProb = 0;
        int bestClass = 0;
        for (int c = 0; c < mInst.numClasses(); c++)
        {
            if (probs[c] == highestProb)
            {
                System.out.println(probs[c] + " = " + highestProb);
                //Probabilities for two classes are equal. Use the class with
                //highest prior probability (the one with most instances).
                if (words.getProbability(c) > words.getProbability(bestClass))
                {
                    highestProb = probs[c];
                    bestClass = c;
                }
            }
            
            if (probs[c] > highestProb)
            {
                highestProb = probs[c];
                bestClass = c;
            }
            
        }
        //... and return the class
        return bestClass;
    }
    
    /**
     * Safe check to avoid a probability from moving to close to the
     * minimum of double.
     * 
     * @param probs Probabilities for each class
     */
    private void doubleSafeCheck(double[] probs)
    {
        boolean low = false;
        for (int c = 0; c < probs.length; c++)
        {
            //The probability is too low
            if (probs[c] < 1.0E-30)
            {
                low = true;
                break;
            }
        }
        
        if (low)
        {
            /*
            Multiply all probabilities by a factor.
            This will not have a negative effect on the result, since it is only 
            a comparison between the probablilities and not the actual probability 
            values themselves that are interesting.
            */
            for (int c = 0; c < probs.length; c++)
            {
                probs[c] *= 1.0E+10;
            }
        }
    }
    
    @Override
    public String toString()
    {
        if (settings.USE_ZERO_R_BASELINE)
        {
            return "Zero-R baseline Classifier";
        }
        
        String str = "Naive Bayes Text Classifier [" + words.getSize() +" words";
        if (settings.NGRAM > 1) str += ", " + settings.NGRAM + "-gram";
        if (settings.USE_LAPLACE_SMOOTHING) str += ", Laplace smoothing = (" + settings.LAPLACE_SMOOTHING[0] + "," + settings.LAPLACE_SMOOTHING[1] + ")";
        if (settings.USE_MISSING_WORD_PENALTY) str += ", Missing word penalty = " + settings.MISSING_WORD_PENALTY;
        if (settings.REMOVE_UNWANTED_WORDS) str += ", remove unwanted words = on";
        if (settings.REMOVE_NUMBERS) str += ", remove numbers = on";
        if (settings.REPLACE_CHARS) str += ", replace special chars = on";
        str += "]";
        return str;
    }
}
