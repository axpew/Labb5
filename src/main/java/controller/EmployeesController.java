package controller;

import domain.CircularLinkedList;
import domain.Employee;
import domain.ListException;
import domain.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class EmployeesController {

    @javafx.fxml.FXML
    private Text txtMessage;
    @javafx.fxml.FXML
    private Pane mainPain;
    @javafx.fxml.FXML
    private TableColumn idTableColumn;
    @javafx.fxml.FXML
    private Pane buttonPane;
    @javafx.fxml.FXML
    private TextArea taShowMessages;
    @FXML
    private TableView employeesTableColumn;
    @FXML
    private TableColumn titleTableColumn;
    @FXML
    private TableColumn firstNameTableColumn;
    @FXML
    private TableColumn lastNameTableColumn;
    @FXML
    private TableColumn birthdayTableColumn;

    private CircularLinkedList employeesList;

    @FXML
    public void initialize() {
        taShowMessages.setWrapText(true);

        // Cargamos la lista general o creamos una nueva si no existe
        this.employeesList = util.Utility.getEmployeeList();
        if (employeesList == null) {
            this.employeesList = new CircularLinkedList();
            util.Utility.setEmployeeList(this.employeesList);
        }

        employeesTableColumn.setItems(convertToObservableList(employeesList));

        // Configuramos las columnas de la tabla
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        birthdayTableColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));

        try {
            taShowMessages.setText("Lista de empleados cargada con éxito. Total de empleados: " + this.employeesList.size());
        } catch (ListException e) {
            taShowMessages.setText("Error al cargar la lista de empleados: " + e.getMessage());
        }
    }

    // Adaptado para CircularLinkedList
    public ObservableList<Employee> convertToObservableList(CircularLinkedList list) {
        ObservableList<Employee> empleados = FXCollections.observableArrayList();

        if (list.isEmpty()) {
            return empleados;
        }

        Node aux = list.first;
        do {
            // Procesar el nodo actual
            Employee current = (Employee) aux.data;
            empleados.add(current);
            aux = aux.next;
        } while (aux != list.first); // Se detiene al volver al inicio

        return empleados;
    }

    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        try {
            employeesList.clear();
            employeesTableColumn.setItems(convertToObservableList(employeesList));
            taShowMessages.setText("Lista de empleados limpiada exitosamente.");

            // Actualizamos la lista global
            util.Utility.setEmployeeList(this.employeesList);
        } catch (Exception e) {
            taShowMessages.setText("Error al limpiar la lista: " + e.getMessage());
            util.FXUtility.showErrorAlert("Error", "Error al limpiar la lista: " + e.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void removeOnAction(ActionEvent actionEvent) {
        try {
            Employee selectedEmployee = (Employee) employeesTableColumn.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                employeesList.remove(selectedEmployee);
                employeesTableColumn.setItems(convertToObservableList(employeesList));
                taShowMessages.setText("Empleado eliminado exitosamente:\n" + selectedEmployee.toString());

                // Actualizamos la lista global
                util.Utility.setEmployeeList(this.employeesList);
            } else {
                taShowMessages.setText("No se ha seleccionado ningún empleado para eliminar.");
                util.FXUtility.showErrorAlert("Error", "No se ha seleccionado ningún empleado");
            }
        } catch (ListException e) {
            taShowMessages.setText("Error al eliminar empleado: " + e.getMessage());
            util.FXUtility.showErrorAlert("Error", "Error al eliminar empleado: " + e.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.laboratory5.HelloApplication", "addEmployee-view.fxml", mainPain);
    }

    @javafx.fxml.FXML
    public void removeLastOnAction(ActionEvent actionEvent) {
        try {
            Employee removedEmployee = (Employee) employeesList.removeLast();
            employeesTableColumn.setItems(convertToObservableList(employeesList));
            taShowMessages.setText("Último empleado eliminado:\n" + removedEmployee.toString());

            // Actualizamos la lista global
            util.Utility.setEmployeeList(this.employeesList);
        } catch (ListException e) {
            taShowMessages.setText("Error al eliminar el último empleado: " + e.getMessage());
            util.FXUtility.showErrorAlert("Error", "Error al eliminar el último empleado: " + e.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void sortNameOnAction(ActionEvent actionEvent) {
        try {
            sortByName(employeesList);
            employeesTableColumn.setItems(convertToObservableList(employeesList));
            taShowMessages.setText("Lista ordenada por apellido");

            // Actualizamos la lista global
            util.Utility.setEmployeeList(this.employeesList);
        } catch (ListException e) {
            taShowMessages.setText("La lista está vacía");
            util.FXUtility.showErrorAlert("Error", "Error al ordenar la lista: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void sortIdOnAction(ActionEvent actionEvent) {
        try {
            sortByID(employeesList);
            employeesTableColumn.setItems(convertToObservableList(employeesList));
            taShowMessages.setText("Lista ordenada por ID");

            // Actualizamos la lista global
            util.Utility.setEmployeeList(this.employeesList);
        } catch (ListException e) {
            taShowMessages.setText("La lista está vacía");
            util.FXUtility.showErrorAlert("Error", "Error al ordenar la lista: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void getPrevOnAction(ActionEvent actionEvent) {
        try {
            Employee selectedEmployee = (Employee) employeesTableColumn.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                taShowMessages.setText("Empleado anterior:\n" + employeesList.getPrev(selectedEmployee));
            } else {
                taShowMessages.setText("No se ha seleccionado ningún empleado.");
                util.FXUtility.showErrorAlert("Error", "No se ha seleccionado ningún empleado");
            }
        } catch (ListException e) {
            taShowMessages.setText("Error: " + e.getMessage());
            util.FXUtility.showErrorAlert("Error", "Error: " + e.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void containsOnAction(ActionEvent actionEvent) {
        try {
            javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
            dialog.setTitle("Verificar Estudiante");
            dialog.setHeaderText("Ingrese el ID del empleado");
            dialog.setContentText("ID:");

            dialog.showAndWait().ifPresent(id -> {
                try {
                    Employee employee = new Employee(Integer.parseInt(id));
                    boolean contains = employeesList.contains(employee);
                    if (contains) {
                        taShowMessages.setText("El empleado con ID " + id + " existe en la lista.");
                    } else {
                        taShowMessages.setText("El empleado con ID " + id + " no existe en la lista.");
                    }
                } catch (ListException e) {
                    taShowMessages.setText("Error al verificar si el empleado existe: " + e.getMessage());
                    util.FXUtility.showErrorAlert("Error", "Error al verificar si el empleado existe: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            taShowMessages.setText("Error: " + e.getMessage());
            util.FXUtility.showErrorAlert("Error", "Error: " + e.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void getNextOnAction(ActionEvent actionEvent) {
        try {
            Employee selectedEmployee = (Employee) employeesTableColumn.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                taShowMessages.setText("Empleado siguiente:\n" + employeesList.getNext(selectedEmployee));
            } else {
                taShowMessages.setText("No se ha seleccionado ningún empleado.");
                util.FXUtility.showErrorAlert("Error", "No se ha seleccionado ningún empleado");
            }
        } catch (ListException e) {
            taShowMessages.setText("Error: " + e.getMessage());
            util.FXUtility.showErrorAlert("Error", "Error: " + e.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) {
        try {
            int size = employeesList.size();
            taShowMessages.setText("Tamaño de la lista de empleados: " + size);
        } catch (ListException e) {
            taShowMessages.setText("Error al obtener el tamaño de la lista: " + e.getMessage());
            util.FXUtility.showErrorAlert("Error", "Error al obtener el tamaño de la lista: " + e.getMessage());
        }
    }

    @FXML
    public void refreshOnAction(ActionEvent actionEvent) throws ListException {
        // Cargamos la lista general
        this.employeesList = util.Utility.getEmployeeList();

        employeesTableColumn.setItems(convertToObservableList(employeesList));

        try {
            taShowMessages.setText("Lista de empleados cargada con éxito. Total de empleados: " + this.employeesList.size());
        } catch (ListException e) {
            taShowMessages.setText("Error al cargar la lista de empleados: " + e.getMessage());
        }
    }

    public void sortByID(CircularLinkedList list) throws ListException {
        if(list.isEmpty())
            throw new ListException("Circular Linked List is empty");

        int n = list.size();
        boolean swapped;

        for(int i = 0; i < n - 1; i++) {
            swapped = false;
            Node current = list.first;

            for(int j = 0; j < n - i - 1; j++) {
                Node nextNode = current.next;

                // Comparar el ID de empleado
                Employee currentEmployee = (Employee)current.data;
                Employee nextEmployee = (Employee)nextNode.data;

                if(util.Utility.compare(currentEmployee.getId(), nextEmployee.getId()) > 0) {
                    // Intercambiamos los datos de los nodos
                    Object temp = current.data;
                    current.data = nextNode.data;
                    nextNode.data = temp;
                    swapped = true;
                }

                current = current.next;
            }

            if(!swapped)
                break;
        }
    }

    public void sortByName(CircularLinkedList list) throws ListException {
        if(list.isEmpty())
            throw new ListException("Circular Linked List is empty");

        int n = list.size();
        boolean swapped;

        for(int i = 0; i < n - 1; i++) {
            swapped = false;
            Node current = list.first;

            for(int j = 0; j < n - i - 1; j++) {
                Node nextNode = current.next;

                // Comparar el apellido de empleado
                Employee currentEmployee = (Employee)current.data;
                Employee nextEmployee = (Employee)nextNode.data;

                if(util.Utility.compare(currentEmployee.getLastname(), nextEmployee.getLastname()) > 0) {
                    // Intercambiamos los datos de los nodos
                    Object temp = current.data;
                    current.data = nextNode.data;
                    nextNode.data = temp;
                    swapped = true;
                }

                current = current.next;
            }

            if(!swapped)
                break;
        }
    }
}