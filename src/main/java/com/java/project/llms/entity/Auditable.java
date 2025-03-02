package com.java.project.llms.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Auditable {

	private String createdBy;

	private Date creationDate;

	private String lastModifiedBy;

	private Date lastModifiedDate;

	public Auditable() {
		super();
	}

}