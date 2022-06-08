/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tubespbo;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultCellEditor;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
/**
 *
 * @author Microsoft
 */
public class PenjualanBuku extends javax.swing.JFrame {

    /**
     * Creates new form penjualanBuku
     */private DefaultTableModel model;
    public long total;
    public long bayar;
    public long kembali;
    public Statement st;
    private DefaultTableModel modeltablepenjualan;
    private int currentrow,currentcolumn;
            
    Connection con = Koneksidb.getkoneksi();
    public PenjualanBuku() {
        initComponents();
        tampil_buku();
        model = new DefaultTableModel();

        modeltablepenjualan = new DefaultTableModel();
        modeltablepenjualan.addColumn("id_buku");
        modeltablepenjualan.addColumn("nama_buku");
        modeltablepenjualan.addColumn("harga");
        modeltablepenjualan.addColumn("jumlah");
        
        tampil_item_penjualan();
        //loadData();
        //nofaktur();
    
    }
    public void FilterHuruf(KeyEvent a){
       if(Character.isDigit(a.getKeyChar())){
           a.consume();
           JOptionPane.showMessageDialog(null, "masukan huruf saja!", "peringatan", JOptionPane.WARNING_MESSAGE);
       }
   }
    public void FilterAngka(KeyEvent a){
       if(Character.isAlphabetic(a.getKeyChar())){
           a.consume();
           JOptionPane.showMessageDialog(null, "masukan angka saja!", "peringatan", JOptionPane.WARNING_MESSAGE);
       }
   }
    public final void loadData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            java.sql.Connection c = Koneksidb.getkoneksi();
            Statement s = c.createStatement();

            String sql = "SELECT * FROM penjualan";
            ResultSet r = s.executeQuery(sql);

