package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import properties.Properties;
import data_structure.DependencyRelation;

public class DependencyReader {
	
	private static BufferedReader in =null;
	public static void main(String str[])
	{
		openFile();
		for (int i = 0; i < 1000; i++) {
			System.out.println(readDependencyRecord());
		}
		closeFile();
	}
	public static void openFile()
	{
		String inPath=Properties.DEP_INPUT_FILE_PATH;
		try {
			in  = new BufferedReader(new FileReader(inPath));	
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}
	public static void closeFile()
	{
		try {
			in.close();	
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}
	public static List<DependencyRelation> readDependencyRecord() {
		String line=null;
		List<DependencyRelation> results= new ArrayList<DependencyRelation>();
		try {
			while ((line=in.readLine())!=null && !line.equals(""))
			{
				String label=line.substring(0,line.indexOf("("));
				String parentText="";
				String childText="";
				int startPos=line.indexOf("(");
				int originalStartPos=line.indexOf("(");
				while (parentText.indexOf("-")==-1||childText.indexOf("-")==-1) {
					int splitCommaPos=line.indexOf(",",startPos);
					parentText=line.substring(originalStartPos+1,splitCommaPos);
					childText=line.substring(splitCommaPos+1,line.indexOf(")")).trim();
					startPos=splitCommaPos+1;
				}
				
				String parent=getWordWithoutID(parentText);
				int parentId=getWordID(parentText);
				String child=getWordWithoutID(childText);
				int childId=getWordID(childText);
				
				DependencyRelation relation=new DependencyRelation(label,parent,child,parentId,childId);
				results.add(relation);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return results;
	}
	
	
	public static ArrayList<ArrayList<DependencyRelation>> readDependencyRecordFromNewStructure() {
		String line=null;
		ArrayList<ArrayList<DependencyRelation>> results= new ArrayList<ArrayList <DependencyRelation>>();
		results.add(new ArrayList <DependencyRelation>());//just fill with two slots
		results.add(new ArrayList <DependencyRelation>());
		try {
			line=in.readLine();
			if (line!=null && !line.equals("")) {
				String[] lineComponents=line.split("\t");//to get the two dependencies
				for (int i = 0; i < 2; i++) {//just coz we read 2 sentences per line
					String currentLine=lineComponents[i];
					currentLine=currentLine.trim().substring(1,currentLine.lastIndexOf("]"));// to remove borders []
					currentLine=currentLine.replaceAll("\\),", ")\n");//to be similar to old format with 1 dependency per line
					String[] component=currentLine.split("\n");//now we have only one relation
					for (int j = 0; j < component.length; j++) {
						component[j]=component[j].trim();
						if(component[j]!=null && !component[j].equals(""))
						{
							String label=component[j].substring(0,component[j].indexOf("("));
							String parentText="";
							String childText="";
							int startPos=component[j].indexOf("(");
							int originalStartPos=component[j].indexOf("(");
							while (parentText.indexOf("-")==-1||childText.indexOf("-")==-1) {
								int splitCommaPos=component[j].indexOf(",",startPos);
								parentText=component[j].substring(originalStartPos+1,splitCommaPos);
								childText=component[j].substring(splitCommaPos+1,component[j].indexOf(")")).trim();
								startPos=splitCommaPos+1;
							}
							String parent=getWordWithoutID(parentText);
							int parentId=getWordID(parentText);
							String child=getWordWithoutID(childText);
							int childId=getWordID(childText);
							DependencyRelation relation=new DependencyRelation(label,parent,child,parentId,childId);
							results.get(i).add(relation);
						}
					}
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return results;
	}
	private static String getWordWithoutID(String str)
	{
		String result="";
		try {
			result= str.substring(0,str.lastIndexOf("-"));	
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return result;
	}
	private static int getWordID(String str)
	{
		int result;
		try {
			result=Integer.parseInt(str.substring(str.lastIndexOf("-")+1));	
		} catch (Exception e) {//to handle strange input with ' at the id section
			result=Integer.parseInt(str.substring(str.lastIndexOf("-")+1).replaceAll("\'", ""));
		}
		
		return result;
	}
}
