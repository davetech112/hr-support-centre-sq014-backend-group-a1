package com.hrsupportcentresq014.services;

import com.hrsupportcentresq014.dtos.request.AwardRequestDTO;
import com.hrsupportcentresq014.dtos.response.AwardResponseDTO;

import java.nio.file.AccessDeniedException;

public interface AwardService {
    String createAward(AwardRequestDTO awardRequestDTO) throws AccessDeniedException;
}
