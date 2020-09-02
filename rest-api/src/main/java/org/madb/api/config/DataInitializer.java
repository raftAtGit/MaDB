package org.madb.api.config;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import lombok.extern.slf4j.Slf4j;

/** Convenience class to create some initial records in the database. This class runs only if the system property 
 * <code>madb.db.createInitialRecords</code> is defined and its value is not <code>false</code>. */
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
	private BeneficiaryRepository beneficiaryRepository;
        
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
	    	
	    	preloadCountries();
	    	
	    	preloadProjects();
	    	preloadProjectCountries();
	    	preloadThemes();
	    	preloadPartnerships();
	    	preloadFundings();
	    	preloadContacts();
	    	preloadBeneficaries();
	    	preloadBudgets();
	    };
	}	

	private void preloadCountries() {
    	for (String country : countries) {
    		log.debug("Preloading " + countryRepository.save(new Country(NO_ID, country)));
    	}
    	log.info("Preloaded {} countries", countries.length);
	}
	
	private void preloadProjects() throws Exception {
    	List<Map<String, String>> list = loadCsv("/data/projects.csv");
    	
    	for (Map<String, String> map : list) {
    		Project project = new Project();
    		
    		project.setStatus(Project.Status.APPROVED);
    		project.setProjectId(map.get("project_id"));
    		project.setName(map.get("name_of_project"));
    		project.setStartDate(date(map.get("project_start_date"), "dd/MM/yyyy"));
    		project.setEndDate(date(map.get("project_end_date"), "dd-MM-yyyy"));
    		project.setBudget(BigDecimal.valueOf(Long.parseLong(map.get("project_budget").trim())));
    		project.setSummary(map.get("summary"));
    		project.setComments(map.get("comments"));
    		project.setUser(map.get("user_name"));

    		log.debug("Preloading " + projectRepository.save(project));
    	}
    	log.info("Preloaded {} projects", list.size());
	}

	private void preloadProjectCountries() throws Exception {
    	List<Map<String, String>> list = loadCsv("/data/project_countries.csv");
    	
    	for (Map<String, String> map : list) {
    		ProjectCountry projectCountry = new ProjectCountry();
    		
    		projectCountry.setProject(projectRepository.findByProjectId(map.get("project_id")).get());
    		projectCountry.setCountry(countryRepository.findById(Integer.parseInt(map.get("country_id"))).get());
    		projectCountry.setUser(map.get("user_name"));

    		log.debug("Preloading " + projectCountryRepository.save(projectCountry));
    	}
    	log.info("Preloaded {} projects countries", list.size());
	}

	private void preloadThemes() throws Exception {
    	List<Map<String, String>> list = loadCsv("/data/themes.csv");
    	
    	for (Map<String, String> map : list) {
    		Theme theme = new Theme();
    		
    		theme.setProject(projectRepository.findByProjectId(map.get("project_id")).get());
    		theme.setTheme(map.get("theme"));
    		theme.setUser(map.get("user_name"));

    		log.debug("Preloading " + themeRepository.save(theme));
    	}
    	log.info("Preloaded {} themes", list.size());
	}
	
	private void preloadPartnerships() throws Exception {
    	List<Map<String, String>> list = loadCsv("/data/partnerships.csv");
    	
    	for (Map<String, String> map : list) {
    		Partnership partnership = new Partnership();
    		
    		partnership.setProject(projectRepository.findByProjectId(map.get("project_id")).get());
    		partnership.setPartner(map.get("partner"));
    		partnership.setPartnershipType(map.get("partnerschip_type"));
    		partnership.setUser(map.get("user_name"));

    		log.debug("Preloading " + partnershipRepository.save(partnership));
    	}
    	log.info("Preloaded {} partnerships", list.size());
	}
	
	private void preloadFundings() throws Exception {
    	List<Map<String, String>> list = loadCsv("/data/fundings.csv");
    	
    	for (Map<String, String> map : list) {
    		Funding funding = new Funding();
    		
    		funding.setProject(projectRepository.findByProjectId(map.get("project_id")).get());
    		funding.setFundingSource(map.get("funding_source"));
    		funding.setUser(map.get("user_name"));

    		log.debug("Preloading " + fundingRepository.save(funding));
    	}
    	log.info("Preloaded {} fundings", list.size());
	}
	
	private void preloadContacts() throws Exception {
    	List<Map<String, String>> list = loadCsv("/data/contacts.csv");
    	
    	for (Map<String, String> map : list) {
    		Contact contact = new Contact();
    		
    		contact.setProject(projectRepository.findByProjectId(map.get("project_id")).get());
    		contact.setType(map.get("type_of_contact"));
    		contact.setFirstName(map.get("first_name"));
    		contact.setLastName(map.get("last_name"));
    		contact.setCountry(map.get("country"));
    		contact.setEmail(map.get("email"));
    		contact.setFunctions(map.get("functions"));
    		contact.setUser(map.get("user_name"));

    		log.debug("Preloading " + contactRepository.save(contact));
    	}
    	log.info("Preloaded {} contacts", list.size());
	}
	
	private void preloadBeneficaries() throws Exception {
    	List<Map<String, String>> list = loadCsv("/data/beneficaries.csv");
    	
    	for (Map<String, String> map : list) {
    		Beneficiary beneficiary = new Beneficiary();
    		
    		beneficiary.setProject(projectRepository.findByProjectId(map.get("project_id")).get());
    		beneficiary.setFinancialYear(map.get("financial_year"));
    		beneficiary.setGender(map.get("gender"));
    		beneficiary.setNumberOfBeneficiaries(Integer.parseInt(map.get("number_of_beneficiaries")));
    		beneficiary.setUser(map.get("user_name"));

    		log.debug("Preloading " + beneficiaryRepository.save(beneficiary));
    	}
    	log.info("Preloaded {} beneficaries", list.size());
	}
	
	private void preloadBudgets() throws Exception {
    	List<Map<String, String>> list = loadCsv("/data/budgets.csv");
    	
    	for (Map<String, String> map : list) {
    		Budget budget = new Budget();
    		
    		budget.setProject(projectRepository.findByProjectId(map.get("project_id")).get());
    		budget.setFinancialYear(map.get("financial_year"));
    		budget.setBudget(BigDecimal.valueOf(Long.parseLong(map.get("budget").trim())));
    		budget.setUser(map.get("user_name"));

    		log.debug("Preloading " + budgetRepository.save(budget));
    	}
    	log.info("Preloaded {} budgets", list.size());
	}
	
	private List<Map<String, String>> loadCsv(String fileName) throws Exception {
        CsvSchema bootstrapSchema = CsvSchema.emptySchema()
        		.withHeader()
        		.withColumnSeparator(';');
        
        CsvMapper mapper = new CsvMapper();
        InputStream in = new ClassPathResource(fileName).getInputStream();
        
        MappingIterator<Map<String, String>> readValues = mapper.readerFor(Map.class)
        		.with(bootstrapSchema)
        		.readValues(in);
        
        return readValues.readAll();
	}	
	
	private static Date date(String s, String format) throws Exception {
		return new Date(new SimpleDateFormat(format).parse(s).getTime());
	}
	
	private static final String[] countries = {
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
