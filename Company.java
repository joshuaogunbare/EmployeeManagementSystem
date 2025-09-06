import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Collections;

public class Company {

    //5a. counter to track amount of employees added
    private static int employeeCount = 0;

    //5b.  map to store employees by their generated ID
    private Map<String, Employee> employees = new HashMap<>();

    //5a. Private static method to generate the next employee ID, padded to 6 digits
    private static String generateEmployeeId() {
        employeeCount++; //increment count for each new employee
        return String.format("%06d", employeeCount); //pad with preceding zeroes
    }

    //5 c i. adds new employee
    public void addEmployee(String name, int salary, String area) {

        // Validate salary
        if (salary < 0) {
            throw new InvalidDataException("Salary cannot be negative.");
        }

        // Validate name
        if (name == null || name.trim().isEmpty()) {// if the name is null or empty
            throw new InvalidDataException("Name cannot be empty.");
        }

        // Validate area
        if (!area.equals("BUSINESS") && !area.equals("ADMINISTRATIVE") && !area.equals("TECHNICAL")) {
            throw new InvalidDataException("Area has to be : BUSINESS or ADMINISTRATIVE or TECHNICAL.");
        }

        String id = generateEmployeeId();// generates ID
        Employee emp = new Employee(name, salary, area); // creates new Employee record
        employees.put(id, emp);//5 c iii. stores in map
    }

    // 5d i.  Adds employees from reading a CSV file
    public void addEmployeesFromFile(String filename){
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine(); // Skip the first row
    
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[1].trim();
                int salary = Integer.parseInt(parts[2].trim());
                String area = parts[3].trim();
                String rowId = parts[0].trim(); // to get row_id from the first column

                try {
                    addEmployee(name, salary, area);
                } catch (InvalidDataException e) {
                    System.out.println("Error adding employee at row ID " + rowId + ": " + e.getMessage());//5 d iii
                }

            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);//displays the error message
            e.printStackTrace();
        }
    }

    //5e. Looks up employee by ID and prints their info or not found message
    public void employeeLookup(String id) {
        Employee emp = employees.get(id);
        if (emp != null) {//if employee exists
            System.out.println("Employee ID: " + id);
            System.out.println("Name: " + emp.name());
            System.out.println("Salary: " + emp.salary());
            System.out.println("Area: " + emp.area());
        } else {
            System.out.println("EMPLOYEE WITH ID " + id + " NOT FOUND");
        }
    }

    //5f. Prints all employees in alphabetical order by name using a stream pipeline
    public void printAllEmployeesByName() {
        employees.entrySet()
            .stream()//creates a stream of employees
            .sorted(Comparator.comparing(entry -> entry.getValue().name()))
            .forEach(entry -> {
                
                 //prints the employee info
                String id = entry.getKey();
                Employee emp = entry.getValue();
                System.out.println("Employee ID: " + id);
                System.out.println("Name: " + emp.name());
                System.out.println("Salary: " + emp.salary());
                System.out.println("Area: " + emp.area());
                System.out.println(); //adds a blank line between the employees
            });
    }

    // Getter method to expose employees map for the Rest API
    public Map<String, Employee> getEmployees() {
    return Collections.unmodifiableMap(employees);
    }


   
}
