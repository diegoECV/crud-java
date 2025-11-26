package vallegrande.edu.pe.service;

import vallegrande.edu.pe.model.Formulario;
import vallegrande.edu.pe.model.FormularioDAO;
import java.util.List;

public class FormularioService {
	private FormularioDAO dao;

	public FormularioService() {
		dao = new FormularioDAO();
	}

	public boolean agregar(Formulario f) {
		return dao.agregar(f);
	}

	public List<Formulario> listar() {
		return dao.listar();
	}

	public boolean actualizar(Formulario f) {
		return dao.actualizar(f);
	}

	public boolean eliminar(int id) {
		return dao.eliminar(id);
	}

	public List<Formulario> buscar(String texto) {
		return dao.buscar(texto);
	}
}
