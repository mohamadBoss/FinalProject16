import java.sql.SQLException;
import java.util.ArrayList;

public interface CompaniesDAO {
    public boolean isCompanyExists(String Email,String Password) throws SQLException;
    public void addCompany(Company company) throws SQLException;
    public void UpdateCompany(Company company);
    public void DeleteCompany(int companyID) throws SQLException;
    public ArrayList<Company> getAllCompany() throws SQLException;
    public Company getOneCompany(int companyID);


}
