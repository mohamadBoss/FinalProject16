import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class main {
    public static void main(String[] args) throws SQLException {


//        Connection_Pool connectionPool = Connection_Pool.createInstance();
//        Connection co = connectionPool.getConnections();
//        Statement stmt = co.createStatement();
//        String a = "Skagen 21";
//        String b = "Stavanger";
//
//        String sqlQuery = "select * from company where company_email = '" + a + "' and company_password = '" + b +"'";
//
//        ResultSet rs = stmt.executeQuery(sqlQuery);
//        while(rs.next()){
//            System.out.println(rs.getInt("company_id"));
//            System.out.println(rs.getString("company_name"));
//            System.out.println(rs.getString("company_email"));
//            System.out.println(rs.getString("company_password"));
//            System.out.println("****************");
//        }
////
//        CustomersDBDAO c = new CustomersDBDAO();
//        Customer companies = c.getOneCustomer(1234567);
//        System.out.println(companies);


//        CompaniesDBDAO c = new CompaniesDBDAO();
//        ArrayList<Coupon> companies = c.getAllcouponsBYcompanyID(545);
//        System.out.println(companies);

//        c.addCustomer(new Customer(12346,"fghff","fghfgh","asdsd","asdsad",null));

        CouponsaDBDAO c = new CouponsaDBDAO();
        c.addCoupon(new Coupon(10015200,10000, Category.Food,"qqqqqq","eeeeee", Date.valueOf("2000-10-17"), Date.valueOf("2000-10-20"),7777,77777,"asdsd"));
//
//        Coupon css = new Coupon(150000,10000,Category.Food,"qqqqqq","eeeeee",new Date(2000,12,10),new Date(2000,12,15),7777,77777,"asdsd");
//        System.out.println(css.getCategory().getId());

//        c.UpdateCoupon(new Coupon(150000,10000,Category.Food,"assssssss","eeeeee",new Date(2000,12,10),new Date(2000,12,15),7777,77777,"asdsd"));


//        CouponsaDBDAO c = new CouponsaDBDAO();
//        c.deleteCouponPurchase(1234567,224415);


    }
}
