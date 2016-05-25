package textclassification;

public class Article {
	private int classification;
	
	public int getClassification() {
		return classification;
	}

	public void setClassification(int classification) {
		this.classification = classification;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private String text;

	public Article(String text, int classification){
		this.text = text;
		this.classification = classification;
	}
}
