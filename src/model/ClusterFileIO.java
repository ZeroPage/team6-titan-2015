package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.tree.*;

public class ClusterFileIO {
	private static ClusterFileIO instance = new ClusterFileIO();
	private static final String namespace = "http://rise.cs.drexel.edu/minos/clsx";
	
	private ClusterFileIO() {
		//use getInstance method instead
	}
	
	public static ClusterFileIO getInstance() {
		return instance;
	}
	
	public void saveClusterData(File path, TreeNode treeRoot) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(path,false));
		out.write("<cluster xmlns=\"http://rise.cs.drexel.edu/minos/clsx\">");
		out.newLine();
		for(int i=0;i<treeRoot.getChildCount();i++) {
			writeGroup(out,(DefaultMutableTreeNode)treeRoot.getChildAt(0));
		}
		out.append("</cluster>");
		out.close();
	}
	
	private void writeGroup(BufferedWriter out, DefaultMutableTreeNode node) throws IOException {
		String attributeName = node.getUserObject().toString();
		if(node.getAllowsChildren()) {
			out.append("<group name=\"" + attributeName + "\">");
			out.newLine();
			for(int i=0;i<node.getChildCount();i++) {
				writeGroup(out,(DefaultMutableTreeNode)node.getChildAt(i));
			}
			out.append("</group>");
			out.newLine();
		} else {
			out.append("<item name=\"" + attributeName + "\" />");
			out.newLine();
		}
	}
	
	public DefaultMutableTreeNode loadClusterData(File source) throws IOException, WrongXMLNamespaceException {
		DefaultMutableTreeNode treeRoot;
		
		DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder xmlBuilder = null;
		Document doc = null;
		try {
			xmlBuilder = xmlFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// To be implemented...
		}
		try {
			doc = xmlBuilder.parse(source);
		} catch (SAXException e) {
			// To be implemented...
		}
		doc.getDocumentElement().normalize();
		Element tempclsx = (Element) doc.getChildNodes().item(0);
		if(!tempclsx.getAttribute("xmlns").equals(namespace)) {
			throw new WrongXMLNamespaceException();
		}
		
		treeRoot = new DefaultMutableTreeNode("",true);
		buildTree(doc.getDocumentElement(),treeRoot);
		treeRoot = (DefaultMutableTreeNode)treeRoot.getFirstChild();
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
				newNode = new DefaultMutableTreeNode(elem.getAttribute("name"),elem.hasChildNodes());
				if(newNode.getAllowsChildren())
					buildTree(elem,newNode);
				treeNode.add(newNode);
			}
		}
	}
}
