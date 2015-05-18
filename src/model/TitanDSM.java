package model;

import java.io.*;
import java.util.ArrayList;

public class TitanDSM {
    private int sizeOfMatrix;
    private ArrayList<ArrayList<Boolean>> dataMatrix;
    private ArrayList<String> nameOfClass;

    public TitanDSM(int sizeOfMatrix) throws NotPositiveException {
        if (sizeOfMatrix <= 0) {
            throw new NotPositiveException();
        }
        this.sizeOfMatrix = sizeOfMatrix;
        dataMatrix = new ArrayList<>();
        nameOfClass = new ArrayList<>();
        initDataMatrix();
        initNameOfClass();
    }

    public TitanDSM(File file) throws IOException {
        dataMatrix = new ArrayList<>();
        nameOfClass = new ArrayList<>();
        loadFromFile(file);
    }

    private void initNameOfClass() {
        for(int i = 1; i <= sizeOfMatrix; i++) {
            nameOfClass.add("entity_" + i);
        }
    }

    private void initDataMatrix() {
        for(int i = 0; i < sizeOfMatrix; i++){
            dataMatrix.add(new ArrayList<>());
            for(int j = 0; j < sizeOfMatrix; j++)
                dataMatrix.get(i).add(false);
        }
    }

    public int getSize() {
        return sizeOfMatrix;
    }

    public Boolean getData(int row, int col) {
        return dataMatrix.get(row).get(col);
    }

    public void setData(boolean data, int row, int col) {
        dataMatrix.get(row).set(col,data);
    }

    public  String getName(int index) {
        return nameOfClass.get(index);
    }

    public void setName(String newName, int index) {
        nameOfClass.set(index, newName);
    }

    public void addEntity(){
        sizeOfMatrix++;
        nameOfClass.add("entity" + sizeOfMatrix);
        dataMatrix.add(new ArrayList<>());
        for(int i = 0;i < sizeOfMatrix; i++){
            dataMatrix.get(i).add(false);
            dataMatrix.get(sizeOfMatrix-1).add(false);
        }
        dataMatrix.get(sizeOfMatrix-1).remove(sizeOfMatrix);
    }

    public void loadFromFile(File dsm) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(dsm));
        String read;
        String[] temp;

        read = fileReader.readLine();
        sizeOfMatrix = Integer.parseInt(read);

        for (int i = 0; i < sizeOfMatrix; i++) {
            read = fileReader.readLine();
            temp = read.split(" ");
            dataMatrix.add(new ArrayList<>());
            for (int j = 0; j < sizeOfMatrix; j++) {
                dataMatrix.get(i).add(Integer.parseInt(temp[j]) == 1);
            }
        }

        for (int i = 0; i < sizeOfMatrix; i++) {
            read = fileReader.readLine();
            System.out.println(read);
            nameOfClass.add(read);
        }

        fileReader.close();
    }

    public void saveToFile(String fileName) throws IOException{
        BufferedWriter out = new BufferedWriter(new FileWriter(fileName));

        out.write(String.valueOf(sizeOfMatrix));
        out.newLine();

        for(int i = 0; i < sizeOfMatrix; i++) {
            for(int j = 0; j < sizeOfMatrix; j++) {
                out.write(dataMatrix.get(i).get(j) + " ");
            }
            out.newLine();
        }

        for(int i = 0; i < sizeOfMatrix; i++) {
            out.write(nameOfClass.get(i));
            out.newLine();
        }

        out.close();
    }

}

