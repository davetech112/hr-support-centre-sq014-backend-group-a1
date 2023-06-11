package com.hrsupportcentresq014.services.serviceImpl;

import com.hrsupportcentresq014.dtos.request.AwardRequestDTO;
import com.hrsupportcentresq014.dtos.response.AwardResponseDTO;
import com.hrsupportcentresq014.entities.Award;

import com.hrsupportcentresq014.exceptions.AwardsNotFoundException;
import com.hrsupportcentresq014.repositories.AwardRepository;
import com.hrsupportcentresq014.services.AwardService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AwardsServiceImpl implements AwardService {

    private final AwardRepository awardsRepository;


    public AwardsServiceImpl(AwardRepository awardsRepository) {
        this.awardsRepository = awardsRepository;
    }

    @Override
    public String createAward(AwardRequestDTO awardRequestDTO) throws AwardsNotFoundException{




        Award awards = new Award();
        awards.setTitle(awardRequestDTO.getTitle());
        awards.setDescription(awardRequestDTO.getDescription());
        awards.setYear(LocalDate.now().getYear());
        Award savedAward = awardsRepository.save(awards);

        return "Award created successfully: " + savedAward.getTitle();
    }

    @Override
    public List<AwardResponseDTO> getAwardByYear(String year) {
        List<Award> foundAwards = awardsRepository.findAwardByYear(Integer.parseInt(year));

        if (foundAwards.isEmpty()) {
            throw new AwardsNotFoundException(String.format("Award not found for year %s", year));
        }

        return foundAwards.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    private AwardResponseDTO toResponseDTO(Award award) {
        return AwardResponseDTO.builder()
                .title(award.getTitle())
                .description(award.getDescription())
                .year(award.getYear())
                .build();
    }
}

