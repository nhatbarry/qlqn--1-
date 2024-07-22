package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dao.ComputerDao;
import dao.FoodDao;
import entity.Client;
import entity.Computer;
import entity.Food;
import utils.DateUtils;
import view.mainFrame;

public class FoodController {
    private FoodDao dao;
    private mainFrame view;
    NumberFormat numFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public FoodController(mainFrame view) {
        this.view = view;
        dao = new FoodDao();

        view.addAddFoodListener(new AddFoodListener());
        view.addEditFoodListener(new EditFoodListener());
        view.addClearFoodListener(new ClearFoodListener());
        view.addDeleteFoodListener(new DeleteFoodListener());
        view.addSortFoodListener(new SortFoodListener());
        view.addListFoodSelectionListener(new ListFoodSelectionListener());
        view.addSearchFoodListener(new SearchFoodNameListener());
        view.addPriceFoodSearchListener(new SearchFoodPriceListener());
        view.addFoodBackListener(new BackListener());
        view.addFoodStatListener(new StatFood());
        showListComCombobox();
    }

    public void showFoodView() {
        List<Food> foodList = dao.readListFoods();
        view.setVisible(true);
        view.showListFoods(foodList);
    }

    public void showListComCombobox() {
        ComputerDao dao = new ComputerDao();
        List<Computer> list = dao.getListComputers();
        view.showListComCombobox(list);
    }

    class AddFoodListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Food food = view.getFoodInfo();
            if (food != null) {
                dao.add(food);
                view.showFood(food);
                view.showListFoods(dao.getListFoods());
                view.showMessage("Đã thêm");
                view.clearFoodInfo();
            }
        }

    }

    class EditFoodListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Food food = view.getFoodInfo();
            if (food != null) {
                dao.edit(food);
                view.showFood(food);
                view.showListFoods(dao.getListFoods());
                view.showMessage("Đã cập nhật");
                view.clearFoodInfo();
            }
        }

    }

    class DeleteFoodListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Food food = view.getFoodInfo();
            if (food != null) {
                dao.delete(food);
                view.clearFoodInfo();
                view.showListFoods(dao.getListFoods());
                view.showMessage("Đã xóa");
                view.clearFoodInfo();
            }
        }
    }

    class ClearFoodListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.clearFoodInfo();
        }
    }

    class SearchFoodNameListener implements ActionListener {
        private List<Food> foodList = dao.getListFoods();

        @Override
        public void actionPerformed(ActionEvent e) {
            String searchField = view.getFoodSearchField();
            List<Food> searchResults = new ArrayList<Food>();
            boolean isFound = false;

            for (Food food : foodList) {
                if (food.getName().contains(searchField)) {
                    searchResults.add(food);
                    isFound = true;
                }
            }
            if (isFound) {
                view.showListFoods(searchResults);
            } else {
                view.showMessage("Không tìm thấy");
            }

        }
    }

    class SearchFoodPriceListener implements ActionListener {
        private List<Food> foodList = dao.getListFoods();

        @Override
        public void actionPerformed(ActionEvent e) {
            double balanceSearchField1 = Double.parseDouble(view.getPriceField1());
            double balanceSearchField2 = Double.parseDouble(view.getPriceField2());
            List<Food> results = new ArrayList<Food>();
            boolean isFound = false;

            for (Food food : foodList) {
                if (food.getPrice() >= balanceSearchField1 && food.getPrice() <= balanceSearchField2) {
                    results.add(food);
                    isFound = true;
                }
            }
            if (isFound) {
                view.showListFoods(results);
            } else {
                view.showMessage("Không tìm thấy");
            }
        }

    }

    class SortFoodListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (view.getIndexFoodSortComCombobox()) {
                case 0:
                    dao.sortFoodtByID();
                    view.showListFoods(dao.getListFoods());
                    break;
                case 1:
                    dao.sortFoodByName();
                    view.showListFoods(dao.getListFoods());
                    break;
                case 2:
                    dao.sortFoodByPrice();
                    view.showListFoods(dao.getListFoods());
                    break;
                case 3:
                    dao.sortFoodByQty();
                    view.showListFoods(dao.getListFoods());
                    break;
            }
        }

    }

    class ListFoodSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            view.fillFoodfromSelectedRow();
        }
    }

    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setBlankFoodSearch();
            dao.sortFoodtByID();
            view.showListFoods(dao.getListFoods());
        }

    }

    class StatFood implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            FoodDao foodDao = new FoodDao();
            List<Food> list = foodDao.getListFoods();
            String text1 = String.format("Tổng số dịch vụ hiện tại là: %d \n", list.size());
            double revenue = 0;
            for (int i = 0; i < list.size() - 1; i++) {
                revenue += list.get(i).getQty() * list.get(i).getPrice();
            }
            String text2 = String.format("Tổng doanh thu đến từ dịch vụ khác tính đến %s là: %s \n",
                    DateUtils.getTime(System.currentTimeMillis()), numFormat.format(revenue));
            String text3 = "";
            if (list.size() > 5) {
                text3 = String.format("Top 5 dịch vụ được dùng nhiều nhất::\n");
                foodDao.sortFoodByTotal();
                for (int i = 0; i < 5; i++) {
                    text3 += String.format("%d. %s: %s\n", i + 1, list.get(i).getName(),
                            numFormat.format(list.get(i).getPrice() * list.get(i).getQty()));
                }
            } else {
                text3 = String.format("Top %d dịch vụ được dùng nhiều nhất::\n", list.size());
                foodDao.sortFoodByTotal();
                for (int i = 0; i < list.size(); i++) {
                    text3 += String.format("%d. %s: %s\n", i + 1, list.get(i).getName(),
                            numFormat.format(list.get(i).getPrice() * list.get(i).getQty()));
                }
            }
            view.showMessage(text1 + text2 + text3);
        }
    }

}
