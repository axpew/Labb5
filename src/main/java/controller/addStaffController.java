package controller;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import util.FXUtility;

import java.time.LocalDateTime;

public class addStaffController {
    @FXML
    private Text txtMessage;
    @FXML
    private ComboBox<String> cbSupervisor;
    @FXML
    private Pane mainPain;
    @FXML
    private DatePicker DPdate;
    @FXML
    private TextField idTextfield;
    @FXML
    private Pane buttonPane;
    @FXML
    private ComboBox<String> cbAssignType;
    @FXML
    private ComboBox<String> cbEmployee;
    @FXML
    private ComboBox<String> cbJob;
    @FXML
    private AnchorPane AP;

    private static int autoStaffId = 1; // Para auto-generar IDs

    @FXML
    public void initialize() {
        // Configurar el ID (autogenerado)
        idTextfield.setEditable(false);
        idTextfield.setText(String.valueOf(autoStaffId));

        // Configurar la fecha actual
        DPdate.setValue(java.time.LocalDate.now());

        // Cargar ComboBox de empleados
        loadEmployees();

        // Cargar ComboBox de puestos
        loadJobs();

        // Cargar ComboBox de supervisores
        loadSupervisors();

        // Cargar ComboBox de tipos de asignación
        loadAssignationTypes();
    }

    private void loadEmployees() {
        ObservableList<String> employeeOptions = FXCollections.observableArrayList();
        CircularLinkedList employeeList = util.Utility.getEmployeeList();

        if (employeeList != null && !employeeList.isEmpty()) {
            try {
                Node aux = employeeList.first;
                do {
                    Employee employee = (Employee) aux.data;
                    employeeOptions.add(employee.getId() + " - " + employee.getLastname() + ", " + employee.getFirstname() +
                            " (" + employee.getTitle() + ", " + employee.getAge() + ")");
                    aux = aux.next;
                } while (aux != employeeList.first);

                cbEmployee.setItems(employeeOptions);
                if (!employeeOptions.isEmpty()) {
                    cbEmployee.setValue(employeeOptions.get(0));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadJobs() {
        ObservableList<String> jobOptions = FXCollections.observableArrayList();
        CircularDoublyLinkedList jobList = util.Utility.getJobPositionList();

        if (jobList != null && !jobList.isEmpty()) {
            try {
                for (int i = 1; i <= jobList.size(); i++) {
                    JobPosition job = (JobPosition) jobList.getNode(i).data;
                    jobOptions.add(job.getId() + " - " + job.getDescription() + " ($" + job.getHourlyWage() + "/hr)");
                }

                cbJob.setItems(jobOptions);
                if (!jobOptions.isEmpty()) {
                    cbJob.setValue(jobOptions.get(0));
                }
            } catch (ListException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadSupervisors() {
        ObservableList<String> supervisorOptions = FXCollections.observableArrayList(
                "Chaves Gabriela",
                "Castro Carlos",
                "Pérez Juan",
                "Ruiz Verónica",
                "Vargas María"
        );
        cbSupervisor.setItems(supervisorOptions);
        if (!supervisorOptions.isEmpty()) {
            cbSupervisor.setValue(supervisorOptions.get(0));
        }
    }

    private void loadAssignationTypes() {
        ObservableList<String> assignationTypeOptions = FXCollections.observableArrayList(
                "Promoción",
                "Transferencia",
                "Nuevo ingreso",
                "Temporal",
                "Otro"
        );
        cbAssignType.setItems(assignationTypeOptions);
        if (!assignationTypeOptions.isEmpty()) {
            cbAssignType.setValue(assignationTypeOptions.get(0));
        }
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) {
        if (validateFields()) {
            try {
                int id = Integer.parseInt(idTextfield.getText());
                LocalDateTime registerDate = DPdate.getValue().atStartOfDay();

                // Extraer la información del empleado si está disponible
                String employeeStr = cbEmployee.getValue();
                int employeeId = 0;
                String employeeName = "";

                if (employeeStr != null && !employeeStr.isEmpty()) {
                    employeeId = Integer.parseInt(employeeStr.substring(0, employeeStr.indexOf(" ")));
                    employeeName = employeeStr.substring(employeeStr.indexOf("-") + 2, employeeStr.indexOf("(") - 1);
                }

                // Extraer la información del puesto si está disponible
                String jobStr = cbJob.getValue();
                String jobPosition = "";

                if (jobStr != null && !jobStr.isEmpty()) {
                    jobPosition = jobStr.substring(jobStr.indexOf("-") + 2, jobStr.indexOf("($") - 1);
                }

                String supervisor = cbSupervisor.getValue();
                String assignationType = cbAssignType.getValue();

                // Crear la asignación
                Staffing staffing = new Staffing(
                        id,
                        registerDate,
                        employeeId,
                        employeeName,
                        jobPosition,
                        supervisor,
                        assignationType
                );

                // Obtener la lista global y agregar la nueva asignación
                CircularDoublyLinkedList staffList = util.Utility.getStaffingList();
                if (staffList == null) {
                    staffList = new CircularDoublyLinkedList();
                }

                staffList.add(staffing);
                util.Utility.setStaffingList(staffList);

                FXUtility.showMessage("Éxito", "Asignación agregada correctamente");

                // Incrementar el ID autogenerado
                autoStaffId++;
                idTextfield.setText(String.valueOf(autoStaffId));

                // Reiniciar los campos
                DPdate.setValue(java.time.LocalDate.now());

            } catch (Exception e) {
                FXUtility.showErrorAlert("Error", "Error al agregar asignación: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void exitView(ActionEvent actionEvent) {
        FXUtility.loadPage("ucr.laboratory5.HelloApplication", "staff-view.fxml", mainPain);
    }

    @FXML
    public void cleanOnAction(ActionEvent actionEvent) {
        DPdate.setValue(java.time.LocalDate.now());

        // Seleccionar el primer elemento en cada ComboBox
        if (!cbEmployee.getItems().isEmpty()) {
            cbEmployee.setValue(cbEmployee.getItems().get(0));
        }

        if (!cbJob.getItems().isEmpty()) {
            cbJob.setValue(cbJob.getItems().get(0));
        }

        if (!cbSupervisor.getItems().isEmpty()) {
            cbSupervisor.setValue(cbSupervisor.getItems().get(0));
        }

        if (!cbAssignType.getItems().isEmpty()) {
            cbAssignType.setValue(cbAssignType.getItems().get(0));
        }
    }

    private boolean validateFields() {
        if (cbEmployee.getValue() == null || cbEmployee.getValue().isEmpty()) {
            FXUtility.showErrorAlert("Error de validación", "Debe seleccionar un empleado");
            return false;
        }

        if (cbJob.getValue() == null || cbJob.getValue().isEmpty()) {
            FXUtility.showErrorAlert("Error de validación", "Debe seleccionar un puesto");
            return false;
        }

        if (cbSupervisor.getValue() == null || cbSupervisor.getValue().isEmpty()) {
            FXUtility.showErrorAlert("Error de validación", "Debe seleccionar un supervisor");
            return false;
        }

        if (cbAssignType.getValue() == null || cbAssignType.getValue().isEmpty()) {
            FXUtility.showErrorAlert("Error de validación", "Debe seleccionar un tipo de asignación");
            return false;
        }

        if (DPdate.getValue() == null) {
            FXUtility.showErrorAlert("Error de validación", "Debe seleccionar una fecha");
            return false;
        }

        return true;
    }
}