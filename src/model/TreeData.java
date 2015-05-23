package model;

import java.util.*;
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
		DefaultMutableTreeNode root = new DefaultMutableTreeNode (" root ");
		Vector<DefaultMutableTreeNode>vec = new Vector<DefaultMutableTreeNode>();//객체생성
		int count = dsmData.getSize();//수정 확인바람
		for(int j=0;j<count ;j++){
		String word = dsmData.getName(j);
		vec.add(new DefaultMutableTreeNode(word));
		root.add(vec.get(j));
		}
		//IMPLEMENT REQUIRED
		
		return root;
	}
	
	private void buildTree(TreeNode clsxTree) {
		
		//IMPLEMENT REQUIRED
		
	}
	
   public void get(){
		
		
	}
	
	public void set(){
		
		
	}
	
	public void save(){
		
	
	}
	
	public void load(){
		
	
	}
	//get,set,save,load to be implemented. Plz get on work.
}
