import application.GameAppService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ui.AppFrame;

import javax.swing.*;

public class UnoApp {
    // Usamos la clase actual para nombrar el logger
    private static final Logger logger = LogManager.getLogger(UnoApp.class);

    public static void main(String[] args) {
        var appService = new GameAppService();

        SwingUtilities.invokeLater(() -> {
            new AppFrame(appService);
            logger.info("UNO app is launched");
        });
    }
}
