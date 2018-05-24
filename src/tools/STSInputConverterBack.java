package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.STS_R1;
import data_structure.PairSentence;
import data_structure.Sentence;
import properties.Properties;

public class STSInputConverterBack {

	public static void main (String[] str)
	{
		List<String> pairs=readSTSFile();
		//BufferedWriter writer = null;
		//writer=STS_R1.openNewWriterFile(Properties.CONVERSION_OUPUT_FILE_PATH);
		for (int i = 0; i < pairs.size(); i++) {
			//System.out.println(pairs.get(i).getSentenceOne()+".");
			//System.out.println(pairs.get(i).getSentenceTwo()+".");
			
				System.out.print(pairs.get(i).substring(0,pairs.get(i).length()-1)+"\t");
				i++;
				System.out.println(pairs.get(i).substring(0,pairs.get(i).length()-1));
			
			
		}
		
		
	}
	
	public static List<String> readSTSFile() {
		BufferedReader in =null;
		String line=null;
		
		List<String> results= new ArrayList<String>();
		String inPath="C:\\Users\\Amr_Local\\Desktop\\STS Shared Task\\All Data Download\\STS2016-en-test\\sts2016-english-with-gs-v1.0\\STS2016.input.headlines_Converted.txt";
		try {
			in  = new BufferedReader(new FileReader(inPath));
			while ((line=in.readLine())!=null)
			{
				results.add(line.trim());
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
		
		return results;
	}
}
