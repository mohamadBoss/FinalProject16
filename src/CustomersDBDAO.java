import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomersDBDAO implements CustomersDAO {

    private Connection_Pool connectionPool = Connection_Pool.createInstance();
    @Override
    public boolean isCustomerExists(String Email, String Password) {
        try {
            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            String sqlQuery = "select * from customers where customer_email = '" + Email + "' and customer_password = '" + Password + "'";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            if (rs.next()) {
                rs.close();
                stmt.close();
                co.close();
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        try {
            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            if (isCustomerExists(customer.getEmail(), customer.getPassword())) {
                System.out.println("customer Already Exist");
                return;
            }
            String sqlQuery = " INSERT INTO customers(customer_id,customer_first_name,customer_last_name,customer_email,customer_password) " +
                    "VALUES ('" + customer.getId() + "','" + customer.getFirstName() + "','" + customer.getLastName() + "','"
                    + customer.getEmail() + "','" + customer.getPassword() + "')";
            stmt.executeUpdate(sqlQuery);
            stmt.close();
            co.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void UpdateCustomer(Customer customer) {
        try {
            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            stmt.executeUpdate("update customers set customer_first_name='" + customer.getFirstName() +
                    "', customer_last_name = '" + customer.getLastName() + "',customer_email ='" + customer.getEmail() + "',customer_password = '" + customer.getPassword() + "'where customer_id ='" + customer.getId() + "'");
            stmt.close();
            co.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void DeleteCustomer(int customerID) {
        try {
            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            stmt.executeUpdate("delete from customers where customer_id = '" + customerID + "'");
            stmt.close();
            co.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ArrayList<Customer> getAllCustomer() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        try {
            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            String sqlQuary = "SELECT customers.customer_id,customers.customer_first_name,customers.customer_last_name,customers.customer_email,customers.customer_password, coupons.coupon_id, coupons.company_id, coupons.categories_id, coupons.coupon_title, coupons.coupon_description, coupons.coupon_start_date, coupons.coupon_end_date, coupons.coupon_amount, coupons.coupon_price, coupons.coupon_image FROM customers INNER JOIN customers_vs_coupons ON customers.customer_id = customers_vs_coupons.customer_id INNER JOIN coupons ON customers_vs_coupons.coupon_id = coupons.coupon_id";
            ResultSet rs = stmt.executeQuery(sqlQuary);
            while (rs.next()) {
                ArrayList<Coupon> coupons = new ArrayList<Coupon>();
                int id = rs.getInt("customer_id");
                Coupon Co = new Coupon(rs.getInt("coupon_id"),
                        rs.getInt("company_id"), Category.valueOf(rs.getInt("categories_id")), rs.getString("coupon_title"),
                        rs.getString("coupon_description"), rs.getDate("coupon_start_date"),
                        rs.getDate("coupon_end_date"), rs.getInt("coupon_amount"),
                        rs.getInt("coupon_price"), rs.getString("coupon_image"));
                if (customers.stream().anyMatch(customer -> customer.getId() == id)) {
                    customers.stream().filter(c -> c.getId() == id).forEach(c -> c.getCoupons().add(Co));
                } else {
                    coupons.add(Co);
                    customers.add(new Customer(id, rs.getString("customer_first_name"),
                            rs.getString("customer_last_name"), rs.getString("customer_email"),
                            rs.getString("customer_password"), coupons));
                }
            }
            stmt.close();
            co.close();
            return customers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //// לתקן את השאילתא
    @Override
    public Customer getOneCustomer(int customerID) {
        Customer customer = null;
        try {
            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            String sqlQuary = "SELECT customers.customer_id,customers.customer_first_name,customers.customer_last_name,customers.customer_email,customers.customer_password, coupons.coupon_id, coupons.company_id, coupons.categories_id, coupons.coupon_title, coupons.coupon_description, coupons.coupon_start_date, coupons.coupon_end_date, coupons.coupon_amount, coupons.coupon_price, coupons.coupon_image FROM customers INNER JOIN customers_vs_coupons ON customers.customer_id = '" + customerID + "' INNER JOIN coupons ON customers_vs_coupons.coupon_id = coupons.coupon_id";
            ResultSet rs = stmt.executeQuery(sqlQuary);
            while (rs.next()) {
                ArrayList<Coupon> coupons = new ArrayList<Coupon>();
                Coupon Co = new Coupon(rs.getInt("coupon_id"),
                        rs.getInt("company_id"), Category.valueOf(rs.getInt("categories_id")), rs.getString("coupon_title"),
                        rs.getString("coupon_description"), rs.getDate("coupon_start_date"),
                        rs.getDate("coupon_end_date"), rs.getInt("coupon_amount"),
                        rs.getInt("coupon_price"), rs.getString("coupon_image"));

                coupons.add(Co);
                customer = new Customer(customerID, rs.getString("customer_first_name"),
                        rs.getString("customer_last_name"), rs.getString("customer_email"),
                        rs.getString("customer_password"), coupons);
            }
            stmt.close();
            co.close();
            return customer;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
