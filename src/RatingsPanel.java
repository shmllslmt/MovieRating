import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class Movies {
    String id;
    String name;
    Movies(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
public class RatingsPanel extends JPanel {
    private DatabaseManager database;
    private ArrayList<Movies> movies = new ArrayList<>();
    public RatingsPanel(CardLayout cardLayout, JPanel mainPanel, DatabaseManager database) {
        this.database = database;

        setLayout(new GridLayout(3, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(160, 50, 160, 50));

        JButton viewRatingsButton = new JButton("View Movie Ratings");
        viewRatingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // View movie ratings
                viewRating();
            }
        });

        JButton addRatingButton = new JButton("Add Movie Rating");
        addRatingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add a movie rating
                addRating();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Home");
            }
        });

        add(viewRatingsButton);
        add(addRatingButton);
        add(backButton);
    }
    public void addRating() {
        try {
            //TODO: Step 17 Prepare an SQL statement to select a list of movieid, titles from the table 'movies' in the database

            //TODO: Step 18 The ResultSet will be used to create a Movie object that will populate the 'movies' arraylist
            Movies movie = new Movies("", "");
            movies.add(movie);

            JComboBox cmbTitles = new JComboBox<>(movies.toArray());

            JOptionPane.showMessageDialog(this, cmbTitles, "Choose Movie Title", JOptionPane.PLAIN_MESSAGE);

            //TODO: Step 19 Extract the movie id based on user selection from the 'cmbTitles' combobox
            int selectedMovie = 0;

            JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

            JTextField txtRating = new JTextField();
            JTextArea txtReview = new JTextArea();

            panel.add(new JLabel("How much would you rate from 1 - 10?"));
            panel.add(txtRating);
            panel.add(new JLabel("Why?"));
            panel.add(txtReview);

            JOptionPane.showMessageDialog(this, panel, "Review Movie", JOptionPane.PLAIN_MESSAGE);

            //TODO: Step 20 Extract user input from text fields
            //TODO: Step 21 Prepare an SQL statement that will insert the rating, review and movieid to the 'reviews' table in the database based on the selected movie

            int successful = 0;

            if(successful > 0){
                //TODO: Step 22 If the SQL statement is successfully executed, update the rating column in the 'movies' table to be the average of rating from the 'reviews' table (based on the selected movie)
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void viewRating() {
        try {
            //TODO: Step 23 Prepare an SQL statement to select a list of movieid, titles from the table 'movies' in the database

            //TODO: Step 24 The ResultSet will be used to create a Movie object that will populate the 'movies' arraylist
            Movies movie = new Movies("", "");
            movies.add(movie);

            JComboBox cmbTitles = new JComboBox<>(movies.toArray());

            JOptionPane.showMessageDialog(this, cmbTitles, "Choose Movie Title", JOptionPane.PLAIN_MESSAGE);

            //TODO: Step 25 Extract the movie id based on user selection from the 'cmbTitles' combobox
            int selectedMovie = 0;

            //TODO: Step 26 Prepare an SQL statement to select a the title, year, duration, director, movie_rating, review_rating and review from the table 'movies' join with the table 'reviews in the database based on the selected movie

            String title = "";
            String year = "";
            String duration = "";
            String director = "";
            String rating = "";
            String message = "";
            int count = 0;

            //TODO: Step 27 The ResultSet will be used to concatenate the 'message' string

            JOptionPane.showMessageDialog(this, message, "Movie Rating", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
