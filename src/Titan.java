import controller.TitanMainController;

class Titan {
    public static void main(String[] args) {
        new view.TitanFrame(new TitanMainController()).setVisible(true);
    }
}