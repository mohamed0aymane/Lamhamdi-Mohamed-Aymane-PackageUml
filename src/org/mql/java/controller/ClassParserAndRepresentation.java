package org.mql.java.controller;




import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;


public class ClassParserAndRepresentation {
	private List<String> attributes;
	private List<String> methodes;
	private String className;
	private Class<?> cls;

//	public ClassParserAndRepresentation (String className) {
//		this.className=className;
//		attributes=new ArrayList<>();
//		methodes=new ArrayList<>();
//		try {
//			this.cls=Class.forName(className);
//			addAttributes();
//			addMethodes();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		
//	}
	

	 public ClassParserAndRepresentation(String className) {
	        this.className = className;
	        this.attributes = new ArrayList<>();
	        this.methodes = new ArrayList<>();
	        parseClass();
	    }
	 
		private void parseClass() {
	        try {
	            Class<?> cls = Class.forName(className);
	            // Parse attributes
	            for (Field field : cls.getDeclaredFields()) {
	            	String modificateur = getModifier(field.getModifiers());
					attributes.add(modificateur+" "+field.getName()+":"+field.getType().getSimpleName());
	               
	            }
	            // Parse methods
	            for (Method methode : cls.getDeclaredMethods()) {
	            	StringBuilder builder=new StringBuilder();
	    			String modificateur = getModifier(methode.getModifiers());
	    			builder.append(modificateur).append(" ").append(methode.getName()).append("(");
	    			
	    			Parameter[] methodeParams=methode.getParameters();
	    			for (Parameter methodeParam : methodeParams) {
	    				builder.append(methodeParam.getType().getSimpleName()).append(", ");
	    			}
	    			if(builder.lastIndexOf(",")>0) {
	    				builder.setLength(builder.length()-2);
	    			}
	    			builder.append(") :"+methode.getReturnType().getSimpleName());
	    			this.methodes.add(builder.toString());
	            }
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }


	

	private String getModifier(int modifier){
			// l'utilisation de 7 ici pour avoir que les 3 premier bit
			// de l'entier qui represente les 3 modificateur d'accèes
			// <PUBLIC, PRIVATE, PROTECTED>
		  switch (modifier & 7) {
          case Modifier.PUBLIC:
              return "+";
          case Modifier.PRIVATE:
              return "-";
          case Modifier.PROTECTED:
              return "#";
          default:
              return "~";
      }
		}

	
	public List<String> getAttributes() {
		return attributes;
	}



	public List<String> getMethodes() {
		return methodes;
	}
	
	public String getClassName() {
	        return className;
	}

	


	
}