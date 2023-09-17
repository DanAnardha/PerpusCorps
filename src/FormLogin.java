
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Danuar Anardha
 */
public class FormLogin extends javax.swing.JFrame {

    Connection koneksiDB = Koneksi.getConnection();
    int xx, xy;

    public FormLogin() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("ggwp.png"));
        Image img1 = image.getImage();
        Image img2 = img1.getScaledInstance(jLabel3.getWidth(), jLabel3.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon i = new ImageIcon(img2);
        jLabel3.setIcon(i);
        setTitle("PerpusCorps - Login");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        LBLIDPetugas = new javax.swing.JLabel();
        LVLPassword = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        LBLIDPetugas.setText("ID Petugas");

        LVLPassword.setText("Password");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordField1KeyPressed(evt);
            }
        });

        jButton1.setText("LOGIN");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("RESET");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("KELUAR");
        jButton3.setMaximumSize(new java.awt.Dimension(77, 30));
        jButton3.setMinimumSize(new java.awt.Dimension(77, 30));
        jButton3.setPreferredSize(new java.awt.Dimension(77, 30));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("Tampilkan Password");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBox2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPasswordField1)
                    .addComponent(jTextField1)
                    .addComponent(LBLIDPetugas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LVLPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(LBLIDPetugas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LVLPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void kondisiAwal() {
        jTextField1.setText("");
        jPasswordField1.setText("");
    }
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x = xx, y = xy);
    }//GEN-LAST:event_formMouseDragged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ImageIcon icon = new ImageIcon("src/images/icons8_cancel_80px_1.png");
        int respon = JOptionPane.showConfirmDialog(this, "Yakin ingin keluar dari PerpusCorps?", "Keluar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
        if (respon == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        kondisiAwal();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String petugas, nama, level, batasbuku, batashari, dendahari, dendarusak;
        try {
            ResultSet hasil = koneksiDB.createStatement().executeQuery("select * from petugas where id_petugas='" + jTextField1.getText() + "'and pass_petugas='" + String.valueOf(jPasswordField1.getText()) + "'");
            if (hasil.next()) {
                try {
                    ResultSet pgt = koneksiDB.createStatement().executeQuery("select * from pengaturan where id_pengaturan ='PGT1001'");
                    if (pgt.next()) {
                        batasbuku = pgt.getString(2);
                        batashari = pgt.getString(3);
                        dendahari = pgt.getString(4);
                        dendarusak = pgt.getString(5);
                        petugas = hasil.getString(1);
                        nama = hasil.getString(2);
                        level = hasil.getString(4);
                        FormMenuUtama fmu = new FormMenuUtama(petugas, nama, level, batasbuku, batashari, dendahari, dendarusak);
                        
                        fmu.setVisible(true);
                        //fmu.setExtendedState(MAXIMIZED_BOTH);
                        dispose();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FormLembaga.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (jTextField1.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Masukkan ID Petugas!");
            } else if (String.valueOf(jPasswordField1.getText()).equals("")) {
                JOptionPane.showMessageDialog(this, "Masukkan Password Petugas!");
            } else {
                JOptionPane.showMessageDialog(this, "ID atau Password Salah!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPasswordField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String petugas, nama, level, batasbuku, batashari, dendahari, dendarusak;
            try {
                ResultSet hasil = koneksiDB.createStatement().executeQuery("select * from petugas where id_petugas='" + jTextField1.getText() + "'and pass_petugas='" + String.valueOf(jPasswordField1.getText()) + "'");
                if (hasil.next()) {
                    try {
                    ResultSet pgt = koneksiDB.createStatement().executeQuery("select * from pengaturan where id_pengaturan ='PGT1001'");
                    if (pgt.next()) {
                        batasbuku = pgt.getString(2);
                        batashari = pgt.getString(3);
                        dendahari = pgt.getString(4);
                        dendarusak = pgt.getString(5);
                        petugas = hasil.getString(1);
                        nama = hasil.getString(2);
                        level = hasil.getString(4);
                        FormMenuUtama fmu = new FormMenuUtama(petugas, nama, level, batasbuku, batashari, dendahari, dendarusak);
                        
                        fmu.setVisible(true);
                        //fmu.setExtendedState(MAXIMIZED_BOTH);
                        dispose();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FormLembaga.class.getName()).log(Level.SEVERE, null, ex);
                }
                } else if (jTextField1.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "Masukkan ID Petugas!");
                } else if (String.valueOf(jPasswordField1.getText()).equals("")) {
                    JOptionPane.showMessageDialog(this, "Masukkan Password Petugas!");
                } else {
                    JOptionPane.showMessageDialog(this, "ID atau Password Salah!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(FormLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jPasswordField1KeyPressed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        if (jCheckBox2.isSelected()) {
            jPasswordField1.setEchoChar((char) 0);
        } else {
            jPasswordField1.setEchoChar('*');
        }
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String petugas, nama, level, batasbuku, batashari, dendahari, dendarusak;
            try {
                ResultSet hasil = koneksiDB.createStatement().executeQuery("select * from petugas where id_petugas='" + jTextField1.getText() + "'and pass_petugas='" + String.valueOf(jPasswordField1.getText()) + "'");
                if (hasil.next()) {
                    try {
                    ResultSet pgt = koneksiDB.createStatement().executeQuery("select * from pengaturan where id_pengaturan ='PGT1001'");
                    if (pgt.next()) {
                        batasbuku = pgt.getString(2);
                        batashari = pgt.getString(3);
                        dendahari = pgt.getString(4);
                        dendarusak = pgt.getString(5);
                        petugas = hasil.getString(1);
                        nama = hasil.getString(2);
                        level = hasil.getString(4);
                        FormMenuUtama fmu = new FormMenuUtama(petugas, nama, level, batasbuku, batashari, dendahari, dendarusak);
                        
                        fmu.setVisible(true);
                        //fmu.setExtendedState(MAXIMIZED_BOTH);
                        dispose();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FormLembaga.class.getName()).log(Level.SEVERE, null, ex);
                }
                } else if (jTextField1.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "Masukkan ID Petugas!");
                } else if (String.valueOf(jPasswordField1.getText()).equals("")) {
                    JOptionPane.showMessageDialog(this, "Masukkan Password Petugas!");
                } else {
                    JOptionPane.showMessageDialog(this, "ID atau Password Salah!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(FormLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTextField1KeyPressed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        SplashScreen ss = new SplashScreen();
        ss.setVisible(true);

        try {
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(20);
            }
            new FormLogin().setVisible(true);
            ss.dispose();
        } catch (InterruptedException ex) {
            Logger.getLogger(FormLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LBLIDPetugas;
    private javax.swing.JLabel LVLPassword;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
