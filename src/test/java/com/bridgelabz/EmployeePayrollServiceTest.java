package com.bridgelabz;

import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

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
           employeePayrollService.updateEmployeeSalary("Terisa",3000000.00);
           boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
           Assert.assertTrue(result);

    }
}