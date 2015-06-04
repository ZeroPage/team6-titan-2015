package model;

import model.exception.NotPositiveException;
import org.junit.Test;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ClusterDataTest {

    @Test
    public void testClusterData() {
        try {
            TitanDSM dsm = new TitanDSM(30);
            ClusterData cluster = new ClusterData(dsm);
            assertNotNull(cluster);
        } catch (NotPositiveException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadClusterData() throws Exception {
        ClusterData cluster = new ClusterData(new DefaultMutableTreeNode());
        File file = new File("./sample/moka/moka_ACDC.clsx");
        cluster.loadClusterData(file);
        assertEquals(cluster.getTreeRoot().getNextNode().getUserObject().toString(), "edu.drexel.cs.rise.moka.jre16.parser.MethodParser");
    }

    @Test
    public void testSaveClusterData() throws Exception {
        ClusterData cluster = new ClusterData(new DefaultMutableTreeNode());
        File file = new File("./sample/moka/moka_ACDC.clsx");
        File file2 = new File("./sample/moka/moka_ACDC2.clsx");
        cluster.loadClusterData(file);
        cluster.saveClusterData(file2);
        assertTrue(file.exists());
        file2.delete();
    }

    @Test
    public void testMoveNode() throws Exception {
        ClusterData cluster = new ClusterData(new DefaultMutableTreeNode());
        File file = new File("./sample/moka/moka_ACDC.clsx");
        cluster.loadClusterData(file);
        cluster.moveNode(cluster.getTreeRoot().getNextNode(), 2);
        assertEquals(cluster.getTreeRoot().getChildAt(2).toString(), "edu.drexel.cs.rise.moka.jre16.parser.MethodParser");
    }

    @Test
    public void testGrouping() throws Exception {
        ClusterData cluster = new ClusterData(new DefaultMutableTreeNode());
        ArrayList<DefaultMutableTreeNode> Array = new ArrayList<>();
        File file = new File("./sample/moka/moka_ACDC.clsx");
        String className = "edu.drexel.cs.rise.moka.jre16.parser.MethodParser";

        cluster.loadClusterData(file);
        Array.add(cluster.getTreeRoot().getNextNode());
        cluster.newGroupbyNode(Array, "newGroup");
        assertEquals(cluster.getNode(className).getParent().toString(), "newGroup");
        cluster.freeGroup(cluster.getTreeRoot().getNextNode());
        assertEquals(cluster.getTreeRoot().getNextNode().getUserObject().toString(), className);
    }

    @Test
    public void testRenameNode() throws Exception {
        File file = new File("./sample/moka/moka_ACDC.clsx");
        ClusterData cluster = new ClusterData(new DefaultMutableTreeNode());
        cluster.loadClusterData(file);
        cluster.renameNode(cluster.getTreeRoot().getNextNode(), "MokaCoffee");
        assertEquals(cluster.getTreeRoot().getNextNode().getUserObject().toString(), "MokaCoffee");
    }

    @Test
    public void testAddItem() throws Exception {
        File file = new File("./sample/moka/moka_ACDC.clsx");
        ClusterData cluster = new ClusterData(new DefaultMutableTreeNode());
        cluster.loadClusterData(file);
        cluster.addItem(cluster.getTreeRoot().getNextNode(), "MokaCoffee");
        assertEquals("MokaCoffee", cluster.getTreeRoot().getNextNode().getChildAt(2).toString());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteItem() throws Exception {
        String filePath = "./sample/titan/titan_ACDC.clsx";
        String className = "edu.drexel.cs.rise.titan.ui.MatrixViewer";
        ClusterData cluster = new ClusterData(new File(filePath));
        cluster.deleteItem((DefaultMutableTreeNode) cluster.getNode(className));
        cluster.getAllowsChildren(className);
    }
}