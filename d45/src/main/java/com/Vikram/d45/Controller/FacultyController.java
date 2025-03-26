package com.Vikram.d45.Controller;

import com.Vikram.d45.Entity.LeaveApplication;
import com.Vikram.d45.Service.LeaveApplicationService;
import com.Vikram.d45.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private static final Logger logger = Logger.getLogger(FacultyController.class.getName());

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @Autowired
    private EmailService emailService;

    // View all leave applications
    @GetMapping("/viewApplications")
    public ResponseEntity<List<LeaveApplication>> viewApplications() {
        List<LeaveApplication> applications = leaveApplicationService.getAllLeaveApplications();
        return ResponseEntity.ok(applications);
    }

    // Approve leave and send email
    @PutMapping("/approveLeave/{id}")
    public ResponseEntity<String> approveLeave(@PathVariable Long id) {
        LeaveApplication leaveApplication = leaveApplicationService.getLeaveApplicationById(id);

        if (leaveApplication != null) {
            leaveApplication.setStatus("Approved");
            leaveApplicationService.saveLeaveApplication(leaveApplication);

            // Check if email is available
            if (leaveApplication.getEmail() != null) {
                emailService.sendEmail(leaveApplication.getEmail(), "Leave Approved", "Your leave application has been approved!");
                logger.info("Approval email sent to: " + leaveApplication.getEmail());
            } else {
                logger.warning("No email found for Leave ID: " + id);
            }

            return ResponseEntity.ok("Leave Approved and Email Sent!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave Application Not Found!");
        }
    }

    // Reject leave and send email
    @PutMapping("/rejectLeave/{id}")
    public ResponseEntity<String> rejectLeave(@PathVariable Long id) {
        LeaveApplication leaveApplication = leaveApplicationService.getLeaveApplicationById(id);

        if (leaveApplication != null) {
            leaveApplication.setStatus("Rejected");
            leaveApplicationService.saveLeaveApplication(leaveApplication);

            // Check if email is available
            if (leaveApplication.getEmail() != null) {
                emailService.sendEmail(leaveApplication.getEmail(), "Leave Rejected", "Your leave application has been rejected!");
                logger.info("Rejection email sent to: " + leaveApplication.getEmail());
            } else {
                logger.warning("No email found for Leave ID: " + id);
            }

            return ResponseEntity.ok("Leave Rejected and Email Sent!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave Application Not Found!");
        }
    }
}
