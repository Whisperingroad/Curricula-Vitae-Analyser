package Parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import utils.Constants;


public class JobDescriptionAnalyzer {
	private int numCategories = 5;
	private String[] categories = new String[numCategories];
	private ArrayList<String> language = new ArrayList<String>();
	private ArrayList<String> qualification = new ArrayList<String>();
	private ArrayList<String> experience = new ArrayList<String>();
	private ArrayList<String> nationality = new ArrayList<String>();
	private ArrayList<String> reqYearExp = new ArrayList<String>();
	
	private ArrayList<String> jobDescription = new ArrayList<String>();
	
	
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
				while (readFile.hasNextLine())
				{
					word = readFile.nextLine();
					word = word.toLowerCase();
					word = word.trim();
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
						attribute = (attribute.toLowerCase()).trim();
						if (paragraph.contains(attribute)){
							if (i == 0 && !language.contains(attribute))
								language.add(attribute);
							else if (i == 1 && !qualification.contains(attribute))
								qualification.add(attribute);
							else if (i == 2 && !experience.contains(attribute))
								experience.add(attribute);
							else if (i == 3 && !nationality.contains(attribute))
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
		//experience with years required
		int yearExp = 4;
		if (categoryPresent[yearExp]){
			String numYear = findYearReq(paragraph);
			reqYearExp.add(numYear);
			if (paragraph.contains("relevant"))
				reqYearExp.add("relevant");
			else{
				String attribute = null;
				String path = rootPath + "yearList.txt";
				try {
					File listPath = new File(path);
					Scanner readFile = new Scanner(listPath);
					while (readFile.hasNextLine()){
						attribute = readFile.nextLine().trim();
						if (paragraph.contains(attribute))
							reqYearExp.add(attribute);
					}
					readFile.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void clearAllArrayList(){
		language.clear();
		qualification.clear();
		experience.clear();
		nationality.clear();
		reqYearExp.clear();
	}
	
	private String findYearReq(String input){
		input = input.replaceAll("[^0-9]+", " ").trim();
		return (input.substring(0, 1));
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
		clearAllArrayList();
		
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
	
	public static void main(String[] args){
		String path = "C:/Users/AdminNUS/Documents/git/Curricula-Vitae-Analyser/Curricula-Vitae-Analyzer/src/Library/";
		JobDescriptionAnalyzer test = new JobDescriptionAnalyzer();
		test.loadCategories("C:/Users/AdminNUS/Documents/git/Curricula-Vitae-Analyser/Curricula-Vitae-Analyzer/src/Library/");
		//limitations - only can read in numeric values
		String paragraph = "minimum of 3 year experience in the relevant field";
		boolean[] categoryPresent = new boolean[test.numCategories];
		categoryPresent = test.findCategory(paragraph, "C:/Users/AdminNUS/Documents/git/Curricula-Vitae-Analyser/Curricula-Vitae-Analyzer/src/Library/");
		for (int i=0;i<5;i++)
			System.out.println(categoryPresent[i]);
		test.addSpecialCase(categoryPresent,paragraph,path);
		for (int i=0;i<test.reqYearExp.size();i++)
			System.out.println(test.reqYearExp.get(i));
		//do something
		//	int year = reqYear();
		//	String exp = reqExp();
	}
}

