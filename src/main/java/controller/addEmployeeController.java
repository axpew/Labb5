package controller;

import domain.CircularLinkedList;
import domain.Employee;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class addEmployeeController {
    @javafx.fxml.FXML
    private Text txtMessage;
    @javafx.fxml.FXML
    private Pane mainPain;
    @javafx.fxml.FXML
    private TextField birthdayTextfield;
    @javafx.fxml.FXML
    private TextField idTextfield;
    @javafx.fxml.FXML
    private TextField lastNameTextfield;
    @javafx.fxml.FXML
    private Pane buttonPane;
    @javafx.fxml.FXML
    private TextField firstNameTextfield;
    @javafx.fxml.FXML
    private TextField titleTextfield;
    @javafx.fxml.FXML
    private AnchorPane AP;

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {



        Date bd = util.Utility.parseDate(birthdayTextfield.getText());
        CircularLinkedList list = util.Utility.getEmployeeList(); //Se toma la lista ya existente y se le agrega el nuevo empleado

        if (!isValidDateFormat(birthdayTextfield.getText())) { //Valida que se ingrese la fecha se ingrese en el formato correcto
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Formato incorrecto");
            alert.setHeaderText(null);
            alert.setContentText("La fecha debe tener el formato dd/MM/yyyy");
            alert.showAndWait();
            birthdayTextfield.setText("");

        } else {

            try {
                String idText = idTextfield.getText();

                if (idText.length() != 9) {
                    util.FXUtility.showErrorAlert("ID inválido", "El ID debe tener exactamente 9 dígitos.");
                    return;
                }

                int id = Integer.parseInt(idTextfield.getText());

                Employee newEmployee = new Employee(id, lastNameTextfield.getText(), firstNameTextfield.getText(), titleTextfield.getText(), bd);
                list.add(newEmployee);

                util.FXUtility.showMessage("Success", "Empleado agregado exitosamente");

                // Limpiar los campos solo si todo fue exitoso
                idTextfield.setText("");
                lastNameTextfield.setText("");
                firstNameTextfield.setText("");
                titleTextfield.setText("");
                birthdayTextfield.setText("");

            } catch (NumberFormatException e) {
                util.FXUtility.showErrorAlert("FORMATO NO VÁLIDO", "El ID debe ser un número");
            }


        }
    }

    public static boolean isValidDateFormat(String text) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(text);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @javafx.fxml.FXML
    public void exitView(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.laboratory5.HelloApplication", "employees-view.fxml", mainPain);
    }

    @javafx.fxml.FXML
    public void cleanOnAction(ActionEvent actionEvent) {

        idTextfield.setText("");
        lastNameTextfield.setText("");
        firstNameTextfield.setText("");
        titleTextfield.setText("");
        birthdayTextfield.setText("");

    }
}
