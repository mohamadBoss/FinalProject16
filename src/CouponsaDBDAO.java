import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CouponsaDBDAO implements CouponsDAO {
    private Connection_Pool connectionPool;

    public CouponsaDBDAO() {
        connectionPool = Connection_Pool.createInstance();
    }

    @Override
    public void addCoupon(Coupon coupon) {
        try {

            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            String sqlQuery = "insert into coupons(coupon_id,company_id,categories_id,coupon_title,coupon_description,coupon_start_date,coupon_end_date,coupon_amount,coupon_price,coupon_image)  VALUES ('" + coupon.getId() + "','" +
                    coupon.getCompanyID() + "','" + coupon.getCategory().getId() + "','" + coupon.getTitle() + "','" + coupon.getDescription() + "','" +
                    new java.sql.Date(coupon.getStartDate().getTime()) + "','" + new java.sql.Date(coupon.getEndDate().getTime()) + "','" + coupon.getAmount() + "','" + coupon.getPrice() + "','" + coupon.getImage() + "')";
            stmt.executeUpdate(sqlQuery);
            stmt.close();
            co.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void UpdateCoupon(Coupon coupon) {

        try {
            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            String sqlQuery = "update coupons set categories_id = '" + coupon.getCategory().getId() + "',coupon_title = '" + coupon.getTitle() + "',coupon_description = '" +
                    coupon.getDescription() + "',coupon_start_date = " + new java.sql.Date(coupon.getStartDate().getTime()) + "'," +
                    "coupon_end_date = '" + new java.sql.Date(coupon.getEndDate().getTime()) + "',coupon_amount = '" + coupon.getAmount() + "'," +
                    "coupon_price = '" + coupon.getPrice() + "',coupon_image = '" + coupon.getImage() + "'where coupon_id = '" + coupon.getId() + "'";
            stmt.executeUpdate(sqlQuery);
            stmt.close();
            co.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void DeleteCoupon(int couponID) {
        try {
            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            String sqlQuery = "SET FOREIGN_KEY_CHECKS = 0;";
            stmt.executeUpdate(sqlQuery);
            sqlQuery = "DELETE FROM coupons WHERE coupon_id = '" + couponID + "';";
            stmt.executeUpdate(sqlQuery);
            sqlQuery = "SET FOREIGN_KEY_CHECKS = 1;";
            stmt.executeUpdate(sqlQuery);
            stmt.close();
            co.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Coupon> getAllCoupon() {
        try {
            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM coupons");
            ArrayList<Coupon> CouponsList = new ArrayList<Coupon>();
            while (rs.next()) {
                CouponsList.add(new Coupon(rs.getInt("coupon_id"), rs.getInt("company_id"),
                        Category.valueOf(rs.getInt("categories_id")), rs.getString("coupon_title"),
                        rs.getString("coupon_description"), rs.getDate("coupon_start_date"),
                        rs.getDate("coupon_end_date"), rs.getInt("coupon_amount"), rs.getInt("coupon_price"),
                        rs.getString("coupon_image")));

            }
            rs.close();
            stmt.close();
            co.close();
            return CouponsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Coupon getOneCoupon(int couponID) {
        try {
            Coupon coupons = null;
            Connection co = connectionPool.getConnections();
            String sqlQuery = "select * from coupons where coupon_id= '" + couponID + "'";
            Statement stmt = co.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next())
                coupons = new Coupon(rs.getInt("coupon_id"), rs.getInt("company_id"),
                        Category.valueOf(rs.getInt("categories_id")), rs.getString("coupon_title"),
                        rs.getString("coupon_description"), rs.getDate("coupon_start_date"),
                        rs.getDate("coupon_end_date"), rs.getInt("coupon_amount"), rs.getInt("coupon_price"),
                        rs.getString("coupon_image"));
            rs.close();
            stmt.close();
            co.close();
            return coupons;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addCouponPurchase(int customerID, int couponID) {
        try {
            Connection co = connectionPool.getConnections();
            String sqlQuery = "insert into customers_vs_coupons(customer_id,coupon_id) values('" + customerID + "','" + couponID + "')";
            Statement stmt = co.createStatement();
            stmt.executeUpdate(sqlQuery);
            stmt.close();
            co.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCouponPurchase(int customerID, int couponID) {
        try {
            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            String sqlQuery = "DELETE FROM customers_vs_coupons WHERE customer_id = '" + customerID + "' and coupon_id = '" + couponID + "'";
            stmt.executeUpdate(sqlQuery);
            stmt.close();
            co.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
