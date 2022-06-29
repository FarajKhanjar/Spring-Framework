package ajbc.learn.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name="Products")
public class Product 
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer productId;
	private String productName;

	@Column(insertable = false, updatable = false)
	private Integer supplierId;
	@ManyToOne
	@JoinColumn(name="supplierId")
	private Supplier supplier;
	
	@Column(insertable = false, updatable = false)
	private Integer categoryId;
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;
	
	
	
	private String quantityPerUnit;
	private Double unitPrice;
	private Integer unitsInStock;
	private Integer unitsOnOrder;
	private Integer reorderLevel;
	private Integer discontinued;
	
	public Product(String productName, Integer supplierId, Integer categoryId, String quantityPerUnit, Double unitPrice,
			Integer unitsInStock, Integer unitsOnOrder, Integer reorderLevel, Integer discontinued) {
		this.productName = productName;
		this.supplierId = supplierId;
		this.categoryId = categoryId;
		this.quantityPerUnit = quantityPerUnit;
		this.unitPrice = unitPrice;
		this.unitsInStock = unitsInStock;
		this.unitsOnOrder = unitsOnOrder;
		this.reorderLevel = reorderLevel;
		this.discontinued = discontinued;
	}
}
