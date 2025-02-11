import java.util.*;
import java.util.stream.Collectors;

public class EmployeeOperations {


    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", 30, 75000, Arrays.asList("Java", "Spring", "Microservices"), Arrays.asList("1234567890", "9876543210"), "Female", "IT", "Software Engineer", 5),
                new Employee("Bob", 28, 68000, Arrays.asList("Python", "Django", "Machine Learning"), Arrays.asList("1231231231", "9879879870"), "Male", "Data Science", "Data Scientist", 4),
                new Employee("Charlie", 35, 95000, Arrays.asList("JavaScript", "React", "Node.js"), Arrays.asList("1112223334", "4445556667"), "Male", "Frontend", "UI Developer", 8),
                new Employee("Diana", 40, 120000, Arrays.asList("AWS", "Kubernetes", "Docker"), Arrays.asList("3334445556", "7778889990"), "Female", "Cloud", "Cloud Architect", 12),
                new Employee("Ethan", 29, 70000, Arrays.asList("SQL", "PL/SQL", "Oracle"), Arrays.asList("5556667778", "9990001112"), "Male", "Database", "Database Administrator", 6),
                new Employee("Fiona", 32, 80000, Arrays.asList("UI/UX", "Sketch", "Figma"), Arrays.asList("2223334445", "8889990001"), "Female", "Design", "UX Designer", 7),
                new Employee("George", 45, 130000, Arrays.asList("Leadership", "Agile", "Scrum"), Arrays.asList("4445556667", "7778889991"), "Male", "Management", "Project Manager", 20),
                new Employee("Hannah", 27, 60000, Arrays.asList("HTML", "CSS", "JavaScript"), Arrays.asList("1113335557", "6667778880"), "Female", "Frontend", "Web Developer", 3),
                new Employee("Ian", 31, 85000, Arrays.asList("Data Analysis", "Excel", "Power BI"), Arrays.asList("1234567891", "9876543211"), "Male", "Analytics", "Data Analyst", 8),
                new Employee("Jenna", 38, 100000, Arrays.asList("Cybersecurity", "Penetration Testing", "Network Security"), Arrays.asList("3216549870", "7891234560"), "Female", "Security", "Security Analyst", 10)
        );

        /// To group the employees by gender and get average salary
        System.out.println("Employees Salary Average : "+employees.stream()
                .collect(Collectors
                        .groupingBy(Employee::getGender,
                                Collectors.averagingDouble(Employee::getSalary)
                        )));

        System.out.println("Employees categorized by gender : "+
                employees.stream()
                        .collect(Collectors
                                .groupingBy(Employee::getGender,
                                        Collectors.mapping(Employee::getName,Collectors.toList())))
        );

        System.out.println("Employees with experience greater than 5 years grouped by gender : "+
                employees.stream()
                        .collect(Collectors.groupingBy(
                                Employee::getGender,
                                Collectors.filtering(employee -> employee.getExperienceYears()>5
                                        ,Collectors.mapping(Employee::getName,Collectors.toList()))
                        ))
        );
       System.out.println("Filtered Employees with phone numbers starting with '987' : "+
               employees.stream().filter(employee -> employee.getPhoneNumbers().stream().anyMatch(num->num.startsWith("987"))).toList()
        );

    }
}
