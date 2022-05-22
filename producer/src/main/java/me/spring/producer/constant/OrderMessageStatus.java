package me.spring.producer.constant;

public enum OrderMessageStatus {
	SENDING("0"),
	SUCCESS("1"),
	FAILURE("2");

	private final String value;

	OrderMessageStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
