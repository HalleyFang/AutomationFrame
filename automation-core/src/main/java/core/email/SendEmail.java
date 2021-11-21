package core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendEmail {

    @Autowired
    SendMailService sendMailService;

    public void sendEmail(EmailModel emailModel) throws Exception {
        List<String> toList = emailModel.getToList();
        if (toList.size() == 0) {
            throw new Exception("emails can not be null");
        }
        if (emailModel.getSubject().isEmpty()) {
            throw new Exception("subject can not be null");
        }
        for (int i = 0; i < toList.size(); i++) {
            if (emailModel.getContentIsHtml()) {
                if (emailModel.getHasAttachments()) {
                    sendMailService.sendAttachmentsMail(toList.get(i), emailModel.getSubject(), emailModel.getContent(), emailModel.getFilePath());
                } else {
                    sendMailService.sendHtmlMail(toList.get(i), emailModel.getSubject(), emailModel.getContent());
                }
            } else {
                sendMailService.sendSimpleMail(toList.get(i), emailModel.getSubject(), emailModel.getContent());
            }
        }

    }
}
