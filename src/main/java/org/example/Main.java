package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class Main  extends JFrame implements ActionListener {
    //attributes
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel statusLabel;
    private JComboBox<User> userComboBox;
    private JList<Book> bookList;
    private DefaultListModel<Book> bookListModel;
    private  User currentUser;

    public Main() {
        setTitle("CPL Library System");
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadUser();
        loadBooks();

    }
    private void initComponents(){
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Username:");
        constraints.gridx  = 0;
        constraints.gridy =0;
        loginPanel.add(usernameLabel, constraints);

        usernameField = new JTextField(15);
        constraints.gridx =1;
        constraints.gridy =0;
        loginPanel.add(usernameField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx  = 0;
        constraints.gridy =1;
        loginPanel.add(passwordLabel, constraints);

        passwordField= new JPasswordField(15);
        constraints.gridx  = 1;
        constraints.gridy =1;
        loginPanel.add(passwordField, constraints);

        JButton loginButton = new JButton("login");
        loginButton.addActionListener(this);
        constraints.gridx =0;
        constraints.gridy =2;
        loginPanel.add(loginButton, constraints);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        constraints.gridx = 1;
        constraints.gridy =2;
        loginPanel.add(registerButton, constraints);

        statusLabel = new JLabel();
        constraints.gridx = 0;
        constraints.gridy =3;
        constraints.gridwidth =2;
        constraints.anchor = GridBagConstraints.CENTER;
        loginPanel.add(statusLabel, constraints);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(loginPanel, BorderLayout.NORTH);

        JPanel userPanel = new JPanel(new BorderLayout());
        userComboBox = new JComboBox<>();
        userComboBox.addActionListener(this);
        userPanel.add(userComboBox, BorderLayout.NORTH);

        bookListModel = new DefaultListModel<>();
        bookList =new JList<>(bookListModel);
        JScrollPane scrollPane = new JScrollPane(bookList);
        userPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton borrowButton = new JButton("Borrow");
        borrowButton.addActionListener(this);
        buttonPanel.add(borrowButton);

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(this);
        buttonPanel.add(returnButton);

        userPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(userPanel, BorderLayout.CENTER);

        add(mainPanel);
    }


    private void loadUser(){
        try {
            List<User> users = UserDAO.getAllUser();
            userComboBox.removeAllItems();
            for (User user : users){
                userComboBox.addItem(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void  loadBooks(){
        try{

            List<Book> books = BookDAO.getAllBooks();
            bookListModel.clear();

            for (Book book : books) {
                bookListModel.addElement(book);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();

        if(command.equals("Login")) {
            login();
        } else if (command.equals("Register")) {
            register();
        } else if (command.equals("Return")) {
            returnBook();
        } else if (e.getSource() == userComboBox) {
            currentUser = (User) userComboBox.getSelectedItem();
            loadBorrowingHistory();
        } else if (command.equals("Borrow")) {
        }borrowBook();
    }

    private void login(){
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            if (UserDAO.authenticateUser(username, password)) {
                loadUser();
                loadBooks();
                statusLabel.setText("Invalid username or password");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
            statusLabel.setText("Default Error");
        }
    }

    private void register(){
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User newUser = new User();

        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole("member");

        try{
            UserDAO.addUser(newUser);
            statusLabel.setText("Register Successful.");
            loadUser();;
        }catch (SQLException ex){
            ex.printStackTrace();
            statusLabel.setText("Database Error!");
        }
    }

    private void borrowBook(){
        User selectedUser = currentUser;
        Book selectedBook = bookList.getSelectedValue();

        if(selectedUser != null && selectedBook != null) {
            try {
                BookDAO.borrowBook(selectedBook.getBookId(), selectedUser.getUserId());
                BorrowingHistoryDAO.addBorrowingHistory(selectedBook.getBookId(), selectedUser.getUserId());
                loadBooks();;
                statusLabel.setText("Book Borrowed Successfully!");
            }catch (SQLException ex) {
                ex.printStackTrace();
                statusLabel.setText("Database Error!");
            }
        }else {
            statusLabel.setText("Please select a user and book.");
        }
    }



    private void returnBook(){
        User selectedUser = currentUser;
        Book selectedbook = bookList.getSelectedValue();

        if (selectedUser != null && selectedbook != null) {
            try {
                BookDAO.returnBook(selectedbook.getBookId());
                List<BorrowingHistory> borrowingHistory = BorrowingHistoryDAO.getBorrowingHistory(selectedUser.getUserId());

                for (BorrowingHistory history : borrowingHistory) {
                    if (history.getBookId() == selectedbook.getBookId() && history.getReturnedDate() == null){
                        BorrowingHistoryDAO.updateReturnDate(history.getHistoryId());
                        break;
                    }
                }
                loadBooks();
                statusLabel.setText("Book returned Successfully");
            }catch (SQLException ex) {
                ex.printStackTrace();
                statusLabel.setText("Database Error!");
            }
        }else {
            statusLabel.setText("Please select a user and book.");
        }
    }

    private void loadBorrowingHistory(){
        if(currentUser != null) {
            try {
                List<BorrowingHistory> borrowingHistory = BorrowingHistoryDAO.getBorrowingHistory(currentUser.getUserId());

                System.out.println("Borrowing History For " + currentUser.getUsername());

                for (BorrowingHistory history : borrowingHistory) {
                    System.out.println("Book ID: " + history.getBookId() + "Borrowed Date: " + history.getBorrowedDate() + "returned Date: " + history.getReturnedDate());
                }
            }catch (SQLException ex){
                ex.printStackTrace();
                statusLabel.setText("Database Error!");
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }


}
























