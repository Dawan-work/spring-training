package fr.dawan.training.services;

import java.util.List;

import fr.dawan.training.dto.ChangePwd;
import fr.dawan.training.dto.Login;
import fr.dawan.training.dto.LoginResponse;
import fr.dawan.training.dto.LongDto;
import fr.dawan.training.dto.ResetPassword;
import fr.dawan.training.dto.StringDto;
import fr.dawan.training.dto.UserDto;

public interface IUserService extends IGenericService<UserDto, Long> {
	
	List<UserDto> getAllBy(int page, int size, String nameOrEmail) throws Exception;
	
	LongDto countBy(String nameOrEmail) throws Exception;
	
	UserDto findByEmail(String email) throws Exception;
	
	LoginResponse authenticate(Login login) throws Exception;

	StringDto emailToResetPassword(ResetPassword resetObj) throws Exception;
	
	StringDto changePassword(ChangePwd changePwdObj) throws Exception;
	

}
