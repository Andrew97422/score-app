package com.bigdata.products.consumer.service;

import com.bigdata.products.common.CommonService;
import com.bigdata.products.consumer.model.dto.ConsumerProduct;
import com.bigdata.products.consumer.model.entity.ConsumerEntity;
import com.bigdata.products.consumer.repository.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsumerService implements CommonService<ConsumerProduct> {

    private final ConsumerRepository consumerRepository;

    private final ConsumerUtils consumerUtils;

    @Transactional
    public Integer registerNewLending(ConsumerProduct consumerProduct) {
        var consumerEntity = consumerUtils.mapToEntity(consumerProduct);
        consumerRepository.save(consumerEntity);
        return consumerEntity.getId();
    }

    @Transactional
    public void deleteById(Integer id) {
        consumerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ConsumerProduct getById(Integer id) {
        return consumerUtils.mapToDto(consumerRepository.getReferenceById(id));
    }

    @Transactional
    public void updateById(Integer id, ConsumerProduct consumerProduct) {
        var consumerEntity = consumerUtils.mapToEntity(consumerProduct);
        ConsumerEntity consumer = consumerRepository.getReferenceById(id);
        consumerUtils.update(consumer, consumerEntity);
        consumerRepository.save(consumer);
    }
}
