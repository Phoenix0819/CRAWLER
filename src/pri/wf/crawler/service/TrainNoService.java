package pri.wf.crawler.service;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.modelmapper.ModelMapper;

import pri.wf.crawler.FormatData;
import pri.wf.crawler.Query;
import pri.wf.crawler.dao.TrainListDao;
import pri.wf.crawler.dao.TrainNoStationDao;
import pri.wf.crawler.dto.StationDataDto;
import pri.wf.crawler.dto.StationDto;
import pri.wf.crawler.dto.TrainNoDataStationDto;
import pri.wf.crawler.dto.TrainNoDto;
import pri.wf.crawler.dto.TrainNoQueryDto;
import pri.wf.crawler.entity.TrainListEntity;
import pri.wf.crawler.entity.TrainNoStationEntity;

public class TrainNoService {
	List<StationDataDto> stationDataDtos=new StationDto().getStationDataDtos();
	
	public void trainNoAdd() {
		List<TrainListEntity> trainListEntities =this.trainListSelectAll();
		if(!trainListEntities.isEmpty()&&trainListEntities.size()>=0){
			for (TrainListEntity trainListEntity : trainListEntities) {
				try {
					if (trainListEntity.getFlag()!=1) {
						TrainNoQueryDto trainNoQueryDto=this.trainListEntToDto(trainListEntity);
						trainNoQueryDto=this.queryCodeByName(trainNoQueryDto);
						TrainNoDto trainNoDto=this.queryByTrainNo(trainNoQueryDto);
						if (trainNoDto!=null) {
							this.trainNoStationAdd(trainNoDto,trainListEntity);
						}else {
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void trainNoStationAdd(TrainNoDto trainNoDto, TrainListEntity trainListEntity) {
		String resource = "pri/wf/crawler/config/mybatis-config.xml";
		Reader reader;
		try {
			reader = Resources.getResourceAsReader(resource);
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			SqlSession session = factory.openSession();
			TrainNoStationDao trainNoDao=session.getMapper(TrainNoStationDao.class);
			ModelMapper modelMapper=new ModelMapper();
			for (TrainNoDataStationDto trainNoStationDto : trainNoDto.getData().getData()) {
				if (trainNoStationDto!=null){
					TrainNoStationEntity trainNoStationEntity=modelMapper.map(trainNoStationDto, TrainNoStationEntity.class);
					System.out.println(trainNoStationEntity.getStation_no());
					trainNoDao.insert(trainNoStationEntity);
				}
			}
			trainListEntity.setFlag(1);
			System.out.println(trainListEntity.getFlag());
			TrainListDao trainListDao=session.getMapper(TrainListDao.class);
			trainListDao.updateFlag(trainListEntity);
			
			session.commit();
			session.close();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private TrainNoDto queryByTrainNo(TrainNoQueryDto trainNoQueryDto) {
		String url="https://kyfw.12306.cn/otn/czxx/queryByTrainNo?train_no="
				+trainNoQueryDto.getTrain_no()// "240000G1010C"
				+ "&from_station_telecode="
				+ trainNoQueryDto.getFrom_station_telecode()//"VNP"
				+ "&to_station_telecode="
				+ trainNoQueryDto.getTo_station_telecode()//"AOH"
				+ "&depart_date=2016-11-25";
		System.out.println(url);
		StringBuffer resultJson=new Query().QueryByUrl(url);
		if (resultJson!=null) {
			TrainNoDto trainNoDto=new TrainNoDto();
			trainNoDto=new FormatData().JsontoObj(resultJson, TrainNoDto.class);
			List<TrainNoDataStationDto> trainNoDataStationDtos=trainNoDto.getData().getData();
			if (trainNoDataStationDtos!=null&&trainNoDataStationDtos.size()!=0) {
				String TrainCode=trainNoDataStationDtos.get(0).getStation_train_code();
				for (TrainNoDataStationDto trainNoDataStationDto : trainNoDataStationDtos) {
					if ((trainNoDataStationDto.getStation_train_code()==null)||(trainNoDataStationDto.getStation_train_code().isEmpty())) {
						trainNoDataStationDto.setStation_train_code(TrainCode);
					}
				}
				return trainNoDto;
			}else {
				return null;
			}
		}else {
			return null;
		}
	}

	private TrainNoQueryDto queryCodeByName(TrainNoQueryDto trainNoQueryDto) {
		int flag=0;
		for (StationDataDto stationDataDto : stationDataDtos) {
			if(flag<2){
				if (stationDataDto.getName().equals(trainNoQueryDto.getStart_station_name())) {
					trainNoQueryDto.setFrom_station_telecode(stationDataDto.getQueryCode());
					flag++;
					continue;
				}else if (stationDataDto.getName().equals(trainNoQueryDto.getEnd_station_name())) {
					trainNoQueryDto.setTo_station_telecode(stationDataDto.getQueryCode());
					flag++;
				}
			}else {
				return trainNoQueryDto;
			}
		}
		return trainNoQueryDto;
	}
	
	private TrainNoQueryDto trainListEntToDto(TrainListEntity trainListEntity) {
		TrainNoQueryDto trainNoQueryDto=new TrainNoQueryDto();
		ModelMapper mapper=new ModelMapper();
		if (trainListEntity!=null) {
			trainNoQueryDto=mapper.map(trainListEntity, TrainNoQueryDto.class);
		}
		return trainNoQueryDto;
	}
	
	public List<TrainListEntity> trainListSelectAll() {
		List<TrainListEntity> trainListEntities=new ArrayList<>();
		String resource = "pri/wf/crawler/config/mybatis-config.xml";
		Reader reader;
		try {
			reader = Resources.getResourceAsReader(resource);
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			SqlSession session = factory.openSession();
			TrainListDao trainListDao=session.getMapper(TrainListDao.class);
			trainListEntities= trainListDao.selectAll();
			session.commit();
			session.close();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
		return trainListEntities;
	}
}
