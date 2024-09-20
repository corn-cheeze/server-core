package com.corncheeze.core.adapter.in.web;

import com.corncheeze.core.adapter.in.web.request.MemberJoinInfo;
import com.corncheeze.core.application.port.in.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void join(@RequestBody MemberJoinInfo memberJoinInfo) {
        userService.join(memberJoinInfo.toNewMember());
    }
}
