package pri.wf.crawler;

import com.google.gson.Gson;

public class FormatData {
	
	public <V> V JsontoObj(StringBuffer jsonString,V resultSet) {
		Gson gson = new Gson();
		resultSet= gson.fromJson(jsonString.toString(), resultSet.getClass());
		return resultSet;
	}
}
