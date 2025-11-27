package vallegrande.edu.pe.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import vallegrande.edu.pe.controller.TablaController;
import vallegrande.edu.pe.model.Tabla;

public class TablaView extends JPanel {
	private TablaController controller;
	private CardLayout cardLayout;
	private JPanel mainPanel;
	private JTable tablaTable;
	private DefaultTableModel tableModel;
	private JTextField searchField;
	private JButton btnBuscar, btnAgregar, btnEditar, btnEliminar, btnActualizar;

	public TablaView(CardLayout cardLayout, JPanel mainPanel, TablaController controller) {
		this.cardLayout = cardLayout;
		this.mainPanel = mainPanel;
		this.controller = controller;
		initComponents();
		loadTablaData("");
	}

	private void initComponents() {
		setLayout(new BorderLayout(10, 10));
		setBackground(new Color(245, 245, 245));

		// Panel superior con título y botones
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(new Color(245, 245, 245));

		// Título
		JLabel lblTitulo = new JLabel("Listado de Clientes");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitulo.setForeground(new Color(0, 102, 204));
		topPanel.add(lblTitulo, BorderLayout.WEST);

		// Panel de botones
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		buttonPanel.setBackground(new Color(245, 245, 245));

		btnAgregar = new JButton("Agregar Cliente");
		btnAgregar.setBackground(new Color(0, 153, 76));
		btnAgregar.setForeground(Color.WHITE);
		btnAgregar.setFont(new Font("Arial", Font.BOLD, 12));
		btnAgregar.addActionListener(e -> agregarTabla());

		btnActualizar = new JButton("Actualizar");
		btnActualizar.setBackground(new Color(255, 193, 7));
		btnActualizar.setForeground(Color.BLACK);
		btnActualizar.setFont(new Font("Arial", Font.BOLD, 12));
		btnActualizar.addActionListener(e -> {
			loadTablaData("");
			JOptionPane.showMessageDialog(this, "Tabla actualizada.");
		});

		btnEditar = new JButton("Editar");
		btnEditar.setBackground(new Color(33, 150, 243));
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("Arial", Font.BOLD, 12));
		btnEditar.addActionListener(e -> editarTabla());

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBackground(new Color(244, 67, 54));
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setFont(new Font("Arial", Font.BOLD, 12));
		btnEliminar.addActionListener(e -> eliminarTabla());

		buttonPanel.add(btnAgregar);
		buttonPanel.add(btnActualizar);
		buttonPanel.add(btnEditar);
		buttonPanel.add(btnEliminar);

		topPanel.add(buttonPanel, BorderLayout.EAST);
		add(topPanel, BorderLayout.NORTH);

		// Tabla con las columnas correctas
		tableModel = new DefaultTableModel(
				new Object[]{"DNI/RUC", "Nombre", "Teléfono", "Correo", "Dirección", "Estado"},
				0
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaTable = new JTable(tableModel);
		tablaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaTable.setFont(new Font("Arial", Font.PLAIN, 11));
		tablaTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		tablaTable.setRowHeight(25);

		JScrollPane scrollPane = new JScrollPane(tablaTable);
		add(scrollPane, BorderLayout.CENTER);
	}

	private void loadTablaData(String filtro) {
		tableModel.setRowCount(0);
		List<Tabla> tablas = filtro.isEmpty() ? controller.listar() : controller.buscar(filtro);
		for (Tabla t : tablas) {
			tableModel.addRow(new Object[]{
					t.getDniRuc(),
					t.getNombre(),
					t.getTelefono(),
					t.getCorreo(),
					t.getDireccion(),
					t.getEstado()
			});
		}
	}

