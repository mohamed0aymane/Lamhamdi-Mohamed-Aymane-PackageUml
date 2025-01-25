package org.mql.java.model;

/*
 * Aggregation: If the field is a collection (List, Set, Map) or an array.
Composition: If the field is a direct reference to another class (like Teacher in Classroom or Project in FileExplorer).
Implementation: If the field is a custom class (not a primitive or String).
Realisation: If the field is an interface.
Dependence: If the field is another class in the same package but doesn't fit into the other categories.
 */
public enum RelationType {
		
	    COMPOSITION,
	    AGGREGATION,
	    IMPLEMENTATION, 
	    INHERITANCE,
	    REALISATION,        
	    DEPENDANCE;         
}
