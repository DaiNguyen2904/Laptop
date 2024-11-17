package com.soa.manageLaptop.controller;

import com.laptop.ManagerUsers.request.AuthenticationRequest;
import com.laptop.ManagerUsers.request.IntrospectRequest;
import com.laptop.ManagerUsers.dto.response.ApiResponse;
import com.laptop.ManagerUsers.dto.response.AuthenticationResponse;
import com.laptop.ManagerUsers.dto.response.IntrospectResponse;
import com.laptop.ManagerUsers.request.LogoutRequest;
import com.nimbusds.jose.JOSEException;
import com.soa.manageLaptop.service.AuthenticationService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;


    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            // Call the authentication service to authenticate the request
            var result = authenticationService.authenticate(request);
            // Build ApiResponse with result
            return ApiResponse.<AuthenticationResponse>builder()
                    .code(HttpStatus.OK.value())
                    .message("Authentication successful")
                    .result(result)
                    .build();
        } catch (RuntimeException e) {
            // Handle any runtime exceptions
            return ApiResponse.<AuthenticationResponse>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Authentication failed: " + e.getMessage())
                    .build();
        }
    }


    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        try {
            var result = authenticationService.introspect(request);
            return ApiResponse.<IntrospectResponse>builder()
                    .code(HttpStatus.OK.value())
                    .result(result)
                    .build();
        }
        catch (RuntimeException e){

            return ApiResponse.<IntrospectResponse>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }


    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }


}
