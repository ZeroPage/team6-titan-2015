package model;

import org.junit.Test;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ClusterDataTest {

    @Test
    public void testLoadClusterData() throws Exception {
        ClusterData cluster = new ClusterData(new DefaultMutableTreeNode());
        File file = new File("./sample/test/moka_ACDC.clsx");
        cluster.loadClusterData(file);
        assertEquals(cluster.getTree().getNextNode().getUserObject().toString(),"edu.drexel.cs.rise.moka.jre16.parser.MethodParser");
    }

    @Test
    public void testSaveClusterData() throws Exception {
        ClusterData cluster = new ClusterData(new DefaultMutableTreeNode());
        File file = new File("./sample/test/moka_ACDC.clsx");
        File file2 = new File("./sample/test/moka_ACDC2.clsx");
        cluster.loadClusterData(file);
        cluster.saveClusterData(file2);
        assertTrue(file.exists());
        file2.delete();
    }

    @Test
    public void testMoveNode() throws Exception {
        ClusterData cluster = new ClusterData(new DefaultMutableTreeNode());
        File file = new File("./sample/test/moka_ACDC.clsx");
        cluster.loadClusterData(file);
        cluster.moveNode(cluster.getTree().getNextNode(), 2);
        assertEquals(cluster.getTree().getChildAt(2).toString(), "edu.drexel.cs.rise.moka.jre16.parser.MethodParser");
    }

    @Test
    public void testNewGroupbyNode() throws Exception {
        ClusterData cluster = new ClusterData(new DefaultMutableTreeNode());
        ArrayList<DefaultMutableTreeNode> Array = new ArrayList<DefaultMutableTreeNode>();
        File file = new File("./sample/test/moka_ACDC.clsx");

        cluster.loadClusterData(file);
        Array.add(cluster.getTree().getNextNode());
        cluster.newGroupbyNode(Array, "newGroup");
        assertEquals(cluster.getNode("edu.drexel.cs.rise.moka.jre16.parser.MethodParser").getParent().toString(),"newGroup");
    }

    @Test
    public void testFreeGroup() throws Exception {

    }

    @Test
    public void testRenameNode() throws Exception {

    }

    @Test
    public void testAddItem() throws Exception {

    }

    @Test
    public void testDeleteItem() throws Exception {

    }
}