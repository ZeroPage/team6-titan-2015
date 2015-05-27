package model;

public class NodeNotFoundException extends Exception {
    public NodeNotFoundException(){
        super("Node not found");
    }
}
