package pri.wf.crawler;

import com.google.gson.Gson;

public class FormatData {
	
	public <V> V JsontoObj(StringBuffer jsonString,Class<V> resultType) {
		Gson gson = new Gson();
		V resultSet= gson.fromJson(jsonString.toString(), resultType);
		return resultSet;
	}
}
