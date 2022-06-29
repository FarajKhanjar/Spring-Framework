package ajbc.learn.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"picture"}) //print without picture.
@Entity
@Table(name="Categories")
public class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int categoryId;
	@Column(name = "categoryName")
	private String catName; //because the name in java is not the same name of the calumn in Sql.
	private String description;
	@JsonIgnore
	private byte[] picture;

}
