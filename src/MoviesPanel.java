import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MoviesPanel extends JPanel {
    private DatabaseManager database;
    public MoviesPanel(CardLayout cardLayout, JPanel mainPanel, DatabaseManager database) {
        this.database = database;

        setLayout(new GridLayout(6, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JButton addButton = new JButton("Add Movie");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMovie();
            }
        });

        JButton updateButton = new JButton("Update Movie");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMovie();
            }
        });

        JButton deleteButton = new JButton("Delete Movie");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMovie();
            }
        });

        JButton viewButton = new JButton("View All Movies");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAll();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Home");
            }
        });

        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(viewButton);
        add(backButton);
    }
    public void addMovie() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JTextField txtTitle = new JTextField();
        JTextField txtYear = new JTextField();
        JTextField txtDuration = new JTextField();
        JTextField txtDirector = new JTextField();

        panel.add(new JLabel("Title"));
        panel.add(txtTitle);
        panel.add(new JLabel("Year"));
        panel.add(txtYear);
        panel.add(new JLabel("Duration"));
        panel.add(txtDuration);
        panel.add(new JLabel("Director"));
        panel.add(txtDirector);

        JOptionPane.showMessageDialog(this, panel, "Add New Movie", JOptionPane.PLAIN_MESSAGE);

        //Step 2 Extract user input from text fields
        String title = txtTitle.getText();
        int year = Integer.parseInt(txtYear.getText());
        int duration = Integer.parseInt(txtDuration.getText());
        String director = txtDirector.getText();

        //Step 3 Prepare an SQL statement to insert the user input to the table 'movies' in the database
        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement("INSERT INTO movies (title, year, duration, director) VALUES (?, ?, ?, ?);");

            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, duration);
            preparedStatement.setString(4, director);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateMovie() {
        try {
            //Step 4 Prepare an SQL statement to select a list of titles from the table 'movies' in the database
            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT title FROM movies");

            JComboBox cmbTitles = new JComboBox<>();

            //Step 5 The ResultSet will be used to populate the 'cmbTitles' combobox
            while (resultSet.next()) {
                cmbTitles.addItem(resultSet.getString("title"));
            }

            JOptionPane.showMessageDialog(this, cmbTitles, "Choose Movie Title", JOptionPane.PLAIN_MESSAGE);

            //Step 6 Extract user selection from the 'cmbTitles' combobox
            String selectedTitle = cmbTitles.getSelectedItem().toString();

            //Step 7 Prepare an SQL statement to select the title, year, duration and director of the selected title from the table 'movies' in the database
            PreparedStatement preparedStatement = database.getConnection().prepareStatement("SELECT title, year, duration, director FROM movies WHERE title = ?");
            preparedStatement.setString(1, selectedTitle);
            ResultSet movieInfo = preparedStatement.executeQuery();

            String title = "";
            String year = "";
            String duration = "";
            String director = "";

            //Step 8 The ResultSet will be used to initialize the title, year, duration and director variables
            while (movieInfo.next()) {
                title = movieInfo.getString("title");
                year = movieInfo.getString("year");
                duration = movieInfo.getString("duration");
                director = movieInfo.getString("director");
            }

            JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

            JTextField txtTitle = new JTextField(title);
            JTextField txtYear = new JTextField(year);
            JTextField txtDuration = new JTextField(duration);
            JTextField txtDirector = new JTextField(director);

            panel.add(new JLabel("Title"));
            panel.add(txtTitle);
            panel.add(new JLabel("Year"));
            panel.add(txtYear);
            panel.add(new JLabel("Duration"));
            panel.add(txtDuration);
            panel.add(new JLabel("Director"));
            panel.add(txtDirector);

            JOptionPane.showMessageDialog(this, panel, "Update Movie", JOptionPane.PLAIN_MESSAGE);

            //Step 9 Extract user input from text fields
            title = txtTitle.getText();
            year = txtYear.getText();
            duration = txtDuration.getText();
            director = txtDirector.getText();

            //Step 10 Prepare an SQL statement to update the title, year, duration and director of the selected movie
            preparedStatement = database.getConnection().prepareStatement("UPDATE movies SET title = ?, year = ?, duration = ?, director = ? WHERE title = ?");
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, Integer.parseInt(year));
            preparedStatement.setInt(3, Integer.parseInt(duration));
            preparedStatement.setString(4, director);
            preparedStatement.setString(5, selectedTitle);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteMovie() {
        try {
            //Step 11 Prepare an SQL statement to select a list of titles from the table 'movies' in the database
            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT title FROM movies");

            JComboBox cmbTitles = new JComboBox<>();

            //Step 12 The ResultSet will be used to populate the 'cmbTitles' combobox
            while (resultSet.next()) {
                cmbTitles.addItem(resultSet.getString("title"));
            }

            JOptionPane.showMessageDialog(this, cmbTitles, "Choose Movie Title", JOptionPane.PLAIN_MESSAGE);

            //Step 13 Extract user selection from the 'cmbTitles' combobox
            String selectedTitle = cmbTitles.getSelectedItem().toString();

            int result = JOptionPane.showConfirmDialog(this,"Are you confirm to delete "+selectedTitle+"?", "Delete Movie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if(result == JOptionPane.YES_OPTION) {
                //Step 14 Prepare an SQL statement to delete the selected title from the table 'movies' in the database
                PreparedStatement preparedStatement = database.getConnection().prepareStatement("DELETE FROM movies WHERE title = ?");
                preparedStatement.setString(1, selectedTitle);

                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewAll() {
        try {
            //Step 15 Prepare an SQL statement to select a list of titles, year, duration, director from the table 'movies' in the database
            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT title, year, duration, director FROM movies");

            String message = "";
            //Step 16 The ResultSet will be used to concatenate the 'message' string
            while (resultSet.next()) {
                message += "Title: " + resultSet.getString("title") +
                        "\nYear: " + resultSet.getString("year") +
                        "\nDuration: " + resultSet.getString("duration") +
                        "\nDirector: " + resultSet.getString("director") +"\n\n";
            }

            JOptionPane.showMessageDialog(this, message, "List of Movies", JOptionPane.PLAIN_MESSAGE);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
