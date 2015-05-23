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
		DefaultMutableTreeNode root = new DefaultMutableTreeNode (" root ");
		Vector<DefaultMutableTreeNode>vec = new Vector<DefaultMutableTreeNode>();//백터객체생성
		String []data ={"A", "B","C","D","E",};//dsm string 가정
		for(int i=0;i<data.length;i++){
			vec.add(new DefaultMutableTreeNode(data[i]));
			root.add(vec.get(i));
		}
		//IMPLEMENT REQUIRED
		
		return root;
	}
	
	private void buildTree(TreeNode clsxTree) {
		
		//IMPLEMENT REQUIRED
		
	}
	
	//get,set,save,load to be implemented. Plz get on work.
}
