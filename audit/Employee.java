package audit;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Employee {
    private final String name;
    private final int age;
    private final double salary;
    private final String department;

    public Employee(String name, int age, double salary, String department) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.department = department;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public double getSalary() { return salary; }
    public String getDepartment() { return department; }

    @Override
    public String toString() {
        return String.format(
            "Employee{Name='%s', Dept='%s', Salary=$%.2f, Age=%d}",
            name, department, salary, age
        );
    }

    public static List<Employee> getSampleData() {
        return List.of(
            new Employee("Alice Johnson", 30, 85000.0, "ENGINEERING"),
            new Employee("Bob Smith", 45, 62000.0, "HR"),
            new Employee("Charlie Brown", 22, 91000.0, "ENGINEERING"),
            new Employee("David Lee", 58, 75000.0, "HR"),
            new Employee("Eve Wilson", 33, 55000.0, "SALES"),
            new Employee("Frank Miller", 55, 120000.0, "ENGINEERING")
        );
    }

    public static List<Employee> filteredEmployees(List<Employee> employees, Predicate<Employee> criteria) {
        return employees.stream()
                .filter(criteria)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Employee> employeeList = Employee.getSampleData();

        Predicate<Employee> criteria = e ->
                e.getSalary() > 70000 &&
                e.getDepartment().equalsIgnoreCase("ENGINEERING");

        List<Employee> eligibleEngineers = filteredEmployees(employeeList, criteria);

        // List<Employee> engineeringList = employeeList.stream()
        //         .filter(e -> e.getDepartment().equalsIgnoreCase("ENGINEERING") && e.getSalary() > 70000).toList();

        List<String> allEmployees = employeeList.stream()
                .map(Employee::getName)
                .map(String::toUpperCase)
                .toList();

        double annualBudget = employeeList.stream()
                .mapToDouble(Employee::getSalary)
                .sum();

        System.out.println(eligibleEngineers);
        // System.out.println(engineeringList);
        System.out.println(allEmployees);
        System.out.println(annualBudget);
    }
}
