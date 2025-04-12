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

import java.util.ArrayList;
import java.util.Calendar;

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

    private CircularLinkedList employeesList;
    @FXML
    private TableColumn titleTableColumn;
    @FXML
    private TableColumn firstNameTableColumn;
    @FXML
    private TableColumn lastNameTableColumn;
    @FXML
    private TableColumn birthdayTableColumn;


    @FXML
    public void initialize() {

        taShowMessages.setWrapText(true);

        //Cargamos la lista general
        this.employeesList = util.Utility.getEmployeeList();

        if (employeesList.isEmpty()) createInitialList();

        employeesTableColumn.setItems(convertToObservableList(employeesList));

        //Configuramos las columnas de la tabla
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
    }//END INITIALIZE

    //Adaptado para CircularLinkedList
    public ObservableList<Employee> convertToObservableList(CircularLinkedList list) {
        ObservableList<Employee> empleados = FXCollections.observableArrayList();
        Node aux = list.first;

        if (aux != null) {
            do {
                // Procesar el nodo actual
                Employee current = (Employee) aux.data;
                empleados.add(current);

                aux = aux.next;
            } while (aux != list.first); // Se detiene al volver al inicio

        } //End if

        return empleados;
    }

    public void createInitialList() {

        //Si la lista está vacía, agregamos algunos empleados iniciales

            Calendar calendar = Calendar.getInstance();
            calendar.set(2005, 6, 21);
            employeesList.add(new Employee(111111111, "Campos", "Ana", "Informática", calendar.getTime()));


            calendar.set(2002, 10, 5);
            employeesList.add(new Employee(222222222, "Gutierrez", "David", "Administración", calendar.getTime()));

            calendar.set(2000, 8, 15);
            employeesList.add(new Employee(333333333, "Campos", "David", "Inglés", calendar.getTime()));

            calendar.set(1997, 3, 23);
            employeesList.add(new Employee(444444444, "Alvarado", "Miguel", "Turismo", calendar.getTime()));

            calendar.set(1995, 1, 2);
            employeesList.add(new Employee(555555555, "Ramírez", "Pablo", "Agronomía", calendar.getTime()));

            calendar.set(1991, 9, 10);
            employeesList.add(new Employee(666666666, "Cordero", "Abigail", "Diseño Web", calendar.getTime()));

            calendar.set(1981, 6, 20);
            employeesList.add(new Employee(777777777, "Vargas", "Roberto", "Abogado", calendar.getTime()));

            calendar.set(1968, 2, 9);
            employeesList.add(new Employee(888888888, "Araya", "Alicia", "Informática", calendar.getTime()));


            calendar.set(1997, 10, 16);
            employeesList.add(new Employee(999999999, "Castro", "María", "Inglés", calendar.getTime()));

            calendar.set(2000, 10, 26);
            employeesList.add(new Employee(122222222, "Rojas", "Enrique", "Administración", calendar.getTime()));

            calendar.set(1960, 3, 10);
            employeesList.add(new Employee(133333333, "Iglesias" ,"Pedro", "Informática", calendar.getTime()));

    }//End



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

        //Después de agregar el objeto debemos actualizar la lista general
        util.Utility.setEmployeeList(employeesList); //Para setear la lista general

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

        //Configuramos el TextArea
        taShowMessages.setWrapText(true);

        //Cargamos la lista general
        this.employeesList = util.Utility.getEmployeeList();

        employeesTableColumn.setItems(convertToObservableList(employeesList));

        //Configuramos las columnas de la tabla
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

                //Comparar el ID de empleado
                Employee currentEmployee = (Employee)current.data;
                Employee nextEmployee = (Employee)nextNode.data;

                    if(util.Utility.compare(currentEmployee.getId(),nextEmployee.getId()) > 0){
                    //Intercambiamos los datos de los nodos
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
            try {
                throw new ListException("Circular Linked List is empty");
            } catch (ListException e) {
                throw new RuntimeException(e);
            }

        int n = list.size();
        boolean swapped;

        for(int i = 0; i < n - 1; i++) {
            swapped = false;
            Node current = list.first;

            for(int j = 0; j < n - i - 1; j++) {
                Node nextNode = current.next;

                //Comparar el ID de empleado
                Employee currentEmployee = (Employee)current.data;
                Employee nextEmployee = (Employee)nextNode.data;

                if(util.Utility.compare(currentEmployee.getLastname(),nextEmployee.getLastname()) > 0){
                    //Intercambiamos los datos de los nodos
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
