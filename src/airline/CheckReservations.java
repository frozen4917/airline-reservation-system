/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package airline;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * Displays all user reservations and allows to view it in detail or to cancel a reservation
 * @author kondk
 */

public class CheckReservations extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CheckReservations.class.getName());

    /**
     * Creates new form CheckReservations
     */
    public CheckReservations() {
        initComponents();
        loadReservations();
    }
    
    /**
     * Loads all reservations into the table
     */
    private void loadReservations() {
        String username = Utility.Session.getUsername();  // Currently logged in user

        // DATABASE CONNECTION
        try (Connection conn = Utility.DatabaseConnection.getConnection()) {
            String PaxID = Utility.Session.getPaxID();  // Passenger ID of the current user

            // 2. Fetch Reservations by combining flights and reservations table
            String sql = "SELECT r.PNR AS 'PNR', " +
                         "f.FlightNumber AS 'Flight Number', " +
                         "f.DepartureAirport AS 'Departure', " +
                         "f.ArrivalAirport AS 'Arrival', " +
                         "f.DepTime AS 'Departure Time', " +
                         "f.ArrTime AS 'Arrival Time', " +
                         "r.Status AS 'Status' " +
                         "FROM flights f " +
                         "JOIN reservations r ON f.FlightNumber = r.FlightNumber WHERE PaxID = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, PaxID);
                ResultSet rs = pst.executeQuery();

                reservationsTable.setModel(buildTableModel(rs)); // <-- helper method to build table
                reservationsTable.setDefaultEditor(Object.class, null); // Non Editable
                reservationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allows only single selection
                reservationsTable.getTableHeader().setReorderingAllowed(false); // Disable reordering
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error loading reservations: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Helper function to build the table
     * @param rs
     * @return DefaultTableModel
     * @throws Exception 
     */
    public static DefaultTableModel buildTableModel(ResultSet rs) throws Exception {
        ResultSetMetaData metaData = rs.getMetaData();

        // Column names
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnLabel(i));
        }

        // Desired date output format
        java.time.format.DateTimeFormatter outputFormat =
                java.time.format.DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a");

        // Data rows
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                Object value = rs.getObject(i);

                if (value != null) {
                    try {
                        if (value instanceof java.sql.Timestamp) {
                            // Handle SQL Timestamp
                            java.time.LocalDateTime dt = ((java.sql.Timestamp) value).toLocalDateTime();
                            value = dt.format(outputFormat);
                        } else if (value instanceof java.sql.Date) {
                            // Handle SQL Date
                            java.time.LocalDateTime dt = ((java.sql.Date) value).toLocalDate().atStartOfDay();
                            value = dt.format(outputFormat);
                        } else if (value.toString().contains("T")) {
                            // Handle ISO string like "2025-09-06T15:30"
                            java.time.LocalDateTime dt = java.time.LocalDateTime.parse(value.toString());
                            value = dt.format(outputFormat);
                        }
                    } catch (Exception e) {
                        // fallback: show raw value
                        value = value.toString();
                    }
                }
                row.add(value);
            }
            data.add(row);
        }

        return new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells non-editable
            }
        };
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rtScrollPane = new javax.swing.JScrollPane();
        reservationsTable = new javax.swing.JTable();
        returnButton = new javax.swing.JButton();
        purgeButton = new javax.swing.JButton();
        detailsButton = new javax.swing.JButton();
        reservationsTitleLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        reservationsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        reservationsTable.getTableHeader().setReorderingAllowed(false);
        rtScrollPane.setViewportView(reservationsTable);

        returnButton.setText("Return Back");
        returnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnButtonActionPerformed(evt);
            }
        });

        purgeButton.setText("Purge Cancelled Flights");
        purgeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purgeButtonActionPerformed(evt);
            }
        });

        detailsButton.setText("Check Details");
        detailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailsButtonActionPerformed(evt);
            }
        });

        reservationsTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        reservationsTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reservationsTitleLabel.setText("YOUR RESERVATIONS");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rtScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(returnButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(purgeButton)
                        .addGap(18, 18, 18)
                        .addComponent(detailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(reservationsTitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(reservationsTitleLabel)
                .addGap(12, 12, 12)
                .addComponent(rtScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(returnButton)
                    .addComponent(purgeButton)
                    .addComponent(detailsButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Returns user back to the main dashboard
     * @param evt 
     */
    private void returnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnButtonActionPerformed
        MainDashboard dashboard = new MainDashboard();
        dashboard.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_returnButtonActionPerformed

    /**
     * Deletes all cancelled flights of the user from database and updates table accordingly
     * @param evt 
     */
    private void purgeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purgeButtonActionPerformed
        int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to purge all your cancelled flights?",
                "Confirm Purge",
                JOptionPane.YES_NO_OPTION
        );

        // If user clicks YES
        if (response == JOptionPane.YES_OPTION) {
            // DATABASE CONNECTION
            try (Connection conn = Utility.DatabaseConnection.getConnection();
             Statement st = conn.createStatement()) {
                String sql = "DELETE FROM reservations WHERE status = ? AND PaXID = ?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, "CANCELLED");
                pst.setString(2, Utility.Session.getPaxID());
                int deletedRows = pst.executeUpdate();

                if (deletedRows > 0) {
                    JOptionPane.showMessageDialog(null, deletedRows + " cancelled flights purged.");

                    // Update table
                    DefaultTableModel model = (DefaultTableModel) reservationsTable.getModel();
                    for (int i = model.getRowCount() - 1; i >= 0; i--) {
                        String status = model.getValueAt(i, 6).toString();
                        if (status.equals("CANCELLED")) {
                            model.removeRow(i);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No cancelled flights to purge.");
                }
                pst.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error purging cancelled flights.");
            }
        }
        
    }//GEN-LAST:event_purgeButtonActionPerformed

    /**
     * Shows details about a selected reservation
     * @param evt 
     */
    private void detailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailsButtonActionPerformed
        int selectedRow = reservationsTable.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a reservation to view.");
            return;
        }

        // Extract data from table
        String PNR = reservationsTable.getValueAt(selectedRow, 0).toString();
        String flightNumber = reservationsTable.getValueAt(selectedRow, 1).toString();
        String departureAirport = reservationsTable.getValueAt(selectedRow, 2).toString();
        String arrivalAirport = reservationsTable.getValueAt(selectedRow, 3).toString();
        String depTime = reservationsTable.getValueAt(selectedRow, 4).toString();
        String arrTime = reservationsTable.getValueAt(selectedRow, 5).toString();
        String status = reservationsTable.getValueAt(selectedRow, 6).toString();
        
        // Pass detail + parent window to ReservationDetails.java and make it visible
        ReservationDetails rd = new ReservationDetails(this, PNR, flightNumber, departureAirport, arrivalAirport, depTime, arrTime, status);
        rd.setVisible(true);
    }//GEN-LAST:event_detailsButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new CheckReservations().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton detailsButton;
    private javax.swing.JButton purgeButton;
    private javax.swing.JTable reservationsTable;
    private javax.swing.JLabel reservationsTitleLabel;
    private javax.swing.JButton returnButton;
    private javax.swing.JScrollPane rtScrollPane;
    // End of variables declaration//GEN-END:variables
}
