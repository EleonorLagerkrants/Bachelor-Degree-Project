package textclassification;

public class ParseResultToExcel {
	String excelPath = "";
	
	public static String parseResultToExcel(String fullResult){
		String parsedResult = "";
		parsedResult += "Build model:";
		parsedResult += fullResult.substring(fullResult.indexOf("Time taken to build model:") + 27);
		
		
		return parsedResult;
	}
}
