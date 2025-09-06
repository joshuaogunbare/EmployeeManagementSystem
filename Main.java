public class Main {
    public static void main(String[] args) {
        // creates a company object
        Company company = new Company();

        //adds employees from the CSV file
        company.addEmployeesFromFile("lab12_data.csv");

        // look up specific employees by ID
        company.employeeLookup("000003");
        company.employeeLookup("000017");
        company.employeeLookup("000100");

        //print all employees in alphabetical order
        company.printAllEmployeesByName();
    }

}
