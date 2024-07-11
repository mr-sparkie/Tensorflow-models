package banking;

import java.sql.SQLException;
import java.util.HashMap;

public class user {
	private static int Accno;
    public static void main(String[] args) {
        System.out.println("Starting login test");
       user.login(10001, 1234);
       user.money();
       user.admin_login(11634, "frozen23");
      
       user.setmoney(1000);
    }

    public static boolean login(int accno, int i) {
        HashMap<Integer, Integer> ch = null;
        float mm = 0 ;
        Accno = accno;
        try {
            ch = db.validate();
          mm = db.transaction(accno);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("validation result: " + ch);
        HashMap<Integer, Integer> ch1 = new HashMap<>(ch != null ? ch : new HashMap<>());

       boolean n = ch1.containsKey(accno) && ch1.get(accno) == i;
//       boolean n = accno == 10001 && password == 1234;
        System.out.println("Login check: accno = " + accno + ", password = " + i);
        System.out.println("Login result: " + mm);
        return n;
    }
    
    public static boolean admin(int accno) {
        HashMap<Integer, String> ch = null;
        Accno = accno;
        try {
            ch = db.adminval();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("validation result: " + ch);

       boolean n = ch.containsKey(accno);
//       boolean n = accno == 10001 && password == 1234;
        return n;
    }  
    public static boolean admin_login(int accno, String password) {
        HashMap<Integer, String> ch = null;
        Accno = accno;
        try {
            ch = db.adminval();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("validation result: " + ch);

       boolean n = ch.containsKey(accno) && ch.get(accno).equals(password);
//       boolean n = accno == 10001 && password == 1234;
        System.out.println("Login check: accno = " + accno + ", password = " + password);
        return n;
    }
    public static int money(){
    	int hh = 0;
    	int s = Accno;
    	try {
			hh = db.transaction(s);
		} catch (SQLException e) {
			System.out.println("error");
			e.printStackTrace();
		}
    	System.out.println("setmoney=" + hh);
		return hh;
    	
    }
    public static int setmoney(int bal) {
        int s = Accno;
        try {
            int oldBalance = db.transaction(s);
            db.roll(s, bal);
            int newBalance = db.transaction(s);
            String transactionType = bal > 0 ? "deposit" : "withdraw";
            double transactionAmount = Math.abs(bal);
            db.insertStatement(s, transactionType, transactionAmount, newBalance);
            return newBalance;
        } catch (SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
            e.printStackTrace();
            return 0; // or handle error accordingly
        }
    }


}
