# SMTP Provider Configuration Examples

This file contains configuration examples for popular SMTP providers.

## Gmail (Current Configuration)

```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: your-email@gmail.com
    password: your-app-password  # Use App Password, not account password
    from: noreply@equipmentsolutions.groupebpce.com
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
          connectiontimeout: 5000
          timeout: 5000
          writetimeout: 5000
```

**Setup Steps:**
1. Enable 2-Factor Authentication: https://myaccount.google.com/security
2. Generate App Password: https://myaccount.google.com/apppasswords
3. Use the 16-character password generated

---

## AWS SES (Simple Email Service)

```yaml
spring:
  mail:
    host: email-smtp.us-east-1.amazonaws.com
    port: 587
    username: SMTP_USERNAME_FROM_AWS
    password: SMTP_PASSWORD_FROM_AWS
    from: noreply@equipmentsolutions.groupebpce.com
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
          connectiontimeout: 5000
          timeout: 5000
          writetimeout: 5000
```

**Setup Steps:**
1. Create SMTP credentials in AWS SES console
2. Verify sender email address
3. Replace region (us-east-1) with your AWS region
4. Request production access if needed

---

## Microsoft Outlook / Office 365

```yaml
spring:
  mail:
    host: smtp-mail.outlook.com
    port: 587
    username: your-email@outlook.com
    password: your-password
    from: noreply@equipmentsolutions.groupebpce.com
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
          connectiontimeout: 5000
          timeout: 5000
          writetimeout: 5000
```

**Note:** For Office 365:
```yaml
host: smtp.office365.com
```

---

## SendGrid

```yaml
spring:
  mail:
    host: smtp.sendgrid.net
    port: 587
    username: apikey
    password: SG.your-sendgrid-api-key
    from: noreply@equipmentsolutions.groupebpce.com
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
          connectiontimeout: 5000
          timeout: 5000
          writetimeout: 5000
```

---

## Mailgun

```yaml
spring:
  mail:
    host: smtp.mailgun.org
    port: 587
    username: postmaster@your-domain.mailgun.org
    password: your-mailgun-password
    from: noreply@equipmentsolutions.groupebpce.com
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
          connectiontimeout: 5000
          timeout: 5000
          writetimeout: 5000
```

---

## Custom SMTP Server

```yaml
spring:
  mail:
    host: mail.your-company.com
    port: 587
    username: your-username
    password: your-password
    from: noreply@equipmentsolutions.groupebpce.com
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
          connectiontimeout: 5000
          timeout: 5000
          writetimeout: 5000
```

---

## Using Environment Variables (Recommended for Production)

Instead of hardcoding credentials, use environment variables:

```yaml
spring:
  mail:
    host: ${MAIL_HOST:smtp.gmail.com}
    port: ${MAIL_PORT:587}
    username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}
    from: ${MAIL_FROM:noreply@equipmentsolutions.groupebpce.com}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
          connectiontimeout: 5000
          timeout: 5000
          writetimeout: 5000
```

Then set environment variables:
```bash
export MAIL_HOST=smtp.gmail.com
export MAIL_PORT=587
export MAIL_USERNAME=your-email@gmail.com
export MAIL_PASSWORD=your-app-password
export MAIL_FROM=noreply@equipmentsolutions.groupebpce.com
```

---

## Troubleshooting Connection Issues

### Port 25 (Unencrypted)
- Not recommended
- Often blocked by ISPs
- Use port 587 (TLS) instead

### Port 465 (SSL/TLS)
```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 465
    properties:
      mail:
        smtp:
          auth: true
          socketFactory:
            port: 465
            class: javax.net.ssl.SSLSocketFactory
            fallback: false
```

### TLS vs SSL
- **TLS (587)**: Recommended, more secure
- **SSL (465)**: Older, less common
- Use TLS unless provider requires SSL

### Authentication Errors
1. Verify username is correct email address
2. Check password is correct (not account password for Gmail)
3. Ensure account has SMTP access enabled
4. Check if 2FA is enabled (use app-specific password)

### Connection Timeout
1. Check network/firewall allows outbound connection
2. Verify SMTP server is accessible from your network
3. Try increasing timeout values
4. Check if port 587 is blocked (try 465)

---

## Testing Connection

Add this to your Spring Boot application:

```java
@Component
public class MailConnectionTest {
    
    @Autowired
    private JavaMailSender mailSender;
    
    public void testConnection() {
        try {
            // This will test the SMTP connection
            mailSender.createMimeMessage();
            System.out.println("✓ SMTP connection successful!");
        } catch (Exception e) {
            System.err.println("✗ SMTP connection failed: " + e.getMessage());
        }
    }
}
```

---

## Best Practices

1. **Use Environment Variables** - Never commit credentials to git
2. **Use TLS** - Always use encrypted connections
3. **Use App Passwords** - For Gmail, use app-specific passwords
4. **Monitor Delivery** - Check delivery logs/bounces
5. **Test Thoroughly** - Test with different email clients
6. **Handle Errors** - Implement proper error handling and logging
7. **Rate Limiting** - Respect SMTP provider rate limits
8. **From Address** - Use verified sender addresses

---

## Additional Resources

- [Spring Boot Mail Documentation](https://spring.io/guides/gs/sending-email/)
- [Gmail App Passwords](https://support.google.com/accounts/answer/185833)
- [AWS SES Documentation](https://docs.aws.amazon.com/ses/)
- [SendGrid Documentation](https://sendgrid.com/docs/for-developers/sending-email/integrations/spring/)
- [Mailgun Documentation](https://documentation.mailgun.com/en/latest/quickstart-sending.html)

