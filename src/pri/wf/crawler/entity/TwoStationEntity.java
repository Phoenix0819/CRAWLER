package pri.wf.crawler.entity;

public class TwoStationEntity {

	private int havetrain;
	private String from_station_telecode;
	private String from_station_name;
	private String to_station_telecode;
	private String to_station_name;

	public int getHavetrain() {
		return havetrain;
	}

	public void setHavetrain(int havetrain) {
		this.havetrain = havetrain;
	}

	public String getFrom_station_telecode() {
		return from_station_telecode;
	}

	public void setFrom_station_telecode(String from_station_telecode) {
		this.from_station_telecode = from_station_telecode;
	}

	public String getFrom_station_name() {
		return from_station_name;
	}

	public void setFrom_station_name(String from_station_name) {
		this.from_station_name = from_station_name;
	}

	public String getTo_station_telecode() {
		return to_station_telecode;
	}

	public void setTo_station_telecode(String to_station_telecode) {
		this.to_station_telecode = to_station_telecode;
	}

	public String getTo_station_name() {
		return to_station_name;
	}

	public void setTo_station_name(String to_station_name) {
		this.to_station_name = to_station_name;
	}

}
