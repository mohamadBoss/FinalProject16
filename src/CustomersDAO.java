import java.util.ArrayList;

public interface CustomersDAO {
    public boolean isCustomerExists(String Email,String Password);
    public void addCustomer(Customer customer);
    public void UpdateCustomer(Customer customer);
    public void DeleteCustomer(int customerID);
    public ArrayList<Customer> getAllCustomer();
    public Customer getOneCustomer(int customerID);

}
