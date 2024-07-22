package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dao.UserDao;
import entity.User;
import view.*;
public class LoginController {
    private UserDao userDao;
    private LoginView loginView;
    private mainFrame clientView;
     
    public LoginController(LoginView view) {
        this.loginView = view;
        this.userDao = new UserDao();
        view.addLoginListener(new LoginListener());
    }
     
    public void showLoginView() {
        loginView.setVisible(true);
    }

    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            User user = loginView.getUser();
            if (userDao.checkUser(user)) {
                // nếu đăng nhập thành công, mở màn hình quản lý sinh viên
                clientView = new mainFrame();
                ClientController clientController = new ClientController(clientView);
                clientController.showClientView();
                ComputerController computerController = new ComputerController(clientView);
                computerController.showComputerView();
                FoodController foodController = new FoodController(clientView);
                foodController.showFoodView();
                loginView.setVisible(false);
            } else {
                loginView.showMessage("username hoặc password không đúng.");
            }
        }
    }
}
