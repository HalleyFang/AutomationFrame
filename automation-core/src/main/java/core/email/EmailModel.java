package core.email;

import java.util.List;

public class EmailModel {

    public List<String> getToList() {
        return toList;
    }

    public String getSubject() {
        return subject;
    }

    public Boolean getContentIsHtml() {
        return contentIsHtml;
    }

    public String getContent() {
        return content;
    }

    public Boolean getHasAttachments() {
        return hasAttachments;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setToList(List<String> toList) {
        this.toList = toList;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContentIsHtml(Boolean contentIsHtml) {
        this.contentIsHtml = contentIsHtml;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setHasAttachments(Boolean hasAttachments) {
        this.hasAttachments = hasAttachments;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private List<String> toList;
    private String subject;
    private Boolean contentIsHtml;
    private String content;
    private Boolean hasAttachments;
    private String filePath;

}
