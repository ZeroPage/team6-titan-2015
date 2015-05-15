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

public class ClusterData {
	private Document doc;
	private File sourceFile;
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
	
//load new .clsx file
	public void loadClusterData(File file) throws ParserConfigurationException, SAXException, IOException {
		this.sourceFile = file;
		initData();
	}
	
	public void loadClusterData(String fileName) throws ParserConfigurationException, SAXException, IOException {
		this.sourceFile = new File(fileName);
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
	
	public void saveClusterData() throws TransformerException {
		TransformerFactory tFac = TransformerFactory.newInstance();
		Transformer transformer = tFac.newTransformer();
		DOMSource source = new DOMSource(this.doc);
		Result output = new StreamResult(this.sourceFile);
		
		this.sourceFile.delete();
		transformer.transform(source, output);
	}

//give whole tree
	public Element getRoot() {
		return doc.getDocumentElement();
	}

//give group node with given name(if there's no node with the name, return null)
	public Element getGroup(String groupName) {
		for(int i=0;i<this.groupList.size();i++) {
			Element group = groupList.get(i);
			if(group.getAttribute("name").equals(groupName)) {
				return group;
			}
		}
		return null;
	}

//get specific item recognized with groupName and itemName
	public Element getItem(String groupName, String itemName) {
		Element groupLevel = getGroup(groupName);
		NodeList inGroup = groupLevel.getChildNodes();
		for(int i=0;i<inGroup.getLength();i++) {
			Node item=inGroup.item(i);
			if(item.getNodeType()==Node.ELEMENT_NODE) {
				Element elemItem = (Element) item;
				if(elemItem.getAttribute("name").equals(itemName))
					return elemItem;
			}
		}
		return null;
	}
	
	public ArrayList<Element> getAllGroup() {
		return groupList;
	}
}
