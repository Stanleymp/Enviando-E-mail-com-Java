package enviando.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.qrcode.ByteArray;

public class ObjetoEnviaEmail {

	private String userName = "[SEU E-MAIL GMAIL]";
	private String senha = "[SUA SENHA]";

	private String destinatarios = "";
	private String nomeRemetente = "";

	private String assunto = "";
	private String texto = "";

	public ObjetoEnviaEmail(String destinatarios, String nomeRemetente, String assunto, String texto) {
		this.destinatarios = destinatarios;
		this.nomeRemetente = nomeRemetente;
		this.assunto = assunto;
		this.texto = texto;
	}

	/*********ENVIO SEM ANEXO*********/
	public void enviaEmail(boolean envioHtml) {

		try {

			Properties properties = new Properties();
			properties.put("mail.smtp.ssl.trust", "*");
			properties.put("mail.smtp.ssl.checkserveridentity", "true");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.socketFactory.port", "465");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, senha);
				}

			});

			Address[] toUser = InternetAddress.parse(destinatarios);

			Message message = new MimeMessage(session); // Passa a sessão de conexão
			message.setFrom(new InternetAddress(userName, nomeRemetente)); // Quem está enviando
			message.setRecipients(Message.RecipientType.TO, toUser); // Para quem estou enviando

			message.setSubject(assunto); // ASSUNTO DO E-MAIL

			if (envioHtml) {
				message.setContent(texto, "text/html; charset=utf-8");
			} else {
				message.setText(texto); // CORPO DO E-MAIL
			}

			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/********ENVIO COM ANEXO********/
	public void enviaEmailAnexo(boolean envioHtml) {

		try {

			Properties properties = new Properties();
			properties.put("mail.smtp.ssl.trust", "*");
			properties.put("mail.smtp.ssl.checkserveridentity", "true");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.socketFactory.port", "465");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, senha);
				}

			});

			Address[] toUser = InternetAddress.parse(destinatarios);

			Message message = new MimeMessage(session); // Passa a sessão de conexão
			message.setFrom(new InternetAddress(userName, nomeRemetente)); // Quem está enviando
			message.setRecipients(Message.RecipientType.TO, toUser); // Para quem estou enviando

			message.setSubject(assunto); // ASSUNTO DO E-MAIL

			/*Parte 1 do e-mail que é texto e a descrição do e-mail*/
			MimeBodyPart corpoEmail = new MimeBodyPart();
			
			
			if (envioHtml) {
				corpoEmail.setContent(texto, "text/html; charset=utf-8");
			} else {
				corpoEmail.setText(texto); // CORPO DO E-MAIL
			}
			
			List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
			arquivos.add(simuladorDePDF());
			arquivos.add(simuladorDePDF());
			arquivos.add(simuladorDePDF());
			arquivos.add(simuladorDePDF());
			arquivos.add(simuladorDePDF());
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(corpoEmail);
			
			int index = 0;
			
			for (FileInputStream fileInputStream : arquivos) {
			/*parte 2 do e-mail que são os anexos em PDF*/
			MimeBodyPart anexoEmail = new MimeBodyPart();
			/*Onde é passado o simuladorDePDF você passa o seu arquivo gravado no banco de dados*/
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
			anexoEmail.setFileName("anexoemail"+index+".pdf");
			multipart.addBodyPart(anexoEmail);
			index++;
			}
			
			message.setContent(multipart);
			
			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Esse método simula o PDF ou qualquer arquivo que possa ser enviado por anexo
	 * no e-mail. Você pode pegar o arquivo no seu banco de dados base64, byte[],
	 * stream de arquivos. Pode estar em um banco de dados ou em uma pasta.
	 */

	private FileInputStream simuladorDePDF() throws Exception {
		
		Document document = new Document();
		File file = new File("fileanexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteudo de PDF anexo com Java Mail, esse texto é do PDF"));
		document.close();
		return new FileInputStream(file);
	}

}
