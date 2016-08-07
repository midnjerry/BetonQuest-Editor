/**
 * BetonQuest Editor - advanced quest creating tool for BetonQuest
 * Copyright (C) 2016  Jakub "Co0sh" Sapalski
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pl.betoncraft.betonquest.editor.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.betoncraft.betonquest.editor.BetonQuestEditor;

/**
 * Controls the pop-up window for editing instruction strings.
 *
 * @author Jakub Sapalski
 */
public class NameEditController {
	
	private Stage stage;
	private StringProperty name;
	
	@FXML private TextField field;
	
	/**
	 * Sets the data in the window.
	 * 
	 * @param stage the pop-up window
	 * @param name StringProperty to edit
	 */
	private void setData(Stage stage, StringProperty name) {
		this.stage = stage;
		this.name = name;
		field.setText(name.get());
	}
	
	/**
	 * Checks if the name is inserted and changes the StringProperty.
	 */
	@FXML private void ok() {
		String text = field.getText();
		if (text == null || text.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(BetonQuestEditor.getInstance().getLanguage().getString("name-not-null"));
			alert.showAndWait();
			return;
		}
		name.set(text.trim());
		BetonQuestEditor.refresh();
		stage.close();
	}
	
	/**
	 * Closes the window without changing StringProperty object.
	 */
	@FXML private void cancel() {
		stage.close();
	}
	
	/**
	 * Displays a window in which the user can edit the StringProperty object.
	 * 
	 * @param data StringProperty to edit
	 */
	public static void display(StringProperty data) {
		try {
			Stage window = new Stage();
			URL location = BetonQuestEditor.class.getResource("view/window/NameEditWindow.fxml");
			ResourceBundle resources = ResourceBundle.getBundle("pl.betoncraft.betonquest.editor.resource.lang.lang");
			FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
			GridPane root = (GridPane) fxmlLoader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(BetonQuestEditor.class.getResource("resource/style.css").toExternalForm());
			window.setScene(scene);
			window.setTitle(resources.getString("edit-name"));
			window.getIcons().add(new Image(BetonQuestEditor.class.getResourceAsStream("resource/icon.png")));
			window.setHeight(100);
			window.setWidth(500);
			window.setResizable(false);
			NameEditController controller = (NameEditController) fxmlLoader.getController();
			controller.setData(window, data);
			window.showAndWait();
		} catch (IOException e) {
			BetonQuestEditor.showStackTrace(e);
		}
	}

}