            while (r.next()) {
                Object[] o = new Object[8];
                o[0] = r.getString("id");
                o[1] = r.getString("tanggal_penjualan");
                o[2] = r.getString("nama_pembeli");
                o[3] = r.getString("no_hp");
                o[4] = r.getString("total");
                o[5] = r.getString("keterangan");
                o[6] = r.getString("tanggal_bayar");
                o[7] = r.getString("status");
                model.addRow(o);
            }
            r.close();
            s.close();
        } catch (SQLException e) {
            System.out.println("Terjadi Error");
        }
   }
      
     
      private void tampil_item_penjualan(){
          
            tableitempenjualan = new javax.swing.JTable();

            tableitempenjualan.setModel(modeltablepenjualan);

            tableitempenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tableBukuMouseClicked(evt);
                }

                private void tableBukuMouseClicked(MouseEvent evt) {
                    currentrow = tableitempenjualan.getSelectedRow();
                    currentcolumn = tableitempenjualan.getSelectedColumn();   
                   
                }
            });
            
            modeltablepenjualan.addTableModelListener(new TableModelListener() {

                @Override
                public void tableChanged(TableModelEvent tableModelEvent) {
                     int totalbelanja=0;
                        for (int index = 0 ; index < modeltablepenjualan.getRowCount();index++){
                            int harga = Integer.parseInt(tableitempenjualan.getModel().getValueAt(index, 2).toString());
                            int jumlah = Integer.parseInt(tableitempenjualan.getModel().getValueAt(index, 3).toString());
                            totalbelanja= (jumlah*harga)+totalbelanja;
                        }
                    totalfield.setText(Integer.toString(totalbelanja));
                }
            });
            
            JTextField cell = new JTextField();
            tableitempenjualan.getDefaultEditor(String.class).addCellEditorListener(
                new CellEditorListener() {
                    public void editingCanceled(ChangeEvent e) {
                        System.out.println("editingCanceled");
                    }

                    public void editingStopped(ChangeEvent e) {
                        int jumlah = Integer.parseInt(tableitempenjualan.getModel().getValueAt(currentrow,currentcolumn).toString());
                        System.out.println(jumlah);
                        modeltablepenjualan.setValueAt(jumlah, currentrow, currentcolumn);
   
                    }
                });

            jScrollPane1.setViewportView(tableitempenjualan);


      }
     private void tampil_buku(){
     // membuat tampilan model tabel
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id buku");
        model.addColumn("No.Isbn");
        model.addColumn("Judul");
        model.addColumn("Pengarang");
        model.addColumn("Penerbit");
        model.addColumn("Tahun Terbit");
        model.addColumn("Keategori buku");
        model.addColumn("Stok");
        model.addColumn("Harga Pokok");
        model.addColumn("Harga Jual");
        //menampilkan data database kedalam tabel
        try {
            String sql = "select * from buku";
            java.sql.Statement stm=con.createStatement();
            java.sql.ResultSet res=stm.executeQuery(sql);
            while(res.next()){
                model.addRow(new Object[]{res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5)
                               ,res.getString(6),res.getString(7),res.getString(8),res.getString(9),res.getString(10)});
            }
            tableitempenjualan.setModel(model);
            res.last();
            int jumlahdata = res.getRow();
            res.first();
        } catch (Exception e) {
        }
    }
     
     public void tabeltransaksi(){
       DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("tanggal_penjualan");
        model.addColumn("nama_pembeli");
        model.addColumn("no_hp");
        model.addColumn("total");
        model.addColumn("keterangan");
        model.addColumn("tanggal_bayar");
        model.addColumn("status");
        //menampilkan data database kedalam tabel
        try {
            String sql = "select * from penjualan order by tanggal desc";
            java.sql.Statement stm=con.createStatement();
            java.sql.ResultSet res=stm.executeQuery(sql);
            while(res.next()){
                model.addRow(new Object[]{res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getDouble(5)
                               ,res.getString(6),res.getString(7),res.getString(8),res.getString(9),res.getString(10)});
            }
         //tabeltransaksi.setModel(model);
            
        } catch (Exception e) {
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableitempenjualan = new javax.swing.JTable();
        btnDatatransaksi = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        simpanbutton = new javax.swing.JButton();
        alltotal = new javax.swing.JLabel();
        totalfield = new javax.swing.JTextField();
        kembalifield = new javax.swing.JTextField();
        bayarfield = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablebuku = new javax.swing.JTable();
        keteranganfield = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Penjualan Buku");

        tableitempenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id_buku", "nama_buku", "harga", "jumlah"
            }
        ));
        tableitempenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableitempenjualanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableitempenjualan);

        btnDatatransaksi.setText("Data Transaksi");
        btnDatatransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDatatransaksiMouseClicked(evt);
            }
        });
        btnDatatransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDatatransaksiActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel9.setText("Bayar");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel10.setText("Kembalian");

        jLabel11.setText("Cari Buku");

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        simpanbutton.setText("Simpan");
        simpanbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                simpanbuttonMouseClicked(evt);
            }
        });
        simpanbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanbuttonActionPerformed(evt);
            }
        });

        alltotal.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        alltotal.setText("Total");

        totalfield.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        totalfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalfieldActionPerformed(evt);
            }
        });

        kembalifield.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        kembalifield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kembalifieldActionPerformed(evt);
            }
        });

        bayarfield.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        bayarfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarfieldActionPerformed(evt);
            }
        });

        tablebuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id_buku", "judul_buku", "stok", "harga"
            }
        ));
        jScrollPane2.setViewportView(tablebuku);

        keteranganfield.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(alltotal, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(totalfield, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bayarfield, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(kembalifield, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(simpanbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnDatatransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addComponent(keteranganfield, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(53, 53, 53)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(alltotal, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(bayarfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(kembalifield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(keteranganfield))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(simpanbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDatatransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt){
        
    }
    
    private void tableitempenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableitempenjualanMouseClicked
        
    }//GEN-LAST:event_tableitempenjualanMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        int row= tablebuku.getSelectedRow();
        String id_buku = tablebuku.getModel().getValueAt(row, 0).toString();
        String judul_buku = tablebuku.getModel().getValueAt(row, 1).toString();
        String harga = tablebuku.getModel().getValueAt(row, 3).toString();
        
            
        String[] data = new String[4];
        data[0] = id_buku;
        data[1] = judul_buku;
        data[2] = harga;
        data[3] = "1";
        int duplicateRow = 0;
        for (int index = 0 ; index < modeltablepenjualan.getRowCount();index++){
            if(id_buku.equals(tableitempenjualan.getModel().getValueAt(index, 0))){
                duplicateRow = 1;
                break;
            }
        }
        if(duplicateRow !=1)
            modeltablepenjualan.addRow(data);
        
        tableitempenjualan.setModel(modeltablepenjualan);
    }//GEN-LAST:event_btnAddActionPerformed

                     

    private void txbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txbayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txbayarActionPerformed

    private void tabelbukuMouseClicked(java.awt.event.MouseEvent evt) {                                       
        
    }                                     

    private void btnDatatransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatatransaksiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDatatransaksiMouseClicked

    private void btnDatatransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDatatransaksiActionPerformed
        dataTransaksi x = new dataTransaksi();
                x.setVisible(true);
                this.dispose();
    }//GEN-LAST:event_btnDatatransaksiActionPerformed

    private void totalfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalfieldActionPerformed

    private void kembalifieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kembalifieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kembalifieldActionPerformed

    private void bayarfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarfieldActionPerformed
        // TODO add your handling code here:
        int bayar = Integer.parseInt(bayarfield.getText());
        int total = Integer.parseInt(totalfield.getText()); 
        int kembali = 0;
        if(bayar>=total){
            kembali = bayar-total;
            keteranganfield.setText("");
        }else{
            keteranganfield.setText("Uang tidak cukup!");
        }
        kembalifield.setText(Integer.toString(kembali));
    }//GEN-LAST:event_bayarfieldActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
        String s=txtCari.getText();
        System.out.println(s);
        getBuku(s);
    }//GEN-LAST:event_txtCariActionPerformed

    private void simpanbuttonMouseClicked(java.awt.event.MouseEvent evt){
        
    }
    private void simpanbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanbuttonActionPerformed
        // TODO add your handling code here:
       try {
            Statement st = con.createStatement(); 
            for (int index = 0 ; index < modeltablepenjualan.getRowCount();index++){
                int id_buku = Integer.parseInt(tableitempenjualan.getModel().getValueAt(index, 0).toString());
                String nama_buku = (tableitempenjualan.getModel().getValueAt(index, 1).toString());
                int harga = Integer.parseInt(tableitempenjualan.getModel().getValueAt(index, 2).toString());
                int jumlah = Integer.parseInt(tableitempenjualan.getModel().getValueAt(index, 3).toString());
               // st.executeUpdate("INSERT INTO penjualan_item " + 
                //"VALUES ("+id_buku+","++"); 
            }
            
        } catch (Exception e) {
        } 
    }//GEN-LAST:event_simpanbuttonActionPerformed
 
    
    private void getBuku(String title){
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("id_buku");
            model.addColumn("judul_buku");
            //model.addColumn("nomor_buku");
            //model.addColumn("pengarang");
            model.addColumn("stok");
            model.addColumn("harga");
            //model.addColumn("penerbit");
            //model.addColumn("kategori");
        
            java.sql.Connection c = Koneksidb.getkoneksi();
            Statement s = c.createStatement();

            String sql = "SELECT * FROM data_buku where judul_buku like \"%"+ title + "%\"";
            System.out.println(sql);
            ResultSet r = s.executeQuery(sql);

            while (r.next()) {
                Object[] o = new Object[4];
                o[0] = r.getString("id_buku");
                o[1] = r.getString("judul_buku");
                //o[2] = r.getString("nomor_buku");
                //o[3] = r.getString("pengarang");
                o[2] = r.getString("stok");
                o[3] = r.getString("harga");
                //o[6] = r.getString("penerbit");
                //o[7] = r.getString("kategori");
                model.addRow(o);
            }
            r.close();
            s.close();
            tablebuku.setModel(model);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
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
            java.util.logging.Logger.getLogger(PenjualanBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PenjualanBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PenjualanBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PenjualanBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PenjualanBuku().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel alltotal;
    private javax.swing.JTextField bayarfield;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDatatransaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField kembalifield;
    private javax.swing.JLabel keteranganfield;
    private javax.swing.JButton simpanbutton;
    private javax.swing.JTable tablebuku;
    private javax.swing.JTable tableitempenjualan;
    private javax.swing.JTextField totalfield;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}
