package model;

import java.io.File;
import java.io.IOException;

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
}
