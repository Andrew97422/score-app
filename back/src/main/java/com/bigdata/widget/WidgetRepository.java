package com.bigdata.widget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WidgetRepository extends JpaRepository<WidgetEntity, Integer> {
}
