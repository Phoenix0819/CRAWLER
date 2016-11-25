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
import pri.wf.crawler.dao.ScheduleDao;
import pri.wf.crawler.dto.ScheduleDto;
import pri.wf.crawler.dto.ScheduleQueDto;
import pri.wf.crawler.dto.ScheduleQueTrainDto;
import pri.wf.crawler.entity.ScheduleEntity;

public class ScheduleService {
	public void scheduleAdd(String url) {
		ScheduleDto resultDto=this.queryAndFormat(url);
		if (resultDto!=null) {
			String resource = "pri/wf/crawler/config/mybatis-config.xml";
			Reader reader;
			try {
				reader = Resources.getResourceAsReader(resource);
				SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
				SqlSessionFactory factory = builder.build(reader);
				SqlSession session = factory.openSession();
				ScheduleDao trainDataDao=session.getMapper(ScheduleDao.class);
				List<ScheduleQueDto> resultQueDtos=resultDto.getData();
				ModelMapper modelMapper=new ModelMapper();
				if (resultQueDtos!=null) {
					for (ScheduleQueDto resultQueDto : resultQueDtos) {
						ScheduleQueTrainDto resultQueTrainDto=resultQueDto.getQueryLeftNewDTO();
						ScheduleEntity scheduleEntity=modelMapper.map(resultQueTrainDto, ScheduleEntity.class);
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
	
	public ScheduleDto queryAndFormat(String url){
		StringBuffer resultJson=new Query().QueryByUrl(url);
		ScheduleDto resultDto=new FormatData().JsontoObj(resultJson, ScheduleDto.class);
		return resultDto;
		
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
