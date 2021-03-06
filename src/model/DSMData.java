package model;

import model.exception.ItemAlreadyExistException;
import model.exception.NotPositiveException;
import model.exception.WrongDSMFormatException;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class DSMData {
    private int sizeOfMatrix;
    private ArrayList<ArrayList<Boolean>> dataMatrix;
    private ArrayList<String> nameOfClass;

    public DSMData(int sizeOfMatrix) throws NotPositiveException {
        if (sizeOfMatrix <= 0) {
            throw new NotPositiveException();
        }
        this.sizeOfMatrix = sizeOfMatrix;
        dataMatrix = new ArrayList<>();
        nameOfClass = new ArrayList<>();
        initDataMatrix();
        initNameOfClass();
    }

    public DSMData(File file) throws IOException, WrongDSMFormatException {
        dataMatrix = new ArrayList<>();
        nameOfClass = new ArrayList<>();
        loadFromFile(file);
    }

    private void initNameOfClass() {
        for (int i = 1; i <= sizeOfMatrix; i++) {
            nameOfClass.add("entity_" + i);
        }
    }

    private void initDataMatrix() {
        for (int i = 0; i < sizeOfMatrix; i++) {
            dataMatrix.add(new ArrayList<>());
            for (int j = 0; j < sizeOfMatrix; j++) {
                dataMatrix.get(i).add(false);
            }
        }
    }

    public int getSize() {
        return sizeOfMatrix;
    }

    public Boolean getData(String row, String col) throws NoSuchElementException {
        return dataMatrix.get(getIndexByName(row)).get(getIndexByName(col));
    }

    public void setData(boolean data, String row, String col) throws NoSuchElementException {
        dataMatrix.get(getIndexByName(row)).set(getIndexByName(col), data);
    }

    public void deleteData(String deleteName) throws NoSuchElementException {
        int index = getIndexByName(deleteName);
        nameOfClass.remove(index);
        for (int i = 0; i < sizeOfMatrix; i++) {
            dataMatrix.get(i).remove(index);
        }
        dataMatrix.remove(index);
        sizeOfMatrix--;
    }

    public String getName(int index) {
        return nameOfClass.get(index);
    }

    public void setName(String newName, String oldName) throws ItemAlreadyExistException, NoSuchElementException {
        if (nameOfClass.contains(newName)) {
            throw new ItemAlreadyExistException();
        } else {
            nameOfClass.set(getIndexByName(oldName), newName);
        }
    }

    public boolean isExist(String name) {
        return nameOfClass.contains(name);
    }

    public void addEntity(String name) {
        sizeOfMatrix++;
        nameOfClass.add(name);
        dataMatrix.add(new ArrayList<>());
        for (int i = 0; i < sizeOfMatrix; i++) {
            dataMatrix.get(i).add(false);
            dataMatrix.get(sizeOfMatrix - 1).add(false);
        }
        dataMatrix.get(sizeOfMatrix - 1).remove(sizeOfMatrix);
    }

    private int getIndexByName(String name) throws NoSuchElementException {
        if (isExist(name)) {
            return nameOfClass.indexOf(name);
        } else {
            throw new NoSuchElementException(name + " not found.");
        }
    }

    public void loadFromFile(File dsm) throws IOException, WrongDSMFormatException {
        BufferedReader fileReader = new BufferedReader(new FileReader(dsm));
        String read;
        String[] temp;

        read = fileReader.readLine();
        if (!isNum(read)) {
            throw new WrongDSMFormatException();
        }
        sizeOfMatrix = Integer.parseInt(read);


        for (int i = 0; i < sizeOfMatrix; i++) {
            read = fileReader.readLine();
            temp = read.split(" ");
            if (temp.length != sizeOfMatrix) {
                throw new WrongDSMFormatException();
            }
            dataMatrix.add(new ArrayList<>());
            for (int j = 0; j < sizeOfMatrix; j++) {
                if (!temp[j].equals("0") && !temp[j].equals("1")) {
                    throw new WrongDSMFormatException();
                }
                dataMatrix.get(i).add(Integer.parseInt(temp[j]) == 1);
            }
        }

        for (int i = 0; i < sizeOfMatrix; i++) {
            read = fileReader.readLine();
            if (read == null) {
                throw new WrongDSMFormatException();
            }
            temp = read.split(" ");
            if (temp.length != 1) {
                throw new WrongDSMFormatException();
            }
            nameOfClass.add(read);
        }

        if (fileReader.read() != -1) {
            throw new WrongDSMFormatException();
        }

        fileReader.close();
    }

    public void saveToFile(File file) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));

        out.write(String.valueOf(sizeOfMatrix));
        out.newLine();

        for (int i = 0; i < sizeOfMatrix; i++) {
            for (int j = 0; j < sizeOfMatrix; j++) {
                if (dataMatrix.get(i).get(j) == Boolean.TRUE) {
                    out.write("1 ");
                } else {
                    out.write("0 ");
                }
            }
            out.newLine();
        }

        for (int i = 0; i < sizeOfMatrix; i++) {
            out.write(nameOfClass.get(i));
            out.newLine();
        }

        out.close();
    }

    private Boolean isNum(String toCheck) {
        return toCheck.matches("[1-9]\\d*");
    }
}
