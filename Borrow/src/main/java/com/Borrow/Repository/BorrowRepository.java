package com.Borrow.Repository;

import com.Borrow.Entity.Borrow;
import com.Borrow.Entity.BorrowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findByDueDateBeforeAndStatus(LocalDate date, BorrowStatus status);
}
