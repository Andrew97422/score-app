package com.bigdata.application.model.dto;

import com.bigdata.application.model.entity.LoanApplicationEntity;
import com.bigdata.application.model.enums.ApplicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Ответ на запрос о заявках по имеющемуся типу")
public class ApplicationResponseByType {

    @Schema(name = "application", description = "Заявка")
    private LoanApplicationEntity applicationsList;

    @Schema(name = "status", description = "Статус заявки")
    private ApplicationStatus status;
}
