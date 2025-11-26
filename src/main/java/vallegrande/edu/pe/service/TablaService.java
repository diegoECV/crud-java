package vallegrande.edu.pe.service;

import vallegrande.edu.pe.model.Tabla;
import vallegrande.edu.pe.model.TablaDAO;
import java.util.List;

public class TablaService {
	private TablaDAO dao;

	public TablaService() {
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
