package extractor;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;

import utils.Constants;

public class PDFTextParser {
	PDFParser parser;
	String parsedText;
	PDFTextStripper pdfStripper;
	PDDocument pdDoc;
	COSDocument cosDoc;
	PDDocumentInformation pdDocInfo;
	String path = Constants.YIXIU + "Input/";
	String postFix = ".pdf";

	// PDFTextParser Constructor 
	public PDFTextParser() 
	{
		
	}

	// Extract text from PDF Document
	public String pdftoText(String fileName) {

		System.out.println("Parsing text from PDF file " + fileName + "....");
		String inputFile = path + fileName + postFix;
		File f = new File(inputFile);

		if (!f.isFile()) {
			System.out.println("File " + inputFile + " does not exist.");
			return null;
		}

		try {
			parser = new PDFParser(new FileInputStream(f));
		} catch (Exception e) {
			System.out.println("Unable to open PDF Parser.");
			return null;
		}

		try {
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc); 
		} catch (Exception e) {
			System.out.println("An exception occured in parsing the PDF Document.");
			e.printStackTrace();
			try {
				if (cosDoc != null) cosDoc.close();
				if (pdDoc != null) pdDoc.close();
			} catch (Exception e1) {
				e.printStackTrace();
			}
			return null;
		}      
		System.out.println("Done.");
		return parsedText;
	}
}
