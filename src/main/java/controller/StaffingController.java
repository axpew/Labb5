package controller;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.time.LocalDateTime;

public class StaffingController {
    @FXML
    private Text txtMessage;
    @FXML
    private TableView employeesTableColumn;
    @FXML
    private Pane mainPain;
    @FXML
    private TableColumn idTableColumn;
    @FXML
    private Pane buttonPane;
    @FXML
    private TextArea taShowMessages;

    // Nuevas columnas según el FXML actualizado
    @FXML
    private TableColumn dateTableColumn;
    @FXML
    private TableColumn EmpIDTableColumn;
    @FXML
    private TableColumn EmpNameTableColumn;
    @FXML
    private TableColumn jobTableColumn;
    @FXML
    private TableColumn supervisorTableColumn;
    @FXML
    private TableColumn firstNameTableColumn1; // Assignation Type

    private CircularDoublyLinkedList staffingList;
    private static int autoStaffId = 1; // Para auto-generar IDs

    @FXML
    public void initialize() {
        taShowMessages.setWrapText(true);

        // Inicializamos la lista de staffing - usamos una lista global si existe
        CircularDoublyLinkedList existingList = util.Utility.getStaffingList();
        if (existingList != null) {
            this.staffingList = existingList;
        } else {
            this.staffingList = new CircularDoublyLinkedList();
            util.Utility.setStaffingList(this.staffingList);
        }

        // Configuramos la tabla
        employeesTableColumn.setItems(convertToObservableList(staffingList));

        // Configuramos las columnas de la tabla usando los nuevos nombres que coinciden con el FXML
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("registerDate"));
        EmpIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        EmpNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        jobTableColumn.setCellValueFactory(new PropertyValueFactory<>("jobPosition"));
        supervisorTableColumn.setCellValueFactory(new PropertyValueFactory<>("supervisor"));
        firstNameTableColumn1.setCellValueFactory(new PropertyValueFactory<>("assignmentType"));

