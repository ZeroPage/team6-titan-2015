package model;

import org.junit.Test;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ClusterDataTest {

    @Test
    public void testClusterData() throws  Exception {
        TitanDSM dsm = new TitanDSM(30);
        File file = new File("./sample/moka/moka_ACDC.clsx");
        ClusterData cluster = new ClusterData(dsm);
        cluster = new ClusterData(file);
    }

    @Test
    public void testLoadClusterData() throws Exception {
        ClusterData cluster = new ClusterData(new DefaultMutableTreeNode());
        File file = new File("./sample/moka/moka_ACDC.clsx");
        cluster.loadClusterData(file);
        assertEquals(cluster.getTree().getNextNode().getUserObject().toString(), "edu.drexel.cs.rise.moka.jre16.parser.MethodParser");
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
        cluster.moveNode(cluster.getTree().getNextNode(), 2);
        assertEquals(cluster.getTree().getChildAt(2).toString(), "edu.drexel.cs.rise.moka.jre16.parser.MethodParser");
    }

    @Test
    public void testGrouping() throws Exception {
        ClusterData cluster = new ClusterData(new DefaultMutableTreeNode());
        ArrayList<DefaultMutableTreeNode> Array = new ArrayList<DefaultMutableTreeNode>();
        File file = new File("./sample/moka/moka_ACDC.clsx");
        String className = "edu.drexel.cs.rise.moka.jre16.parser.MethodParser";

        cluster.loadClusterData(file);
        Array.add(cluster.getTree().getNextNode());
        cluster.newGroupbyNode(Array, "newGroup");
        assertEquals(cluster.getNode(className).getParent().toString(),"newGroup");
        cluster.freeGroup(cluster.getTree().getNextNode());
        assertEquals(cluster.getTree().getNextNode().getUserObject().toString(), className);
    }

    @Test
    public void testRenameNode() throws Exception {

    }

    @Test
    public void testAddItem() throws Exception {

    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteItem() throws Exception {
        String filePath = "./sample/titan/titan_ACDC.clsx";
        String className = "edu.drexel.cs.rise.titan.ui.MatrixViewer";
        ClusterData cluster = new ClusterData(new File(filePath));
        cluster.deleteItem((DefaultMutableTreeNode)cluster.getNode(className));
        cluster.getAllowsChildren(className);
    }
}