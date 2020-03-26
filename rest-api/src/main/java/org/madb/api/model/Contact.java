package org.madb.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CONTACTS")
public class Contact {

    @Id 
    @GeneratedValue 
    private Integer id;
        
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PROJECT_ID")
    @JsonIgnore
    private Project project;
	
    @NotNull
    @Size(max=100)
    @Column(name = "TYPE_OF_CONTACT")
    private String type;
    
    @NotNull
    @Size(max=100)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull
    @Size(max=100)
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotNull
    @Size(max=50)
    private String country;
    
    @NotNull
    @Size(max=100)
    private String email;
    
    @NotNull
    @Size(max=100)
    private String functions;
    
    @NotNull
    @Size(max = 50)
    @Column(name = "USER_NAME")
    private String user;
	
}
