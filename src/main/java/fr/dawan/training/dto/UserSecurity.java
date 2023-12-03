package fr.dawan.training.dto;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.dawan.training.entities.User;

@SuppressWarnings("serial")
public class UserSecurity implements UserDetails {

	/**
	 * 
	 */
	private final User user;
	
	public UserSecurity(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// user contient String roles
		// userDetails de SpringSecurity > Collection<GrantedAuthority
		
		return Arrays.stream(user.getRoles().split(","))
			  .map(SimpleGrantedAuthority::new)
			  .toList();
		
		//EQUIVALENT A : 
//		String[] rolesArray = user.getRoles().split(",");
//		List<GrantedAuthority> result = new ArrayList<>();
//		for(String s : rolesArray) {
//			result.add(new SimpleGrantedAuthority(s));
//		}
//		return result;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}

}
