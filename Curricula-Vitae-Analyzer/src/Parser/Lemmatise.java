package Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.*;
public class Lemmatise {
	Properties props;
	StanfordCoreNLP pipeline;

	public Lemmatise(){
		props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, parse, lemma");
		pipeline = new StanfordCoreNLP(props);
	}

	public ArrayList<String> lemmatiser(ArrayList<String> textInput){
		ArrayList<String> sanitisedInput = new ArrayList<String>();
		for(String s:textInput){
			//String words[] = s.split(" ");
			//String line = "";
			//for (String w:words){
			//remove all brackets
			s = s.replaceAll("[()]","");
			s = s.replaceAll("\\[","").replaceAll("\\]","");
			s = s.replaceAll("[{}]","");
			//line += w + " ";
			//}
			//s.trim();
			sanitisedInput.add(s);
		}
		ArrayList<String> lemmas = new ArrayList<String>();
		//a sentence
		for(String s:sanitisedInput)
		{
			String line = "";
			// create an empty Annotation just with the given text
			Annotation annotation = new Annotation(s);
			// run annotator on this string
			pipeline.annotate(annotation);
			List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
			for(CoreMap sentence: sentences) {				
				// Iterate over all tokens in a sentence
				for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
					// Retrieve and add the lemma for each word into the list of lemmas
					String str = token.get(LemmaAnnotation.class);
					if (Pattern.matches("\\p{Punct}", str)) {
						line = line.trim() + str;
					}
					
					else{
					line = line + str + " ";
					}
				}
				
			}
			line.trim();
			lemmas.add(line);
		}

		return lemmas;
}
}

