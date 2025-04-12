package domain;

public class JobPosition {

    private int id;
    private String description;
    private double hourlyWage;
    private static int autoID; //para el auto generado

    //Constructor 1
    public JobPosition(int id, String description, double hourlyWage) {
        //autoID++; //Se incrementa aunque no se use para evitar duplicidad
        this.id = id;
        this.description = description;
        this.hourlyWage = hourlyWage;
    }

    //Constructor 2
    public JobPosition(String description, double hourlyWage) {
        this.id = ++autoID;//El ID tiene que ser autogenerado
        this.description = description;
        this.hourlyWage = hourlyWage;
    }

    //Constructor 3
    public JobPosition(int id) {
       // autoID++; //Se incrementa aunque no se use para evitar duplicidad
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public static int getAutoID() {
        return autoID;
    }

    public static void setAutoID(int autoID) {
        JobPosition.autoID = autoID;
    }

    @Override
    public String toString() {
        return "(ID)"+id+"/(Job Position)"+description +" /(Hourly Wage)"  + hourlyWage;
    }

    // MÉTODOS
    public double getSalary(double n){ //retorna el salario mensual para ese puesto
        return n * hourlyWage;
    }//End getSalary()


}
