package controller;

import domain.CircularDoublyLinkedList;
import domain.JobPosition;
import domain.ListException;
import domain.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.Random;

public class JobPositionController {
    @FXML
    private Text txtMessage;
    @FXML
    private TableColumn idTableColumn;
    @FXML
    private TableView employeesTableColumn;
    @FXML
    private TableColumn firstNameTableColumn;
    @FXML
    private Pane mainPain;
    @FXML
    private Pane buttonPane;
    @FXML
    private TextArea taShowMessages;
    @FXML
    private TableColumn lastNameTableColumn;
    @FXML
    private TableColumn titleTableColumn; // Esta es para Total Hours
    @FXML
    private TableColumn birthdayTableColumn; // Esta es para Monthly Wage

    private CircularDoublyLinkedList jobPositionList;
    private Random random = new Random();

    @FXML
    public void initialize() {
        taShowMessages.setWrapText(true);

        //Obtenemos la lista global, no creamos una nueva
        this.jobPositionList = util.Utility.getJobPositionList();

        // Si la lista es null, inicializamos una nueva
        if (jobPositionList == null) {
            this.jobPositionList = new CircularDoublyLinkedList();
            util.Utility.setJobPositionList(this.jobPositionList);
        }

        employeesTableColumn.setItems(convertToObservableList(jobPositionList));

        //Configuramos las columnas de la tabla
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("hourlyWage"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("totalHours"));
        birthdayTableColumn.setCellValueFactory(new PropertyValueFactory<>("monthlyWage"));

        try {
            taShowMessages.setText("Lista de puestos de trabajo cargada con éxito. Total de puestos: " + this.jobPositionList.size());
        } catch (ListException e) {
            taShowMessages.setText("Error al cargar la lista de puestos: " + e.getMessage());
        }
    }

    // Adaptado para CircularDoublyLinkedList
    public ObservableList<JobPositionWrapper> convertToObservableList(CircularDoublyLinkedList list) {
        ObservableList<JobPositionWrapper> jobPositions = FXCollections.observableArrayList();

        if (list.isEmpty()) {
            return jobPositions; // Lista vacía
        }

        try {
            int size = list.size();
            for (int i = 1; i <= size; i++) {
                JobPosition current = (JobPosition) list.getNode(i).data;
                // Generar horas trabajadas aleatorias entre 40 y 50
                double hoursWorked = 40 + random.nextDouble() * 10;
                // Calcular salario mensual
                double monthlySalary = current.getSalary(hoursWorked);

                // Crear un wrapper que contiene todos los datos necesarios
                JobPositionWrapper wrapper = new JobPositionWrapper(
                        current.getId(),
                        current.getDescription(),
                        current.getHourlyWage(),
                        hoursWorked,
                        monthlySalary
                );

                jobPositions.add(wrapper);
            }
        } catch (ListException e) {
            e.printStackTrace();
        }

        return jobPositions;
    }

    @FXML
    public void clearOnAction(ActionEvent actionEvent) {
        try {
            jobPositionList.clear();
            employeesTableColumn.setItems(convertToObservableList(jobPositionList));
            taShowMessages.setText("Lista de puestos limpiada exitosamente.");

            // Actualizamos la lista global
            util.Utility.setJobPositionList(jobPositionList);
        } catch (Exception e) {
            taShowMessages.setText("Error al limpiar la lista: " + e.getMessage());
            util.FXUtility.showErrorAlert("Error", "Error al limpiar la lista: " + e.getMessage());
        }
    }

