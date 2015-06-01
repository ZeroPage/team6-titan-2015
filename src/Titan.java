import controller.TitanMainController;

import javax.swing.*;

class Titan {
    public static void main(String[] args) {

        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            try {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            } catch (Exception e) {
                // If it's not available, just use default L&F
                break;
            }
        }

        new TitanMainController().setDialogVisible(true);
    }
}