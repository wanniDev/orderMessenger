package me.spring.model;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BrokerMessageLog {
	private String messageId;
	private String message;
	private Integer tryCount;
	private String status;
	private LocalDateTime nextRetry;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
}
