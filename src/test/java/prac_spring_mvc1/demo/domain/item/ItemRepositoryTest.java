package prac_spring_mvc1.demo.domain.item;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ItemRepositoryTest {
    
    ItemRepository itemRepository = new ItemRepository();
    
    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }
    
    @Test
    void save() throws Exception {
        //given
        Item item = new Item("itemA", 10000, 10);
        //when
        Item savedItem = itemRepository.save(item);
        //then
        Item findItem = itemRepository.findById(savedItem.getId());
        Assertions.assertThat(findItem).isEqualTo(savedItem);
    }
    
    @Test
    void findAll() throws Exception {
        //given
        Item item1 = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemB", 20000, 20);
        itemRepository.save(item1);
        itemRepository.save(item2);
        //when
        List<Item> result = itemRepository.findAll();
        //then
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).contains(item1, item2);
        
    }
    
    @Test
    void updateItem() throws Exception {
        //given
        Item item1 = new Item("itemA", 10000, 10);
        Item savedItem = itemRepository.save(item1);
        Long id = savedItem.getId();
        //when
        Item updateParam = new Item("itemB", 20000, 20);
        itemRepository.update(id, updateParam);
        
        //then
        Item findItem = itemRepository.findById(id);
        
        Assertions.assertThat(findItem.getName()).isEqualTo(updateParam.getName());
        Assertions.assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        Assertions.assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }
}