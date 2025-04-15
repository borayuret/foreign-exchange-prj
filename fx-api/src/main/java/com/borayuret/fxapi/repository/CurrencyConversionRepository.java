package com.borayuret.fxapi.repository;

import com.borayuret.fxapi.model.CurrencyConversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for accessing currency conversion history.
 * Supports filtering by transaction ID or date.
 */
public interface CurrencyConversionRepository extends JpaRepository<CurrencyConversion, UUID> {

    /**
     * Finds a single conversion by transaction ID.
     *
     * @param transactionId the UUID of the transaction
     * @return the matching conversion, if found
     */
    Optional<CurrencyConversion> findByTransactionId(UUID transactionId);

    /**
     * Finds all conversions made on a specific date (based on timestamp).
     *
     * @param date     the date to filter by (only year-month-day is considered)
     * @param pageable pagination parameters
     * @return paginated list of conversions made on the given date
     */
    Page<CurrencyConversion> findByTimestampBetween(LocalDateTime startOfDay, LocalDateTime endOfDay, Pageable pageable);
}
