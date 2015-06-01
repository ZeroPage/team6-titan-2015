package model;

import java.io.File;
import java.io.IOException;

import java.util.*;

import javax.swing.tree.DefaultMutableTreeNode;

public class TreeData {
	private TitanDSM dsmData;
	private ClusterData cluster;

//initializing with only DSM
	public TreeData(File dsmFile) throws IOException, WrongDSMFormatException {
		this.dsmData = new TitanDSM(dsmFile);
		this.cluster = new ClusterData(this.dsmData);
	}
	
	public TreeData(int size) throws IOException, WrongDSMFormatException, NotPositiveException {
		this.dsmData = new TitanDSM(size);
		this.cluster = new ClusterData(this.dsmData);
	}

	public TreeData(TreeData original, DefaultMutableTreeNode newRoot) { // Forking
		this.dsmData = original.dsmData;
		this.cluster = new ClusterData(newRoot);
	}

//load clsx, and rebuild the data tree structure
	public void loadClusterData(File clsxFile) throws IOException, WrongXMLNamespaceException {
		this.cluster = new ClusterData(clsxFile);
		this.cluster.refresh(this.dsmData);
	}
	
	public void saveData(File dsmFile, File clusterFile) throws IOException {
		this.dsmData.saveToFile(dsmFile);
		this.cluster.saveClusterData(clusterFile);
	}
	
	public void saveDSMData(File dsmFile) throws IOException {
		this.dsmData.saveToFile(dsmFile);
	}

	public void saveClusterData(File clusterFile) throws IOException {
		this.cluster.saveClusterData(clusterFile);
	}
	
//build default cluster with DSM only.
	public void setClusterAsDefault() {
		this.cluster = new ClusterData(this.dsmData);
	}
	
	public void sortGroupElements(DefaultMutableTreeNode group) {
		for(int i=1;i<group.getChildCount();i++) {
			int index=0;
			
			DefaultMutableTreeNode comparisonNode = (DefaultMutableTreeNode)group.getChildAt(index);
			DefaultMutableTreeNode targetNode = (DefaultMutableTreeNode)group.getChildAt(i);
			
			String comparison = comparisonNode.getUserObject().toString();
			String target = targetNode.getUserObject().toString();
			
			while(comparison.compareTo(target)<0&&index<i) {
				index++;
				comparisonNode = (DefaultMutableTreeNode)group.getChildAt(index);
				comparison = comparisonNode.getUserObject().toString();
			}
			group.insert(targetNode,index);
		}
	}
	
