package org.example.sbgroup2.services;

import org.example.sbgroup2.ResourceNotFoundException;
import org.example.sbgroup2.models.Area;
import org.example.sbgroup2.models.Consumer;
import org.example.sbgroup2.repositories.ConsumerRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    private ConsumerRepository consumerRepository;

    public Consumer updateConsumer(Long id, Consumer consumerDetails) {
        Consumer consumer = consumerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consumer not found"));
        consumer.setName(consumerDetails.getName());
        consumer.setArea(consumerDetails.getArea());
        consumer.setPurchaseAmount(consumerDetails.getPurchaseAmount());
        consumer.setDueAmount(consumerDetails.getDueAmount());
        return consumerRepository.save(consumer);
    }

    public Consumer getConsumerById(Long id) {
        return consumerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Area not found"));
    }

    public Consumer getConsumerByName(String name) {
        return consumerRepository.findByName(name);
    }

}
