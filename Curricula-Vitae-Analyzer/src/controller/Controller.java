package controller;

import Parser.JobDescriptionAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

import extractor.TextExtractor;
import Parser.CvAnalyzer;
import Parser.Lemmatise;
import storage.Resume;
import storage.Storage;
import utils.Constants;


public class Controller 
{
	protected Lemmatise textLemmatiser = new Lemmatise();
	protected JobDescriptionAnalyzer jobDescriptionAnalyzer = new JobDescriptionAnalyzer();
	protected CvAnalyzer cvAnalyzer = new CvAnalyzer();
	protected Storage storage = new Storage(); 

	String resumePath = Constants.YIXIU + "Input\\";
	String textResumePath = Constants.YIXIU + "Storage\\TextResumes\\";
	String lemmatisedResumePath = Constants.YIXIU + "Storage\\LemmatisedResumes\\";
	String libraryPath = Constants.YIXIU + "Library\\";


	ArrayList<String> language = new ArrayList<String>();
	ArrayList<String> qualification = new ArrayList<String>();
	ArrayList<String> experience = new ArrayList<String>();
	ArrayList<String> nationality = new ArrayList<String>();

	TreeMap<Double,String> nameScorePairsTree =  new TreeMap<Double,String>();
	HashMap<String,Double> nameScorePairsHash =  new HashMap<String,Double>();

	// default constructor
	public Controller()
	{

	}


	public Boolean extractCV(File cvFolder) throws IOException
	{
		// extracting all resume information from .pdf and .doc files
		// and convert them into .txt format
		//File cvFolder = new File(resumePath);
		Boolean extractComplete = false;
		File[] listOfCVs = cvFolder.listFiles();
		for (int i=0;i< listOfCVs.length ; i++)
			//for (File cv : listOfCVs)
		{
			//System.out.println(cv);
			//System.out.println(cv.toPath());

			String fileType = Files.probeContentType(listOfCVs[i].toPath());
			//System.out.println("Document type is " + fileType);
			String fileName = listOfCVs[i].getName();
			storage.addResume(fileName);
			//System.out.println("File Name is " + fileName);
			extractComplete = TextExtractor.execute(listOfCVs[i]);
			if(extractComplete == true)
			{	
				String textResumeFile = (listOfCVs[i].toString()).replace(cvFolder.toString(), textResumePath);		

				if(TextExtractor.getFilePostfix().equals(Constants.txtPostFix)){
					storage.moveTxtFile(textResumeFile, listOfCVs[i]);
				}
				else if(TextExtractor.getFilePostfix().equals("none")){
					System.out.println("file not accepted");
				}
				//file type = ".pdf", ".doc", ".docx"
				else{

					textResumeFile = textResumeFile.replace(TextExtractor.getFilePostfix(), Constants.txtPostFix);
					//System.out.println("changed" + textResumeFile);
					// for checking purposes
					//System.out.println(cv.toString());
					//System.out.println(textResumeFile);
					storage.writeData(TextExtractor.getFileContent(), textResumeFile);
				}
			}

		}
		return extractComplete;
	}

	private void clearLists(){
		language.clear();
		qualification.clear();
		experience.clear();
		nationality.clear();
	}

	private void updateLists(){	
		language  = jobDescriptionAnalyzer.getLanguageReq();
		qualification = jobDescriptionAnalyzer.getQualificationReq();
		experience = jobDescriptionAnalyzer.getExperienceReq();
		nationality = jobDescriptionAnalyzer.getNationalityReq();
	}

	public HashMap<String,Double> startProcessing(String jobDescription, File resumePath) throws IOException, FileNotFoundException
	{
		if(extractCV(resumePath)){
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
				String lemmatisedResumeFile = (cvText.toString()).replace(textResumePath, lemmatisedResumePath);
				storage.writeData(lemmatisedResume, lemmatisedResumeFile);	
			}
			// job description extraction
			ArrayList<String> jobReq = new ArrayList<String>(Arrays.asList(jobDescription.split("\\r?\\n")));
			jobDescriptionAnalyzer.setJobRequirement(jobReq);
			jobDescriptionAnalyzer.execute(libraryPath);

			clearLists();
			updateLists();

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
			for (int i=0; i<listOfLemmatisedCVs.length ;i++)
			{
				ArrayList<String> cvInfo = new ArrayList<String>();
				cvInfo = storage.readData(listOfLemmatisedCVs[i]);
				cvAnalyzer.inputCV(cvInfo);
				double score = cvAnalyzer.execute(libraryPath, language, qualification, experience, nationality);
				String candidateName = (listOfLemmatisedCVs[i].toString()).replace(lemmatisedResumePath, "");
				candidateName = candidateName.replace(Constants.txtPostFix, "");
				nameScorePairsHash.put(candidateName, score);
				storage.getResume(i).setResume(score, candidateName);
			}

			for (int i= 0; i<listOfLemmatisedCVs.length;i++){
				String filename = storage.getResume(i).getFileName();
				String name = storage.getResume(i).getName();
				double score = storage.getResume(i).getScore();
				System.out.println(filename + " " + name + " " + score);
			}

			return nameScorePairsHash;
		}
		else{
			return null;
		}
	}
}
