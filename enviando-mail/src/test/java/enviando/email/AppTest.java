package enviando.email;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	@org.junit.Test
	public void testeEmail() throws Exception{
		
		StringBuilder stringBuilderTextoEmail = new StringBuilder();
		
		stringBuilderTextoEmail.append("<center><h1 font-family:roboto>Olá</h1>, <br/> </center>");
		stringBuilderTextoEmail.append("<center><h2 font-family:roboto>Você está recebendo um e-mail personalizado com HTML</h2> <br/></center>");
		stringBuilderTextoEmail.append("<center><h3 font-family:roboto>Clique no botão abaixo para ter acesso ao google</h3> <br/></center>");
		stringBuilderTextoEmail.append("<center><a target=\"_blank\" href=\"http://www.google.com.br\" style=\"color: #2F4F4F; padding:14px 25px; text-align:center; "
				+ "text-decoration:none; display: inline-block; border-radius: 15px; font-size:20px; font-family:roboto; border: 2px solid #00FA9A; background-color:#F5FFFA;\" > Acessar Página</a><br/><br/></center>");
		stringBuilderTextoEmail.append("<span font-family:roboto; style=\"font-size:8px\">Ass.: Rayane Maciel </span>");
				
				
		ObjetoEnviaEmail enviaEmail = 
				new ObjetoEnviaEmail("rayanedevteste@gmail.com", 
				"Rayane M.", 
				"Testando Email com Java", stringBuilderTextoEmail.toString());
		
		enviaEmail.enviarEmailAnexo(true);
		
		
		
		
	
	}
	
}
