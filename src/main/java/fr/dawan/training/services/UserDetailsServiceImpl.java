package fr.dawan.training.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.dawan.training.dto.UserSecurity;
import fr.dawan.training.entities.User;
import fr.dawan.training.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> opt = userRepository.findByEmail(username);
		if(opt.isPresent())
			return new UserSecurity(opt.get());
		else
			throw new UsernameNotFoundException("User not found !");
		
		//Equivalent à : 
//		return userRepository.findByEmail(username)
//							.map(UserSecurity::new) //appel au constructeur de UserSecurity
//							.orElseThrow(()->new UsernameNotFoundException("User not found !"));
		
	}

}
