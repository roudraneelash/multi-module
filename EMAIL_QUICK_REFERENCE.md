# Email Notification Quick Reference

## Sending Notifications

The notification system works as follows:

```java
// 1. Create a FinanceRequest object with required data
FinanceRequest financeRequest = FinanceRequest.builder()
    .financeEntityName("ABC Finance Ltd")
    .dealerName("Benelax Motors")
    .assetName("Excavator ZX200")
    // ... other fields
    .build();

// 2. Create recipients list
List<Recipients> recipients = Arrays.asList(
    Recipients.builder().emailId("user@example.com").build()
);

// 3. Create notification request
NotificationRequest notificationRequest = NotificationRequest.builder()
    .recipients(recipients)
    .context(new NotificationContext<>(financeRequest))
    .notificationType(Notification.PAYOFF_ASSET)  // or INSURANCE_EXPIRATION, LIMIT_EXPIRATION, TRANSFORMATION_ASSET
    .build();

// 4. Send (inject ObtainNotificationClient and call)
client.sendNotification(notificationRequest);
```

## Supported Notification Types

1. **INSURANCE_EXPIRATION** - When insurance coverage expires
   - Template: `body/insurance-expiration.html`
   - Context Model: `InsuranceExpiry`

2. **LIMIT_EXPIRATION** - When credit limit expires
   - Template: `body/limit-expiration.html`
   - Context Model: `LimitExpiry`

3. **PAYOFF_ASSET** - When asset is marked for payoff
   - Template: `body/payoff-asset.html`
   - Context Model: `PayoffAsset`

4. **TRANSFORMATION_ASSET** - When asset is scheduled for transformation
   - Template: `body/transformation-asset.html`
   - Context Model: `PayoffAsset`

## Template Structure

```
templates.email/
├── layout.html                    # Main layout with header/footer
├── fragments/
│   ├── header.html               # Logo and header section
│   └── footer.html               # Copyright and contact links
└── body/
    ├── insurance-expiration.html  # Insurance expiration template
    ├── limit-expiration.html      # Limit expiration template
    ├── payoff-asset.html          # Payoff asset template
    └── transformation-asset.html  # Transformation asset template
```

## Available Template Variables

### Global Variables (all templates)
- `${logoUrl}` - Company logo URL
- `${copyrightUrl}` - Copyright link
- `${contactUrl}` - Contact page link

### Data Variables (depends on notification type)

**InsuranceExpiry / LimitExpiry:**
- `${data.financeEntity_name}` - Finance entity name
- `${data.dealer_name}` - Dealer name
- `${data.expiry_Date}` - Expiration date

**PayoffAsset / TransformationAsset:**
- `${data.financeEntity_name}` - Finance entity name
- `${data.dealer_name}` - Dealer name
- `${data.asset_Name}` - Asset name
- `${data.creator}` - Created by user
- `${data.approver}` - Approved by user
- `${data.payoff_status}` - Payoff status
- `${data.type_of_business}` - Type of business
- `${data.serial_number}` - Serial number
- `${data.manufacturer}` - Manufacturer
- `${data.internal_ref_number}` - Internal reference number
- `${data.disb_date}` - Disbursement date
- `${data.outstanding_amount}` - Outstanding amount

## SMTP Configuration

**File:** `bootstrap/src/main/resources/application.yml`

### Gmail Configuration (Recommended for Testing)
1. Enable 2-factor authentication on Google Account
2. Generate App Password: https://support.google.com/accounts/answer/185833
3. Use the generated password in `spring.mail.password`

### Custom SMTP Server
Update the following properties:
```yaml
spring:
  mail:
    host: your-smtp-host.com
    port: 587
    username: your-username
    password: your-password
```

## Troubleshooting

### Email Not Sending
- Verify SMTP credentials in `application.yml`
- Check firewall/network access to SMTP server
- Verify recipient email addresses are valid
- Check application logs for detailed error messages

### Template Not Found
- Ensure template files exist in `templates.email/body/`
- Verify template file names match NotificationTemplate enum

### Variable Not Rendering
- Check variable names use correct case (camelCase or snake_case as defined)
- Ensure data object is properly populated
- Verify NotificationTemplate enum mapping

## Email Design Notes

- Templates are responsive and mobile-friendly
- Email clients: Outlook, Gmail, Apple Mail, Thunderbird
- Maximum width: 600px for desktop
- Inline CSS for better email client compatibility
- All styles are email-safe (no external stylesheets)

