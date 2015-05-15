package model;

import java.io.*;

public class TitanDSM {
    private int sizeOfMatrix;
    private boolean[][] dataMatrix;
    private String[] nameOfClass;

    public TitanDSM(int sizeOfMatrix) {
        this.sizeOfMatrix = sizeOfMatrix;
        dataMatrix = new boolean[sizeOfMatrix][sizeOfMatrix];
        nameOfClass = new String[sizeOfMatrix];
    }

    public TitanDSM(File file) throws IOException {
        loadFromFile(file);
    }

    public int getSize() {
        return sizeOfMatrix;
    }

    public void setSize(int size) {
        sizeOfMatrix = size;
    }

    public boolean[][] getDataMatrix() {
        return dataMatrix;
    }

    public void setData(boolean data, int row, int col) {
        dataMatrix[row][col] = data;
    }

    public String[] getNames() {
        return nameOfClass;
    }

    public void loadFromFile(File dsm) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(dsm));
        String read;
        String[] temp;

        read = fileReader.readLine();
        sizeOfMatrix = Integer.parseInt(read);

        dataMatrix = new boolean[sizeOfMatrix][sizeOfMatrix];
        nameOfClass = new String[sizeOfMatrix];

        for (int i = 0; i < sizeOfMatrix; i++) {
            read = fileReader.readLine();
            temp = read.split(" ");
            for (int j = 0; j < sizeOfMatrix; j++) {
                dataMatrix[i][j] = Integer.parseInt(temp[j]) == 1;
            }
        }

        for (int i = 0; i < sizeOfMatrix; i++) {
            read = fileReader.readLine();
            nameOfClass[i] = read;
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
            out.write(nameOfClass[i]);
            out.newLine();
        }

        out.close();
    }

}