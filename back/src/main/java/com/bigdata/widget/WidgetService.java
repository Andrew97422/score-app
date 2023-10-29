package com.bigdata.widget;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WidgetService {

    private final WidgetRepository widgetRepository;
    private final ThemesWidgetRepository themesWidgetRepository;

    @Transactional(readOnly = true)
    public WidgetResponse getWidget(Integer id) {
        var widget = widgetRepository.getReferenceById(id);
        var settings = widget.getThemesWidget();
        return WidgetResponse.builder().id(id).interestRate(widget.getInterestRate()).font(settings.getFont())
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
    public void setWidget(Map<String, String> params) {
        try {
            Integer widgetId = Integer.parseInt(params.get("id"));
            var widget = widgetRepository.getReferenceById(widgetId);
            if (params.containsKey("interestRate")) {
                widget.setInterestRate(Double.parseDouble(params.get("interestRate")));
            }

            var themes = widget.getThemesWidget();
            if (params.containsKey("color")) {
                themes.setColor(params.get("color"));
            }
            if (params.containsKey("font")) {
                themes.setFont(params.get("font"));
            }
            if (params.containsKey("name")) {
                themes.setName(params.get("name"));
            }

            widget.setThemesWidget(themes);
            widgetRepository.save(widget);
        } catch (Exception e) {
            log.error("Some problem with setting widget!");
        }
    }
}
