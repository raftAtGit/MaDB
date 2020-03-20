package org.madb.api.config;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.madb.api.jpa.BudgetRepository;
import org.madb.api.jpa.ContactRepository;
import org.madb.api.jpa.ProjectRepository;
import org.madb.api.model.Budget;
import org.madb.api.model.Contact;
import org.madb.api.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@ConditionalOnProperty("madb.db.createInitialRecords")
@Slf4j
class DataInitializer {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired 
	private BudgetRepository budgetRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Bean
	CommandLineRunner createInitialRecords() {
	    return args -> {
	    	log.info("creating initial database records");
	    	
	    	Project project = new Project(null, Project.Status.PENDING, "B1", "Burkina Faso", "Promoting Quality Education in South West regions in Burkina Faso  (EQUIP)", 
	    			date("01/01/2017"), date("31/12/2021"), new BigDecimal(1280925), "Cool project", "Partial YEE");
	    	log.info("Preloading " + projectRepository.save(project));
	    	log.info("Preloading " + projectRepository.save(new Project(null, Project.Status.APPROVED, "E1", "Egypt", "Pioneers for the Future: Contributing to a more inclusive work environment for young women in Egypt", 
	    			date("01/01/2014"), date("31/12/2021"), new BigDecimal(833939), 
	    			"The project aims to enhance economic participation of young women and men aged 18-35, from marginalized areas of Cairo, Alexandria and Assuit in Egypt, and promote equal economic opportunities and decision making for young women. If youth have access to employability, entrepreneurship, relevant technical and innovative skills to access either wage or self-employment opportunities; and key stakeholders have the capacity and understanding to promote gender supportive work and business environment, then those youth will earn income and develop confidence, improving their long-term capabilities and economic status.", 
	    			null)));
	    	
	    	log.info("Preloading " + budgetRepository.save(new Budget(null, project, "2019-2020", new BigDecimal(100000))));

	    	log.info("Preloading " + contactRepository.save(new Contact(null, project, "Primary", "Paul", "Brown", "Account Manager")));
	    	log.info("Preloading " + contactRepository.save(new Contact(null, project, "Secondary", "Bob", "Smith", "Junior Account Manager")));
	    };
	  }	

	static Date now() {
		return new Date(System.currentTimeMillis());
	}
	
	static Date date(String s) throws Exception {
		return new Date(new SimpleDateFormat("dd/mm/yyyy").parse(s).getTime());
	}
	
}
