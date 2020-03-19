package org.madb.api.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PROJECTS")
public class Project {
	
	public enum Status { PENDING, APPROVED, REJECTED };
	
	@Id 
	@GeneratedValue 
	private Integer id;
	
	@NotNull
	private Status status = Status.PENDING; 
	
	@NotNull
	@NotEmpty
	@Column(name = "PROJECT_ID", unique = true)
	private String projectId;
	
	@NotNull
	private String country;
	
	@NotNull
	@Size(max = 100)
	@Column(name = "NAME_OF_PROJECT")
	private String name;
	
	@NotNull
	@Column(name = "PROJECT_START_DATE")
	private Date startDate;
	
	@NotNull
	@Column(name = "PROJECT_END_DATE")
	private Date endDate;
	
	@NotNull
	@Column(name = "PROJECT_BUDGET")
	private BigDecimal budget;
	
	@NotNull
	@Size(max = 1024)
	@Column(name = "SUMMARY_DESCRIPTION")
	private String description;
	
	@Size(max = 100)
	@Column(name = "OBSERVATIONS")
	private String observations;
	

}
