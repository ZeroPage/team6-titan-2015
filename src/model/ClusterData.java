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
	public TreeNode getTree() {
		return this.treeRoot;
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
	public void moveNode(String nodeName, int newIndex) {
		DefaultMutableTreeNode node = findNode(this.treeRoot,nodeName); //NotFoundException required
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)node.getParent();
		parent.insert(node, newIndex);
	}

//group multiple nodes(group or item) into new group, and add it as a new child of parental group	
	public void newGroup(ArrayList<String> nodeName, String newGroupName) {
		DefaultMutableTreeNode firstElem = findNode(this.treeRoot,nodeName.get(0));
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)firstElem.getParent();
		int groupIndex = parent.getIndex(firstElem);
		DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(newGroupName,true);
		for(int i=nodeName.size()-1;i>=0;i--) {
			DefaultMutableTreeNode child = findNode(parent,nodeName.get(i));//NotFoundException required(All node to be regrouped should be the child of same parent node)
			if(child==null||parent.getIndex(child)==-1) {
				//Exception
			}
			newGroup.insert(child,0);
		}
		parent.insert(newGroup, groupIndex);
	}

//free the group, and add all the children as the child of parent group
	public void freeGroup(String nodeName) {
		DefaultMutableTreeNode node = findNode(this.treeRoot,nodeName); //NotFoundException required
		if(node==null) {
			//Exception
		}
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)node.getParent();
		int cursor = parent.getIndex(node);
		while(node.getChildCount()!=0) {
			parent.insert((MutableTreeNode)node.getFirstChild(),cursor);
			cursor++;
		}
		parent.remove(node);
	}

//rename the particular item or group
	public void renameNode(String nodeName, String newName) {
		DefaultMutableTreeNode node = findNode(this.treeRoot,nodeName); //NotFoundException required
		if(node==null) {
			//Exception
		}
		node.setUserObject(newName);
	}
	
	public void addItem(String groupName, String itemName) {
		DefaultMutableTreeNode group = findNode(this.treeRoot,groupName);
		if(group==null) {
			//Exception
		}
		group.add(new DefaultMutableTreeNode(groupName,false));
	}
	
	public void deleteItem(String itemName) {
		DefaultMutableTreeNode node = findNode(this.treeRoot,itemName);
		if(node==null) {
			//Exception
		}
		node.removeFromParent();
	}
}
