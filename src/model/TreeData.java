package model;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.NoSuchElementException;

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

	public TreeData(TreeData original, DefaultMutableTreeNode newRoot) { // Forking
		dsmData = original.dsmData;
		cluster = new ClusterData(newRoot);
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
				result = getItemItemValue(rowElement,columnElement);
			}
		}

		return result;
	}
	
	private boolean getGroupGroupValue(DefaultMutableTreeNode rGroup, DefaultMutableTreeNode cGroup) {
		boolean result = false;
		
		boolean isR, isC;
		isR = rGroup.getFirstChild().getAllowsChildren();
		isC = cGroup.getFirstChild().getAllowsChildren();
		
		if(isR&isC) {
			for(int i=0;i<cGroup.getChildCount();i++) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode)cGroup.getChildAt(i);
				if(result = getGroupGroupValue(rGroup,child)) {
					return result;
				}
			}
		} else if(isR) {
			for(int i=0;i<rGroup.getChildCount();i++) {
				DefaultMutableTreeNode item = (DefaultMutableTreeNode)cGroup.getChildAt(i);
				if(result = getGroupItemValue(rGroup,item)) {
					return result;
				}
			}
		} else if(isC) {
			for(int i=0;i<cGroup.getChildCount();i++) {
				DefaultMutableTreeNode item = (DefaultMutableTreeNode)rGroup.getChildAt(i);
				if(result = getItemGroupValue(item,cGroup)) {
					return result;
				}
			}
		} else {
			for(int i=0;i<rGroup.getChildCount();i++) {
				DefaultMutableTreeNode item = (DefaultMutableTreeNode)rGroup.getChildAt(i);
				if(result = getItemGroupValue(item,cGroup)) {
					return result;
				}
			}
		}
		return result;
	}
	
	private boolean getGroupItemValue(DefaultMutableTreeNode group, DefaultMutableTreeNode element) {
		boolean  result = false;
		
		if(group.getFirstChild().getAllowsChildren()) {
			for(int i=0;i<group.getChildCount();i++) {
				if(result = getGroupItemValue((DefaultMutableTreeNode)group.getChildAt(i),element)) {
					return result;
				}
			}
		} else {
			for(int i=0;i<group.getChildCount();i++) {
				DefaultMutableTreeNode item = (DefaultMutableTreeNode)group.getChildAt(i);
				if(result = getItemItemValue(item, element)) {
					return result;
				}
			}
		}
		return result;
	}
	
	private boolean getItemGroupValue(DefaultMutableTreeNode element, DefaultMutableTreeNode group) {
		boolean result = false;
		
		if(group.getFirstChild().getAllowsChildren()) {
			for(int i=0;i<group.getChildCount();i++) {
				if(result = getItemGroupValue(element,(DefaultMutableTreeNode)group.getChildAt(i))) {
					return result;
				}
			}
		} else {
			for(int i=0;i<group.getChildCount();i++) {
				DefaultMutableTreeNode item = (DefaultMutableTreeNode)group.getChildAt(i);
				if(result = getItemItemValue(element, item)) {
					return result;
				}
			}
		}
		return result;
	}
	
	private boolean getItemItemValue(DefaultMutableTreeNode rowElement, DefaultMutableTreeNode columnElement) {
		boolean result = false;
		
		String row = rowElement.getUserObject().toString();
		String column = columnElement.getUserObject().toString();
		
		result = dsmData.getData(row, column);
			
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
	public void renameElement(DefaultMutableTreeNode currentNode, String newName) throws ItemAlreadyExistException, NoSuchElementException {
		if(!currentNode.getAllowsChildren()) {
			dsmData.setName(newName, currentNode.getUserObject().toString());
		}
		cluster.renameNode(currentNode, newName);
	}
	
	public void repositionElement(DefaultMutableTreeNode elementNode,int newIndex) throws NoSuchElementException {
		cluster.moveNode(elementNode, newIndex);
		//Does DSM has something to do with this method?
	}
	
	public void removeElement(DefaultMutableTreeNode elementNode) throws NoSuchElementException {
		if(elementNode.getAllowsChildren()) {
			//Case 1: the element was group - subtree has to be deleted.
		} else {
			//Case 2: the element was item - delete only stated element.
		}
		cluster.deleteItem(elementNode);
	}
	
	public void addElement(DefaultMutableTreeNode groupNode, String itemName) throws NoSuchElementException {
		cluster.addItem(groupNode, itemName);
		//DSM team, Plz add your codes that are needed.
	}
	
	public void groupElement(ArrayList<DefaultMutableTreeNode> elementList, String groupName) {
		cluster.newGroupbyNode(elementList, groupName);
	}
	
	public void freeGroup(DefaultMutableTreeNode groupNode) throws NoSuchElementException {
		cluster.freeGroup(groupNode);
	}
	
	public void setDSMData(DefaultMutableTreeNode rowNode, DefaultMutableTreeNode columnNode, Boolean value) {
		String row = rowNode.getUserObject().toString();
		String column = columnNode.getUserObject().toString();
		dsmData.setData(value, row, column);
	}
	
//build temporary cluster with DSM only.
	public DefaultMutableTreeNode buildDefaultTree() {
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
