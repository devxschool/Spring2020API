package models;

import db.DBUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductModel extends BaseModel {
    int productId;
    String productName;
    String productDescription;
    double price;
    String productLine;

    private static final String getAllQ = "" +
            "SELECT id as productId, productName, productDescription,\n" +
            " price, productLineName as productLine\n" +
            "FROM product\n" +
            "JOIN productLine\n" +
            "ON productLine = productLineId";

    ProductModel(ResultSet rs) throws SQLException {
        getProcessor().populateBean(rs, this);
    }

    public static List<ProductModel> getAll() throws SQLException {
        ResultSet rs = DBUtils.query(getAllQ);
        return getProcessor().toBeanList(rs, ProductModel.class);
    }

}
