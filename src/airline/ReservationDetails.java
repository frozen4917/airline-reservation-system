package airline;

import java.sql.*;
import javax.swing.*;

/**
 * Shows details of a specific reservation from the reservation table (CheckReservations.java)
 * @author kondk
 */

public class ReservationDetails extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReservationDetails.class.getName());
    private static String pnrDB = null; // PNR
    private CheckReservations parentFrame;
    
    /**
     * Creates new form Reservation Details
     * @param parent Parent window of this window i.e. CheckReservations page
     * @param PNR Reservation's PNR number
     * @param flightNumber Flight Number of the selected flight
     * @param departureAirport Departure airport of the selected flight
     * @param arrivalAirport Arrival Airport of the selected flight
     * @param depTime Departure Time
     * @param arrTime Arrival Time
     * @param status Reservation Status
     */
    public ReservationDetails(CheckReservations parent, String PNR, 
            String flightNumber, String departureAirport, 
            String arrivalAirport, String depTime, 
            String arrTime, String status) {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        PNRLabel.setText("PNR: " + PNR);
        flightNumberLabel.setText("Flight Number: " + flightNumber);
        depTimeLabel.setText("Departure Time: " + depTime);
        arrTimeLabel.setText("Arrival Time: " + arrTime);
        this.pnrDB = PNR;
        this.parentFrame = parent;
        loadDetails(PNR,flightNumber,departureAirport,arrivalAirport);
    }

    /**
     * Loads all data fields of the reservation
     * @param PNR PNR of the reservation
     * @param flightNumber Flight number of selected flight
     * @param DEP Departure airport code
     * @param ARR Arrival Airport code
     */
    private void loadDetails(String PNR, String flightNumber, String DEP, String ARR) {
        // String username = Utility.Session.getUsername();
        
        // DATABASE CONNECTION
        try (Connection conn = Utility.DatabaseConnection.getConnection()) {
            // String PaxID = Utility.Session.getPaxID();

            // Step 1: Get Airline Name from Flight Number
            String sql1 = "SELECT a.AirlineName " +
             "FROM flights f " +
             "JOIN airlines a ON f.AirlineID = a.AirlineID " + 
             "WHERE f.FlightNumber = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql1)) {
                pst.setString(1, flightNumber);
                try (ResultSet rs2 = pst.executeQuery()) {
                    if (rs2.next()) { 
                        airlineLabel.setText("Airline: " + rs2.getString("AirlineName"));
                    }
                }
            }
            
            // Step 2: Get departure airport name from airport code
            String sql2 = "SELECT AirportName " + 
                    "From airport WHERE AirportCode = ?;";
            try (PreparedStatement pst = conn.prepareStatement(sql2)) {
                pst.setString(1, DEP);
                try (ResultSet rs2 = pst.executeQuery()) {
                    if (rs2.next()) {  
                        depLabel.setText(rs2.getString("AirportName") + " (" + DEP + ")");
                    }
                }
            }
            
            // Step 3: Get arrival airport name from airport code
            String sql3 = "SELECT AirportName " + 
                    "From airport WHERE AirportCode = ?;";
            try (PreparedStatement pst = conn.prepareStatement(sql3)) {
                pst.setString(1, ARR);
                try (ResultSet rs3 = pst.executeQuery()) {
                    if (rs3.next()) {  
                        arrLabel.setText(rs3.getString("AirportName") + " (" + ARR + ")");
                    }
                }
            }
            
            // Step 4: Get seats, fare, and status from PNR
            String sql4 = "SELECT EconomySeats, BusinessSeats, TotalFare, Status from reservations " +
                    " WHERE PNR = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql4)) {
                pst.setString(1, PNR);
                try (ResultSet rs4 = pst.executeQuery()) {
                    if (rs4.next()) { 
                        seatsLabel.setText("Seats: " + rs4.getString("EconomySeats") + " Economy, " + 
                                                    rs4.getString("BusinessSeats") + " Business");
                        fareLabel.setText("Fare: " + rs4.getString("TotalFare"));
                        statusLabel.setText("Status: " + rs4.getString("Status"));
                        
                        if ("CONFIRMED".equalsIgnoreCase(rs4.getString("Status"))) {
                            statusLabel.setForeground(new java.awt.Color(0,100,0)); // Sets foreground to Dark Green if Status is "CONFIRMED"
                        } else { 
                            statusLabel.setForeground(java.awt.Color.RED); // Sets foreground to Red if status is "CANCELLED"
                            cancelButton.setEnabled(false); // Disables cancel button
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error loading reservation: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        reservationDetailsLabel = new javax.swing.JLabel();
        PNRLabel = new javax.swing.JLabel();
        flightNumberLabel = new javax.swing.JLabel();
        airlineLabel = new javax.swing.JLabel();
        depLabel = new javax.swing.JLabel();
        toLabel = new javax.swing.JLabel();
        arrLabel = new javax.swing.JLabel();
        arrTimeLabel = new javax.swing.JLabel();
        depTimeLabel = new javax.swing.JLabel();
        seatsLabel = new javax.swing.JLabel();
        fareLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        reservationDetailsLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        reservationDetailsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reservationDetailsLabel.setText("RESERVATION DETAILS");

        PNRLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        PNRLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PNRLabel.setText("PNR: ");

        flightNumberLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        flightNumberLabel.setText("Flight Number: ");

        airlineLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        airlineLabel.setText("Airline:");

        depLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        depLabel.setText("__Dep__");

        toLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        toLabel.setText("to");

        arrLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        arrLabel.setText("__Arr__");

        arrTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        arrTimeLabel.setText("Arrival Time:");

        depTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        depTimeLabel.setText("Departure Time:");

        seatsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        seatsLabel.setText("Seats: ? Economy, ? Business");

        fareLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        fareLabel.setText("Fare:");

        cancelButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(255, 0, 0));
        cancelButton.setText("Cancel Reservation");
        cancelButton.setToolTipText("");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        statusLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        statusLabel.setText("Status: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(depLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reservationDetailsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PNRLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(flightNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(airlineLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(toLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(arrLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(depTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(arrTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(seatsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                            .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fareLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(cancelButton)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(reservationDetailsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PNRLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(flightNumberLabel)
                    .addComponent(airlineLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(depLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(arrLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(arrTimeLabel)
                    .addComponent(depTimeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(seatsLabel)
                    .addComponent(fareLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(statusLabel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Opens Cancellation Dialog
     * @param evt 
     */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel this reservation? Your amount will be refunded in 3-5 business days in case of cancellation.",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            // DATABASE CONNECTION
            try (Connection conn = Utility.DatabaseConnection.getConnection()) {
                String sql = "UPDATE reservations SET Status = 'CANCELLED' WHERE PNR = ?";
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setString(1, pnrDB);
                    int updated = pst.executeUpdate();

                    if (updated > 0) {
                        JOptionPane.showMessageDialog(this,
                                "Reservation cancelled successfully. Your paid amount will be refunded in 3-5 business days.",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);

                        // Dispose windows
                        this.dispose(); // Closes ReservationDetails
                        if (parentFrame != null) {
                            parentFrame.dispose(); // Closes parent window (CheckReservations)
                        }

                        // Open MainDashboard
                        MainDashboard dashboard = new MainDashboard();
                        dashboard.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Unable to cancel reservation. Please try again.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Database error: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_cancelButtonActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new ReservationDetails(null,"NULL","NULL","NULL","NULL","NULL","NULL","NULL").setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PNRLabel;
    private javax.swing.JLabel airlineLabel;
    private javax.swing.JLabel arrLabel;
    private javax.swing.JLabel arrTimeLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel depLabel;
    private javax.swing.JLabel depTimeLabel;
    private javax.swing.JLabel fareLabel;
    private javax.swing.JLabel flightNumberLabel;
    private javax.swing.JLabel reservationDetailsLabel;
    private javax.swing.JLabel seatsLabel;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JLabel toLabel;
    // End of variables declaration//GEN-END:variables
}
