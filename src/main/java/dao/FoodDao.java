package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import entity.Food;
import entity.FoodXML;
import utils.FileUtils;


public class FoodDao {
    private static final String FOOD_FILE_NAME = "food.xml";
    private List<Food> listFoods;
    private long bill;

    public long getBill() {
        return bill;
    }

    public void setBill(long bill) {
        this.bill = bill;
    }

    public FoodDao(){
        this.listFoods = readListFoods();
        if (listFoods == null) {
            listFoods = new ArrayList<Food>();
        }
    }

    public void writeListFood(List<Food> foods){
        FoodXML xml = new FoodXML();
        xml.setFood(foods);
        FileUtils.writeXMLtoFile(FOOD_FILE_NAME, xml);
    }
    
    public void writeFoodBill(long bill){
        FoodXML xml = new FoodXML();
        xml.setBill(bill);
        FileUtils.writeXMLtoFile(FOOD_FILE_NAME, xml);
    }

    public List<Food> readListFoods(){
        List<Food> list = new ArrayList<Food>();
        FoodXML xml = (FoodXML) FileUtils.readXMLFile(FOOD_FILE_NAME, FoodXML.class);
        if (xml != null) {
            list = xml.getFoods();
        }
        return list;
    }

    public void add (Food food){
        int id = 1;
        if (listFoods != null && listFoods.size() > 0) {
            id = listFoods.size() + 1;
        }
        food.setId(id);
        listFoods.add(food);
        writeListFood(listFoods);
    }

    public void edit (Food food){
        int size = listFoods.size();
        for(int i = 0; i < size; i++){
            if (listFoods.get(i).getId() == food.getId()) {
                listFoods.get(i).setName(food.getName());
                listFoods.get(i).setPrice(food.getPrice());
                writeListFood(listFoods);
                break;
            }
        }
    }

    public boolean delete(Food food){
        boolean found = false;
        int size = listFoods.size();
        for(int i = 0; i < size; i++){
            if (listFoods.get(i).getId() == food.getId()) {
                food = listFoods.get(i);
                found = true;
                break;
            }
        }
        if (found) {
            int id = 1;
            listFoods.remove(food);
            size = listFoods.size();
            for(Food i : listFoods){
                i.setId(id++);
            }
            writeListFood(listFoods);
            return true;
        }
        return false;
    }

    public void sortFoodByName() {
        Collections.sort(listFoods, new Comparator<Food>() {
            public int compare(Food client1, Food client2) {
                return client1.getName().compareTo(client2.getName());
            }
        });
    }

    public void sortFoodtByID() {
        Collections.sort(listFoods, new Comparator<Food>() {
            public int compare(Food client1, Food client2) {
                if (client1.getId() > client2.getId()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    public void sortFoodByPrice() {
        Collections.sort(listFoods, new Comparator<Food>() {
            public int compare(Food client1, Food client2) {
                if (client1.getPrice() > client2.getPrice()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    public void sortFoodByQty() {
        Collections.sort(listFoods, new Comparator<Food>() {
            public int compare(Food client1, Food client2) {
                if (client1.getQty() < client2.getQty()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    public void sortFoodByTotal() {
        Collections.sort(listFoods, new Comparator<Food>() {
            public int compare(Food client1, Food client2) {
                if (client1.getQty() * client1.getPrice() < client2.getQty() * client2.getPrice()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    public List<Food> getListFoods() {
        return listFoods;
    }

    public void setListFoods(List<Food> listFoods) {
        this.listFoods = listFoods;
    }
    
}
