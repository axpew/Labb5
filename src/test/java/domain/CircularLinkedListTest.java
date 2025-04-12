package domain;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
class CircularLinkedListTest {

    @Test
    void test(){

        CircularLinkedList list = new CircularLinkedList();
        /* informática, administración, inglés, turismo, agronomía, diseño publicitario, diseño web, asesor,
           doctor, abogado; y las edades: 20, 23, 25, 28, 30, 34, 37,39, 42, 44, 48, 52, 55, 57, 60, 63, 65 */
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
            list.add(new Employee(133333333, "Iglesias" ,"Pedro", "Informática", calendar.getTime()));


            System.out.println(list);
            System.out.println();

            System.out.println("Empleados con rango de edad de 18 a 25");
            System.out.println(showAgeList(list,18,25));
            System.out.println("Empleados con rango de edad de 26 a 40");
            System.out.println(showAgeList(list,26,40));
            System.out.println("Empleados con rango de edad de 41 a 55");
            System.out.println(showAgeList(list,41,55));
            System.out.println("Empleados con rango de edad mayor a 55");
            System.out.println(showAgeList(list,56,100));


            CircularLinkedList my_list = getTitleList(list, "Informática");
            System.out.println("Empleados con la profesión: Informática: \n" + my_list.show());


            my_list = getTitleList(list, "Administración");
            System.out.println("Empleados con la profesión: Administración: \n" + my_list.show());

            my_list = getTitleList(list, "Inglés");
            System.out.println("Empleados con la profesión: Inglés: \n" + my_list.show());

            my_list = getTitleList(list, "Turismo");
            System.out.println("Empleados con la profesión: Turismo: \n" + my_list.show());

            my_list = getTitleList(list, "Diseño Web");
            System.out.println("Empleados con la profesión: Diseño Web: \n" + my_list.show());

            my_list = getTitleList(list, "Abogado");
            System.out.println("Empleados con la profesión: Abogado: \n" + my_list.show());

            my_list = getTitleList(list, "Agronomía");
            System.out.println("Empleados con la profesión: Agronomía: \n" + my_list.show());

        } catch (ListException e) {
            throw new RuntimeException(e);
        }

    }

    private CircularLinkedList getTitleList(CircularLinkedList list, String title) throws ListException {
        CircularLinkedList resultList = new CircularLinkedList();

        for (int i = 1; i <= list.size(); i++){

            Employee employee = (Employee) list.getNode(i).data;

            if(title.equals(employee.getTitle())){  //Otra forma: if(util.Utility.compare(employee.getTitle(), title) == 0)


                resultList.add(employee) ;
                System.out.println();

            }

        }

        return resultList;
    }


    public String showAgeList(CircularLinkedList list, int low, int high) throws ListException {
        String result = "";
        for (int i = 1; i <= list.size(); i++){

            Employee employee = (Employee) list.getNode(i).data;
            // int age = employee.getAge(employee.getBirthday());
            int age = employee.getAge();

            if( age >= low && age <= high){

                result += employee + "\n";

            }
        }

        return result;
    }

  
}//END TEST CLASS