package data_structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import properties.Properties;

public class AlignmentMatch implements Comparable{

	private int word1Id;
	private int word2Id;
	private String word1Text;
	private String word2Text;
	private double alignmentScore;
	
	
	public AlignmentMatch(int word1Id, int word2Id, String  word1Text,
			String word2Text, double alignmentScore) {
		super();
		this.word1Id = word1Id;
		this.word2Id = word2Id;
		this.word1Text = word1Text;
		this.word2Text = word2Text;
		this.alignmentScore = alignmentScore;
	}
	public static int getAligedStatmentCount(List<AlignmentMatch> list, int statmentNo)
	{
		Set<Integer> ids= new HashSet<Integer>();
		for (int i = 0; i < list.size(); i++) {
			if (statmentNo==1) {
			ids.add(list.get(i).word1Id);	
			}
			if (statmentNo==2) {
				ids.add(list.get(i).word2Id);	
			}
		}
		return ids.size();
	}
	public static int getNonStopAligedStatmentCount(Set<AlignmentMatch> list, int statmentNo, PairSentence sentence)
	{
		Set<Integer> ids= new HashSet<Integer>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			AlignmentMatch match = (AlignmentMatch) iterator.next();
			if (statmentNo==1 && ! sentence.getSentenceOne().getSentenceImpWords()[match.word1Id].equals(Properties.STOP_WORD)) {
				ids.add(match.word1Id);	
				}
				if (statmentNo==2&& ! sentence.getSentenceTwo().getSentenceImpWords()[match.word2Id].equals(Properties.STOP_WORD)) {
				ids.add(match.word2Id);	
				}
		}
		return ids.size();
	}
	public static boolean hasRelation(List<AlignmentMatch> list,int statmentNo, int wordNo)
	{
		Set<AlignmentMatch> set= new HashSet<AlignmentMatch>();
		set.addAll(list);
		return hasRelation(set,statmentNo,wordNo);
		
	}
	public static boolean hasRelation(Set<AlignmentMatch> set,int statmentNo, int wordNo)
	{
		Iterator<AlignmentMatch> iter=set.iterator();
		while (iter.hasNext()) {
		    AlignmentMatch match=iter.next();
		    if (statmentNo==1 && match.word1Id==wordNo) {
				return true;	
			}
			if (statmentNo==2 && match.word2Id==wordNo) {
				return true;
			}
		}
		return false;
	}
	public static double getBestAlignmentScore(List<AlignmentMatch> relationList,int statmentNo, int wordNo)
	{
		double maximum=-10.0D;
		for (int i = 0; i < relationList.size(); i++) {
			AlignmentMatch match=relationList.get(i);
			if (statmentNo==1 && match.word1Id==wordNo && match.getAlignmentScore()>maximum) {
				maximum= match.getAlignmentScore();	
			}
			if (statmentNo==2 && match.word2Id==wordNo && match.getAlignmentScore()>maximum) {
				maximum= match.getAlignmentScore();	
			}
		}
		return maximum==-10.0D?0:maximum;
	}
	public static AlignmentMatch getbestAlignMatch(List<AlignmentMatch> relationList,int statmentNo, int wordNo)
	{
		AlignmentMatch result=null;
		double maximum=-10.0D;
		for (int i = 0; i < relationList.size(); i++) {
			AlignmentMatch match=relationList.get(i);
			if (statmentNo==1 && match.word1Id==wordNo && match.getAlignmentScore()>maximum) {
				maximum= match.getAlignmentScore();	
				result=match;
			}
			if (statmentNo==2 && match.word2Id==wordNo && match.getAlignmentScore()>maximum) {
				maximum= match.getAlignmentScore();	
				result=match;
			}
		}
		return result;
	}
	
	public static Set<AlignmentMatch> encodeTheRelations(List<AlignmentMatch> relationList,PairSentence pairSentence)
	{
		Collections.sort(relationList);
		Set<AlignmentMatch> results=new HashSet<AlignmentMatch>();
		for (int i = 0; i < relationList.size(); i++) {
			AlignmentMatch match=relationList.get(i);
			//TODO Test to through out the weak alignment 
			if (match.getAlignmentScore()<0.5) {
				break;
			}
			if(AlignmentMatch.getNonStopAligedStatmentCount(results, 1,pairSentence)==pairSentence.getSentenceOne().getNonStopWordCount()
			&& AlignmentMatch.getNonStopAligedStatmentCount(results, 2,pairSentence)==pairSentence.getSentenceTwo().getNonStopWordCount())
			{
				break;
			}
			if (AlignmentMatch.hasRelation(results, 1, match.word1Id)&&AlignmentMatch.hasRelation(results, 2, match.word2Id)) {
				continue;//no need to consider it, coz the two words are aligned already
			}
			if (pairSentence.getSentenceOne().getSentenceImpWords()[match.word1Id].equals(Properties.STOP_WORD)&&
				pairSentence.getSentenceTwo().getSentenceImpWords()[match.word2Id].equals(Properties.STOP_WORD)) 
			{
				continue;//no need to consider it, coz the two words are Stopwords already
			}
			if (AlignmentMatch.hasRelation(results, 1, match.word1Id)&&pairSentence.getSentenceTwo().getSentenceImpWords()[match.word2Id].equals(Properties.STOP_WORD)) {
				continue;//no need to consider it, coz the two words are aligned already
			}
			if (AlignmentMatch.hasRelation(results, 2, match.word2Id)&&pairSentence.getSentenceOne().getSentenceImpWords()[match.word1Id].equals(Properties.STOP_WORD)) {
				continue;//no need to consider it, coz the two words are aligned already
			}
			
			
			results.add(match);
		}
		return results;
	}
	public static void addAligedRelationIfNotExist(List<AlignmentMatch> list, AlignmentMatch match)
	{
		if (!list.contains(match)) {
			list.add(match);//TODO
		}
	}
	
	public boolean equals(Object o) {
        if (!(o instanceof AlignmentMatch))
            return false;
        AlignmentMatch n = (AlignmentMatch) o;
        return n.getWord1Id()==this.word1Id && n.getWord2Id()==this.word2Id;
    }
	public int hashCode() {
		return 37 * word1Id + word2Id; 
	}
	
	
	public int compareTo(Object anotherMatch) throws ClassCastException {
	    if (!(anotherMatch instanceof AlignmentMatch))
	      throw new ClassCastException("Match Alignment object expected.");
	    double anotherMatchScore = ((AlignmentMatch) anotherMatch).getAlignmentScore();  
	    return this.alignmentScore > anotherMatchScore?-1:this.alignmentScore == anotherMatchScore?0:1;    
	  }


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return word1Text+"#"+word1Id+" "+word2Text+"#"+word2Id+"="+alignmentScore;
	}
	public int getWord1Id() {
		return word1Id;
	}


	public void setWord1Id(int word1Id) {
		this.word1Id = word1Id;
	}


	public int getWord2Id() {
		return word2Id;
	}


	public void setWord2Id(int word2Id) {
		this.word2Id = word2Id;
	}


	public double getAlignmentScore() {
		return alignmentScore;
	}


	public void setAlignmentScore(double alignmentScore) {
		this.alignmentScore = alignmentScore;
	}
	public String  getWord1Text() {
		return word1Text;
	}
	public void setWord1Text(String  word1Text) {
		this.word1Text = word1Text;
	}
	public String  getWord2Text() {
		return word2Text;
	}
	public void setWord2Text(String  word2Text) {
		this.word2Text = word2Text;
	}
	
}
