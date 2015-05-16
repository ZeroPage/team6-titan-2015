package controller;

public class ClusterTreeFactory {
	private static ClusterTreeFactory factoryInstance = new ClusterTreeFactory();
	
	private ClusterTreeFactory() {
		//Overriding default constructor with private constructor
	}
	
	public static ClusterTreeFactory getInstance() {
		return factoryInstance;
	}
/*
//RKTP's part from this line
	public Document buildDocInstance() {
		//devoted to RKTP
	}
	
//Yong-ho's part from this line	
	public DefaultMutableTreeNode buildNodeInstance() {
		//devoted to Yongho
	}
	*/
}
