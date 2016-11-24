package pri.wf.crawler.dto;

public class TrainNoQueryDto {
	private String station_train_code;
	private String train_no;
	private String start_station_name;
	private String from_station_telecode;
	private String end_station_name;
	private String to_station_telecode;
	public String getStation_train_code() {
		return station_train_code;
	}
	public void setStation_train_code(String station_train_code) {
		this.station_train_code = station_train_code;
	}
	public String getTrain_no() {
		return train_no;
	}
	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}
	public String getStart_station_name() {
		return start_station_name;
	}
	public void setStart_station_name(String start_station_name) {
		this.start_station_name = start_station_name;
	}
	public String getFrom_station_telecode() {
		return from_station_telecode;
	}
	public void setFrom_station_telecode(String from_station_telecode) {
		this.from_station_telecode = from_station_telecode;
	}
	public String getEnd_station_name() {
		return end_station_name;
	}
	public void setEnd_station_name(String end_station_name) {
		this.end_station_name = end_station_name;
	}
	public String getTo_station_telecode() {
		return to_station_telecode;
	}
	public void setTo_station_telecode(String to_station_telecode) {
		this.to_station_telecode = to_station_telecode;
	}
}
