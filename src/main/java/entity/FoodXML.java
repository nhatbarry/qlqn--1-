package entity;

import java.util.List;
 
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "foods")
@XmlAccessorType(XmlAccessType.FIELD)

public class FoodXML {
    private List<Food> Food;
    private long bill;
    

    public long getBill() {
        return bill;
    }

    public void setBill(long bill) {
        this.bill = bill;
    }

    public List<Food> getFoods() {
        return Food;
    }

    public void setFood(List<Food> Food) {
        this.Food = Food;
    }

    
}
