package wordnet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import properties.Properties;
import data_structure.AlignmentMatch;
import data_structure.PairSentence;
import data_structure.Sentence;
import stop_words.StopWords;
import edu.cmu.lti.jawjaw.db.SynlinkDAO;
import edu.cmu.lti.jawjaw.pobj.Link;
import edu.cmu.lti.jawjaw.pobj.POS; 
import edu.cmu.lti.jawjaw.pobj.Synlink;
import edu.cmu.lti.jawjaw.pobj.Synset;
import edu.cmu.lti.jawjaw.pobj.Word;
import edu.cmu.lti.jawjaw.util.WordNetUtil;
import edu.cmu.lti.lexical_db.ILexicalDatabase; 
import edu.cmu.lti.lexical_db.NictWordNet; 
import edu.cmu.lti.lexical_db.data.Concept; 
import edu.cmu.lti.ws4j.Relatedness; 
import edu.cmu.lti.ws4j.RelatednessCalculator; 
import edu.cmu.lti.ws4j.impl.HirstStOnge; 
import edu.cmu.lti.ws4j.impl.JiangConrath; 
import edu.cmu.lti.ws4j.impl.LeacockChodorow; 
import edu.cmu.lti.ws4j.impl.Lesk; 
import edu.cmu.lti.ws4j.impl.Lin; 
import edu.cmu.lti.ws4j.impl.Path; 
import edu.cmu.lti.ws4j.impl.Resnik; 
import edu.cmu.lti.ws4j.impl.WuPalmer; 
import edu.cmu.lti.ws4j.util.WS4JConfiguration; 

public class WordNetTool {

	

	 
	 private static ILexicalDatabase db = new NictWordNet(); 
	    private static RelatednessCalculator lin = new Lin(db); 
	    private static RelatednessCalculator wup = new WuPalmer(db); 
	    private static RelatednessCalculator path = new Path(db); 
	     
	    private static RelatednessCalculator lea = new LeacockChodorow(db); 
	    private static RelatednessCalculator hirst = new HirstStOnge(db); 
	    private static RelatednessCalculator lesk = new Lesk(db); 
	    private static RelatednessCalculator resnik = new Lesk(db); 
	    private static RelatednessCalculator jiang = new JiangConrath(db); 
	   
	    
	    private static WordNetTool instance = null;
		public static WordNetTool getInstance() {
		      if(instance == null) {
		         instance = new WordNetTool();
		      }
		      return instance;
		   }
		
	    
	   
	     private static RelatednessCalculator[] rcs = { 
	                     new HirstStOnge(db), new LeacockChodorow(db), new Lesk(db),  new WuPalmer(db),  
	                     new Resnik(db), new JiangConrath(db), new Lin(db), new Path(db) 
	                     }; 
	      
