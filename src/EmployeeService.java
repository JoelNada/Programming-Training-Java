import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeService {
    public static void main(String[] args) {

     List<Employee> employees = Arrays.asList(
             new Employee("Alice", 30, 50000, List.of("Java", "Spring"), List.of("12345", "67890"),"M"),
             new Employee("Bob", 35, 60000, List.of("Python", "Django"), List.of("23456", "78901"),"M"),
             new Employee("Charlie", 28, 55000, List.of("JavaScript", "React"), List.of("34567", "89012"),"F"),
        new Employee("Tejaswi", 25, 57000, List.of("Java", "React"), List.of("34567", "89012"),"F")
     );

        employees.stream().filter(emp->emp.getPhoneNumbers().stream().anyMatch(num->num.contains("89012"))).forEach(System.out::println);

        System.out.println(employees.stream().collect(Collectors.groupingBy(Employee::getGender,Collectors.counting())));
//        System.out.println( employees.stream()
//                .collect(Collectors.groupingBy(emp->emp.getSkills().stream()
//                        .filter(skill->skill.equals("Java")).toList(),Collectors.toList())));
        System.out.println(employees.stream()
                .collect(Collectors.groupingBy(emp -> emp.getSkills().stream()
                        .filter(skill -> skill.equals("Java"))
                        .collect(Collectors.toList()))));

    }



}
