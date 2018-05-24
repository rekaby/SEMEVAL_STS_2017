package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data_structure.DependencyRelation;
import data_structure.PairSentence;
import data_structure.Sentence;
import properties.Properties;

public class STSReader {

	public static List<PairSentence> readSTSFile() {
		BufferedReader in =null;
		//BufferedReader gold =null;
		int count=1;
		String line=null;
		//String goldLine=null;
		
		List<PairSentence> results= new ArrayList<PairSentence>();
		String inPath=Properties.INPUT_FILE_PATH;
		//String goldPath=Properties.GOLD_FILE_PATH;
		try {
			in  = new BufferedReader(new FileReader(inPath));
			DependencyReader.openFile();
			//gold  = new BufferedReader(new FileReader(goldPath));
			while ((line=in.readLine())!=null && !line.trim().equals("") && count<=Properties.MAXIMUM_LINES_COUNT)
			{
				/*//For debug
				if (count<Properties.START_LINES_COUNT) {
					DependencyReader.readDependencyRecord();
					DependencyReader.readDependencyRecord();
					count++;
					continue;
				}
				//end of debug code
*/				
				//goldLine=gold.readLine();
				PairSentence pair=new PairSentence();
				Sentence s1;
				Sentence s2;
				String[] lineComponents=line.split("\t");
				String string1=lineComponents[0].trim();
				String string2=lineComponents[1].trim();
				int id=0;
//				try {
					id=Integer.parseInt(lineComponents[lineComponents.length-1].trim());	
//				} catch (Exception e) {
//					System.out.println("ERROR in line structure");
//					id=Integer.parseInt(lineComponents[3].trim());
//				}
				
				//s1=new Sentence(string1, Properties.SENT_1_LANG,goldLine.equals("")?false:true);
				s1=new Sentence(string1, Properties.SENT_1_LANG,true);
				//s1.setDependencyRelations(DependencyReader.readDependencyRecord());
				s2=new Sentence(string2,Properties.SENT_2_LANG,true);
				//s2=new Sentence(string2,Properties.SENT_2_LANG,goldLine.equals("")?false:true);
				//s2.setDependencyRelations(DependencyReader.readDependencyRecord());
				ArrayList<ArrayList<DependencyRelation>> tempRelationVariable= DependencyReader.readDependencyRecordFromNewStructure();
				s1.setDependencyRelations(tempRelationVariable.get(0));
				s2.setDependencyRelations(tempRelationVariable.get(1));
				
				pair.setSentenceOne(s1);
				pair.setSentenceTwo(s2);
				pair.id=id;
				results.add(pair);
				System.out.println(count+++" Line is processed");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		try {
			in.close();	
			//gold.close();
			DependencyReader.closeFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return results;
	}
}
