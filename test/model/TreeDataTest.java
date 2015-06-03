package model;

import model.exception.ItemAlreadyExistException;
import model.exception.NotPositiveException;
import model.exception.WrongDSMFormatException;

import model.exception.WrongXMLNamespaceException;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.ParserConfigurationException;

import static org.junit.Assert.*;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TreeDataTest {

    @Test
    public void testTreeData() throws IOException, WrongDSMFormatException, NotPositiveException {
        TreeData treeData;

        treeData = new TreeData(new File("./sample/titan/titan.dsm"));
        assertNotNull(treeData);

        treeData = new TreeData(treeData, treeData.getTreeRoot());
        assertNotNull(treeData);

        treeData = new TreeData(10);
        assertNotNull(treeData);
    }

    @Test
    public void testDSMSaveLoad() throws NotPositiveException, IOException, WrongDSMFormatException {
        File temp = new File("temp.dsm");
        TreeData treeData = new TreeData(5);

        DefaultMutableTreeNode root = treeData.getTreeRoot();
        treeData.setDSMData((DefaultMutableTreeNode) root.getChildAt(0), (DefaultMutableTreeNode) root.getChildAt(1), true);
        treeData.saveDSMData(temp);

        TreeData savedTreeData = new TreeData(temp);
        temp.delete();

        DefaultMutableTreeNode savedRoot = savedTreeData.getTreeRoot();
        boolean originalData = treeData.getDSMValue((DefaultMutableTreeNode) root.getChildAt(0), (DefaultMutableTreeNode) root.getChildAt(1));
        boolean savedData = treeData.getDSMValue((DefaultMutableTreeNode) savedRoot.getChildAt(0), (DefaultMutableTreeNode) savedRoot.getChildAt(1));

        assertEquals(originalData, savedData);
    }

    @Test
    public void testClusterSaveLoad() throws NotPositiveException, IOException, WrongDSMFormatException, ParserConfigurationException, SAXException, WrongXMLNamespaceException {
        File temp = new File("temp.dsm");
        TreeData treeData = new TreeData(3);


        treeData.groupElement(new ArrayList<>(Collections.list(treeData.getTreeRoot().children())), "group");

        treeData.saveClusterData(temp);
        treeData.loadClusterData(temp);
        temp.delete();

        assertEquals(treeData.getTreeRoot().getChildAt(0).toString(), "group");
    }

    @Test
    public void testGroup() throws NotPositiveException, IOException, WrongDSMFormatException {
        TreeData treeData = new TreeData(4);
        DefaultMutableTreeNode root = treeData.getTreeRoot();
        DefaultMutableTreeNode child0 = (DefaultMutableTreeNode) root.getChildAt(0);
        DefaultMutableTreeNode child1 = (DefaultMutableTreeNode) root.getChildAt(1);
        DefaultMutableTreeNode child2 = (DefaultMutableTreeNode) root.getChildAt(2);
        DefaultMutableTreeNode child3 = (DefaultMutableTreeNode) root.getChildAt(3);

        treeData.setDSMData(child0, child2, true);
        treeData.groupElement(new ArrayList<>(Arrays.asList(new DefaultMutableTreeNode[] {child0, child1})), "group1");
        treeData.groupElement(new ArrayList<>(Arrays.asList(new DefaultMutableTreeNode[] {child2, child3})), "group2");
        assertEquals(treeData.getDSMValue((DefaultMutableTreeNode) root.getChildAt(0), (DefaultMutableTreeNode) root.getChildAt(1)), true);

        treeData.freeGroup((DefaultMutableTreeNode) root.getChildAt(0));
        child0 = (DefaultMutableTreeNode) root.getChildAt(0);
        child2 = (DefaultMutableTreeNode) root.getChildAt(2);
        assertEquals(treeData.getDSMValue(child0, child2), true);
    }

    @Test
    public void testAddRenameDeleteSort() throws NotPositiveException, IOException, WrongDSMFormatException, ItemAlreadyExistException {
        TreeData treeData = new TreeData(3);
        DefaultMutableTreeNode root = treeData.getTreeRoot();

        treeData.addElement(root, "new1");
        treeData.addElement(root, "new2");
        assertEquals(root.getChildAt(4).toString(), "new2");

        treeData.removeElement((DefaultMutableTreeNode) root.getChildAt(3));
        assertEquals(root.getChildAt(3).toString(), "new2");

        treeData.renameElement((DefaultMutableTreeNode) root.getChildAt(3), "_");
        assertEquals(root.getChildAt(3).toString(), "_");

        treeData.sortGroupElements(root);
        assertEquals(root.getChildAt(0).toString(), "_");
    }

    @Test
    public void testPartition() throws NotPositiveException, IOException, WrongDSMFormatException {
        TreeData treeData = new TreeData(10);
        DefaultMutableTreeNode root = treeData.getTreeRoot();

        treeData.setDSMData((DefaultMutableTreeNode) root.getChildAt(1), (DefaultMutableTreeNode) root.getChildAt(2), true);
        treeData.setDSMData((DefaultMutableTreeNode) root.getChildAt(2), (DefaultMutableTreeNode) root.getChildAt(1), true);
        treeData.setDSMData((DefaultMutableTreeNode) root.getChildAt(4), (DefaultMutableTreeNode) root.getChildAt(3), true);

        treeData.partition();
        // TODO: check partition is valid
    }
}