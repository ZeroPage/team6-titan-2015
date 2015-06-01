package model;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.NoSuchElementException;

public class TitanDSMTest {

    @Test
    public void testTitanDSM() throws Exception {
        int size = 10;
        String filePath = "./sample/titan/titan.dsm";
        File file = new File(filePath);

        TitanDSM newDSM = new TitanDSM(size);
        assertNotNull(newDSM);
        TitanDSM loadDSM = new TitanDSM(file);
        assertNotNull(loadDSM);
    }

    @Test(expected=NotPositiveException.class)
    public void testTitanDSMWithException() throws Exception {
        int size = -1;
        TitanDSM newDSM = new TitanDSM(size);
    }

    @Test
    public void testGetSize() throws Exception {
        TitanDSM dsm = new TitanDSM(30);
        assertEquals(30, dsm.getSize());
    }

    @Test
    public void testGetData() throws Exception {
        TitanDSM dsm = new TitanDSM(30);
        assertEquals(false, dsm.getData("entity_1", "entity_2"));
    }

    @Test
    public void testSetData() throws Exception {
        TitanDSM dsm = new TitanDSM(30);
        dsm.setData(true, "entity_1", "entity_2");
        assertEquals(true, dsm.getData("entity_1", "entity_2"));
    }

    @Test(expected=NoSuchElementException.class)
    public void testSetDataWithException() throws Exception {
        int size = 10;
        String existingString = "entity_2";
        String nonExistingString = "entity";

        TitanDSM dsm = new TitanDSM(size);
        dsm.setData(true,nonExistingString, existingString);
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
        dsm.setName("NewName", "entity_1");
        assertEquals("NewName",dsm.getName(0));
    }

    @Test(expected=ItemAlreadyExistException.class)
    public void testSetNameWithException() throws Exception {
        int size = 10;
        String newClassName = "entity_1";
        String oldClassName = "entity_2";
        TitanDSM dsm = new TitanDSM(size);
        dsm.setName(newClassName, oldClassName);
    }

    @Test
    public void testIsExist() throws Exception {
        int size = 10;
        String existingString = "entity_10";
        String nonExistingString = "test";
        TitanDSM dsm = new TitanDSM(size);
        assertTrue(dsm.isExist(existingString));
        assertFalse(dsm.isExist(nonExistingString));
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

    @Test(expected = WrongDSMFormatException.class)
    public void testLoadFromFileWithExceptionForWrongLength() throws Exception {
        int size = 10;
        String pathName = "./sample/titan/titan2.dsm";
        File file = new File(pathName);
        TitanDSM dsm = new TitanDSM(size);
        dsm.loadFromFile(file);
    }

    @Test(expected = WrongDSMFormatException.class)
    public void testLoadFromFileWithExceptionForWrongMatrixColomn() throws Exception {
        int size = 10;
        String pathName = "./sample/titan/titan3.dsm";
        File file = new File(pathName);
        TitanDSM dsm = new TitanDSM(size);
        dsm.loadFromFile(file);
    }

    @Test(expected = WrongDSMFormatException.class)
    public void testLoadFromFileWithExceptionForWrongMatrixData() throws Exception {
        int size = 10;
        String pathName = "./sample/titan/titan4.dsm";
        File file = new File(pathName);
        TitanDSM dsm = new TitanDSM(size);
        dsm.loadFromFile(file);
    }

    @Test(expected = WrongDSMFormatException.class)
    public void testLoadFromFileWithExceptionForWrongClassSize() throws Exception {
        int size = 10;
        String pathName = "./sample/titan/titan5.dsm";
        File file = new File(pathName);
        TitanDSM dsm = new TitanDSM(size);
        dsm.loadFromFile(file);
    }

    @Test(expected = WrongDSMFormatException.class)
    public void testLoadFromFileWithExceptionForWrongClassData() throws Exception {
        int size = 10;
        String pathName = "./sample/titan/titan6.dsm";
        File file = new File(pathName);
        TitanDSM dsm = new TitanDSM(size);
        dsm.loadFromFile(file);
    }

    @Test(expected = WrongDSMFormatException.class)
    public void testLoadFromFileWithExceptionForWrongClassEnd() throws Exception {
        int size = 10;
        String pathName = "./sample/titan/titan7.dsm";
        File file = new File(pathName);
        TitanDSM dsm = new TitanDSM(size);
        dsm.loadFromFile(file);
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