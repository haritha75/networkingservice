package com.revature.revhire.Controllers;


import com.revature.revhire.models.Employee;
import com.revature.revhire.models.Job;
import com.revature.revhire.repositories.EmployeeRepository;
import com.revature.revhire.repositories.JobRepository;
import com.revature.revhire.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobRepository jobRepository;



    @PostMapping("/employees")
    public String registerUser(@RequestParam String email, @RequestParam String username) {
        if (employeeRepository.findByEmail(email).isPresent() && employeeRepository.findByUserName(username).isPresent()) {
            notificationService.sendRegistrationEmail(email, username);
            return "Registration successful, notification sent to " + email;
        } else {
            return "User not found. Registration notification could not be sent.";
        }
    }
    @PostMapping("/employees/login")
    public String loginUser(@RequestParam String email, @RequestParam String username) {
        if (employeeRepository.findByEmail(email).isPresent() && employeeRepository.findByUserName(username).isPresent()) {
            notificationService.sendLoginSuccessEmail(email, username);
            return "Login successful, notification sent to " + email;
        } else {
            return "User not found. Login notification could not be sent.";
        }
    }
    @PostMapping("/employees/reset-password")
    public String resetPassword(@RequestParam String email) {
        if (employeeRepository.findByEmail(email).isPresent()) {
            notificationService.sendPasswordResetEmail(email);
            return "Password reset request successful, notification sent to " + email;
        } else {
            return "Email not found. Password reset notification could not be sent.";
        }
    }

    @PostMapping("/employees/application-status")
    public String updateApplicationStatus(@RequestParam String email, @RequestParam String applicationId, @RequestParam String status) {
        if (employeeRepository.findByEmail(email).isPresent()) {
            notificationService.sendApplicationStatusUpdateEmail(email, applicationId, status);
            return "Application status updated, notification sent to " + email;
        } else {
            return "User not found. Application status notification could not be sent.";
        }
    }

    @PostMapping("/job/createjob")
    public String createJob(@RequestBody Job job) {
        jobRepository.save(job);

        List<Employee> matchingEmployees = employeeRepository.findAll().stream()
                .filter(employee -> employee.getSkills().stream()
                        .anyMatch(skill -> job.getSkills().stream()
                                .map(s -> s.getSkillName())
                                .collect(Collectors.toList())
                                .contains(skill.getSkillName())))
                .collect(Collectors.toList());

        notificationService.sendJobMatchNotification(job, matchingEmployees);

        return "Job created successfully, notifications sent to matching employees.";
    }

    @PostMapping("/new-application")
    public String sendNewApplicationNotification(
            @RequestParam String employerEmail,
            @RequestParam String candidateName,
            @RequestParam String jobTitle,
            @RequestParam String applicationLink) {

        notificationService.sendNewApplicationNotification(employerEmail, candidateName, jobTitle, applicationLink);
        return "New application notification sent to " + employerEmail;
    }

    @PostMapping("/profile-viewed")
    public String sendProfileViewedNotification(
            @RequestParam String candidateEmail,
            @RequestParam String employerName) {

        notificationService.sendProfileViewedNotification(candidateEmail, employerName);
        return "Profile viewed notification sent to " + candidateEmail;
    }

    @PostMapping("/job-alert")
    public String sendNewJobAlert(
            @RequestParam String candidateEmail,
            @RequestParam String jobTitle,
            @RequestParam String jobLink) {

        notificationService.sendNewJobAlert(candidateEmail, jobTitle, jobLink);
        return "Job alert sent to " + candidateEmail;
    }

    @PostMapping("/application-status-update")
    public String sendApplicationStatusUpdate(
            @RequestParam String candidateEmail,
            @RequestParam String status,
            @RequestParam String jobTitle) {

        notificationService.sendApplicationStatusUpdate(candidateEmail, status, jobTitle);
        return "Application status update sent to " + candidateEmail;
    }

    @PostMapping("/application-confirmation")
    public String sendApplicationConfirmation(
            @RequestParam String candidateEmail,
            @RequestParam String jobTitle) {

        notificationService.sendApplicationConfirmation(candidateEmail, jobTitle);
        return "Application confirmation sent to " + candidateEmail;
    }
}
