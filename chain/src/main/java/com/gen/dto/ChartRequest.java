package com.gen.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(description = "生成图表的请求")
@Data
public class ChartRequest {
    private String title;

    private String goal;

    private String type;
}
