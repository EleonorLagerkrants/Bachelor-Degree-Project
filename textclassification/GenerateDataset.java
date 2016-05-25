package textclassification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import weka.core.Environment;

public class GenerateDataset {
	
	//private final static String pathToData = "C:\\Users\\Jesper\\Desktop\\New folder\\betygsatta ellie\\";

	private final static String pathToData = "C:\\Users\\Jesper\\Desktop\\examensarbete\\JesperArtiklar\\";
	private static List<Article> articleList = new ArrayList<Article>();
	
	static String path = "F:\\java_kurser\\TextClassification\\data\\JESPER-TAG.arff";
	static int howmany = 200;

	public static void main(String[] args) {
		try {
			File[] articles = new File(pathToData).listFiles();
			
			for(File article : articles){
				Scanner scan = new Scanner(article);
				
				String text = "";
				while(scan.hasNextLine()){
					text += scan.nextLine();
				}

				text.replaceAll(System.lineSeparator(), "");
				//String articletext = text.substring(text.indexOf("<text>") + 6, text.indexOf("</text>"));
				//articletext = articletext.replaceAll("'|\"", "");
				//articletext = articletext.replaceAll("Dela Tweeta", "");
				
				String tags = text.substring(text.indexOf("<tags>") + 6, text.indexOf("</tags>"));
				
				
				tags = tags.replaceAll("<tag>", "");
				tags = tags.replaceAll("</tag>", " ");
				int classification = Integer.parseInt(text.substring(text.indexOf("<grade>") + 7, text.indexOf("</grade>")));
				if(classification == 2)
					classification = 3;
				
				if(tags.split(" ").length > 2)
				{
					System.out.println(tags);
					Article a = new Article(tags, classification);
					articleList.add(a);
				}
				
				scan.close();
			}
			String dataset = "@relation 'Reuters-21578 Corn ModApte Test-weka.filters.unsupervised.attribute.NumericToBinary-weka.filters.unsupervised.instance.RemoveFolds-S0-N5-F1'\n"
									+"@attribute Text string\n"
									+"@attribute class-att {1,3}\n"
									+"@data\n";
			
			int i = 1;
			for(Article a : articleList){
				dataset += "'" + a.getText() + "'," + a.getClassification() + "\n";
				if(i == howmany)
					break;

				i++;
			};
			
			
        	try {
				Files.write( Paths.get(path), dataset.getBytes(), StandardOpenOption.CREATE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

