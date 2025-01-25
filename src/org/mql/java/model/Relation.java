package org.mql.java.model;
/*
 Agrégation : Diamant non rempli.
Composition : Diamant rempli.
Héritage : Flèche simple (indiquant que la classe fille hérite de la classe parente).
Indépendance : Pas de relation visible, ou un simple trait sans symboles.*/
public class Relation{
	private String classSourceName;
    private String classTargetName;
    private RelationType relationType; 

    public Relation(String classSourceName, String classTargetName, RelationType relationType) {
        this.classSourceName = classSourceName;
        this.classTargetName = classTargetName;
        this.relationType = relationType;
    }

    public String getClassSourceName() {
        return classSourceName;
    }

    public String getClassTargetName() {
        return classTargetName;
    }

    public RelationType getRelationType() {
        return relationType;
    }

	@Override
	public String toString() {
		return "RelationModel [classSourceName=" + classSourceName + ", classTargetName=" + classTargetName
				+ ", relationType=" + relationType + "]";
	}

	
}