package controller;

import model.TitanDSM;

import java.io.File;
import java.io.IOException;

public class TitanMainController {
    private TitanDSM dsm;

    public TitanMainController() {

    }

    public void newDSM(int size) {
        dsm = new TitanDSM(size);
    }

    public void openDSM(File file) throws IOException {
        dsm = new TitanDSM(file);
    }
}
