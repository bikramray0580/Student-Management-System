import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

class Student {
    String name, rollNo;

    Student(String name, String rollNo) {
        this.name = name;
        this.rollNo = rollNo;
    }
}

public class StudentManagementGUI {

    static ArrayList<Student> students = new ArrayList<>();
    static DefaultTableModel model;
    static String fileName = "students.csv";

    public static void main(String[] args) {

        JFrame frame = new JFrame("Student Management System");
        frame.setSize(700, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // TOP PANEL
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        JTextField nameField = new JTextField();
        JTextField rollField = new JTextField();

        topPanel.add(new JLabel("Name:"));
        topPanel.add(nameField);
        topPanel.add(new JLabel("Roll No:"));
        topPanel.add(rollField);

        // TABLE
        String[] columns = { "Name", "Roll No" };
        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);

        // BUTTON PANEL
        JPanel buttonPanel = new JPanel();

        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");
        JButton searchBtn = new JButton("Search");
        JButton saveBtn = new JButton("Save CSV");

        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(saveBtn);

        // ADD
        addBtn.addActionListener(e -> {
            String name = nameField.getText();
            String roll = rollField.getText();

            if (!name.isEmpty() && !roll.isEmpty()) {
                students.add(new Student(name, roll));
                model.addRow(new Object[] { name, roll });

                nameField.setText("");
                rollField.setText("");
            }
        });

        // DELETE
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                students.remove(row);
                model.removeRow(row);
            }
        });

        // SEARCH
        searchBtn.addActionListener(e -> {
            String roll = rollField.getText();

            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).rollNo.equals(roll)) {
                    table.setRowSelectionInterval(i, i);
                    JOptionPane.showMessageDialog(frame, "Student Found!");
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Not Found!");
        });

        // SAVE CSV
        saveBtn.addActionListener(e -> saveToCSV(frame));

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // SAVE FUNCTION
    static void saveToCSV(JFrame frame) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Name,RollNo");

            for (Student s : students) {
                writer.println(s.name + "," + s.rollNo);
            }

            JOptionPane.showMessageDialog(frame, "Saved Successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error Saving File");
        }
    }
}