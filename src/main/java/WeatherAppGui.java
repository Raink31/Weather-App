import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGui extends JFrame {
    public WeatherAppGui() {
        // Setup GUI and add a title
        super("Weather App");

        // Configure GUI to end the program's process once it has been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Set the size of the GUI (Px)
        setSize(450, 650);
        setResizable(false);

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
        searchTextField.setBounds(15, 15, 351, 45);

        // Change the font style and size
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));

        // Add the Search field to the app
        add(searchTextField);


        // Search button
        JButton searchButton = new JButton(loadImage("src/main/assets/search.png"));

        // Change the cursor to a hand cursor when hover the button
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 15, 47, 45);
        add(searchButton);


        // Weather image
        JLabel weatherConditionImage = new JLabel(loadImage("src/main/assets/cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);


        // Temperature text
        JLabel temperatureText = new JLabel("10 C°");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("dialog", Font.BOLD, 48));

        // Center the temperature text
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);


        // Weather condition description
        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 405, 450, 36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);


        // Humidity image
        JLabel humidityImage = new JLabel(loadImage("src/main/assets/humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);
        add(humidityImage);

        // Humidity text
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);


        // Windspeed image
        JLabel windspeedImage = new JLabel(loadImage("src/main/assets/windspeed.png"));
        windspeedImage.setBounds(220, 500, 74, 66);
        add(windspeedImage);

        // Windspeed text
        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedText);
    }

    // Used to create images in the GUI Component
    private ImageIcon loadImage(String resourcePath) {
        try {
            // Read the image file from the given path
            BufferedImage image = ImageIO.read(new File(resourcePath));

            // Returns an image icon that the component can render
            return new ImageIcon(image);
        } catch(IOException e) {
            e.printStackTrace();
        }

        System.out.println("Could not find resource");
        return null;
    }
}
