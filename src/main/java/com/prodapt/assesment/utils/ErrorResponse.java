package com.prodapt.assesment.utils;

import java.time.ZonedDateTime;
import java.util.Map;

public class ErrorResponse {
    private ZonedDateTime timestamp;
    private int status;
    private String error;
    private Map<String, String> message;
    private String path;

    public ErrorResponse(ZonedDateTime timestamp, int status, String error, Map<String, String> message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }


	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Map<String, String> getMessage() {
		return message;
	}

	public void setMessage(Map<String, String> message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}