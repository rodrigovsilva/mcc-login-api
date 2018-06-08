package com.rvfs.challenge.mcc.login.controller;

import com.rvfs.challenge.mcc.login.exception.ApiError;
import com.rvfs.challenge.mcc.login.model.Account;
import com.rvfs.challenge.mcc.login.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;


@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String msg = String.format("Server is OK, %s", name);
        return new ResponseEntity<Object>(msg, HttpStatus.OK);
    }

    @GetMapping(path = "/user/me", produces = "application/json")
    public Account me() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountService.findAccountByUsername(username);
    }

    @PostMapping(path = "/register", produces = "application/json")
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
