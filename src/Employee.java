

import java.util.List;

public class Employee {
    private String name;
    private int age;
    private double salary;
    List<String> skills;
    List<String> phoneNumbers;
    private String gender;
    private String department;
    private String designation;
    private int experienceYears;


    public Employee(String name, int age, double salary, List<String> skills, List<String> phoneNumbers, String gender, String department, String designation, int experienceYears) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.skills = skills;
        this.phoneNumbers = phoneNumbers;
        this.gender = gender;
        this.department = department;
        this.designation = designation;
        this.experienceYears = experienceYears;
    }

    public Employee(String name, int age, double salary, List<String> skills, List<String> phoneNumbers, String gender) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.skills = skills;
        this.phoneNumbers = phoneNumbers;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", skills=" + skills +
                ", phoneNumbers=" + phoneNumbers +
                ", gender='" + gender + '\'' +
                ", department='" + department + '\'' +
                ", designation='" + designation + '\'' +
                ", experienceYears=" + experienceYears +
                '}';
    }
}
