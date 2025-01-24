package org.mql.java.xml;

import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLNode {
private Node node;
	
	public XMLNode(Node node) {
		super();
		this.node = node;
	}
	public XMLNode(String source) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(source);
			node = document.getFirstChild();
			while (node.getNodeType() != Node.ELEMENT_NODE) {
				node = node.getNextSibling();
			}
//			Short
		}catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}
	
	public XMLNode[] children() {
		List<XMLNode> list = new Vector<XMLNode>();
		NodeList n1 = node.getChildNodes();
		int n = n1.getLength();
		for (int i = 0; i < n ;i++) {
			Node child = n1.item(i);
			if(child.getNodeType() == Node.ELEMENT_NODE) {
				list.add(new XMLNode(child));
			}
		}
		XMLNode t[]=new XMLNode[list.size()];
		list.toArray(t);
		return t;
	}
	
	public XMLNode child(String name) {
		List<XMLNode> list = new Vector<XMLNode>();
		NodeList n1 = node.getChildNodes();
		int n = n1.getLength();
		for (int i = 0; i < n ;i++) {
			Node child = n1.item(i);
			if(child.getNodeName().equals(name)){
				return new XMLNode(child);
			}
		}
		return null;
	}
	
	
	public String getName() {
		return node.getNodeName();
	}
	
//??	Un nom n'a de fils que si il na pas de valeur et que sa valeur est le nom de son fils textuel
	public String getValue() {
		NodeList list = node.getChildNodes();
		if(list.getLength() == 1 && list.item(0).getNodeType() == Node.TEXT_NODE) {
			return list.item(0).getNodeValue();			
		}
		return null;
	}
	
	public String attribute(String name) {
		NamedNodeMap atts=node.getAttributes();
		return atts.getNamedItem(name) !=null? atts.getNamedItem(name).getNodeValue():null;
	}
	
	public int intAttribute(String name) {
		String att=attribute(name);
		try {
				return Integer.parseInt(att);
		}catch (Exception e) {
			return -1;
		}
	}
}
