package ajbc.learn.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="Suppliers")
public class Supplier 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int supplierId;
	private String companyName;
	private String contactName;
	private String contactTitle;
	private String address;
	private String city;
	private String postalCode;
	private String region;
	private String country;
	private String phone;
	private String fax;
	private String homePage;
	private Integer inActive;

}
