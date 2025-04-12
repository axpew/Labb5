package controller;

import domain.JobPosition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import util.FXUtility;

public class addJobPositionController {
    @FXML
    private Text txtMessage;
    @FXML
    private TextField tfJobID;
    @FXML
    private Pane mainPain;
    @FXML
    private TextField tfHourlyWage;
    @FXML
    private Pane buttonPane;
    @FXML
    private TextField tfDescription;
    @FXML
    private AnchorPane AP;

    @FXML
    public void initialize() {
        // Si es un autogenerado, el ID no debería ser editable
        tfJobID.setEditable(false);
        tfJobID.setText(String.valueOf(JobPosition.getAutoID() + 1));
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) {
        if (validateFields()) {
            try {
                // Si es autogenerado, no necesita el ID
                String description = tfDescription.getText();
                double hourlyWage = Double.parseDouble(tfHourlyWage.getText());

                // Crear y agregar el puesto de trabajo
                JobPosition jobPosition = new JobPosition(description, hourlyWage);

                // Agregar el nuevo puesto a la lista global
                util.Utility.getJobPositionList().add(jobPosition);

                FXUtility.showMessage("Éxito", "Puesto de trabajo agregado correctamente");

                // Limpiar campos
                cleanFields();

                // Actualizar el ID para el próximo
                tfJobID.setText(String.valueOf(JobPosition.getAutoID() + 1));

            } catch (NumberFormatException e) {
                FXUtility.showErrorAlert("Error", "El salario por hora debe ser un número válido");
            } catch (Exception e) {
                FXUtility.showErrorAlert("Error", "Error al agregar puesto: " + e.getMessage());
            }
        }
    }

    @FXML
    public void exitView(ActionEvent actionEvent) {
        FXUtility.loadPage("ucr.laboratory5.HelloApplication", "jobPosition-view.fxml", mainPain);
    }

    @FXML
    public void cleanOnAction(ActionEvent actionEvent) {
        cleanFields();
    }

    private void cleanFields() {
        tfDescription.clear();
        tfHourlyWage.clear();
    }

    private boolean validateFields() {
        if (tfDescription.getText().isEmpty()) {
            FXUtility.showErrorAlert("Error de validación", "La descripción no puede estar vacía");
            return false;
        }

        if (tfHourlyWage.getText().isEmpty()) {
            FXUtility.showErrorAlert("Error de validación", "El salario por hora no puede estar vacío");
            return false;
        }

        try {
            double hourlyWage = Double.parseDouble(tfHourlyWage.getText());
            if (hourlyWage <= 0) {
                FXUtility.showErrorAlert("Error de validación", "El salario por hora debe ser mayor que cero");
                return false;
            }
        } catch (NumberFormatException e) {
            FXUtility.showErrorAlert("Error de validación", "El salario por hora debe ser un número válido");
            return false;
        }

        return true;
    }
}