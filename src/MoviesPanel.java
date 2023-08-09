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

        //TODO: Step 2 Extract user input from text fields
        //TODO: Step 3 Prepare an SQL statement to insert the user input to the table 'movies' in the database
    }

    public void updateMovie() {
        try {
            //TODO: Step 4 Prepare an SQL statement to select a list of titles from the table 'movies' in the database

            JComboBox cmbTitles = new JComboBox<>();

            //TODO: Step 5 The ResultSet will be used to populate the 'cmbTitles' combobox
            cmbTitles.addItem("");


            JOptionPane.showMessageDialog(this, cmbTitles, "Choose Movie Title", JOptionPane.PLAIN_MESSAGE);

            //TODO: Step 6 Extract user selection from the 'cmbTitles' combobox
            //TODO: Step 7 Prepare an SQL statement to select the title, year, duration and director of the selected title from the table 'movies' in the database

            String title = "";
            String year = "";
            String duration = "";
            String director = "";

            //TODO: Step 8 The ResultSet will be used to initialize the title, year, duration and director variables

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

            //TODO: Step 9 Extract user input from text fields
            //TODO: Step 10 Prepare an SQL statement to update the title, year, duration and director of the selected movie

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteMovie() {
        try {
            //TODO: Step 11 Prepare an SQL statement to select a list of titles from the table 'movies' in the database

            JComboBox cmbTitles = new JComboBox<>();

            //TODO: Step 12 The ResultSet will be used to populate the 'cmbTitles' combobox
            cmbTitles.addItem("");

            JOptionPane.showMessageDialog(this, cmbTitles, "Choose Movie Title", JOptionPane.PLAIN_MESSAGE);

            //TODO: Step 13 Extract user selection from the 'cmbTitles' combobox
            String selectedTitle = "";

            int result = JOptionPane.showConfirmDialog(this,"Are you confirm to delete "+selectedTitle+"?", "Delete Movie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if(result == JOptionPane.YES_OPTION) {
                //TODO: Step 14 Prepare an SQL statement to delete the selected title from the table 'movies' in the database

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewAll() {
        try {
            //TODO: Step 15 Prepare an SQL statement to select a list of titles, year, duration, director from the table 'movies' in the database

            String message = "";
            //TODO: Step 16 The ResultSet will be used to concatenate the 'message' string

            JOptionPane.showMessageDialog(this, message, "List of Movies", JOptionPane.PLAIN_MESSAGE);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
