package Parser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;


public class CvAnalyzer {

	//private double score;
	private ArrayList<String> language = new ArrayList<String>();
	private ArrayList<String> qualification = new ArrayList<String>();
	private ArrayList<String> experience = new ArrayList<String>();
	private ArrayList<String> nationality = new ArrayList<String>();
	private ArrayList<String> importantRequirements = new ArrayList<String>();
 	private ArrayList<String> CV = new ArrayList<String>();
	
	private ArrayList<String> qualificationsFulfilled = new ArrayList<String>();
	private ArrayList<String> experienceFulfilled = new ArrayList<String>();
	private ArrayList<String> languageFulfilled = new ArrayList<String>();
	private ArrayList<String> particularsFulfilled = new ArrayList<String>();
	private ArrayList<String> importantRequirementsFulfilled = new ArrayList<String>();
	
	private double HIGHSCORE = 9;
	private double SCORE = 1;
	
	
	// for headers
	private ArrayList<String> qualificationHeaders = new ArrayList<String>();
	private ArrayList<String> experienceHeaders = new ArrayList<String>();
	private ArrayList<String> languageHeaders = new ArrayList<String>();
	private ArrayList<String> particularsHeaders = new ArrayList<String>();
	
	public enum HeaderTypes {QUALIFICATION, EXPERIENCE, LANGUAGE, PARTICULARS, INVALID};
	
	private double computeTotalAttribute()
	{
		return (language.size() + qualification.size() + experience.size() + nationality.size());
	}
	
	private double computeScore()
	{
		double totalAttributes = computeTotalAttribute();
		double totalRequirementsFulfilled = 0;
		double totalImportantRequirementsFulfilled = 0;
		double totalScore = 0;
		double candidateScore = 0;
		
		// check for validity
		if (totalAttributes != 0)
		{
			totalImportantRequirementsFulfilled = importantRequirementsFulfilled.size();
			totalRequirementsFulfilled = languageFulfilled.size() + qualificationsFulfilled.size() + experienceFulfilled.size() + particularsFulfilled.size();
			totalScore = importantRequirements.size()*HIGHSCORE + totalAttributes*SCORE;
			candidateScore = ((totalImportantRequirementsFulfilled*HIGHSCORE + totalRequirementsFulfilled*SCORE)/totalScore)*100;
		}
		return candidateScore;
	}
	
	private boolean checkAvail(){
		if (computeTotalAttribute() == 0)
			return false;
		else 
			return true;
	}
	
	public void inputCV(ArrayList<String> input){
		CV.clear();
		CV.addAll(input);
	}
	
	public ArrayList<String> getQualification(){
		return qualificationsFulfilled;
	}
	
	public ArrayList<String> getExperience(){
		return experienceFulfilled;
	}
	
	
	public ArrayList<String> getLanguage(){
		return languageFulfilled;
	}
	
	
	public ArrayList<String> getParticulars(){
		return particularsFulfilled;
	}
	
	public ArrayList<String> getImportantRequirements(){
		return importantRequirementsFulfilled;
	}
	
	
	public void clearLists(){
		language.clear();
		qualification.clear();
		experience.clear();
		nationality.clear();
		importantRequirements.clear();
		languageFulfilled.clear();
		qualificationsFulfilled.clear();
		experienceFulfilled.clear();
		particularsFulfilled.clear();
		importantRequirementsFulfilled.clear();
	}
	
	public void addLists( ArrayList<String> languageInput,ArrayList<String> qualificationInput,ArrayList<String> experienceInput,ArrayList<String> nationalityInput, ArrayList<String> importantRequirementsInput){
		language.addAll(languageInput);
		qualification.addAll(qualificationInput);
		experience.addAll(experienceInput);
		nationality.addAll(nationalityInput);
		importantRequirements.addAll(importantRequirementsInput);
	}

	public double execute(String path, ArrayList<String> languageInput,ArrayList<String> qualificationInput,
			ArrayList<String> experienceInput,ArrayList<String> nationalityInput, 
			ArrayList<String> importantRequirementsInput) throws IOException, FileNotFoundException
	{
		clearLists();
		loadAllHeaderTypes(path);
		addLists(languageInput,qualificationInput, experienceInput, nationalityInput, importantRequirementsInput);

		if (checkAvail() == true){
			//score = 0;
			String lineInCV = null;
			
			// loop through the entire CV for the first time to look
			// for important requirements
			
			for (int i = 0; i < CV.size(); i++ )
			{	
				lineInCV = CV.get(i).toLowerCase().trim();
				matchRequirements(importantRequirements, importantRequirementsFulfilled, lineInCV);
			}
			// initialize header to be invalid
			HeaderTypes header = HeaderTypes.INVALID;
			// loop through the entire CV for the second time to look
			// for other requirements
			for (int i = 0; i < CV.size(); i++)
			{
				lineInCV = CV.get(i).toLowerCase().trim();
				header = checkForHeader(lineInCV, header);
				if (header == HeaderTypes.QUALIFICATION)
				{
					//matchQualificationDetails(paragraph);
					matchRequirements(qualification, qualificationsFulfilled, lineInCV);
				}
				else if (header == HeaderTypes.EXPERIENCE)
				{
					//matchExperienceDetails(paragraph);
					matchRequirements(experience, experienceFulfilled, lineInCV);
				}
				else if (header == HeaderTypes.LANGUAGE)
				{
					matchRequirements(language, languageFulfilled, lineInCV);
					//matchLanguageDetails(paragraph);
				}
				else if (header == HeaderTypes.PARTICULARS)
				{
					matchRequirements(nationality, particularsFulfilled, lineInCV);
					//matchParticularDetails(paragraph);
				}
			}			
			double score = computeScore();
			return score;
		}
		else
			return 0.0;
	}
	
