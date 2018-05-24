package data_structure;

import java.util.List;

public class DependencyRelation {

	private String dependencyLabel;
	private String dependencyParent;
	private String dependencyChild;
	private int dependencyParentID;
	private int dependencyChildID;
	
	public DependencyRelation(String dependencyLabel, String dependencyParent,
			String dependencyChild, int dependencyParentID, int dependencyChildID) {
		super();
		this.dependencyLabel = dependencyLabel;
		this.dependencyParent = dependencyParent;
		this.dependencyChild = dependencyChild;
		this.dependencyParentID=dependencyParentID;
		this.dependencyChildID=dependencyChildID;
	}
	public String getDependencyLabel() {
		return dependencyLabel;
	}
	public void setDependencyLabel(String dependencyLabel) {
		this.dependencyLabel = dependencyLabel;
	}
	public String getDependencyParent() {
		return dependencyParent;
	}
	public void setDependencyParent(String dependencyParent) {
		this.dependencyParent = dependencyParent;
	}
	public String getDependencyChild() {
		return dependencyChild;
	}
	public void setDependencyChild(String dependencyChild) {
		this.dependencyChild = dependencyChild;
	}
	public int getDependencyParentID() {
		return dependencyParentID;
	}
	public void setDependencyParentID(int dependencyParentID) {
		this.dependencyParentID = dependencyParentID;
	}
	public int getDependencyChildID() {
		return dependencyChildID;
	}
	public void setDependencyChildID(int dependencyChildID) {
		this.dependencyChildID = dependencyChildID;
	}
	public String toString()
	{
		return   dependencyLabel+" "+dependencyParent+" "+dependencyParentID+" "+dependencyChild+" "+dependencyChildID;
	}
	
	public static DependencyRelation getDependencyRelation(List<DependencyRelation> list, int wordNo, String word)
	{
		
		for (int i = 0; i < list.size(); i++) {
			DependencyRelation dependency=list.get(i);
			if (dependency.getDependencyChildID()==wordNo+1 &&	dependency.getDependencyChild().equalsIgnoreCase(word)) {
				return dependency;
			}
		}
		return null;
	}
}
