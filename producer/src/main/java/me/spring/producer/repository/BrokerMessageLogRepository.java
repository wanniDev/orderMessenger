package me.spring.producer.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.spring.model.BrokerMessageLog;

public interface BrokerMessageLogRepository extends JpaRepository<BrokerMessageLog, Long> {
	Optional<BrokerMessageLog> findBrokerMessageLogByMessageId(String messageId);
	List<BrokerMessageLog> findBrokerMessageLogsByStatusAndNextRetryLessThanEqual(String status, LocalDateTime nextRetry);
}
