package textclassification;

/**
 * Settings for the Naive Bayes text classification tool.
 * 
 * @author Johan HagelbÃ¤ck (johan.hagelback@gmail.com)
 */
public class Settings 
{
	 public int getDATASET_SIZE() {
		return DATASET_SIZE;
	}

	public void setDATASET_SIZE(int dATASET_SIZE) {
		DATASET_SIZE = dATASET_SIZE;
	}

	public String getEXPERIMENT_NAME() {
		return EXPERIMENT_NAME;
	}

	public void setEXPERIMENT_NAME(String eXPERIMENT_NAME) {
		EXPERIMENT_NAME = eXPERIMENT_NAME;
	}

	public String getDATASET_AUTHOR() {
		return DATASET_AUTHOR;
	}

	public void setDATASET_AUTHOR(String dATASET_AUTHOR) {
		DATASET_AUTHOR = dATASET_AUTHOR;
	}

	public boolean isSAVE_RESULT() {
		return SAVE_RESULT;
	}

	public void setSAVE_RESULT(boolean sAVE_RESULT) {
		SAVE_RESULT = sAVE_RESULT;
	}

	public String getTRAINING_DATA() {
		return TRAINING_DATA;
	}

	public void setTRAINING_DATA(String tRAINING_DATA) {
		TRAINING_DATA = tRAINING_DATA;
	}

	public String getTEST_DATA() {
		return TEST_DATA;
	}

	public void setTEST_DATA(String tEST_DATA) {
		TEST_DATA = tEST_DATA;
	}

	public int getNGRAM() {
		return NGRAM;
	}

	public void setNGRAM(int nGRAM) {
		NGRAM = nGRAM;
	}

	public boolean isUSE_MISSING_WORD_PENALTY() {
		return USE_MISSING_WORD_PENALTY;
	}

	public void setUSE_MISSING_WORD_PENALTY(boolean uSE_MISSING_WORD_PENALTY) {
		USE_MISSING_WORD_PENALTY = uSE_MISSING_WORD_PENALTY;
	}

	public double getMISSING_WORD_PENALTY() {
		return MISSING_WORD_PENALTY;
	}

	public void setMISSING_WORD_PENALTY(double mISSING_WORD_PENALTY) {
		MISSING_WORD_PENALTY = mISSING_WORD_PENALTY;
	}

	public boolean isREMOVE_UNWANTED_WORDS() {
		return REMOVE_UNWANTED_WORDS;
	}

	public void setREMOVE_UNWANTED_WORDS(boolean rEMOVE_UNWANTED_WORDS) {
		REMOVE_UNWANTED_WORDS = rEMOVE_UNWANTED_WORDS;
	}

	public String[] getUNWANTED_WORDS() {
		return UNWANTED_WORDS;
	}

	public void setUNWANTED_WORDS(String[] uNWANTED_WORDS) {
		UNWANTED_WORDS = uNWANTED_WORDS;
	}

	public boolean isREPLACE_CHARS() {
		return REPLACE_CHARS;
	}

	public void setREPLACE_CHARS(boolean rEPLACE_CHARS) {
		REPLACE_CHARS = rEPLACE_CHARS;
	}

	public String[][] getREPLACE_CHARS_LIST() {
		return REPLACE_CHARS_LIST;
	}

	public void setREPLACE_CHARS_LIST(String[][] rEPLACE_CHARS_LIST) {
		REPLACE_CHARS_LIST = rEPLACE_CHARS_LIST;
	}

	public boolean isUSE_LAPLACE_SMOOTHING() {
		return USE_LAPLACE_SMOOTHING;
	}

	public void setUSE_LAPLACE_SMOOTHING(boolean uSE_LAPLACE_SMOOTHING) {
		USE_LAPLACE_SMOOTHING = uSE_LAPLACE_SMOOTHING;
	}

