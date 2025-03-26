package com.Vikram.d45.Controller;

import com.Vikram.d45.Entity.LeaveApplication;
import com.Vikram.d45.Service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.logging.Logger;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/student")
public class StudentController {

    private static final Logger logger = Logger.getLogger(StudentController.class.getName());

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @PostMapping("/applyLeave")
    public ResponseEntity<?> applyLeave(@RequestBody LeaveApplication leaveApplication) {
        try {
            // Validation: Ensure required fields are not empty
            if (leaveApplication.getName() == null || leaveApplication.getName().isEmpty() ||
                    leaveApplication.getEmail() == null || leaveApplication.getEmail().isEmpty() ||
                    leaveApplication.getReason() == null || leaveApplication.getReason().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields are required!");
            }

            leaveApplication.setStatus("Pending"); // Default status
            leaveApplicationService.saveLeaveApplication(leaveApplication);

            logger.info("Leave Application Submitted for: " + leaveApplication.getEmail());
            return ResponseEntity.ok("Leave Application Submitted!");
        } catch (Exception e) {
            logger.severe("Error submitting leave: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request.");
        }
    }
}
