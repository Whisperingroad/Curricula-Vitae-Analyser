package Parser;
import java.util.ArrayList;
import java.util.Arrays;

public class JobDescriptionAnalyzer {
	private int numCategories 		= 5;
	private int languageNum 		= 0;
	private int qualificationNum 	= 1;
	private int experienceNum 		= 2;
	private int nationalityNum 		= 3;
	
	private String[] categories = new String[numCategories];
	
	private String jobClass = null;
	
	//loading of Dictionary
	protected ArrayList<String> experienceStorage 			= new ArrayList<String>();
	protected ArrayList<String> experienceListStorage 		= new ArrayList<String>();
	protected ArrayList<String> languageStorage 			= new ArrayList<String>();
	protected ArrayList<String> languageListStorage 		= new ArrayList<String>();
	protected ArrayList<String> mainCategoriesStorage 		= new ArrayList<String>();
	protected ArrayList<String> modalsStorage 				= new ArrayList<String>();
	protected ArrayList<String> nationalityStorage 			= new ArrayList<String>();
	protected ArrayList<String> nationalityListStorage 		= new ArrayList<String>();
	protected ArrayList<String> qualificationStorage 		= new ArrayList<String>();
	protected ArrayList<String> qualificationListStorage	= new ArrayList<String>();
	protected ArrayList<String> yearStorage 				= new ArrayList<String>();
	protected ArrayList<String> yearListStorage 			= new ArrayList<String>();
	
	//Attributes required
	protected ArrayList<String> language 					= new ArrayList<String>();
	protected ArrayList<String> qualification 				= new ArrayList<String>();
	protected ArrayList<String> experience 					= new ArrayList<String>();
	protected ArrayList<String> nationality 				= new ArrayList<String>();
	protected ArrayList<String> reqYearExp 					= new ArrayList<String>();
	protected ArrayList<String> VVVIPList 					= new ArrayList<String>();
	
	protected ArrayList<String> jobDescription 				= new ArrayList<String>();
		
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
		// values default to false
		boolean[] categoryPresent = new boolean[numCategories];
		if (findLanguageVerb(paragraph))
			categoryPresent[languageNum] = true;
		if (findQualificationVerb(paragraph))
			categoryPresent[qualificationNum] = true;
		if (findExperienceVerb(paragraph))
			categoryPresent[experienceNum] = true;
		if (findNationalityVerb(paragraph))
			categoryPresent[nationalityNum] = true;
		return categoryPresent;
	}

	public void addRequirements(boolean[] categoryPresent, String paragraph, boolean impt){
		if (categoryPresent[languageNum])
			findAddAttribute(paragraph, languageListStorage, languageNum, impt);
		if (categoryPresent[qualificationNum])
			findAddAttribute(paragraph, qualificationListStorage, qualificationNum, impt);
		if (categoryPresent[experienceNum])
			findAddAttribute(paragraph, experienceListStorage, experienceNum, impt);
		if (categoryPresent[nationalityNum])
			findAddAttribute(paragraph, nationalityListStorage, nationalityNum, impt);
	}
		

	private void findAddAttribute(String line, ArrayList<String> verbs, int category, boolean impt){
		String attribute = null;
		for (int size = 0; size < verbs.size(); size++){
			attribute = verbs.get(size).toLowerCase().trim();
			if (checkWordLength(attribute)){
				ArrayList<String> words = parseSentence(line);
				if (compareWord(words, attribute)){
					if (category == languageNum)
						language.add(attribute);
					else if (category == qualificationNum)
						qualification.add(attribute);
					else if (category == experienceNum)
						experience.add(attribute);
					else if (category == nationalityNum)
						nationality.add(attribute);
					if (impt)
						VVVIPList.add(attribute);
				}
			}
			else{
				if (line.contains(attribute)){
					if (category == languageNum){
						if (!language.contains(attribute)){
							language.add(attribute);
						}
					}
					if (category == qualificationNum){
						if (!qualification.contains(attribute)){
							qualification.add(attribute);
						}
					}
					else if (category == experienceNum){
						if (!experience.contains(attribute)){
							experience.add(attribute);
						}
					}
					if (category == nationalityNum){
						if (!nationality.contains(attribute)){
							nationality.add(attribute);
						}
					}
					if (impt)
						VVVIPList.add(attribute);
				}
			}
		}
	}

	private boolean compareWord(ArrayList<String> words, String attribute){
		for (int size = 0; size < words.size(); size++){
			if (words.get(size).equals(attribute))
				return true;
		}
		return false;
	}

	private boolean checkWordLength(String word){
		if (word.length() < 3)
			return true;
		else 
			return false;
	}

	private ArrayList<String> parseSentence(String line){
		return new ArrayList<String>(Arrays.asList(line.split("(?!\\+)(?!#)\\p{Punct}| ")));
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
	
	private boolean findLanguageVerb(String line){
		String verb = null;
		for (int size = 0; size < languageStorage.size(); size++){
			verb = languageStorage.get(size).trim().toLowerCase();
			if (line.contains(verb))
				return true;
		}
		return false;
	}
	
	private boolean findQualificationVerb(String line){
		String verb = null;
		for (int size = 0; size < qualificationStorage.size(); size++){
			verb = qualificationStorage.get(size).trim().toLowerCase();
			if (line.contains(verb))
				return true;
		}
		return false;
	}
	
	private boolean findExperienceVerb(String line){
		String verb = null;
		for (int size = 0; size < experienceStorage.size(); size++){
			verb = experienceStorage.get(size).trim().toLowerCase();
			if (line.contains(verb))
				return true;
		}
		return false;
	}
	
	private boolean findNationalityVerb(String line){
		String verb = null;
		for (int size = 0; size < nationalityStorage.size(); size++){
			verb = nationalityStorage.get(size).trim().toLowerCase();
			if (line.contains(verb))
				return true;
		}
		return false;
	}
	
	//check whether sentence contains modals
		boolean findModal(String line){
			for (int i = 0; i < modalsStorage.size(); i++){
				if (line.contains(modalsStorage.get(i)))
					return true;
			}
			return false;
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
			if (findModal(paragraph)){
				impt = true;
				addRequirements(categoryPresent, paragraph, impt);
			}
			else{
				impt = false;
				addRequirements(categoryPresent, paragraph, impt);
				addSpecialCase(categoryPresent, paragraph, impt);
			}
		}
		System.out.println("jd check");
		checkList();
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

