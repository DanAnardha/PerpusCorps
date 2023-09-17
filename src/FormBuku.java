
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Danuar Anardha
 */
public class FormBuku extends javax.swing.JFrame {

    Connection koneksiDB = Koneksi.getConnection();

    /**
     * Creates new form FormBuku
     */
    public FormBuku() {
        initComponents();
        this.setLocationRelativeTo(null);
        jTextField5.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent de) {
                warn();
            }

            public void removeUpdate(DocumentEvent de) {
                warn();
            }

            public void insertUpdate(DocumentEvent de) {
                warn();
            }

            public void warn() {
                try {
                    ResultSet hasil = koneksiDB.createStatement().executeQuery("select * from buku where id_buku like '%" + jTextField5.getText() + "%' or judul like '%" + jTextField5.getText() + "%' or pengarang like '%" + jTextField5.getText() + "%' or penerbit like '%" + jTextField5.getText() + "%'");
                    DefaultTableModel model = (DefaultTableModel) TBLBuku.getModel();
                    model.setRowCount(0);
                    while (hasil.next()) {
                        model.addRow(new String[]{hasil.getString(1), hasil.getString(2), hasil.getString(3), hasil.getString(4), hasil.getString(5), hasil.getString(6), hasil.getString(7), hasil.getString(8), hasil.getString(9), hasil.getString(10)});
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FormPetugas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        kondisiAwal();
        idOtomatis();
        tampilKategori();
        setResizable(false);
        setDefaultCloseOperation(FormBuku.DISPOSE_ON_CLOSE);
        jTextField1.setEnabled(false);
        jTextField9.setEnabled(false);
        DefaultListCellRenderer dlcr = new DefaultListCellRenderer(); 
        dlcr.setHorizontalAlignment(DefaultListCellRenderer.RIGHT);
        jComboBox2.setRenderer(dlcr);
        setTitle("PerpusCorps - Buku");
    }

    public void idOtomatis() {
        try {
            ResultSet hasil = koneksiDB.createStatement().executeQuery("select id_buku from buku order by id_buku desc limit 1");
            if (hasil.next()) {
                String rnno = hasil.getString("id_buku");
                int co = rnno.length();
                String txt = rnno.substring(0, 2);
                String num = rnno.substring(2, 7);
                int n = Integer.parseInt(num);
                n++;
                String snum = Integer.toString(n);
                String ftxt = txt + snum;
                jTextField1.setText(ftxt);
            } else {
                jTextField1.setText("BK10001");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormPetugas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void kondisiAwal() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            try {
                Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now));
                jDateChooser1.setDate(date2);
            } catch (ParseException ex) {
                Logger.getLogger(FormBuku.class.getName()).log(Level.SEVERE, null, ex);
            }
            jTextField2.setText("");
            jTextField3.setText("");
            jTextField4.setText("");
            jTextField5.setText("");
            jTextField6.setText("");
            jTextField7.setText("");
            jTextField8.setText("");
            jTextField9.setText("");
            ImageIcon icon = new ImageIcon();
            jLabel11.setIcon(icon);
            ResultSet hasil = koneksiDB.createStatement().executeQuery("select * from buku");
            DefaultTableModel model = (DefaultTableModel) TBLBuku.getModel();
            model.setRowCount(0);
            while (hasil.next()) {
                model.addRow(new String[]{hasil.getString(1), hasil.getString(2), hasil.getString(3), hasil.getString(4), hasil.getString(5), hasil.getString(6), hasil.getString(7), hasil.getString(8), hasil.getString(9), hasil.getString(10)});
            }
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }
    
    private void tampilKategori(){
        PreparedStatement stm;
        try {
            stm = koneksiDB.prepareStatement("select kategori from kategori");
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                String name = rs.getString("kategori");
                jComboBox1.addItem(name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
            //stm.executeUpdate();
            
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TBLBuku = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Input"));

        jLabel1.setText("ID Buku");

        jLabel2.setText("Judul");

        jLabel3.setText("Pengarang");

        jLabel4.setText("Penerbit");

        jLabel5.setText("Kategori");

        jLabel6.setText("Tahun");

        jLabel7.setText("Stok");

        jLabel8.setText("Harga");

        jLabel9.setText("Lokasi Gambar");
        jLabel9.setToolTipText("");

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField1");

        jTextField3.setText("jTextField1");

        jTextField4.setText("jTextField1");

        jTextField6.setText("jTextField1");

        jTextField7.setText("jTextField1");

        jTextField8.setText("jTextField1");

        jTextField9.setText("jTextField1");

        jLabel10.setText("Tanggal Masuk");

        jButton5.setText("Pilih");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2)
                    .addComponent(jTextField3)
                    .addComponent(jTextField4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5)))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Sampul Buku"));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Proses"));

