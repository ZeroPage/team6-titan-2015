package controller;

import java.util.*;

import javax.swing.tree.DefaultMutableTreeNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ClusterTreeFactory {
	private static ClusterTreeFactory factoryInstance = new ClusterTreeFactory();
	
	private ClusterTreeFactory() {
		//Overriding default constructor with private constructor
	}
	
	public static ClusterTreeFactory getInstance() {
		return factoryInstance;
	}

//RKTP's part from this line
	public Document buildDocInstance() {
		//devoted to RKTP
		return null;
	}

//Yong-ho's part from this line
	public DefaultMutableTreeNode buildNodeInstance() {
		//devoted to Yongho
		return null;
	}

}
