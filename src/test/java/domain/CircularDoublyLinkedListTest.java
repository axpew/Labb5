package domain;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class CircularDoublyLinkedListTest {


    @Test
    void test() {

        //Punto a
        CircularDoublyLinkedList a = new CircularDoublyLinkedList();
        CircularDoublyLinkedList b = new CircularDoublyLinkedList();
        CircularDoublyLinkedList c = new CircularDoublyLinkedList();
        CircularDoublyLinkedList d = new CircularDoublyLinkedList();
        //Punto B
        CircularLinkedList list = new CircularLinkedList();

        try {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2005, 6, 21);
        list.add(new Employee(111111111, "Campos", "Ana", "Informática", calendar.getTime()));


        calendar.set(2002, 10, 5);
        list.add(new Employee(222222222, "Gutierrez", "David", "Administración", calendar.getTime()));

        calendar.set(2000, 8, 15);
        list.add(new Employee(333333333, "Campos", "David", "Inglés", calendar.getTime()));

        calendar.set(1997, 3, 23);
        list.add(new Employee(444444444, "Alvarado", "Miguel", "Turismo", calendar.getTime()));

        calendar.set(1995, 1, 2);
        list.add(new Employee(555555555, "Ramírez", "Pablo", "Agronomía", calendar.getTime()));

        calendar.set(1991, 9, 10);
        list.add(new Employee(666666666, "Cordero", "Abigail", "Diseño Web", calendar.getTime()));

        calendar.set(1981, 6, 20);
        list.add(new Employee(777777777, "Vargas", "Roberto", "Abogado", calendar.getTime()));

        calendar.set(1968, 2, 9);
        list.add(new Employee(888888888, "Araya", "Alicia", "Informática", calendar.getTime()));


        calendar.set(1997, 10, 16);
        list.add(new Employee(999999999, "Castro", "María", "Inglés", calendar.getTime()));

        calendar.set(2000, 10, 26);
        list.add(new Employee(122222222, "Rojas", "Enrique", "Administración", calendar.getTime()));

        calendar.set(1960, 3, 10);
        list.add(new Employee(133333333, "Iglesias", "Pedro", "Informática", calendar.getTime()));


        System.out.println(list);
        System.out.println();

        showAgeList(a, list, 18, 25);
        System.out.println("Empleados con rango de edad de 18 a 25");
        System.out.println(a);
        System.out.println();

        showAgeList(b, list, 26, 40);
        System.out.println("Empleados con rango de edad de 26 a 40");
        System.out.println(b);
        System.out.println();

        showAgeList(c, list, 41, 55);
        System.out.println("Empleados con rango de edad de 41 a 55");
        System.out.println(c);
        System.out.println();

        showAgeList(d, list, 56, 100);
        System.out.println("Empleados con rango de edad mayor a 55");
        System.out.println(d);
        System.out.println();

        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }


    public CircularDoublyLinkedList showAgeList(CircularDoublyLinkedList doublyList,CircularLinkedList list, int low, int high) throws ListException {

        for (int i = 1; i <= list.size(); i++){

            Employee employee = (Employee) list.getNode(i).data;

            int age = employee.getAge();

            if( age >= low && age <= high){

                doublyList.add(employee);
            }
        }

        return doublyList;
    }

}
