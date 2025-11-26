package vallegrande.edu.pe;

import vallegrande.edu.pe.model.FormularioDAO;
import vallegrande.edu.pe.view.FormularioView;
import vallegrande.edu.pe.view.TablaView;
import javax.swing.*;
import java.awt.*;

public class AppLauncher {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("CRUD de Clientes");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(700, 700);
			frame.setLocationRelativeTo(null);

			CardLayout cardLayout = new CardLayout();
			JPanel mainPanel = new JPanel(cardLayout);


			FormularioDAO dao = new FormularioDAO();
			vallegrande.edu.pe.controller.TablaController tablaController = new vallegrande.edu.pe.controller.TablaController();
			TablaView tablaView = new TablaView(cardLayout, mainPanel, tablaController);
			vallegrande.edu.pe.controller.FormularioController formularioController = new vallegrande.edu.pe.controller.FormularioController();
			FormularioView formularioView = new FormularioView(cardLayout, mainPanel, formularioController, tablaView);

			mainPanel.add(formularioView, "formulario");
			mainPanel.add(tablaView, "tabla");

			frame.setContentPane(mainPanel);
			frame.setVisible(true);
			cardLayout.show(mainPanel, "formulario");
		});
	}
}
