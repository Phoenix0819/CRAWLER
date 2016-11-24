package pri.wf.crawler.dao;

import java.util.List;

import pri.wf.crawler.entity.TrainListEntity;

public interface TrainListDao {
	public int insert(TrainListEntity entity);

	public List<TrainListEntity> selectAll();
	
	public void updateFlag(TrainListEntity trainListEntity);
}
