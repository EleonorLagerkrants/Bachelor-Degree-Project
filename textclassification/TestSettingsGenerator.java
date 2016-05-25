package textclassification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestSettingsGenerator {

	public static void main(String[] args) {
		List<String> datasets = new ArrayList<String>();
		datasets.addAll(Arrays.asList(
				"data\\JESPER-50-TEXT.arff", 
				"data\\JESPER-100-TEXT.arff",
				"data\\JESPER-150-TEXT.arff",
				"data\\JESPER-200-TEXT.arff",
				"data\\ELEONOR-50-TEXT.arff", 
				"data\\ELEONOR-100-TEXT.arff",
				"data\\ELEONOR-150-TEXT.arff",
				"data\\ELEONOR-200-TEXT.arff"));
		
		for(String dataset : datasets){
			Settings settings = new Settings();
			String experimentName = "BASELINE";
			settings.setEXPERIMENT_NAME(experimentName);
			System.out.println(dataset);
			settings.setTRAINING_DATA(dataset);
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
		}
		
		for (int ngram = 0; ngram < 2; ngram++) { 								//NGRAM: 1 OR 2
			for (int missingword = 0; missingword < 2; missingword++) {			//MISSING WORD PENALTY: ON OR OFF
				for (int removeword = 0; removeword < 2; removeword++) {		//REMOVE UNWANTED WORDS: ON OR OFF
					for (int laplace = 0; laplace < 2; laplace++) {				//USE LAPLACE SMOOTHING: ON OR OFF
						for(String dataset : datasets){
							Settings settings = new Settings();
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
							
							settings.setEXPERIMENT_NAME(experimentName);
							settings.setTRAINING_DATA(dataset);
							if(dataset.contains("JESPER"))
								settings.setDATASET_AUTHOR("JESPER");
							else
								settings.setDATASET_AUTHOR("ELEONOR");
							System.out.println("C:\\Users\\Jesper\\Desktop\\examensarbete\\resultat\\" + settings.DATASET_AUTHOR + "-" + settings.EXPERIMENT_NAME + settings.DATASET_SIZE + ".txt");
						}
					}
				}
			}
		}
		
	}

}
