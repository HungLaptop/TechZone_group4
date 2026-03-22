package com.techzone.app.repository;

import com.techzone.app.entity.Feedback;
import com.techzone.app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByApprovedFalse();
    List<Feedback> findByProduct(Product product);
}