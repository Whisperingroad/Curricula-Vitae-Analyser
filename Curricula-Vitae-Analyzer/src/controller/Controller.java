package controller;

import Parser.JobDescriptionAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import extractor.TextExtractor;
import Parser.CvAnalyzer;
import Parser.Lemmatise;
import storage.Resume;
import storage.Storage;
import utils.Constants;


public class Controller 
{
	ArrayList<String> experienceStorage = new ArrayList<String>();
	ArrayList<String> experienceHeadersStorage = new ArrayList<String>();
	ArrayList<String> experienceListStorage = new ArrayList<String>();
	ArrayList<String> headersStorage = new ArrayList<String>();
	ArrayList<String> languageStorage = new ArrayList<String>();
	ArrayList<String> languageHeadersStorage = new ArrayList<String>();
	ArrayList<String> languageListStorage = new ArrayList<String>();
	ArrayList<String> mainCategoriesStorage = new ArrayList<String>();
	ArrayList<String> modalsStorage = new ArrayList<String>();
	ArrayList<String> nationalityStorage = new ArrayList<String>();
	ArrayList<String> nationalityListStorage = new ArrayList<String>();
	ArrayList<String> particularsHeadersStorage = new ArrayList<String>();
	ArrayList<String> qualificationStorage = new ArrayList<String>();
	ArrayList<String> qualificationHeadersStorage = new ArrayList<String>();
	ArrayList<String> qualificationListStorage = new ArrayList<String>();
	ArrayList<String> yearStorage = new ArrayList<String>();
	ArrayList<String> yearListStorage = new ArrayList<String>();

	protected Lemmatise textLemmatiser = new Lemmatise();
//	protected JobDescriptionAnalyzer jobDescriptionAnalyzer = new JobDescriptionAnalyzer();
	protected CvAnalyzer cvAnalyzer = new CvAnalyzer();
	protected Storage storage = new Storage(); 

	String resumePath = Constants.NICHOLAS + "Input\\";
	String textResumePath = Constants.NICHOLAS + "Storage\\TextResumes\\";
	String lemmatisedResumePath = Constants.NICHOLAS + "Storage\\LemmatisedResumes\\";
	String libraryPath = Constants.NICHOLAS + "Library\\";

	ArrayList<String> language = new ArrayList<String>();
	ArrayList<String> qualification = new ArrayList<String>();
	ArrayList<String> experience = new ArrayList<String>();
	ArrayList<String> nationality = new ArrayList<String>();
	ArrayList<String> reqYearExp = new ArrayList<String>();
	ArrayList<String> VVVIPList = new ArrayList<String>();

	HashMap<String,Double> nameScorePairsHash =  new HashMap<String,Double>();

