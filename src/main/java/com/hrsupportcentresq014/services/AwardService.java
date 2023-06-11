package com.hrsupportcentresq014.services;

import com.hrsupportcentresq014.dtos.request.AwardRequestDTO;
import com.hrsupportcentresq014.dtos.response.AwardResponseDTO;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface AwardService {
    String createAward(AwardRequestDTO awardRequestDTO) throws AccessDeniedException;

    List<AwardResponseDTO> getAwardByYear(String year);
}
