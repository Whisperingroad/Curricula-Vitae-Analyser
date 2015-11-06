package Parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import utils.Constants;


public class JobDescriptionAnalyzer {
	private int numCategories = 5;
	private String[] categories = new String[numCategories];
	
	private String jobClass = null;
	
	//loading of Dictionary
	protected ArrayList<String> experienceStorage = new ArrayList<String>();
	protected ArrayList<String> experienceListStorage = new ArrayList<String>();
	protected ArrayList<String> languageStorage = new ArrayList<String>();
	protected ArrayList<String> languageListStorage = new ArrayList<String>();
	protected ArrayList<String> mainCategoriesStorage = new ArrayList<String>();
	protected ArrayList<String> modalsStorage = new ArrayList<String>();
	protected ArrayList<String> nationalityStorage = new ArrayList<String>();
	protected ArrayList<String> nationalityListStorage = new ArrayList<String>();
	protected ArrayList<String> qualificationStorage = new ArrayList<String>();
	protected ArrayList<String> qualificationListStorage = new ArrayList<String>();
	protected ArrayList<String> yearStorage = new ArrayList<String>();
	protected ArrayList<String> yearListStorage = new ArrayList<String>();
	
	private ArrayList<String> language = new ArrayList<String>();
	private ArrayList<String> qualification = new ArrayList<String>();
	private ArrayList<String> experience = new ArrayList<String>();
	private ArrayList<String> nationality = new ArrayList<String>();
	private ArrayList<String> reqYearExp = new ArrayList<String>();
	private ArrayList<String> VVVIPList = new ArrayList<String>();
	
	private ArrayList<String> jobDescription = new ArrayList<String>();
		
	//constructor
	public JobDescriptionAnalyzer(ArrayList<String> mainCategoriesInput, ArrayList<String> experienceInput, ArrayList<String> experienceListInput, ArrayList<String> languageInput,
			ArrayList<String> languageListInput, ArrayList<String> nationalityInput, ArrayList<String> nationalityListInput, ArrayList<String> qualificationInput, ArrayList<String> qualificationListInput, 
			ArrayList<String> yearInput, ArrayList<String> yearListInput, ArrayList<String> modalsInput){
		//loading of dictionaries into ArrayList
		mainCategoriesStorage.addAll(mainCategoriesInput);
		experienceStorage.addAll(experienceInput);
		experienceListStorage.addAll(experienceListInput);
		languageStorage.addAll(languageInput);
		languageListStorage.addAll(languageListInput);
		nationalityStorage.addAll(nationalityInput);
		nationalityListStorage.addAll(nationalityListInput);
		qualificationStorage.addAll(qualificationInput);
		qualificationListStorage.addAll(qualificationListInput);
		yearStorage.addAll(yearInput);
		yearListStorage.addAll(yearListInput);
		modalsStorage.addAll(modalsInput);
	}
	
	// loading the types of categories
	public void loadCategories(){
		for (int size = 0; size < mainCategoriesStorage.size(); size++){
			categories[size] = mainCategoriesStorage.get(size);
		}
	}
	
	// looking through libraries of all categories
	// comparing with a single line of job description
	public boolean[] findCategory(String paragraph){
		String word = null;
		// values default to false
		boolean[] categoryPresent = new boolean[numCategories];
		
		for (int i = 0; i < languageStorage.size(); i++){
			word = languageStorage.get(i);
			word = word.toLowerCase();
			word = word.trim();
			if (paragraph.contains(word))
			{
				categoryPresent[0] = true;
			}
		}
		for (int i = 0; i < qualificationStorage.size(); i++){
			word = qualificationStorage.get(i);
			word = word.toLowerCase();
			word = word.trim();
			if (paragraph.contains(word))
			{
				categoryPresent[1] = true;
			}
		}
		for (int i = 0; i < experienceStorage.size(); i++){
			word = experienceStorage.get(i);
			word = word.toLowerCase();
			word = word.trim();
			if (paragraph.contains(word))
			{
				categoryPresent[2] = true;
			}
		}
		for (int i = 0; i < nationalityStorage.size(); i++){
			word = nationalityStorage.get(i);
			word = word.toLowerCase();
			word = word.trim();
			if (paragraph.contains(word))
			{
				categoryPresent[3] = true;
			}
		}
		for (int i = 0; i < yearStorage.size(); i++){
			word = yearStorage.get(i);
			word = word.toLowerCase();
			word = word.trim();
			if (paragraph.contains(word))
			{
				categoryPresent[4] = true;
			}
		}
		return categoryPresent;
	}
	
	//check whether sentence contains modals
	boolean checkModal(String paragraph){
		for (int i = 0; i < modalsStorage.size(); i++){
			if (paragraph.contains(modalsStorage.get(i)))
				return true;
		}
		return false;
	}

