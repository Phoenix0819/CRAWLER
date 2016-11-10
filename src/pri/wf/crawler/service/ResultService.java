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
import pri.wf.crawler.dto.ResultDto;
import pri.wf.crawler.dto.ResultQueDto;
import pri.wf.crawler.dto.ResultQueTrainDto;
import pri.wf.crawler.entity.ScheduleEntity;

public class ResultService {
	public void resultAdd(ResultDto resultDto) {
		String resource = "pri/wf/crawler/config/mybatis-config.xml";
		Reader reader;
		try {
			reader = Resources.getResourceAsReader(resource);
			
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			SqlSession session = factory.openSession();
			TrainDataDao trainDataDao=session.getMapper(TrainDataDao.class);
			List<ResultQueDto> resultQueDtos=resultDto.getData();
			ModelMapper modelMapper=new ModelMapper();
			
			for (ResultQueDto resultQueDto : resultQueDtos) {
				ResultQueTrainDto resultQueTrainDto=resultQueDto.getQueryLeftNewDTO();
				ScheduleEntity scheduleEntity=modelMapper.map(resultQueTrainDto, ScheduleEntity.class);
				System.out.println(scheduleEntity.getTrain_no());
				trainDataDao.insert(scheduleEntity);
				
			}
			session.commit();
			session.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
