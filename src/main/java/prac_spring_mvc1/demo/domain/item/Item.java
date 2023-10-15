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
// not recommended
// @ScriptAssert(lang = "javascript", script = "_this.price * _this.quantity >= 10000")
public class Item {

	@NotNull (groups = UpdateCheck.class)
	private Long id;

	@NotBlank (groups = {SaveCheck.class, UpdateCheck.class})
	private String name;
	@NotNull (groups = {SaveCheck.class, UpdateCheck.class})
	@Range (min = 1000, max = 1000000, groups = {SaveCheck.class, UpdateCheck.class})
	private Integer price;

	@NotNull (groups = {SaveCheck.class, UpdateCheck.class})
	@Max (value = 9999, groups = {SaveCheck.class, UpdateCheck.class})
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
