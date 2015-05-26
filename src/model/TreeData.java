package model;

import java.util.*;
import java.io.File;
import java.io.IOException;

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
		cluster.refresh(dsmData);
		treeRoot = cluster.getTree();
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
	
	public void set(){
		
		
	}
	
	public void save(){
		
	
	}
	
	public void load(){
		
	
	}
	//get,set,save,load to be implemented. Plz get on work.
}
