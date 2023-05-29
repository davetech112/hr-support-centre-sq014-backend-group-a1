package com.hrsupportcentresq014.services.serviceImpl;

import com.hrsupportcentresq014.dtos.request.JobSearchRequest;
import com.hrsupportcentresq014.dtos.response.JobResponseDto;
import com.hrsupportcentresq014.dtos.response.JobSearchResponse;
import com.hrsupportcentresq014.entities.Job;
import com.hrsupportcentresq014.exceptions.NoJobsFoundException;
import com.hrsupportcentresq014.repositories.JobRepository;
import com.hrsupportcentresq014.services.JobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public JobServiceImpl( MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public Page<JobSearchResponse> searchJobs(String keywords, String filter, String department, Pageable pageable) throws NoJobsFoundException {
        Query query = new Query().with(pageable);
        List<Criteria> criteriaList = new ArrayList<>();

        if (keywords != null) {
            criteriaList.add(Criteria.where("title").regex(keywords, "i")); // Using regex for case-insensitive search
        }

        if (department != null) {
            criteriaList.add(Criteria.where("departmentName").is(department));
        }

        if ("newest".equalsIgnoreCase(filter)) {
            // Sort by descending order of createdOn field to get the newest jobs
            query.with(Sort.by(Sort.Direction.DESC, "createdOn"));
        } else if ("oldest".equalsIgnoreCase(filter)) {
            // Sort by ascending order of createdOn field to get the oldest jobs
            query.with(Sort.by(Sort.Direction.ASC, "createdOn"));
        }

        if ("past_week".equalsIgnoreCase(filter)) {
            // Filter jobs created in the past week
            LocalDate weekAgo = LocalDate.now().minusWeeks(1);
            criteriaList.add(Criteria.where("createdOn").gte(weekAgo.atStartOfDay()));
        }

        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        Page<Job> jobPage = PageableExecutionUtils.getPage(mongoTemplate.find(query, Job.class), pageable, () -> mongoTemplate.count(query, Job.class));

        if (jobPage.isEmpty()) {
            throw new NoJobsFoundException("No Jobs found");
        }

        List<JobResponseDto> jobResponseDtoList = jobPage.getContent().stream().map(this::toJobResponseDTO).collect(Collectors.toList());
        JobSearchResponse jobSearchResponse = new JobSearchResponse(jobResponseDtoList, jobPage.getTotalPages(), jobPage.getTotalElements(), null);
        return new PageImpl<>(Collections.singletonList(jobSearchResponse), pageable, 1);
    }

    private JobResponseDto toJobResponseDTO(Job job) {
        return JobResponseDto.builder()
                .title(job.getTitle())
                .description(job.getDescription())
                .departmentName(job.getDepartmentName())
                .closingDate(job.getClosingDate())
                .createdOn(job.getCreatedOn().toString())
                .isActive(job.getIsActive())
                .build();
    }
}