	public void addRequirements(boolean[] categoryPresent, String paragraph, boolean impt){

		String attribute = null;
		if (categoryPresent[0]){
			for (int i = 0; i < languageListStorage.size(); i++){
				attribute = languageListStorage.get(i);
				attribute = (attribute.toLowerCase()).trim();
				
				if (attribute.length() < 3){
					ArrayList<String> words = new ArrayList<String>(Arrays.asList(paragraph.split("\\p{Punct}| ")));
					for (String word : words)
					{
						if (word.length() < 3){
							if (word.equals(attribute)){
								language.add(attribute);
								if (impt == true){
									VVVIPList.add(attribute);
								}
							}
						}
					}
				}
				else {
					if (paragraph.contains(attribute)){
						if (!language.contains(attribute)){
							language.add(attribute);
							if (impt == true){
								VVVIPList.add(attribute);
							}
						}
					}
				}
			}
		}
		if (categoryPresent[1]){
			for (int i = 0; i < qualificationListStorage.size(); i++){
				attribute = qualificationListStorage.get(i);
				attribute = (attribute.toLowerCase()).trim();
				
				if (attribute.length() < 3){
					ArrayList<String> words = new ArrayList<String>(Arrays.asList(paragraph.split("\\p{Punct}| ")));
					for (String word : words)
					{
						if (word.length() < 3){
							if (word.equals(attribute)){
								qualification.add(attribute);
								if (impt == true){
									VVVIPList.add(attribute);
								}
							}
						}
					}
				}
				else {
					if (paragraph.contains(attribute)){
						if (!qualification.contains(attribute)){
							qualification.add(attribute);
							if (impt == true){
								VVVIPList.add(attribute);
							}
						}
					}
				}
			}
		}
		if (categoryPresent[2]){
			for (int i = 0; i < experienceListStorage.size(); i++){
				attribute = experienceListStorage.get(i);
				attribute = (attribute.toLowerCase()).trim();
				
				if (attribute.length() < 3){
					ArrayList<String> words = new ArrayList<String>(Arrays.asList(paragraph.split("\\p{Punct}| ")));
					for (String word : words)
					{
						if (word.length() < 3){
							System.out.println("enter 1");
							if (word.equals(attribute)){
								System.out.println("enetere 2");
								experience.add(attribute);
								if (impt == true){
									VVVIPList.add(attribute);
								}
							}
						}
					}
				}
				else {
					if (paragraph.contains(attribute)){
						if (!experience.contains(attribute)){
							experience.add(attribute);
							if (impt == true){
								VVVIPList.add(attribute);
							}
						}
					}
				}
			}
		}
		if (categoryPresent[3]){
			for (int i = 0; i < nationalityListStorage.size(); i++){
				attribute = nationalityListStorage.get(i);
				attribute = (attribute.toLowerCase()).trim();
				
				if (attribute.length() < 3){
					ArrayList<String> words = new ArrayList<String>(Arrays.asList(paragraph.split("\\p{Punct}| ")));
					for (String word : words)
					{
						if (word.length() < 3){
							if (word.equals(attribute)){
								nationality.add(attribute);
								if (impt == true){
									VVVIPList.add(attribute);
								}
							}
						}
					}
				}
				else {
					if (paragraph.contains(attribute)){
						if (!nationality.contains(attribute)){
							nationality.add(attribute);
							if (impt == true){
								VVVIPList.add(attribute);
							}
						}
					}
				}
			}
		}
	}

	//adding the number of years of experience in the field required
	public void addSpecialCase(boolean[] categoryPresent, String paragraph, boolean impt){
		//experience with years required
		int yearExp = 4;
		if (categoryPresent[yearExp]){
			String numYear = findYearReq(paragraph);
			reqYearExp.add(numYear);
			if (paragraph.contains("relevant"))
				reqYearExp.add(jobClass);
			else{
				String attribute = null;
				for (int i = 0; i < yearListStorage.size(); i++){
					attribute = yearListStorage.get(i).trim().toLowerCase();
					if (paragraph.contains(attribute))
						reqYearExp.add(attribute);
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

	//finding number 0~9 in the sentence
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
		loadCategories();
		for (int size = 1; size < jobDescription.size(); size++){
			String paragraph = jobDescription.get(size).toLowerCase();
			System.out.println("JOB DESCIRPTION:" + paragraph);
			categoryPresent = findCategory(paragraph);
			if (checkModal(paragraph)){
				impt = true;
				addRequirements(categoryPresent, paragraph, impt);
			}
			else{
				impt = false;
				addRequirements(categoryPresent, paragraph, impt);
				addSpecialCase(categoryPresent, paragraph, impt);
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

