package ui;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.Controller;

/**
 * Created by workshop on 9/18/2015.
 */
public class ui extends JFrame implements ActionListener{

    JPanel contentPane;
    JButton readButton, startButton;
    JTextField textField;
    JTextArea textArea;
    JTable table;
    DefaultTableModel model;

    int resultSize = 20;

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

        JPanel queryPanel = new JPanel();
        queryPanel.add(jScrollPanelTxtArea);
        queryPanel.add(readButton);
        queryPanel.add(startButton);

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

        contentPane.setVisible(true);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == readButton){
            String jobDescription = textArea.getText();
            System.out.println(jobDescription);

        }else if (e.getSource() == startButton){
        	removeRows();
        	clearList();

        	Controller controller = new Controller();
        	HashMap<String,Double> resultList = new HashMap<String,Double>();
        	try {
				resultList = controller.startProcessing();
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
        	String data1 = null;
        	double data2 = 0.0;
        	
        	for (Map.Entry f: resultList.entrySet()){
                data1 = (String) f.getKey();
                data2 = (double) f.getValue();
                model.addRow( new Object[] { data1, data2 } );
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

    public static void main(String[] args) {
    	new ui();
    }
}

