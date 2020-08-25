package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import db.DBUtils;
import models.ProductInfo;
import org.junit.Assert;

import java.sql.SQLException;

public class ProductInfoDef {
    ProductInfo infoFromAPI;
    ProductInfo infoFromDB;

    @Given("^Product Info with \"([^\"]*)\" retreived using API$")
    public void product_Info_with_Name_retreived_using_API(String name)  {
       try{
           infoFromAPI = ProductInfo.getByNameUsingAPI(name);
       }catch (SQLException e){
           Assert.fail("Product with name: " + name + " doesn't exist");
       }
    }

    @Given("^Product Info with \"([^\"]*)\" retreived from DB$")
    public void product_Info_with_Name_reteived_from_DB(String name)  {
       try {
           infoFromDB = ProductInfo.getByName(name);
       }catch (SQLException e){
           Assert.fail("Product with name: " + name + " doesn't exist in Data Base");
       }
    }

    @Then("^Objects should be equal$")
    public void objects_should_be_equal()  {
        Assert.assertEquals(infoFromDB, infoFromAPI);
    }

    @Given("^DB connection is open$")
    public void db_connection_is_open()  {
        try{
            DBUtils.open();
        }catch (SQLException e){
            Assert.fail(e.getMessage());
        }
    }

    @Then("^connection is closed$")
    public void connection_is_closed()  {
        DBUtils.close();
    }
}
