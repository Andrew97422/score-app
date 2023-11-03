package com.bigdata.products.mortgage.service;

import com.bigdata.products.common.model.LendingType;
import com.bigdata.products.common.service.CommonService;
import com.bigdata.products.mortgage.model.dto.MortgageProduct;
import com.bigdata.products.mortgage.model.entity.MortgageCacheEntity;
import com.bigdata.products.mortgage.model.entity.MortgageEntity;
import com.bigdata.products.mortgage.repository.MortgageCacheRepository;
import com.bigdata.products.mortgage.repository.MortgageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
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
@EnableScheduling
@RequiredArgsConstructor
public class MortgageService implements CommonService<MortgageProduct> {

    private final MortgageRepository mortgageRepository;

    private final MortgageUtils mortgageUtils;

    private final MortgageCacheRepository mortgageCacheRepository;

    ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(10);

    @Override
    public Integer registerNewLending(MortgageProduct mortgageProduct) {
        var mortgageEntity = new MortgageEntity();
        mortgageUtils.mapToEntity(mortgageProduct, mortgageEntity);

        var cacheProduct = serialize(mortgageEntity);
        List<MortgageCacheEntity> list = mortgageEntity.getMortgageCache();
        list.add(cacheProduct);
        mortgageEntity.setMortgageCache(list);

        mortgageRepository.save(mortgageEntity);
        mortgageCacheRepository.saveAndFlush(cacheProduct);
        return mortgageEntity.getId();
    }

    @Override
    public void deleteById(Integer id) {
        mortgageRepository.deleteById(id);
    }

    @Override
    public MortgageProduct getById(Integer id) {
        var mortgageEntity = mortgageRepository.getReferenceById(id);
        var mortgageProduct = new MortgageProduct();
        mortgageUtils.mapToDto(mortgageProduct, mortgageEntity);
        return mortgageProduct;
    }

    @Override
    public void updateById(Integer id, MortgageProduct mortgageProduct) {
        var mortgage = mortgageRepository.getReferenceById(id);
        var mortgageEntity = new MortgageEntity();

        mortgageUtils.mapToEntity(mortgageProduct, mortgageEntity);
        mortgageUtils.update(mortgage, mortgageEntity);

        var cacheProduct = serialize(mortgage);
        List<MortgageCacheEntity> list = mortgage.getMortgageCache();
        list.add(cacheProduct);
        mortgage.setMortgageCache(list);

        mortgageCacheRepository.save(cacheProduct);
        mortgageRepository.save(mortgage);
    }

    @Override
    public void addProductToSchedulingToSetActive() {
        List<Integer> soonActive = mortgageRepository.findAllSoonActive()
                .stream().map(MortgageEntity::getId).toList();
        if (!soonActive.isEmpty()) {
            changeActive(soonActive, true);
        }
    }

    @Override
    public void addProductToSchedulingToSetNotActive() {
        List<Integer> soonNotActive = mortgageRepository.findAllSoonNotActive()
                .stream().map(MortgageEntity::getId).toList();
        if (!soonNotActive.isEmpty()) {
            changeActive(soonNotActive, false);
        }
    }

    @Override
    public void changeActive(List<Integer> ids, boolean active) {
        ids.forEach((id) -> {
            var mortgageEntity = mortgageRepository.getReferenceById(id);

            LocalDateTime time = active ? mortgageEntity.getStartDate() : mortgageEntity.getFinishDate();
            long timeLong = time.toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH));

            poolExecutor.schedule(() -> {
                mortgageEntity.setActive(active);
                mortgageRepository.save(mortgageEntity);
            }, timeLong - LocalDateTime.now().toEpochSecond(
                    ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH)
            ), TimeUnit.SECONDS);
        });
    }

    @Override
    public List<MortgageProduct> getAllProducts() {
        return Optional.of(mortgageRepository.findAll().stream().map((p) -> {
            var product = new MortgageProduct();
            mortgageUtils.mapToDto(product, p);
            return product;
        }).collect(Collectors.toList())).orElseThrow(EntityNotFoundException::new);
    }

    private MortgageCacheEntity serialize(MortgageEntity mortgage) {
        var mortgageCacheProduct = new MortgageCacheEntity();

        mortgageCacheProduct.setMortgage(mortgage);
        mortgageCacheProduct.setLendingType(LendingType.MORTGAGE);

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(mortgage);
            mortgageCacheProduct.setProduct(bos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mortgageCacheProduct;
    }
}
