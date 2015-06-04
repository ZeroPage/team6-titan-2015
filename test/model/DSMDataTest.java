package model;

import model.exception.ItemAlreadyExistException;
import model.exception.NotPositiveException;
import model.exception.WrongDSMFormatException;
import org.junit.Test;

import java.io.File;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class DSMDataTest {

    @Test
    public void testTitanDSM() throws Exception {
        int size = 10;
        String filePath = "./sample/titan/titan.dsm";
        File file = new File(filePath);

        DSMData newDSM = new DSMData(size);
        assertNotNull(newDSM);
        DSMData loadDSM = new DSMData(file);
        assertNotNull(loadDSM);
    }

    @Test(expected = NotPositiveException.class)
    public void testTitanDSMWithException() throws Exception {
        int size = -1;
        DSMData newDSM = new DSMData(size);
    }

    @Test
    public void testGetSize() throws Exception {
        DSMData dsm = new DSMData(30);
        assertEquals(30, dsm.getSize());
    }

    @Test
    public void testGetData() throws Exception {
        DSMData dsm = new DSMData(30);
        assertEquals(false, dsm.getData("entity_1", "entity_2"));
    }

    @Test
    public void testSetData() throws Exception {
        DSMData dsm = new DSMData(30);
        dsm.setData(true, "entity_1", "entity_2");
        assertEquals(true, dsm.getData("entity_1", "entity_2"));
    }

    @Test(expected = NoSuchElementException.class)
    public void testSetDataWithException() throws Exception {
        int size = 10;
        String existingString = "entity_2";
        String nonExistingString = "entity";

        DSMData dsm = new DSMData(size);
        dsm.setData(true, nonExistingString, existingString);
    }

    @Test
    public void testDeleteData() throws Exception {
        DSMData dsm = new DSMData(30);
        dsm.deleteData("entity_1");
        assertEquals(29, dsm.getSize());
    }

    @Test
    public void testGetName() throws Exception {
        DSMData dsm = new DSMData(30);
        assertEquals("entity_1", dsm.getName(0));
    }

    @Test
    public void testSetName() throws Exception {
        DSMData dsm = new DSMData(30);
        dsm.setName("NewName", "entity_1");
        assertEquals("NewName", dsm.getName(0));
    }

    @Test(expected = ItemAlreadyExistException.class)
    public void testSetNameWithException() throws Exception {
        int size = 10;
        String newClassName = "entity_1";
        String oldClassName = "entity_2";
        DSMData dsm = new DSMData(size);
        dsm.setName(newClassName, oldClassName);
    }

    @Test
    public void testIsExist() throws Exception {
        int size = 10;
        String existingString = "entity_10";
        String nonExistingString = "test";
        DSMData dsm = new DSMData(size);
        assertTrue(dsm.isExist(existingString));
        assertFalse(dsm.isExist(nonExistingString));
    }

    @Test
    public void testAddEntity() throws Exception {
        int size = 10;
        String newName = "test";
        DSMData dsm = new DSMData(size);
        dsm.addEntity(newName);
        assertEquals(size + 1, dsm.getSize());
        assertEquals(newName, dsm.getName(dsm.getSize() - 1));
    }

    @Test
    public void testLoadFromFile() throws Exception {
        int size = 10;
        String testString = "edu.drexel.cs.rise.titan.action.RedrawAction";
        String pathName = "./sample/titan/titan.dsm";
        File file = new File(pathName);
        DSMData dsm = new DSMData(size);
        dsm.loadFromFile(file);
        assertEquals(true, dsm.isExist(testString));
    }

    @Test(expected = WrongDSMFormatException.class)
    public void testLoadFromFileWithExceptionForWrongLength() throws Exception {
        int size = 10;
        String pathName = "./sample/test/titan2.dsm";
        File file = new File(pathName);
        DSMData dsm = new DSMData(size);
        dsm.loadFromFile(file);
    }

    @Test(expected = WrongDSMFormatException.class)
    public void testLoadFromFileWithExceptionForWrongMatrixColumn() throws Exception {
        int size = 10;
        String pathName = "./sample/test/titan3.dsm";
        File file = new File(pathName);
        DSMData dsm = new DSMData(size);
        dsm.loadFromFile(file);
    }

    @Test(expected = WrongDSMFormatException.class)
    public void testLoadFromFileWithExceptionForWrongMatrixData() throws Exception {
        int size = 10;
        String pathName = "./sample/test/titan4.dsm";
        File file = new File(pathName);
        DSMData dsm = new DSMData(size);
        dsm.loadFromFile(file);
    }

    @Test(expected = WrongDSMFormatException.class)
    public void testLoadFromFileWithExceptionForWrongClassSize() throws Exception {
        int size = 10;
        String pathName = "./sample/test/titan5.dsm";
        File file = new File(pathName);
        DSMData dsm = new DSMData(size);
        dsm.loadFromFile(file);
    }

    @Test(expected = WrongDSMFormatException.class)
    public void testLoadFromFileWithExceptionForWrongClassData() throws Exception {
        int size = 10;
        String pathName = "./sample/test/titan6.dsm";
        File file = new File(pathName);
        DSMData dsm = new DSMData(size);
        dsm.loadFromFile(file);
    }

    @Test(expected = WrongDSMFormatException.class)
    public void testLoadFromFileWithExceptionForWrongClassEnd() throws Exception {
        int size = 10;
        String pathName = "./sample/test/titan7.dsm";
        File file = new File(pathName);
        DSMData dsm = new DSMData(size);
        dsm.loadFromFile(file);
    }

    @Test(expected = WrongDSMFormatException.class)
    public void testLoadFromFileWithExceptionForNoClass() throws Exception {
        int size = 10;
        String pathName = "./sample/test/titan8.dsm";
        File file = new File(pathName);
        DSMData dsm = new DSMData(size);
        dsm.loadFromFile(file);
    }

    @Test
    public void testSaveToFile() throws Exception {
        DSMData dsm = new DSMData(30);
        File file = new File("a");
        dsm.setData(true, "entity_1", "entity_2");
        dsm.saveToFile(file);
        DSMData dsm2 = new DSMData(file);
        assertEquals(dsm.getData("entity_1", "entity_2"), dsm2.getData("entity_1", "entity_2"));
        file.delete();
    }
}