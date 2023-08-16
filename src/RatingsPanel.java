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
            //Step 17 Prepare an SQL statement to select a list of movieid, titles from the table 'movies' in the database
            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT movieid, title FROM movies");


            //Step 18 The ResultSet will be used to create a Movie object that will populate the 'movies' arraylist
            while (resultSet.next()) {
                Movies movie = new Movies(resultSet.getString("movieid"), resultSet.getString("title"));
                movies.add(movie);
            }

            JComboBox cmbTitles = new JComboBox<>(movies.toArray());

            JOptionPane.showMessageDialog(this, cmbTitles, "Choose Movie Title", JOptionPane.PLAIN_MESSAGE);

            //Step 19 Extract the movie id based on user selection from the 'cmbTitles' combobox
            int selectedMovie = Integer.parseInt(((Movies)cmbTitles.getSelectedItem()).id);

            JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

            JTextField txtRating = new JTextField();
            JTextArea txtReview = new JTextArea();

            panel.add(new JLabel("How much would you rate from 1 - 10?"));
            panel.add(txtRating);
            panel.add(new JLabel("Why?"));
            panel.add(txtReview);

            JOptionPane.showMessageDialog(this, panel, "Review Movie", JOptionPane.PLAIN_MESSAGE);

            //Step 20 Extract user input from text fields
            int rating = Integer.parseInt(txtRating.getText());
            String review = txtReview.getText();

            //Step 21 Prepare an SQL statement that will insert the rating, review and movieid to the 'reviews' table in the database
            PreparedStatement preparedStatement = database.getConnection().prepareStatement("INSERT INTO reviews (rating, review, movieid) VALUES (?,?,?)");
            preparedStatement.setInt(1, rating);
            preparedStatement.setString(2, review);
            preparedStatement.setInt(3, selectedMovie);

            int successful = preparedStatement.executeUpdate();

            if(successful > 0){
                //Step 22 If the SQL statement is successfully executed, update the rating column in the 'movies' table to be the average of rating from the 'reviews' table (based on the selected movie)
                preparedStatement = database.getConnection().prepareStatement("UPDATE movies " +
                        "SET rating = (SELECT AVG(rating) FROM reviews WHERE movieid = ?) " +
                        "WHERE movieid = ?");
                preparedStatement.setInt(1, selectedMovie);
                preparedStatement.setInt(2, selectedMovie);

                preparedStatement.executeUpdate();
            }
            movies.clear();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void viewRating() {
        try {
            //Step 23 Prepare an SQL statement to select a list of movieid, titles from the table 'movies' in the database
            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT movieid, title FROM movies");


            //Step 24 The ResultSet will be used to create a Movie object that will populate the 'movies' arraylist
            while (resultSet.next()) {
                Movies movie = new Movies(resultSet.getString("movieid"), resultSet.getString("title"));
                movies.add(movie);
            }

            JComboBox cmbTitles = new JComboBox<>(movies.toArray());

            JOptionPane.showMessageDialog(this, cmbTitles, "Choose Movie Title", JOptionPane.PLAIN_MESSAGE);

            //Step 25 Extract the movie id based on user selection from the 'cmbTitles' combobox
            int selectedMovie = Integer.parseInt(((Movies)cmbTitles.getSelectedItem()).id);

            //TODO: Step 26 Prepare an SQL statement to select a the title, year, duration, director, movie_rating, review_rating and review from the table 'movies' join with the table 'reviews in the database based on the selected movie
            PreparedStatement preparedStatement = database.getConnection().prepareStatement("SELECT title, year, duration, director, movies.rating as rate, reviews.rating as rating, review FROM movies LEFT JOIN reviews ON movies.movieid = reviews.movieid WHERE movies.movieid = ?;");
            preparedStatement.setInt(1, selectedMovie);
            ResultSet reviewInfo = preparedStatement.executeQuery();

            String title = "";
            String year = "";
            String duration = "";
            String director = "";
            String rating = "";
            String message = "";
            int count = 0;

            //Step 27 The ResultSet will be used to concatenate the 'message' string
            while (reviewInfo.next()) {
                if(title.isBlank() || year.isBlank() || duration.isBlank() || director.isBlank() || rating.isBlank()) {
                    title = reviewInfo.getString("title");
                    year = reviewInfo.getString("year");
                    duration = reviewInfo.getString("duration");
                    director = reviewInfo.getString("director");
                    rating = reviewInfo.getString("rate");

                    message = "Title: " + title + "\nYear: " + year + "\nDuration: " + duration + "\nDirector: " + director + "\nAverage Rating: " + rating + "\n\n\n";
                }
                message += "Rating      Reviews\n";
                message += String.format("%10s", reviewInfo.getString("rating")) + "      " + reviewInfo.getString("review") + "\n";
            }

            JOptionPane.showMessageDialog(this, message, "Movie Rating", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
