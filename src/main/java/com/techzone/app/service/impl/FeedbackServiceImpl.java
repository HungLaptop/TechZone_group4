package com.techzone.app.service.impl;

import com.techzone.app.entity.Feedback;
import com.techzone.app.entity.Product;
import com.techzone.app.repository.FeedbackRepository;
import com.techzone.app.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    public Feedback postFeedback(Feedback feedback) {
        feedback.setApproved(false);
        feedback.setCreatedAt(LocalDateTime.now());
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> listPendingFeedback() {
        return feedbackRepository.findByApprovedFalse();
    }

    @Override
    public List<Feedback> listByProduct(Product product) {
        return feedbackRepository.findByProduct(product);
    }

    @Override
    public Feedback approveFeedback(Long id) {
        Feedback fb = feedbackRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Feedback not found"));
        fb.setApproved(true);
        return feedbackRepository.save(fb);
    }

    @Override
    public Feedback replyFeedback(Long id, String reply) {
        Feedback fb = feedbackRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Feedback not found"));
        fb.setReply(reply);
        fb.setRepliedAt(LocalDateTime.now());
        return feedbackRepository.save(fb);
    }
}