	public int[] getLAPLACE_SMOOTHING() {
		return LAPLACE_SMOOTHING;
	}

	public void setLAPLACE_SMOOTHING(int[] lAPLACE_SMOOTHING) {
		LAPLACE_SMOOTHING = lAPLACE_SMOOTHING;
	}

	public boolean isREMOVE_NUMBERS() {
		return REMOVE_NUMBERS;
	}

	public void setREMOVE_NUMBERS(boolean rEMOVE_NUMBERS) {
		REMOVE_NUMBERS = rEMOVE_NUMBERS;
	}

	public boolean isUSE_ZERO_R_BASELINE() {
		return USE_ZERO_R_BASELINE;
	}

	public void setUSE_ZERO_R_BASELINE(boolean uSE_ZERO_R_BASELINE) {
		USE_ZERO_R_BASELINE = uSE_ZERO_R_BASELINE;
	}

	/** ------------------------------
   * Experiment variables
   ------------------------------ */
	
	public int DATASET_SIZE;
	public String EXPERIMENT_NAME;
	public String DATASET_AUTHOR;
	public boolean SAVE_RESULT = true;
	
  /** ------------------------------
   * Data files
   ------------------------------ */
  
  /** 
   * Training data set.
   */
  public String TRAINING_DATA = "";
  //public String TRAINING_DATA = "data/ReutersCorn-train.arff";
  
  /** 
   * Test data set. 
   * If not set, 10-fold cross validation on the training data set will be used.
   */
  public String TEST_DATA = "";
  //public String TEST_DATA = "data/ReutersGrain-test.arff";
  //public String TEST_DATA = "data/ReutersCorn-test.arff";
  
  /** ------------------------------
   * Naive Bayes settings
   ------------------------------ */
  
  /**
   * N-gram factor. If 1, no N-gram is applied.
   */
  public int NGRAM = 1;
  
  /**
   * Sets if a penalty factor shall be applied to the probability
   * if a word is missing for a specific class, but exists for 
   * other classes.
   */
  public boolean USE_MISSING_WORD_PENALTY = false;
  
  /**
   * The missing word penalty to apply (less than 1).
   */
  public double MISSING_WORD_PENALTY = 0.001;
  
  /**
   * If unwanted words, as specified in the array below, shall be removed from the
   * text or not.
   */
  public boolean REMOVE_UNWANTED_WORDS = false;
  
  /**
   * List of unwanted words.
   */
  public String[] UNWANTED_WORDS = {"DELA", "TWEETA", "och", "eller", "det", "i", "att", "på", "är", "av", "för", "med", "till", "den", "har", "om", "ett", "men"};
  
  /**
   * Specifies if special characters shall be replaced or not.
   */
  public boolean REPLACE_CHARS = true;
  
  /**
   * List if what characters to replace with what.
   */
  public String[][] REPLACE_CHARS_LIST = {
    {"\r", " "},
    {".", ""},
    {",", ""},
    {"&lt;", ""},
    {"&#127", " "},
    {";", ""},
    {"\"", ""},
    {":", ""},
    {"(", ""},
    {")", ""}
  };
  
  /**
   * Specifies if Laplace smoothing shall be applied or not.
   */
  public boolean USE_LAPLACE_SMOOTHING = false;
  
  /**
   * Laplace smoothing factor to apply.
   * A common smoothing setting is (1,3).
   * Use (0,0) for no smoothing.
   */
  public int[] LAPLACE_SMOOTHING = {1,3};
  
  /**
   * If true, all numbers (integers or decimal values) will be
   * removed from the word list.
   */
  public boolean REMOVE_NUMBERS = false;
  
  /** ------------------------------
   * Baseline comparison
   ------------------------------ */
  
  /**
   * If true, the system will use the Zero-R classifier instead
   * of Naive Bayes. Zero-R is used as a baseline for comparison.
   */
  public boolean USE_ZERO_R_BASELINE = false;
}