	     private static void run( String word1, String word2 ) { 
	             WS4JConfiguration.getInstance().setMFS(true); 
	             for ( RelatednessCalculator rc : rcs ) { 
	                     double s = rc.calcRelatednessOfWords(word1, word2); 
	                     System.out.println( rc.getClass().getName()+"\t"+s ); 
	             } 
	     } 
	      
	      
	     private static void runConcept(String word1, String word2){ 
	      ILexicalDatabase db = new NictWordNet(); 
	      WS4JConfiguration.getInstance().setMFS(true); 
	      RelatednessCalculator rc = new WuPalmer(db); 
	     
	      List<POS[]> posPairs = rc.getPOSPairs(); 
	      double maxScore = -1D; 
	 
	      for(POS[] posPair: posPairs) { 
	          List<Concept> synsets1 = (List<Concept>)db.getAllConcepts(word1, posPair[0].toString()); 
	          List<Concept> synsets2 = (List<Concept>)db.getAllConcepts(word2, posPair[1].toString()); 
	 
	          for(Concept synset1: synsets1) { 
	              for (Concept synset2: synsets2) { 
	                  Relatedness relatedness = rc.calcRelatednessOfSynset(synset1, synset2); 
	                  double score = relatedness.getScore(); 
	                  if (score > maxScore) {  
	                      maxScore = score; 
	                  } 
	              } 
	          } 
	      } 
	 
	      if (maxScore == -1D) { 
	          maxScore = 0.0; 
	      } 
	 
	      System.out.println("sim('" + word1 + "', '" + word2 + "') =  " + maxScore); 
	     } 
	      
	      
/*	     public static void main(String[] args) { 
	             long t0 = System.currentTimeMillis(); 
//	             run( "add","add" ); 
	              
//	             String w1 = "add"; 
//	             String w2 = "add"; 
//	             System.out.println(lin.calcRelatednessOfWords(w1, w2)); 
//	             System.out.println(wup.calcRelatednessOfWords(w1, w2)); 
//	             System.out.println(path.calcRelatednessOfWords(w1, w2)); 
//	              
//	             System.out.println(lea.calcRelatednessOfWords(w1, w2)); 
//	             System.out.println(hirst.calcRelatednessOfWords(w1, w2)); 
//	             System.out.println(lesk.calcRelatednessOfWords(w1, w2)); 
//	             System.out.println(resnik.calcRelatednessOfWords(w1, w2)); 
//	             System.out.println(jiang.calcRelatednessOfWords(w1, w2)); 
	              
	                       
	              
	          //   runConcept("Man", "Woman");
	            String word1="Home";
	            String word2="House";
	             
	            System.out.println("WuPalmer "+wup.calcRelatednessOfWords(word1, word2));
	            System.out.println("lin "+lin.calcRelatednessOfWords(word1, word2));
	            System.out.println("path "+path.calcRelatednessOfWords(word1, word2));
	            System.out.println("LeacockChodorow "+lea.calcRelatednessOfWords(word1, word2));
	            System.out.println("lesk "+lesk.calcRelatednessOfWords(word1, word2));
	            List<Synset> synsets = WordNetUtil.wordToSynsets("home", POS.n);
	            for (int i = 0; i < synsets.size(); i++) {
					System.out.println(WordNetUtil.synsetToWords(synsets.get(i).getSynset()));
				}
	            //wup.calcRelatednessOfSynset(home#n#8 , house#n#10);
	            System.out.println(getSynonymString("boy", "n"));
	            System.out.println(getAllHyperHypoWords("boy", "n"));
	              
	             //long t1 = System.currentTimeMillis(); 
	            // System.out.println( "Done in "+(t1-t0)+" msec." ); 
	              
	     } 
*/	      
	    public static List <String> getHypernyms(String synset) {
	 		List<Synlink> links = SynlinkDAO.findSynlinksBySynsetAndLink(synset, Link.hype);
	 		List<String> hypernyms = new ArrayList<String>();
	 		for ( Synlink link : links ) {
	 			hypernyms.add( link.getSynset2() );
	 		}
	 		return hypernyms;
	 	}
	    public static List <String> getHyponyms(String synset) {
	 		List<Synlink> links = SynlinkDAO.findSynlinksBySynsetAndLink(synset, Link.hypo);
	 		List<String> hypernyms = new ArrayList<String>();
	 		for ( Synlink link : links ) {
	 			hypernyms.add( link.getSynset2() );
	 		}
	 		return hypernyms;
	 	}
     
