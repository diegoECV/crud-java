
package vallegrande.edu.pe.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import vallegrande.edu.pe.model.Tabla;
import vallegrande.edu.pe.model.TablaDAO;
import java.util.List;
import vallegrande.edu.pe.controller.TablaController;

public class TablaView extends JPanel {
	private TablaController controller;
	private JTable tablaTable;
	private DefaultTableModel tableModel;
	private JTextField searchField;
	private JButton btnBuscar, btnAgregar, btnEditar, btnEliminar, btnActualizar;

	public TablaView(TablaController controller) {
		this.controller = controller;
		initComponents();
		loadTablaData("");
	}

	private void initComponents() {
		setLayout(new BorderLayout(16, 16));

		// Top panel: Search bar and buttons
		JPanel topPanel = new JPanel(new BorderLayout(8, 8));
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		searchField = new JTextField(20);
		btnBuscar = new JButton("Buscar");
		searchPanel.add(new JLabel("Buscar:"));
		searchPanel.add(searchField);
		searchPanel.add(btnBuscar);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnAgregar = new JButton("Agregar");
		btnEditar = new JButton("Editar");
		btnEliminar = new JButton("Eliminar");
		btnActualizar = new JButton("Actualizar");
		buttonPanel.add(btnAgregar);
		buttonPanel.add(btnEditar);
		buttonPanel.add(btnEliminar);
		buttonPanel.add(btnActualizar);

		topPanel.add(searchPanel, BorderLayout.WEST);
		topPanel.add(buttonPanel, BorderLayout.EAST);
		add(topPanel, BorderLayout.NORTH);

		// Table setup
		tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Descripción"}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tablaTable = new JTable(tableModel);
		tablaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(tablaTable);
		add(scrollPane, BorderLayout.CENTER);
		btnEliminar.addActionListener(e -> eliminarTabla());
	}

	private void loadTablaData(String filtro) {
		tableModel.setRowCount(0);
		List<Tabla> tablas = filtro.isEmpty() ? controller.listar() : controller.buscar(filtro);
		for (Tabla t : tablas) {
			tableModel.addRow(new Object[]{
				t.getId(),
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
		String estado = JOptionPane.showInputDialog(this, "Estado:");
		if (estado == null) estado = "";
		Tabla nueva = new Tabla(0, dniRuc.trim(), nombre.trim(), telefono.trim(), correo.trim(), direccion.trim(), estado.trim());
		if (controller.agregar(nueva)) {
			JOptionPane.showMessageDialog(this, "Tabla agregada correctamente.");
			loadTablaData("");
		} else {
			JOptionPane.showMessageDialog(this, "Error al agregar la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void editarTabla() {
		int row = tablaTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione una fila para editar.");
			return;
		}
		int id = (int) tableModel.getValueAt(row, 0);
		String dniRucActual = (String) tableModel.getValueAt(row, 1);
		String nombreActual = (String) tableModel.getValueAt(row, 2);
		String telefonoActual = (String) tableModel.getValueAt(row, 3);
		String correoActual = (String) tableModel.getValueAt(row, 4);
		String direccionActual = (String) tableModel.getValueAt(row, 5);
		String estadoActual = (String) tableModel.getValueAt(row, 6);

		String dniRuc = JOptionPane.showInputDialog(this, "Nuevo DNI/RUC:", dniRucActual);
		if (dniRuc == null || dniRuc.trim().isEmpty()) return;
		String nombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", nombreActual);
		if (nombre == null || nombre.trim().isEmpty()) return;
		String telefono = JOptionPane.showInputDialog(this, "Nuevo teléfono:", telefonoActual);
		if (telefono == null) telefono = "";
		String correo = JOptionPane.showInputDialog(this, "Nuevo correo:", correoActual);
		if (correo == null) correo = "";
		String direccion = JOptionPane.showInputDialog(this, "Nueva dirección:", direccionActual);
		if (direccion == null) direccion = "";
		String estado = JOptionPane.showInputDialog(this, "Nuevo estado:", estadoActual);
		if (estado == null) estado = "";

		Tabla editada = new Tabla(id, dniRuc.trim(), nombre.trim(), telefono.trim(), correo.trim(), direccion.trim(), estado.trim());
		if (controller.actualizar(editada)) {
			JOptionPane.showMessageDialog(this, "Tabla editada correctamente.");
			loadTablaData("");
		} else {
			JOptionPane.showMessageDialog(this, "Error al editar la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void eliminarTabla() {
		int row = tablaTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar.");
			return;
		}
		int id = (int) tableModel.getValueAt(row, 0);
		int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar la tabla?", "Confirmar", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			if (controller.eliminar(id)) {
				JOptionPane.showMessageDialog(this, "Tabla eliminada correctamente.");
				loadTablaData("");
			} else {
				JOptionPane.showMessageDialog(this, "Error al eliminar la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public TablaController getController() {
		return controller;
	}
}
// ...existing code above...

// ...existing code above...

