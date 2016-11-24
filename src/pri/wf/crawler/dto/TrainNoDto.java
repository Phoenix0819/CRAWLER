package pri.wf.crawler.dto;

import java.util.List;

public class TrainNoDto {
	private String validateMessagesShowId;
	private String status;
	private int httpstatus;
	private List<String> messages;
	private TrainNoDataDto data;
	public TrainNoDataDto getData() {
		return data;
	}
	public void setData(TrainNoDataDto data) {
		this.data = data;
	}
	public String getValidateMessagesShowId() {
		return validateMessagesShowId;
	}
	public void setValidateMessagesShowId(String validateMessagesShowId) {
		this.validateMessagesShowId = validateMessagesShowId;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
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
