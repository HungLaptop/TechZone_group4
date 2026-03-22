package com.techzone.app.service;

import com.techzone.app.entity.Feedback;
import com.techzone.app.entity.Product;

import java.util.List;

public interface FeedbackService {
    Feedback postFeedback(Feedback feedback);
    List<Feedback> listPendingFeedback();
    List<Feedback> listByProduct(Product product);
    Feedback approveFeedback(Long id);
    Feedback replyFeedback(Long id, String reply);
}