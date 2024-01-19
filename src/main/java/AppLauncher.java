import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Display the app GUI
            new WeatherAppGui().setVisible(true);
        });
    }
}
