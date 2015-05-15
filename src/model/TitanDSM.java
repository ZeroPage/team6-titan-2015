package model;

import java.io.*;
import java.util.ArrayList;

public class TitanDSM {
    private int sizeOfMatrix;
    private boolean[][] dataMatrix;
    private ArrayList<String> nameOfClass;

    public TitanDSM(int sizeOfMatrix) {
        this.sizeOfMatrix = sizeOfMatrix;
        dataMatrix = new boolean[sizeOfMatrix][sizeOfMatrix];
        nameOfClass = new ArrayList<>();
        initNameOfClass();
    }

    public TitanDSM(File file) throws IOException {
        nameOfClass = new ArrayList<>();
        loadFromFile(file);
    }

    private void initNameOfClass() {
        for(int i = 0; i < sizeOfMatrix; i++) {
            nameOfClass.add("entity_" + i);
        }
    }

    public int getSize() {
        return sizeOfMatrix;
    }

    public void addEntity() {
        sizeOfMatrix++;
        nameOfClass.add("entity_" + sizeOfMatrix);
    }

    public boolean[][] getDataMatrix() {
        return dataMatrix;
    }

    public void setData(boolean data, int row, int col) {
        dataMatrix[row][col] = data;
    }

    public ArrayList<String> getNames() {
        return nameOfClass;
    }

    public void setName(String newName, int location) {
        nameOfClass.set(location,newName);
    }

    public void loadFromFile(File dsm) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(dsm));
        String read;
        String[] temp;

        read = fileReader.readLine();
        sizeOfMatrix = Integer.parseInt(read);

        dataMatrix = new boolean[sizeOfMatrix][sizeOfMatrix];

        for (int i = 0; i < sizeOfMatrix; i++) {
            read = fileReader.readLine();
            temp = read.split(" ");
            for (int j = 0; j < sizeOfMatrix; j++) {
                dataMatrix[i][j] = Integer.parseInt(temp[j]) == 1;
            }
        }

        for (int i = 0; i < sizeOfMatrix; i++) {
            read = fileReader.readLine();
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
                out.write(dataMatrix[i][j] + " ");
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