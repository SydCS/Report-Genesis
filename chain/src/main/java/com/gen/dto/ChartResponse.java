package com.gen.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(description = "大模型生成的option配置项和数据分析")
@Data
public class ChartResponse {
    String option;
    String analysis;
}
