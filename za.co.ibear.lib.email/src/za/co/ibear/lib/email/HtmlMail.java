package za.co.ibear.lib.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import za.co.ibear.lib.email.constant.MailConstant;

public class HtmlMail {
	private String from = MailConstant.USER;  
	private String subject;
	private String message;
	private String[] toAddresses;

	public HtmlMail(String subject,String message,String[] toAddresses) throws Exception {
		this.subject =subject;
		this.message = message;
		this.toAddresses = toAddresses;
	}

	public void send() throws Exception {
		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName(MailConstant.HOST);
			email.setSmtpPort(MailConstant.PORT);
			email.setAuthenticator(new DefaultAuthenticator(MailConstant.USER,MailConstant.PASSWORD));
			email.setSSLOnConnect(true);
			email.setFrom(from);
			email.setSubject(subject);
			
			email.setHtmlMsg(message);
			email.setTextMsg("Your email client does not support HTML messages");
			
			for(String a:toAddresses) {
				email.addTo(a);
			}

			email.send();
		} catch (Exception e) {
			throw e;
		}
	}

	protected void p(String l) {
		System.out.println(this.getClass().getSimpleName() + ":) " + l);
	}

}
