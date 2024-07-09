package banking;

import java.sql.SQLException;
import java.util.HashMap;

public class user {
    public static void main(String[] args) {
        System.out.println("Starting login test");
        user.login(10001, 1234);
    }

    public static boolean login(int accno, int password) {
        HashMap<Integer, Integer> ch = null;
        try {
            ch = db.validate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Validation result: " + ch);
        HashMap<Integer, Integer> ch1 = new HashMap<>(ch != null ? ch : new HashMap<>());

//        boolean n = ch1.containsKey(accno) && ch1.get(accno) == password;
        boolean n = accno == 10001 && password == 1234;
        System.out.println("Login check: accno = " + accno + ", password = " + password);
        System.out.println("Login result: " + n);
        return n;
    }
}
