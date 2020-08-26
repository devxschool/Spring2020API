package stepDefinitions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import db.DBUtils;
import org.junit.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeneralDB {

    @When("^table is truncated \"([^\"]*)\"$")
    public void table_is_truncated(String table) throws SQLException {
        DBUtils.truncate(table);
    }

    @Then("^table should be empty \"([^\"]*)\"$")
    public void table_should_be_empty(String table) throws SQLException {
        ResultSet rs = DBUtils.query("SELECT * FROM " + table);
        Assert.assertFalse(rs.next());
    }
}
