package prac_spring_mvc1.demo.domain.item;

import lombok.Getter;

@Getter
public enum ItemType {
    Book("book"), Food("food"), ETC("etc");
    
    private  final String description;
    
    ItemType(String description) {
        this.description = description;
    }
    
    
}
