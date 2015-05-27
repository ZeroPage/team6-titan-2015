package model;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.tree.*;

public class ClusterData {
	private DefaultMutableTreeNode treeRoot;
	
	public ClusterData() {
		System.out.println("I need a directory of the .clsx file!");
	}
	
//initializing with File instance	
	public ClusterData(File source) throws IOException, WrongXMLNamespaceException {
		ClusterFileIO reader = ClusterFileIO.getInstance();
		this.treeRoot = reader.loadClusterData(source);
	}
	
//load new .clsx file
	public void loadClusterData(File source) throws IOException, WrongXMLNamespaceException {
		ClusterFileIO reader = ClusterFileIO.getInstance();
		this.treeRoot = reader.loadClusterData(source);
	}

//gives root of the Node(<cluster> level)
	public DefaultMutableTreeNode getTree() {
		return this.treeRoot;
	}

//refactor the tree with dsmData
	public void refresh(TitanDSM dsmData) {
		DefaultMutableTreeNode leaf = this.treeRoot.getFirstLeaf();
		while(leaf!=null) {
			if(leaf.getAllowsChildren()) {
				leaf = leaf.getNextLeaf();
			} else {
				if(!dsmData.isExist(leaf.getUserObject().toString())) {
					leaf.removeFromParent();
				}
			}
		}
	}
	
//saving clusterData
	public void saveClusterData(File path) throws IOException {
		ClusterFileIO writer = ClusterFileIO.getInstance();
		writer.saveClusterData(path, this.treeRoot);
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

//moving a node(group or item) to new index	
	public void moveNode(String nodeName, int newIndex) throws NodeNotFoundException {
		DefaultMutableTreeNode node = findNode(this.treeRoot,nodeName);
		if(node==null) {throw new NodeNotFoundException();}
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)node.getParent();
		parent.insert(node, newIndex);
	}
	
	public void moveNode(DefaultMutableTreeNode node, int newIndex) {
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)node.getParent();
		parent.insert(node, newIndex);
	}

	
//Added for DSM search - whether item or not
	public boolean getAllowsChildren(String nodeName) throws NodeNotFoundException {
		DefaultMutableTreeNode node = findNode(this.treeRoot,nodeName);
		if(node==null) {throw new NodeNotFoundException();}
		return node.getAllowsChildren();
	}
	
	public TreeNode getNode(String nodeName) throws NodeNotFoundException {
		DefaultMutableTreeNode node = findNode(this.treeRoot,nodeName);
		if(node==null) {throw new NodeNotFoundException();}
		return node;
	}

//group multiple nodes(group or item) into new group, and add it as a new child of parental group	
	public void newGroupbyName(ArrayList<String> nodeName, String newGroupName) throws NodeNotFoundException {
		DefaultMutableTreeNode firstElem = findNode(this.treeRoot,nodeName.get(0));
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)firstElem.getParent();
		int groupIndex = parent.getIndex(firstElem);
		DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(newGroupName,true);
		for(int i=nodeName.size()-1;i>=0;i--) {
			DefaultMutableTreeNode child = findNode(parent,nodeName.get(i));
			if(child==null||parent.getIndex(child)==-1) {
				throw new NodeNotFoundException();
			}
			newGroup.insert(child,0);
		}
		parent.insert(newGroup, groupIndex);
	}
	
	public void newGroupbyNode(ArrayList<DefaultMutableTreeNode> nodeArr,String newGroupName) {
		DefaultMutableTreeNode firstElem = nodeArr.get(0);
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)firstElem.getParent();
		int groupIndex = parent.getIndex(firstElem);
		DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(newGroupName,true);
		for(int i=nodeArr.size()-1;i>=0;i--) {
			DefaultMutableTreeNode child = nodeArr.get(i);
			newGroup.insert(child,0);
		}
		parent.insert(newGroup, groupIndex);
	}

//free the group, and add all the children as the child of parent group
	public void freeGroup(String nodeName) throws NodeNotFoundException {
		DefaultMutableTreeNode node = findNode(this.treeRoot,nodeName);
		if(node==null) {
			throw new NodeNotFoundException();
		}
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)node.getParent();
		int cursor = parent.getIndex(node);
		while(node.getChildCount()!=0) {
			parent.insert((MutableTreeNode)node.getFirstChild(),cursor);
			cursor++;
		}
		parent.remove(node);
	}
	
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
	public void renameNode(String nodeName, String newName) throws NodeNotFoundException {
		DefaultMutableTreeNode node = findNode(this.treeRoot,nodeName);
		if(node==null) {
			throw new NodeNotFoundException();
		}
		node.setUserObject(newName);
	}
	
	public void renameNode(DefaultMutableTreeNode node, String newName) {
		node.setUserObject(newName);
	}
	
	public void addItem(String groupName, String itemName) throws NodeNotFoundException {
		DefaultMutableTreeNode group = findNode(this.treeRoot,groupName);
		if(group==null) {
			throw new NodeNotFoundException();
		}
		group.add(new DefaultMutableTreeNode(itemName,false));
	}
	
	public void addItem(DefaultMutableTreeNode groupNode, String itemName) {
		groupNode.add(new DefaultMutableTreeNode(itemName,false));
	}
	
	public void deleteItem(String itemName) throws NodeNotFoundException {
		DefaultMutableTreeNode node = findNode(this.treeRoot,itemName);
		if(node==null) {
			throw new NodeNotFoundException();
		}
		node.removeFromParent();
	}
	
	public void deleteItem(DefaultMutableTreeNode node) {
		node.removeFromParent();
	}
}
