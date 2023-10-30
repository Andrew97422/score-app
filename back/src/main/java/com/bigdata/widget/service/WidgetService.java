package com.bigdata.widget.service;

import com.bigdata.widget.model.dto.ThemeRequest;
import com.bigdata.widget.model.dto.ThemeResponse;
import com.bigdata.widget.model.dto.WidgetRequest;
import com.bigdata.widget.model.dto.WidgetResponse;
import com.bigdata.widget.model.entity.ThemesWidget;
import com.bigdata.widget.repository.ThemesWidgetRepository;
import com.bigdata.widget.repository.WidgetRepository;
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
}
