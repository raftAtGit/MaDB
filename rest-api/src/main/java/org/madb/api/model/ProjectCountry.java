package org.madb.api.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author raft
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PROJECT_COUNTRIES")
public class ProjectCountry {
    @Id 
    @GeneratedValue 
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PROJECT_ID")
    @JsonIgnore
    private Project project;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;
}
