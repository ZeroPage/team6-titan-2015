package model;

public class TitanDSM{
    private int sizeOfMatrix;
    private int[][] dataMatrix;
    private String [] nameOfClass;

    public TitanDSM(int sizeOfMatrix) {
        this.sizeOfMatrix = sizeOfMatrix;
        dataMatrix = new int[sizeOfMatrix][sizeOfMatrix];
        nameOfClass =  new String[sizeOfMatrix];
    }
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

