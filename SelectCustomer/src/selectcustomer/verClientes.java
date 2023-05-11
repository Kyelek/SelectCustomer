/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package selectcustomer;

import conexion.conexion;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.console;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Desktop;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Kyelek
 */
public class verClientes extends javax.swing.JFrame {

    conexion conex = new conexion();
    Connection con = conex.getConexion();

    String nombre;
    String apellidos;
    String domicilio;
    String telefono;
    String email;
    String producto;
    String precio;

    public verClientes() {
        initComponents();

        mostrarTablaClientes();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public void mostrarTablaClientes() {

        String[] encabezados = {"Id", "Nombre", "Apellidos", "domicilio", "telefono", "Email", "Producto", "Precio"};
        String[] registros = new String[8];

        DefaultTableModel model = new DefaultTableModel(null, encabezados);

        String cadSQL = "Select * from clientes";

        try {
            Statement st = con.createStatement();
            ResultSet rset = st.executeQuery(cadSQL); // Contiene la consulta

            while (rset.next()) {
                registros[0] = rset.getString("Id");
                registros[1] = rset.getString("Nombre");
                registros[2] = rset.getString("Apellidos");
                registros[3] = rset.getString("domicilio");
                registros[4] = rset.getString("telefono");
                registros[5] = rset.getString("Email");
                registros[6] = rset.getString("Producto");
                registros[7] = rset.getString("Precio");

                model.addRow(registros);
                tbl_tablaClientes.setModel(model);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "La tabla no se muestra correctamente");
        }
    }

    public void registrarCliente() {

        String SQL = "INSERT INTO clientes (nombre,apellidos,domicilio,telefono,email,producto,precio) VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement pst = con.prepareStatement(SQL);

            pst.setString(1, txt_name.getText());
            pst.setString(2, txt_Apellidos.getText());
            pst.setString(3, txt_Domicilio.getText());
            pst.setInt(4, Integer.parseInt(txt_Telefono.getText()));
            pst.setString(5, txt_email.getText());
            pst.setString(6, txt_producto.getText());
            pst.setString(7, txt_precio.getText());
            //pst.setInt(7,Integer.parseInt(txt_precio.getText()));

            pst.execute();
            System.out.println("Cliente guardado");

        } catch (Exception e) {
            System.out.println("No se ha insertado el registro" + e);
        }
    }

