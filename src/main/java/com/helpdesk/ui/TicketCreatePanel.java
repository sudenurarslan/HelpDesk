package com.helpdesk.ui;

import com.helpdesk.dao.TicketDAO;
import com.helpdesk.model.Ticket;
import com.helpdesk.model.User;

import javax.swing.*;
import java.awt.*;

public class TicketCreatePanel extends JFrame {

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<String> priorityBox;
    private final User user;

    public TicketCreatePanel(User user) {
        this.user = user;

        setTitle("Create a ticket");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel heading = new JLabel("New Ticket");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(heading, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 1, 8, 8));

        titleField = new JTextField();
        titleField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);

        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(scrollPane);

        formPanel.add(new JLabel("Priority:"));
        priorityBox = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        priorityBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(priorityBox);

        panel.add(formPanel, BorderLayout.CENTER);

        JButton submitBtn = new JButton("📨 Submit Ticket");
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submitBtn.setBackground(new Color(60, 120, 200));
        submitBtn.setForeground(Color.BLACK);

        submitBtn.addActionListener(e -> submitTicket());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(submitBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void submitTicket() {
        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();
        String priority = (String) priorityBox.getSelectedItem();

        if (title.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in fields", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Ticket ticket = new Ticket(user.getId(), title, description, "New", priority);
        TicketDAO ticketDAO = new TicketDAO();

        boolean success = ticketDAO.createTicket(ticket);
        if (success) {
            JOptionPane.showMessageDialog(this, "Ticket submitted ", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "An error occurred try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