	      public static List<Word> getSynonymWords(String input, String pos)
	      {
	    	  List<Word> results=new ArrayList<Word>();
	    	  List<Synset> synsets = WordNetUtil.wordToSynsets(input,POS.valueOf(pos));
	    	  for (int i = 0; i < synsets.size(); i++) {
	    		  results.addAll( WordNetUtil.synsetToWords(synsets.get(i).getSynset()));
	    		  //System.out.println(WordNetUtil.synsetToWords(synsets.get(i).getSynset()));
			}
	    	  return results; 
	      }
	      public static List<String> getSynonymAndHyperHyproString(String input, String pos, String lang)
	      {
	    	  List<String> results=getSynonymString(input, pos,lang);
	    	  List<String> results2=getAllHyperHypoWords(input, pos,lang);
	    	  Set<String> setResults=new HashSet<String>();
	    	  for (int i = 0; i < results.size(); i++) {
	    		  setResults.add(results.get(i));
	    	  }
	    	  for (int i = 0; i < results2.size(); i++) {
	    		  setResults.add(results2.get(i));
	    	  }
	    	  List<String>finalResults= new ArrayList<String>();
	    	  finalResults.addAll(setResults);
	    	  return finalResults;
	      }
	      public static List<String> getSynonymString(String input, String pos, String lang)
	      {
	    	  Set<String> results=new HashSet<String>();
	    	  List<Synset> synsets = WordNetUtil.wordToSynsets(input,POS.valueOf(pos));
	    	  for (int i = 0; i < synsets.size(); i++) {
	    		  List<Word> words=WordNetUtil.synsetToWords(synsets.get(i).getSynset());
	    		  for (int j = 0; j < words.size(); j++) {
	    			  if (words.get(j).getLang().toString().equalsIgnoreCase(lang)) {
	    				  results.add(words.get(j).getLemma());
					}
	    		}
	    	}
	    	  List<String> finalResults=new ArrayList<String>();
	    	  finalResults.addAll(results);
	    	  return finalResults; 
	      }
	     
	      public static List<String> getAllHyperHypoWords(String input, String pos, String lang)
	      {
	    	  Set<String> results=new HashSet<String>();
	    	  List<Word> words=null;
	    	  List<Synset> synsets = WordNetUtil.wordToSynsets(input,POS.valueOf(pos));
	    	  for (int i = 0; i < synsets.size(); i++) {
	    		  List<String> hypoSynset=getHyponyms(synsets.get(i).getSynset());
	    		  List<String> hyperSynset=getHypernyms(synsets.get(i).getSynset());
	    		  for (int j = 0; j < hypoSynset.size(); j++) {
	    			  words=WordNetUtil.synsetToWords(hypoSynset.get(j));
	    			  for (int t = 0; t < words.size(); t++) {
	    				  if (words.get(t).getLang().toString().equalsIgnoreCase(lang)) {
	    					  results.add(words.get(t).getLemma());
	    				  }
					}
	    		  }
	    		  for (int j = 0; j < hyperSynset.size(); j++) {
	    			  words=WordNetUtil.synsetToWords(hyperSynset.get(j));
	    			  for (int t = 0; t < words.size(); t++) {
	    				  if (words.get(t).getLang().toString().equalsIgnoreCase(lang)) {
	    					  results.add(words.get(t).getLemma());
	    				  }
					}
	    		  }
	    		  
	    	  }
	    	  
	    	  List<String> finalResults=new ArrayList<String>();
	    	  finalResults.addAll(results);
	    	  return finalResults; 
	      }
	      