	public DefaultMutableTreeNode getTree(){
		return this.cluster.getTree();
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
	
	public void setDSMData(DefaultMutableTreeNode rowNode, DefaultMutableTreeNode columnNode, Boolean value) {
		String row = rowNode.getUserObject().toString();
		String column = columnNode.getUserObject().toString();
		
		this.dsmData.setData(value, row, column);
	}

	public void renameElement(DefaultMutableTreeNode currentNode, String newName) throws ItemAlreadyExistException, NoSuchElementException {
		if(!currentNode.getAllowsChildren()) {
			String elementName = currentNode.getUserObject().toString();
			this.dsmData.setName(newName, elementName);
		}
		this.cluster.renameNode(currentNode, newName);
	}
	
	public void repositionElement(DefaultMutableTreeNode elementNode,int newIndex) throws NoSuchElementException {
		this.cluster.moveNode(elementNode, newIndex);
	}
	
	public void removeElement(DefaultMutableTreeNode elementNode) throws NoSuchElementException {
		this.cluster.deleteItem(elementNode);
	}
	
	public void addElement(DefaultMutableTreeNode groupNode, String itemName) throws NoSuchElementException {
		this.cluster.addItem(groupNode, itemName);
	}
	
	public void groupElement(ArrayList<DefaultMutableTreeNode> elementList, String groupName) {
		this.cluster.newGroupbyNode(elementList, groupName);
	}
	
	public void freeGroup(DefaultMutableTreeNode groupNode) throws NoSuchElementException {
		this.cluster.freeGroup(groupNode);
	}
	
	private boolean getGroupGroupValue(DefaultMutableTreeNode rGroup, DefaultMutableTreeNode cGroup) {
		boolean isR, isC;
		isR = rGroup.getFirstChild().getAllowsChildren();
		isC = cGroup.getFirstChild().getAllowsChildren();
		
		if(isR&isC) {
			for(int i=0;i<cGroup.getChildCount();i++) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode)cGroup.getChildAt(i);
				if(getGroupGroupValue(rGroup,child)) {
					return true;
				}
			}
		} else if(isR) {
			for(int i=0;i<cGroup.getChildCount();i++) {
				DefaultMutableTreeNode item = (DefaultMutableTreeNode)cGroup.getChildAt(i);
				if(getGroupItemValue(rGroup,item)) {
					return true;
				}
			}
		} else if(isC) {
			for(int i=0;i<rGroup.getChildCount();i++) {
				DefaultMutableTreeNode item = (DefaultMutableTreeNode)rGroup.getChildAt(i);
				if(getItemGroupValue(item,cGroup)) {
					return true;
				}
			}
		} else {
			for(int i=0;i<rGroup.getChildCount();i++) {
				DefaultMutableTreeNode item = (DefaultMutableTreeNode)rGroup.getChildAt(i);
				if(getItemGroupValue(item,cGroup)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean getGroupItemValue(DefaultMutableTreeNode group, DefaultMutableTreeNode element) {
		if(group.getChildCount()!=0&&group.getFirstChild().getAllowsChildren()) {
			for(int i=0;i<group.getChildCount();i++) {
				if(getGroupItemValue((DefaultMutableTreeNode)group.getChildAt(i),element)) {
					return true;
				}
			}
		} else {
			for(int i=0;i<group.getChildCount();i++) {
				DefaultMutableTreeNode item = (DefaultMutableTreeNode)group.getChildAt(i);
				if(getItemItemValue(item, element)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean getItemGroupValue(DefaultMutableTreeNode element, DefaultMutableTreeNode group) {
		if(group.getChildCount()!=0&&group.getFirstChild().getAllowsChildren()) {
			for(int i=0;i<group.getChildCount();i++) {
				if(getItemGroupValue(element,(DefaultMutableTreeNode)group.getChildAt(i))) {
					return true;
				}
			}
		} else {
			for(int i=0;i<group.getChildCount();i++) {
				DefaultMutableTreeNode item = (DefaultMutableTreeNode)group.getChildAt(i);
				if(getItemItemValue(element, item)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean getItemItemValue(DefaultMutableTreeNode rowElement, DefaultMutableTreeNode columnElement) {
		String row = rowElement.getUserObject().toString();
		String column = columnElement.getUserObject().toString();
		
		return this.dsmData.getData(row, column);
	}

	public void partition() {
		setClusterAsDefault();
		partitionSubTree(getTree());
	}

	private void partitionSubTree(DefaultMutableTreeNode subRoot) {
		List<DefaultMutableTreeNode> children = Collections.list(subRoot.children());
		int top = 0;
		int bottom = children.size();

		// STEP 1: move empty row items to top
		for (int i = 0; i < bottom; i++) {
			DefaultMutableTreeNode current = children.get(i);
			boolean rowEmpty = true;

			for (int j = 0; j < bottom; j++) {
				rowEmpty &= !getDSMValue(current, children.get(j));
			}

			if (rowEmpty) {
				repositionElement(current, top); // move to top
				top++;
				children = Collections.list(subRoot.children());
			}
		}

		// STEP 2: move empty column items to bottom
		children = Collections.list(subRoot.children());
		for (int i = top; i < bottom; i++) {
			DefaultMutableTreeNode current = children.get(i);
			boolean columnEmpty = true;

			for (int j = top; j < bottom; j++) {
				columnEmpty &= !getDSMValue(children.get(j), current);
			}

			if (columnEmpty) {
				i--;
				bottom--;
				repositionElement(current, bottom); // move to bottom
				children = Collections.list(subRoot.children());
			}
		}

		// STEP 3: find circuits and group them
		int newGroupNumber = 0;
		children = Collections.list(subRoot.children());

		for (int i = top; i < bottom; i++) {
			HashSet<DefaultMutableTreeNode> circuit = new HashSet<>();
			Queue<DefaultMutableTreeNode> queue = new LinkedList<>();

			circuit.add(children.get(i));
			queue.add(children.get(i));

			while (!queue.isEmpty()) {
				DefaultMutableTreeNode current = queue.remove();

				for (int j = top; j < bottom; j++) {
					DefaultMutableTreeNode related = children.get(j);
					if (getDSMValue(current, related)) {
						if (!circuit.contains(related)) {
							circuit.add(related);
							queue.add(related);
						}
					}
				}
			}

			if (circuit.size() > 1) {
				newGroupNumber++;
				groupElement(new ArrayList<>(circuit), "group_" + newGroupNumber);

				bottom -= circuit.size() - 1;
				children = Collections.list(subRoot.children());
			}

			top++;
		}
	}
}
