package com.bridgelabz;

import java.util.List;

public class EmployeePayrollFileIOService {
    public final static String PAYROLL_FILE_NAME = "payroll.txt";

    public void writeData(List<EmployeePayrollData> employeePayrollList) {
        StringBuilder stringBuilder = new StringBuilder();
        employeePayrollData.forEach(emp -> stringBuilder.append(emp.toString().concat("\n")));
        try {
            Files.write(Paths.get(PAYROLL_FILE_NAME), stringBuilder.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printData() {

    }

    public long countEntries() {
    }
}
