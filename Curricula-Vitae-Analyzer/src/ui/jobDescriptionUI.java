package ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;

import utils.Constants;




import com.sun.org.apache.bcel.internal.classfile.ConstantNameAndType;

import storage.Resume;
import controller.Controller;

/**
 * Created by workshop on 9/18/2015.
 */
public class jobDescriptionUI extends JFrame implements ActionListener{

	JTextArea inputJDArea, textAreaExperience, textAreaLanguage, textAreaNationality, textAreaQualification, textJobTitleArea;
	JButton readJDButton, doneButton, addExperienceButton, removeExperienceButton, addLanguageButton, removeLanguageButton, 
	addNationalityButton, removeNationalityButton, addQualificationButton, removeQualificationButton, browseButton;
	
	JPanel contentPane, browseError;
	DefaultTableModel modelExperienceTable, modelLanguageTable, modelNationalityTable, modelQualificationTable;

	String experienceString = null;
	String languageString = null;
	String nationalityString = null;
	String qualificationString = null;
	
	//to change to the relevant data type
	List<String> resultFiles = new ArrayList<String>();
	ArrayList<String> experience = new ArrayList<String>();
	ArrayList<String> language = new ArrayList<String>();
	ArrayList<String> nationality = new ArrayList<String>();
	ArrayList<String> qualification = new ArrayList<String>();
	
	File filepath;

	//Create controller
	//Controller controller = new Controller();
	Controller controller;
	
