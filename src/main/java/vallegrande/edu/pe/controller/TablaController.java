package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.Tabla;
import vallegrande.edu.pe.model.TablaDAO;
import java.util.List;

public class TablaController {
	private TablaDAO dao;

	public TablaController() {
		dao = new TablaDAO();
	}

	public boolean agregar(Tabla t) {
		return dao.agregar(t);
	}

	public List<Tabla> listar() {
		return dao.listar();
	}

	public boolean actualizar(Tabla t) {
		return dao.actualizar(t);
	}

	public boolean eliminar(int id) {
		return dao.eliminar(id);
	}

	public List<Tabla> buscar(String texto) {
		return dao.buscar(texto);
	}
}
