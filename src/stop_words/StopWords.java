package stop_words;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import properties.Properties;

public class StopWords {
	private static List<String> listStopWords=new ArrayList<String>();
	
	private static StopWords instance = null;
	public static StopWords getInstance() {
	      if(instance == null) {
	         instance = new StopWords();
	      }
	      return instance;
	   }
	
	
	public StopWords() {
		loadStopWordsFromFile(Properties.STOP_WORDS_PATH);
	}

	public static void loadStopWordsFromFile(String filePath)
	{
		//List<String> results=new ArrayList<String>();
		BufferedReader in ;
		String line=null;
		try {
				in  = new BufferedReader(new FileReader(filePath));	
				while ((line=in.readLine())!=null)
				{
					listStopWords.add(line.split(" ")[0].trim().toLowerCase());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		
	}

	public String getNonStopString(String inputSentence)
	{
		for (int i = 0; i < listStopWords.size(); i++) {
			inputSentence.replace( listStopWords.get(i), Properties.STOP_WORD);
		}
		return inputSentence;
	}
	public String[] getNonStopWords(String inputSentence)
	{
		String[] originalWords=inputSentence.split("\\s+");
		return getNonStopWords(originalWords);
	}
	public String[] getNonStopWords(String[] originalWords)
	{
		String[] nonStopWords= new String[originalWords.length];
		for (int i = 0; i < originalWords.length; i++) {
			if(listStopWords.contains(originalWords[i].toLowerCase()))
			{
				nonStopWords[i]=Properties.STOP_WORD;
			}
			else
			{
				nonStopWords[i]=originalWords[i];
			}
			
		}
		return nonStopWords;
		//return Arrays.asList(words);
	}
}
