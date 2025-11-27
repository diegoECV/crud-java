package vallegrande.edu.pe.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import vallegrande.edu.pe.controller.FormularioController;
import vallegrande.edu.pe.model.Formulario;

public class FormularioView extends JPanel {
    private FormularioController controller;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private TablaView tablaView;
    private JTextField txtDniRuc;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JTextField txtDireccion;
    private JComboBox<String> cmbEstado;
    private JButton btnAgregar;
    private JButton btnVerTabla;

    public FormularioView(CardLayout cardLayout, JPanel mainPanel, FormularioController controller, TablaView tablaView) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.controller = controller;
        this.tablaView = tablaView;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("CRUD de Clientes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 20, 10);
        add(lblTitulo, gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

        // DNI/RUC
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("DNI/RUC"), gbc);
        gbc.gridx = 1;
        txtDniRuc = new JTextField(20);
        txtDniRuc.setFont(new Font("Arial", Font.PLAIN, 12));
        add(txtDniRuc, gbc);

        // Nombre y Apellidos
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Nombre y Apellidos"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 12));
        add(txtNombre, gbc);

        // Teléfono
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Teléfono"), gbc);
        gbc.gridx = 1;
        txtTelefono = new JTextField(20);
        txtTelefono.setFont(new Font("Arial", Font.PLAIN, 12));
        add(txtTelefono, gbc);

        // Correo
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Correo"), gbc);
        gbc.gridx = 1;
        txtCorreo = new JTextField(20);
        txtCorreo.setFont(new Font("Arial", Font.PLAIN, 12));
        add(txtCorreo, gbc);

        // Dirección
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Dirección"), gbc);
        gbc.gridx = 1;
        txtDireccion = new JTextField(20);
        txtDireccion.setFont(new Font("Arial", Font.PLAIN, 12));
        add(txtDireccion, gbc);

        // Estado
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Estado"), gbc);
        gbc.gridx = 1;
        cmbEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});
        cmbEstado.setFont(new Font("Arial", Font.PLAIN, 12));
        add(cmbEstado, gbc);

        // Botón Agregar Cliente
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 5, 10);
        btnAgregar = new JButton("Agregar Cliente");
        btnAgregar.setBackground(new Color(0, 102, 204));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAgregar.setPreferredSize(new Dimension(300, 40));
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregar.addActionListener(e -> agregarCliente());
        add(btnAgregar, gbc);

        // Botón Ver Tabla de Clientes
        gbc.gridy = 8;
        gbc.insets = new Insets(5, 10, 20, 10);
        btnVerTabla = new JButton("Ver Tabla de Clientes");
        btnVerTabla.setBackground(new Color(0, 153, 76));
        btnVerTabla.setForeground(Color.WHITE);
        btnVerTabla.setFont(new Font("Arial", Font.BOLD, 14));
        btnVerTabla.setPreferredSize(new Dimension(300, 40));
        btnVerTabla.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVerTabla.addActionListener(e -> cardLayout.show(mainPanel, "tabla"));
        add(btnVerTabla, gbc);

        // Panel relleno para empujar todo hacia arriba
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(new JPanel(), gbc);
    }

    private void agregarCliente() {
        // Validar que los campos obligatorios no estén vacíos
        if (txtDniRuc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El DNI/RUC es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear objeto Formulario con los datos del formulario
        Formulario formulario = new Formulario(
                0,
                txtDniRuc.getText().trim(),
                txtNombre.getText().trim(),
                txtTelefono.getText().trim(),
                txtCorreo.getText().trim(),
                txtDireccion.getText().trim(),
                (String) cmbEstado.getSelectedItem()
        );

        // Llamar al controlador para agregar a la base de datos
        if (controller.agregar(formulario)) {
            JOptionPane.showMessageDialog(this, "Cliente agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar el cliente. Verifique que el DNI/RUC no exista.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtDniRuc.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        cmbEstado.setSelectedIndex(0);
    }
}
