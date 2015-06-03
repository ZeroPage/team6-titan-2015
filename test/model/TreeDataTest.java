package model;

import model.exception.NotPositiveException;
import model.exception.WrongDSMFormatException;

import org.junit.Test;

import javax.swing.tree.DefaultMutableTreeNode;

import static org.junit.Assert.*;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TreeDataTest {

    @Test
    public void testTreeData() throws IOException, WrongDSMFormatException, NotPositiveException {
        TreeData treeData;

        treeData = new TreeData(new File("./sample/titan/titan.dsm"));
        assertNotNull(treeData);

        treeData = new TreeData(treeData, treeData.getTree());
        assertNotNull(treeData);

        treeData = new TreeData(10);
        assertNotNull(treeData);
    }

    @Test
    public void testDSMSaveLoad() throws NotPositiveException, IOException, WrongDSMFormatException {
        File temp = new File("temp.dsm");
        TreeData treeData = new TreeData(5);

        DefaultMutableTreeNode root = treeData.getTree();
        treeData.setDSMData((DefaultMutableTreeNode) root.getChildAt(0), (DefaultMutableTreeNode) root.getChildAt(1), true);
        treeData.saveDSMData(temp);

        TreeData savedTreeData = new TreeData(temp);
        temp.delete();

        DefaultMutableTreeNode savedRoot = savedTreeData.getTree();
        boolean originalData = treeData.getDSMValue((DefaultMutableTreeNode) root.getChildAt(0), (DefaultMutableTreeNode) root.getChildAt(1));
        boolean savedData = treeData.getDSMValue((DefaultMutableTreeNode) savedRoot.getChildAt(0), (DefaultMutableTreeNode) savedRoot.getChildAt(1));

        assertEquals(originalData, savedData);
    }

    @Test
    public void testGroup() throws NotPositiveException, IOException, WrongDSMFormatException {
        TreeData treeData = new TreeData(3);
        DefaultMutableTreeNode root = treeData.getTree();
        DefaultMutableTreeNode child0 = (DefaultMutableTreeNode) root.getChildAt(0);
        DefaultMutableTreeNode child1 = (DefaultMutableTreeNode) root.getChildAt(1);
        DefaultMutableTreeNode child2 = (DefaultMutableTreeNode) root.getChildAt(2);

        treeData.setDSMData(child0, child2, true);
        treeData.groupElement(new ArrayList<>(Arrays.asList(new DefaultMutableTreeNode[] {child0, child1})), "group");

        assertEquals(treeData.getDSMValue((DefaultMutableTreeNode) root.getChildAt(0), (DefaultMutableTreeNode) root.getChildAt(1)), true);

        treeData.freeGroup((DefaultMutableTreeNode) root.getChildAt(0));

        child0 = (DefaultMutableTreeNode) root.getChildAt(0);
        child2 = (DefaultMutableTreeNode) root.getChildAt(2);
        assertEquals(treeData.getDSMValue(child0, child2), true);
    }

    @Test
    public void testPartition() throws NotPositiveException, IOException, WrongDSMFormatException {
        TreeData treeData = new TreeData(10);
        DefaultMutableTreeNode root = treeData.getTree();

        treeData.setDSMData((DefaultMutableTreeNode) root.getChildAt(1), (DefaultMutableTreeNode) root.getChildAt(2), true);
        treeData.setDSMData((DefaultMutableTreeNode) root.getChildAt(2), (DefaultMutableTreeNode) root.getChildAt(1), true);
        treeData.setDSMData((DefaultMutableTreeNode) root.getChildAt(4), (DefaultMutableTreeNode) root.getChildAt(3), true);

        treeData.partition();
        // TODO: check partition is valid
    }
}