package com.bigdata.widget.service;

import com.bigdata.widget.model.dto.WidgetRequest;
import com.bigdata.widget.model.dto.WidgetResponse;
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
        return WidgetResponse.builder().id(1).interestRate(widget.getInterestRate())
                .themeId(widget.getThemesWidget().getId()).build();
    }

    @Transactional(readOnly = true)
    public List<WidgetResponse> getAllWidgets() {
        return widgetRepository.findAll().stream().map(w -> WidgetResponse.builder().id(w.getId())
                .interestRate(w.getInterestRate())
                .themeId(w.getThemesWidget().getId()).build()).collect(Collectors.toList());
    }

    @Transactional
    public void setWidget(WidgetRequest request, Integer id) {
        var widget = widgetRepository.getReferenceById(id);
        log.info("Got widget: {}", widget);

        widget.setInterestRate(request.getInterestRate());

        var theme = themesWidgetRepository.getReferenceById(request.getThemeId());
        widget.setThemesWidget(theme);

        widgetRepository.save(widget);
    }
}
