package com.example.clean_architecture.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    AuthenticationController(final AuthenticationManager authenticationManager,
                             final TokenService tokenService) {

        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    ResponseEntity<AuthenticationResponseDto> createToken(@RequestBody AuthenticationRequestDto authRequest) {
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(),
                                                                                              authRequest.password()));

        var user = (UserDetails) auth.getPrincipal();
        return ResponseEntity.ok(new AuthenticationResponseDto(tokenService.generateNewToken(user)));
    }
}
