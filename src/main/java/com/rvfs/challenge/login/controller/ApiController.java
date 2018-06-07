package com.rvfs.challenge.login.controller;

import com.rvfs.challenge.login.exception.ApiError;
import com.rvfs.challenge.login.model.Account;
import com.rvfs.challenge.login.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountException;

@RestController
public class ApiController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/api/hello")
    public ResponseEntity<?> hello() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String msg = String.format("Hello %s", name);
        return new ResponseEntity<Object>(msg, HttpStatus.OK);
    }

    @GetMapping(path = "/api/me", produces = "application/json")
    public Account me() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountService.findAccountByUsername(username);
    }

    @PostMapping(path = "/api/register", produces = "application/json")
    public ResponseEntity<?> register(@RequestBody Account account) {
        try {
            account.grantAuthority("ROLE_USER");
            return new ResponseEntity<Object>(
                    accountService.register(account), HttpStatus.OK);
        } catch (AccountException e) {
            e.printStackTrace();
            return new ResponseEntity<ApiError>(new ApiError(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
