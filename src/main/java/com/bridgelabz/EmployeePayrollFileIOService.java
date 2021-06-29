package com.bridgelabz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class EmployeePayrollFileIOService {
    public final static String PAYROLL_FILE_NAME = "payroll.txt";

    public void writeData(List<EmployeePayrollData> employeePayrollData) {
        StringBuilder stringBuilder = new StringBuilder();
        employeePayrollData.forEach(emp -> stringBuilder.append(emp.toString().concat("\n")));
        try {
            Files.write(Paths.get(PAYROLL_FILE_NAME), stringBuilder.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printData() {
        try {
            Files.lines(new File(PAYROLL_FILE_NAME).toPath()).forEach(System.out::println);
            System.out.println("No of Entries of Employees Pay in the File : " + countEntries());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public long countEntries() {
        long entries = 0;
        try {
            entries = Files.lines(new File(PAYROLL_FILE_NAME).toPath()).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

    public void readDataFromFile() {
        try {
            Files.lines(new File(PAYROLL_FILE_NAME).toPath()).map(line -> line.trim()).forEach(line -> System.out.println(line));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