    public void escribirEnTXT() throws IOException {

        nombre = txt_name.getText();
        apellidos = txt_Apellidos.getText();
        domicilio = txt_Domicilio.getText();
        telefono = txt_Telefono.getText();
        email = txt_email.getText();
        producto = txt_producto.getText();

        // Buscar todas las cadenas de números en producto y sumarlos
        int total = 0;
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(producto);
        while (matcher.find()) {
            total += Integer.parseInt(matcher.group());
        }
        // Convertir el total a una cadena y guardarlo en precio
        precio = Integer.toString(total);

        String rutaProyecto = "D:\\Kyelek\\Documentos\\NetBeansProjects\\SelectCustomer"; // Establecer la ruta manualmente
        String rutaCarpetaArchivos = rutaProyecto + File.separator + "Clientes";
        File carpetaArchivos = new File(rutaCarpetaArchivos);
        //System.out.println(rutaCarpetaArchivos);

        if (!carpetaArchivos.exists()) {
            carpetaArchivos.mkdirs();
        }

        int contador = 1;
        File archivo = new File(carpetaArchivos, nombre + ".html");
        while (archivo.exists()) {
            contador++;
            archivo = new File(carpetaArchivos, nombre + contador + ".html");
        }

        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write("<h1>Cliente: " + nombre + " " + apellidos + "</h1>\n");
            writer.write("<p>Domicilio: " + domicilio + "</p>\n");
            writer.write("<p>Teléfono: " + telefono + "</p>\n");
            writer.write("<p>Email: " + email + "</p>\n");
            writer.write("<p>Producto: <br> " + producto + "</p>\n");
            writer.write("<p>Precio: " + precio + "</p>\n");

            JOptionPane.showMessageDialog(null, "Archivo creado correctamente en la carpeta \"archivos\".");
            Desktop.getDesktop().open(archivo);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el archivo de texto: " + e.getMessage());
        }
    }

    public void actualizarCliente() {
        // nombre,apellidos,domicilio,telefono,email,producto,precio
        try {
            String cadSQL = "update clientes set nombre =?,apellidos =?,domicilio =?, telefono=?, email=?, producto=?, precio=? where id=? ";

            PreparedStatement pst = con.prepareStatement(cadSQL);

            int numFila = tbl_tablaClientes.getSelectedRow();
            String idSelecionado = (String) tbl_tablaClientes.getValueAt(numFila, 0);
            pst.setString(1, txt_name.getText());
            pst.setString(2, txt_Apellidos.getText());
            pst.setString(3, txt_Domicilio.getText());
            pst.setString(4, txt_Telefono.getText());
            pst.setString(5, txt_email.getText());
            pst.setString(6, txt_producto.getText());
            pst.setString(7, txt_precio.getText());
            pst.setString(8, idSelecionado);

            pst.execute();

            resetCampos();

            System.out.println("Entrada de cliente Actualizada");

        } catch (Exception e) {
            System.out.println("Algo ha ido mal con la funcion actualizarCliente()" + e);
        }
    }

    public void borrarCliente() {

        int numFila = tbl_tablaClientes.getSelectedRow();

        try {
            String cadSQL = "delete from clientes where id =" + tbl_tablaClientes.getValueAt(numFila, 0);
            java.sql.Statement st = con.createStatement();

            int confirmado = JOptionPane.showConfirmDialog(null, "Estas seguro de que quieres borrar al cliente " + tbl_tablaClientes.getValueAt(numFila, 1) +" ?");

            if (confirmado == 0) {
                int result = st.executeUpdate(cadSQL);
                if (result >= 0) {
                    System.out.println("Cliente Eliminado");
                } else {
                    System.out.println("El cliente no ha sido eliminado correctamente");
                }
            } else {
                System.out.println("El acto de borrar no ha sido ejecutado");
            }

        } catch (Exception e) {

            System.out.println("No se ha podido borrar el cliente");
        }
    }

    public void resetCampos() {

        txt_id.setText("");
        txt_name.setText("");
        txt_Apellidos.setText("");
        txt_Domicilio.setText("");
        txt_Telefono.setText("");
        txt_email.setText("");
        txt_producto.setText("");
        txt_precio.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_tablaClientes = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txt_name = new javax.swing.JTextField();
        txt_Domicilio = new javax.swing.JTextField();
        txt_Telefono = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_Apellidos = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_producto = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_precio = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        btn_clienteNuevo = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbl_tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Apellidos", "Domicilio", "Telefono", "Email", "Producto", "Precio"
            }
        ));
        tbl_tablaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_tablaClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_tablaClientes);

        jButton1.setText("Borrar Cliente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Editar Cliente");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txt_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nameActionPerformed(evt);
            }
        });

        jLabel2.setText("Nombre :");

        jLabel3.setText("Apellidos :");

        jLabel4.setText("Telefono :");

        jLabel5.setText("Domicilio");

        jLabel6.setText("E-mail");

        txt_producto.setColumns(20);
        txt_producto.setRows(5);
        jScrollPane2.setViewportView(txt_producto);

        jLabel9.setText("Productos");

        jLabel10.setText("Precio");

        txt_precio.setText(" ");
        txt_precio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_precioActionPerformed(evt);
            }
        });

        jButton4.setText("Mostrar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btn_clienteNuevo.setText("Cliente Nuevo");
        btn_clienteNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clienteNuevoActionPerformed(evt);
            }
        });

        jLabel7.setText("Id :");

        txt_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Domicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btn_clienteNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(139, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Domicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jLabel9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_clienteNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nameActionPerformed

    private void tbl_tablaClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_tablaClientesMouseClicked
        // TODO add your handling code here:
        int numFila = tbl_tablaClientes.rowAtPoint(evt.getPoint());

        try {
            txt_id.setText(tbl_tablaClientes.getValueAt(numFila, 0).toString());
            txt_name.setText(tbl_tablaClientes.getValueAt(numFila, 1).toString());
            txt_Apellidos.setText(tbl_tablaClientes.getValueAt(numFila, 2).toString());
            txt_Domicilio.setText(tbl_tablaClientes.getValueAt(numFila, 3).toString());
            txt_Telefono.setText(tbl_tablaClientes.getValueAt(numFila, 4).toString());
            txt_email.setText(tbl_tablaClientes.getValueAt(numFila, 5).toString());
            txt_producto.setText(tbl_tablaClientes.getValueAt(numFila, 6).toString());
            txt_precio.setText(tbl_tablaClientes.getValueAt(numFila, 7).toString());
        } catch (Exception e) {
            System.out.println("Esto va mal " + e);
        }

    }//GEN-LAST:event_tbl_tablaClientesMouseClicked

    private void txt_precioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_precioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_precioActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            // TODO add your handling code here:
            escribirEnTXT();
        } catch (IOException ex) {
            System.out.println("La funcion no se ha ejecutado correctamente");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btn_clienteNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clienteNuevoActionPerformed
        // TODO add your handling code here:
        registrarCliente();
        mostrarTablaClientes();
    }//GEN-LAST:event_btn_clienteNuevoActionPerformed

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        actualizarCliente();
        mostrarTablaClientes();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        borrarCliente();
        mostrarTablaClientes();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(verClientes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(verClientes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(verClientes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(verClientes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new verClientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clienteNuevo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_tablaClientes;
    private javax.swing.JTextField txt_Apellidos;
    private javax.swing.JTextField txt_Domicilio;
    private javax.swing.JTextField txt_Telefono;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_precio;
    private javax.swing.JTextArea txt_producto;
    // End of variables declaration//GEN-END:variables
}
