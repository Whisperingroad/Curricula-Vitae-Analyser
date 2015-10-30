package ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.Controller;

/**
 * Created by workshop on 9/18/2015.
 */
public class ui extends JFrame implements ActionListener{

	JPanel contentPane, browseError;
	JButton readButton, startButton, browseButton;
	JTextField textField;
	JTextArea textArea;
	JTable table;
	DefaultTableModel model;
	File filepath;

	int resultSize = 20;
	String jobReq = null;

	//to change to the relevant data type
	List<String> resultFiles = new ArrayList<String>();

	// Constructor
	public ui() {
		//	textField = new JTextField(20);
		textArea = new JTextArea(5, 20);

		textArea.setColumns(40);
		textArea.setLineWrap(true);
		textArea.setRows(10);
		textArea.setWrapStyleWord(true);

		JScrollPane jScrollPanelTxtArea = new JScrollPane(textArea);

		readButton = new JButton("Read Job Description");
		readButton.addActionListener(this);

		startButton = new JButton("Start Processing");
		startButton.addActionListener(this);

		browseButton = new JButton("Browse Folder");
		browseButton.addActionListener(this);

		JPanel queryPanel = new JPanel();
		queryPanel.add(jScrollPanelTxtArea);
		JPanel buttonPanel = new JPanel(new BorderLayout());
		queryPanel.add(buttonPanel, BorderLayout.EAST);
		buttonPanel.add(readButton, BorderLayout.NORTH);
		buttonPanel.add(startButton, BorderLayout.CENTER);
		buttonPanel.add(browseButton, BorderLayout.SOUTH);

		JPanel resultPanel = new JPanel();

		resultPanel.setLayout(new GridLayout(1, 40, 60, 60));

		String[] columnNames = {"CV name", "Score"};
		model = new DefaultTableModel(columnNames, 0);
		JTable table = new JTable(model);
		JScrollPane jScrollPanelTable = new JScrollPane(table);

		resultPanel.add(jScrollPanelTable);

		resultPanel.setBorder(BorderFactory.createEmptyBorder(30,16,10,16));

		contentPane = (JPanel)this.getContentPane();
		setSize(800,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane.add(queryPanel, BorderLayout.PAGE_START);
		contentPane.add(resultPanel, BorderLayout.CENTER);

		contentPane.setSize(600, 500);
		contentPane.setVisible(true);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent e){
		if (e.getSource() == readButton){
			jobReq = textArea.getText();
			//System.out.println(jobReq);


		}else if (e.getSource() == browseButton){
			filepath = fileChooser();
			System.out.println("1getSelectedFile() : " + filepath);
		}else if (e.getSource() == startButton){
			if(filepath != null){
				removeRows();
				clearList();

				Controller controller = new Controller();
				HashMap<String,Double> resultList = new HashMap<String,Double>();
				try {
					resultList = controller.startProcessing(jobReq,filepath);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	

				//HashMap<String,Double> resultList = new HashMap<String,Double>();
				//resultList.put("yq.cv", 12.1);
				//resultList.put("seb.cv", 31.1);
				//resultList.put("yx.cv", 22.1);
				//resultList.put("nic.cv", 42.1);

				//int numEntry = resultList.size();

				//   SearchDemo searchDemo = new SearchDemo();
				//   resultFiles = searchDemo.resultList(queryAudio.getAbsolutePath(),0);

				//Testing Purposes
				/*
        	resultFiles.add("1");
        	resultFiles.add("2");
        	resultFiles.add("3");
        	resultFiles.add("4");
        	resultFiles.add("5");
        	resultFiles.add("6");
        	resultFiles.add("7");
        	resultFiles.add("8");
        	resultFiles.add("9");
        	resultFiles.add("10");
				 */
				if(resultList!=null){
					String data1 = null;
					double data2 = 0.0;

					for (Entry<String, Double> f: resultList.entrySet()){
						data1 = f.getKey();
						data2 = f.getValue();
						model.addRow( new Object[] { data1, data2 } );
					}
				}
				else{
					filePathInvalidDialog();
				}
			}else{
				missingFolderErrorDialog();
			}
		}
	}

	private void removeRows(){
		if (model.getRowCount() > 0) {
			for (int i = model.getRowCount() - 1; i > -1; i--) {
				model.removeRow(i);
			}
		}
	}

	private void clearList(){
		while (!resultFiles.isEmpty())
			resultFiles.remove(0);
	}

	private void filePathInvalidDialog(){
		JFrame frame = new JFrame("Folder invalid");
		frame.setSize(300, 100);
		browseError = new JPanel(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		String message = "Folder does not contain PDF or Word Documents";
		JLabel content1 = new JLabel(message,JLabel.CENTER);
		message = "Please choose another folder"; 
		JLabel content2 = new JLabel(message,JLabel.CENTER);
		browseError.add(content1, BorderLayout.NORTH);
		browseError.add(content2, BorderLayout.CENTER);

		frame.setContentPane(browseError);
		frame.setVisible(true);
	}
	private void missingFolderErrorDialog(){
		JFrame frame = new JFrame("Missing Folder");
		frame.setSize(300, 100);
		browseError = new JPanel(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		String message = "Please choose a folder for processing"; 
		JLabel content = new JLabel(message,JLabel.CENTER);
		browseError.add(content, BorderLayout.CENTER);

		frame.setContentPane(browseError);
		frame.setVisible(true);
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

	public static void main(String[] args) {
		new ui();
	}
}

