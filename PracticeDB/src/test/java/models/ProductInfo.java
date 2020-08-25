package models;

import db.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ProductInfo extends BaseModel {
    String productName;
    String productScale;
    String productVendor;
    String productDescription;
    double price;
    String productLine;
    String productLineDescription;

    public static final String getAllQuery =
            "SELECT productName, productScale, productVendor,\n" +
                    "productDescription, buyPrice AS price, productLine,\n" +
                    "textDescription AS productLineDescription\n" +
                    "FROM products\n" +
                    "INNER JOIN productlines\n" +
                    "USING(productLine)";

    public ProductInfo() {
    }

    public static List<ProductInfo> getAll() throws SQLException {
        ResultSet rs = DBUtils.query(getAllQuery);// Query and get ResultSet
        List<ProductInfo> productInfos = getProcessor().toBeanList(rs, ProductInfo.class);// Pass the resultSet and the class(Type) of which objects have to be created
        return productInfos;
    }

    public static ProductInfo getByName(String productName) throws SQLException {
        System.out.println("Getting From DB with name: " + productName);
        String query = getAllQuery + "\nWHERE productName = ?;\n";
        ResultSet rs = DBUtils.query(query, productName);
        if(!rs.next()) return null;
        return getProcessor().toBean(rs, ProductInfo.class);
    }

    public static ProductInfo getByNameUsingAPI(String name) throws SQLException {
        /** CODE TO RETREIVE DATA USING API**/
        System.out.println("Getting from API with name: " + name);
        return getByName(name);
    }

    public static void main(String[] args) {
       try{
           DBUtils.open();
           ProductInfo productInfos = getByName("1952 Alpine Renault 1300");
           System.out.println(productInfos);
       }catch (SQLException e){
           e.printStackTrace();
       }finally {
           DBUtils.close();
       }
    }


    @Override
    public String toString() {
        return "ProductInfo{" +
                "productName='" + productName + '\'' +
                ", productScale='" + productScale + '\'' +
                ", productVendor='" + productVendor + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", price=" + price +
                ", productLine='" + productLine + '\'' +
                ", productLineDescription='" + productLineDescription + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInfo that = (ProductInfo) o;
        return Double.compare(that.price, price) == 0 &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(productScale, that.productScale) &&
                Objects.equals(productVendor, that.productVendor) &&
                Objects.equals(productDescription, that.productDescription) &&
                Objects.equals(productLine, that.productLine) &&
                Objects.equals(productLineDescription, that.productLineDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, productScale, productVendor, productDescription, price, productLine, productLineDescription);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductScale() {
        return productScale;
    }

    public void setProductScale(String productScale) {
        this.productScale = productScale;
    }

    public String getProductVendor() {
        return productVendor;
    }

    public void setProductVendor(String productVendor) {
        this.productVendor = productVendor;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getProductLineDescription() {
        return productLineDescription;
    }

    public void setProductLineDescription(String productLineDescription) {
        this.productLineDescription = productLineDescription;
    }
}
