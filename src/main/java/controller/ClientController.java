package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dao.ClientDao;
import dao.FoodDao;
import entity.Client;
import entity.Food;
import utils.DateUtils;
import view.mainFrame;

public class ClientController {
    private ClientDao clientDao;
    private mainFrame view;
    NumberFormat numFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public ClientController(mainFrame view) {
        this.view = view;
        clientDao = new ClientDao();

        view.addAddClientListener(new AddClientListener());
        view.addEditClientListener(new EditClientListener());
        view.addDeleteClientListener(new DeleteClientListener());
        view.addClearListener(new ClearClientListener());
        view.addSortClientListener(new SortClientListener());
        view.addListClientSelectionListener(new ListClientSelectionListener());
        view.addSearchListener(new SearchNameListener());
        view.addBalanceSearchListener(new SearchBalanceListener());
        view.addChargeClientListener(new ChargeClientListener());
        view.addStatListener(new StatClientListener());
        view.addCltBackListener(new BackListener());
    }

    public void showClientView() {
        List<Client> clientList = clientDao.getListClients();
        view.setVisible(true);
        view.showListClients(clientList);
    }

    class AddClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Client client = view.getClientInfo();
            if (client != null) {
                clientDao.add(client);
                view.showClient(client);
                view.showListClients(clientDao.getListClients());
                view.showMessage("Đã thêm");
                view.clearClientInfo();
            }
        }

    }

    class EditClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Client client = view.getClientInfo();
            if (client != null) {
                clientDao.edit(client);
                view.showClient(client);
                view.showListClients(clientDao.getListClients());
                view.showMessage("Đã cập nhật");
                view.clearClientInfo();
            }
        }

    }

    class DeleteClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Client client = view.getClientInfo();
            if (client != null) {
                clientDao.delete(client);
                view.clearClientInfo();
                view.showListClients(clientDao.getListClients());
                view.showMessage("Đã xóa");
                view.clearClientInfo();
            }
        }
    }

    class ClearClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.clearClientInfo();
        }

    }

    class SortClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (view.getIndexSortCombobox()) {
                case 0:
                    clientDao.sortClientByID();
                    view.showListClients(clientDao.getListClients());
                    break;
                case 1:
                    clientDao.sortClientByName();
                    view.showListClients(clientDao.getListClients());
                    break;
                case 2:
                    clientDao.sortClientByBalance();
                    view.showListClients(clientDao.getListClients());
                    break;
                case 3:
                    clientDao.sortClientByTotal();
                    view.showListClients(clientDao.getListClients());
                    break;
                case 4:
                    view.showListClients(clientDao.sortClientNam());
                    break;
                case 5:
                    view.showListClients(clientDao.sortClientNu());
                    break;
                case 6:
                    view.showListClients(clientDao.sortClientKhac());
                    break;
            }
        }

    }

    class SearchNameListener implements ActionListener {
        private List<Client> clientList = clientDao.getListClients();

        @Override
        public void actionPerformed(ActionEvent e) {
            String searchField = view.getNameSearchField();
            List<Client> searchResults = new ArrayList<Client>();
            boolean isFound = false;

            for (Client client : clientList) {
                if (client.getName().contains(searchField)) {
                    searchResults.add(client);
                    isFound = true;
                }
            }
            if (isFound) {
                view.showListClients(searchResults);
            } else {
                view.showMessage("Không tìm thấy");
            }

        }
    }

    class SearchBalanceListener implements ActionListener {
        private List<Client> clientList = clientDao.getListClients();

        @Override
        public void actionPerformed(ActionEvent e) {
            double balanceSearchField1 = view.getFromBalanceSearchField();
            double balanceSearchField2 = view.getToBalanceSearchField();
            List<Client> results = new ArrayList<Client>();
            boolean isFound = false;

            for (Client client : clientList) {
                if (client.getBalance() >= balanceSearchField1 && client.getBalance() <= balanceSearchField2) {
                    results.add(client);
                    isFound = true;
                }
            }
            if (isFound) {
                view.showListClients(results);
            } else {
                view.showMessage("Không tìm thấy");
            }
        }

    }

    class ListClientSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            view.fillClientfromSelectedRow();
        }
    }

    class ChargeClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Client client = view.getClientInfo();
            if (client != null) {
                clientDao.charge(client);
                view.fillClientfromSelectedRow();
                view.showListClients(clientDao.getListClients());
                view.showMessage("Đã nạp");
            }
        }
    }

    class StatClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Client> list = clientDao.getListClients();
            List<Food> listFood = (new FoodDao()).getListFoods();
            String text1 = String.format("Tổng số khách hàng hiện tại là: %d \n", list.size());
            double revenue = 0;
            double revenueFood = 0;
            for (int i = 0; i < list.size() - 1; i++) {
                revenue += list.get(i).getTotal();
            }
            for (int i = 0; i < listFood.size() - 1; i++) {
                revenueFood += listFood.get(i).getQty() * listFood.get(i).getPrice();
            }
            String text5 = String.format("Tổng doanh thu quán đến %s là: %s \n",
                    DateUtils.getTime(System.currentTimeMillis()), numFormat.format(revenue + revenueFood));

            String text2 = String.format("Doanh thu từ phòng máy là: %s \n", numFormat.format(revenue));
            String text3 = String.format("Doanh thu từ dịch vụ là: %s \n", numFormat.format(revenueFood));
            String text4 = "";

            if (list.size() > 5) {
                text4 = "Top 5 khách hàng nạp nhiều nhất:\n";
                clientDao.sortClientByTotal();
                for (int i = 0; i < 5; i++) {
                    text4 += String.format("%d. %s: %s\n", i + 1, list.get(i).getName(),
                            numFormat.format(list.get(i).getTotal()));
                }
            } else {
                text4 = String.format("Top %d khách hàng nạp nhiều nhất:\n", list.size());
                clientDao.sortClientByTotal();
                for (int i = 0; i < list.size(); i++) {
                    text4 += String.format("%d. %s: %s\n", i + 1, list.get(i).getName(),
                            numFormat.format(list.get(i).getTotal()));
                }
            }

            view.showMessage(text1 + text5 + text2 + text3 + text4);
        }

    }

    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setBlankCltSearch();
            clientDao.sortClientByID();
            view.showListClients(clientDao.getListClients());
        }
    }
}
