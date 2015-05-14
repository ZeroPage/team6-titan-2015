package model;

import java.util.*;
import java.io.*;
import java.lang.Exception.*;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

public class ClusterData {
	private Document doc;
	private File sourceFile;
	private String groupName;
	private ArrayList<Element> groupList = new ArrayList<Element>();
	
	public ClusterData() {
		System.out.println("I need a directory of the .clsx file!");
	}
//initializing with directory
	public ClusterData(String fileDir) throws ParserConfigurationException, SAXException, IOException {
		sourceFile = new File(fileDir);
		initData();
	}
//initializing with File instance	
	public ClusterData(File file) throws ParserConfigurationException, SAXException, IOException {
		sourceFile = file;
		initData();
	}

//Parse XML data from .clsx file into tree
	private void initData() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder xmlBuilder = xmlFactory.newDocumentBuilder();
		this.doc = xmlBuilder.parse(this.sourceFile);
		
		//get root
		Element recentNode = doc.getDocumentElement();
		recentNode.normalize();
		System.out.println("Root element :" + recentNode.getNodeName() + " - " + recentNode.getAttribute("xmlns"));
		
		NodeList nodeList = recentNode.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
			if(node.getNodeType()==Node.ELEMENT_NODE) {
				Element elem = (Element) node;
				this.groupName = elem.getAttribute("name");
				
				NodeList groupNodeList = elem.getChildNodes();
				for(int j=0;j<groupNodeList.getLength();j++) {
					Node groupNode = groupNodeList.item(j);
					
					if(groupNode.getNodeType()==groupNode.ELEMENT_NODE) {
						Element gElem = (Element) groupNode;
						System.out.println(gElem.getAttribute("name"));
						this.groupList.add(gElem);
					}
					
				}
			}
		}
	}
	
	public void loadClusterData(File file) throws ParserConfigurationException, SAXException, IOException {
		this.sourceFile = file;
		initData();
	}
	
	public void loadClusterData(String fileName) throws ParserConfigurationException, SAXException, IOException {
		this.sourceFile = new File(fileName);
		initData();
	}
	
	public void saveClusterData() {
		//to be implemented
	}

//give whole tree
	public Element getRoot() {
		return doc.getDocumentElement();
	}

//give group node with given name(if there's no node with the name, return null)
	public Element getGroup(String groupName) {
		for(Element group : groupList) {
			if(group.getAttribute("name").equals(groupName)) {
				return group;
			}
		}
		return null;
	}
	
	public void showTree() {
		NodeList list = doc.getChildNodes();
		
		for(int i=0;i<list.getLength();i++) {
			if(list.item(i).getNodeType()==Node.ELEMENT_NODE)
				System.out.println(((Element) list.item(i)).getAttribute("Name"));
		}
	}
}
