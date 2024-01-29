package com.bulade.donor.common.utils;

import com.alibaba.fastjson2.JSON;
import com.bulade.donor.common.exception.BusinessException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@Data
public class HttpServletResponseUtils {

    private HttpServletResponseUtils() {
    }

    public static void writeAndFlush(HttpServletResponse response, Object o, HttpStatus httpStatus) {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.addHeader("Content-Type", "application/json");
            response.setStatus(httpStatus.value());
            outputStream.write(JSON.toJSONBytes(o));
            outputStream.flush();
        } catch (IOException ioException) {
            log.error("HttpServletResponse报错", ioException);
            throw new BusinessException(ioException.getMessage(), ioException);
        }
    }

}
