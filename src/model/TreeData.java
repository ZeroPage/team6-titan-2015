package model;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class TreeData {
	private TitanDSM dsmData;
	private ClusterData cluster;

//initializing with only DSM
	public TreeData(File dsmFile) throws IOException, WrongDSMFormatException {
		dsmData = new TitanDSM(dsmFile);
		cluster = new ClusterData(this.dsmData);
	}
	
	public TreeData(int size) throws IOException, WrongDSMFormatException, NotPositiveException {
		dsmData = new TitanDSM(size);
		cluster = new ClusterData(this.dsmData);
	}

//load clsx, and rebuild the data tree structure
	public void loadClusterData(File clsxFile) throws IOException, WrongXMLNamespaceException {
		cluster = new ClusterData(clsxFile);
		cluster.refresh(this.dsmData);
	}
	
	public boolean getDSMValue(DefaultMutableTreeNode rowElement, DefaultMutableTreeNode columnElement) {
		boolean result = false;
		
		if(rowElement.getAllowsChildren()) {
			if(columnElement.getAllowsChildren()) {
				result = getGroupGroupValue(rowElement,columnElement);
			} else {
				result = getGroupItemValue(rowElement, columnElement);
			}
			
		} else {
			if(columnElement.getAllowsChildren()) {
				result = getItemGroupValue(columnElement, rowElement);
			} else {
				String row = rowElement.getUserObject().toString();
				String column = columnElement.getUserObject().toString();
				result = dsmData.getData(row, column);
			}
		}

		return result;
	}
	
	private boolean getGroupGroupValue(DefaultMutableTreeNode rGroup, DefaultMutableTreeNode cGroup) {
		boolean result = false;
		
		for(int i=0;i<rGroup.getChildCount();i++) {
			DefaultMutableTreeNode rItem = (DefaultMutableTreeNode)rGroup.getChildAt(i);
			String row = rItem.getUserObject().toString();
			for(int j=0;j<cGroup.getChildCount();j++) {
				DefaultMutableTreeNode cItem = (DefaultMutableTreeNode)cGroup.getChildAt(j);
				String column = cItem.getUserObject().toString();
				if(result = dsmData.getData(row, column)) {
					return result;
				}
			}
		}
		return result;
	}
	
	private boolean getGroupItemValue(DefaultMutableTreeNode group, DefaultMutableTreeNode element) {
		boolean result = false;
		
		for(int i=0;i<group.getChildCount();i++) {
			DefaultMutableTreeNode item = (DefaultMutableTreeNode)group.getChildAt(i);
			String row = item.getUserObject().toString();
			String column = element.getUserObject().toString();
			if(result = dsmData.getData(row,column)) {
				return result;
			}
		}
		return result;
	}
	
	private boolean getItemGroupValue(DefaultMutableTreeNode element, DefaultMutableTreeNode group) {
		boolean result = false;
		
		for(int i=0;i<group.getChildCount();i++) {
			DefaultMutableTreeNode item = (DefaultMutableTreeNode)group.getChildAt(i);
			String row = element.getUserObject().toString();
			String column = item.getUserObject().toString();
			if(result = dsmData.getData(row,column)) {
				return result;
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

//rename the element(Group, Item both)
	public void renameElement(DefaultMutableTreeNode currentNode, String newName) throws ItemAlreadyExistException, NodeNotFoundException {
		if(!currentNode.getAllowsChildren()) {
			dsmData.setName(newName, currentNode.getUserObject().toString());
		}
		cluster.renameNode(currentNode, newName);
	}
	
	public void repositionElement(DefaultMutableTreeNode elementNode,int newIndex) throws NodeNotFoundException {
		cluster.moveNode(elementNode, newIndex);
		//Does DSM has something to do with this method?
	}
	
	public void removeElement(DefaultMutableTreeNode elementNode) throws NodeNotFoundException {
		if(elementNode.getAllowsChildren()) {
			//Case 1: the element was group - subtree has to be deleted.
		} else {
			//Case 2: the element was item - delete only stated element.
		}
		cluster.deleteItem(elementNode);
	}
	
	public void addElement(DefaultMutableTreeNode groupNode, String itemName) throws NodeNotFoundException {
		cluster.addItem(groupNode, itemName);
		//DSM team, Plz add your codes that are needed.
	}
	
	public void groupElement(ArrayList<DefaultMutableTreeNode> elementList, String groupName) {
		cluster.newGroupbyNode(elementList, groupName);
	}
	
	public void freeGroup(DefaultMutableTreeNode groupNode) throws NodeNotFoundException {
		cluster.freeGroup(groupNode);
	}
	
//build temporary cluster with DSM only.
	private DefaultMutableTreeNode buildDefaultTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("ROOT",true);
		for(int i=0;i<this.dsmData.getSize();i++) {
			root.add(new DefaultMutableTreeNode(this.dsmData.getName(i),false));
		}
		return root;
	}
	
   public DefaultMutableTreeNode getTree(){
		return this.cluster.getTree();
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
