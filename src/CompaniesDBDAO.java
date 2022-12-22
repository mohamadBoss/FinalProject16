
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CompaniesDBDAO implements CompaniesDAO {
    private Connection_Pool connectionPool = Connection_Pool.createInstance();

    @Override
    public boolean isCompanyExists(String Email, String Password) {
        try {
            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            String sqlQuery = "select * from company where company_email = '" + Email + "' and company_password = '" + Password + "'";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            boolean flag = false;
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
    public void addCompany(Company company) {
        try {
            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            String sqlQuery = "select * from company where company_id = '" + company.getId() + "'";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                System.out.println("Company id Already Exist");
                return;
            }
            sqlQuery = " INSERT INTO company (company_id,company_name,company_email,company_password) VALUES ('" + company.getId() + "','" + company.getName() + "','" + company.getEmail() + "','" + company.getPassword() + "')";
            stmt.executeUpdate(sqlQuery);
            stmt.close();
            co.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void UpdateCompany(Company company) {
        try {
            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            stmt.executeUpdate("update company set company_email ='" + company.getEmail() +
                    "', company_password = '" + company.getPassword() + "'where company_id ='" + company.getId() + "'");
            stmt.close();
            co.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void DeleteCompany(int companyID) {
        try {
            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            stmt.executeUpdate("delete from company where company_id = '" + companyID + "'");
            stmt.close();
            co.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Company> getAllCompany(){
        try {
            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM company");
            ArrayList<Company> companyList = new ArrayList<Company>();
            while (rs.next())
            {
                companyList.add(new Company(rs.getInt("company_id"), rs.getString("company_name"),
                        rs.getString("company_email"), rs.getString("company_password"),
                        getAllcouponsBYcompanyID(rs.getInt("company_id"))));

            }
            rs.close();
            stmt.close();
            co.close();
            return companyList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Company getOneCompany(int companyID) {
        try {
            ArrayList<Coupon> coupons;
            Connection co = connectionPool.getConnections();
            String sqlQuery = "select * from company where company_id= '" + companyID + "'";
            Statement stmt = co.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            coupons = getAllcouponsBYcompanyID(companyID);
            Company company = null;
            while (rs.next())
                company = new Company(rs.getInt("company_id"), rs.getString("company_name"), rs.getString("company_email"), rs.getString("company_password"), coupons);
            rs.close();
            stmt.close();
            co.close();
            Coupon asd =company.getCoupons().get(0);
            System.out.println(asd.getCategory());
            return company;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Coupon> getAllcouponsBYcompanyID(int companyID)
    {
        ArrayList<Coupon> coupons = new ArrayList<>();
        try{
            Connection co = connectionPool.getConnections();
            Statement stmt = co.createStatement();
            String sqlQuery = "select * from coupons where company_id= '" + companyID + "'";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next())
                coupons.add(new Coupon(rs.getInt("coupon_id"), rs.getInt("company_id"), Category.valueOf(rs.getInt("categories_id")),
                        rs.getString("coupon_title"), rs.getString("coupon_description"),
                        rs.getDate("coupon_start_date"), rs.getDate("coupon_end_date"),
                        rs.getInt("coupon_amount"), rs.getDouble("coupon_price"), rs.getString("coupon_image")));
            return coupons;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


