package me.spring.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class BrokerMessageLog {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String messageId;
	private String message;
	private Integer tryCount;
	private String status;
	private LocalDateTime nextRetry;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;

	public void updateBrokerMessageLogStatus(String status, LocalDateTime updateTime) {
		this.status = status;
		this.updateTime = updateTime;
	}

	public void updateResend(LocalDateTime updateTime) {
		this.updateTime = updateTime;
		this.tryCount++;
	}
}
