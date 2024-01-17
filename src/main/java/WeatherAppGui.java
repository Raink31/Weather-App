import javax.swing.*;
import java.awt.*;

public class WeatherAppGui extends JFrame {
    public WeatherAppGui() {
        // Setup GUI and add a title
        super("Weather App");

        // Configure GUI to end the program's process once it has been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set the size of the GUI (Px)
        setSize(450, 650);

        // Load the GUI at the center of the screen
        setLocationRelativeTo(null);

        // Make layout manager null to manually position the components within the GUI
        setResizable(false);

        // Add every component to the app
        addGuiComponents();
    }

    private void addGuiComponents() {
        // Search field
        JTextField searchTextField = new JTextField();

        // Set the location and size of the component
        searchTextField.setLayout(null);
        searchTextField.setBounds(15, 15, 351, 45);

        // Change the font style and size
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));

        // Add the Search field to the app
        add(searchTextField);
    }
}
