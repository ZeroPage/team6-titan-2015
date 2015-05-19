package model;

import java.util.*;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.*;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.tree.*;

public class ClusterData {
	private Document doc;
	private File sourceFile;
	private ArrayList<Element> groupList = new ArrayList<Element>();
	private DefaultMutableTreeNode treeRoot;
	
	public ClusterData() {
		System.out.println("I need a directory of the .clsx file!");
	}
	
//initializing with directory(seems not needed)
	public ClusterData(String fileDir) throws IOException {
		sourceFile = new File(fileDir);
		initData();
	}
	
//initializing with File instance	
	public ClusterData(File file) throws IOException {
		sourceFile = file;
		initData();
	}
	
//load new .clsx file
	public void loadClusterData(File file) throws IOException {
		this.sourceFile = file;
		initData();
	}
	
	public void loadClusterData(String fileName) throws IOException {
		this.sourceFile = new File(fileName);
		initData();
	}

//Parse XML data from .clsx file into tree
	private void initData() throws  IOException {
		DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder xmlBuilder=null;
		try {
			xmlBuilder = xmlFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// To be implemented...
		}
		try {
			this.doc = xmlBuilder.parse(this.sourceFile);
		} catch (SAXException e) {
			// To be implemented...
		}
		
		//get root
		Element recentNode = doc.getDocumentElement();
		recentNode.normalize();
		
		NodeList nodeList = recentNode.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
			if(node.getNodeType()==Node.ELEMENT_NODE) {
				Element elem = (Element) node;
				
				NodeList groupNodeList = elem.getChildNodes();
				for(int j=0;j<groupNodeList.getLength();j++) {
					Node groupNode = groupNodeList.item(j);
					
					if(groupNode.getNodeType()==groupNode.ELEMENT_NODE) {
						Element gElem = (Element) groupNode;
						this.groupList.add(gElem);
					}
				}
			}
		}
	}
	
	private void DOMtoTree() {
		
	}
	
	private void refresh() {
		
	}
	
	public TreeNode getTree() {
		return this.treeRoot;
	}
	
	public void setTree(TreeNode newTree) {
		//To be implemented...
	}
	
	public void saveClusterData() throws TransformerException {//Will be Modified
		TransformerFactory tFac = TransformerFactory.newInstance();
		Transformer transformer = tFac.newTransformer();
		DOMSource source = new DOMSource(this.doc);
		Result output = new StreamResult(this.sourceFile);
		
		this.sourceFile.delete();
		transformer.transform(source, output);
	}
}
