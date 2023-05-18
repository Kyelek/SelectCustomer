/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package selectcustomer;

import conexion.conexion;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Desktop;
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
    String id;

    public verClientes() {
        initComponents(); // Iniciamos los componentes de la tabla

        mostrarTablaClientes();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void mostrarTablaClientes() { // funcion para mostrar la tabla con todos los campos

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

    public void registrarCliente() { //Funcion para registrar un nuevo cliente

        id = txt_id.getText();
        nombre = txt_name.getText();
        apellidos = txt_Apellidos.getText();
        domicilio = txt_Domicilio.getText();
        telefono = txt_Telefono.getText();
        email = txt_email.getText();
        producto = txt_producto.getText();

        String SQL = "INSERT INTO clientes (nombre,apellidos,domicilio,telefono,email,producto) VALUES (?,?,?,?,?,?)"; // No se inserta el campo precio por que inserta en la funcion insertarPrecio() 

        try {
            PreparedStatement pst = con.prepareStatement(SQL);

            pst.setString(1, txt_name.getText());
            pst.setString(2, txt_Apellidos.getText());
            pst.setString(3, txt_Domicilio.getText());
            pst.setInt(4, Integer.parseInt(txt_Telefono.getText()));
            pst.setString(5, txt_email.getText());
            pst.setString(6, txt_producto.getText());
            //pst.setString(7, txt_precio.getText());
            //pst.setInt(7,Integer.parseInt(txt_precio.getText()));
            pst.execute();
            System.out.println("Cliente guardado");

        } catch (Exception e) {
            System.out.println("No se ha insertado el registro" + e);
        }
        insertarPrecio(); // insertamos el precio
        mostrarTablaClientes();
    }

    public void escribirEnTXT() throws IOException { //Funcion para escribir y el HTML y mostrarlo

        id = txt_id.getText();
        nombre = txt_name.getText();
        apellidos = txt_Apellidos.getText();
        domicilio = txt_Domicilio.getText();
        telefono = txt_Telefono.getText();
        email = txt_email.getText();
        producto = txt_producto.getText();

        insertarPrecio(); // la necesitamos para recoger el precio

        //Salto de linea en productos con expresiones regulares
        String productoSeparado = producto.replaceAll("(\\d+)", "$1 €<br>");

        //Crear/Abrir archivo HTML
        String rutaProyecto = "D:\\Kyelek\\Documentos\\NetBeansProjects\\SelectCustomer"; // Establecer la ruta manualmente
        String rutaCarpetaArchivos = rutaProyecto + File.separator + "Clientes";
        File carpetaArchivos = new File(rutaCarpetaArchivos);
        //System.out.println(rutaCarpetaArchivos);

        if (!carpetaArchivos.exists()) {
            carpetaArchivos.mkdirs();
        }
        //Nombre del archivo con un numero que va incrementando para evitar confusion y archivos con el mismo nombre
        int contador = 1;
        File archivo = new File(carpetaArchivos, nombre + ".html");
        while (archivo.exists()) {
            contador++;
            archivo = new File(carpetaArchivos, nombre + contador + ".html");
        }

        try (FileWriter writer = new FileWriter(archivo)) {
            // HTML Y CSS de la plantilla
            writer.write("<body style=\"background-color: rgb(255, 204, 102);\">\n");
            //CSS
            writer.write("<style>\n");
            writer.write("    .estilo {\n");
            writer.write("        font-family: Arial, sans-serif;\n");
            writer.write("        border-collapse: collapse;\n");
            writer.write("        padding: 2% 5%;\n");
            writer.write("    }\n");
            writer.write("    .estilo-header {\n");
            writer.write("        background-color: #f5f5f5;\n");
            writer.write("        text-align: center;\n");
            writer.write("        padding: 10px;\n");
            writer.write("    }\n");
            writer.write("    .estilo-header h1 {\n");
            writer.write("        margin: 0;\n");
            writer.write("        font-size: 24px;\n");
            writer.write("        font-weight: bold;\n");
            writer.write("    }\n");
            writer.write("    .estilo-logo {\n");
            writer.write("        text-align: center;\n");
            writer.write("        margin-bottom: 20px;\n");
            writer.write("    }\n");
            writer.write("    .estilo-logo img {\n");
            writer.write("        width: 200px;\n");
            writer.write("    }\n");
            writer.write("    .estilo-details {\n");
            writer.write("        display: flex;\n");
            writer.write("        justify-content: space-between;\n");
            writer.write("        margin-top: 20px;\n");
            writer.write("    }\n");
            writer.write("    .estilo-details-left {\n");
            writer.write("        flex: 1;\n");
            writer.write("    }\n");
            writer.write("    .estilo-details-right {\n");
            writer.write("        flex: 1;\n");
            writer.write("        margin-left: 65%;\n");
            writer.write("    }\n");
            writer.write("    .estilo-details p {\n");
            writer.write("        margin: 5px 0;\n");
            writer.write("    }\n");
            writer.write("    .estilo-logo {\n");
            writer.write("        width: 100%;\n");
            writer.write("        text-align: center;\n");
            writer.write("        margin-bottom: 20px;\n");
            writer.write("    }\n");
            writer.write("    .estilo-logo img {\n");
            writer.write("        width: 200px;\n");
            writer.write("    }\n");
            writer.write("    .estilo-title {\n");
            writer.write("        text-align: center;\n");
            writer.write("        margin-bottom: 20px;\n");
            writer.write("        font-size: 20px;\n");
            writer.write("        font-weight: bold;\n");
            writer.write("        text-transform: uppercase;\n");
            writer.write("    }\n");
            writer.write("    .estilo-line {\n");
            writer.write("        border-bottom: 1px solid #ccc;\n");
            writer.write("        margin-bottom: 20px;\n");
            writer.write("    }\n");
            writer.write("    .estilo-items {\n");
            writer.write("        margin-top: 20px;\n");
            writer.write("    }\n");
            writer.write("    .estilo-items h3 {\n");
            writer.write("        font-weight: bold;\n");
            writer.write("    }\n");
            writer.write("</style>\n");
            //HTML
            writer.write("<div class=\"estilo\">\n");
            writer.write("    <div class=\"estilo-logo\">\n");
            writer.write("        <img src=\"D:\\Kyelek\\Documentos\\NetBeansProjects\\SelectCustomer\\src\\img\\logo.PNG\" alt=\"Logo de la empresa\">\n");
            writer.write("    </div>\n");
            writer.write("    <div class=\"estilo-details\">\n");
            writer.write("        <div class=\"estilo-details-left\">\n");
            writer.write("            <p class=\"estilo-bold\"><STRONG>Nombre de la empresa:</STRONG></p>\n");
            writer.write("            <p>CustomerSC</p>\n");
            writer.write("            <p class=\"estilo-bold\"><STRONG>Dirección de la empresa:</STRONG></p>\n");
            writer.write("            <p>calle falsa 123 SN/24</p>\n");
            writer.write("            <p class=\"estilo-bold\"><STRONG>Teléfono:</STRONG></p>\n");
            writer.write("            <p>654815359</p>\n");
            writer.write("            <p class=\"estilo-bold\"><STRONG>Correo de contacto:</STRONG></p>\n");
            writer.write("            <p>fco.eugenio@hotmail.com</p>\n");
            writer.write("        </div>\n");
            writer.write("        <div class=\"estilo-details-right\">\n");
            writer.write("            <p class=\"estilo-bold\"><STRONG>Cliente:</STRONG></p>\n");
            writer.write("            <p>" + nombre + " " + apellidos + "</p>\n");
            writer.write("            <p class=\"estilo-bold\"><STRONG>Domicilio:</STRONG></p>\n");
            writer.write("            <p>" + domicilio + "</p>\n");
            writer.write("            <p class=\"estilo-bold\"><STRONG>Teléfono:</STRONG></p>\n");
            writer.write("            <p>" + telefono + "</p>\n");
            writer.write("            <p class=\"estilo-bold\"><STRONG>Email:</STRONG></p>\n");
            writer.write("            <p>" + email + "</p>\n");
            writer.write("        </div>\n");
            writer.write("    </div>\n");
            writer.write("    <div class=\"estilo-line\"></div>\n");
            writer.write("<table class=\"estilo-items\" style=\"width: 100%; border: 1px solid #000;\">\n");
            writer.write("    <tr>\n");
            writer.write("        <th>Productos:</th>\n");
            writer.write("    </tr>\n");
            writer.write("    <tr>\n");
            writer.write("        <td>" + productoSeparado + "</td>\n");
            writer.write("    </tr>\n");
            writer.write("</table>\n");
            writer.write("<div class=\"estilo-line\"></div>\n");
            writer.write("<table class=\"estilo-items\" style=\"width: 100%; border: 1px solid #000;\">\n");
            writer.write("    <tr>\n");
            writer.write("        <th>Total:</th>\n");
            writer.write("    </tr>\n");
            writer.write("    <tr>\n");
            writer.write("        <td>Total: " + precio + " €</td>\n");
            writer.write("    </tr>\n");
            writer.write("</table>\n");
            //FIN PLANTILLA 
            
            JOptionPane.showMessageDialog(null, "Archivo creado correctamente en la carpeta \"archivos\".");
            mostrarTablaClientes();
            Desktop.getDesktop().open(archivo);//abrimos el archivo que hemos seleccionado
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el archivo de texto: " + e.getMessage());
        }
    }

    public void actualizarCliente() { //Funcion para actualizar el cliente seleccionado

        id = txt_id.getText();
        nombre = txt_name.getText();
        apellidos = txt_Apellidos.getText();
        domicilio = txt_Domicilio.getText();
        telefono = txt_Telefono.getText();
        email = txt_email.getText();
        producto = txt_producto.getText();

        insertarPrecio();// la utilizamos para actualizar el precio

        try {
            String cadSQL = "update clientes set nombre =?,apellidos =?,domicilio =?, telefono=?, email=?, producto=? where id=? ";

            PreparedStatement pst = con.prepareStatement(cadSQL);

            int numFila = tbl_tablaClientes.getSelectedRow();
            String idSelecionado = (String) tbl_tablaClientes.getValueAt(numFila, 0);
            pst.setString(1, nombre);
            pst.setString(2, apellidos);
            pst.setString(3, domicilio);
            pst.setString(4, telefono);
            pst.setString(5, email);
            pst.setString(6, producto);
            //pst.setString(7, txt_precio.getText());
            pst.setString(7, idSelecionado);

            pst.execute();
            resetCampos();

            System.out.println("Entrada de cliente Actualizada");

        } catch (Exception e) {
            System.out.println("Algo ha ido mal con la funcion actualizarCliente()" + e);
        }
    }

    public void borrarCliente() { // funcion para borrar el cliente mediante id

        int numFila = tbl_tablaClientes.getSelectedRow();

        try {
            String cadSQL = "delete from clientes where id =" + tbl_tablaClientes.getValueAt(numFila, 0);
            java.sql.Statement st = con.createStatement();

            int confirmado = JOptionPane.showConfirmDialog(null, "Estas seguro de que quieres borrar al cliente " + tbl_tablaClientes.getValueAt(numFila, 1) + " ?");

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

    public void insertarPrecio() { // funcion que inserta el precio Total que lo recoge de los productos mediante expresiones regulares
        // Buscar todas las cadenas de números en producto y sumarlos

        int total = 0;
        Pattern pattern = Pattern.compile("\\d+"); //objeto que se utiliza para hacer el patron en la cadena de la expresion regular, en este caso los numeros
        Matcher matcher = pattern.matcher(producto); // Le decimos donde tiene que utilizar la expresion regular
        while (matcher.find()) { // Con el for vamos a separar las condiciones que le hemos dado y a sumar las cadenas restantes obteniendo un precio total
            total += Integer.parseInt(matcher.group());
        }
        // Convertir el total a una cadena y guardarlo en precio
        precio = Integer.toString(total);
        telefono = txt_Telefono.getText();
        //id = txt_id.getText();
        String SQL = "UPDATE clientes SET precio = ? WHERE telefono = ?"; // vamos a meter el precio por tlf,que es una clave unica por que cuando la utilizamos no siempre tenemos 
                                                                        //  acceso a preseleccionar un id, como por ejemplo cuando creamos un nuevo cliente
        try {

            PreparedStatement pst = con.prepareStatement(SQL);

            pst.setInt(1, Integer.parseInt(precio));
            pst.setString(2, telefono);
            pst.execute();
            //pst.setString(1, precio);

        } catch (Exception e) {
            System.out.println("Error al insertar el precio en la bd");
        }
    }

    public void resetCampos() { // hacemos reset de los campos a vaciarlos

        txt_id.setText("");
        txt_name.setText("");
        txt_Apellidos.setText("");
        txt_Domicilio.setText("");
        txt_Telefono.setText("");
        txt_email.setText("");
        txt_producto.setText("");
        //txt_precio.setText("");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_tablaClientes = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_producto = new javax.swing.JTextArea();
        txt_name = new javax.swing.JTextField();
        txt_Domicilio = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_Telefono = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btn_clienteNuevo = new javax.swing.JButton();
        txt_email = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        txt_Apellidos = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 102));

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

        jLabel4.setText("Telefono :");

        jLabel5.setText("Domicilio");

        jButton1.setBackground(new java.awt.Color(204, 204, 255));
        jButton1.setText("Borrar Cliente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setText("E-mail");

        jButton2.setBackground(new java.awt.Color(204, 204, 255));
        jButton2.setText("Editar Cliente");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txt_producto.setColumns(20);
        txt_producto.setRows(5);
        jScrollPane2.setViewportView(txt_producto);

        txt_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nameActionPerformed(evt);
            }
        });

        jLabel9.setText("Productos");

        jButton4.setBackground(new java.awt.Color(204, 204, 255));
        jButton4.setText("Mostrar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel2.setText("Nombre :");

        btn_clienteNuevo.setBackground(new java.awt.Color(204, 204, 255));
        btn_clienteNuevo.setText("Cliente Nuevo");
        btn_clienteNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clienteNuevoActionPerformed(evt);
            }
        });

        jLabel7.setText("Id :");

        jLabel3.setText("Apellidos :");

        txt_id.setEditable(false);
        txt_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Domicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_clienteNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 912, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Domicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jLabel9)))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_clienteNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
            //txt_precio.setText(tbl_tablaClientes.getValueAt(numFila, 7).toString());
        } catch (Exception e) {
            System.out.println("Esto va mal " + e);
        }

    }//GEN-LAST:event_tbl_tablaClientesMouseClicked

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
        //separarPrecio();
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_tablaClientes;
    private javax.swing.JTextField txt_Apellidos;
    private javax.swing.JTextField txt_Domicilio;
    private javax.swing.JTextField txt_Telefono;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextArea txt_producto;
    // End of variables declaration//GEN-END:variables
}
