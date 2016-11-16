package pri.wf.crawler.service;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.modelmapper.ModelMapper;

import com.sun.media.sound.ModelMappedInstrument;

import pri.wf.crawler.dao.TrainDataDao;
import pri.wf.crawler.dao.TwoStationDao;
import pri.wf.crawler.dto.ResultDto;
import pri.wf.crawler.dto.ResultQueDto;
import pri.wf.crawler.dto.ResultQueTrainDto;
import pri.wf.crawler.entity.ScheduleEntity;
import pri.wf.crawler.entity.TwoStationEntity;

public class ResultService {
	public void resultAdd(ResultDto resultDto) {
		String resource = "pri/wf/crawler/config/mybatis-config.xml";
		Reader reader;
		try {
			reader = Resources.getResourceAsReader(resource);
			
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			SqlSession session = factory.openSession();
			//SqlSession session2 = factory.openSession();
			TrainDataDao trainDataDao=session.getMapper(TrainDataDao.class);
			//TwoStationDao twoStationDao=session2.getMapper(TwoStationDao.class);
			List<ResultQueDto> resultQueDtos=resultDto.getData();
			ModelMapper modelMapper=new ModelMapper();
			//TwoStationEntity twoStationEntity =modelMapper.map(resultDto.getData(), TwoStationEntity.class);
			if (resultQueDtos!=null) {
				
				//twoStationEntity.setHavetrain(resultDto.getData().size());
				//twoStationDao.insert(twoStationEntity);
				
				for (ResultQueDto resultQueDto : resultQueDtos) {
					
					ResultQueTrainDto resultQueTrainDto=resultQueDto.getQueryLeftNewDTO();
					ScheduleEntity scheduleEntity=modelMapper.map(resultQueTrainDto, ScheduleEntity.class);
					System.out.println(scheduleEntity.getTrain_no());
					trainDataDao.insert(scheduleEntity);
					
					
				}
			}/*else{
				twoStationEntity.setHavetrain(0);
				twoStationDao.insert(twoStationEntity);
				
			}*/
			
			session.commit();
			session.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
