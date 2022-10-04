package ecom.udpm.vn.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
	private final String message;
	private final Boolean success;
	private final String timestamp;
	private final String cause;
	private final String path;

	public ApiResponse(Boolean success, String message, String cause, String path) {

		this.timestamp = Instant.now().toString();
		this.message = message;
		this.success = success;
		this.cause = cause;
		this.path = path;
	}

	public ApiResponse(Boolean success, String data) {
		this.timestamp = Instant.now().toString();
		this.message = data;
		this.success = success;
		this.cause = null;
		this.path = null;
	}

	public String getMessage() {
		return message;
	}
}