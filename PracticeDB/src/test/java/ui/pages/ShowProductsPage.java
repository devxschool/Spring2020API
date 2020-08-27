package ui.pages;

import models.Employee;
import models.ProductModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.config.Driver;

import java.util.ArrayList;
import java.util.List;

public class ShowProductsPage {

    @FindBy(css = "tbody tr")
    List<WebElement> tableRows;

    public ShowProductsPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    public static ShowProductsPage goTo() {
        Driver.getDriver().get("http://ec2-3-129-218-184.us-east-2.compute.amazonaws.com:300/products");
        return new ShowProductsPage();
    }

    public List<ProductModel> parseEmployees() {
        List<ProductModel> productModels = new ArrayList<>();
        for (WebElement row : tableRows) {
            ProductModel productModel = new ProductModel();
            List<WebElement> columns = row.findElements(By.tagName("td"));
            productModel.setProductId(Integer.parseInt(columns.get(0).getText()));
            productModel.setProductName(columns.get(1).getText());
            productModel.setProductDescription(columns.get(2).getText());
            productModel.setPrice(Double.parseDouble(columns.get(3).getText()));
            productModel.setProductLine(columns.get(4).getText());
            productModels.add(productModel);
        }
        return productModels;
    }
}
