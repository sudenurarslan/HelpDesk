package com.helpdesk.ui;

import com.helpdesk.dao.TicketDAO;
import com.helpdesk.model.Ticket;
import com.helpdesk.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TicketListPanel extends JFrame {

    private JTable ticketTable;
    private DefaultTableModel tableModel;
    private final TicketDAO ticketDAO;
    private final User user;
    private JComboBox<String> statusFilter;
    private JComboBox<String> priorityFilter;

    public TicketListPanel(User user) {
        this.user = user;
        this.ticketDAO = new TicketDAO();

        setTitle("Help Desk-Tickets (" + user.getRole().toUpperCase() + ")");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        loadTickets();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel statusLabel = new JLabel("Status:");
        statusFilter = new JComboBox<>(new String[]{"All", "New", "In Progress", "Resolved"});
        JLabel priorityLabel = new JLabel("Priority:");
        priorityFilter = new JComboBox<>(new String[]{"All", "Low", "Medium", "High"});
        JButton filterBtn = new JButton("Apply Filter");

        filterPanel.add(statusLabel);
        filterPanel.add(statusFilter);
        filterPanel.add(priorityLabel);
        filterPanel.add(priorityFilter);
        filterPanel.add(filterBtn);

        panel.add(filterPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Title", "Description", "Status", "Priority", "Created At"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ticketTable = new JTable(tableModel);
        ticketTable.setRowHeight(24);
        ticketTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        ticketTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columnNames.length; i++) {
            ticketTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton createButton = new JButton("➕ New Ticket");
        JButton viewDetailBtn = new JButton("🔍 View Ticket");
        JButton refreshBtn = new JButton("🔄 Refresh");
        createButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        viewDetailBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        refreshBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.add(createButton);
        bottomPanel.add(viewDetailBtn);
        bottomPanel.add(refreshBtn);

        if (user.getRole().equalsIgnoreCase("support")) {
            createButton.setVisible(false);
        }

        panel.add(bottomPanel, BorderLayout.SOUTH);
        add(panel);


        filterBtn.addActionListener(e -> loadTickets());
        refreshBtn.addActionListener(e -> loadTickets());
        viewDetailBtn.addActionListener(e -> openSelectedTicketDetail());
        createButton.addActionListener(e -> new TicketCreatePanel(user));
    }

    private void loadTickets() {
        List<Ticket> tickets;

        if (user.getRole().equalsIgnoreCase("admin") || user.getRole().equalsIgnoreCase("support")) {
            tickets = ticketDAO.getAllTickets();
        } else {
            tickets = ticketDAO.getTicketsByUserId(user.getId());
        }

        String selectedStatus = (String) statusFilter.getSelectedItem();
        String selectedPriority = (String) priorityFilter.getSelectedItem();

        tableModel.setRowCount(0);

        for (Ticket t : tickets) {
            boolean statusMatch = selectedStatus.equals("All") || t.getStatus().equalsIgnoreCase(selectedStatus);
            boolean priorityMatch = selectedPriority.equals("All") || t.getPriority().equalsIgnoreCase(selectedPriority);

            if (statusMatch && priorityMatch) {
                tableModel.addRow(new Object[]{
                        t.getId(),
                        t.getTitle(),
                        t.getDescription(),
                        t.getStatus(),
                        t.getPriority(),
                        t.getCreatedAt()
                });
            }
        }
    }

    private void openSelectedTicketDetail() {
        int selectedRow = ticketTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a ticket.");
            return;
        }

        int ticketId = (int) tableModel.getValueAt(selectedRow, 0);
        Ticket ticket = ticketDAO.getTicketById(ticketId);
        if (ticket != null) {
            new TicketDetailPanel(ticket, user);
        } else {
            JOptionPane.showMessageDialog(this, "Ticket not found.");
        }
    }
}
