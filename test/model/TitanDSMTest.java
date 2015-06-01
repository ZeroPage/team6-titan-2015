package model;

import model.TitanDSM;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

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
        assertEquals(true, dsm.getData("entity_1","entity_2"));
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
        assertEquals("entity",dsm.getName(0));
    }

    @Test
    public void testSetName() throws Exception {
        TitanDSM dsm = new TitanDSM(30);
        dsm.setName("NewName","entity_1");
        assertEquals("NewName",dsm.getName(0));
    }

    @Test
    public void testIsExist() throws Exception {

    }

    @Test
    public void testAddEntity() throws Exception {

    }

    @Test
    public void testLoadFromFile() throws Exception {

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