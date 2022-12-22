import java.sql.SQLException;
import java.util.ArrayList;

public interface CouponsDAO {
    public void addCoupon(Coupon coupon) throws SQLException;
    public void UpdateCoupon(Coupon coupon);
    public void DeleteCoupon(int couponID);
    public ArrayList<Coupon> getAllCoupon();
    public Coupon getOneCoupon(int couponID);
    public void addCouponPurchase(int customerID, int couponID);
    public void deleteCouponPurchase(int customerID, int couponID);


}
