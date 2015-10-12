package controller;

import Parser.JobDescriptionAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

import extractor.TextExtractor;
import Parser.CvAnalyzer;
import Parser.Lemmatise;
import storage.Storage;
import utils.Constants;


public class Controller 
{
	protected Lemmatise textLemmatiser = new Lemmatise();
	protected JobDescriptionAnalyzer jobDescriptionAnalyzer = new JobDescriptionAnalyzer();
	protected CvAnalyzer cvAnalyzer = new CvAnalyzer();
	protected Storage storage = new Storage(); 
	
	String resumePath = Constants.SEBASTIAN + "Input/";
	String textResumePath = Constants.SEBASTIAN + "Storage/TextResumes/";
	String lemmatisedResumePath = Constants.SEBASTIAN + "Storage/LemmatisedResumes/";
	String libraryPath = Constants.SEBASTIAN + "Library/";
	
	ArrayList<String> language = new ArrayList<String>();
	ArrayList<String> qualification = new ArrayList<String>();
	ArrayList<String> experience = new ArrayList<String>();
	ArrayList<String> nationality = new ArrayList<String>();
	
	HashMap<String,String> nameScorePairs =  new HashMap<String,String>();
	
	
	// default constructor
	public Controller()
	{
		
	}
	
	/*
	public void extractCV()
	{
		// extracting all resume information from .pdf and .doc files
		// and convert them into .txt format
		File cvFolder = new File(resumePath);
		File[] listOfCVs = cvFolder.listFiles();
		for (File cv : listOfCVs)
		{
			String extractedCV = "stub";
			// write to storage
			String textResumeFile = (cv.toString()).replaceAll(resumePath, textResumePath);
			textResumeFile = textResumeFile.replaceAll(Constants.pdfPostFix, Constants.txtPostFix);
			// for checking purposes
			System.out.println(cv.toString());
			System.out.println(textResumeFile);
			storage.writeData(extractedCV, textResumeFile);	
		}
	}*/
	
	public void extractCV() throws IOException
	{
		// extracting all resume information from .pdf and .doc files
		// and convert them into .txt format
		File cvFolder = new File(resumePath);
		File[] listOfCVs = cvFolder.listFiles();
		for (File cv : listOfCVs)
		{
			System.out.println(cv);
			System.out.println(cv.toPath());
			String fileType = Files.probeContentType(cv.toPath());
			System.out.println("Document type is " + fileType);
			String fileName = cv.getName();
			System.out.println("File Name is " + fileName);
			Boolean extractComplete = TextExtractor.execute(cv);
			if(extractComplete == true)
			{	
				String textResumeFile = (cv.toString()).replaceAll(resumePath, textResumePath);
				if(TextExtractor.getFilePostfix().equals(Constants.txtPostFix)){
					storage.moveTxtFile(textResumeFile, cv);
				}
				else if(TextExtractor.getFilePostfix().equals("none")){
					System.out.println("file not accepted");
				}
				//file type = ".pdf", ".doc", ".docx"
				else{
					
					textResumeFile = textResumeFile.replaceAll(TextExtractor.getFilePostfix(), Constants.txtPostFix);
					System.out.println("changed" + textResumeFile);
					// for checking purposes
					System.out.println(cv.toString());
					System.out.println(textResumeFile);
					storage.writeData(TextExtractor.getFileContent(), textResumeFile);
				}
			}
		}
	}
	
	public HashMap<String,String> startProcessing() throws IOException, FileNotFoundException
	{
		extractCV();
		// lemmatizing resumes 
		File cvTextFolder = new File(textResumePath);
		File[] listOfTextCVs = cvTextFolder.listFiles();
		for (File cvText : listOfTextCVs)
		{
			ArrayList<String> resume = new ArrayList<String>();
			ArrayList<String> lemmatisedResume = new ArrayList<String>();
 			resume = storage.readData(cvText);
 			lemmatisedResume = textLemmatiser.lemmatiser(resume);
 			// write to storage
 			String lemmatisedResumeFile = (cvText.toString()).replaceAll(textResumePath, lemmatisedResumePath);
 			storage.writeData(lemmatisedResume, lemmatisedResumeFile);	
		}
		// job description extraction
		//jobDescriptionAnalyzer.setJobRequirement();
		jobDescriptionAnalyzer.execute(libraryPath);
		language  = jobDescriptionAnalyzer.getLanguageReq();
		qualification = jobDescriptionAnalyzer.getQualificationReq();
		experience = jobDescriptionAnalyzer.getExperienceReq();
		nationality = jobDescriptionAnalyzer.getNationalityReq();
		
		// for testing purposes
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
		
		// match cv
		File lemmatisedCVFolder = new File(lemmatisedResumePath);
		File[] listOfLemmatisedCVs = lemmatisedCVFolder.listFiles();
		for (File lemmatisedCV : listOfLemmatisedCVs)
		{
			ArrayList<String> cvInfo = new ArrayList<String>();
			cvInfo = storage.readData(lemmatisedCV);
			cvAnalyzer.inputCV(cvInfo);
			cvAnalyzer.execute(libraryPath, language, qualification, experience, nationality);
			String score = String.valueOf(cvAnalyzer.getScore());
			String candidateName = (lemmatisedCV.toString()).replaceAll(lemmatisedResumePath, "");
			candidateName = candidateName.replaceAll(Constants.txtPostFix, "");
			// name of candidate and score
			nameScorePairs.put(candidateName, score);
		}
		return nameScorePairs;
	}
}
