import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {
    public HomePanel(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new GridLayout(2, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(200, 50, 200, 50));

        JButton moviesButton = new JButton("Movies");
        moviesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the "MoviesPanel" for movie-related actions
                cardLayout.show(mainPanel, "Movies");
            }
        });

        JButton ratingsButton = new JButton("Ratings");
        ratingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the "RatingsPanel" for rating-related actions
                cardLayout.show(mainPanel, "Ratings");
            }
        });

        add(moviesButton);
        add(ratingsButton);
    }
}
