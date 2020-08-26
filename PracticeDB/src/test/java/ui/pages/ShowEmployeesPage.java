package ui.pages;

import cucumber.api.java.gl.E;
import models.Employee;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.config.Driver;

import java.util.ArrayList;
import java.util.List;

public class ShowEmployeesPage {

    @FindBy(css = "tbody tr")
    List<WebElement> tableRows;

    public ShowEmployeesPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    public static ShowEmployeesPage goTo(){
        Driver.getDriver().get("http://localhost:300/employees");
        return new ShowEmployeesPage();
    }

    public List<Employee> parseEmployees(){
        List<Employee> employees = new ArrayList<>();
        tableRows.remove(0);
        for(WebElement row : tableRows){
            Employee employee = new Employee();
            List<WebElement> columns = row.findElements(By.tagName("td"));
            employee.setEmployeeNumber(Integer.parseInt(columns.get(0).getText()));
            employee.setLastName(columns.get(1).getText());
            employee.setFirstName(columns.get(2).getText());
            employee.setExtension(columns.get(3).getText());
            employee.setEmail(columns.get(4).getText());
            employee.setOfficeCode(columns.get(5).getText());
            employee.setReportsTo(Integer.parseInt(columns.get(6).getText()));
            employee.setJobTitle(columns.get(7).getText());
            employees.add(employee);
        }

        return employees;
    }

    public static void main(String[] args) {
        MainPage mainPage = MainPage.goTo();
        mainPage.enterEmployeeInfo(new Employee(0, "Tbone", "Mike", "300", "Mke@gmail.com", "909", 101, "Tester"));
        mainPage.submitInfo();
    }
}
