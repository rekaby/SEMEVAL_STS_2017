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

public class STSInputConverter {

	public static void main (String[] str)
	{
		List<String> pairs=readSTSFile();
		BufferedWriter writer = null;
		writer=STS_R1.openNewWriterFile(Properties.CONVERSION_OUPUT_FILE_PATH);
		for (int i = 0; i < pairs.size(); i++) {
			//System.out.println(pairs.get(i).getSentenceOne()+".");
			//System.out.println(pairs.get(i).getSentenceTwo()+".");
			try {
				writer.write(pairs.get(i)+".\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		try {
			writer.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static List<String> readSTSFile() {
		BufferedReader in =null;
		String line=null;
		
		List<String> results= new ArrayList<String>();
		String inPath=Properties.INPUT_FILE_PATH;
		try {
			in  = new BufferedReader(new FileReader(inPath));
			while ((line=in.readLine())!=null)
			{
				String[] lineComponents=line.split("\t");
				String string1=lineComponents[0].trim();
				String string2=lineComponents[1].trim();
				results.add(lineComponents[0].trim());
				results.add(lineComponents[1].trim());
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
