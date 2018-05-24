package glove;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.STS_R1;
import properties.Properties;
import tools.CosineSimilarityCalculation;
import data_structure.AlignmentMatch;
import data_structure.PairSentence;
import data_structure.Sentence;
import edu.stanford.nlp.ling.tokensregex.types.Expressions.IfExpression;
import edu.stanford.nlp.util.ArrayMap;

public class GloveTools {
	public static void main(String[] arg)
	{
		System.out.println("Test");
		double[] w1=GloveTools.searchForVectorInGloveold(Properties.WORD_EMB_PATH, "Woman");
		double[] w2=GloveTools.searchForVectorInGloveold(Properties.WORD_EMB_PATH, "he");
		System.out.println(CosineSimilarityCalculation.cosineSimilarity(w1, w2));
		
		
	}
	public static double[] gloveToDoubleArray(String gloveText)
	{
		String[] words = gloveText.split("\\s+");
		double[] vector=new double [words.length-1];
		for (int i = 1; i < words.length; i++) {
		    // You may want to check for a non-word character before blindly
		    // performing a replacement
		    // It may also be necessary to adjust the character class
		//    words[i] = words[i].replaceAll("[^\\w]", "");
		  //  System.out.println("words:"+words[i]);
			try {
				vector[i-1]=Double.parseDouble(words[i]);	
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(gloveText);
			}
		    
		}
		return vector;
	}
	public static double[] searchForVectorInGloveold(String filePath,String inputWord)
	{
		BufferedReader in=null ;
		String line=null;
		try {
				in  = new BufferedReader(new FileReader(filePath));	
				while ((line=in.readLine())!=null)
				{
					if (line.split(" ")[0].trim().equalsIgnoreCase(inputWord)) {
						return GloveTools.gloveToDoubleArray(line);
				}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		try {
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		
		return null;
	}
	public static void searchForVectorInGloveForList(String filePath,Set<String> inputWords)
	{
		BufferedReader in=null ;
		int count=0;
		//Map<String,double[]> resultMap= new ArrayMap<String, double[]>();
		String line=null;
		for (Iterator iterator = inputWords.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			if(STS_R1.wordVector.keySet().contains(string))
			{
				count++;
			}
		}
		try {
				in  = new BufferedReader(new FileReader(filePath));	
				while ((line=in.readLine())!=null)
				{
					if (inputWords.contains(line.split(" ")[0].trim())&& !STS_R1.wordVector.keySet().contains(line.split(" ")[0].trim())) {
						//return GloveTools.gloveToDoubleArray(line);
							STS_R1.wordVector.put(line.split(" ")[0].trim(), GloveTools.gloveToDoubleArray(line));
							count++;	
								System.out.print(" "+count + "-" +inputWords.size());
							
					}
					if (count>=inputWords.size()) {
						break;
					}
					
				}
		}
			 catch (Exception e) {
				e.printStackTrace();
			}
		try {
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		
		//return resultMap;
	}
	public  static List<AlignmentMatch> createGlovesAlignmentRelationsNotInList(List<AlignmentMatch> inputrelations, PairSentence inputSentences)
	{
		Set<AlignmentMatch> results=new HashSet<AlignmentMatch>();
		Sentence s1=inputSentences.getSentenceOne();
		Sentence s2=inputSentences.getSentenceTwo();
		for (int i = 0; i < s1.getSentenceImpWords().length; i++) {
			String word1=s1.getSentenceImpWords()[i];
			String word1Original=s1.getSentenceAllWords()[i];
			String word1Lemm=s1.getSentenceImpLemm()[i];
			
			String pos1=s1.getSentenceAllPos()[i];
			if (word1.equalsIgnoreCase(Properties.STOP_WORD) || AlignmentMatch.hasRelation(inputrelations, 1, i)) {//means we already find a wordnet relation
				continue;
			}
			//List<String> probMatchWords=WordNetTool.getInstance().getSynonymAndHyperHyproString(word1, pos1.startsWith("V")?"v":pos1.startsWith("A")?"a":pos1.startsWith("R")?"r":"n");
			for (int j = 0; j < s2.getSentenceImpWords().length; j++) {
				String word2=s2.getSentenceImpWords()[j];
				String word2Original=s2.getSentenceAllWords()[j];
				String word2Lemm=s2.getSentenceImpLemm()[j];
				String pos2=s2.getSentenceAllPos()[j];
				results.add(new AlignmentMatch(i,j,word1Original,word2Original,CosineSimilarityCalculation.cosineSimilarity(STS_R1.wordVector.get(word1Original), STS_R1.wordVector.get(word2Original)) ));
			}
		}
		
		for (int j = 0; j < s2.getSentenceImpWords().length; j++) {
			String word2=s2.getSentenceImpWords()[j];
			String word2Original=s2.getSentenceAllWords()[j];
			String word2Lemm=s2.getSentenceImpLemm()[j];
			
			String pos2=s2.getSentenceAllPos()[j];
			if (word2.equalsIgnoreCase(Properties.STOP_WORD) || AlignmentMatch.hasRelation(inputrelations, 2, j)) {//means we already find a wordnet relation
				continue;
			}
			//List<String> probMatchWords=WordNetTool.getInstance().getSynonymAndHyperHyproString(word1, pos1.startsWith("V")?"v":pos1.startsWith("A")?"a":pos1.startsWith("R")?"r":"n");
			for (int i = 0; i < s1.getSentenceImpWords().length; i++) {
				String word1=s1.getSentenceImpWords()[i];
				String word1Original=s1.getSentenceAllWords()[i];
				String word1Lemm=s1.getSentenceImpLemm()[i];
				String pos1=s1.getSentenceAllPos()[i];
				results.add(new AlignmentMatch(i,j,word1Original,word2Original,CosineSimilarityCalculation.cosineSimilarity(STS_R1.wordVector.get(word1Original), STS_R1.wordVector.get(word2Original)) ));
			}
		}
		List<AlignmentMatch> finalResults=new ArrayList<AlignmentMatch>();
		finalResults.addAll(results);
		Collections.sort(finalResults);
		return finalResults;
	}
	
	public  static List<AlignmentMatch> findExactAlignmentRelations(PairSentence inputSentences)
	{
		List<AlignmentMatch> results=new ArrayList<AlignmentMatch>();
		Sentence s1=inputSentences.getSentenceOne();
		Sentence s2=inputSentences.getSentenceTwo();
		for (int i = 0; i < s1.getSentenceImpWords().length; i++) {
			String word1=s1.getSentenceAllWords()[i];
			String word1Lemm=s1.getSentenceImpLemm()[i];
			String pos1=s1.getSentenceAllPos()[i];
			/*if (word1.equalsIgnoreCase(Properties.STOP_WORD) ) {
				continue;
			}*/
			//List<String> probMatchWords=WordNetTool.getInstance().getSynonymAndHyperHyproString(word1, pos1.startsWith("V")?"v":pos1.startsWith("A")?"a":pos1.startsWith("R")?"r":"n");
			for (int j = 0; j < s2.getSentenceImpWords().length; j++) {
				String word2=s2.getSentenceAllWords()[j];
				String word2Lemm=s2.getSentenceImpLemm()[j];
				String pos2=s2.getSentenceAllPos()[j];
				if (word1Lemm.equalsIgnoreCase(word2Lemm)&& pos1.equals(pos2)) {
					results.add(new AlignmentMatch(i,j,word1,word2,1.0D));	
				}
				
			}
		}
		return results;
	}
}
