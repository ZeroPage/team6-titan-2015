package model;

import java.io.*;

public class TitanDSM {
    private int sizeOfMatrix;
    private int[][] dataMatrix;
    private String[] nameOfClass;

    public TitanDSM(int sizeOfMatrix) {
        this.sizeOfMatrix = sizeOfMatrix;
        dataMatrix = new int[sizeOfMatrix][sizeOfMatrix];
        nameOfClass = new String[sizeOfMatrix];
    }

    public TitanDSM(File file) throws IOException {
        loadFile(file);
    }

    public int getSize() {
        return sizeOfMatrix;
    }

    public void setSize(int size) {
        sizeOfMatrix = size;
    }

    public int[][] getData() {
        return dataMatrix;
    }

    public void setData(int[][] data) {
        dataMatrix = data;
    }

    public String[] getNames() {
        return nameOfClass;
    }

    public void setNames(String[] names) {
        nameOfClass = names;
    }

    public void loadFile(File dsm) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(dsm));
        String read;
        String[] temp;

        read = fileReader.readLine();
        sizeOfMatrix = Integer.parseInt(read);

        for (int i = 0; i < sizeOfMatrix; i++) {
            read = fileReader.readLine();
            temp = read.split(" ");
            for (int j = 0; j < sizeOfMatrix; j++) {
                dataMatrix[i][j] = Integer.parseInt(temp[j]);
            }
        }

        for (int i = 0; i < sizeOfMatrix; i++) {
            read = fileReader.readLine();
            nameOfClass[i] = read;
        }
    }

}