package com.helpdesk.ui;

import com.helpdesk.dao.TicketDAO;
import com.helpdesk.model.Ticket;
import com.helpdesk.model.User;

import javax.swing.*;
import java.awt.*;

public class TicketDetailPanel extends JFrame {

    private final Ticket ticket;
    private final User user;
    private JComboBox<String> statusBox;
    private JComboBox<String> priorityBox;

    public TicketDetailPanel(Ticket ticket, User user) {
        this.ticket = ticket;
        this.user = user;

        setTitle("Ticket Details - #" + ticket.getId());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel header = new JLabel("Ticket Detail");
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        int y = 0;


        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JLabel(ticket.getTitle()), gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JLabel(ticket.getDescription()), gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Created At:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JLabel(ticket.getCreatedAt().toString()), gbc);
        y++;

        if (user.getRole().equalsIgnoreCase("admin") || user.getRole().equalsIgnoreCase("support")) {

            gbc.gridx = 0; gbc.gridy = y;
            formPanel.add(new JLabel("Status:"), gbc);
            statusBox = new JComboBox<>(new String[]{"New", "In Progress", "Resolved"});
            statusBox.setSelectedItem(ticket.getStatus());
            gbc.gridx = 1;
            formPanel.add(statusBox, gbc);
            y++;


            gbc.gridx = 0; gbc.gridy = y;
            formPanel.add(new JLabel("Priority:"), gbc);
            priorityBox = new JComboBox<>(new String[]{"Low", "Medium", "High"});
            priorityBox.setSelectedItem(ticket.getPriority());
            gbc.gridx = 1;
            formPanel.add(priorityBox, gbc);
            y++;
        } else {

            gbc.gridx = 0; gbc.gridy = y;
            formPanel.add(new JLabel("Status:"), gbc);
            gbc.gridx = 1;
            formPanel.add(new JLabel(ticket.getStatus()), gbc);
            y++;


            gbc.gridx = 0; gbc.gridy = y;
            formPanel.add(new JLabel("Priority:"), gbc);
            gbc.gridx = 1;
            formPanel.add(new JLabel(ticket.getPriority()), gbc);
            y++;
        }

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JButton updateBtn = new JButton("💾 Update");
        updateBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        updateBtn.setBackground(new Color(0, 123, 255));
        updateBtn.setForeground(Color.BLACK);
        updateBtn.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        if (user.getRole().equalsIgnoreCase("admin") || user.getRole().equalsIgnoreCase("support")) {
            buttonPanel.add(updateBtn);
        }

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        updateBtn.addActionListener(e -> updateTicket());
    }

    private void updateTicket() {
        String newStatus = (String) statusBox.getSelectedItem();
        String newPriority = (String) priorityBox.getSelectedItem();

        TicketDAO ticketDAO = new TicketDAO();
        boolean updated = ticketDAO.updateTicketStatusAndPriority(ticket.getId(), newStatus, newPriority);

        if (updated) {
            JOptionPane.showMessageDialog(this, "Ticket updated", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Update failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
