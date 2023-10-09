package com.bigdata.products.consumer.service;

import com.bigdata.products.common.service.CommonService;
import com.bigdata.products.consumer.model.dto.ConsumerProduct;
import com.bigdata.products.consumer.model.entity.ConsumerEntity;
import com.bigdata.products.consumer.repository.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ConsumerService implements CommonService<ConsumerProduct> {

    private final ConsumerRepository consumerRepository;

    private final ConsumerUtils consumerUtils;

    ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(10);

    @Transactional
    public Integer registerNewLending(ConsumerProduct consumerProduct) {
        var consumerEntity = new ConsumerEntity();
        consumerUtils.mapToEntity(consumerProduct, consumerEntity);
        consumerRepository.save(consumerEntity);
        return consumerEntity.getId();
    }

    @Transactional
    public void deleteById(Integer id) {
        consumerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ConsumerProduct getById(Integer id) {
        var consumerEntity = consumerRepository.getReferenceById(id);
        var consumerProduct = new ConsumerProduct();
        consumerUtils.mapToDto(consumerProduct, consumerEntity);
        return consumerProduct;
    }

    @Transactional
    public void updateById(Integer id, ConsumerProduct consumerProduct) {
        var consumer = consumerRepository.getReferenceById(id);
        var consumerEntity = new ConsumerEntity();
        consumerUtils.mapToEntity(consumerProduct, consumerEntity);
        consumerUtils.update(consumer, consumerEntity);
        consumerRepository.save(consumer);
    }

    @Override
    @Scheduled(cron = "0 * * * * ?")
    public void addProductToSchedulingToSetActive() {
        List<Integer> soonActive = consumerRepository.findAllSoonActive()
                .stream().map(ConsumerEntity::getId).toList();
        if (!soonActive.isEmpty()) {
            makeActive(soonActive);
        }
    }

    @Override
    @Scheduled(cron = "0 * * * * ?")
    public void addProductToSchedulingToSetNotActive() {
        List<Integer> soonNotActive = consumerRepository.findAllSoonNotActive()
                .stream().map(ConsumerEntity::getId).toList();
        if (!soonNotActive.isEmpty()) {
            makeActive(soonNotActive);
        }
    }

    @Override
    public void makeActive(List<Integer> ids) {
        ids.forEach((id) -> {
            var consumerEntity = consumerRepository.getReferenceById(id);

            LocalDateTime timeActive = consumerEntity.getStartDate();
            long timeToActive = timeActive.toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH));

            poolExecutor.schedule(() -> {
                        consumerEntity.setActive(true);
                        consumerRepository.save(consumerEntity);
                    },timeToActive - LocalDateTime.now()
                            .toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH))
                    , TimeUnit.SECONDS);
        });
    }

    @Override
    public void makeNotActive(List<Integer> ids) {
        ids.forEach((id) -> {
            var consumerEntity = consumerRepository.getReferenceById(id);

            LocalDateTime timeNotActive = consumerEntity.getFinishDate();
            long timeToNotActive = timeNotActive.toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH));

            poolExecutor.schedule(() -> {
                        consumerEntity.setActive(false);
                        consumerRepository.save(consumerEntity);
                    },timeToNotActive - LocalDateTime.now()
                            .toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH))
                    , TimeUnit.SECONDS);
        });
    }
}
