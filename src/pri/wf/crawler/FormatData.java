package pri.wf.crawler;

import com.google.gson.Gson;

public class FormatData<T> {
	
	public <T> T JsontoObj(StringBuffer result,T resultSet) {
		Gson gson = new Gson();
		resultSet= gson.fromJson(result.toString(), resultSet.getClass());
		return resultSet;
	}
}
