package org.innotrackers.demo.api.routers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
/*

@RestController
@RequestMapping("/api/csrf")
public class CsrfController {


    @Operation(summary = "Получить csrf токен")
    @GetMapping(path="/", produces = "application/json")
    public void getToken(HttpServletRequest request) {
        System.out.println(Arrays.toString(Arrays.stream(request.getCookies()).toArray()));
        System.out.println(request.getParameterNames().asIterator());
        System.out.println(Arrays.toString(request.getParameterValues("X-CSRF-TOKEN")));

    }

}
*/

@RestController
@RequestMapping("/api/csrf")
public class CsrfController {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void CsrfToken(){

    }
}