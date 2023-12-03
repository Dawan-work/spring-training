package fr.dawan.training.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.dawan.training.dto.ChangePwd;
import fr.dawan.training.dto.Login;
import fr.dawan.training.dto.LoginResponse;
import fr.dawan.training.dto.LongDto;
import fr.dawan.training.dto.ResetPassword;
import fr.dawan.training.dto.StringDto;
import fr.dawan.training.dto.UserDto;
import fr.dawan.training.entities.User;
import fr.dawan.training.exceptions.AuthenticateException;
import fr.dawan.training.exceptions.SamePasswordException;
import fr.dawan.training.exceptions.TokenException;
import fr.dawan.training.repositories.UserRepository;
import fr.dawan.training.tools.DtoTools;
import fr.dawan.training.tools.JwtUtils;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl extends GenericServiceImpl<UserDto, User, Long> implements IUserService {

	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private IEmailService emailService;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		super(userRepository, UserDto.class, User.class);
		this.userRepository = userRepository;
	}

	@Override
	public List<UserDto> getAllBy(int page, int size, String nameOrEmail) throws Exception {
		List<UserDto> result = new ArrayList<>();

		List<User> users = userRepository.findAllByFirstNameContainingOrLastNameContainingOrEmailContaining(nameOrEmail,
				nameOrEmail, nameOrEmail, PageRequest.of(page, size)).getContent();

		for (User s : users) {
			result.add(DtoTools.convert(s, UserDto.class));
		}
		return result;
	}

	@Override
	public LongDto countBy(String nameOrEmail) throws Exception {
		long nb = userRepository.countByFirstNameContainingOrLastNameContainingOrEmailContaining(nameOrEmail,
				nameOrEmail, nameOrEmail);
		LongDto result = new LongDto();
		result.setResult(nb);
		return result;
	}

	@Override
	public UserDto saveOrUpdate(UserDto obj) throws Exception {
		User u = DtoTools.convert(obj, User.class);
		if (u.getId() <= 0) { // insert, then encrypt password
			u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
		} else { // update then check if password changed then encrypt it
			Optional<User> userInDbOpt = userRepository.findById(u.getId());
			if (userInDbOpt.isPresent()) {
				String userInDbPwd = userInDbOpt.get().getPassword();
				if (!userInDbPwd.equals(u.getPassword()))
					u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
			}
		}
		u = repository.saveAndFlush(u);
		return DtoTools.convert(u, UserDto.class);
	}

	@Override
	public UserDto findByEmail(String email) throws Exception {
		Optional<User> u = userRepository.findByEmail(email);
		if (u.isPresent())
			return DtoTools.convert(u.get(), UserDto.class);

		return null;
	}

	@Override
	public LoginResponse authenticate(Login login) throws Exception {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword(), new ArrayList<>()));

		UserDetails user = userDetailsService.loadUserByUsername(login.getEmail());

		if (user == null)
			throw new AuthenticateException("User not found");

		String jwt = jwtUtils.generateToken(user);
		LoginResponse resp = new LoginResponse();
		resp.setToken(jwt);
		return resp;
	}

	@Override
	public StringDto emailToResetPassword(ResetPassword resetObj) throws Exception {
		UserDetails user = userDetailsService.loadUserByUsername(resetObj.getEmail());
		if (user == null)
			throw new UsernameNotFoundException("User not found");

		String jwt = jwtUtils.generateToken(user);
		emailService.sendEmailForResetPwd(user.getUsername(), jwt);
		return new StringDto("Email sent for update");
	}

	@Override
	public StringDto changePassword(ChangePwd changePwdObj) throws Exception {

		String username = jwtUtils.extractUsername(changePwdObj.getToken());
		Optional<User> opt = userRepository.findByEmail(username);
		if (!opt.isPresent()) 
			throw new AuthenticateException("User not found");
		
		boolean valid = (username.equals(username) && !jwtUtils.isTokenExpired(changePwdObj.getToken()));
		if (!valid)
			throw new TokenException("Invalid token");

		BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
		String newEncodedPwd = pwdEncoder.encode(changePwdObj.getPassword());

		User u = opt.get();
		
		if (!u.isEnabled()) 
			throw new AuthenticateException("Disabled Account, contact Site Administrator");
		
		if (changePwdObj.getPassword().equals(u.getPassword()))
			throw new SamePasswordException("Updating with the same Old password");

		u.setPassword(newEncodedPwd);
		userRepository.saveAndFlush(u);
		return new StringDto("Password updated");
	}

}
