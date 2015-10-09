package Parser;

import java.util.Scanner;
import java.util.Vector;
import utils.Constants;
import extractor.PDFTextParser;
import storage.Storage;


public class Rule_Based {
	
	private static Vector<String> language = new Vector<String>();
	private static Vector<String> qualification = new Vector<String>();
	private static Vector<String> experience = new Vector<String>();
	private static Vector<String> nationality = new Vector<String>();
	
	public static void main(String[] args){

		// change this line for file path
		PDFTextParser pdfTextParserObj = new PDFTextParser();
		String extractedData = "";
		Scanner scanner = new Scanner(System.in);
		String inputFile = scanner.nextLine();
		String outputFile = scanner.nextLine();
		extractedData = pdfTextParserObj.pdftoText(inputFile);
		scanner.close();
		Storage storage = new Storage();
		storage.writeData(extractedData, outputFile);
	
		String path =  Constants.YIXIU + "/Library/";
		
		//adding of job description
		Vector<String> paragraph = new Vector<String>();
		paragraph.add( "Able to speak bilingual language as need to liaise with mandarin speaking students");
		paragraph.add("Possess good interpersonal, people management and organization skills");
		paragraph.add("proficient in Oracle Financials and ms-excel");
		paragraph.add("proficient in microsoft office");
	//	paragraph.add("Bachelors degree or equivalent preferred");
		paragraph.add("Graduated with a Bachelor's degree in it security. computer science or computer engineering");
		readJobDescription.setJobRequirement(paragraph);
		readJobDescription.execute(path);
		
		language = readJobDescription.getLanguageReq();
		qualification = readJobDescription.getQualificationReq();
		experience = readJobDescription.getExperienceReq();
		nationality = readJobDescription.getNationalityReq();
		
		System.out.println("language");
		for (int i=0;i<language.size();i++)
			System.out.println(language.get(i));
		System.out.println("qualification");
		for (int i=0;i<qualification.size();i++)
			System.out.println(qualification.get(i));
		System.out.println("experience");
		for (int i=0;i<experience.size();i++)
			System.out.println(experience.get(i));
		System.out.println("nationality");
		for (int i=0;i<nationality.size();i++)
			System.out.println(nationality.get(i));
		
		//adding of CV
		Vector<String> cv = new Vector<String>();
		cv.add("Proficient in English and Mandarin writing and conversation");
		cv.add("Proficient in microsoft office");
		matchCV.inputCV(cv);
		matchCV.execute(path, language, qualification, experience, nationality);
		double score = matchCV.getScore();
		System.out.println(score);
	}
}
