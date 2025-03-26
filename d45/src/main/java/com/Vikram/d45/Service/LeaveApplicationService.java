package com.Vikram.d45.Service;

import com.Vikram.d45.Entity.LeaveApplication;
import com.Vikram.d45.Repository.LeaveApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveApplicationService {

    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    private EmailService emailService;

    // Save leave application (default status: Pending)
    public void saveLeaveApplication(LeaveApplication leaveApplication) {
        // Only set "Pending" if it's a new leave application
        if (leaveApplication.getId() == null) {
            leaveApplication.setStatus("Pending");
        }
        leaveApplicationRepository.save(leaveApplication);
    }

    // Get all leave applications
    public List<LeaveApplication> getAllLeaveApplications() {
        return leaveApplicationRepository.findAll();
    }

    // Get leave application by ID
    public LeaveApplication getLeaveApplicationById(Long id) {
        return leaveApplicationRepository.findById(id).orElse(null);
    }

    // Approve leave
    public String approveLeave(Long id) {
        Optional<LeaveApplication> optionalLeaveApplication = leaveApplicationRepository.findById(id);
        if (optionalLeaveApplication.isPresent()) {
            LeaveApplication leaveApplication = optionalLeaveApplication.get();
            leaveApplication.setStatus("Approved");
            leaveApplicationRepository.save(leaveApplication);

            // Send approval email
            emailService.sendEmail(leaveApplication.getEmail(), "Leave Approved", "Your leave application has been approved!");

            return "Leave Approved and Email Sent!";
        }
        return "Leave Application Not Found!";
    }

    // Reject leave
    public String rejectLeave(Long id) {
        Optional<LeaveApplication> optionalLeaveApplication = leaveApplicationRepository.findById(id);
        if (optionalLeaveApplication.isPresent()) {
            LeaveApplication leaveApplication = optionalLeaveApplication.get();
            leaveApplication.setStatus("Rejected");
            leaveApplicationRepository.save(leaveApplication);

            // Send rejection email
            emailService.sendEmail(leaveApplication.getEmail(), "Leave Rejected", "Your leave application has been rejected!");

            return "Leave Rejected and Email Sent!";
        }
        return "Leave Application Not Found!";
    }
}
