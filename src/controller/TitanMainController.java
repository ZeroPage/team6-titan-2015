package controller;

public class TitanMainController {
    private TitanDSMController dsmController;
    private TitanClusterController clusterController;

    public TitanMainController() {
        dsmController = new TitanDSMController();
        clusterController = new TitanClusterController();
    }

    public TitanDSMController getDSMController() {
        return dsmController;
    }

    public TitanClusterController getClusterController() { return clusterController; }
}
