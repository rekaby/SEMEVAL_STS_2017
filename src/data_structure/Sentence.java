package data_structure;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import glove.GloveTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.StyledEditorKit.BoldAction;

import properties.Properties;
import standford.StanfordLemmatizer;
import standford.StanfordPosTager;
import stop_words.StopWords;

public class Sentence {

	private String sentenceString;
//	private String sentenceStringTranslated;
	private Boolean loaded;
	private String sentenceLang;
	
	private String[] sentenceAllWords;//just split the String input
	
	private String[] sentenceAllPos;//just split the String input
	
	private String[] sentenceImpWords;//after replace all stop words with a template work
	private String[] sentenceImpLemm;
	//public static Map<String, double[]> wordVector= new HashMap<String, double []>();
	private List<DependencyRelation> dependencyRelations=new ArrayList<DependencyRelation>();
	
	public List<DependencyRelation> getDependencyRelations() {
		return dependencyRelations;
	}
	public void setDependencyRelations(List<DependencyRelation> dependencyRelations) {
		this.dependencyRelations = dependencyRelations;
	}
	public Sentence(String sentenceString, String sentenceLang,Boolean load) {
		super();
		this.sentenceString = sentenceString;
		this.sentenceLang=sentenceLang;
		this.loaded=load;
		loadAllSentenceInformationNew();
	}
	public void loadAllSentenceInformationNew()
	{
		StanfordLemmatizer slem = StanfordLemmatizer.getInstance();
		StanfordPosTager sTagger = StanfordPosTager.getInstance();
		/*if (sentenceLang.equals("ENG")) {
			//TODO check translation or what
			sentenceStringTranslated=sentenceString;
		}*/
		//sentenceAllWords=slem.toknize(sentenceString);//   sentenceString.split("[	.,;:\"\\s\\(\\)]+");//("\\s+");
		sentenceAllWords=  sentenceString.split(" ");
		
		sentenceImpLemm=new String[sentenceAllWords.length];
		sentenceAllPos=new String[sentenceAllWords.length];
		
		//wordvector section
		for (int i = 0; i < sentenceAllWords.length; i++) {
			sentenceImpLemm[i]=slem.lemmatizeWord(sentenceAllWords[i]);
			/*if (!wordVector.containsKey(sentenceAllWords[i])) {
					//System.out.println("Word:"+sentenceAllWords[i]);
					wordVector.put (sentenceAllWords[i],GloveTools.searchForVectorInGlove(Properties.WORD_EMB_PATH, sentenceAllWords[i]));
			}*/
			sentenceAllPos[i]=sTagger.tagWord(sentenceImpLemm[i]);
		}
		sentenceImpWords=StopWords.getInstance().getNonStopWords(sentenceAllWords);
	}
	private void loadAllSentenceInformation(Boolean load)
	{
		StanfordLemmatizer slem = StanfordLemmatizer.getInstance();
		StanfordPosTager sTagger = StanfordPosTager.getInstance();
		/*if (sentenceLang.equals("ENG")) {
			//TODO check translation or what
			sentenceStringTranslated=sentenceString;
		}*/
		sentenceAllWords=slem.toknize(sentenceString);//   sentenceString.split("[	.,;:\"\\s\\(\\)]+");//("\\s+");
		
		sentenceImpLemm=new String[sentenceAllWords.length];
		sentenceAllPos=new String[sentenceAllWords.length];
		
		for (int i = 0; i < sentenceAllWords.length; i++) {
			sentenceImpLemm[i]=slem.lemmatizeWord(sentenceAllWords[i]);
			if (load) {
				//if (!wordVector.containsKey(sentenceAllWords[i])) {
				//	wordVector.put (sentenceAllWords[i],GloveTools.searchForVectorInGlove(Properties.WORD_EMB_PATH, sentenceAllWords[i]));
				//}
			}
			sentenceAllPos[i]=sTagger.tagWord(sentenceImpLemm[i]);
		}
		sentenceImpWords=StopWords.getInstance().getNonStopWords(sentenceAllWords);
	}
	
	public String[] getSentenceAllPos() {
		return sentenceAllPos;
	}
	public void setSentenceAllPos(String[] sentenceAllPos) {
		this.sentenceAllPos = sentenceAllPos;
	}
	public String getSentenceString() {
		return sentenceString;
	}
	public void setSentenceString(String sentenceString) {
		this.sentenceString = sentenceString;
	}
	public String getSentenceLang() {
		return sentenceLang;
	}
	public void setSentenceLang(String sentenceLang) {
		this.sentenceLang = sentenceLang;
	}
	public String[] getSentenceAllWords() {
		return sentenceAllWords;
	}
	public void setSentenceAllWords(String[] sentenceAllWords) {
		this.sentenceAllWords = sentenceAllWords;
	}
	public String[] getSentenceImpWords() {
		return sentenceImpWords;
	}
	public void setSentenceImpWords(String[] sentenceImpWords) {
		this.sentenceImpWords = sentenceImpWords;
	}
	public String[] getSentenceImpLemm() {
		return sentenceImpLemm;
	}
	public void setSentenceImpLemm(String[] sentenceImpLemm) {
		this.sentenceImpLemm = sentenceImpLemm;
	}
	/*public Map<String, double[]> getWordVector() {
		return wordVector;
	}
	public void setWordVector(Map<String, double[]> wordVector) {
		this.wordVector = wordVector;
	}*/
	/*public String getSentenceStringTranslated() {
		return sentenceStringTranslated;
	}
	public void setSentenceStringTranslated(String sentenceStringTranslated) {
		this.sentenceStringTranslated = sentenceStringTranslated;
	}*/
	public int getNonStopWordCount()
	{
		int count=0;
		for (int i = 0; i < sentenceImpWords.length; i++) {
			if (!sentenceImpWords[i].equals(Properties.STOP_WORD)) {
				count++;
			}
		}
		return count;
	}
	public Boolean getLoaded() {
		return loaded;
	}
	public void setLoaded(Boolean loaded) {
		this.loaded = loaded;
	}
	
	
}
