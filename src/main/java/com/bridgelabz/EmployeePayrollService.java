package com.bridgelabz;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EmployeePayrollService {
    private IOService ioService;

    public enum IOService {FILE_IO, CONSOLE_IO, DB_IO, REST_IO}

    private List<EmployeePayrollData> employeePayrollList;
    private EmployeePayrollDBService employeePayrollDBService;

    public EmployeePayrollService() {
        employeePayrollDBService = EmployeePayrollDBService.getInstance();

    }

    public EmployeePayrollService(List<EmployeePayrollData>employeePayrollList) {
        this();
        this.employeePayrollList = employeePayrollList;
    }

    public static void main(String[] args) {
        ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayrollService.readEmployeePayrollData(consoleInputReader);
        employeePayrollService.writeEmployeeData(IOService.CONSOLE_IO);
    }

    private void readEmployeePayrollData(Scanner consoleInputReader) {
        System.out.println("Enter Employee ID: ");
        int id = consoleInputReader.nextInt();
        System.out.println("Enter Employee Name: ");
        String name = consoleInputReader.next();
        System.out.println("Enter Employee Salary: ");
        double salary = consoleInputReader.nextDouble();
        employeePayrollList.add(new EmployeePayrollData(id, name, salary));
    }

    public List <EmployeePayrollData>  readEmployeePayrollData(IOService ioService) {
    if (ioService.equals(IOService.DB_IO))
        this.employeePayrollList =  employeePayrollDBService.readData();
        return this.employeePayrollList;
    }

    public List<EmployeePayrollData> readEmployeePayrollDataRange(IOService ioService, LocalDate startDate, LocalDate endDate) {
    if(ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getEmployeePayrollForDataRange(startDate,endDate);
        return null;
}
    public Map<String, Double> readAverageSalaryByGender(IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getAverageSalaryByGender();
        return null;
    }

    public boolean checkEmployeePayrollInSyncWithDB(String name) {
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getInstance().getEmployeePayrollData(name);
        return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
    }

    public void updateEmployeeSalary(String name, double salary) {
        int result =   employeePayrollDBService.updateEmployeeData(name,salary);
        if (result == 0) return;
        EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
        if (employeePayrollData != null) employeePayrollData.salary = salary;
    }
    private EmployeePayrollData getEmployeePayrollData(String name){
        return this.employeePayrollList.stream()
                .filter(employeePayrollDataItem -> employeePayrollDataItem.name.equals(name))
                .findFirst()
                .orElse(null);
    }

    public void addEmployeeToPayroll(String name, double salary, LocalDate startDate, char gender) {
        employeePayrollList.add(employeePayrollDBService.addEmployeeToPayroll(name,salary,startDate,gender));
    }

    public void writeEmployeeData(IOService ioService) {
        if (ioService.equals(IOService.CONSOLE_IO))
            System.out.println("\nWriting Employee Payroll Roaster to Console\n" + employeePayrollList);
        else if(ioService.equals(IOService.FILE_IO))
            new EmployeePayrollFileIOService().writeData(employeePayrollList);
    }

    public void printData(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
        new EmployeePayrollFileIOService().printData();
    }

    public long countEntries(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
            return new EmployeePayrollFileIOService().countEntries();
        return 0;
    }
    public void readDataFromFile(IOService ioService) {
        if (ioService.equals(IOService.CONSOLE_IO)) {
            new EmployeePayrollFileIOService().readDataFromFile();
        }
    }
}