	public void matchRequirements(ArrayList<String> requirements, ArrayList<String> requirementsFulfilled, String line)
	{
		for (int i = requirements.size()-1; i >= 0; i--)
		{
			String attribute = requirements.get(i);
			attribute = (attribute.toLowerCase()).trim();
			// short attribute
			// would be safer to get an exact match
			if (attribute.length() < 4)
			{
				System.out.println(line);
				ArrayList<String> words = new ArrayList<String>(Arrays.asList(line.split("(?!\\+)\\p{Punct}| ")));			
				for (String word : words)
				{	
					System.out.println("check how does split sentences look like " + word);
					
					
					// attribute is found in sentence
					if (attribute.equals(word.trim()))
					{
						// check if this attribute has been fulfilled
						boolean isFulfilled = false;
						for (String requirement : requirementsFulfilled)
						{
							if (requirement.equals(attribute))
								isFulfilled = true;
						}
						if (isFulfilled == false)
						{
							requirementsFulfilled.add(attribute);
							System.out.println("4 word add score " + line);
						}
					}
				}
			}
			else if (line.contains(attribute) && !requirementsFulfilled.contains(attribute))
			{
				requirementsFulfilled.add(attribute);
				System.out.println("normal add score" + line);
			}
		}
	}
	
	public void matchQualificationDetails(String line)
	{
		for (int i = qualification.size()-1; i >= 0; i--)
		{
			String attribute = qualification.get(i);
			attribute = (attribute.toLowerCase()).trim();
			if (line.contains(attribute) && !qualificationsFulfilled.contains(attribute))
			{
				qualificationsFulfilled.add(attribute);
			}
		}
	}
		
	// load all types predefined headers
	public void loadAllHeaderTypes(String path) throws FileNotFoundException, IOException
	{	
		String educationPath = path + "qualificationHeaders.txt";
		String experiencePath = path + "experienceHeaders.txt";
		String languagePath = path + "languageHeaders.txt";
		String particularsPath = path + "particularsHeaders.txt";
		
		loadHeaders(educationPath, qualificationHeaders);
		loadHeaders(experiencePath, experienceHeaders);
		loadHeaders(languagePath, languageHeaders);
		loadHeaders(particularsPath, particularsHeaders);
		
	}
	
	// load headers of a particular type
	public void loadHeaders(String headerFilePath, ArrayList<String> headerType) throws FileNotFoundException, IOException
	{
		FileReader fileReader = new FileReader(headerFilePath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = "";
		// repeat until all headers are read
		while ((line = bufferedReader.readLine())!= null)
		{
			headerType.add(line);
		}
	
		bufferedReader.close();
	}
	
	
	public HeaderTypes checkForHeader(String line, HeaderTypes header)
	{
		// first and second check
		if (checkWordLimit(line) && checkForSymbols(line))
		{
			// passed first and second check
			if(checkForDefinedHeaders(line,qualificationHeaders)== true)
			{
				System.out.println("qualification header is " + line);
				return HeaderTypes.QUALIFICATION;
			}
			
			else if (checkForDefinedHeaders(line,experienceHeaders)== true)
			{
				System.out.println("experience header is " + line);
				return HeaderTypes.EXPERIENCE;
			}
			else if (checkForDefinedHeaders(line,languageHeaders)== true)
			{
				System.out.println("language header is " + line);
				return HeaderTypes.LANGUAGE;
			}
			
			else if (checkForDefinedHeaders(line,particularsHeaders)== true)
			{
				System.out.println("particulars header is " +line);
				return HeaderTypes.PARTICULARS;
			}
			else 
				return header;				
		}
	return header;
	}
	
	// header check 1
	// check if the sentence has less than 4 words
	public boolean checkWordLimit(String line)
	{
		
		ArrayList<String> words = new ArrayList<String>(Arrays.asList(line.split("(?!\\:)\\p{Punct}| ")));
		if (words.size() > 4)
			return false;
		else
		{
			return true;
		}
	}
	
	// header check 2
	// check if punctuation can be found in line
	// headers should not contain commas and full stops
	public boolean checkForSymbols(String line)
	{
		if (line.contains(",|."))
			return false;
		else
			return true;
	}
	
	// header check 3
	// check if sentence contains defined headers
	public boolean checkForDefinedHeaders(String line, ArrayList<String> headerType)
	{
		String checkHeader = line.toLowerCase().trim();

		for (String header : headerType)
		{
			if (checkHeader.contains(header.trim().toLowerCase()))
			{
				return true;
			}
		}
		return false;
	}
	
}
