package org.madb.api.config;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.madb.api.jpa.BeneficiaryRepository;
import org.madb.api.jpa.BudgetRepository;
import org.madb.api.jpa.ContactRepository;
import org.madb.api.jpa.CountryRepository;
import org.madb.api.jpa.FundingRepository;
import org.madb.api.jpa.PartnershipRepository;
import org.madb.api.jpa.ProjectCountryRepository;
import org.madb.api.jpa.ProjectRepository;
import org.madb.api.jpa.ThemeRepository;
import org.madb.api.model.Beneficiary;
import org.madb.api.model.Budget;
import org.madb.api.model.Contact;
import org.madb.api.model.Country;
import org.madb.api.model.Funding;
import org.madb.api.model.Partnership;
import org.madb.api.model.Project;
import org.madb.api.model.ProjectCountry;
import org.madb.api.model.Theme;
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
	
	static final Integer NO_ID = null;
	
	
	@Autowired
	private ProjectRepository projectRepository;
	
    @Autowired
	private CountryRepository countryRepository;
	
	@Autowired 
	private BudgetRepository budgetRepository;
	
	@Autowired
	private ContactRepository contactRepository;
        
    @Autowired
	private FundingRepository fundingRepository;
        
    @Autowired
	private BeneficiaryRepository beneficiariesRepository;
        
    @Autowired
	private ThemeRepository themeRepository;
        
    @Autowired
    private PartnershipRepository partnershipRepository;
        
    @Autowired
    private ProjectCountryRepository projectCountryRepository;
	
	@Bean
	CommandLineRunner createInitialRecords() {
	    return args -> {
	    	log.info("creating initial database records");
	    	
	    	
	    	for (String country : countries) {
		    	log.info("Preloading " + countryRepository.save(new Country(NO_ID, country)));
	    	}
	    	
	    	Country egypt = countryRepository.findByName("Egypt"); 
	    	Country burkinaFaso = countryRepository.findByName("Burkina Faso"); 
	    	
	    	Project project = new Project(NO_ID, Project.Status.PENDING, "B1", "Promoting Quality Education in South West regions in Burkina Faso  (EQUIP)", 
	    			date("01/01/2017"), date("31/12/2021"), new BigDecimal(1280925), "Cool project", "Partial YEE", "DummyUser1");
	    	log.info("Preloading " + projectRepository.save(project));
	    	log.info("Preloading " + projectRepository.save(new Project(NO_ID, Project.Status.APPROVED, "E1",   
	    			"Pioneers for the Future: Contributing to a more inclusive work environment for young women in Egypt", 
	    			date("01/01/2014"), date("31/12/2021"), new BigDecimal(634848), 
	    			"The project constitutes a unique process of development based on a holistic and systemic approach, in compliance with the SDG's roadmap and UN recommendations on the empowerment of women, including recommendations on the implementation of the Convention on Elimination of All Forms of Discrimination against Women (CEDAW) in Egypt. Through a mainstreaming and transformative approach, gender equality at work can be a key contribution to the full enjoyment of fundamental women's rights. The project is the second phase of the Pioneers for the Future' project funded in Cairo by the Sawiris Foundation. It will build on the successes and lessons learned from the first phase as well as expand it to include national level advocacy on improving the work environment in Egypt, especially for women.", 
	    			null, "DummyUser2")));
	    	log.info("Preloading " + projectRepository.save(new Project(NO_ID, Project.Status.APPROVED, "E2",   
	    			"Towards an inclusive socio-economic empowerment of young women and men in Egypt ", 
	    			date("01/01/2014"), date("31/12/2021"), new BigDecimal(833939), 
	    			" The project aims to enhance economic participation of young women and men aged 18-35, from marginalized areas of Cairo, Alexandria and Assuit in Egypt, and promote equal economic opportunities and decision making for young women. If youth have access to employability, entrepreneurship, relevant technical and innovative skills to access either wage or self-employment opportunities; and key stakeholders have the capacity and understanding to promote gender supportive work and business environment, then those youth will earn income and develop confidence, improving their long-term capabilities and economic status.", 
	    			null, "DummyUser3")));

	    	log.info("Preloading " + projectCountryRepository.save(new ProjectCountry(NO_ID, project, egypt)));
	    	log.info("Preloading " + projectCountryRepository.save(new ProjectCountry(NO_ID, project, burkinaFaso)));
	    	
	    	log.info("Preloading " + budgetRepository.save(new Budget(NO_ID, project, "2019-2020", new BigDecimal(100000), "DummyUser1")));
                
	    	log.info("Preloading " + contactRepository.save(new Contact(NO_ID, project, "Primary", "Paul", "Brown", "Burkina Faso", "paul@nowhere.com", "Account Manager", "DummyUser1")));
	    	log.info("Preloading " + contactRepository.save(new Contact(NO_ID, project, "Secondary", "Bob", "Smith", "Burkina Faso", "bob@somewhere,org", "Junior Account Manager", "DummyUser1")));
	    	
	        log.info("Preloading " + themeRepository.save(new Theme(NO_ID, project, "Digital", "DummyUser1")));
	        log.info("Preloading " + fundingRepository.save(new Funding(NO_ID, project, "Child sponsorship", "DummyUser1")));        
	        log.info("Preloading " + beneficiariesRepository.save(new Beneficiary(NO_ID, project, "Male", "2014", 5, "DummyUser1"))); 
	        log.info("Preloading " + partnershipRepository.save(new Partnership(NO_ID, project, "Private Sector", "French companies in Egypt", "DummyUser1")));
	    };
	}	

	static Date now() {
		return new Date(System.currentTimeMillis());
	}
	
	static Date date(String s) throws Exception {
		return new Date(new SimpleDateFormat("dd/mm/yyyy").parse(s).getTime());
	}
	
	static final String[] countries = {
			"Australia", 
			"Bangladesh", 
			"Belgium", 
			"Benin", 
			"Bolivia", 
			"Brazil", 
			"Burkina Faso", 
			"Cambodia", 
			"Cameroon", 
			"Canada", 
			"Central African Republic", 
			"China", 
			"Colombia", 
			"Denmark", 
			"Dominican Republic", 
			"Ecuador", 
			"Egypt", 
			"El Salvador", 
			"Ethiopia", 
			"Finland", 
			"France", 
			"Germany", 
			"Ghana", 
			"Guatemala", 
			"Guinea", 
			"Guinea-Bissau", 
			"Haiti", 
			"Honduras", 
			"Hong Kong, SAR of China", 
			"India", 
			"Indonesia", 
			"Ireland", 
			"Italy", 
			"Japan", 
			"Jordan", 
			"Kenya", 
			"Korea", 
			"Laos", 
			"Lebanon", 
			"Liberia", 
			"Malawi", 
			"Mali", 
			"Mozambique", 
			"Myanmar", 
			"Nepal", 
			"Netherlands", 
			"New Guinea", 
			"Nicaragua", 
			"Niger", 
			"Nigeria", 
			"Norway", 
			"Pakistan", 
			"Panama", 
			"Papua", 
			"Paraguay", 
			"Peru", 
			"Philippines", 
			"Rwanda", 
			"Senegal", 
			"Sierra Leone", 
			"Solomon Islands", 
			"Somalia", 
			"South Sudan", 
			"Spain", 
			"Sri Lanka", 
			"Sudan", 
			"Sweden", 
			"Switzerland", 
			"Tanzania", 
			"Thailand", 
			"Timor-Leste", 
			"Togo", 
			"Uganda", 
			"UK", 
			"USA", 
			"Vietnam", 
			"Zambia", 
			"Zimbabwe", 
		}; 
}	
