package fr.dawan.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import fr.dawan.training.interceptors.JwtAuthFilter;
import fr.dawan.training.tools.DelegatingMultiAuthorizationManager;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	public static String[] authorizedUrls = new String[]{
			"/authenticate",

			"/reset-password", 
			"/change-password", 
			"/error"
	};
	
	@Autowired
	private JwtAuthFilter jwtAuthFilter; //filtre pour contrôler le jeton JWT
	
	@Autowired
	private UserDetailsService userDetailsService; //service permettant d'aller chercher en bdd l'utilisateur par username
	
	//Encodeur de mot de passe pour hasher les valeurs
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	//Modification de la chaine des filtres pour ajouter un filtre qui vérifiera la présence d'un jeton JWT
	@SuppressWarnings("unchecked")
	@Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.cors(AbstractHttpConfigurer::disable)
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth-> auth
						.requestMatchers(authorizedUrls)
						.permitAll()
						
						//exemple de config de sécurité globale
						//.requestMatchers("/admin/**")
						//.access(DelegatingMultiAuthorizationManager.hasAll(AuthorityAuthorizationManager.hasAuthority("admin")))
						
						.anyRequest()
						.authenticated()
						)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.userDetailsService(userDetailsService)
				.build();
	}

//	/admin/*
//	/public/*





}
