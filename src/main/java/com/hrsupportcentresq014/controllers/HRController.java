package com.hrsupportcentresq014.controllers;

import com.hrsupportcentresq014.dtos.request.CreateStaffRequest;
import com.hrsupportcentresq014.dtos.response.CreateStaffResponse;
import com.hrsupportcentresq014.dtos.response.JobSearchResponse;
import com.hrsupportcentresq014.dtos.response.StaffProfileDTO;
import com.hrsupportcentresq014.dtos.response.ViewStaffResponse;
import com.hrsupportcentresq014.exceptions.EmployeeNotFoundException;
import com.hrsupportcentresq014.exceptions.NoJobsFoundException;
import com.hrsupportcentresq014.exceptions.UserAlreadyExistsException;
import com.hrsupportcentresq014.services.HrService;
import com.hrsupportcentresq014.services.JobService;
import com.hrsupportcentresq014.utils.PaginationConstants;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hr")
//@PreAuthorize("hasRole('HR')")
public class HRController {
  //  todo This is a must read for guys working on these end points read below
  // todo: if you want to mark any endpoint for fine-grained authorization refer to the Enum roles to see the specific roles
  //  and the Enum permissions that the HR has for that particular endpoint then you can mark with @PreAuthorize("hasAuthority('hr:create')")
    private final HrService hrService;
    private final JobService jobService;
    @PostMapping("/register")
    public ResponseEntity<CreateStaffResponse> registerStaffByHR(@Valid @RequestBody CreateStaffRequest staff) throws UserAlreadyExistsException, MessagingException {
        return hrService.registerStaff(staff);
    }

  @GetMapping("/{id}")
// @PreAuthorize("hasRole('HR')")
  public ResponseEntity<StaffProfileDTO> getStaffProfile(@PathVariable("id") String id) throws  EmployeeNotFoundException {
    StaffProfileDTO staffProfileDTO = hrService.getStaffProfile(id);
    if (staffProfileDTO != null) {
      return ResponseEntity.ok(staffProfileDTO);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/view-all-staff")
    public ViewStaffResponse viewAllStaff(@RequestParam(value = "pageNo", defaultValue = PaginationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = PaginationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                          @RequestParam(value = "sortBy", defaultValue = PaginationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                          @RequestParam(value = "sortDir", defaultValue = PaginationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
      return hrService.viewAllStaff(pageNo, pageSize, sortBy, sortDir);
    }
  @PreAuthorize("hasRole('HR')")
  @GetMapping("/filter-jobs")
  public ResponseEntity<Page<JobSearchResponse>> filterJob(
          @RequestParam(name = "page", defaultValue = "0") Integer page,
          @RequestParam(name = "size", defaultValue = "6") Integer size,
          @RequestParam(value = "keywords", required = false) String keywords,
          @RequestParam(value = "filter", defaultValue = "newest") String filter,
          @RequestParam(value = "department", required = false) String department) throws NoJobsFoundException {
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(jobService.filterAllJobs(keywords, filter, department, pageable));
  }
}
