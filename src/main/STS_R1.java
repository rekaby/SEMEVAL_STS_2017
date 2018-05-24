package main;
import data_structure.AlignmentMatch;
import data_structure.DependencyRelation;
import data_structure.PairSentence;
import data_structure.Sentence;
import edu.cmu.lti.jawjaw.pobj.POS;
import edu.stanford.nlp.util.Pair;
import glove.GloveTools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import properties.Properties;
import tfidf.TfIdf;
import tools.CosineSimilarityCalculation;
import tools.DependencyReader;
import tools.STSReader;
import wordnet.WordNetTool;



public class STS_R1 {

	//static Map<String, Double> tfMap=new HashMap<String, Double>();
	static Map<String, Double> tfidfMap1=new HashMap<String, Double>();
	static Map<String, Double> tfidfMap2=new HashMap<String, Double>();
	public static  Map<String, double[]> wordVector=new HashMap<String, double[]>();
	//static Map<String, Double> idfMap=new HashMap<String, Double>();
	
	public static void main(String[] args) {
		ArrayList<String> folders = new ArrayList<String>() {{
			add("STS.input.track1.ar-ar");
			add("STS.input.track2.ar-en");
			add("STS.input.track3.es-es");
			add("STS.input.track4a.es-en");
			add("STS.input.track4b.es-en");
			add("STS.input.track5.en-en");
			add("STS.input.track6.tr-en");
			
			
			//add("STS2012-en-test.input.gs.MSRvid");
			
			//add("STS2012-en-test.input.gs.SMTeuroparl");
			//add("STS2012-en-test.input.gs.surprise.OnWN");
			//add("STS2012-en-test.input.gs.surprise.SMTnews");
			//add("STS2012-en-train.input.gs.MSRpar");
			////////////add("STS2012-en-test.input.gs.MSRpar");
			
			/*add("STS2012-en-train.input.gs.MSRvid");
		    add("STS2012-en-train.input.gs.SMTeuroparl");
		    add("STS2013-en-test.input.gs.FNWN");
		    
		    
		    add("STS2013-en-test.input.gs.headlines");
		    add("STS2013-en-test.input.gs.OnWN");
		    add("STS2014-en-test.input.gs.deft-forum");
		    add("STS2014-en-test.input.gs.deft-news");
		    add("STS2014-en-test.input.gs.headlines");//done
		    add("STS2014-en-test.input.gs.images");//done
		    add("STS2014-en-test.input.gs.OnWN");
		    add("STS2014-en-test.input.gs.tweet-news");
		    */
			//add("STS2015-en-test.input.gs.answers-forums");
		    //add("STS2015-en-test.input.gs.answers-students");
		    //add("STS2015-en-test.input.gs.belief");
		    //add("STS2015-en-test.input.gs.headlines");
		    //add("STS2015-en-test.input.gs.images");
		    //add("STS2016-en-test.input.gs.answer-answer");
		    //add("STS2016-en-test.input.gs.headlines");
		    //add("STS2016-en-test.input.gs.plagiarism");
		    //add("STS2016-en-test.input.gs.postediting");
		    //add("STS2016-en-test.input.gs.question-question");
		    //add("STS2017-trial-data.input.gs.trial");
		    //add("STS2012-en-test.input.gs.MSRpar");
		}};
		for (int i = 0; i < folders.size(); i++) {
			Properties.FOLDER_NAME=folders.get(i);
			System.out.println("-------------------------------------------"+Properties.FOLDER_NAME+"-----------------------");
			
			Properties.FOLDER_PATH=Properties.PARENT_FOLDER_PATH+"\\"+Properties.FOLDER_NAME;
			
			Properties.INPUT_FILE_PATH=
					Properties.FOLDER_PATH+"\\tokenized_"+Properties.FOLDER_NAME+".txt";
					//"C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\All Data Download\\STS2016-en-test\\sts2016-english-with-gs-v1.0\\STS2016.input.headlines-play.txt";
					//"C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\All Data Download\\STS2016-en-test\\sts2016-english-with-gs-v1.0\\Debug_Dependency.txt";
			
			Properties.INPUT_TFIDF_FILE=
					Properties.FOLDER_PATH+"\\wtfidf\\TFIDF_"+Properties.FOLDER_NAME;//+".txt";
			
			Properties.DEP_INPUT_FILE_PATH=
					Properties.FOLDER_PATH+"\\dep_"+Properties.FOLDER_NAME+".txt";
					//"C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\All Data Download\\STS2016-en-test\\sts2016-english-with-gs-v1.0\\Dependency.txtSTS2016.input.headlines_Dep.txt";
			
			Properties.OUPUT_FILE_PATH=
					Properties.FOLDER_PATH+"\\out_w_Dep_TFIDF_"+Properties.FOLDER_NAME+".txt";
					//"C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\All Data Download\\STS2016-en-test\\sts2016-english-with-gs-v1.0\\output.headlines-30-tfidf-word.txt";
			
			
			Properties.DETILED_OUPUT_FILE_PATH=
					Properties.FOLDER_PATH+"\\out_w_Dep_detailed_"+Properties.FOLDER_NAME+".txt";
					//"C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\All Data Download\\STS2016-en-test\\sts2016-english-with-gs-v1.0\\STS2016.input.headlines-Det30-tfidf-word.txt";

			
			
			
			/*Properties.FOLDER_PATH=Properties.PARENT_FOLDER_PATH+"\\"+Properties.FOLDER_NAME;
			
			
			Properties.INPUT_FILE_PATH=
					Properties.FOLDER_PATH+"\\tokenized_"+Properties.FOLDER_NAME+".txt";
					
			Properties.DEP_INPUT_FILE_PATH=
					Properties.FOLDER_PATH+"\\dep_"+Properties.FOLDER_NAME+".txt";
					//"C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\All Data Download\\STS2016-en-test\\sts2016-english-with-gs-v1.0\\Dependency.txtSTS2016.input.headlines_Dep.txt";
			
			Properties.OUPUT_FILE_PATH=
					Properties.FOLDER_PATH+"\\out_"+Properties.FOLDER_NAME+".txt";
			
			Properties.DETILED_OUPUT_FILE_PATH=
					Properties.FOLDER_PATH+"\\out_detailed_"+Properties.FOLDER_NAME+".txt";*/
			main1(null);
		}
	}
	public static void main1(String[] args) {
		BufferedWriter writer = null;
		BufferedWriter detailedWriter = null;
		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.HALF_UP);
		List<PairSentence> inputSentences	=STSReader.readSTSFile();
		Set<String> bagOfWords=new HashSet<String>();
		bagOfWords.add("ROOT");
		//Sentence.wordVector.put("ROOT", GloveTools.searchForVectorInGlove(Properties.WORD_EMB_PATH, "ROOT"));//add a dummy root to the wordvector
		List<Sentence> test=new ArrayList<Sentence>();
		for (int i = 0; i < inputSentences.size(); i++) 
		{
			PairSentence pair=inputSentences.get(i);
			test.add(pair.getSentenceOne());
			test.add(pair.getSentenceTwo());
		}
		//calculateIdfMap(test);
		//Get the wordvectors
		System.out.println("Load word vectors");
		for (int i = 0; i < inputSentences.size(); i++) {
			Collections.addAll(bagOfWords, inputSentences.get(i).getSentenceOne().getSentenceAllWords());
			Collections.addAll(bagOfWords, inputSentences.get(i).getSentenceTwo().getSentenceAllWords());
		}
		GloveTools.searchForVectorInGloveForList(Properties.WORD_EMB_PATH, bagOfWords);
		System.out.println("End of Load word vectors");
		//
		writer=openNewWriterFile(Properties.OUPUT_FILE_PATH);
		detailedWriter=openNewWriterFile(Properties.DETILED_OUPUT_FILE_PATH);
		for (int i = 0; i < inputSentences.size(); i++) {
			if (!inputSentences.get(i).getSentenceOne().getLoaded()) {
				try {
					System.out.println();
					writer.write("\n");	//neglect this sentence	
					detailedWriter.write("\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
				continue;
			}
			List<AlignmentMatch> exactRelations=GloveTools.findExactAlignmentRelations(inputSentences.get(i));
			List<AlignmentMatch> wordNetRelations=WordNetTool.findWordNetAlignmentRelations(inputSentences.get(i));
			List<AlignmentMatch> glovesRelations=GloveTools.createGlovesAlignmentRelationsNotInList(wordNetRelations, inputSentences.get(i));
			List<AlignmentMatch> allRelations=new ArrayList<AlignmentMatch>();
			
			allRelations.addAll(exactRelations);
			allRelations.addAll(wordNetRelations);
			allRelations.addAll(glovesRelations);
			Set<AlignmentMatch> finalRelations=AlignmentMatch.encodeTheRelations(allRelations,inputSentences.get(i));
			
			loadTFIdfMap(i+1);
			//System.out.println(allRelations);
			//System.out.println(finalRelations);
			try {
				double alignScore=calculateWholeSentenceAlignmentScore(finalRelations,inputSentences.get(i))*5;
				double dependencyScore=calculateWholeSentenceDependencyScore(finalRelations,inputSentences.get(i))*5;
				//double finalScore=0;
				alignScore=alignScore<0?0:alignScore>5?5:alignScore;
				dependencyScore=dependencyScore<0?0:dependencyScore>5?5:dependencyScore;
				
				//finalScore=((alignScore*Properties.ALIGNMENT_WEIGHT )+(dependencyScore*Properties.DEPENDENCY_WEIGHT))/(Properties.ALIGNMENT_WEIGHT+Properties.DEPENDENCY_WEIGHT);
				System.out.println(alignScore+"\t"+dependencyScore);
				writer.write(df.format(alignScore)+"\t"+df.format(dependencyScore)+"\t"+inputSentences.get(i).id+"\n");
				//writer.write(df.format(alignScore)+"\n");
				detailedWriter.write(finalRelations+"\t"+inputSentences.get(i).id+"\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//System.out.println();
			//System.out.println("WordNet: "+wordNetrelations);
			//System.out.println("Gloves : "+glovesrelations);
			
		}
		try {
			writer.close();	
			detailedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	private static double calculateWholeSentenceDependencyScore(Set<AlignmentMatch> relationsSet, PairSentence pairSentence)
	{
		double score=0.0D;
		double totalScore=0.0D;
		List<AlignmentMatch> relationsList=new ArrayList<AlignmentMatch>();
		relationsList.addAll(relationsSet);
	//	Collections.sort(relationsSet);
		for (int i = 0; i < pairSentence.getSentenceOne().getSentenceImpWords().length; i++) {
			//double wordTfIdf=TfIdf.getInstance().tfCalculator(pairSentence.getSentenceOne(),pairSentence.getSentenceOne().getSentenceAllWords()[i] )
			//		*idfMap.get(pairSentence.getSentenceOne().getSentenceAllWords()[i]);
			double wordTfIdf=0;
			try {
				wordTfIdf=tfidfMap1.get(pairSentence.getSentenceOne().getSentenceAllWords()[i]);	
			} catch (Exception e) {
				continue;
				// TODO: handle exception
			}
			
			if (pairSentence.getSentenceOne().getSentenceImpWords()[i].equals(Properties.STOP_WORD)) {
				continue;
			}
			AlignmentMatch bestMatch=AlignmentMatch.getbestAlignMatch(relationsList, 1, i);
			if (bestMatch==null) {
				totalScore+=wordTfIdf;
				//System.out.println("Best Match can not be found:"+pairSentence);
				continue;
			}
			DependencyRelation pairRelation1=DependencyRelation.getDependencyRelation(pairSentence.getSentenceOne().getDependencyRelations(), bestMatch.getWord1Id(), bestMatch.getWord1Text());
			DependencyRelation pairRelation2=DependencyRelation.getDependencyRelation(pairSentence.getSentenceTwo().getDependencyRelations(), bestMatch.getWord2Id(), bestMatch.getWord2Text());
			if (pairRelation1==null || pairRelation2==null) {
				//System.out.println("Catch");
			}
			else
			{
			//score+=CosineSimilarityCalculation.cosineSimilarity(Sentence.wordVector.get(pairRelation1.getDependencyParent()), 
			//		Sentence.wordVector.get(pairRelation2.getDependencyParent()))*wordTfIdf;
				score+=CosineSimilarityCalculation.cosineSimilarity(wordVector.get(pairRelation1.getDependencyParent()), 
						wordVector.get(pairRelation2.getDependencyParent()))*wordTfIdf;
			totalScore+=wordTfIdf;
			}
		}
		for (int i = 0; i < pairSentence.getSentenceTwo().getSentenceImpWords().length; i++) {
			//double wordTfIdf=TfIdf.getInstance().tfCalculator(pairSentence.getSentenceTwo(),pairSentence.getSentenceTwo().getSentenceAllWords()[i] )
			//		*idfMap.get(pairSentence.getSentenceTwo().getSentenceAllWords()[i]);
			//double wordTfIdf=tfidfMap2.get(pairSentence.getSentenceTwo().getSentenceAllWords()[i]);
			double wordTfIdf=0;
			try {
				wordTfIdf=tfidfMap2.get(pairSentence.getSentenceTwo().getSentenceAllWords()[i]);
			} catch (Exception e) {
				continue;
				// TODO: handle exception
			}
			if (pairSentence.getSentenceTwo().getSentenceImpWords()[i].equals(Properties.STOP_WORD)) {
				continue;
			}
			AlignmentMatch bestMatch=AlignmentMatch.getbestAlignMatch(relationsList, 2, i);
			if (bestMatch==null) {
				totalScore+=wordTfIdf;
				//System.out.println("Best Match can not be found in second statement:"+pairSentence);
				continue;
			}
			DependencyRelation pairRelation1=DependencyRelation.getDependencyRelation
					(pairSentence.getSentenceTwo().getDependencyRelations(), bestMatch.getWord2Id(), bestMatch.getWord2Text());;
			DependencyRelation pairRelation2=DependencyRelation.getDependencyRelation
					(pairSentence.getSentenceOne().getDependencyRelations(), bestMatch.getWord1Id(), bestMatch.getWord1Text());
			
			if (pairRelation1==null || pairRelation2==null) {
			//	System.out.println("Catch");
			}
			else
			{
				//score+=CosineSimilarityCalculation.cosineSimilarity(Sentence.wordVector.get(pairRelation1.getDependencyParent()), 
				//		Sentence.wordVector.get(pairRelation2.getDependencyParent()))*wordTfIdf;	
				score+=CosineSimilarityCalculation.cosineSimilarity(wordVector.get(pairRelation1.getDependencyParent()), 
						wordVector.get(pairRelation2.getDependencyParent()))*wordTfIdf;	
				totalScore+=wordTfIdf;	
			}
			
		}
		return score/totalScore;
	}
	
	
	private static double calculateWholeSentenceAlignmentScore(Set<AlignmentMatch> relationsSet, PairSentence pairSentence)
	{
		double score=0.0D;
		double totalScore=0.0D;
		List<AlignmentMatch> relationsList=new ArrayList<AlignmentMatch>();
		relationsList.addAll(relationsSet);
	//	Collections.sort(relationsSet);
		for (int i = 0; i < pairSentence.getSentenceOne().getSentenceImpWords().length; i++) {
			//double wordTfIdf=TfIdf.getInstance().tfCalculator(pairSentence.getSentenceOne(),pairSentence.getSentenceOne().getSentenceAllWords()[i] )
			//		*idfMap.get(pairSentence.getSentenceOne().getSentenceAllWords()[i]);
			//double wordTfIdf=tfidfMap1.get(pairSentence.getSentenceOne().getSentenceAllWords()[i]);
			double wordTfIdf=0;
			try {
				wordTfIdf=tfidfMap1.get(pairSentence.getSentenceOne().getSentenceAllWords()[i]);
			} catch (Exception e) {
				continue;
				// TODO: handle exception
			}
			if (pairSentence.getSentenceOne().getSentenceImpWords()[i].equals(Properties.STOP_WORD)) {
				//score+=AlignmentMatch.getBestAlignment(relationsList, 1, i)*wordTfIdf;
				//totalScore+=wordTfIdf;
				continue;
			}
			
			score+=AlignmentMatch.getBestAlignmentScore(relationsList, 1, i)*wordTfIdf;
			totalScore+=wordTfIdf;
		}
		for (int i = 0; i < pairSentence.getSentenceTwo().getSentenceImpWords().length; i++) {
			//double wordTfIdf=TfIdf.getInstance().tfCalculator(pairSentence.getSentenceTwo(),pairSentence.getSentenceTwo().getSentenceAllWords()[i] )
			//		*idfMap.get(pairSentence.getSentenceTwo().getSentenceAllWords()[i]);
			//double wordTfIdf=tfidfMap2.get(pairSentence.getSentenceTwo().getSentenceAllWords()[i]);
			double wordTfIdf=0;
			try {
				wordTfIdf=tfidfMap2.get(pairSentence.getSentenceTwo().getSentenceAllWords()[i]);
			} catch (Exception e) {
				continue;
				// TODO: handle exception
			}
			if (pairSentence.getSentenceTwo().getSentenceImpWords()[i].equals(Properties.STOP_WORD)) {
				//score+=AlignmentMatch.getBestAlignment(relationsList, 2, i)*wordTfIdf;
				//totalScore+=wordTfIdf;
				continue;
			}
			score+=AlignmentMatch.getBestAlignmentScore(relationsList, 2, i)*wordTfIdf;
			totalScore+=wordTfIdf;
		}
		
		
		return score/totalScore;
	}
	
	
	private static void loadTFIdfMap(int rowNumber)
	{
		
			BufferedReader in =null;
			String line=null;
			String inPath=Properties.INPUT_TFIDF_FILE+"_"+rowNumber+".1_deps.txt";//TODO complete file path
			try {
				in  = new BufferedReader(new FileReader(inPath));
				while ((line=in.readLine())!=null )
				{
					String[] lineComponents=line.split("\t");
					String string1=lineComponents[0].trim();
					String string2=lineComponents[1].trim();
					if(!tfidfMap1.containsKey(string1))
					{
						tfidfMap1.put(string1, Double.parseDouble(string2));
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			try {
				in.close();	
			} catch (Exception e) {
				e.printStackTrace();
			}
			//second MAP
			inPath=Properties.INPUT_TFIDF_FILE+"_"+rowNumber+".2_deps.txt";//TODO complete file path
			try {
				in  = new BufferedReader(new FileReader(inPath));
				while ((line=in.readLine())!=null )
				{
					String[] lineComponents=line.split("\t");
					String string1=lineComponents[0].trim();
					String string2=lineComponents[1].trim();
					if(!tfidfMap2.containsKey(string1))
					{
						tfidfMap2.put(string1, Double.parseDouble(string2));
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			try {
				in.close();	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		

		
		
	}
	//not needed after loading the TFIDF, may be needed later
	/*private static void calculateIdfMap(List<Sentence> test)
	{
		idfMap=new HashMap<String, Double>();;
		for (int i = 0; i < test.size(); i++) {
			Sentence sentence= test.get(i);
			for (int j = 0; j < sentence.getSentenceAllWords().length; j++) {
				String word=sentence.getSentenceAllWords()[j];
				if(!idfMap.containsKey(word))
				{
					//tfMap.put(sentence.getSentenceAllWords()[j], TfIdf.getInstance().tfCalculator(test,sentence.getSentenceAllWords()[j] ));
					//idfMap.put(sentence.getSentenceAllWords()[j], TfIdf.getInstance().idfCalculatorLemm(test,sentence.getSentenceImpLemm()[j] ));
					idfMap.put(word, TfIdf.getInstance().idfCalculator(test,word ));
					
				}
			//	System.out.println(word+"\t"+TfIdf.getInstance().tfCalculatorLemm(sentence,sentence.getSentenceImpLemm()[j] )*idfMap.get(word));
			}
		}
	}*/
	public static BufferedWriter openNewWriterFile(String path)
	{
		File logFile = new File(path);
		BufferedWriter writer=null;
		try {
			 writer = new BufferedWriter(new FileWriter(logFile));	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return writer;
		
	}
	/*
	public static void main(String[] args) {
		int rowCount=0;
		BufferedReader in =null;
		String line=null;
		BufferedWriter writer=null;
		String masterWord="awarded";
		String inPath="C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\Tools and Resources\\glove.840B.300d\\glove.840B.300d.txt";
		String outFolder="C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\Tools and Resources\\glove.840B.300d";
		File outLogFile = new File(outFolder+"\\similarityOutawarded.txt");
		//Map<String, double[]> lines= new HashMap<String, double[]>();
		double[] masterWordVector=searchForVectorInFile(inPath,masterWord);
		if(masterWordVector==null)
		{
			System.out.println("INPUT word is not there");
			return;
		}
		else
		{
			System.out.println("INPUT word is FOUND");
		}
		
		
		try {
			in  = new BufferedReader(new FileReader(inPath));
			
			 writer = new BufferedWriter(new FileWriter(outLogFile));	
			
			while ((line=in.readLine())!=null)
			{
				String key=line.split(" ")[0].trim();
				double[] value=GloveTools.gloveToDoubleArray(line);
				writer.write(masterWord+" - "+key+"=\t"+CosineSimilarityCalculation.cosineSimilarity(masterWordVector,value)+"\n");
				rowCount++;
				lines.put(line.split(" ")[0].trim(),GloveTools.gloveToDoubleArray(line));
				System.out.println("Exception at line:"+rowCount);
			
			}
			//System.out.println("Lines="+lines.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			in.close();	
			 writer.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		for (String string : lines.keySet()) {
			System.out.println("frog:"+string+"=\t"+CosineSimilarityCalculation.cosineSimilarity(lines.get("frog"), lines.get(string)));	
		}
	}

*/	
	/*public static double[] searchForVectorInFile(String filePath,String inputWord)
	{
		BufferedReader in ;
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
			
		
		return null;
	}*/
	/*for (int i = 0; i < pair.getSentenceOne().getWordVector().size(); i++) {
	for (int j = 0; j < pair.getSentenceTwo().getWordVector().size(); j++) {
		if (!pair.getSentenceOne().getSentenceImpWords()[i].equals(Properties.STOP_WORD)&& !pair.getSentenceTwo().getSentenceImpWords()[j].equals(Properties.STOP_WORD)) {
			System.out.println(pair.getSentenceOne().getSentenceAllWords()[i]+"-"+pair.getSentenceTwo().getSentenceAllWords()[j]+"=\t"+
					CosineSimilarityCalculation.cosineSimilarity(pair.getSentenceOne().getWordVector().get(i), pair.getSentenceTwo().getWordVector().get(j)));
		}
		
	}
	
}*/

	
}
