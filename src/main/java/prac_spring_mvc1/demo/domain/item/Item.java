package prac_spring_mvc1.demo.domain.item;

import lombok.Data;

//@Getter
//@Setter
@Data
public class Item {
    
    private Long id;
    private String name;
    private Integer price;
    private Integer quantity;
    
    public Item() {
    }
    
    public Item(String name, Integer price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    
    public Item(Long id, String name, Integer price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    
    public void setItem(String name, Integer price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
