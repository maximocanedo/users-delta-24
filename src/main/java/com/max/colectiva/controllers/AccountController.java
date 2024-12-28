package com.max.colectiva.controllers;

import com.max.colectiva.models.User;
import com.max.colectiva.models.dto.req.UserSignupRequest;
import com.max.colectiva.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/accounts")
public class AccountController {

    private final UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> createAccount(@RequestBody UserSignupRequest request) {
        return ResponseEntity.ok(userService.signup(request));
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getAccount(@PathVariable String username) {
        var account = userService.getByUsername(username);
        return ResponseEntity.ok()
                .eTag(Integer.toHexString(account.hashCode()))
                .body(account);
    }

    @GetMapping
    public ResponseEntity<Slice<User>> findAccounts(Pageable pageable, @RequestParam(required = false) String q) {
        return ResponseEntity.ok(userService.findAccounts(pageable, q));
    }


}
