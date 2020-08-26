package stepDefinitions;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import db.DBUtils;
import models.BaseModel;
import models.Employee;
import org.junit.Assert;
import ui.config.Driver;
import ui.pages.MainPage;
import ui.pages.ShowEmployeesPage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreateEmployee<T> {
    MainPage mainPage;
    private ShowEmployeesPage showEmployeesPage;
    private Employee current;
    private Employee recordFromDB;
    private List<Employee> employees;
    private List<Employee> beansRecordsFormDB;
    private ResultSet rs;

    @Given("^User is on the main page$")
    public void user_is_on_the_main_page() {
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
        showEmployeesPage = ShowEmployeesPage.goTo();
    }

    @Then("^User should be able to see an employee record with provided info$")
    public void user_should_be_able_to_see_an_enployee_record_with_provided_info() {
        List<Employee> employees = showEmployeesPage.parseEmployees();
        employees.forEach(System.out::println);
        System.out.println();
        System.out.println(current);
        Assert.assertTrue(employees.contains(current));
    }

    @When("^Employee info is retreived with firstName \"([^\"]*)\" and lastName \"([^\"]*)\"$")
    public void employee_info_is_retreived_with_firstName_and_lastName(String arg1, String arg2) throws SQLException {
        List<String> fieds = new ArrayList<>();
        fieds.add("firstName");
        fieds.add("lastName");
        recordFromDB = Employee.getBy(fieds, arg1, arg2);
    }

    @Then("^The record should be present in the data base$")
    public void the_record_should_be_present_in_the_data_base() {
        Assert.assertEquals(current, recordFromDB);
    }

    @Given("^record is inserted using query$")
    public void record_is_inserted_using_query(DataTable dt) throws Throwable {
        List<Employee> employees = dt.asList(Employee.class);
        current = employees.get(0);
        current.insertIntoDB();
        System.out.println("Inserted record: " + current);
    }

    @When("^records are parsed$")
    public void records_are_parsed() {
        employees = showEmployeesPage.parseEmployees();
        System.out.println("LIST FROM UI: ");
        employees.forEach(System.out::println);
    }

    @Then("^created record should be present in the list$")
    public void created_record_should_be_present_in_the_list() throws Throwable {
        Assert.assertTrue(employees.contains(current));
    }

    @When("^all records retrieved from the table \"([^\"]*)\"$")
    public void all_records_retrieved_from_the_table(String table) throws Throwable {
        rs = DBUtils.query("SELECT * FROM " + table + ";");
    }

    @When("^records are mapped to beans$")
    public void records_are_mapped_to_beans() {
        try {
            beansRecordsFormDB = BaseModel.getProcessor().toBeanList(rs, Employee.class);
            beansRecordsFormDB.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail("Couldn't map ResultSet To Java Objects");
        }
    }

    @Then("^lists should be equal$")
    public void lists_should_be_equal() throws Throwable {
        boolean allPresent = employees.containsAll(beansRecordsFormDB);
        Assert.assertTrue(allPresent);
    }


}