	private void agregarTabla() {
		String dniRuc = JOptionPane.showInputDialog(this, "DNI/RUC:");
		if (dniRuc == null || dniRuc.trim().isEmpty()) return;
		String nombre = JOptionPane.showInputDialog(this, "Nombre:");
		if (nombre == null || nombre.trim().isEmpty()) return;
		String telefono = JOptionPane.showInputDialog(this, "Teléfono:");
		if (telefono == null) telefono = "";
		String correo = JOptionPane.showInputDialog(this, "Correo:");
		if (correo == null) correo = "";
		String direccion = JOptionPane.showInputDialog(this, "Dirección:");
		if (direccion == null) direccion = "";
		String estado = JOptionPane.showInputDialog(this, "Estado (Activo/Inactivo):");
		if (estado == null) estado = "Activo";

		Tabla nueva = new Tabla(0, dniRuc.trim(), nombre.trim(), telefono.trim(), correo.trim(), direccion.trim(), estado.trim());
		if (controller.agregar(nueva)) {
			JOptionPane.showMessageDialog(this, "Cliente agregado correctamente.");
			loadTablaData("");
		} else {
			JOptionPane.showMessageDialog(this, "Error al agregar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void editarTabla() {
		int row = tablaTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione una fila para editar.");
			return;
		}

		String dniRucActual = (String) tableModel.getValueAt(row, 0);
		String nombreActual = (String) tableModel.getValueAt(row, 1);
		String telefonoActual = (String) tableModel.getValueAt(row, 2);
		String correoActual = (String) tableModel.getValueAt(row, 3);
		String direccionActual = (String) tableModel.getValueAt(row, 4);
		String estadoActual = (String) tableModel.getValueAt(row, 5);

		// Crear un panel de diálogo para edición
		JPanel dialogPanel = new JPanel();
		dialogPanel.setLayout(new java.awt.GridLayout(7, 2, 10, 10));

		JLabel lblDni = new JLabel("DNI/RUC:");
		JTextField txtDni = new JTextField(dniRucActual);
		dialogPanel.add(lblDni);
		dialogPanel.add(txtDni);

		JLabel lblNombre = new JLabel("Nombre:");
		JTextField txtNombre = new JTextField(nombreActual);
		dialogPanel.add(lblNombre);
		dialogPanel.add(txtNombre);

		JLabel lblTelefono = new JLabel("Teléfono:");
		JTextField txtTelefono = new JTextField(telefonoActual);
		dialogPanel.add(lblTelefono);
		dialogPanel.add(txtTelefono);

		JLabel lblCorreo = new JLabel("Correo:");
		JTextField txtCorreo = new JTextField(correoActual);
		dialogPanel.add(lblCorreo);
		dialogPanel.add(txtCorreo);

		JLabel lblDireccion = new JLabel("Dirección:");
		JTextField txtDireccion = new JTextField(direccionActual);
		dialogPanel.add(lblDireccion);
		dialogPanel.add(txtDireccion);

		JLabel lblEstado = new JLabel("Estado:");
		JComboBox<String> cmbEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});
		cmbEstado.setSelectedItem(estadoActual);
		dialogPanel.add(lblEstado);
		dialogPanel.add(cmbEstado);

		int result = JOptionPane.showConfirmDialog(this, dialogPanel, "Editar Cliente", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			String dniRuc = txtDni.getText().trim();
			String nombre = txtNombre.getText().trim();
			String telefono = txtTelefono.getText().trim();
			String correo = txtCorreo.getText().trim();
			String direccion = txtDireccion.getText().trim();
			String estado = (String) cmbEstado.getSelectedItem();

			if (dniRuc.isEmpty() || nombre.isEmpty()) {
				JOptionPane.showMessageDialog(this, "DNI/RUC y Nombre son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Buscar el ID del cliente por DNI/RUC actual
			List<Tabla> tablas = controller.listar();
			int id = -1;
			for (Tabla t : tablas) {
				if (t.getDniRuc().equals(dniRucActual)) {
					id = t.getId();
					break;
				}
			}

			if (id != -1) {
				Tabla editada = new Tabla(id, dniRuc, nombre, telefono, correo, direccion, estado);
				if (controller.actualizar(editada)) {
					JOptionPane.showMessageDialog(this, "Cliente editado correctamente.");
					loadTablaData("");
				} else {
					JOptionPane.showMessageDialog(this, "Error al editar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	private void eliminarTabla() {
		int row = tablaTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar.");
			return;
		}

		String dniRucSeleccionado = (String) tableModel.getValueAt(row, 0);

		// Buscar el ID del cliente por DNI/RUC
		List<Tabla> tablas = controller.listar();
		int id = -1;
		for (Tabla t : tablas) {
			if (t.getDniRuc().equals(dniRucSeleccionado)) {
				id = t.getId();
				break;
			}
		}

		if (id != -1) {
			int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				if (controller.eliminar(id)) {
					JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
					loadTablaData("");
				} else {
					JOptionPane.showMessageDialog(this, "Error al eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	public TablaController getController() {
		return controller;
	}
}
