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

/**
 *
 * @author hirad.emamialagha
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PARTNERSHIPS")

public class Partnerships {
   @Id 
    @GeneratedValue 
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PROJECT_ID")
    @JsonIgnore
    private Project project;
    
    @NotNull
    @Size(max=500)
    @Column(name = "PARTNERSHIP_TYPE")
    private String partnership_type;
    
    @NotNull
    @Size(max=500)
    @Column(name = "PARTNER")
    private String partner;
    
    @NotNull
    @Size(max = 50)
    @Column(name = "USER_NAME")
    private String user;   
}
