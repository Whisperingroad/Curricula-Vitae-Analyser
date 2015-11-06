
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
	//List<float[]> resultScores = new ArrayList<float[]>();
	ArrayList<Resume> candidateList;

	// Constructor
	public ui(ArrayList<Resume> resultList) {
		String[] columnNames = {"CV name", "Score"};
		model = new DefaultTableModel(columnNames, 0);
		candidateList = new ArrayList<Resume>(resultList);
		System.out.println("Size of candidateList = "+ candidateList.size());
		for(int i=0; i<resultList.size(); i++){
			System.out.println("row: "+ i);
			ArrayList<String> expsize = resultList.get(i).getMatchedExperience();
			for(int j=0; j<expsize.size(); j++){
				System.out.println("experience: "+expsize.get(j));
			}
			expsize = resultList.get(i).getMatchedQualification();
			for(int j=0; j<expsize.size(); j++){
				System.out.println("qual: "+expsize.get(j));
			}
			expsize = resultList.get(i).getMatchedLanguage();
			for(int j=0; j<expsize.size(); j++){
				System.out.println("lang: "+expsize.get(j));
			}
			expsize = resultList.get(i).getMatchedParticulars();
			for(int j=0; j<expsize.size(); j++){
				System.out.println("part: "+expsize.get(j));
			}
		}
		
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

                if (realColumnIndex == 0) { //file name column
                    tip = "<html>"+"File: "
                           + getValueAt(rowIndex, colIndex)
                           + "<br>"
                           + "Score: "
                           + getValueAt(rowIndex, colIndex+1)
                           + "</html>";
                } else { 
                    //You can omit this part if you know you don't 
                    //have any renderers that supply their own tool 
                    //tips.
                    tip = super.getToolTipText(e);
                }
                return tip;
            }
		};
		
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				JTable target = (JTable)e.getSource();
				int row = target.getSelectedRow();
				int col = target.getSelectedColumn();
				if(col==0){
					resultDetailsDialog(row,col,target);
				}
			}
		});
		
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		contentPane.add(resultPanel, BorderLayout.PAGE_START);
		contentPane.add(buttonPanel);

		contentPane.setSize(600, 500);
		contentPane.setVisible(true);
		setVisible(true);

	}

	private void resultDetailsDialog(int row, int col, JTable table1){
		JFrame frame = new JFrame();
		String message = "<html>"
				+ "File: " + table1.getValueAt(row, col) + "<br>"
                + "Score: " + table1.getValueAt(row, col+1)+ "<br>"
                + "Fufilled:" + "<br>";
		ArrayList<ArrayList<String>> fufilled = new ArrayList<ArrayList<String>>();
		fufilled.add(candidateList.get(row).getMatchedExperience());
		fufilled.add(candidateList.get(row).getMatchedQualification());
		fufilled.add(candidateList.get(row).getMatchedLanguage());
		fufilled.add(candidateList.get(row).getMatchedParticulars());
		
		message = buildResultDialogMessage(message, fufilled);
		JOptionPane.showMessageDialog(frame,
				message,
			    "Categories Fufilled",
			    JOptionPane.PLAIN_MESSAGE);
	}
	private String buildResultDialogMessage(String message, ArrayList<ArrayList<String>> fufilled){
		
		String[] heading = {"Experience: ", "Qualification: ", "Language: ", "Particulars: "};
		for(int j=0; j<heading.length; j++){
			message += heading[j];
			System.out.println(heading[j]);
			int listSize = fufilled.get(j).size();
			for(int i=0; i<listSize; i++){
				message += "<br>"+fufilled.get(j).get(i);
				System.out.println(fufilled.get(j).get(i));
			}
			message += "<br>"+"<br>";
		}
		return message;
	}
	
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == returnButton){
			this.dispose();
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

}