        jButton1.setText("SIMPAN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("HAPUS");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("TUTUP");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton2.setText("RESET");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Judul", "Pengarang", "Penerbit", "Kategori", "Tahun", "Stok", "Harga", "Tanggal Masuk" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ascending", "Descending" }));

        jButton6.setText("Urutkan");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jComboBox3, 0, 1, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(84, 84, 84))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2, jButton3, jButton4});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton4)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton2, jButton3, jButton4});

        TBLBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Buku", "Judul", "Pengarang", "Penerbit", "Kategori", "Tahun", "Stok", "Harga", "Gambar", "Tanggal Masuk"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TBLBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TBLBukuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TBLBuku);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Pencarian"));

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField5)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jTextField5)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String tampilan = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);
        String tanggal = String.valueOf(fm.format(jDateChooser1.getDate()));

        String image = jTextField9.getText();
        image = image.replace("\\", "\\\\");
        if (jTextField1.getText().equals("") || jTextField2.getText().equals("") || jTextField3.getText().equals("") || jTextField4.getText().equals("") || jComboBox1.getSelectedItem().toString().equals("") || jTextField6.getText().equals("") || jTextField7.getText().equals("") || jTextField8.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Pastikan tidak ada field yang kosong!");
        } else {
            try {
                PreparedStatement stm = koneksiDB.prepareStatement("select * from buku where id_buku='" + jTextField1.getText() + "'");
                //stm.executeUpdate();
                ResultSet rs = stm.executeQuery();
                if (!rs.next()) {
                    PreparedStatement stmt = koneksiDB.prepareStatement("insert into buku (id_buku, judul, pengarang, penerbit, kategori, tahun, stok, harga, gambar, tanggal_masuk)"
                            + "values ('" + jTextField1.getText() + "','" + jTextField2.getText() + "','" + jTextField3.getText() + "','" + jTextField4.getText() + "','" + jComboBox1.getSelectedItem().toString() + "','" + jTextField6.getText() + "','" + jTextField7.getText() + "','" + jTextField8.getText() + "','" + image + "','" + tanggal + "')");
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Simpan data berhasil!");
                    kondisiAwal();
                    idOtomatis();
                } else {
                    PreparedStatement stmt = koneksiDB.prepareStatement("update buku set judul='" + jTextField2.getText() + "', pengarang='" + jTextField3.getText() + "', penerbit='" + jTextField4.getText() + "', kategori='" + jComboBox1.getSelectedItem().toString() + "', tahun='" + jTextField6.getText() + "', stok='" + jTextField7.getText() + "', harga='" + jTextField8.getText() + "', gambar='" + image + "', tanggal_masuk='" + tanggal + "' where id_buku='" + jTextField1.getText() + "'");
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Update data berhasil!");
                    kondisiAwal();
                    idOtomatis();
                }

            } catch (HeadlessException | SQLException ex) {
                Logger.getLogger(FormBuku.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            PreparedStatement stm = koneksiDB.prepareStatement("select * from buku where id_buku='" + jTextField1.getText() + "'");
            //stm.executeUpdate();
            ResultSet rs = stm.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Pilih record yang ingin di hapus!!");
            } else {
                PreparedStatement stmt = koneksiDB.prepareStatement("delete from buku where id_buku='" + jTextField1.getText() + "'");
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Hapus data berhasil!");
                kondisiAwal();
                idOtomatis();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormPetugas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        kondisiAwal();
        idOtomatis();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    public void ScaleImage() {
        initComponents();
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();
        jTextField9.setText(filename);
        Image getAbsolutePath = null;
        ImageIcon icon = new ImageIcon(filename);
        Image image = icon.getImage();
        Image imgScale = image.getScaledInstance(jLabel11.getWidth(), jLabel11.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        jLabel11.setIcon(scaledIcon);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void TBLBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TBLBukuMouseClicked
        try {
            int row = TBLBuku.getSelectedRow();
            String to = TBLBuku.getModel().getValueAt(row, 0).toString();
            ResultSet hasil = koneksiDB.createStatement().executeQuery("select * from buku where id_buku ='" + to + "'");
            if (hasil.next()) {
                String id = hasil.getString(1);
                String judul = hasil.getString(2);
                String pengarang = hasil.getString(3);
                String penerbit = hasil.getString(4);
                String kategori = hasil.getString(5);
                String tahun = hasil.getString(6);
                String stok = hasil.getString(7);
                String harga = hasil.getString(8);
                String gambar = hasil.getString(9);

                jTextField1.setText("" + id);
                jTextField2.setText(judul);
                jTextField3.setText(pengarang);
                jTextField4.setText(penerbit);
                jComboBox1.setSelectedItem(kategori);
                jTextField6.setText(tahun);
                jTextField7.setText(stok);
                jTextField8.setText(harga);
                jTextField9.setText(gambar);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                try {
                    Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(hasil.getString(10));
                    jDateChooser1.setDate(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(FormBuku.class.getName()).log(Level.SEVERE, null, ex);
                }

                ImageIcon icon = new ImageIcon(jTextField9.getText());
                Image image = icon.getImage();
                Image imgScale = image.getScaledInstance(jLabel11.getWidth(), jLabel11.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(imgScale);
                jLabel11.setIcon(scaledIcon);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormPetugas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_TBLBukuMouseClicked

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(jComboBox2.getSelectedItem().toString().equals("ID") && jComboBox3.getSelectedItem().toString().equals("Ascending") ){
            QuickSortAsc.quickSortAsc(TBLBuku, 0, 0, TBLBuku.getRowCount()-1);
        } else if (jComboBox2.getSelectedItem().toString().equals("ID") && jComboBox3.getSelectedItem().toString().equals("Descending")){
            QuickSortDesc.quickSortAsc(TBLBuku, 0, 0, TBLBuku.getRowCount()-1);
        } else if(jComboBox2.getSelectedItem().toString().equals("Judul") && jComboBox3.getSelectedItem().toString().equals("Ascending")){
            QuickSortAsc.quickSortAsc(TBLBuku, 1, 0, TBLBuku.getRowCount()-1);
        } else if(jComboBox2.getSelectedItem().toString().equals("Judul") && jComboBox3.getSelectedItem().toString().equals("Descending")){
            QuickSortDesc.quickSortAsc(TBLBuku, 1, 0, TBLBuku.getRowCount()-1);
        } else if(jComboBox2.getSelectedItem().toString().equals("Pengarang") && jComboBox3.getSelectedItem().toString().equals("Ascending")){
            QuickSortAsc.quickSortAsc(TBLBuku, 2, 0, TBLBuku.getRowCount()-1);
        } else if(jComboBox2.getSelectedItem().toString().equals("Pengarang") && jComboBox3.getSelectedItem().toString().equals("Descending")){
            QuickSortDesc.quickSortAsc(TBLBuku, 2, 0, TBLBuku.getRowCount()-1);
        } else if(jComboBox2.getSelectedItem().toString().equals("Penerbit") && jComboBox3.getSelectedItem().toString().equals("Ascending")){
            QuickSortAsc.quickSortAsc(TBLBuku, 3, 0, TBLBuku.getRowCount()-1);
        } else if(jComboBox2.getSelectedItem().toString().equals("Penerbit") && jComboBox3.getSelectedItem().toString().equals("Descending")){
            QuickSortDesc.quickSortAsc(TBLBuku, 3, 0, TBLBuku.getRowCount()-1);
        } else if(jComboBox2.getSelectedItem().toString().equals("Kategori") && jComboBox3.getSelectedItem().toString().equals("Ascending")){
            QuickSortAsc.quickSortAsc(TBLBuku, 4, 0, TBLBuku.getRowCount()-1);
        } else if(jComboBox2.getSelectedItem().toString().equals("Kategori") && jComboBox3.getSelectedItem().toString().equals("Descending")){
            QuickSortDesc.quickSortAsc(TBLBuku, 4, 0, TBLBuku.getRowCount()-1);
        } else if(jComboBox2.getSelectedItem().toString().equals("Tahun") && jComboBox3.getSelectedItem().toString().equals("Ascending")){
            QuickSortAsc.quickSortAsc(TBLBuku, 5, 0, TBLBuku.getRowCount()-1);
        } else if(jComboBox2.getSelectedItem().toString().equals("Tahun") && jComboBox3.getSelectedItem().toString().equals("Descending")){
            QuickSortDesc.quickSortAsc(TBLBuku, 5, 0, TBLBuku.getRowCount()-1);
        } else if(jComboBox2.getSelectedItem().toString().equals("Stok") && jComboBox3.getSelectedItem().toString().equals("Ascending")){
            QuickSortAsc.quickSortAsc(TBLBuku, 6, 0, TBLBuku.getRowCount()-1);
        } else if(jComboBox2.getSelectedItem().toString().equals("Stok") && jComboBox3.getSelectedItem().toString().equals("Descending")){
            QuickSortDesc.quickSortAsc(TBLBuku, 6, 0, TBLBuku.getRowCount()-1);
        } else if(jComboBox2.getSelectedItem().toString().equals("Harga") && jComboBox3.getSelectedItem().toString().equals("Ascending")){
            QuickSortAsc.quickSortAsc(TBLBuku, 7, 0, TBLBuku.getRowCount()-1);
        } else if(jComboBox2.getSelectedItem().toString().equals("Harga") && jComboBox3.getSelectedItem().toString().equals("Descending")){
            QuickSortDesc.quickSortAsc(TBLBuku, 7, 0, TBLBuku.getRowCount()-1);
        } else if(jComboBox2.getSelectedItem().toString().equals("Tanggal Masuk") && jComboBox3.getSelectedItem().toString().equals("Ascending")){
            QuickSortAsc.quickSortAsc(TBLBuku, 9, 0, TBLBuku.getRowCount()-1);
        } else if(jComboBox2.getSelectedItem().toString().equals("Tanggal Masuk") && jComboBox3.getSelectedItem().toString().equals("Descending")){
            QuickSortDesc.quickSortAsc(TBLBuku, 9, 0, TBLBuku.getRowCount()-1);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
            String laf = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(laf);
            
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new FormBuku().setVisible(true);
                }
            });
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FormBuku.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FormBuku.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FormBuku.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FormBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TBLBuku;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
