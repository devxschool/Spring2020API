package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import models.ProductModel;
import org.junit.Assert;
import ui.pages.ShowProductsPage;

import java.sql.SQLException;
import java.util.List;

public class ShowProducts {
    ShowProductsPage showProductsPage;
    List<ProductModel> valuesFormUI;
    List<ProductModel> valuesFromDB;

    @Given("^user is on display products page$")
    public void user_is_on_display_products_page() {
        showProductsPage = ShowProductsPage.goTo();
    }

    @When("^values are parsed$")
    public void values_are_parsed() {
        valuesFormUI = showProductsPage.parseEmployees();
    }

    @When("^product records are retrieved$")
    public void product_records_are_retrieved()  {
        try{
            valuesFromDB = ProductModel.getAll();
        }catch (SQLException e){
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Then("^Values should be equal$")
    public void values_should_be_equal()  {
        System.out.println(valuesFormUI);
        System.out.println();
        System.out.println(valuesFromDB);
       boolean uiContainsAllFromDB =  valuesFormUI.containsAll(valuesFromDB);
       boolean dbContainsAllFromDB = valuesFromDB.containsAll(valuesFormUI);
       Assert.assertTrue(uiContainsAllFromDB && dbContainsAllFromDB);
    }



}
