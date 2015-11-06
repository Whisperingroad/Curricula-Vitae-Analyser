
package ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import storage.Resume;
import controller.Controller;

/**
 * Created by workshop on 9/18/2015.
 */

public class ui extends JFrame implements ActionListener{

	JPanel contentPane;
	JButton returnButton;
	JTable table;
	DefaultTableModel model;

	int resultSize = 20;

	//to change to the relevant data type
	List<String> resultFiles = new ArrayList<String>();

	// Constructor
	public ui(ArrayList<Resume> resultList) {
		String[] columnNames = {"CV name", "Score"};
		model = new DefaultTableModel(columnNames, 0);
		
		if(resultList!=null){
			removeRows();
			clearList();
			String data1 = null;
			double data2 = 0.0;

			for (int i = 0; i < resultList.size(); i++){
				data1 = resultList.get(i).getName();
				data2 = resultList.get(i).getScore();
				model.addRow( new Object[] { data1, data2 } );
			}
		}
		
		JTable table = new JTable(model){
            //Implement table cell tool tips.
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);
                int realColumnIndex = convertColumnIndexToModel(colIndex);

                if (realColumnIndex == 0) { //Sport column
                    tip = "<html>"+"File: "
                           + getValueAt(rowIndex, colIndex)
                           + "<br>"
                           + "Score: "
                           + getValueAt(rowIndex, colIndex+1)
                           + "</html>";
                } else if (realColumnIndex == 2) { //Veggie column
                    TableModel model = getModel();
//                    String firstName = (String)model.getValueAt(rowIndex,0);
//                    String lastName = (String)model.getValueAt(rowIndex,1);
//                    Boolean veggie = (Boolean)model.getValueAt(rowIndex,4);
//                    if (Boolean.TRUE.equals(veggie)) {
//                        tip = firstName + " " + lastName
//                              + " is a vegetarian";
//                    } else {
//                        tip = firstName + " " + lastName
//                              + " is not a vegetarian";
//                    }
                } else { 
                    //You can omit this part if you know you don't 
                    //have any renderers that supply their own tool 
                    //tips.
                    tip = super.getToolTipText(e);
                }
                return tip;
            }
		};
		JScrollPane jScrollPanelTable = new JScrollPane(table);
		
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(1, 40, 60, 60));
		resultPanel.add(jScrollPanelTable);

		resultPanel.setBorder(BorderFactory.createEmptyBorder(90,16,20,16));
		
		returnButton = new JButton("Back to Home");
		returnButton.addActionListener(this);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(returnButton);

		contentPane = (JPanel)this.getContentPane();
		setSize(800,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane.add(resultPanel, BorderLayout.PAGE_START);
		contentPane.add(buttonPanel);

		contentPane.setSize(600, 500);
		contentPane.setVisible(true);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent e){
		if (e.getSource() == returnButton){
			this.dispose();
		}
	}
		/*
		if (e.getSource() == readButton){
			jobReq = textArea.getText();
			controller.startJobProcess(jobReq);
			experience = controller.getExperience();
			language = controller.getLanguage();
			nationality = controller.getNationality();
			qualification = controller.getQualification();
		//	jobDescriptionUI jdUI = new jobDescriptionUI(experience,language,nationality,qualification,controller);
			
		//	experience = jdUI.getExperienceList();
		//	language = jdUI.getLanguageList();
		//	nationality= jdUI.getNationality();
		//	qualification = jdUI.getQualification();
			//System.out.println(jobReq);

		}else if (e.getSource() == browseButton){
			filepath = fileChooser();
			System.out.println("1getSelectedFile() : " + filepath);
		}else if (e.getSource() == startButton){
			if(filepath != null){
				removeRows();
				clearList();

				Controller controller = new Controller();
				ArrayList<String> resultList = new ArrayList<String>();
				
				try {
					resultList = controller.startProcessing(filepath);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	

				if(resultList!=null){
					String data1 = null;
					String data2 = null;

					for (int i = 0; i < resultList.size(); i+=2){
						data1 = resultList.get(i);
						data2 = resultList.get(i+1);
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
*/
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

}

