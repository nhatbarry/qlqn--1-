package com.mycompany.qlqn;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import controller.LoginController;
import view.LoginView;

public class App {
    public static void main(String[] args) {
        try {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        UIManager.setLookAndFeel(new FlatMacLightLaf());
                    } catch (UnsupportedLookAndFeelException e) {
                        e.printStackTrace();
                    }
                    LoginView view = new LoginView();
                    LoginController controller = new LoginController(view);
                    controller.showLoginView();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
