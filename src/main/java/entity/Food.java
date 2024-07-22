package entity;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "food")
@XmlAccessorType(XmlAccessType.FIELD)
public class Food implements Serializable{
    private static final long serialVersionUID = 1L;
    private static long bill;
    private int id;
    private String name;
    private double price;
    private int qty = 0;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQty() {
        return qty;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }
    public static long getBill() {
        return bill;
    }
    public static void setBill(long bill) {
        Food.bill = bill;
    }
    
}