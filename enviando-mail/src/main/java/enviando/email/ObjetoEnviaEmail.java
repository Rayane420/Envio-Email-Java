package enviando.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
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

public class ObjetoEnviaEmail {

	private String userName = "rayanedevteste@gmail.com";
	private String senha = "configsd1542";
	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String descricaoEmail = "";
	
	
	public ObjetoEnviaEmail(String listaDestinatario, String nomeRemetente, String assuntoEmail, String descricaoEmail) {
		
		this.listaDestinatarios = listaDestinatario;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.descricaoEmail = descricaoEmail;
		
	}
	
	
	public void enviarEmailAnexo(boolean envioHtml) throws Exception {

		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.auth", "true"); // Autorização
		properties.put("mail.smtp.starttls", "true"); // Autenticação
		properties.put("mail.smtp.host", "smtp.gmail.com"); // Servidor gmail Google
		properties.put("mail.smtp.port", "465"); // Porta do servidor
		properties.put("mail.smtp.socketFactory.port", "465"); // Especifica a porta a ser conectada pelo socket
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Classe socket de conexão
																							// ao SMTP

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);
			}
		});

		// Criando a lista de e-mails
		Address[] toUser = InternetAddress.parse(listaDestinatarios);

		Message message = new MimeMessage(session); // Mensagem recebendo a sessão habilitada para envio
		message.setFrom(new InternetAddress(userName, nomeRemetente)); // Email que está enviando
		message.setRecipients(Message.RecipientType.TO, toUser); // Email de destino
		message.setSubject(assuntoEmail); // Assunto do e-mail
		
		
		/*Parte 1 do e-mail que é o texto e a descrição do e-mail*/
		MimeBodyPart corpoEmail = new MimeBodyPart();
		
		if(envioHtml) {
			
			corpoEmail.setContent(descricaoEmail, "text/html; charset=utf-8"); //Descrição do e-mail
			
		}else {
			corpoEmail.setText(descricaoEmail); // Descrição do e-mail
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
			
		/*Parte 2 do e-mail que são os anexos*/
		MimeBodyPart anexoEmail = new MimeBodyPart();
		
		/*Onde é passado o simuladorDePDF() poderia ser passado o arquivo gravado no banco de dados*/
		anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
		anexoEmail.setFileName("anexoemail"+index+".pdf");
		
		multipart.addBodyPart(anexoEmail);
		
		index++;
		}
		
		message.setContent(multipart);
		
		Transport.send(message);
	}
	
	/**
	 * Esse método simula o pdf ou qualquer arquivo que possa ser enviado por anexo no email
	 * É possível pegar o arquivo do banco de dados base 64, byte[], stream de Arquivos e etc
	 * Pode estar em um banco de dados, ou em uma pasta
	 * Retorna um PDF em branco com o texto do paragrafo de exemplo
	 * */
	
	private FileInputStream simuladorDePDF() throws Exception {
		Document document = new Document();
		File file = new File("fileanexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteudo do PDF anexo com Java Mail, esse texto é do PDF"));
		document.close();
		
		return new FileInputStream(file); 
		
	}

}
