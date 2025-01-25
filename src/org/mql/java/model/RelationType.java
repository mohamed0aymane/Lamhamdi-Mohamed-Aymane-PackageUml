package org.mql.java.model;

/*
 COMPOSITION : Une relation forte où une classe dépend complètement d'une autre. Si la classe principale est détruite, les objets associés sont également détruits.

AGGREGATION : Une relation plus faible où une classe peut contenir des objets d'une autre classe, mais ces objets peuvent exister indépendamment.

IMPLEMENTATION : Une classe implémente une interface en fournissant des comportements définis par celle-ci.

INHERITANCE : Une classe hérite des propriétés et méthodes d'une autre classe (classe mère).

REALISATION : Une classe réalise une interface en fournissant des comportements définis par cette interface.

DEPENDANCE : Une classe dépend d'une autre pour fonctionner, généralement par des paramètres ou des méthodes.*/
public enum RelationType {
		
	    COMPOSITION,
	    AGGREGATION,
	    IMPLEMENTATION, 
	    INHERITANCE,
	    REALISATION,        
	    DEPENDANCE;         
}
