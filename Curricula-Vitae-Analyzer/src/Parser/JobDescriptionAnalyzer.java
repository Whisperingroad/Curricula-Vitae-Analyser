package Parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import utils.Constants;


public class JobDescriptionAnalyzer {
	private int numCategories = 5;
	private String[] categories = new String[numCategories];
	
	private String jobClass = null;
	
	private ArrayList<String> language = new ArrayList<String>();
	private ArrayList<String> qualification = new ArrayList<String>();
	private ArrayList<String> experience = new ArrayList<String>();
	private ArrayList<String> nationality = new ArrayList<String>();
	private ArrayList<String> reqYearExp = new ArrayList<String>();
	private ArrayList<String> VVVIPList = new ArrayList<String>();
	
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
	
	boolean checkModal(String paragraph, String rootPath){
		String path = rootPath + "modals.txt";
		String modal = null;
		try {
			File listPath = new File(path);
			Scanner readFile = new Scanner(listPath);
			while (readFile.hasNextLine()){
				modal = readFile.nextLine();
				if (paragraph.contains(modal)){
					readFile.close();
					return true;
				}
			}
			readFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void addRequirements(boolean[] categoryPresent, String paragraph, String rootPath, boolean impt){
		
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
							if (i == 0 && !language.contains(attribute)){
								language.add(attribute);
								if (impt == true){
									VVVIPList.add(attribute);
								}
							}
							else if (i == 1 && !qualification.contains(attribute)){
								qualification.add(attribute);
								if (impt == true){
									VVVIPList.add(attribute);
								}
							}
							else if (i == 2 && !experience.contains(attribute)){
								experience.add(attribute);
								if (impt == true){
									VVVIPList.add(attribute);
								}
							}
							else if (i == 3 && !nationality.contains(attribute)){
								nationality.add(attribute);
								if (impt == true){
									VVVIPList.add(attribute);
								}
							}
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


	public void addSpecialCase(boolean[] categoryPresent, String paragraph, String rootPath, boolean impt){
		//experience with years required
		int yearExp = 4;
		if (categoryPresent[yearExp]){
			String numYear = findYearReq(paragraph);
			reqYearExp.add(numYear);
			if (paragraph.contains("relevant"))
				reqYearExp.add(jobClass);
			else{
				String attribute = null;
				String path = rootPath + "yearList.txt";
				try {
					File listPath = new File(path);
					Scanner readFile = new Scanner(listPath);
					while (readFile.hasNextLine()){
						attribute = readFile.nextLine().trim().toLowerCase();
						System.out.println(attribute);
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
		VVVIPList.clear();
	}

	private String findYearReq(String input){
		input = input.replaceAll("[^0-9]+", " ").trim();
		return (input.substring(0, 1));
	}

	private void checkList(){
		System.out.println("language");
		for (int i=0;i<language.size();i++){
			System.out.println(language.get(i));
		}
		System.out.println("qualification");
		for (int i=0;i<qualification.size();i++){
			System.out.println(qualification.get(i));
		}
		System.out.println("experience");
		for (int i=0;i<experience.size();i++){
			System.out.println(experience.get(i));
		}
		System.out.println("nationality");
		for (int i=0;i<nationality.size();i++){
			System.out.println(nationality.get(i));
		}
		System.out.println("reqYearExp");
		for (int i=0;i<reqYearExp.size();i++){
			System.out.println(reqYearExp.get(i));
		}
		System.out.println("VVVIPList");
		for (int i=0;i<VVVIPList.size();i++){
			System.out.println(VVVIPList.get(i));
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
	
	public ArrayList<String> getreqYearExp(){
		return reqYearExp;
	}
	
	public ArrayList<String> getVVVIPList(){
		return VVVIPList;
	}

	public void execute(String path) {
		clearAllArrayList();
		boolean impt = false;
		boolean[] categoryPresent = new boolean[numCategories];
		System.out.println("JOB DESCIRPTION:" + jobDescription.size());
		jobClass = jobDescription.get(0);
		loadCategories(path);
		for (int size = 1; size < jobDescription.size(); size++){
			String paragraph = jobDescription.get(size).toLowerCase();
			System.out.println("JOB DESCIRPTION:" + paragraph);
			categoryPresent = findCategory(paragraph, path);
			if (checkModal(paragraph, path)){
				impt = true;
				addRequirements(categoryPresent, paragraph, path, impt);
			}
			else{
				impt = false;
				addRequirements(categoryPresent, paragraph, path, impt);
				addSpecialCase(categoryPresent, paragraph, path, impt);
			}
		}
		
		jobDescription.clear();
	}
	/*
	public static void main(String[] args){
		String path = "C:/Users/AdminNUS/Documents/git/Curricula-Vitae-Analyser/Curricula-Vitae-Analyzer/src/Library/";
		JobDescriptionAnalyzer test = new JobDescriptionAnalyzer();
		
		ArrayList<String> paragraphs = new ArrayList<String>();
		paragraphs.add("accountant");
		paragraphs.add("have 3 year of relevant experience");
		paragraphs.add("2 years in IT experience");
		paragraphs.add("must be proficient in c++");
		paragraphs.add("must be proficient in ms-office");
		test.setJobRequirement(paragraphs);
		test.execute(path);
		test.checkList();
	}*/
}

