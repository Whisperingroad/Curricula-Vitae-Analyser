package Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
		//Properties props = new Properties();
		//props.put("annotators", "tokenize, ssplit, pos, parse, lemma");
		//StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		ArrayList<String> lemmas = new ArrayList<String>();
		//String text = "program programs programming programmed";
		for(String s:textInput)
		{
			// create an empty Annotation just with the given text
			Annotation annotation = new Annotation(s);
			// run annotator on this string
			pipeline.annotate(annotation);
			// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
			// getting the sentences contained by an annotation
			List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

			for(CoreMap sentence: sentences) {
				String line = "";
				// Iterate over all tokens in a sentence					
				for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
					// Retrieve and add the lemma for each word into the list of lemmas
					//lemmas.add(token.get(LemmaAnnotation.class));
					line+= token.get(LemmaAnnotation.class) + " ";
				}
				line.trim();
				System.out.println(line);
				lemmas.add(line);
			}
		}

		return lemmas;
	}
}