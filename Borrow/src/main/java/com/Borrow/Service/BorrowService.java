package com.Borrow.Service;

import com.Borrow.Entity.Borrow;
import com.Borrow.Repository.BorrowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowService {

    private final BorrowRepository borrowRepository;

    public BorrowService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll();
    }

    public Borrow createBorrow(Borrow borrow) {
        return borrowRepository.save(borrow);
    }

    public void deleteBorrow(Long id) {
        borrowRepository.deleteById(id);
    }
}
