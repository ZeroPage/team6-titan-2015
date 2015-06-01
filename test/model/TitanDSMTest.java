package model;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;

public class TitanDSMTest {

    @Test
    public void testGetSize() throws Exception {
        TitanDSM dsm = new TitanDSM(30);
        assertEquals(30,dsm.getSize());
    }

    @Test
    public void testGetData() throws Exception {
        TitanDSM dsm = new TitanDSM(30);
        assertEquals(false, dsm.getData("entity_1","entity_2"));
    }

    @Test
    public void testSetData() throws Exception {
        TitanDSM dsm = new TitanDSM(30);
        dsm.setData(true,"entity_1","entity_2");
        assertTrue(dsm.getData("entity_1","entity_2"));
    }

    @Test
    public void testDeleteData() throws Exception {
        TitanDSM dsm = new TitanDSM(30);
        dsm.deleteData("entity_1");
        assertEquals(29,dsm.getSize());
    }

    @Test
    public void testGetName() throws Exception {
        TitanDSM dsm = new TitanDSM(30);
        assertEquals("entity_1",dsm.getName(0));
    }

    @Test
    public void testSetName() throws Exception {
        TitanDSM dsm = new TitanDSM(30);
        dsm.setName("NewName","entity_1");
        assertEquals("NewName",dsm.getName(0));
    }

    @Test
    public void testIsExist() throws Exception {
        int size = 10;
        String existingString = "entity_10";
        String nonExistingString = "test";
        TitanDSM dsm = new TitanDSM(size);
        assertEquals(true, dsm.isExist(existingString));
        assertEquals(false, dsm.isExist(nonExistingString));
    }

    @Test
    public void testAddEntity() throws Exception {
        int size = 10;
        TitanDSM dsm = new TitanDSM(size);
        dsm.addEntity();
        assertEquals(size + 1, dsm.getSize());
    }

    @Test
    public void testLoadFromFile() throws Exception {
        int size = 10;
        String testString = "edu.drexel.cs.rise.titan.action.RedrawAction";
        String pathName = "./sample/titan/titan.dsm";
        File file = new File(pathName);
        TitanDSM dsm = new TitanDSM(size);
        dsm.loadFromFile(file);
        assertEquals(true, dsm.isExist(testString));
    }

    @Test
    public void testSaveToFile() throws Exception {
        TitanDSM dsm = new TitanDSM(30);
        File file = new File("a");
        dsm.setData(true,"entity_1","entity_2");
        dsm.saveToFile(file);
        TitanDSM dsm2 = new TitanDSM(file);
        assertEquals(dsm.getData("entity_1","entity_2"),dsm2.getData("entity_1","entity_2"));
    }
}