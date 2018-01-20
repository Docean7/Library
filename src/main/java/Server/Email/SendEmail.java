//public class SendEmail {
//    public static void main(String[] args) {
//        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
//        // Get a Properties object
//        Properties props = System.getProperties();
//        props.setProperty("mail.smtp.host", "smtp.gmail.com");
//        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
//        props.setProperty("mail.smtp.socketFactory.fallback", "false");
//        props.setProperty("mail.smtp.port", "465");
//        props.setProperty("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.debug", "true");
//        props.put("mail.store.protocol", "pop3");
//        props.put("mail.transport.protocol", "smtp");
//        final String username = "dbdrp30@gmail.com";//
//        final String password = "Smolonec359";
//        try {
//            Session session = Session.getDefaultInstance(props,
//                    new Authenticator() {
//                        protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication(username, password);
//                        }
//                    });
//
//            // -- Create a new message --
//            Message msg = new MimeMessage(session);
//
//            // -- Set the FROM and TO fields --
//            msg.setFrom(new InternetAddress("dbdrp30@gmail.com"));
//            msg.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse("denys.rachynskyi@nure.ua", false));
//            msg.setSubject("Hello");
//            msg.setText("How are you");
//            msg.setSentDate(new Date());
//            Transport.send(msg);
//            System.out.println("Message sent.");
//        } catch (MessagingException e) {
//            System.out.println("Error: " + e);
//        }
//    }
//}
