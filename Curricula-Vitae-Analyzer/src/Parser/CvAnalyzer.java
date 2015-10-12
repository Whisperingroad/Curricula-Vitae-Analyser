package Parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class CvAnalyzer {
	private int numCategories = 4;
	private String[] categories = new String[numCategories];
	private double score;
	private ArrayList<String> language = new ArrayList<String>();
	private ArrayList<String> qualification = new ArrayList<String>();
	private ArrayList<String> experience = new ArrayList<String>();
	private ArrayList<String> nationality = new ArrayList<String>();
	
	private ArrayList<String> CV = new ArrayList<String>();
	
	private void loadCategories(String path){
		int index = 0;
		String rootPath = path + "mainCategories.txt";
		File mainCategoriesPath = new File(rootPath);
		Scanner txtFile;
		try {
			txtFile = new Scanner(mainCategoriesPath);
			while (txtFile.hasNext())
				categories[index++] = txtFile.next();
			txtFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean[] findCategory(String paragraph, String rootPath){
		int index = 0;
		String word = null;
		String categoryPath = null;
		boolean[] categoryPresent = new boolean[numCategories];

		try {
			for (int i = 0; i < numCategories ; i++){
				categoryPath = rootPath + categories[i] + ".txt";
				File categoriesPath = new File(categoryPath);
				Scanner readFile = new Scanner(categoriesPath);
				while (readFile.hasNext()){
					word = readFile.next();
					word = word.toLowerCase();
					if (paragraph.contains(word)){
						categoryPresent[index] = true;
					}
				}
				index++;
				readFile.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return categoryPresent;
	}

	private void matchRequirement(boolean[] categoryPresent, String paragraph){
		String attribute = null;
		for (int category = 0; category < numCategories; category++){
			if (categoryPresent[category]){
				if (category == 0){
					for (int i = 0; i < language.size(); i++){
						attribute = language.get(i);
						attribute = attribute.toLowerCase();
						if (paragraph.contains(attribute))
							score++;
					}
				}
				else if (category == 1){
					for (int i = 0; i < qualification.size(); i++){
						attribute = qualification.get(i);
						attribute = attribute.toLowerCase();
						if (paragraph.contains(attribute))
							score++;
					}
				}
				else if (category == 2){
					for (int i = 0; i < experience.size(); i++){
						attribute = experience.get(i);
						attribute = attribute.toLowerCase();
						if (paragraph.contains(attribute))
							score++;
					}
				}
				else{
					for (int i = 0; i < nationality.size(); i++){
						attribute = nationality.get(i);
						attribute = attribute.toLowerCase();
						if (paragraph.contains(attribute))
							score++;
					}
				}
			}
		}
	}
	
	public void inputCV(ArrayList<String> input){
		CV.addAll(input);
	}

	public double getScore(){
		double size = language.size() + qualification.size() + experience.size() + nationality.size();
		return (score/size);
	}

	public void execute(String path, ArrayList<String> languageInput,ArrayList<String> qualificationInput,ArrayList<String> experienceInput,ArrayList<String> nationalityInput){
		boolean[] categoryPresent = new boolean[numCategories];
		score = 0;
		language.addAll(languageInput);
		qualification.addAll(qualificationInput);
		experience.addAll(experienceInput);
		nationality.addAll(nationalityInput);

		String paragraph = null;
		for (int i = 0; i < CV.size(); i++){
			paragraph = CV.get(i);
			paragraph = paragraph.toLowerCase();
			loadCategories(path);
			categoryPresent = findCategory(paragraph,path);
			matchRequirement(categoryPresent,paragraph);
			System.out.println(score);
		}
	}
}
