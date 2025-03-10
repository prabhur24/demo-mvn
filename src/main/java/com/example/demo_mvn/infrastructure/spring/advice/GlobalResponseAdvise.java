package com.example.demo_mvn.infrastructure.spring.advice;

import lombok.extern.slf4j.Slf4j;

import com.example.demo_mvn.presentation.rest.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@Order(2)
@RestControllerAdvice
public class GlobalResponseAdvise implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body == null) {
            return ResponseEntity.notFound().build();
        }
        if (body instanceof ResponseEntity<?> || body instanceof ApiResponse<?>) {
            return body;
        } else {
            RequestMethod method = RequestMethod.resolve(request.getMethod());
            String requestURI = request.getURI().getPath();
            ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.OK, "Success", body);
            HttpStatus httpStatus = HttpStatus.OK;
            if (method == RequestMethod.DELETE) {
                httpStatus = HttpStatus.ACCEPTED;
                apiResponse = new ApiResponse<>(httpStatus, "Success", body);
            }
            if (method == RequestMethod.POST && StringUtils.contains(requestURI, "create")) {
                httpStatus = HttpStatus.CREATED;
                apiResponse = new ApiResponse<>(httpStatus, "Created", body);
            }
            return ResponseEntity.status(httpStatus).body(apiResponse);
        }
    }
}
