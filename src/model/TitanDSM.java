package model;

public class TitanDSM{
    private int sizeOfMatrix;
    private int[][] dataMatrix;
    private String [] nameOfClass;

    public int getSize(){
        return sizeOfMatrix;
    }
    public void setSize(int size){
        sizeOfMatrix =  size;
    }

    public int[][] getData(){
        return dataMatrix;
    }
    public void setData(int[][] data){
        dataMatrix = data;
    }

    public String[] getNames(){
        return nameOfClass;
    }
    public void setNames(String[] names){
        nameOfClass = names;
    }
}