    @FXML
    public void removeOnAction(ActionEvent actionEvent) {
        try {
            JobPositionWrapper selectedPosition = (JobPositionWrapper) employeesTableColumn.getSelectionModel().getSelectedItem();
            if (selectedPosition != null) {
                // Debemos buscar el objeto JobPosition real en nuestra lista
                JobPosition toRemove = new JobPosition(selectedPosition.getId());
                jobPositionList.remove(toRemove);
                employeesTableColumn.setItems(convertToObservableList(jobPositionList));
                taShowMessages.setText("Puesto eliminado exitosamente:\n" + selectedPosition.toString());

                // Actualizamos la lista global
                util.Utility.setJobPositionList(jobPositionList);
            } else {
                taShowMessages.setText("No se ha seleccionado ningún puesto para eliminar.");
                util.FXUtility.showErrorAlert("Error", "No se ha seleccionado ningún puesto");
            }
        } catch (ListException e) {
            taShowMessages.setText("Error al eliminar puesto: " + e.getMessage());
            util.FXUtility.showErrorAlert("Error", "Error al eliminar puesto: " + e.getMessage());
        }
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.laboratory5.HelloApplication", "addJob-view.fxml", mainPain);
    }

    @FXML
    public void removeLastOnAction(ActionEvent actionEvent) {
        try {
            JobPosition removedPosition = (JobPosition) jobPositionList.removeLast();
            employeesTableColumn.setItems(convertToObservableList(jobPositionList));
            taShowMessages.setText("Último puesto eliminado:\n" + removedPosition.toString());

            // Actualizamos la lista global
            util.Utility.setJobPositionList(jobPositionList);
        } catch (ListException e) {
            taShowMessages.setText("Error al eliminar el último puesto: " + e.getMessage());
            util.FXUtility.showErrorAlert("Error", "Error al eliminar el último puesto: " + e.getMessage());
        }
    }

    @FXML
    public void sortNameOnAction(ActionEvent actionEvent) {
        try {
            sortByName(jobPositionList);
            employeesTableColumn.setItems(convertToObservableList(jobPositionList));
            taShowMessages.setText("Lista ordenada por nombre");

            // Actualizamos la lista global
            util.Utility.setJobPositionList(jobPositionList);
        } catch (ListException e) {
            taShowMessages.setText("La lista está vacía");
            util.FXUtility.showErrorAlert("Error", "Error al ordenar la lista: " + e.getMessage());
        }
    }

    @FXML
    public void sortHourlyOnAction(ActionEvent actionEvent) {
        try {
            sortByHourlyWage(jobPositionList);
            employeesTableColumn.setItems(convertToObservableList(jobPositionList));
            taShowMessages.setText("Lista ordenada por salario por hora");

            // Actualizamos la lista global
            util.Utility.setJobPositionList(jobPositionList);
        } catch (ListException e) {
            taShowMessages.setText("La lista está vacía");
            util.FXUtility.showErrorAlert("Error", "Error al ordenar la lista: " + e.getMessage());
        }
    }

    @FXML
    public void getPrevOnAction(ActionEvent actionEvent) {
        try {
            JobPositionWrapper selectedPosition = (JobPositionWrapper) employeesTableColumn.getSelectionModel().getSelectedItem();
            if (selectedPosition != null) {
                JobPosition position = new JobPosition(selectedPosition.getId());
                Object prevObject = jobPositionList.getPrev(position);
                taShowMessages.setText("Puesto anterior:\n" + prevObject.toString());
            } else {
                taShowMessages.setText("No se ha seleccionado ningún puesto.");
                util.FXUtility.showErrorAlert("Error", "No se ha seleccionado ningún puesto");
            }
        } catch (ListException e) {
            taShowMessages.setText("Error: " + e.getMessage());
            util.FXUtility.showErrorAlert("Error", "Error: " + e.getMessage());
        }
    }

