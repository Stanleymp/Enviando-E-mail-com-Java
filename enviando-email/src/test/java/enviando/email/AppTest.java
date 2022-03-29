package enviando.email;

import static org.junit.Assert.assertTrue;

import java.nio.charset.MalformedInputException;
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

public class AppTest {

	
	
	@org.junit.Test
	public void testeEmail() throws Exception{

		StringBuilder stringBuilderTextoEmail = new StringBuilder();
		
		stringBuilderTextoEmail.append("Olá!! <br/><br/>");
		stringBuilderTextoEmail.append("Clique no botão abaixo para ver seu Anime Favorito!!<br/><br/>");
		
		stringBuilderTextoEmail.append("<a href=\"https://betteranime.net/anime/legendado/kimetsu-no-yaiba-yuukaku-hen\" style=\"padding: 14px 25px; text-align: center; text-decoration: none; display: inline-block; border-radius: 10px; font-size: 20px; background-color: dimgray; font-weight: bold; text-shadow: 0.1em 0.1em 0.2em black; font-family: 'Brush Script MT', cursive; color: black; border: 3px solid black;\" >Demon Slayer 2ª Temporada</a>");
		
		ObjetoEnviaEmail enviarEmail = new ObjetoEnviaEmail("kauecomsyruskayrus@gmail.com, stanleymp99@hotmail.com", 
				"Aew!!", 
				
				"VENHA VER ANIME!!! HEHEHE", 
				
				stringBuilderTextoEmail.toString());
		
		enviarEmail.enviaEmailAnexo(true);
		
		Thread.sleep(2000);
	}

}
