package fr.dawan.training.interceptors;

import java.sql.SQLIntegrityConstraintViolationException;

import org.hibernate.StaleObjectStateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fr.dawan.training.dto.LogMsg;
import fr.dawan.training.dto.LogMsg.LogLevel;
import fr.dawan.training.dto.LogMsg.LogType;
import fr.dawan.training.exceptions.AuthenticateException;
import fr.dawan.training.exceptions.SamePasswordException;
import fr.dawan.training.exceptions.TokenException;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

	public ResponseEntity<?> handleError(Exception ex, WebRequest request, int code, LogLevel level, LogType type, String message, HttpStatus status){
		LogMsg m = new LogMsg();
		m.setCode(code);
		m.setLevel(level);
		m.setType(type);
		m.setMessage(message);
		if(request!=null)
			m.setPath(((ServletWebRequest)request).getRequest().getRequestURI());
		
		ex.printStackTrace(); //display stacktrace
//		StringWriter sw = new StringWriter();
//		ex.printStackTrace(new PrintWriter(sw));
//		m.setStackTrace(sw.toString());
		return handleExceptionInternal(ex, m, new HttpHeaders(), status, request);
	}
	
	
	@ExceptionHandler(value={IllegalArgumentException.class})
	protected ResponseEntity<?> handleIllegalArgument(Exception ex, WebRequest request){
		return handleError(ex, request, 400, LogLevel.ERROR, LogType.EXCEPTION, ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value={DataIntegrityViolationException.class, ConstraintViolationException.class,  SQLIntegrityConstraintViolationException.class})
	protected ResponseEntity<?> handleSQLConstrException(Exception ex, WebRequest request){
		return handleError(ex, request, 409, LogLevel.ERROR, LogType.EXCEPTION, "Values Error (constraint violation)", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value={ObjectOptimisticLockingFailureException.class, StaleObjectStateException.class})
	protected ResponseEntity<?> handleErrorVersion(Exception ex, WebRequest request){
		return handleError(ex, request, 409, LogLevel.ERROR, LogType.EXCEPTION, "Object Version error", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value={TokenException.class, UsernameNotFoundException.class, BadCredentialsException.class})
	protected ResponseEntity<?> handleTokenException(Exception ex, WebRequest request){
		return handleError(ex, request, 401, LogLevel.ERROR, LogType.ACCESS, ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value={AuthenticateException.class, AuthenticationException.class, SamePasswordException.class})
	protected ResponseEntity<?> handleAuthenticateException(Exception ex, WebRequest request){
		return handleError(ex, request, 401, LogLevel.ERROR, LogType.ACCESS, "Authentication Error", HttpStatus.UNAUTHORIZED);
	}
	
	
	@ExceptionHandler(value={AccessDeniedException.class})
	protected ResponseEntity<?> handleAccessDeniedException(Exception ex, WebRequest request){
		return handleError(ex, request, 403, LogLevel.ERROR, LogType.ACCESS, ex.getMessage(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(value={Exception.class})
	protected ResponseEntity<?> handleGenericException(Exception ex, WebRequest request){
		return handleError(ex, request, 500, LogLevel.ERROR, LogType.EXCEPTION, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
}
