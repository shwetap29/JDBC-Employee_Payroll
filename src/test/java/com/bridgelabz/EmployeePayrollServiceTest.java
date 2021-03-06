package com.bridgelabz;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.bridgelabz.EmployeePayrollService.IOService.DB_IO;
import static com.bridgelabz.EmployeePayrollService.IOService.FILE_IO;

public class EmployeePayrollServiceTest {
       @Test
    public void givenEmployees_WhenWrittenToFile_ShouldMatchEmployeeEntries(){
      EmployeePayrollData[] arrayOfEmps ={
               new EmployeePayrollData(1,"Jeff Bezos",100000.0),
              new EmployeePayrollData(2,"Bill Gates",200000.0),
               new EmployeePayrollData(3,"Mark Zuckerberg",300000.0),
           };
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
        employeePayrollService.writeEmployeeData(FILE_IO);
        employeePayrollService.printData(FILE_IO);
        long entries = employeePayrollService.countEntries(FILE_IO);
        Assert.assertEquals(3,entries);
  }


    @Test
    public void givenEmployeePayrollInDb_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(DB_IO);
        Assert.assertEquals(3,employeePayrollData.size());
       }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() {
           EmployeePayrollService employeePayrollService = new EmployeePayrollService();
           List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(DB_IO);
           employeePayrollService.updateEmployeeSalary("Charlie",3000000.00);
           boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Charlie");
           Assert.assertTrue(result);

    }

    @Test
    public void givenDataRange_WhenRetrieved_ShouldMatchEmployeeCount() {
           EmployeePayrollService employeePayrollService = new EmployeePayrollService();
           employeePayrollService.readEmployeePayrollData(DB_IO);
        LocalDate startDate = LocalDate.of(2018,01,01);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDataRange(DB_IO,startDate,endDate);
        Assert.assertEquals(3,employeePayrollData.size());
    }

    @Test
    public void givenPayrollData_WhenAverageSalaryRetrievedByGender_ShouldReturnProperValue() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        Map<String,Double> averageSalaryByGender = employeePayrollService.readAverageSalaryByGender(EmployeePayrollService.IOService.DB_IO);
        Assert.assertTrue(averageSalaryByGender.get("M").equals(2500000.00)
                && averageSalaryByGender.get("F").equals(3000000.00));
    }

    @Test
    public void givenNewEmployee_WhenAdded_ShouldSyncWithDB() {
        EmployeePayrollService employeePayrollService =new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.addEmployeeToPayroll("John",5000000.00,LocalDate.now(),'M');
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("John");
        Assert.assertTrue(result);
    }
}
