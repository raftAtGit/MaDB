package org.madb.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COUNTRIES")
public class Country {

	@Id 
	@GeneratedValue 
	private Integer id;
	
	@NotNull
    @Size(max=50)
	@Column(unique = true)
    private String name;
    
	
}