	      private List<POS> getWordNetPos()
	      {
	    	  List<POS> results=new ArrayList<POS>();
	    	  results.add(POS.n);
	    	  results.add(POS.v);
	    	  results.add(POS.a);
	    	  results.add(POS.r);
	    	  
	    	  return results;
	      }
	      public static List<AlignmentMatch> findWordNetAlignmentRelations(PairSentence inputSentences)
	  	{
	  		List<AlignmentMatch> results=new ArrayList<AlignmentMatch>();
	  		List<String> probMatchWordsSyn=new ArrayList<String>();
	  		List<String> probMatchWordsHyperHypo=new ArrayList<String>();
	  		Sentence s1=inputSentences.getSentenceOne();
	  		Sentence s2=inputSentences.getSentenceTwo();
	  		for (int i = 0; i < s1.getSentenceImpWords().length; i++) {
	  			String word1=s1.getSentenceImpWords()[i];
	  			String word1Original=s1.getSentenceAllWords()[i];
	  			String word1Lemm=s1.getSentenceImpLemm()[i];
	  			
	  			String pos1=s1.getSentenceAllPos()[i];
	  			
	  			if (word1.equalsIgnoreCase(Properties.STOP_WORD)) {
	  				continue;
	  			}
	  			if (Properties.USE_WORD_NET_HYPERNOM) {
	  				probMatchWordsSyn=WordNetTool.getInstance().getSynonymString(word1Lemm, pos1.startsWith("V")?"v":pos1.startsWith("A")?"a":pos1.startsWith("R")?"r":"n",s1.getSentenceLang());
	  				probMatchWordsHyperHypo=WordNetTool.getInstance().getAllHyperHypoWords(word1Lemm, pos1.startsWith("V")?"v":pos1.startsWith("A")?"a":pos1.startsWith("R")?"r":"n",s1.getSentenceLang());
	  				//probMatchWords=WordNetTool.getInstance().getSynonymAndHyperHyproString(word1Lemm, pos1.startsWith("V")?"v":pos1.startsWith("A")?"a":pos1.startsWith("R")?"r":"n",s1.getSentenceLang());
		  		}
	  			else
	  			{
	  				probMatchWordsSyn=WordNetTool.getInstance().getSynonymString(word1Original, pos1.startsWith("V")?"v":pos1.startsWith("A")?"a":pos1.startsWith("R")?"r":"n",s1.getSentenceLang());
	  			}
	  			for (int j = 0; j < s2.getSentenceImpWords().length; j++) {
	  				String word2=s2.getSentenceImpWords()[j];
	  				String word2Original=s2.getSentenceAllWords()[j];
	  				String word2Lemm=s2.getSentenceImpLemm()[j];
	  				String pos2=s2.getSentenceAllPos()[j];
	  				if (probMatchWordsSyn.contains(word2Lemm)||probMatchWordsSyn.contains(word2Original)) {
	  					results.add(new AlignmentMatch(i,j,word1Original,word2Original,Properties.WORD_NET_SYNONYM_STRENGTH));
	  				}
	  				else if (probMatchWordsHyperHypo.contains(word2Lemm)||probMatchWordsHyperHypo.contains(word2Original)) {
	  					results.add(new AlignmentMatch(i,j,word1Original,word2Original,Properties.WORD_NET_HYPERNOM_STRENGTH));
	  				}
	  			}
	  		}
	  		return results;
	  	}
	      public static void main(String[] arg)
	      {
	    	  BufferedReader br;
	    	  try {
	    		  br = new BufferedReader(new FileReader("file.txt"));
	    	      StringBuilder sb = new StringBuilder();
	    	      String line = br.readLine();

	    	      while (line != null) {
	    	    	  String[] strings=line.split(",");
	    	    	  System.out.println(getSynonymAndHyperHyproString(strings[0].trim(), strings[1].trim(),"eng"));
	    	    	    
	    	    	  line = br.readLine();
	    	      }
	    	      String everything = sb.toString();
	    	  }
	    	  catch(Exception e)
	    	  {
	    		  e.printStackTrace();
	    	  }
	    	  finally {
	    	      
	    	  }
	    	  
	    	  //System.out.println(getSynonymString("vitrine", edu.cmu.lti.jawjaw.pobj.POS.n.toString(),"eng"));
	    	  //System.out.println(getSynonymAndHyperHyproString("vitrine", edu.cmu.lti.jawjaw.pobj.POS.n.toString(),"eng"));
	    	  
	    	  //System.out.println(getSynonymString("clean", edu.cmu.lti.jawjaw.pobj.POS.v.toString(),"eng"));
	    	  //System.out.println(getSynonymAndHyperHyproString("clean", edu.cmu.lti.jawjaw.pobj.POS.v.toString(),"eng"));
	      }
	      
}
