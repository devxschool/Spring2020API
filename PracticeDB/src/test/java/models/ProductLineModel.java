package models;

import com.github.javafaker.Faker;
import db.DBUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductLineModel extends BaseModel {
    int productLineId;
    String productLineDescription;
    public static final String[] properties = {"productLineId", "productLineDescription"};

    public ProductLineModel(ResultSet rs) throws SQLException {
        getProcessor().populateBean(rs, this);
    }

    public static List<ProductLineModel> getAll() throws SQLException {
        ResultSet rs = DBUtils.query("SELECT * FROM productLine");
        return getProcessor().toBeanList(rs, ProductLineModel.class);
    }

    public static ProductLineModel getById(int id) throws SQLException {
        String query = "SELECT * FROM productLine WHERE id = ?;";
        ResultSet rs = DBUtils.query(query, id);
        return getProcessor().toBean(rs, ProductLineModel.class);
    }

    public void insertIntoDB() throws SQLException {
        String q = "INSERT INTO productLine VALUES(?, ?);";
        DBUtils.insertBean(q, this, properties);
    }

    public static void main(String[] args) throws SQLException {
        int i = 5;
        try {
            DBUtils.open("test");
            while (i-- > 0) {
                ProductLineModel model = new ProductLineModel();
                model.setProductLineDescription(Faker.instance().company().name());
                try {
                    model.insertIntoDB();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            DBUtils.close();
        }
    }
}
