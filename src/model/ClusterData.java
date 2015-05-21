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

public class ClusterData {
	private File sourceFile;
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
		Document doc=null;
		try {
			xmlBuilder = xmlFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// To be implemented...
		}
		try {
			doc = xmlBuilder.parse(this.sourceFile);
		} catch (SAXException e) {
			// To be implemented...
		}
		doc.getDocumentElement().normalize();
		
		this.treeRoot = new DefaultMutableTreeNode(true);
		buildTree(doc.getDocumentElement(),treeRoot);
	}
	
//Build tree recursively
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
			if(elem.hasChildNodes())
				buildTree(elem,newNode);
			treeNode.add(newNode);
		}
	}
	
	private void refresh() {
		//FRRRRRRRRRRRRESH!
	}
	
	public TreeNode getTree() {
		return this.treeRoot;
	}
	
	public void setTree(TreeNode newTree) {
		//To be implemented...
		refresh();
	}
	
	public void saveClusterData() {//Will be Modified
/*		TransformerFactory tFac = TransformerFactory.newInstance();
		Transformer transformer = tFac.newTransformer();
		DOMSource source = new DOMSource(this.doc);
		Result output = new StreamResult(this.sourceFile);
		
		this.sourceFile.delete();
		transformer.transform(source, output);*/
	}
}