        try {
            taShowMessages.setText("Lista de asignaciones cargada con éxito. Total de asignaciones: " + this.staffingList.size());
        } catch (ListException e) {
            taShowMessages.setText("Error al cargar la lista de asignaciones: " + e.getMessage());
        }
    }

    private ObservableList<Staffing> convertToObservableList(CircularDoublyLinkedList list) {
        ObservableList<Staffing> staffings = FXCollections.observableArrayList();

        if (list.isEmpty()) {
            return staffings; // Lista vacía
        }

        try {
            int size = list.size();
            for (int i = 1; i <= size; i++) {
                Staffing current = (Staffing) list.getNode(i).data;
                staffings.add(current);
            }
        } catch (ListException e) {
            e.printStackTrace();
        }

        return staffings;
    }

    @FXML
    public void sortJobPosOnAction(ActionEvent actionEvent) {
        try {
            sortByJobPosition(staffingList);
            employeesTableColumn.setItems(convertToObservableList(staffingList));
            taShowMessages.setText("Lista ordenada por puesto de trabajo");

            // Actualizamos la lista global
            util.Utility.setStaffingList(staffingList);
        } catch (ListException e) {
            taShowMessages.setText("La lista está vacía");
            util.FXUtility.showErrorAlert("Error", "Error al ordenar la lista: " + e.getMessage());
        }
    }

    @FXML
    public void clearOnAction(ActionEvent actionEvent) {
        try {
            staffingList.clear();
            employeesTableColumn.setItems(convertToObservableList(staffingList));
            taShowMessages.setText("Lista de asignaciones limpiada exitosamente.");

            // Actualizamos la lista global
            util.Utility.setStaffingList(staffingList);
        } catch (Exception e) {
            taShowMessages.setText("Error al limpiar la lista: " + e.getMessage());
            util.FXUtility.showErrorAlert("Error", "Error al limpiar la lista: " + e.getMessage());
        }
    }

    @FXML
    public void sortAssigOnAction(ActionEvent actionEvent) {
        try {
            sortByAssignationType(staffingList);
            employeesTableColumn.setItems(convertToObservableList(staffingList));
            taShowMessages.setText("Lista ordenada por tipo de asignación");

            // Actualizamos la lista global
            util.Utility.setStaffingList(staffingList);
        } catch (ListException e) {
            taShowMessages.setText("La lista está vacía");
            util.FXUtility.showErrorAlert("Error", "Error al ordenar la lista: " + e.getMessage());
        }
    }

    @FXML
    public void sortEmpNameOnAction(ActionEvent actionEvent) {
        try {
            sortByEmployeeName(staffingList);
            employeesTableColumn.setItems(convertToObservableList(staffingList));
            taShowMessages.setText("Lista ordenada por nombre de empleado");

            // Actualizamos la lista global
            util.Utility.setStaffingList(staffingList);
        } catch (ListException e) {
            taShowMessages.setText("La lista está vacía");
            util.FXUtility.showErrorAlert("Error", "Error al ordenar la lista: " + e.getMessage());
        }
    }

    @FXML
    public void removeOnAction(ActionEvent actionEvent) {
        try {
            Staffing selectedStaffing = (Staffing) employeesTableColumn.getSelectionModel().getSelectedItem();
            if (selectedStaffing != null) {
                staffingList.remove(selectedStaffing);
                employeesTableColumn.setItems(convertToObservableList(staffingList));
                taShowMessages.setText("Asignación eliminada exitosamente:\n" + selectedStaffing.toString());

                // Actualizamos la lista global
                util.Utility.setStaffingList(staffingList);
            } else {
                taShowMessages.setText("No se ha seleccionado ninguna asignación para eliminar.");
                util.FXUtility.showErrorAlert("Error", "No se ha seleccionado ninguna asignación");
            }
        } catch (ListException e) {
            taShowMessages.setText("Error al eliminar asignación: " + e.getMessage());
            util.FXUtility.showErrorAlert("Error", "Error al eliminar asignación: " + e.getMessage());
        }
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.laboratory5.HelloApplication", "addStaff-view.fxml", mainPain);
    }

    @FXML
    public void sortEmpIDOnAction(ActionEvent actionEvent) {
        try {
            sortByEmployeeId(staffingList);
            employeesTableColumn.setItems(convertToObservableList(staffingList));
            taShowMessages.setText("Lista ordenada por ID de empleado");

            // Actualizamos la lista global
            util.Utility.setStaffingList(staffingList);
        } catch (ListException e) {
            taShowMessages.setText("La lista está vacía");
            util.FXUtility.showErrorAlert("Error", "Error al ordenar la lista: " + e.getMessage());
        }
    }

    @FXML
    public void sizeOnAction(ActionEvent actionEvent) {
        try {
            int size = staffingList.size();
            taShowMessages.setText("Tamaño de la lista de asignaciones: " + size);
        } catch (ListException e) {
            taShowMessages.setText("Error al obtener el tamaño de la lista: " + e.getMessage());
            util.FXUtility.showErrorAlert("Error", "Error al obtener el tamaño de la lista: " + e.getMessage());
        }
    }

    // Métodos de ordenamiento

    private void sortByEmployeeId(CircularDoublyLinkedList list) throws ListException {
        if(list.isEmpty())
            throw new ListException("Circular Doubly Linked List is empty");

        int n = list.size();
        boolean swapped;

        for(int i = 1; i <= n - 1; i++) {
            swapped = false;
            for(int j = 1; j <= n - i; j++) {
                Staffing current = (Staffing) list.getNode(j).data;
                Staffing next = (Staffing) list.getNode(j + 1).data;

                if(current.getEmployeeId() > next.getEmployeeId()) {
                    // Intercambiar los datos de los nodos
                    Object temp = list.getNode(j).data;
                    list.getNode(j).data = list.getNode(j + 1).data;
                    list.getNode(j + 1).data = temp;
                    swapped = true;
                }
            }

            if(!swapped)
                break;
        }
    }

    private void sortByEmployeeName(CircularDoublyLinkedList list) throws ListException {
        if(list.isEmpty())
            throw new ListException("Circular Doubly Linked List is empty");

        int n = list.size();
        boolean swapped;

        for(int i = 1; i <= n - 1; i++) {
            swapped = false;
            for(int j = 1; j <= n - i; j++) {
                Staffing current = (Staffing) list.getNode(j).data;
                Staffing next = (Staffing) list.getNode(j + 1).data;

                if(util.Utility.compare(current.getEmployeeName(), next.getEmployeeName()) > 0) {
                    // Intercambiar los datos de los nodos
                    Object temp = list.getNode(j).data;
                    list.getNode(j).data = list.getNode(j + 1).data;
                    list.getNode(j + 1).data = temp;
                    swapped = true;
                }
            }

            if(!swapped)
                break;
        }
    }

    private void sortByJobPosition(CircularDoublyLinkedList list) throws ListException {
        if(list.isEmpty())
            throw new ListException("Circular Doubly Linked List is empty");

        int n = list.size();
        boolean swapped;

        for(int i = 1; i <= n - 1; i++) {
            swapped = false;
            for(int j = 1; j <= n - i; j++) {
                Staffing current = (Staffing) list.getNode(j).data;
                Staffing next = (Staffing) list.getNode(j + 1).data;

                if(util.Utility.compare(current.getJobPosition(), next.getJobPosition()) > 0) {
                    // Intercambiar los datos de los nodos
                    Object temp = list.getNode(j).data;
                    list.getNode(j).data = list.getNode(j + 1).data;
                    list.getNode(j + 1).data = temp;
                    swapped = true;
                }
            }

            if(!swapped)
                break;
        }
    }

    private void sortByAssignationType(CircularDoublyLinkedList list) throws ListException {
        if(list.isEmpty())
            throw new ListException("Circular Doubly Linked List is empty");

        int n = list.size();
        boolean swapped;

        for(int i = 1; i <= n - 1; i++) {
            swapped = false;
            for(int j = 1; j <= n - i; j++) {
                Staffing current = (Staffing) list.getNode(j).data;
                Staffing next = (Staffing) list.getNode(j + 1).data;

                if(util.Utility.compare(current.getAssignmentType(), next.getAssignmentType()) > 0) {
                    // Intercambiar los datos de los nodos
                    Object temp = list.getNode(j).data;
                    list.getNode(j).data = list.getNode(j + 1).data;
                    list.getNode(j + 1).data = temp;
                    swapped = true;
                }
            }

            if(!swapped)
                break;
        }
    }
}