	// default constructor
	public Controller() throws FileNotFoundException, IOException
	{
			experienceStorage = storage.readData(libraryPath + "experience.txt");
			experienceHeadersStorage = storage.readData(libraryPath + "experienceHeaders.txt");
			experienceListStorage = storage.readData(libraryPath + "experienceList.txt");
			headersStorage = storage.readData(libraryPath + "Headers.txt");
			languageStorage = storage.readData(libraryPath + "language.txt");
			languageHeadersStorage = storage.readData(libraryPath + "languageHeaders.txt");
			languageListStorage = storage.readData(libraryPath + "languageList.txt");
			mainCategoriesStorage = storage.readData(libraryPath + "mainCategories.txt");
			modalsStorage = storage.readData(libraryPath + "modals.txt");
			nationalityStorage = storage.readData(libraryPath + "nationality.txt");
			nationalityListStorage = storage.readData(libraryPath + "nationalityList.txt");
			particularsHeadersStorage = storage.readData(libraryPath + "particularsHeaders.txt");
			qualificationStorage = storage.readData(libraryPath + "qualification.txt");
			qualificationHeadersStorage = storage.readData(libraryPath + "qualificationHeaders.txt");
			qualificationListStorage = storage.readData(libraryPath + "qualificationList.txt");
			yearStorage = storage.readData(libraryPath + "year.txt");
			yearListStorage = storage.readData(libraryPath + "yearList.txt");
		/*catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

	
	/*
	private File File(String string) {
		// TODO Auto-generated method stub
		return null;
	}
*/


	public String extractCV(File CV) throws IOException
	{
		//String fileName = CV.getName();
		//storage.addResume(fileName);
		//System.out.println("File Name is " + fileName);
		Boolean extractComplete = TextExtractor.execute(CV);
		if(extractComplete == true)
			return TextExtractor.getFileContent();
		else
			return null;

	}

	private void clearLists(){
		language.clear();
		qualification.clear();
		experience.clear();
		nationality.clear();
		reqYearExp.clear();
		VVVIPList.clear();
	}

	private void updateLists(JobDescriptionAnalyzer jobDescriptionAnalyzer){	
		language  = jobDescriptionAnalyzer.getLanguageReq();
		qualification = jobDescriptionAnalyzer.getQualificationReq();
		experience = jobDescriptionAnalyzer.getExperienceReq();
		nationality = jobDescriptionAnalyzer.getNationalityReq();
		reqYearExp = jobDescriptionAnalyzer.getreqYearExp();
		VVVIPList = jobDescriptionAnalyzer.getVVVIPList();
	}
	
	public void startJobProcess(String jobDescription){
		JobDescriptionAnalyzer jobDescriptionAnalyzer = new JobDescriptionAnalyzer(mainCategoriesStorage, experienceStorage, experienceListStorage, languageStorage,
				languageListStorage, nationalityStorage, nationalityListStorage, qualificationStorage, qualificationListStorage, 
				yearStorage, yearListStorage, modalsStorage);
	
		ArrayList<String> jobReq = new ArrayList<String>(Arrays.asList(jobDescription.split("\\r?\\n")));
		jobReq = textLemmatiser.lemmatiser(jobReq);
		//System.out.println(jobReq.toString());
		jobDescriptionAnalyzer.setJobRequirement(jobReq);
		jobDescriptionAnalyzer.execute(libraryPath);
		
		clearLists();
		updateLists(jobDescriptionAnalyzer);
		
		// for testing purposes
		System.out.println("testing");
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
		System.out.println("reqYearExp");
		for (int i=0;i<reqYearExp.size();i++)
			System.out.println(reqYearExp.get(i));
		System.out.println("VVVIPList");
		for (int i=0;i<VVVIPList.size();i++)
			System.out.println(VVVIPList.get(i));
	}
	
	public void setExperienceList(ArrayList<String> input){
		experience.clear();
		experience.addAll(input);
	}
	
	public void setLanguageList(ArrayList<String> input){
		language.clear();
		language.addAll(input);
	}
	
	public void setNationalityList(ArrayList<String> input){
		nationality.clear();
		nationality.addAll(input);
	}
	
	public void setQualificationList(ArrayList<String> input){
		qualification.clear();
		qualification.addAll(input);
	}
	
	public ArrayList<String> getExperience(){
		return experience;
	}
	
	public ArrayList<String> getLanguage(){
		return language;
	}
	
	public ArrayList<String> getNationality(){
		return nationality;
	}
	
	public ArrayList<String> getQualification(){
		return qualification;
	}

	public ArrayList<Resume> startProcessing(File resumePath) throws IOException, FileNotFoundException
	{		
		storage.clearList();
		File[] listOfCVs = resumePath.listFiles();
		for (int i=0;i< listOfCVs.length ; i++)
		{
			String txtCV = extractCV(listOfCVs[i]);
			ArrayList<String> resume = new ArrayList<String>(Arrays.asList(txtCV.split("\n")));	
			ArrayList<String> lemmatisedResume = new ArrayList<String>(textLemmatiser.lemmatiser(resume));
			cvAnalyzer.inputCV(lemmatisedResume);
			double score = cvAnalyzer.execute(libraryPath, language, qualification, experience, nationality, VVVIPList);
			String candidateName = (listOfCVs[i].getName()).replace(lemmatisedResumePath, "");
			candidateName = candidateName.replace(Constants.txtPostFix, "");
			//nameScorePairsHash.put(candidateName, score);
			storage.addResume(score, candidateName,cvAnalyzer.getQualification(), 
					cvAnalyzer.getExperience(), cvAnalyzer.getLanguage(),cvAnalyzer.getParticulars());
		}
		
		for (int i = 0; i< storage.getResumeList().size(); i++){
			System.out.println("CANDIDATE IS " + storage.getResume(i).getName() + " " + storage.getResume(i).getScore());
			for (int j = 0; j< storage.getResume(i).getMatchedExperience().size(); j++){
				System.out.println("EXPERIENCE " + storage.getResume(i).getMatchedExperience().get(j));
			}
		}
		 storage.sortResumeList();
		 return storage.getResumeList();
	}
	
	public void writeAllToLib() throws FileNotFoundException, IOException{
		for (int i = 0; i < experience.size(); i++){
			System.out.println(experience.size());
			if (!experienceListStorage.contains(experience.get(i).toLowerCase())){
				experienceListStorage.add(experience.get(i));
			}
		}
		storage.writeData(experienceListStorage, libraryPath + "experienceList.txt");
		for (int i = 0; i < language.size(); i++){
			if (!languageListStorage.contains(language.get(i).toLowerCase()))
				languageListStorage.add(language.get(i));
		}
		storage.writeData(languageListStorage, libraryPath + "languageList.txt");
		for (int i = 0; i < nationality.size(); i++){
			if (!nationalityListStorage.contains(nationality.get(i).toLowerCase()))
				nationalityListStorage.add(nationality.get(i));
		}
		storage.writeData(nationalityListStorage, libraryPath + "nationalityList.txt");
		for (int i = 0; i < qualification.size(); i++){
			if (!qualificationListStorage.contains(qualification.get(i).toLowerCase()))
				qualificationListStorage.add(qualification.get(i));
		}
		storage.writeData(qualificationListStorage, libraryPath + "qualificationList.txt");
	}
}
