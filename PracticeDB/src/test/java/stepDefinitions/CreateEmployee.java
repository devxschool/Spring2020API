package stepDefinitions;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.gl.E;
import models.Employee;
import org.junit.Assert;
import ui.config.Driver;
import ui.pages.MainPage;
import ui.pages.ShowEmployeesPage;

import java.sql.SQLOutput;
import java.util.List;

public class CreateEmployee {
    MainPage mainPage;
    private ShowEmployeesPage showEmployeesPage;
    private Employee current;

    @Given("^User is on the main page$")
    public void user_is_on_the_main_page() throws Throwable {
        mainPage = MainPage.goTo();
    }

    @When("^user fills up the form$")
    public void user_fills_up_the_form(DataTable dt) {
        List<Employee> employees = dt.asList(Employee.class);
        current = employees.get(0);
        mainPage.enterEmployeeInfo(current);
    }

    @When("^usr submits the form$")
    public void usr_submits_the_form() {
        mainPage.submitInfo();
        Assert.assertEquals(Driver.getDriver().getTitle(), "Data Saved");
    }

    @When("^User navigates to show Employees page$")
    public void user_navigates_to_show_Employees_page() {
        Driver.getDriver().navigate().back();
        showEmployeesPage = mainPage.openShowEmployeesPage();
    }

    @Then("^User should be able to see an employee record with provided info$")
    public void user_should_be_able_to_see_an_enployee_record_with_provided_info() {
        List<Employee> employees = showEmployeesPage.parseEmployees();
        employees.forEach(System.out::println);
        System.out.println();
        System.out.println(current);
        Assert.assertTrue(employees.contains(current));
    }
}
