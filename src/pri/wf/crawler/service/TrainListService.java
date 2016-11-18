package pri.wf.crawler.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.AbstractDocument.BranchElement;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.modelmapper.ModelMapper;

import pri.wf.crawler.FormatData;
import pri.wf.crawler.dao.TrainListDao;
import pri.wf.crawler.dto.ResultDto;
import pri.wf.crawler.dto.TrainListDto;

public class TrainListService {
	private BufferedReader br;

	public TrainListDto queryAndFormat(){
		String jsonFile="./js/train_list_format.json";
		String charset = "UTF-8";
		StringBuffer jsonBuffer = new StringBuffer();
		try {
			InputStreamReader isr=new InputStreamReader(new FileInputStream(jsonFile),charset);
			br = new BufferedReader(isr);
			String s;
			
			while (( s=br.readLine())!=null) {
				jsonBuffer.append(s);
			}
			System.out.println(jsonBuffer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		TrainListDto trainListDto=new TrainListDto();
		trainListDto=new FormatData().JsontoObj(jsonBuffer, trainListDto);
//		ResultDto resultDto=new ResultDto();
//		resultDto=new FormatData().JsontoObj(resultJson, resultDto);
		return trainListDto;
		
	}
	
	public void TrainListAdd(ResultDto resultDto) {
		String resource = "pri/wf/crawler/config/mybatis-config.xml";
		Reader reader;
		try {
			reader = Resources.getResourceAsReader(resource);
			
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			SqlSession session = factory.openSession();
			TrainListDao trainListDao=session.getMapper(TrainListDao.class);
			List<TrainListDto> trainListDtos=new ArrayList<TrainListDto>();
				//	new FormatData().JsontoObj(jsonUrl, resultSet)
			
			ModelMapper modelMapper=new ModelMapper();
			
/*			if (resultQueDtos!=null) {
				
				//twoStationEntity.setHavetrain(resultDto.getData().size());
				//twoStationDao.insert(twoStationEntity);
				
				for (ResultQueDto resultQueDto : resultQueDtos) {
					
					ResultQueTrainDto resultQueTrainDto=resultQueDto.getQueryLeftNewDTO();
					ScheduleEntity scheduleEntity=modelMapper.map(resultQueTrainDto, ScheduleEntity.class);
					System.out.println(scheduleEntity.getTrain_no());
					trainDataDao.insert(scheduleEntity);
					
					
				}
			}*/			
			session.commit();
			session.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