	// Constructor
	public jobDescriptionUI() {
		//Read Job Description Panel
		try{
		controller = new Controller();
		}
		catch(FileNotFoundException e){
			//e.printStackTrace();
			errorDialog("File not found!");
		}
		catch(IOException e1){
			//e1.printStackTrace();
			errorDialog("File cannot be opened!");
		}
		catch (NullPointerException e1) {
			//e1.printStackTrace();
			errorDialog("Wrong file format exists!");
		}
		
		inputJDArea = new JTextArea(5, 20);
		inputJDArea.setColumns(50);
		inputJDArea.setLineWrap(true);
		inputJDArea.setRows(10);
		inputJDArea.setWrapStyleWord(true);
		inputJDArea.setText("Job Description: ");

		JScrollPane jScrollPanelInputJDArea = new JScrollPane(inputJDArea);
		
		
		readJDButton = new JButton("Read Job Description");
		readJDButton.addActionListener(this);
		JPanel inputJDPanel = new JPanel();
		inputJDPanel.add(jScrollPanelInputJDArea);
		inputJDPanel.add(readJDButton);
		
		
		//Attribute Table Panel
		String[] experienceColumns = {"Experience"};
		modelExperienceTable = new DefaultTableModel(experienceColumns, 0);
		JTable experienceTable = new JTable(modelExperienceTable);
		JScrollPane jScrollPanelExperienceTable = new JScrollPane(experienceTable);
		jScrollPanelExperienceTable.setPreferredSize(new Dimension(180, 200));
		String[] languageColumns = {"Language"};
		modelLanguageTable = new DefaultTableModel(languageColumns, 0);
		JTable languageTable = new JTable(modelLanguageTable);
		JScrollPane jScrollPanelLanguageTable = new JScrollPane(languageTable);
		jScrollPanelLanguageTable.setPreferredSize(new Dimension(180, 200));
		String[] nationalityColumns = {"Nationality"};
		modelNationalityTable = new DefaultTableModel(nationalityColumns, 0);
		JTable nationalityTable = new JTable(modelNationalityTable);
		JScrollPane jScrollPanelNationalityTable = new JScrollPane(nationalityTable);
		jScrollPanelNationalityTable.setPreferredSize(new Dimension(180, 200));
		String[] qualificationColumns = {"Qualification"};
		modelQualificationTable = new DefaultTableModel(qualificationColumns, 0);
		JTable qualificationTable = new JTable(modelQualificationTable);
		JScrollPane jScrollPanelQualificationTable = new JScrollPane(qualificationTable);
		jScrollPanelQualificationTable.setPreferredSize(new Dimension(180, 200));
		
		JPanel attributeTablePanel = new JPanel();
		attributeTablePanel.add(jScrollPanelExperienceTable);
		attributeTablePanel.add(jScrollPanelLanguageTable);
		attributeTablePanel.add(jScrollPanelNationalityTable);
		attributeTablePanel.add(jScrollPanelQualificationTable);
		
		//Attributes tab
		JTabbedPane attributeTab = new JTabbedPane();
		JPanel experiencePanel = new JPanel();
		JPanel languagePanel = new JPanel();
		JPanel nationalityPanel = new JPanel();
		JPanel qualificationPanel = new JPanel();
		
		attributeTab.addTab("Experience", experiencePanel);
		
		textAreaExperience = new JTextArea(5, 20);
		textAreaExperience.setColumns(40);
		textAreaExperience.setLineWrap(true);
		textAreaExperience.setRows(6);
		textAreaExperience.setWrapStyleWord(true);
		JScrollPane jScrollPanelTxtAreaExperience = new JScrollPane(textAreaExperience);

		addExperienceButton = new JButton("Add Experience");
		addExperienceButton.addActionListener(this);
		removeExperienceButton = new JButton("Remove Experience");
		removeExperienceButton.addActionListener(this);
		JPanel experienceButtonPanel = new JPanel();
		experienceButtonPanel.add(addExperienceButton);
		experienceButtonPanel.add(removeExperienceButton);
		experiencePanel.add(jScrollPanelTxtAreaExperience);
		experiencePanel.add(experienceButtonPanel);
		
		attributeTab.addTab("Language", languagePanel);
		
		textAreaLanguage = new JTextArea(5, 20);
		textAreaLanguage.setColumns(40);
		textAreaLanguage.setLineWrap(true);
		textAreaLanguage.setRows(6);
		textAreaLanguage.setWrapStyleWord(true);
		JScrollPane jScrollPanelTxtAreaLanguage = new JScrollPane(textAreaLanguage);

		addLanguageButton = new JButton("Add Language");
		addLanguageButton.addActionListener(this);
		removeLanguageButton = new JButton("Remove Language");
		removeLanguageButton.addActionListener(this);
		JPanel languageButtonPanel = new JPanel();
		languageButtonPanel.add(addLanguageButton);
		languageButtonPanel.add(removeLanguageButton);
		languagePanel.add(jScrollPanelTxtAreaLanguage);
		languagePanel.add(languageButtonPanel);
		
		attributeTab.addTab("Nationality", nationalityPanel);
		
		textAreaNationality = new JTextArea(5, 20);
		textAreaNationality.setColumns(40);
		textAreaNationality.setLineWrap(true);
		textAreaNationality.setRows(6);
		textAreaNationality.setWrapStyleWord(true);
		JScrollPane jScrollPanelTxtAreaNationality = new JScrollPane(textAreaNationality);

		addNationalityButton = new JButton("Add Nationality");
		addNationalityButton.addActionListener(this);
		removeNationalityButton = new JButton("Remove Nationality");
		removeNationalityButton.addActionListener(this);
		JPanel nationalityButtonPanel = new JPanel();
		nationalityButtonPanel.add(addNationalityButton);
		nationalityButtonPanel.add(removeNationalityButton);
		nationalityPanel.add(jScrollPanelTxtAreaNationality);
		nationalityPanel.add(nationalityButtonPanel);
		
		attributeTab.addTab("Qualification", qualificationPanel);
		
		textAreaQualification = new JTextArea(5, 20);
		textAreaQualification.setColumns(40);
		textAreaQualification.setLineWrap(true);
		textAreaQualification.setRows(6);
		textAreaQualification.setWrapStyleWord(true);
		JScrollPane jScrollPanelTxtAreaQualification = new JScrollPane(textAreaQualification);

		addQualificationButton = new JButton("Add Qualification");
		addQualificationButton.addActionListener(this);
		removeQualificationButton = new JButton("Remove Qualification");
		removeQualificationButton.addActionListener(this);
		JPanel qualificationButtonPanel = new JPanel();
		qualificationButtonPanel.add(addQualificationButton);
		qualificationButtonPanel.add(removeQualificationButton);
		qualificationPanel.add(jScrollPanelTxtAreaQualification);
		qualificationPanel.add(qualificationButtonPanel);
		
		attributeTab.setPreferredSize(new Dimension(500, 180));
		
		browseButton = new JButton("Browse Files");
		browseButton.addActionListener(this);
		doneButton = new JButton("Start Processing");
		doneButton.addActionListener(this);
		
		JPanel finishButtonPanel = new JPanel(new BorderLayout());
		finishButtonPanel.add(browseButton, BorderLayout.NORTH);
		finishButtonPanel.add(doneButton, BorderLayout.SOUTH);
		
		JPanel tabDonePanel = new JPanel();
		tabDonePanel.add(attributeTab, BorderLayout.WEST);
		tabDonePanel.add(finishButtonPanel, BorderLayout.EAST);
		
		contentPane = (JPanel)this.getContentPane();
		setSize(800,700);
		contentPane.setLayout(new GridLayout(3,0));
		contentPane.add(inputJDPanel);
		contentPane.add(attributeTablePanel);
		contentPane.add(tabDonePanel);

		contentPane.setSize(600, 500);
		contentPane.setVisible(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e){
			 
		if (e.getSource() == readJDButton){
			flushArrayList();
			String jobReq = inputJDArea.getText();
			controller.startJobProcess(jobReq);
			experience.addAll(controller.getExperience());
			language.addAll(controller.getLanguage());
			nationality.addAll(controller.getNationality());
			qualification.addAll(controller.getQualification());
			displayExperienceTable();
			displayLanguageTable();
			displayNationalityTable();
			displayQualificationTable();
		}
		
		else if (e.getSource() == addExperienceButton){
			experienceString = textAreaExperience.getText();
			ArrayList<String> experienceLearning = parseData(experienceString);
			checkAddAllExperience(experienceLearning);
			displayExperienceTable();
		}
		
		else if (e.getSource() == removeExperienceButton){
			experienceString = textAreaExperience.getText();
			ArrayList<String> experienceLearning = parseData(experienceString);
			removeExperience(experienceLearning);
			displayExperienceTable();
		}
		
		else if (e.getSource() == addLanguageButton){
			languageString = textAreaLanguage.getText();
			ArrayList<String> languageLearning = parseData(languageString);
			checkAddAllLanguage(languageLearning);
			displayLanguageTable();
		}

		else if (e.getSource() == removeLanguageButton){
			languageString = textAreaLanguage.getText();
			ArrayList<String> languageLearning = parseData(languageString);
			removeLanguage(languageLearning);
			displayLanguageTable();
		}
		
		else if (e.getSource() == addNationalityButton){
			nationalityString = textAreaNationality.getText();
			ArrayList<String> nationalityLearning = parseData(nationalityString);
			checkAddAllNationality(nationalityLearning);
			displayNationalityTable();
		}

		else if (e.getSource() == removeNationalityButton){
			nationalityString = textAreaNationality.getText();
			ArrayList<String> nationalityLearning = parseData(nationalityString);
			removeNationality(nationalityLearning);
			displayNationalityTable();	
		}
		
		else if (e.getSource() == addQualificationButton){
			qualificationString = textAreaQualification.getText();
			ArrayList<String> qualificationLearning = parseData(qualificationString);
			checkAddAllQualification(qualificationLearning);
			displayQualificationTable();
		}

		else if (e.getSource() == removeQualificationButton){
			qualificationString = textAreaQualification.getText();
			ArrayList<String> qualificationLearning = parseData(qualificationString);
			removeQualification(qualificationLearning);
			displayQualificationTable();
		}
		
		else if (e.getSource() == browseButton){
			filepath = fileChooser();
		}
		
		else if (e.getSource() == doneButton){
			if(filepath != null){
				ArrayList<Resume> resultList = new ArrayList<Resume>();				
				
				try {
					controller.setExperienceList(experience);
					controller.setLanguageList(language);
					controller.setNationalityList(nationality);
					controller.setQualificationList(qualification);
					resultList.clear();
					resultList = controller.startProcessing(filepath);
					ui UI = new ui(resultList);
					controller.writeAllToLib();
				} catch (FileNotFoundException e1) {
					//e1.printStackTrace();
					errorDialog("File not found!");
				} catch (IOException e1) {
					//e1.printStackTrace();
					errorDialog("File cannot be opened!");
				}
				 catch (NullPointerException e1) {
						//e1.printStackTrace();
					errorDialog("Wrong file format exists!");
				}
				catch (org.apache.poi.POIXMLException e1){
					errorDialog("Please close all input files!");
				}
				
			/* checking purposes
			System.out.println("processing");
			for (int i=0;i<experience.size();i++)
				System.out.println(experience.get(i));
			for (int i=0;i<language.size();i++)
				System.out.println(language.get(i));
			for (int i=0;i<nationality.size();i++)
				System.out.println(nationality.get(i));
			for (int i=0;i<qualification.size();i++)
				System.out.println(qualification.get(i));
			controller.setExperienceList(experience);
			controller.setLanguageList(language);
			controller.setNationalityList(nationality);
			controller.setqualificationList(qualification);
			 */
				flushArrayList();
				displayExperienceTable();
				displayLanguageTable();
				displayNationalityTable();
				displayQualificationTable();
				//this.dispose();
			}
			else 
				missingFolderErrorDialog();
		}
	}
	
	private ArrayList<String> parseData(String data) {
		ArrayList<String> parseArray = new ArrayList<String>(Arrays.asList(data.split("\\r?\\n")));
		return parseArray;
	}
	
	private void displayExperienceTable(){
		String experienceData = null;
		removeRows(modelExperienceTable);
		for (int i = 0;i < experience.size(); i++){
			experienceData = experience.get(i);
			modelExperienceTable.addRow( new Object[] { experienceData } );
		}
	}
	
	private void displayLanguageTable(){
		String languageData = null;
		removeRows(modelLanguageTable);
		for (int i = 0;i < language.size(); i++){
			languageData = language.get(i);
			modelLanguageTable.addRow( new Object[] { languageData } );
		}
	}
	
	private void displayNationalityTable(){
		String nationalityData = null;
		removeRows(modelNationalityTable);
		for (int i = 0;i < nationality.size(); i++){
			nationalityData = nationality.get(i);
			modelNationalityTable.addRow( new Object[] { nationalityData } );
		}
	}
	
	private void displayQualificationTable(){
		String qualificationData = null;
		removeRows(modelQualificationTable);
		for (int i = 0;i < qualification.size(); i++){
			qualificationData = qualification.get(i);
			modelQualificationTable.addRow( new Object[] { qualificationData } );
		}
	}
	
	private void removeRows(DefaultTableModel model){
		if (model.getRowCount() > 0) {
			for (int i = model.getRowCount() - 1; i > -1; i--) {
				model.removeRow(i);
			}
		}
	}
	
	private void checkAddAllExperience(ArrayList<String> newInput){
		for (int size = 0; size < newInput.size(); size++){
			if (!experience.contains(newInput.get(size).trim())){
				experience.add(newInput.get(size).trim());
			}
		}
	}
	
	private void checkAddAllLanguage(ArrayList<String> newInput){
		for (int size = 0; size < newInput.size(); size++){
			if (!language.contains(newInput.get(size).trim())){
				language.add(newInput.get(size).trim());
			}
		}
	}
	
	private void checkAddAllNationality(ArrayList<String> newInput){
		for (int size = 0; size < newInput.size(); size++){
			if (!nationality.contains(newInput.get(size).trim())){
				nationality.add(newInput.get(size).trim());
			}
		}
	}
	
	private void checkAddAllQualification(ArrayList<String> newInput){
		for (int size = 0; size < newInput.size(); size++){
			if (!qualification.contains(newInput.get(size).trim())){
				qualification.add(newInput.get(size).trim());
			}
		}
	}
	
	private void removeExperience(ArrayList<String> experienceLearning){
		for (int size = 0; size < experienceLearning.size() ; size++){
			if (experience.contains(experienceLearning.get(size).trim())){
				experience.remove(experienceLearning.get(size));
			}
		}
	}
	
	private void removeLanguage(ArrayList<String> languageLearning){
		for (int size = 0; size < languageLearning.size() ; size++){
			if (language.contains(languageLearning.get(size).trim())){
				language.remove(languageLearning.get(size));
			}
		}
	}
	
	private void removeNationality(ArrayList<String> nationalityLearning){
		for (int size = 0; size < nationalityLearning.size() ; size++){
			if (nationality.contains(nationalityLearning.get(size).trim())){
				nationality.remove(nationalityLearning.get(size));
			}
		}
	}
	
	private void removeQualification(ArrayList<String> qualificationLearning){
		for (int size = 0; size < qualificationLearning.size() ; size++){
			if (qualification.contains(qualificationLearning.get(size).trim())){
				qualification.remove(qualificationLearning.get(size));
			}
		}
	}
	
	/* unused function
	private void filePathInvalidDialog(){
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,
			    "Please select another folder",
			    "Invalid Folder",
			    JOptionPane.WARNING_MESSAGE);
//		JFrame frame = new JFrame("Folder invalid");
//		frame.setSize(300, 100);
//		browseError = new JPanel(new BorderLayout());
//		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//		String message = "Folder does not contain PDF or Word Documents";
//		JLabel content1 = new JLabel(message,JLabel.CENTER);
//		message = "Please choose another folder"; 
//		JLabel content2 = new JLabel(message,JLabel.CENTER);
//		browseError.add(content1, BorderLayout.NORTH);
//		browseError.add(content2, BorderLayout.CENTER);
//
//		frame.setContentPane(browseError);
//		frame.setVisible(true);
	}*/
	
	private void missingFolderErrorDialog(){
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,
			    "Please select a folder",
			    "No Folder Selected",
			    JOptionPane.WARNING_MESSAGE);
//		JFrame frame = new JFrame("Missing Folder");
//		frame.setSize(300, 100);
//		browseError = new JPanel(new BorderLayout());
//		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//		String message = "Please choose a folder for processing"; 
//		JLabel content = new JLabel(message,JLabel.CENTER);
//		browseError.add(content, BorderLayout.CENTER);
//
//		frame.setContentPane(browseError);
//		frame.setVisible(true);
	}
	
	private void errorDialog(String text){
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,
			    text,
			    "Error",
			    JOptionPane.WARNING_MESSAGE);
	}

	private File fileChooser(){
		JFileChooser chooser = new JFileChooser();
		String folder = null;
		if(filepath!=null){
			folder = filepath.toString();
		}else{
			folder = ".";
		}
		chooser.setCurrentDirectory(new java.io.File(folder));
		chooser.setDialogTitle("Browse Folder");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		File filePath = null;

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
			filePath = chooser.getSelectedFile();
		} else {
			System.out.println("No Selection ");
		}
		return filePath;
	}
	
	private void flushArrayList(){
		experience.clear();
		language.clear();
		nationality.clear();
		qualification.clear();
	}
	
	public static void main(String[] args) {
		new jobDescriptionUI();
	}
}