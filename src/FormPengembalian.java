
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.exolab.castor.types.DateTime;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Danuar Anardha
 */
public class FormPengembalian extends javax.swing.JFrame {

    Connection koneksiDB = Koneksi.getConnection();
    private int lamapinjam = 0, denda = 0, total_denda = 0;
    private boolean ditemukan = false;

    /**
     * Creates new form FormPengembalian
     */
    public FormPengembalian() {
        initComponents();
        idOtomatis();
        tampilPinjaman();
        tampilTanggal();
        kondisiAwal();

        jTable1.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                // cek apakah perubahan terjadi pada kolom 3
                if (e.getColumn() == 5) {
                    try {
                        ResultSet hasil = koneksiDB.createStatement().executeQuery("select denda_rusak from pengaturan");
                        if (hasil.next()) {
                            int row = e.getFirstRow();
                            String rusak = jTable1.getModel().getValueAt(row, 5).toString();
                            int penambahan = Integer.parseInt(hasil.getString(1)) + denda;
                            if (rusak.equals("Ya")) {
                                jTable1.getModel().setValueAt("Tidak", row, 6);
                                jTable1.getModel().setValueAt(Integer.toString(penambahan), row, 7);
                                hitungDenda();
                            } else if (rusak.equals("Tidak")) {
                                jTable1.getModel().setValueAt(Integer.toString(denda), row, 7);
                                hitungDenda();
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(FormPengembalian.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                if (e.getColumn() == 6) {
                    try {
                        int row = e.getFirstRow();
                        String hilang = jTable1.getModel().getValueAt(row, 6).toString();
                        ResultSet hasil = koneksiDB.createStatement().executeQuery("select harga from buku where id_buku='" + jTable1.getModel().getValueAt(row, 0) + "'");
                        if (hasil.next()) {
                            String harga = hasil.getString(1);
                            if (hilang.equals("Ya")) {
                                jTable1.getModel().setValueAt("Tidak", row, 5);
                                jTable1.getModel().setValueAt(harga, row, 7);
                                hitungDenda();
                            } else if (hilang.equals("Tidak")) {
                                jTable1.getModel().setValueAt(Integer.toString(denda), row, 7);
                                hitungDenda();
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(FormPengembalian.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        jTable1.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                // Cek apakah tombol "Del" ditekan
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    // Ambil baris yang dipilih oleh user
                    int[] rows = jTable1.getSelectedRows();

                    // Hapus baris yang dipilih oleh user dari model
                    for (int i = 0; i < rows.length; i++) {
                        model.removeRow(rows[i] - i);
                    }
                }
            }
        });
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(FormPengembalian.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("PerpusCorps - Transaksi Pengembalian");
    }

    private void kondisiAwal() {
        jComboBox1.setSelectedIndex(-1);
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField7.setText("0");
        jTextField8.setText("0");
        jTextField9.setText("0");
        jTextField1.setEditable(false);
        jTextField2.setEditable(false);
        jTextField3.setEditable(false);
        jTextField4.setEditable(false);
        jTextField5.setEditable(false);
        jTextField7.setEditable(false);
        jTextField9.setEditable(false);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();
        model2.setRowCount(0);
    }

    private void hitungDenda() {
        total_denda = 0;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            total_denda += Integer.valueOf(jTable1.getValueAt(i, 7).toString());
        }
        jTextField7.setText(Integer.toString(total_denda));
    }

    private void idOtomatis() {
        try {
            ResultSet hasil = koneksiDB.createStatement().executeQuery("select id_kembali from pengembalian order by id_kembali desc limit 1");
            if (hasil.next()) {
                String rnno = hasil.getString("id_kembali");
                int co = rnno.length();

                String txt = rnno.substring(0, 3);
                String num = rnno.substring(3, 9);
                int n = Integer.parseInt(num);
                n++;
                String snum = Integer.toString(n);
                String ftxt = txt + snum;
                jTextField1.setText(ftxt);

            } else {
                jTextField1.setText("KBL100000");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormPetugas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tampilTanggal() {
        SimpleDateFormat s = new SimpleDateFormat("dd MMMM yy");
        Date d = new Date();
        jTextField2.setText(s.format(d));
//        new Timer(0, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                SimpleDateFormat s = new SimpleDateFormat("HH:mm");
//                Date d = new Date();
//                jTextField9.setText(s.format(d));
//            }
//        }).start();
    }

    private void tampilPinjaman() {
        PreparedStatement stm;
        jComboBox1.removeAllItems();
        try {
            stm = koneksiDB.prepareStatement("select distinct id_pinjam from detail_pinjam where status='Dipinjam'");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String name = rs.getString("id_pinjam");
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

        jTextField6 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        jTextField6.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Peminjaman"));

        jLabel1.setText("ID Pengembalian");

        jLabel2.setText("Tanggal Kembali");

        jLabel3.setText("ID Peminjaman");

        jTextField2.setText("jTextField1");

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel4.setText("ID Anggota");

        jLabel5.setText("Total Pinjaman");

        jTextField3.setText("jTextField1");

        jTextField4.setText("jTextField1");

        jTextField5.setText("jTextField1");

        jLabel8.setText("Kembali");

        jLabel7.setText("Dibayar");

        jLabel6.setText("Total Denda");

        jTextField7.setText("jTextField1");

        jTextField8.setText("jTextField1");
        jTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField8KeyPressed(evt);
            }
        });

        jTextField9.setText("jTextField1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Proses"));

        jButton1.setText("SIMPAN");
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

        jButton3.setText("TUTUP");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Proses Pengembalian"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Buku", "Judul", "Tanggal Pinjam", "Lama Pinjam", "Terlambat", "Rusak", "Hilang", "Denda", "Jumlah"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable1PropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Sedang Dipinjam"));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Buku", "Judul", "Pengarang", "Penerbit", "Jumlah"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(266, 266, 266))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Cover Buku"));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        try {
            ResultSet hasil = koneksiDB.createStatement().executeQuery("select id_anggota from peminjaman where id_pinjam='" + jComboBox1.getSelectedItem() + "'");
            if (hasil.next()) {
                jTextField4.setText(hasil.getString(1));
            } else {
                jTextField4.setText("");
            }

            ResultSet anggota = koneksiDB.createStatement().executeQuery("select nama_anggota from anggota where id_anggota='" + jTextField4.getText() + "'");
            if (anggota.next()) {
                jTextField3.setText(anggota.getString(1));
            }

            ResultSet tp = koneksiDB.createStatement().executeQuery("select total_pinjam from peminjaman where id_pinjam='" + jComboBox1.getSelectedItem() + "'");
            if (tp.next()) {
                jTextField5.setText(tp.getString(1));
            }

            ResultSet buku = koneksiDB.createStatement().executeQuery("select nama_anggota from anggota where id_anggota='" + jTextField4.getText() + "'");
            if (buku.next()) {
                jTextField3.setText(anggota.getString(1));
            }

            ResultSet hsl = koneksiDB.createStatement().executeQuery("select detail_pinjam.id_buku, buku.judul, buku.pengarang, buku.penerbit, detail_pinjam.jumlah, buku.gambar from detail_pinjam  inner join buku on buku.id_buku=detail_pinjam.id_buku where detail_pinjam.status='Dipinjam' and detail_pinjam.id_pinjam='" + jComboBox1.getSelectedItem() + "'");
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);
            while (hsl.next()) {
                model.addRow(new String[]{hsl.getString(1), hsl.getString(2), hsl.getString(3), hsl.getString(4), hsl.getString(5)});
            }
//            jTextField13.setText(Integer.toString(pernahpinjam));
//            bat = Integer.parseInt(jTextField13.getText());
//            bat2 = Integer.parseInt(jTextField15.getText());
//            total = bat + bat2;
//            jTextField14.setText(Integer.toString(total));
        } catch (SQLException ex) {
            Logger.getLogger(FormLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        try {
            int row = jTable2.getSelectedRow();
            String to = jTable2.getModel().getValueAt(row, 0).toString();
            ResultSet hasil = koneksiDB.createStatement().executeQuery("select gambar from buku where id_buku ='" + to + "'");
            if (hasil.next()) {
                String gambar = hasil.getString(1);
                ImageIcon icon = new ImageIcon(gambar);
                Image image = icon.getImage();
                Image imgScale = image.getScaledInstance(jLabel9.getWidth(), jLabel9.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(imgScale);
                jLabel9.setIcon(scaledIcon);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormPengembalian.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (evt.getClickCount() == 2) {
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            String[] a = {"Ya", "Tidak"};
            JComboBox rusak = new JComboBox(a);
            JComboBox hilang = new JComboBox(a);
            try {
                ResultSet hasil = koneksiDB.createStatement().executeQuery("select tanggal_pinjam, harus_kembali from peminjaman where id_pinjam ='" + jComboBox1.getSelectedItem() + "'");
                if (hasil.next()) {
                    String tanggal_pinjam = hasil.getString(1);
                    String harus_kembali = hasil.getString(2);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate tgl_pinjam = LocalDate.parse(tanggal_pinjam, formatter);
                    LocalDate keterlambatan = LocalDate.parse(harus_kembali, formatter);
                    LocalDate endDate = LocalDate.now();
                    Period lama = Period.between(tgl_pinjam, endDate);
                    Period terlambat = Period.between(keterlambatan, endDate);
                    int tlbt = terlambat.getDays();
                    if (tlbt < 0) {
                        tlbt = 0;
                    }

                    ResultSet pgt = koneksiDB.createStatement().executeQuery("select denda_hari from pengaturan where id_pengaturan ='PGT1001'");
                    if (pgt.next()) {
                        int row = jTable2.getSelectedRow();
                        denda = Integer.parseInt(pgt.getString(1)) * tlbt;
                        String id = jTable2.getModel().getValueAt(row, 0).toString();
                        String judul = jTable2.getModel().getValueAt(row, 1).toString();
                        String jumlah = jTable2.getModel().getValueAt(row, 4).toString();
                        String data[] = {id, judul, s.format(d), Integer.toString(lama.getDays()), Integer.toString(tlbt), "Tidak", "Tidak", Integer.toString(denda), jumlah};
                        jTable1.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(rusak));
                        jTable1.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(hilang));
                        DefaultTableModel model1 = (DefaultTableModel) jTable1.getModel();
                        DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();
                        boolean found = false;
                        for (int i = 0; i < jTable2.getRowCount(); i++) {
                            String data2 = (String) jTable2.getValueAt(i, 0);
                            found = false;
                            for (int j = 0; j < jTable1.getRowCount(); j++) {
                                String data1 = (String) jTable1.getValueAt(j, 0);
                                if (data2.equals(data1)) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                        if (!found) {
                            model.addRow(data);
                        }
                        if (found) {
                            JOptionPane.showMessageDialog(this, "Buku dalam proses pengembalian!");
                        }

                    }
                }
                hitungDenda();
            } catch (SQLException ex) {
                Logger.getLogger(FormPengembalian.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange

    }//GEN-LAST:event_jTable1PropertyChange

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        kondisiAwal();
        idOtomatis();
        tampilPinjaman();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (Integer.parseInt(jTextField7.getText()) > Integer.parseInt(jTextField8.getText())) {
            JOptionPane.showMessageDialog(this, "Denda belum dibayar!");
        } else {
            if (jTable1.getRowCount() < 1) {
                JOptionPane.showMessageDialog(this, "Silakan melakukan transaksi terlebih dahulu!");
            } else {
                try {
                    SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
                    Date tanggal = new Date();
                    Date kembali = new Date();
                    DateTime dt = new DateTime(tanggal);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(kembali);
                    kembali = cal.getTime();
                    SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                    Date d = new Date();
                    PreparedStatement stm = koneksiDB.prepareStatement("select * from pengembalian where id_kembali='" + jComboBox1.getSelectedItem().toString() + "'");
                    //stm.executeUpdate();
                    ResultSet rs = stm.executeQuery();
                    int ttl_kbl = 0;
                    for (int i = 0; i < jTable1.getRowCount(); i++) {
                        ttl_kbl += Integer.parseInt(jTable1.getValueAt(i, 8).toString());
                    }
                    if (!rs.next()) {
                        PreparedStatement stmt = koneksiDB.prepareStatement("insert into pengembalian (id_kembali, id_anggota, id_petugas, tanggal_kembali, jam_kembali, total_kembali, total_denda, lama_pinjam)"
                                + "values ('" + jTextField1.getText() + "','" + jTextField4.getText() + "','" + FormMenuUtama.jLabel1.getText() + "','" + s.format(tanggal) + "','" + time.format(d) + "','" + ttl_kbl + "','" + jTextField7.getText() + "','" + jTable1.getValueAt(0, 3).toString() + "')");
                        stmt.executeUpdate();
                        String status = "Dipinjam";
                        for (int i = 0; i < jTable1.getRowCount(); i++) {
                            if (jTable1.getValueAt(i, 5).toString().equals("Ya")) {
                                status = "Rusak";
                            } else if (jTable1.getValueAt(i, 6).toString().equals("Ya")) {
                                status = "Hilang";
                            } else {
                                status = "Dikembalikan";
                            }

                            int stok;
                            if (jTable1.getValueAt(i, 6).toString().equals("Ya")) {
                                stok = 0;
                            } else {
                                stok = Integer.parseInt(jTable1.getValueAt(i, 8).toString());
                            }

                            PreparedStatement simpandetail = koneksiDB.prepareStatement("insert into detail_kembali (id_kembali, id_buku, rusak, hilang, terlambat, denda, status, jumlah)"
                                    + "values ('" + jTextField1.getText() + "','" + jTable1.getValueAt(i, 0).toString() + "','" + jTable1.getValueAt(i, 5).toString() + "','" + jTable1.getValueAt(i, 6).toString() + "','" + jTable1.getValueAt(i, 4).toString() + "','" + Integer.parseInt(jTable1.getValueAt(i, 7).toString()) + "','" + status + "','" + Integer.parseInt(jTable1.getValueAt(i, 8).toString()) + "')");
                            simpandetail.executeUpdate();

                            PreparedStatement tambahstok = koneksiDB.prepareStatement("update buku set stok=stok+'" + stok + "' where id_buku ='" + jTable1.getValueAt(i, 0).toString() + "'");
                            tambahstok.executeUpdate();

                            PreparedStatement setstatus = koneksiDB.prepareStatement("update detail_pinjam set status='" + status + "' where id_pinjam ='" + jComboBox1.getSelectedItem().toString() + "' and id_buku='" + jTable1.getValueAt(i, 0).toString() + "'");
                            setstatus.executeUpdate();
                        }

                        int respon = JOptionPane.showConfirmDialog(this, "Cetak bukti pengembalian?", "Cetak Nota", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (respon == JOptionPane.YES_OPTION) {
                            try {

                                SimpleDateFormat waktu = new SimpleDateFormat("dd MMMM yy");
                                Date de = new Date();
                                Date e = new Date();
                                DateTime dte = new DateTime(d);
                                jTextField7.setText(s.format(de));

                                SimpleDateFormat se = new SimpleDateFormat("HH:mm");
                                Date dd = new Date();

                                // mengambil laporan dari file
                                JasperReport report = JasperCompileManager.compileReport("src\\Laporan\\NotaKembali.jrxml");
                                JasperReport detail = JasperCompileManager.compileReport("src\\Laporan\\NotaKembaliDetail.jrxml");
                                // mengisi laporan dengan data
                                HashMap parameter = new HashMap();
                                parameter.put("id_kembali", jTextField1.getText());
                                parameter.put("nama_anggota", jTextField3.getText());
                                parameter.put("nama_petugas", FormMenuUtama.jLabel2.getText());
                                parameter.put("tanggal_kembali", s.format(de));
                                Calendar cale = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                String jamSekarang = sdf.format(cal.getTime());
                                
                                parameter.put("jam_kembali", jamSekarang);
                                JasperPrint print = JasperFillManager.fillReport(report, parameter, koneksiDB);
                                // menampilkan laporan menggunakan JasperViewer
                                JasperViewer.viewReport(print, false);
                                setDefaultCloseOperation(JasperViewer.DISPOSE_ON_CLOSE);
                                setResizable(false);
                                kondisiAwal();
                                idOtomatis();
                            } catch (JRException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Simpan data berhasil!");
                            kondisiAwal();
                            idOtomatis();
                            tampilPinjaman();
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FormPengembalian.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField8KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (Integer.parseInt(jTextField7.getText()) > Integer.parseInt(jTextField8.getText())) {
                JOptionPane.showMessageDialog(this, "Pembayaran kurang!");
            } else if (Integer.parseInt(jTextField8.getText()) >= Integer.parseInt(jTextField7.getText())) {
                int total = Integer.parseInt(jTextField8.getText()) - Integer.parseInt(jTextField7.getText());
                jTextField9.setText(Integer.toString(total));
            }
        }
    }//GEN-LAST:event_jTextField8KeyPressed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPengembalian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
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
