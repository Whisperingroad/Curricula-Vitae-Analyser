package extractor;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

//import PDFTextParser.PDFTextParser;
//import WordDocExtractor.WordDocumentExtractor;

public class TextExtractor {
	static String path = "D:\\3219\\Curricula-Vitae-Analyzers\\Curricula-Vitae-Analyzers\\src\\Input\\";
	static String filePostfix = "";
	private static String text = "";
	private enum Type {pdf, doc, docx, txt, none};
	
	public TextExtractor(){}
	
	private static Type checkFileType(File file) throws IOException{
		String fileType = Files.probeContentType(file.toPath());
		System.out.println("Document type is " + fileType);
		Type type;
		filePostfix = "";
		if(fileType.contains("pdf")){
			type = Type.pdf;
			filePostfix = ".pdf";
		}
		else if(fileType.contains("wordprocessingml.document")){
			type = Type.docx;
			filePostfix = ".docx";
		}
		else if(fileType.contains("msword")){
			type = Type.doc;
			filePostfix = ".doc";
		}
		else if(fileType.contains("text/plain")){
			type = Type.txt;
			filePostfix = ".txt";
		}
		else{
			type = Type.none;
			filePostfix = "none";
		}
		return type;
		
	}
	
	private static void callParser(Type type, String file){
		text="";
		switch(type){
		case pdf:
			text = PDFTextParser.PDFParser(file);
			break;
		case doc:
			text = WordDocumentExtractor.extractDocText(file);
			break;
		case docx:
			text = WordDocumentExtractor.extractDocxText(file);
			break;
		case txt:
			text = "txt file";
		default:
			break;
		}
	}
	
	public static String getFilePostfix(){
		return filePostfix;
	}
	
	public static String getFileContent(){
		return text;
	}
	
	public static Boolean execute(File file) throws IOException
	{
			if(file.exists() && !file.isDirectory()) { 
				Type fileType = checkFileType(file);
				System.out.println("Document type is " + fileType);
				String fileName = file.getName();
				System.out.println("File Name is " + fileName);
				callParser(fileType, file.toString());
				return true;
			}
			else
				return false;
	}
}
