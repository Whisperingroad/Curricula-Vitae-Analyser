import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;


public class readJobDescription {
	private static int numCategories = 4;
	private static String[] categories = new String[numCategories];
	private static Vector<String> language = new Vector<String>();
	private static Vector<String> qualification = new Vector<String>();
	private static Vector<String> experience = new Vector<String>();
	private static Vector<String> nationality = new Vector<String>();
	
	private static Vector<String> jobDescription = new Vector<String>();
	
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

	private static void addRequirements(boolean[] categoryPresent, String paragraph, String rootPath){
		
		String path = null;
		String attribute = null;
		try {
			for (int i = 0; i < numCategories; i++){
				if (categoryPresent[i]){
					path = rootPath + categories[i] + "List.txt";
					System.out.println(path);
					File listPath = new File(path);
					Scanner readFile = new Scanner(listPath);
					while (readFile.hasNextLine()){
						attribute = readFile.nextLine();
						attribute = attribute.toLowerCase();
						if (paragraph.contains(attribute)){
							if (i == 0)
								language.add(attribute);
							else if (i == 1)
								qualification.add(attribute);
							else if (i == 2)
								experience.add(attribute);
							else
								nationality.add(attribute);
						}
					}
					readFile.close();
				}
			} 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void addSpecialCase(boolean[] categoryPresent, String paragraph, String rootPath){
		String attribute = null;
		String path = rootPath + "qualification.txt";
		File listPath = new File(path);
		try {
			Scanner readFile = new Scanner(listPath);
			if (categoryPresent[1] && !paragraph.contains("in")){
				while (readFile.hasNextLine()){
					attribute = readFile.nextLine();
					if (paragraph.contains(attribute))
						qualification.add(attribute);
				}
			}
			readFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setJobRequirement(Vector<String> input){
		jobDescription.addAll(input);
	}

	public static Vector<String> getLanguageReq(){
		return language;
	}

	public static Vector<String> getQualificationReq(){
		return qualification;
	}

	public static Vector<String> getExperienceReq(){
		return experience;
	}

	public static Vector<String> getNationalityReq(){
		return nationality;
	}

	public static void execute(String path) {
		boolean[] categoryPresent = new boolean[numCategories];

		loadCategories(path);
		for (int size = 0; size < jobDescription.size(); size++){
			String paragraph = jobDescription.get(size);
			System.out.println(paragraph);
			categoryPresent = findCategory(paragraph, path);
			addRequirements(categoryPresent,paragraph, path);
			addSpecialCase(categoryPresent,paragraph, path);
		}
	}
}

