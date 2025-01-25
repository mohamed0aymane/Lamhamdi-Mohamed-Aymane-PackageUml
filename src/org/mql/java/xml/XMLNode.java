package org.mql.java.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.Vector;


public class XMLNode {
	
	private Node node;
	 public void appendChild(XMLNode childNode) {
	        if (childNode != null) {
	            node.appendChild(childNode.node);
	        }
	    }
	public XMLNode(Node node) {
		super();
		this.setNode(node);
	}
	public XMLNode(String source) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(source);
			setNode(document.getFirstChild());
			while (getNode().getNodeType() != Node.ELEMENT_NODE) {
				setNode(getNode().getNextSibling());
			}
//			Short
		}catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}
	
	public XMLNode[] children() {
		List<XMLNode> list = new Vector<XMLNode>();
		NodeList n1 = getNode().getChildNodes();
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
		NodeList n1 = getNode().getChildNodes();
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
		return getNode().getNodeName();
	}
	
//??	Un nom n'a de fils que si il na pas de valeur et que sa valeur est le nom de son fils textuel
	public String getValue() {
		NodeList list = getNode().getChildNodes();
		if(list.getLength() == 1 && list.item(0).getNodeType() == Node.TEXT_NODE) {
			return list.item(0).getNodeValue();			
		}
		return null;
	}
	
	public String attribute(String name) {
		NamedNodeMap atts=getNode().getAttributes();
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
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
}

 