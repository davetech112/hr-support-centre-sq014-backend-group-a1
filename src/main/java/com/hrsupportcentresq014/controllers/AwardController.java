package com.hrsupportcentresq014.controllers;

import com.hrsupportcentresq014.dtos.request.AwardRequestDTO;
import com.hrsupportcentresq014.dtos.response.AwardResponseDTO;
import com.hrsupportcentresq014.services.AwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.nio.file.AccessDeniedException;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/awards")
public class AwardController {
    private final AwardService awardsService;

    @PostMapping("/register")
    public ResponseEntity<String> createAward(@RequestBody AwardRequestDTO awardRequestDTO) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean isHrUser = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("HR"));

        if (!isHrUser) {
            throw new AccessDeniedException("Only HR users are allowed to create awards.");
        }

        String response = awardsService.createAward(awardRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{year}")
    public ResponseEntity<List<AwardResponseDTO>> getAwardByYear(@PathVariable("year") String year) {
        var response = awardsService.getAwardByYear(year);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
