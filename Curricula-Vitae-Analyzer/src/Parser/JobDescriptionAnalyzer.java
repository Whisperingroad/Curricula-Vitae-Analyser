package Parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import utils.Constants;


public class JobDescriptionAnalyzer {
	private static int numCategories = 4;
	private static String[] categories = new String[numCategories];
	private static ArrayList<String> language = new ArrayList<String>();
	private static ArrayList<String> qualification = new ArrayList<String>();
	private static ArrayList<String> experience = new ArrayList<String>();
	private static ArrayList<String> nationality = new ArrayList<String>();
	
	private static ArrayList<String> jobDescription = new ArrayList<String>();
	
	
	// loading the types of categories
	public void loadCategories(String path){
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
	
	// looking through libraries of all categories
	// comparing with a single line of job description
	public boolean[] findCategory(String paragraph, String rootPath){
		int index = 0;
		String word = null;
		String categoryPath = null;
		// values default to false
		boolean[] categoryPresent = new boolean[numCategories];
		
		try {
			for (int i = 0; i < numCategories ; i++){
				categoryPath = rootPath + categories[i] + Constants.txtPostFix;
				File categoriesPath = new File(categoryPath);
				Scanner readFile = new Scanner(categoriesPath);
				// checking every word in a category
				while (readFile.hasNext())
				{
					word = readFile.next();
					word = word.toLowerCase();
					// category is present
					// attribute list for this category is processed
					if (paragraph.contains(word))
					{
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

	public void addRequirements(boolean[] categoryPresent, String paragraph, String rootPath){
		
		String path = null;
		String attribute = null;
		try {
			for (int i = 0; i < numCategories; i++)
			{
				// process attribute list
				// for categories present
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

	public void addSpecialCase(boolean[] categoryPresent, String paragraph, String rootPath){
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

	public void setJobRequirement(ArrayList<String> input){
		jobDescription.addAll(input);
	}

	public ArrayList<String> getLanguageReq(){
		return language;
	}

	public ArrayList<String> getQualificationReq(){
		return qualification;
	}

	public ArrayList<String> getExperienceReq(){
		return experience;
	}

	public ArrayList<String> getNationalityReq(){
		return nationality;
	}

	public void execute(String path) {
		language.clear();
		qualification.clear();
		experience.clear();
		nationality.clear();
		
		boolean[] categoryPresent = new boolean[numCategories];
		System.out.println("JOB DESCIRPTION:" + jobDescription.size());
		loadCategories(path);
		for (int size = 0; size < jobDescription.size(); size++){
			String paragraph = jobDescription.get(size).toLowerCase();
			System.out.println("JOB DESCIRPTION:" + paragraph);
			categoryPresent = findCategory(paragraph, path);
			addRequirements(categoryPresent,paragraph, path);
			addSpecialCase(categoryPresent,paragraph, path);
		}
		jobDescription.clear();
	}
}

