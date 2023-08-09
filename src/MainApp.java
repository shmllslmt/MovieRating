import javax.swing.*;
import java.awt.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Rate A Movie!");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);

                CardLayout cardLayout = new CardLayout();
                JPanel mainPanel = new JPanel(cardLayout);

                DatabaseManager database = new DatabaseManager();

                HomePanel homePanel = new HomePanel(cardLayout, mainPanel);
                MoviesPanel moviePanel = new MoviesPanel(cardLayout, mainPanel, database);
                RatingsPanel ratingPanel = new RatingsPanel(cardLayout, mainPanel, database);

                mainPanel.add(homePanel, "Home");
                mainPanel.add(moviePanel, "Movies");
                mainPanel.add(ratingPanel, "Ratings");

                frame.add(mainPanel);
                frame.setVisible(true);
            }
        });
    }
}
