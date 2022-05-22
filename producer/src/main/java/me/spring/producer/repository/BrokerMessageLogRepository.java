package me.spring.producer.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import me.spring.model.BrokerMessageLog;

@Repository
public interface BrokerMessageLogRepository extends JpaRepository<BrokerMessageLog, Long> {
	@Query("update BrokerMessageLog bml "
		+ "set bml.status = :status,"
		+ "bml.updateTime = :updateTime "
		+ "where bml.messageId = :messageId"
	)
	void updateBrokerMessageLogStatus(String status, LocalDateTime updateTime, String messageId);

	List<BrokerMessageLog> findBrokerMessageLogsByStatusAndNextRetryLessThanEqual(String status, LocalDateTime nextRetry);
}
