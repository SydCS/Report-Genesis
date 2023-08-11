package com.gen.controller;

import com.alibaba.fastjson.JSON;
import com.gen.dto.ChartRequest;
import com.gen.dto.ChartResponse;
import com.gen.utils.ResultUtil;
import com.gen.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import java.nio.charset.StandardCharsets;

@Api(tags = "查询")
@RestController
@RequestMapping("/api")
@CrossOrigin()
@Log4j2
public class ChainController {

    @PostMapping("/option")
    @ApiOperation(value = "获得echarts图表的option配置项")
    public Result getOption(@RequestBody ChartRequest chartRequest) {
        log.info(chartRequest);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String uri = "http://127.0.0.1:5000/getOption";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
        String chartDTO = JSON.toJSONString(chartRequest);
        HttpEntity<String> entity = new HttpEntity<String>(chartDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

        ChartResponse chartResp = JSON.parseObject(response.getBody(), ChartResponse.class);
        log.info(chartResp);
        return ResultUtil.success(chartResp);

//        return ResultUtil.success();
    }

    @PostMapping("/report")
    @ApiOperation(value = "生成新报告")
    @ApiImplicitParam(name = "message", value = "原始报告模板", required = true)
    public Result getReport(@RequestBody String message) {
        log.info(message);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String uri = "http://127.0.0.1:5000/getReport?message=" + message;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        log.info(response);

        return ResultUtil.success(response.getBody());

//        return ResultUtil.success("共有5个订单。最早的订单是由客户ID为1000000003的客户发送的。最早的订单的发送地是Muncie，接收地是1 Sunny Place。");

    }

    @PostMapping("/query")
    @ApiOperation(value = "自然语言查询数据库")
    @ApiImplicitParam(name = "message", value = "查询语句", required = true)
    @RequiresRoles("admin")
    public Result query(@RequestBody String question) {
        log.info(question);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String uri = "http://127.0.0.1:5000/getAnswer?question=" + question;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> answer = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        log.info(answer);

        return ResultUtil.success(answer.getBody());

//        return ResultUtil.success();
    }

}
