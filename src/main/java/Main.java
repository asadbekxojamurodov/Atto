import controller.AuthController;

import db.DatabaseUtil;
import db.InitDatabase;

public class Main {
    public static void main(String[] args) {
        DatabaseUtil databaseUtil = new DatabaseUtil();
        databaseUtil.createProfileTable();
        databaseUtil.createCardTable();
        databaseUtil.createTerminalTable();
        databaseUtil.createTransactionTable();

        InitDatabase.adminInit();
        InitDatabase.addCompanyCard();

        AuthController authController = new AuthController();
        authController.start();






    }
}