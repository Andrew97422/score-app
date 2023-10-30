package com.bigdata.widget.repository;

import com.bigdata.widget.model.entity.WidgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WidgetRepository extends JpaRepository<WidgetEntity, Integer> {
}
