package com.bigdata.widget;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WidgetService {

    private final WidgetRepository widgetRepository;

    public WidgetResponse getWidget() {
        return widgetRepository.findAll().stream().findAny().map(w ->
                WidgetResponse.builder().id(w.getId()).build()).orElse(new WidgetResponse());
    }

    public List<WidgetResponse> getAllWidgets() {
        return widgetRepository.findAll().stream().map(w ->
                WidgetResponse.builder().id(w.getId()).build()).collect(Collectors.toList());
    }

    public void setWidget(String color) {
        try {
            WidgetEntity widget = widgetRepository.getReferenceById(Integer.parseInt(color));
            widget.setColor(Integer.parseInt(color));
            widgetRepository.save(widget);
        } catch (NumberFormatException e) {
            log.error("Color should be a number!");
        }
    }
}
