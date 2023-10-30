package com.bigdata.widget.repository;

import com.bigdata.widget.model.entity.ThemesWidget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemesWidgetRepository extends JpaRepository<ThemesWidget, Integer> {
}
