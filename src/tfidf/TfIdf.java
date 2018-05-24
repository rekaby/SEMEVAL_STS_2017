package tfidf;

import java.util.List;

import data_structure.Sentence;
import standford.StanfordLemmatizer;

public class TfIdf {
	private static TfIdf instance = null;
	public static TfIdf getInstance() {
	      if(instance == null) {
	    	  instance = new TfIdf();
	      }
	      return instance;
	   }
	
    public double tfCalculator(Sentence sentences, String termToCheck) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
       
        //for (int i = 0; i < allSentences.size(); i++) {
        	 for (String s : sentences.getSentenceAllWords()) {
                 if (s.equalsIgnoreCase(termToCheck)) {
                     count++;
                 }
                
             }
		//}
        return count / sentences.getSentenceAllWords().length;
    }
    public double tfCalculatorLemm(Sentence sentences, String termToCheck) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
       
        //for (int i = 0; i < allSentences.size(); i++) {
        	 for (String s : sentences.getSentenceImpLemm()) {
                 if (s.equalsIgnoreCase(termToCheck)) {
                     count++;
                 }
                
             }
		//}
        return count / sentences.getSentenceImpLemm().length;
    }


    public double idfCalculator(List<Sentence> allSentences, String termToCheck) {
        double count = 0;
       for (int i = 0; i < allSentences.size(); i++) {
		   for (String s : allSentences.get(i).getSentenceAllWords()) {
                if (s.equalsIgnoreCase(termToCheck)) {
                    count++;
                    break;
                }
            }
        }
        return 1 + Math.log(allSentences.size() / count);
    }
    public double idfCalculatorLemm(List<Sentence> allSentences, String termToCheck) {
        double count = 0;
       for (int i = 0; i < allSentences.size(); i++) {
		   for (String s : allSentences.get(i).getSentenceImpLemm()) {
                if (s.equalsIgnoreCase(termToCheck)) {
                    count++;
                    break;
                }
            }
        }
        return 1 + Math.log(allSentences.size() / count);
    }
}
