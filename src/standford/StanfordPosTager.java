package standford;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PositionAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.POSTaggerAnnotator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.CoreMap;


public class StanfordPosTager {
protected StanfordCoreNLP pipeline;
private static StanfordPosTager instance = null;
public static StanfordPosTager getInstance() {
      if(instance == null) {
         instance = new StanfordPosTager();
      }
      return instance;
   }

private StanfordPosTager() {
	// Create StanfordCoreNLP object properties, with POS tagging
    // (required for lemmatization), and lemmatization
    Properties props;
    props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos, lemma");
   
    this.pipeline = new StanfordCoreNLP(props);
}
public String tagWord(String documentText)
{
    String tag="";// = new LinkedList<String>();
    // Create an empty Annotation just with the given text
    Annotation document = new Annotation(documentText);
    // run all Annotators on this text
    this.pipeline.annotate(document);
    // Iterate over all of the sentences found
    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
    for(CoreMap sentence: sentences) {
        // Iterate over all tokens in a sentence
        for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
            // Retrieve and add the lemma for each word into the
            // list of lemmas
            return token.tag();
        }
    }
    return tag;
}
public List<String> tag(String documentText)
{
    List<String> tags = new LinkedList<String>();
    // Create an empty Annotation just with the given text
    Annotation document = new Annotation(documentText);
    // run all Annotators on this text
    this.pipeline.annotate(document);
    // Iterate over all of the sentences found
    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
    for(CoreMap sentence: sentences) {
        // Iterate over all tokens in a sentence
        for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
            // Retrieve and add the lemma for each word into the
            // list of lemmas
            tags.add(token.tag());//
        	//tags.add(token.get(LemmaAnnotation.class));
        }
    }
    return tags;
}

public static void main(String[] args) {
    System.out.println("Starting Stanford Lemmatizer");
    String text = "How could you be seeing into my eyes like open doors? \n"+
            "You led me down into my core where I've became so numb \n"+
            "Without a soul my spirit's sleeping somewhere cold \n"+
            "Until you find it there and led it back home \n"+
            "You woke me up inside \n"+
            "Called my name and saved me from the dark \n"+
            "You have bidden my blood and it ran \n"+
            "Before I would become undone \n"+
            "You saved me from the nothing I've almost become \n"+
            "You were bringing me to life \n"+
            "Now that I knew what I'm without \n"+
            "You can've just left me \n"+
            "You breathed into me and made me real \n"+
            "Frozen inside without your touch \n"+
            "Without your love, darling \n"+
            "Only you are the life among the dead \n"+
            "I've been living a lie, there's nothing inside \n"+
            "You were bringing me to life.";
    StanfordPosTager slem = new StanfordPosTager();
   // System.out.println(slem.lemmatize(text));
    StringTokenizer idata;
	
	idata=new StringTokenizer(text);
	while (idata.hasMoreTokens()) {
		String str= idata.nextToken();
		System.out.println(str+"-"+slem.tag(str));
	}
	System.out.println(slem.tag(text));
	
	
}

/*public static void main(String[] args) throws ClassNotFoundException {
	
	// Initialize the tagger
	MaxentTagger tagger = new MaxentTagger(
			"C:\\Rekaby\\workspaces\\PHD\\SEMEVAL_STS_2017\\src\\models\\left3words-wsj-0-18.tagger");

	// The sample string
	String sample = "This is a sample text";
	
	// The tagged string
	String tagged = tagger.tagString(sample);
	
	// 	Output the result
	System.out.println(tagged);
}*/


}