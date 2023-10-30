package com.bigdata.widget.service;

import com.bigdata.widget.model.dto.ThemeRequest;
import com.bigdata.widget.model.dto.ThemeResponse;
import com.bigdata.widget.model.entity.ThemesWidget;
import com.bigdata.widget.repository.ThemesWidgetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ThemeWidgetService {

    private final ThemesWidgetRepository themesWidgetRepository;

    @Transactional(readOnly = true)
    public List<ThemeResponse> getAllThemes() {
        return themesWidgetRepository.findAll().stream().map(t -> ThemeResponse.builder().font(t.getFont())
                .id(t.getId()).color(t.getColor()).name(t.getName()).build()).collect(Collectors.toList());
    }

    @Transactional
    public void addNewTheme(ThemeRequest request) {
        var theme = ThemesWidget.builder().font(request.getFont()).color(request.getColor())
                .name(request.getName()).build();
        themesWidgetRepository.saveAndFlush(theme);
    }

    @Transactional(readOnly = true)
    public ThemeResponse getThemeById(Integer id) {
        var theme = themesWidgetRepository.getReferenceById(id);
        return ThemeResponse.builder().color(theme.getColor()).font(theme.getFont()).id(theme.getId())
                .name(theme.getName()).build();
    }

    @Transactional
    public void deleteThemeById(Integer id) {
        themesWidgetRepository.deleteById(id);
    }

    @Transactional
    public void updateThemeById(Integer id, ThemeRequest request) {
        var theme = themesWidgetRepository.getReferenceById(id);
        theme.setFont(request.getFont());
        theme.setName(request.getName());
        theme.setColor(request.getColor());
        themesWidgetRepository.saveAndFlush(theme);
    }
}
