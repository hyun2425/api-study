package com.rest.api.controller.exception;

import com.rest.api.advice.exception.CAuthenticationEntryPointException;
import com.rest.api.model.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@RequestMapping("/exception")
@RestController
public class ExceptionController {

    @GetMapping("/entrypoint")
    public CommonResult entrypointException() {
        throw new CAuthenticationEntryPointException();
    }

    @GetMapping("/accessDenied")
    public CommonResult accessDeniedException() throws AccessDeniedException {
        throw new AccessDeniedException("");
    }

}
