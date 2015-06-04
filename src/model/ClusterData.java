package model;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.tree.*;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import model.exception.WrongXMLNamespaceException;
import util.ClusterFileIO;

public class ClusterData {
	private DefaultMutableTreeNode treeRoot;
	
	public ClusterData(DSMData dsmData) {
		this.treeRoot = new DefaultMutableTreeNode("ROOT",true);
		
		for(int i=0;i<dsmData.getSize();i++) {
			treeRoot.add(new DefaultMutableTreeNode(dsmData.getName(i),false));
		}
	}
	
//initializing with File instance	
	public ClusterData(File source) throws IOException, WrongXMLNamespaceException, ParserConfigurationException, SAXException {
		ClusterFileIO reader = ClusterFileIO.getInstance();
		this.treeRoot = reader.loadClusterData(source);
	}

	public ClusterData(DefaultMutableTreeNode root) {
		root.removeFromParent();
		treeRoot = root;
	}
	
//load new .clsx file
	public void loadClusterData(File source) throws IOException, WrongXMLNamespaceException, ParserConfigurationException, SAXException {
		ClusterFileIO reader = ClusterFileIO.getInstance();
		this.treeRoot = reader.loadClusterData(source);
	}

//gives root of the Node(<cluster> level)
	public DefaultMutableTreeNode getTreeRoot() {
		return this.treeRoot;
	}
	
	public boolean getAllowsChildren(String nodeName) throws NoSuchElementException {
		DefaultMutableTreeNode node = findNode(this.treeRoot,nodeName);
		if(node==null) {throw new NoSuchElementException();}
		
		return node.getAllowsChildren();
	}
	
	public TreeNode getNode(String nodeName) throws NoSuchElementException {
		DefaultMutableTreeNode node = findNode(this.treeRoot,nodeName);
		if(node==null) {throw new NoSuchElementException();}
		
		return node;
	}

//refactor the tree with dsmData
	public void refresh(DSMData dsmData) {
		DefaultMutableTreeNode leaf = this.treeRoot.getFirstLeaf();
		
		while(leaf!=null) {
			if(leaf.getAllowsChildren()) {
				leaf = leaf.getNextLeaf();
			} else {
				if(!dsmData.isExist(leaf.getUserObject().toString())) {
					leaf.removeFromParent();
				}
			}
			leaf = leaf.getNextLeaf();
		}
	}
	
//saving clusterData
	public void saveClusterData(File path) throws IOException {
		ClusterFileIO writer = ClusterFileIO.getInstance();
		writer.saveClusterData(path, this.treeRoot);
	}


//moving a node(group or item) to new index	
	public void moveNode(DefaultMutableTreeNode node, int newIndex) {
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)node.getParent();
		parent.insert(node, newIndex);
	}

//group multiple nodes(group or item) into new group, and add it as a new child of parental group
	public void newGroupbyNode(ArrayList<DefaultMutableTreeNode> nodeArr,String newGroupName) {
		DefaultMutableTreeNode firstElem = nodeArr.get(0);
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)firstElem.getParent();
		
		for(int i=1;i<nodeArr.size();i++) {
			int index=0;
			int comparison = parent.getIndex(nodeArr.get(index));
			int target = parent.getIndex(nodeArr.get(i));
			while(comparison<target&&index<i) {
				index++;
				comparison = parent.getIndex(nodeArr.get(index));
			}
			DefaultMutableTreeNode targetNode = nodeArr.get(i);
			nodeArr.add(index, targetNode);
			nodeArr.remove(i+1);
		}
		
		firstElem = nodeArr.get(0);
		
		int groupIndex = parent.getIndex(firstElem);
		DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(newGroupName,true);
		
		for(int i=nodeArr.size()-1;i>=0;i--) {
			DefaultMutableTreeNode child = nodeArr.get(i);
			newGroup.insert(child,0);
		}
		
		parent.insert(newGroup, groupIndex);
	}

//free the group, and add all the children as the child of parent group
	public void freeGroup(DefaultMutableTreeNode node) {
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)node.getParent();
		int cursor = parent.getIndex(node);
		while(node.getChildCount()!=0) {
			parent.insert((MutableTreeNode)node.getFirstChild(),cursor);
			cursor++;
		}
		parent.remove(node);
	}

//rename the particular item or group
	public void renameNode(DefaultMutableTreeNode node, String newName) {
		node.setUserObject(newName);
	}
	
	public void addItem(DefaultMutableTreeNode groupNode, String itemName) {
		groupNode.add(new DefaultMutableTreeNode(itemName,false));
	}
	
	public void deleteItem(DefaultMutableTreeNode node) {
		node.removeFromParent();
	}
		
	private DefaultMutableTreeNode findNode(DefaultMutableTreeNode node, String nodeName) {
		DefaultMutableTreeNode targetNode = null;
		DefaultMutableTreeNode temp;
		
		if(node.getUserObject().equals(nodeName)) {
			targetNode = node;
		} else if(node.getAllowsChildren()) {
			for(int i=0;i<node.getChildCount();i++) {
				temp = findNode((DefaultMutableTreeNode)node.getChildAt(i),nodeName);
				if(temp!=null) {
					targetNode = temp;
					break;
				}
			}
		}
		return targetNode;
	}
	
	public boolean isExists(String itemName) {
		Enumeration<DefaultMutableTreeNode> itemList;
		ArrayList<String> nameList = new ArrayList<String>();
		DefaultMutableTreeNode tempNode=null;
		
		itemList = this.treeRoot.preorderEnumeration();
		
		while(itemList.hasMoreElements()) {
			tempNode = itemList.nextElement();
			if(!tempNode.getAllowsChildren()) {
				nameList.add(tempNode.getUserObject().toString());
			}
		}
		
		if(nameList.contains(itemName)) {
			return true;
		}
		return false;
	}
}
