package br.edu.ifsuldeminas.CarlosManoelinfoh.servico;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    private static final String REMETENTE_EMAIL =
            "carlosmanoel2ss9@gmail.com";

    private static final String REMETENTE_SENHA =
            "cxww qgdq tgrr zjoy";

    public static void enviarEmailRecuperacao(
            String destinatario, String linkRecuperacao) {

Properties propriedades = new Properties();

propriedades.put("mail.smtp.auth", "true");
propriedades.put("mail.smtp.starttls.enable", "true");
propriedades.put("mail.smtp.starttls.required", "true");
propriedades.put("mail.smtp.host", "smtp.gmail.com");
propriedades.put("mail.smtp.port", "587");

propriedades.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(
                propriedades,
                new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        REMETENTE_EMAIL,
                        REMETENTE_SENHA);
            }
        });

        try {

            MimeMessage mensagem = new MimeMessage(session);

            mensagem.setFrom(
                    new InternetAddress(REMETENTE_EMAIL));

            mensagem.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(destinatario));

            mensagem.setSubject(
                    "Recuperação de senha - Farmácia");

            mensagem.setText(
                    "Olá,\n\n"
                    + "Recebemos uma solicitação para redefinir sua senha.\n\n"
                    + "Acesse o link abaixo:\n\n"
                    + linkRecuperacao + "\n\n"
                    + "Este link é válido por tempo limitado.");

            Transport.send(mensagem);

            System.out.println(
                    "E-MAIL ENVIADO COM SUCESSO PARA: "
                    + destinatario);

        } catch (MessagingException e) {

            System.out.println("ERRO AO ENVIAR E-MAIL:");
            e.printStackTrace();
        }
    }
}