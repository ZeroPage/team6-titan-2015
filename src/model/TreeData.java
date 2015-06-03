package model;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import model.exception.ItemAlreadyExistException;
import model.exception.NotPositiveException;
import model.exception.WrongDSMFormatException;
import model.exception.WrongXMLNamespaceException;

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
	public void loadClusterData(File clsxFile) throws IOException, WrongXMLNamespaceException, ParserConfigurationException, SAXException {
		this.cluster = new ClusterData(clsxFile);
		this.cluster.refresh(this.dsmData);
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
		ArrayList<DefaultMutableTreeNode> rows = new ArrayList<>();
		ArrayList<DefaultMutableTreeNode> cols = new ArrayList<>();

		if (rowElement.getAllowsChildren()) {
			Enumeration<DefaultMutableTreeNode> rowEnum = rowElement.preorderEnumeration();

			while (rowEnum.hasMoreElements()) {
				DefaultMutableTreeNode tempRow = rowEnum.nextElement();
				if (!tempRow.getAllowsChildren()) {
					rows.add(tempRow);
				}
			}
		} else {
			rows.add(rowElement);
		}


		if (columnElement.getAllowsChildren()) {
			Enumeration<DefaultMutableTreeNode> colEnum = columnElement.preorderEnumeration();

			while (colEnum.hasMoreElements()) {
				DefaultMutableTreeNode tempRow = colEnum.nextElement();
				if (!tempRow.getAllowsChildren()) {
					cols.add(tempRow);
				}
			}
		} else {
			cols.add(columnElement);
		}

		for (int i = 0; i < rows.size(); i++) {
			for (int j = 0; j < cols.size(); j++) {
				if (dsmData.getData(rows.get(i).toString(), cols.get(j).toString())) {
					return true;
				}
			}
		}

		return false;
	}
	
	public void setDSMData(DefaultMutableTreeNode rowNode, DefaultMutableTreeNode columnNode, Boolean value) {
		String row = rowNode.getUserObject().toString();
		String column = columnNode.getUserObject().toString();
		
		this.dsmData.setData(value, row, column);
	}

	public void renameElement(DefaultMutableTreeNode currentNode, String newName) throws ItemAlreadyExistException, NoSuchElementException {
		if(!currentNode.getAllowsChildren()) {
			if(cluster.isExists(newName)) {
				throw new ItemAlreadyExistException();
			}
			
			String elementName = currentNode.getUserObject().toString();
			this.dsmData.setName(newName, elementName);
		}
		this.cluster.renameNode(currentNode, newName);
	}
	
	public void repositionElement(DefaultMutableTreeNode elementNode,int newIndex) throws NoSuchElementException {
		this.cluster.moveNode(elementNode, newIndex);
	}
	
	public void removeElement(DefaultMutableTreeNode elementNode) throws NoSuchElementException {
		if(!elementNode.getAllowsChildren()) {
			this.dsmData.deleteData(elementNode.getUserObject().toString());
		}
		this.cluster.deleteItem(elementNode);

	}
	
	public void addElement(DefaultMutableTreeNode groupNode, String itemName) throws NoSuchElementException, ItemAlreadyExistException {
		if(cluster.isExists(itemName)) {
			throw new ItemAlreadyExistException();
		}
		this.cluster.addItem(groupNode, itemName);
		this.dsmData.addEntity(itemName);
	}
	
	public void groupElement(ArrayList<DefaultMutableTreeNode> elementList, String groupName) {
		this.cluster.newGroupbyNode(elementList, groupName);
	}
	
	public void freeGroup(DefaultMutableTreeNode groupNode) throws NoSuchElementException {
		this.cluster.freeGroup(groupNode);
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
