package br.com.clrf.web.controller;

import br.com.clrf.service.PasswordUseCase;
import br.com.clrf.web.dto.PasswordRequest;
import br.com.clrf.web.dto.PasswordResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-password")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordUseCase useCase;

    @PostMapping("/validate")
    public ResponseEntity<PasswordResponse> validatePassword(@Valid
            @RequestBody PasswordRequest request) {

        boolean isValid = useCase.execute(request.password());
        return ResponseEntity.ok(new PasswordResponse(isValid));
    }
}


//package br.com.clrf.web.controller;
//
//import br.com.clrf.service.PasswordUseCase;
//import br.com.clrf.web.dto.PasswordRequest;
//import br.com.clrf.web.dto.PasswordResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api-password")
//@RequiredArgsConstructor
//public class PasswordController {
//
//    private final PasswordUseCase useCase;
//
//    @PostMapping("/validate")
//    public ResponseEntity<PasswordResponse> validatePassword(
//            @RequestBody(required = false) PasswordRequest request) {
//
//        if (request == null || request.password() == null) {
//            return ResponseEntity.badRequest()
//                    .body(new PasswordResponse(false));
//        }
//
//        boolean isValid = useCase.execute(request.password());
//        return ResponseEntity.ok(new PasswordResponse(isValid));
//    }
//}
