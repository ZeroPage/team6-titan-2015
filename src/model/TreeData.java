package model;

import java.io.File;
import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class TreeData {
	private TitanDSM dsmData;
	private ClusterData cluster;
	private DefaultMutableTreeNode treeRoot;
	
	public TreeData(File dsmFile) throws IOException, WrongDSMFormatException {
		dsmData = new TitanDSM(dsmFile);
		cluster = null;
		treeRoot = null;
	}
	
	public void loadClusterData(File clsxFile) throws IOException, WrongXMLNamespaceException {
		cluster = new ClusterData(clsxFile);
		buildTree(cluster.getTree());
	}
	
	private DefaultMutableTreeNode buildTree() {
		DefaultMutableTreeNode node=null;//This is temporary implementation.
		
		//IMPLEMENT REQUIRED
		
		return node;
	}
	
	private void buildTree(TreeNode clsxTree) {
		
		//IMPLEMENT REQUIRED
		
	}
	
	//get,set,save,load to be implemented. Plz get on work.
}
