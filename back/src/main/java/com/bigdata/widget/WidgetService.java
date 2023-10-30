package com.bigdata.widget;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WidgetService {

    private final WidgetRepository widgetRepository;
    private final ThemesWidgetRepository themesWidgetRepository;

    @Transactional(readOnly = true)
    public WidgetResponse getWidget() {
        var widget = widgetRepository.getReferenceById(1);
        var settings = widget.getThemesWidget();
        return WidgetResponse.builder().id(1).interestRate(widget.getInterestRate()).font(settings.getFont())
                .name(settings.getName()).color(settings.getColor()).build();
    }

    @Transactional(readOnly = true)
    public List<WidgetResponse> getAllWidgets() {
        return widgetRepository.findAll().stream().map(w -> WidgetResponse.builder().id(w.getId())
                .interestRate(w.getInterestRate())
                .color(w.getThemesWidget().getColor()).name(w.getThemesWidget().getName())
                .font(w.getThemesWidget().getFont()).build()).collect(Collectors.toList());
    }

    @Transactional
    public void setWidget(WidgetRequest request) {
        var widget = widgetRepository.getReferenceById(1);
        log.info("Got widget: {}", widget);
        widget.setInterestRate(request.getInterestRate());
        var theme = widget.getThemesWidget();
        log.info("Got themes: {}", theme);
        theme.setName(request.getName());
        theme.setColor(request.getColor());
        theme.setFont(request.getFont());
        themesWidgetRepository.saveAndFlush(theme);
        log.info("Have: {}", theme);
    }

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
