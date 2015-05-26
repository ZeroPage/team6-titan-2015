package model;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class TreeData {
	private TitanDSM dsmData;
	private ClusterData cluster;
	private TreeNode treeRoot;

//initializing with only DSM
	public TreeData(File dsmFile) throws IOException, WrongDSMFormatException {
		dsmData = new TitanDSM(dsmFile);
		cluster = null;
		treeRoot = buildDefaultTree();
	}

//load clsx, and rebuild the data tree structure
	public void loadClusterData(File clsxFile) throws IOException, WrongXMLNamespaceException {
		cluster = new ClusterData(clsxFile);
		cluster.refresh(this.dsmData);
		treeRoot = cluster.getTree();
	}
	
	
	public void loadDSM(String dsmFileName) throws IOException, WrongDSMFormatException{
		this.dsmData = new TitanDSM(new File(dsmFileName));
		if(cluster == null) {
			buildDefaultTree();
		} else {
			cluster.refresh(this.dsmData);
		}
	}

//rename the element(Group, Item both)
	public void renameElem(String currentName, String newName) throws ItemAlreadyExistException, NodeNotFoundException {
		cluster.renameNode(currentName, newName);
		if(!cluster.getAllowsChildren(newName)) {
			dsmData.setName(newName, dsmData.getIndexByName(currentName));
		}
	}
	
	public void repositionElem(String elemName,int newIndex) throws NodeNotFoundException {
		cluster.moveNode(elemName, newIndex);
		//Does DSM has something to do with this method?
	}
	
	public void removeElem(String elemName) throws NodeNotFoundException {
		if(cluster.getAllowsChildren(elemName)) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)cluster.getNode(elemName);
			//Case 1: the element was group - subtree has to be deleted.
		} else {
			//Case 2: the element was item - delete only stated element.
		}
		cluster.deleteItem(elemName);
	}
	
	public void addElem(String groupName, String itemName) throws NodeNotFoundException {
		cluster.addItem(groupName, itemName);
		//DSM team, Plz add your codes that are needed.
	}
	
	public void groupElem(ArrayList<String> elemList, String groupName) throws NodeNotFoundException {
		cluster.newGroup(elemList, groupName);
	}
	
	public void freeGroup(String groupName) throws NodeNotFoundException {
		cluster.freeGroup(groupName);
	}
	
//build temporary cluster with DSM only.
	private TreeNode buildDefaultTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("ROOT",true);
		for(int i=0;i<this.dsmData.getSize();i++) {
			root.add(new DefaultMutableTreeNode(this.dsmData.getName(i),false));
		}
		return root;
	}
	
   public TreeNode getTree(){
		return this.treeRoot;
	} 
	
	public void saveDSMData(String dsmFileName) throws IOException{
		this.dsmData.saveToFile(dsmFileName);
	}
	
	public void saveClusterData(File clusterFile) throws IOException{
		this.cluster.saveClusterData(clusterFile);
	}
	
	public void saveData(String dsmFileName, File clusterFile) throws IOException{
		this.dsmData.saveToFile(dsmFileName);
		this.cluster.saveClusterData(clusterFile);
	}
}
