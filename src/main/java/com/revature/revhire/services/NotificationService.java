package com.revature.revhire.services;

import com.revature.revhire.models.Employee;
import com.revature.revhire.models.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    // Registration email
    public void sendRegistrationEmail(String toEmail, String username) {
        String subject = "Welcome to Our Service";
        String body = String.format(
                "Hello %s,\n\nThank you for registering with our service!\n\n" +
                        "To get started, please log in using the following link:\n" +
                        "http://localhost:8080/api/employees/login\n\n" +
                        "If you have any questions or need assistance, feel free to reach out to our support team.\n\n" +
                        "Best regards,\nThe Team",
                username
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("g.gurumita@gmail.com");

        mailSender.send(message);
    }

    // Login success email
    public void sendLoginSuccessEmail(String toEmail, String username) {
        String subject = "Login Successful";
        String body = String.format(
                "Hello %s,\n\n" +
                        "You have successfully logged in to your account.\n\n" +
                        "If you did not log in or suspect any unusual activity, please contact our support team immediately.\n\n" +
                        "Best regards,\nThe Team",
                username
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("g.gurumita@gmail.com");

        mailSender.send(message);
    }

    // Password reset email
    public void sendPasswordResetEmail(String toEmail) {
        String subject = "Password Reset Request";
        String body = String.format(
                "Hello,\n\n" +
                        "We received a request to reset your password.\n\n" +
                        "If you did not request a password reset, please ignore this email. Otherwise, you can reset your password using the following link:\n" +
                        "http://localhost:8080/reset-password?email=%s\n\n" +
                        "Best regards,\nThe Team",
                toEmail
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("g.gurumita@gmail.com");

        mailSender.send(message);
    }

    // Application status update email
    public void sendApplicationStatusUpdateEmail(String toEmail, String applicationId, String status) {
        String subject = "Application Status Update";
        String body = String.format(
                "Hello,\n\n" +
                        "Your application with ID %s has been updated to: %s.\n\n" +
                        "If you have any questions, feel free to reach out to our team.\n\n" +
                        "Best regards,\nThe Hiring Team",
                applicationId, status
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("g.gurumita@gmail.com");

        mailSender.send(message);
    }

    // Job match notification email
    public void sendJobMatchNotification(Job job, List<Employee> matchingEmployees) {
        for (Employee employee : matchingEmployees) {
            String content = generateJobMatchContent(job, employee);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(employee.getEmail());
            message.setSubject("Job Match Notification");
            message.setText(content);
            message.setFrom("g.gurumita@gmail.com");

            mailSender.send(message);
        }
    }

    // Generate the email content for job match
    private String generateJobMatchContent(Job job, Employee employee) {
        return "Hi " + employee.getFirstName() + ",\n\n" +
                "We found a job that matches your skills!\n" +
                "Job Title: " + job.getJobTitle() + "\n" +
                "Job Description: " + job.getJobDescription() + "\n" +
                "Location: " + job.getJobLocation() + "\n" +
                "Experience Required: " + job.getExperience() + "\n\n" +
                "Best regards,\nThe Notification Team";
    }
    public void sendNewApplicationNotification(String toEmail, String candidateName, String jobTitle, String applicationLink) {
        String subject = "New Application Received";
        String body = String.format(
                "Hello,\n\nYou have received a new application for the position of %s from %s.\n\nYou can review the application here: %s\n\nBest regards,\nThe Job Portal Team",
                jobTitle, candidateName, applicationLink
        );
        sendEmail(toEmail, subject, body);
    }

    public void sendProfileViewedNotification(String toEmail, String employerName) {
        String subject = "Your Profile Was Viewed";
        String body = String.format(
                "Hello,\n\nYour profile was viewed by %s. Keep your profile updated to increase your chances of getting shortlisted.\n\nBest regards,\nThe Job Portal Team",
                employerName
        );
        sendEmail(toEmail, subject, body);
    }

    public void sendNewJobAlert(String toEmail, String jobTitle, String jobLink) {
        String subject = "New Job Alert";
        String body = String.format(
                "Hello,\n\nA new job that matches your profile has been posted:\n\nJob Title: %s\n\nYou can view and apply here: %s\n\nBest regards,\nThe Job Portal Team",
                jobTitle, jobLink
        );
        sendEmail(toEmail, subject, body);
    }

    public void sendApplicationStatusUpdate(String toEmail, String status, String jobTitle) {
        String subject = "Application Status Updated";
        String body = String.format(
                "Hello,\n\nThe status of your application for the position of %s has been updated to: %s.\n\nWe will notify you of further updates.\n\nBest regards,\nThe Job Portal Team",
                jobTitle, status
        );
        sendEmail(toEmail, subject, body);
    }

    public void sendApplicationConfirmation(String toEmail, String jobTitle) {
        String subject = "Application Submitted Successfully";
        String body = String.format(
                "Hello,\n\nYou have successfully submitted your application for the position: %s.\n\nWe will notify you of any updates.\n\nBest regards,\nThe Job Portal Team",
                jobTitle
        );
        sendEmail(toEmail, subject, body);
    }

    private void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("jobportal@example.com");

        mailSender.send(message);
    }
}
