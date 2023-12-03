package fr.dawan.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import fr.dawan.training.interceptors.MyInterceptor;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.demo"})
@EnableJpaRepositories
@EnableScheduling //activation de la prise en charge de tâches planifiées
@EnableCaching
public class TrainingApplication {

	@Value("${cors.allowed-urls}")
	private String allowedUrls;
	
	@Autowired
	private MyInterceptor myInterceptor;
	
	public static void main(String[] args) {
		SpringApplication.run(TrainingApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
//		SimpleClientHttpRequestFactory reqFactory = new SimpleClientHttpRequestFactory();
//		Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("",8083));
//		reqFactory.setProxy(proxy);
//		return new RestTemplate(reqFactory);
		
		return new RestTemplate();
	}
	
	@Bean
	public WebMvcConfigurer mvcConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/admin/*")
//					.allowedOrigins("dawan.fr")
//					.allowedMethods("GET","POST");
				
				//On autorise les domaines renseignés dans allowedUrls
				//à accéder à n'importe quel url de mon service avec toutes les méthodes
				registry.addMapping("/**")
					.allowedOrigins(allowedUrls.split(","))
					.allowedMethods("GET","POST","PUT","DELETE","PATCH", "OPTIONS");
				
			}

//			@Override
//			public void addInterceptors(InterceptorRegistry registry) {
//				//registry.addInterceptor(myInterceptor);
//			}
			
		};
	}
	

}
