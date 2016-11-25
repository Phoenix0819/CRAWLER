package pri.wf.crawler.dto;

import java.util.List;

public class ScheduleDto {
	private String validateMessagesShowId;
	private Boolean status;
	private int httpstatus;
	private List<String> messages;
	private List<ScheduleQueDto> data;
	public List<ScheduleQueDto> getData() {
		return data;
	}
	public void setData(List<ScheduleQueDto> data) {
		this.data = data;
	}
	public String getValidateMessagesShowId() {
		return validateMessagesShowId;
	}
	public void setValidateMessagesShowId(String validateMessagesShowId) {
		this.validateMessagesShowId = validateMessagesShowId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public int getHttpstatus() {
		return httpstatus;
	}
	public void setHttpstatus(int httpstatus) {
		this.httpstatus = httpstatus;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}
