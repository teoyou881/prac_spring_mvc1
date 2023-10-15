package prac_spring_mvc1.demo.web.item.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import prac_spring_mvc1.demo.domain.item.ItemType;

@Data
public class ItemUpdateForm {

	@NotNull
	private Long id;
	@NotBlank
	private String name;
	@NotNull
	@Range (min = 1000, max = 1000000)
	private Integer price;
	// in edit form, user can set quantity freely
	private Integer quantity;

	private Boolean open;
	private List<String> regions;
	private ItemType itemType;
	private String deliveryCode;
}
