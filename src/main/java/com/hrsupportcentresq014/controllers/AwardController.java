package com.hrsupportcentresq014.controllers;

import com.hrsupportcentresq014.dtos.request.AwardRequestDTO;
import com.hrsupportcentresq014.dtos.response.AwardResponseDTO;
import com.hrsupportcentresq014.services.AwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/awards")
public class AwardController {
    private final AwardService awardsService;

    @PostMapping("/register")
//@PreAuthorize("hasRole('HR')")
    public ResponseEntity<String> createAward(@RequestBody AwardRequestDTO awardRequestDTO) throws AccessDeniedException {
        String response = awardsService.createAward(awardRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}