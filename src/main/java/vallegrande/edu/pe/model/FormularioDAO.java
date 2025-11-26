package vallegrande.edu.pe.model;

import vallegrande.edu.pe.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FormularioDAO {
	public boolean agregar(Formulario f) {
		String sql = "INSERT INTO clientes (dni_ruc, nombre, telefono, correo, direccion, estado) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = DatabaseConnection.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, f.getDniRuc());
			ps.setString(2, f.getNombre());
			ps.setString(3, f.getTelefono());
			ps.setString(4, f.getCorreo());
			ps.setString(5, f.getDireccion());
			ps.setString(6, f.getEstado());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Formulario> listar() {
		List<Formulario> lista = new ArrayList<>();
		String sql = "SELECT * FROM clientes";
		try (Connection conn = DatabaseConnection.getConnection();
			 Statement st = conn.createStatement();
			 ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				Formulario f = new Formulario(
					rs.getInt("id"),
					rs.getString("dni_ruc"),
					rs.getString("nombre"),
					rs.getString("telefono"),
					rs.getString("correo"),
					rs.getString("direccion"),
					rs.getString("estado")
				);
				lista.add(f);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public boolean actualizar(Formulario f) {
		String sql = "UPDATE clientes SET dni_ruc=?, nombre=?, telefono=?, correo=?, direccion=?, estado=? WHERE id=?";
		try (Connection conn = DatabaseConnection.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, f.getDniRuc());
			ps.setString(2, f.getNombre());
			ps.setString(3, f.getTelefono());
			ps.setString(4, f.getCorreo());
			ps.setString(5, f.getDireccion());
			ps.setString(6, f.getEstado());
			ps.setInt(7, f.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean eliminar(int id) {
		String sql = "DELETE FROM clientes WHERE id=?";
		try (Connection conn = DatabaseConnection.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Formulario> buscar(String texto) {
		List<Formulario> lista = new ArrayList<>();
		String sql = "SELECT * FROM clientes WHERE dni_ruc LIKE ? OR nombre LIKE ? OR telefono LIKE ? OR correo LIKE ? OR direccion LIKE ? OR estado LIKE ?";
		try (Connection conn = DatabaseConnection.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			String like = "%" + texto + "%";
			for (int i = 1; i <= 6; i++) {
				ps.setString(i, like);
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Formulario f = new Formulario(
						rs.getInt("id"),
						rs.getString("dni_ruc"),
						rs.getString("nombre"),
						rs.getString("telefono"),
						rs.getString("correo"),
						rs.getString("direccion"),
						rs.getString("estado")
					);
					lista.add(f);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
}
