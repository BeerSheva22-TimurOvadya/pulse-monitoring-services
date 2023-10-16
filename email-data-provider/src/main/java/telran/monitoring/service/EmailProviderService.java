package telran.monitoring.service;

import telran.monitoring.dto.EmailNotificationData;

public interface EmailProviderService {
EmailNotificationData getEmailNotificationData(long patientId);
}