package com.bigdata.products.autoloan.service;

import com.bigdata.products.autoloan.model.dto.AutoLoanProduct;
import com.bigdata.products.autoloan.model.entity.AutoLoanCacheEntity;
import com.bigdata.products.autoloan.model.entity.AutoLoanEntity;
import com.bigdata.products.autoloan.repository.AutoLoanCacheRepository;
import com.bigdata.products.autoloan.repository.AutoLoanRepository;
import com.bigdata.products.common.model.LendingType;
import com.bigdata.products.common.service.CommonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class AutoLoanService implements CommonService<AutoLoanProduct> {

    private final AutoLoanRepository autoLoanRepository;

    private final AutoLoanUtils autoLoanUtils;

    private final AutoLoanCacheRepository commonCacheRepository;

    ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(10);

    @Transactional
    public Integer registerNewLending(AutoLoanProduct autoLoanProduct) {
        var autoLoanEntity = new AutoLoanEntity();
        autoLoanUtils.mapToEntity(autoLoanProduct, autoLoanEntity);

        var cacheProduct = serialize(autoLoanEntity);
        List<AutoLoanCacheEntity> list = autoLoanEntity.getAutoLoanCache();
        list.add(cacheProduct);
        autoLoanEntity.setAutoLoanCache(list);

        autoLoanRepository.save(autoLoanEntity);
        commonCacheRepository.save(cacheProduct);

        return autoLoanEntity.getId();
    }

    @Transactional
    public void deleteById(Integer id) {
        autoLoanRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public AutoLoanProduct getById(Integer id) {
        var autoLoanEntity = autoLoanRepository.getReferenceById(id);
        var autoLoanProduct = new AutoLoanProduct();

        autoLoanUtils.mapToDto(autoLoanProduct, autoLoanEntity);
        return autoLoanProduct;
    }

    @Transactional
    public void updateById(Integer id, AutoLoanProduct autoLoanProduct) {
        var autoLoan = autoLoanRepository.getReferenceById(id);
        var autoLoanEntity = new AutoLoanEntity();

        autoLoanUtils.mapToEntity(autoLoanProduct, autoLoanEntity);
        autoLoanUtils.update(autoLoan, autoLoanEntity);

        var cacheProduct = serialize(autoLoan);
        List<AutoLoanCacheEntity> list = autoLoan.getAutoLoanCache();
        list.add(cacheProduct);
        autoLoan.setAutoLoanCache(list);

        commonCacheRepository.save(cacheProduct);
        autoLoanRepository.save(autoLoan);
    }

    @Override
    @Scheduled(fixedRate = 1000)
    //@Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void addProductToSchedulingToSetActive() {
        List<Integer> soonActive = autoLoanRepository.findAllSoonActive()
                .stream().map(AutoLoanEntity::getId).toList();
        if (!soonActive.isEmpty()) {
            changeActive(soonActive, true);
        }
    }

    @Override
    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void addProductToSchedulingToSetNotActive() {
        List<Integer> soonNotActive = autoLoanRepository.findAllSoonNotActive()
                .stream().map(AutoLoanEntity::getId).toList();
        if (!soonNotActive.isEmpty()) {
            changeActive(soonNotActive, false);
        }
    }

    @Override
    @Transactional
    public void changeActive(List<Integer> ids, boolean active) {
        ids.forEach((id) -> {
            var autoLoanEntity = autoLoanRepository.getReferenceById(id);
            LocalDateTime time = active ? autoLoanEntity.getStartDate() : autoLoanEntity.getFinishDate();

            long timeLong = time.toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH));

            poolExecutor.schedule(() -> {
                autoLoanEntity.setActive(active);
                autoLoanRepository.save(autoLoanEntity);
            }, timeLong - LocalDateTime.now()
                    .toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH))
            , TimeUnit.SECONDS);
        });
    }

    @Override
    @Transactional
    public List<AutoLoanProduct> getAllProducts() {
        return Optional.of(autoLoanRepository.findAll().stream().map((p) -> {
            var product = new AutoLoanProduct();
            autoLoanUtils.mapToDto(product, p);
            return product;
        }).collect(Collectors.toList())).orElseThrow(EntityNotFoundException::new);
    }

    private AutoLoanCacheEntity serialize(AutoLoanEntity autoLoan) {
        var autoLoanCacheProduct = new AutoLoanCacheEntity();

        autoLoanCacheProduct.setAutoLoan(autoLoan);
        autoLoanCacheProduct.setLendingType(LendingType.AUTO_LOAN);

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(autoLoan);
            autoLoanCacheProduct.setProduct(bos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return autoLoanCacheProduct;
    }
}
