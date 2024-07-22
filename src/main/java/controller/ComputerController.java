package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dao.ComputerDao;
import dao.ClientDao;
import entity.Computer;
import view.mainFrame;

public class ComputerController {
    private ComputerDao computerDao;
    private mainFrame mainFrame;
    private ClientDao clientDao;

    public ComputerController(mainFrame mainFrame) {
        this.mainFrame = mainFrame;
        computerDao = new ComputerDao();
        clientDao = new ClientDao();

        mainFrame.addAddComListener(new AddComListener());
        mainFrame.addEditComListener(new EditComListener());
        mainFrame.addClearComListener(new ClearComListener());
        mainFrame.addDeleteComListener(new DeleteComListener());
        mainFrame.addListComSelectionListener(new ListComSelectionListener());
        mainFrame.addRentComListener(new RentComListener());
        mainFrame.addReturnComListener(new ReturnComListener());
        mainFrame.addSortComListener(new SortComListener());
        mainFrame.addIdleComListener(new idleCom());
        mainFrame.addUsingComListener(new usingCom());
        mainFrame.addHistoryListener(new history());
    }

    public void showComputerView() {
        List<Computer> computerList = computerDao.getListComputers();
        mainFrame.setVisible(true);
        mainFrame.showListComputers(computerList);
    }

    class AddComListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Computer computer = mainFrame.getComputeInfo();
            if (computer != null) {
                computerDao.add(computer);
                mainFrame.showCom(computer);
                // mainFrame.showListComputers(computerDao.getListComputers());
                mainFrame.showMessage("Đã thêm");
                mainFrame.clearComInfo();
                mainFrame.showListComCombobox(computerDao.getListComputers());
            }
        }

    }

    class EditComListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Computer computer = mainFrame.getComputeInfo();
            if (computer != null) {
                computerDao.edit(computer);
                mainFrame.showCom(computer);
                // mainFrame.showListComputers(computerDao.getListComputers());
                mainFrame.showMessage("Đã cập nhật");
                mainFrame.clearComInfo();
                mainFrame.showListComCombobox(computerDao.getListComputers());
            }
        }

    }

    class DeleteComListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Computer computer = mainFrame.getComputeInfo();
            if (computer != null) {
                computerDao.delete(computer);
                mainFrame.clearComInfo();
                // mainFrame.showListComputers(computerDao.getListComputers());
                mainFrame.showMessage("Đã xóa");
                mainFrame.clearComInfo();
                mainFrame.showListComCombobox(computerDao.getListComputers());
            }
        }
    }

    class RentComListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Computer computer = mainFrame.getComputeInfo();
            if (computer == null) {
                mainFrame.showMessage("Hãy chọn máy để cho thuê trước");
            }
            else{
                if (computerDao.rentCom(computer)) {
                    mainFrame.showMessage("Đã cho thuê máy");
                    mainFrame.showCom(computer);
                } else {
                    mainFrame.showMessage("Không thể cho thuê máy");
                }
                // mainFrame.showListComputers(computerDao.getListComputers());
                mainFrame.showListClients(clientDao.readListClient());
                mainFrame.clearComInfo();
            }
        }

    }

    class ReturnComListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Computer computer = mainFrame.getComputeInfo();
            computerDao.returnCom(computer);
            mainFrame.showMessage("Đã trả máy");
            // mainFrame.showListComputers(computerDao.getListComputers());
            mainFrame.showListClients(clientDao.readListClient());
            mainFrame.clearComInfo();
        }

    }

    class ClearComListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.clearComInfo();
        }
    }

    class ListComSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            mainFrame.fillComputerfromSelectedRow();
        }
    }

    class SortComListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (mainFrame.getIndexSortComCombobox()) {
                case 0:
                    computerDao.sortComByID();
                    break;
                case 1:
                    computerDao.sortComByName();
                    break;
                case 2:
                    computerDao.sortComByPrice();
                    break;

                default:
                    break;
            }
        }

    }

    class idleCom implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.showMessage(computerDao.idleCom());
        }
        
    }

    class usingCom implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.showMessage(computerDao.usingCom());
        }
        
    }

    class history implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.showHistoryCom((new ComputerDao()).getHistory());
        }
        
    }
}
