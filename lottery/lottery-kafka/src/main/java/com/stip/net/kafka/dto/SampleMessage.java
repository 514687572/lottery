package com.stip.net.kafka.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @Title:
 * @date：20181112
 * @author：cja
 *
 */
public class SampleMessage implements Serializable{
	private Integer id;

	private String message;

	@JsonCreator
	public SampleMessage(@JsonProperty("id") Integer id,@JsonProperty("message") String message) {
		this.id = id;
		this.message = message;
	}

	public Integer getId() {
		return this.id;
	}

	public String getMessage() {
		return this.message;
	}

	@Override
	public String toString() {
		return "SampleMessage{id=" + this.id + ", message='" + this.message + "'}";
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
