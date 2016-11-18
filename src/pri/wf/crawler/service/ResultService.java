package pri.wf.crawler.service;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.modelmapper.ModelMapper;

import pri.wf.crawler.FormatData;
import pri.wf.crawler.Query;
import pri.wf.crawler.dao.TrainDataDao;
import pri.wf.crawler.dto.ResultDto;
import pri.wf.crawler.dto.ResultQueDto;
import pri.wf.crawler.dto.ResultQueTrainDto;
import pri.wf.crawler.entity.ScheduleEntity;

public class ResultService {
	public ResultDto queryAndFormat(String url){
		StringBuffer resultJson=new Query().QueryByUrl(url);
		ResultDto resultDto=new ResultDto();
		resultDto=new FormatData().JsontoObj(resultJson, resultDto);
		return resultDto;
		
	}
	
	public void resultAdd(String url) {
		ResultDto resultDto=this.queryAndFormat(url);
		if (resultDto!=null) {
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
				if (resultQueDtos!=null) {
					for (ResultQueDto resultQueDto : resultQueDtos) {
						ResultQueTrainDto resultQueTrainDto=resultQueDto.getQueryLeftNewDTO();
						ScheduleEntity scheduleEntity=modelMapper.map(resultQueTrainDto, ScheduleEntity.class);
						System.out.println(scheduleEntity.getTrain_no());
						trainDataDao.insert(scheduleEntity);
					}
				}			
				session.commit();
				session.close();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	/*List< ResultQueDto> list=resultDto.getData();
	for (ResultQueDto resultQueDto : list) {
		System.out.println(resultQueDto.getQueryLeftNewDTO().getTrain_no());
	}*/
	//int sum = 0;
	/*for (StationDto startStation : stationDtos) {
		for (StationDto endStation : stationDtos) {
			if (!startStation.equals(endStation)) {
				sum++;
				url = "https://kyfw.12306.cn/otn/leftTicket/queryX?leftTicketDTO.train_date=2016-11-20&leftTicketDTO.from_station="
						+ startStation.getQueryCode()
						+ "&leftTicketDTO.to_station="
						+ endStation.getQueryCode()
						+ "&purpose_codes=ADULT";
				System.out.println(url);
				queryByTwoStation(url);
				try {
					Thread.currentThread().sleep( (long) (1+Math.random()*(1000-1+1)));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
		}
	}*/


}
