import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;


public class matchCV {
	private static int numCategories = 4;
	private static String[] categories = new String[numCategories];
	private static double score;
	private static Vector<String> language = new Vector<String>();
	private static Vector<String> qualification = new Vector<String>();
	private static Vector<String> experience = new Vector<String>();
	private static Vector<String> nationality = new Vector<String>();
	
	private static Vector<String> CV = new Vector<String>();
	
	private static void loadCategories(String path){
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

	private static boolean[] findCategory(String paragraph, String rootPath){
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

	private static void matchRequirement(boolean[] categoryPresent, String paragraph){
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
	
	public static void inputCV(Vector<String> input){
		CV.addAll(input);
	}

	public static double getScore(){
		double size = language.size() + qualification.size() + experience.size() + nationality.size();
		return (score/size);
	}

	public static void execute(String path, Vector<String> languageInput,Vector<String> qualificationInput,Vector<String> experienceInput,Vector<String> nationalityInput){
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
