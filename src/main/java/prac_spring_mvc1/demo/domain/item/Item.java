package prac_spring_mvc1.demo.domain.item;

import java.util.List;
import lombok.Data;

//@Getter
//@Setter
@Data
public class Item {
    
    private Long id;
    private String name;
    private Integer price;
    private Integer quantity;
    
    private Boolean open;
    private List<String> regions;
    private ItemType itemType;
    private String deliveryCode;
    
    public Item() {
    }
    
    public Item(String name, Integer price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
