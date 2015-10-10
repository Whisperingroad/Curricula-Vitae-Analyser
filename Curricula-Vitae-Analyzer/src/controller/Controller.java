package controller;

import extractor.PDFTextParser;
import Parser.JobDescriptionAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import Parser.CvAnalyzer;
import Parser.Lemmatise;
import storage.Storage;
import utils.Constants;


public class Controller 
{
	protected PDFTextParser pdfExtractor = new PDFTextParser();
	protected Lemmatise textLemmatiser = new Lemmatise();
	protected JobDescriptionAnalyzer jobDescriptionAnalyzer = new JobDescriptionAnalyzer();
	protected CvAnalyzer cvAnalyzer = new CvAnalyzer();
	protected Storage storage = new Storage(); 
	
	// default constructor
	public Controller()
	{
		
	}
	
	
	public String extractCV(File resume)
	{
		//stub
		String stub = "";
		return stub;
	}
	
	public void startProcessing() throws IOException, FileNotFoundException
	{

		String resumePath = Constants.SEBASTIAN + "Input/";
		String textResumePath = Constants.SEBASTIAN + "Storage/TextResumes/";
		String lemmatisedResumePath = Constants.SEBASTIAN + "/Storage/LemmatisedResumes/";
		
		// extracting all resume information from .pdf and .doc files
		// and convert them into .txt format
		File cvFolder = new File(resumePath);
		File[] listOfCVs = cvFolder.listFiles();
		for (File cv : listOfCVs)
		{
			String extractedCV = extractCV(cv);
			// write to storage
			String textResumeFile = (cv.toString()).replaceAll(resumePath, textResumePath);
			textResumeFile = textResumeFile.replaceAll(Constants.pdfPostFix, Constants.txtPostFix);
			// for checking purposes
			System.out.println(cv.toString());
			System.out.println(textResumeFile);
			storage.writeData(extractedCV, textResumeFile);	
		}
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
		
		
		// match cv		
	}
}
