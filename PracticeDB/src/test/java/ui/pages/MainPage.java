package ui.pages;

import cucumber.api.java.it.Ma;
import models.Employee;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.config.Driver;

import java.util.List;

public class MainPage {

    @FindBy(css = "input")
    List<WebElement> inputFields;

    @FindBy(css = "a")
    WebElement showEmployeesLink;

    @FindBy(css = "button")
    WebElement submitButton;

    public MainPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    public static MainPage goTo(){
        Driver.getDriver().get("http://ec2-3-129-218-184.us-east-2.compute.amazonaws.com:300/");
        return new MainPage();
    }

    public void submitInfo(){
        submitButton.click();
    }

    public void enterEmployeeInfo(Employee employee){
        Driver.getDriver().findElement(By.id("name")).sendKeys(employee.getFirstName());
        Driver.getDriver().findElement(By.id("last_name")).sendKeys(employee.getLastName());
        Driver.getDriver().findElement(By.id("extension")).sendKeys(employee.getExtension());
        Driver.getDriver().findElement(By.id("email")).sendKeys(employee.getEmail());
        Driver.getDriver().findElement(By.id("reports_to")).sendKeys("" + employee.getReportsTo());
        Driver.getDriver().findElement(By.id("office_code")).sendKeys(employee.getOfficeCode());
        Driver.getDriver().findElement(By.id("job_title")).sendKeys(employee.getJobTitle());
    }

    public ShowEmployeesPage openShowEmployeesPage(){
        showEmployeesLink.click();
        return new ShowEmployeesPage();
    }
}
