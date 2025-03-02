package com.java.project.llms.vos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseVo {

	private String successMessage;

	private String failureMessage;

	private String apiEndPoint;

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	public String getApiEndPoint() {
		return apiEndPoint;
	}

	public void setApiEndPoint(String apiEndPoint) {
		this.apiEndPoint = apiEndPoint;
	}

	public ResponseVo(String successMessage, String failureMessage, String apiEndPoint) {
		super();
		this.successMessage = successMessage;
		this.failureMessage = failureMessage;
		this.apiEndPoint = apiEndPoint;
	}

	public ResponseVo() {
		super();

	}

}
