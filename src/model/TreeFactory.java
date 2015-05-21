package model;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.tree.*;

public class TreeFactory {
	private static TreeFactory factoryInstance = new TreeFactory();
	
	private TreeFactory() {
		
	}
	
	public static TreeFactory getInstance() {
		return factoryInstance;
	}
	
	public DefaultMutableTreeNode newTree(File dataSource) throws IOException {
		DefaultMutableTreeNode treeRoot;
		DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder xmlBuilder=null;
		Document doc=null;
		try {
			xmlBuilder = xmlFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// To be implemented...
		}
		try {
			doc = xmlBuilder.parse(dataSource);
		} catch (SAXException e) {
			// To be implemented...
		}
		doc.getDocumentElement().normalize();
		
		treeRoot = new DefaultMutableTreeNode(true);
		buildTree(doc.getDocumentElement(),treeRoot);
		return treeRoot;
	}
	
	private void buildTree(Element DOMnode, DefaultMutableTreeNode treeNode) {
		NodeList nodeList = DOMnode.getChildNodes();
		for(int i=0; i<nodeList.getLength();i++) {
			Element elem=null;
			DefaultMutableTreeNode newNode=null;
			Node node = nodeList.item(i);
			if(node.getNodeType()==Node.ELEMENT_NODE) {
				elem = (Element) node;
			}
			newNode = new DefaultMutableTreeNode(elem.getAttribute("name"),elem.hasChildNodes());
			if(newNode.getAllowsChildren())
				buildTree(elem,newNode);
			treeNode.add(newNode);
		}
	}
	
	
}
