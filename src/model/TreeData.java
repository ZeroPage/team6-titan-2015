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
	
	public boolean getGroupDSM(DefaultMutableTreeNode group, DefaultMutableTreeNode elem) {
		boolean result = false;
		if(!elem.getAllowsChildren()) {
			for(int i=0;i<group.getChildCount();i++) {
				DefaultMutableTreeNode groupItem = (DefaultMutableTreeNode)group.getChildAt(i);
				String rowName = groupItem.getUserObject().toString();
				String colName = elem.getUserObject().toString();
				if(result = dsmData.getData(rowName,colName)) {
					return result;
				}
			}
		} else {
			for(int i=0;i<group.getChildCount();i++) {
				DefaultMutableTreeNode groupItem = (DefaultMutableTreeNode)group.getChildAt(i);
				String rowName = groupItem.getUserObject().toString();
				for(int j=0;j<elem.getChildCount();j++) {
					DefaultMutableTreeNode elemItem = (DefaultMutableTreeNode)elem.getChildAt(j);
					String colName = elemItem.getUserObject().toString();
					if(result = dsmData.getData(rowName, colName)) {
						return result;
					}
				}
			}
		}
		return result;
	}
	
	
	public void loadDSM(String dsmFileName) throws IOException, WrongDSMFormatException{
		this.dsmData = new TitanDSM(new File(dsmFileName));
		if(cluster == null) {
			buildDefaultTree();
		} else {
			cluster.refresh(this.dsmData);
		}
	}
	
	public boolean getDSMvalue(DefaultMutableTreeNode row, DefaultMutableTreeNode col) {
		String rowName = row.getUserObject().toString();
		String colName = col.getUserObject().toString();
		return dsmData.getData(rowName,colName);
	}

//rename the element(Group, Item both)
	public void renameElem(DefaultMutableTreeNode currentNode, String newName) throws ItemAlreadyExistException, NodeNotFoundException {
		if(!currentNode.getAllowsChildren()) {
			dsmData.setName(newName, currentNode.getUserObject().toString());
		}
		cluster.renameNode(currentNode, newName);
	}
	
	public void repositionElem(DefaultMutableTreeNode elemNode,int newIndex) throws NodeNotFoundException {
		cluster.moveNode(elemNode, newIndex);
		//Does DSM has something to do with this method?
	}
	
	public void removeElem(DefaultMutableTreeNode elemNode) throws NodeNotFoundException {
		if(elemNode.getAllowsChildren()) {
			//Case 1: the element was group - subtree has to be deleted.
		} else {
			//Case 2: the element was item - delete only stated element.
		}
		cluster.deleteItem(elemNode);
	}
	
	public void addElem(DefaultMutableTreeNode groupNode, String itemName) throws NodeNotFoundException {
		cluster.addItem(groupNode, itemName);
		//DSM team, Plz add your codes that are needed.
	}
	
	public void groupElem(ArrayList<DefaultMutableTreeNode> elemList, String groupName) {
		cluster.newGroupbyNode(elemList, groupName);
	}
	
	public void freeGroup(DefaultMutableTreeNode groupNode) throws NodeNotFoundException {
		cluster.freeGroup(groupNode);
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
	
	public void saveDSMData(File dsmFile) throws IOException{
		this.dsmData.saveToFile(dsmFile);
	}
	
	public void saveClusterData(File clusterFile) throws IOException{
		this.cluster.saveClusterData(clusterFile);
	}
	
	public void saveData(File dsmFile, File clusterFile) throws IOException{
		this.dsmData.saveToFile(dsmFile);
		this.cluster.saveClusterData(clusterFile);
	}
}
