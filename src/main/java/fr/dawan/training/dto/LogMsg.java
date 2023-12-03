package fr.dawan.training.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LogMsg implements Serializable {

	
	private int code;
	private String message;
	private LocalDateTime timestamp;
	private String stackTrace;
	private String path;
	
	public enum LogLevel { INFO, WARN, DEBUG, ERROR}
	private LogLevel level;
	
	public enum LogType { CLIENT_APP, ACCESS, EXCEPTION }
	private LogType type;
	
	
	public LogMsg() {
		timestamp = LocalDateTime.now();
	}
	
	
	
	public LogType getType() {
		return type;
	}



	public void setType(LogType type) {
		this.type = type;
	}



	public LogLevel getLevel() {
		return level;
	}

	public void setLevel(LogLevel level) {
		this.level = level;
	}



	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public String getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	
	
}
