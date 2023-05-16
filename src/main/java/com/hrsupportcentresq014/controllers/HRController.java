package com.hrsupportcentresq014.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hr")
@PreAuthorize("hasRole('HR')")
public class HRController {
  //todo This is a must read for guys working on these end points read below
// todo: if you want to mark any endpoint for fine-grained authorization refer to the Enum roles to see the specific roles
//  and the Enum permissions that the HR has for that particular endpoint then you can mark with @PreAuthorize("hasAuthority('hr:create')")

}