    @FXML
    public void containsOnAction(ActionEvent actionEvent) {
        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
        dialog.setTitle("Verificar Puesto");
        dialog.setHeaderText("Ingrese el ID del puesto");
        dialog.setContentText("ID:");

        dialog.showAndWait().ifPresent(id -> {
            try {
                JobPosition position = new JobPosition(Integer.parseInt(id));
                boolean contains = jobPositionList.contains(position);
                if (contains) {
                    taShowMessages.setText("El puesto con ID " + id + " existe en la lista.");
                } else {
                    taShowMessages.setText("El puesto con ID " + id + " no existe en la lista.");
                }
            } catch (ListException e) {
                taShowMessages.setText("Error al verificar si el puesto existe: " + e.getMessage());
                util.FXUtility.showErrorAlert("Error", "Error al verificar si el puesto existe: " + e.getMessage());
            } catch (NumberFormatException e) {
                taShowMessages.setText("Error: El ID debe ser un número entero.");
                util.FXUtility.showErrorAlert("Error", "El ID debe ser un número entero.");
            }
        });
    }

    @FXML
    public void getNextOnAction(ActionEvent actionEvent) {
        try {
            JobPositionWrapper selectedPosition = (JobPositionWrapper) employeesTableColumn.getSelectionModel().getSelectedItem();
            if (selectedPosition != null) {
                JobPosition position = new JobPosition(selectedPosition.getId());
                Object nextObject = jobPositionList.getNext(position);
                taShowMessages.setText("Puesto siguiente:\n" + nextObject.toString());
            } else {
                taShowMessages.setText("No se ha seleccionado ningún puesto.");
                util.FXUtility.showErrorAlert("Error", "No se ha seleccionado ningún puesto");
            }
        } catch (ListException e) {
            taShowMessages.setText("Error: " + e.getMessage());
            util.FXUtility.showErrorAlert("Error", "Error: " + e.getMessage());
        }
    }

    @FXML
    public void sizeOnAction(ActionEvent actionEvent) {
        try {
            int size = jobPositionList.size();
            taShowMessages.setText("Tamaño de la lista de puestos: " + size);
        } catch (ListException e) {
            taShowMessages.setText("Error al obtener el tamaño de la lista: " + e.getMessage());
            util.FXUtility.showErrorAlert("Error", "Error al obtener el tamaño de la lista: " + e.getMessage());
        }
    }

    private void sortByName(CircularDoublyLinkedList list) throws ListException {
        if(list.isEmpty())
            throw new ListException("Circular Doubly Linked List is empty");

        int n = list.size();
        boolean swapped;

        for(int i = 1; i <= n - 1; i++) {
            swapped = false;
            for(int j = 1; j <= n - i; j++) {
                JobPosition current = (JobPosition) list.getNode(j).data;
                JobPosition next = (JobPosition) list.getNode(j + 1).data;

                if(util.Utility.compare(current.getDescription(), next.getDescription()) > 0) {
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

    private void sortByHourlyWage(CircularDoublyLinkedList list) throws ListException {
        if(list.isEmpty())
            throw new ListException("Circular Doubly Linked List is empty");

        int n = list.size();
        boolean swapped;

        for(int i = 1; i <= n - 1; i++) {
            swapped = false;
            for(int j = 1; j <= n - i; j++) {
                JobPosition current = (JobPosition) list.getNode(j).data;
                JobPosition next = (JobPosition) list.getNode(j + 1).data;

                if(current.getHourlyWage() > next.getHourlyWage()) {
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

    // Clase interna para envolver JobPosition y agregar propiedades calculadas
    public static class JobPositionWrapper {
        private int id;
        private String description;
        private double hourlyWage;
        private double totalHours;
        private double monthlyWage;

        public JobPositionWrapper(int id, String description, double hourlyWage, double totalHours, double monthlyWage) {
            this.id = id;
            this.description = description;
            this.hourlyWage = hourlyWage;
            this.totalHours = totalHours;
            this.monthlyWage = monthlyWage;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public double getHourlyWage() {
            return hourlyWage;
        }

        public double getTotalHours() {
            return totalHours;
        }

        public double getMonthlyWage() {
            return monthlyWage;
        }

        @Override
        public String toString() {
            return "(ID)" + id + "/(Job Position)" + description + " /(Hourly Wage)" + hourlyWage +
                    " /(Total Hours)" + totalHours + " /(Monthly Wage)" + monthlyWage;
        }
    }
}