package fr.dawan.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.training.dto.ChangePwd;
import fr.dawan.training.dto.Login;
import fr.dawan.training.dto.LoginResponse;
import fr.dawan.training.dto.ResetPassword;
import fr.dawan.training.dto.StringDto;
import fr.dawan.training.services.IUserService;

@RestController
public class LoginController {

	@Autowired
	private IUserService userService;

	@PostMapping(value = "/authenticate", consumes = "application/json", produces = "application/json")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody Login login) throws Exception {
		LoginResponse res = userService.authenticate(login);
		return ResponseEntity.ok(res);
	}

	/**
	 * Sending an email to reset password
	 * 
	 * @param resetObj
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/reset-password", consumes = "application/json", produces = "application/json")
	public ResponseEntity<StringDto> resetPassword(@RequestBody ResetPassword resetObj) throws Exception {
		StringDto res = userService.emailToResetPassword(resetObj);
		return ResponseEntity.ok(res);
	}

	@PostMapping(value = "/change-password", consumes = "application/json", produces = "application/json")
	public ResponseEntity<StringDto> changePassword(@RequestBody ChangePwd changePwdObj) throws Exception {
		StringDto res = userService.changePassword(changePwdObj);
		return ResponseEntity.ok(res);
	}

}
