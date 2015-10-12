package extractor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hwpf.extractor.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class WordDocumentExtractor 
{
	public WordDocumentExtractor(){
	}
	/*
	// Write the parsed text from Word to a file
	void writeTexttoFile(String wordText, String fileName) {

		System.out.println("\nWriting Word text to output text file " + fileName + "....");
		try {
			PrintWriter pw = new PrintWriter(fileName);
			pw.print(wordText);
			pw.close();    	
		} catch (Exception e) {
			System.out.println("An exception occured in writing the Word text to file.");
			e.printStackTrace();
		}
		System.out.println("Done.");
	}*/

	private static String formatting(String text){
		StringBuilder textString = new StringBuilder();
		textString.append(text);
		for(int i=0; i<textString.length(); i++){
			int b = (int)textString.charAt(i);
			if(b>127||(b<32 && b!=10)){
				textString.replace(i, i+1, " ");
			}
		}
		text = textString.toString();
		text = text.replaceAll("^ +| +$| (?= )", "");
		return text;
	}
	
	public static String extractDocText(String inputFile){
		String text = "";
		try {
			HWPFDocument docx = new HWPFDocument(new FileInputStream(inputFile));

			//using HWPFWordExtractor Class
			WordExtractor we = new WordExtractor(docx);
			System.out.println(we.getText());
			text = we.getText();
			text = formatting(text);
			System.out.println(text);
			//extractor.writeTexttoFile(text, outputFile);
			we.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}

	public static String extractDocxText(String inputFile){
		String text = "";
		try {
			XWPFDocument docx = new XWPFDocument(
					new FileInputStream(inputFile));
			//using XWPFWordExtractor Class
			XWPFWordExtractor we = new XWPFWordExtractor(docx);
			
			text = we.getText();
			text = formatting(text);
			System.out.println(text);
			//extractor.writeTexttoFile(text, outputFile);
			we.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
}