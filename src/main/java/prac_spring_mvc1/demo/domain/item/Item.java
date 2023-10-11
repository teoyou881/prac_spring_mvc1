package prac_spring_mvc1.demo.domain.item;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

//@Getter
//@Setter
@Data
public class Item {

	private Long id;

	@NotBlank
	private String name;
	@NotNull
	@Range (min = 1000, max = 1000000)
	private Integer price;

	@NotNull
	@Max (9999)
	private Integer quantity;

	private Boolean open;
	private List<String> regions;
	private ItemType itemType;
	private String deliveryCode;

	public Item () {
	}

	public Item (String name, Integer price, Integer quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
}
