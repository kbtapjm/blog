package kr.co.blog.common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
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
import javax.mail.internet.MimeUtility;

import kr.co.blog.domain.Board;

/**
 * E-mail 전송처리
 * @author jmpark
 *
 */
public class MailSend {
    private final static String ID = "kbtapjm";
    private final static String PASSWORD = "jmpark9214";
    private final static String HOST = "smtp.gmail.com";
    private final static String PORT = "465";
    private final static String AUTH = "true";
    
    /**
     * 메일 전송
     * @param params
     * @return
     */
    public static boolean mailsend(Map<String, Object> params) {
        String sendContent = null;
        
        String from = params.get("from").toString();
        String to = params.get("to").toString();
        String subject = params.get("subject").toString();
        String content = params.get("content").toString();
        String strMessage = params.get("message").toString();
        Board board = (Board)params.get("board");
        
        // ----------------------------------------------------------------------------------------
        // 메일 내용 설정
        // ----------------------------------------------------------------------------------------
        boolean result = false;
        try {
            Message message = new MimeMessage(mailServerInitialize());
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            //message.setText(content);
            message.setSentDate(new Date());
            
            MimeBodyPart mail_text = new MimeBodyPart();
            
            Multipart mpt = new MimeMultipart();
            sendContent = content + "<br><br>" + strMessage;
            mail_text.setContent(sendContent, "text/html;charset=euc-kr");
            
            // ----------------------------------------------------------------------------------------
            // 첨부파일 존재시 파일 첨부 -> 여러건 처리
            // ----------------------------------------------------------------------------------------
            /*
            if(fileList.size() != 0) {
                for(AttachFile attachFile : fileList) {
                    if(attachFile == null) continue;
                    
                    FileDataSource fds = new FileDataSource(new File(FileUtil.PATH + "/" + attachFile.getFilename()));

                    MimeBodyPart mbp = new MimeBodyPart();
                    mbp.setDataHandler(new DataHandler(fds));
                    mbp.setFileName(MimeUtility.encodeText(fds.getName(), "KSC5601" , "B"));
                    
                    mpt.addBodyPart(mbp);
                }
            }
            */
            
            // 한개의 첨부파일 처리
            FileDataSource fds = new FileDataSource(new File(FileUtil.PATH + "/" + board.getFileName()));

            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setDataHandler(new DataHandler(fds));
            try {
                mbp.setFileName(MimeUtility.encodeText(fds.getName(), "KSC5601" , "B"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            
            mpt.addBodyPart(mbp);
            
            mpt.addBodyPart(mail_text);
            message.setContent(mpt);
            
            Transport.send(message);
            result = Boolean.TRUE;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * 자바 메일 설정
     * @return session
     */
    public static Session mailServerInitialize() {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.socketFactory.port", PORT);
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.port", PORT);
     
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() { 
                return new PasswordAuthentication(ID, PASSWORD); 
            }
        });
        
        return session;
    }
  
}
