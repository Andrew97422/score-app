package com.bigdata.products.consumer.service;

import com.bigdata.products.common.model.LendingType;
import com.bigdata.products.common.service.CommonService;
import com.bigdata.products.consumer.model.dto.ConsumerProduct;
import com.bigdata.products.consumer.model.entity.ConsumerCacheEntity;
import com.bigdata.products.consumer.model.entity.ConsumerEntity;
import com.bigdata.products.consumer.repository.ConsumerCacheRepository;
import com.bigdata.products.consumer.repository.ConsumerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsumerService implements CommonService<ConsumerProduct> {

    private final ConsumerRepository consumerRepository;

    private final ConsumerUtils consumerUtils;

    private final ConsumerCacheRepository consumerCacheRepository;

    ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(10);

    @Override
    public Integer registerNewLending(ConsumerProduct consumerProduct) {
        var consumerEntity = new ConsumerEntity();
        consumerUtils.mapToEntity(consumerProduct, consumerEntity);

        var cacheProduct = serialize(consumerEntity);
        List<ConsumerCacheEntity> list = consumerEntity.getConsumerCache();
        list.add(cacheProduct);
        consumerEntity.setConsumerCache(list);

        consumerRepository.save(consumerEntity);
        consumerCacheRepository.save(cacheProduct);
        return consumerEntity.getId();
    }

    @Override
    public void deleteById(Integer id) {
        consumerRepository.deleteById(id);
    }

    @Override
    public ConsumerProduct getById(Integer id) {
        var consumerEntity = consumerRepository.getReferenceById(id);
        var consumerProduct = new ConsumerProduct();
        consumerUtils.mapToDto(consumerProduct, consumerEntity);
        return consumerProduct;
    }

    @Override
    public void updateById(Integer id, ConsumerProduct consumerProduct) {
        var consumer = consumerRepository.getReferenceById(id);
        var consumerEntity = new ConsumerEntity();

        consumerUtils.mapToEntity(consumerProduct, consumerEntity);
        consumerUtils.update(consumer, consumerEntity);

        var cacheProduct = serialize(consumer);
        List<ConsumerCacheEntity> list = consumer.getConsumerCache();
        list.add(cacheProduct);
        consumer.setConsumerCache(list);

        consumerCacheRepository.save(cacheProduct);
        consumerRepository.save(consumer);
    }

    @Override
    public void addProductToSchedulingToSetActive() {
        List<Integer> soonActive = consumerRepository.findAllSoonActive()
                .stream().map(ConsumerEntity::getId).toList();
        if (!soonActive.isEmpty()) {
            changeActive(soonActive, true);
        }
    }

    @Override
    public void addProductToSchedulingToSetNotActive() {
        List<Integer> soonNotActive = consumerRepository.findAllSoonNotActive()
                .stream().map(ConsumerEntity::getId).toList();
        if (!soonNotActive.isEmpty()) {
            changeActive(soonNotActive, false);
        }
    }

    @Override
    public void changeActive(List<Integer> ids, boolean active) {
        ids.forEach((id) -> {
            var consumerEntity = consumerRepository.getReferenceById(id);

            LocalDateTime time = active ? consumerEntity.getStartDate() : consumerEntity.getFinishDate();
            long timeLong = time.toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH));

            poolExecutor.schedule(() -> {
                consumerEntity.setActive(active);
                consumerRepository.save(consumerEntity);
            }, timeLong - LocalDateTime.now()
                    .toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH)), TimeUnit.SECONDS);
        });
    }

    @Override
    public List<ConsumerProduct> getAllProducts() {
        return Optional.of(consumerRepository.findAll().stream().map((p) -> {
            var product = new ConsumerProduct();
            consumerUtils.mapToDto(product, p);
            return product;
        }).collect(Collectors.toList())).orElseThrow(EntityNotFoundException::new);
    }

    private ConsumerCacheEntity serialize(ConsumerEntity consumer) {
        var consumerCacheProduct = new ConsumerCacheEntity();

        consumerCacheProduct.setConsumer(consumer);
        consumerCacheProduct.setLendingType(LendingType.CONSUMER);

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(consumer);
            consumerCacheProduct.setProduct(bos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return consumerCacheProduct;
    }
}
