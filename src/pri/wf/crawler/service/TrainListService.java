package pri.wf.crawler.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import pri.wf.crawler.FormatData;
import pri.wf.crawler.dao.TrainListDao;
import pri.wf.crawler.dto.TrainListDataDto;
import pri.wf.crawler.dto.TrainListDto;
import pri.wf.crawler.entity.TrainListEntity;

public class TrainListService {
	private BufferedReader br;
	TrainListDto trainListDto=new TrainListDto();

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
		
		trainListDto=new FormatData().JsontoObj(jsonBuffer, TrainListDto.class);
		return trainListDto;
		
	}
	

	
	public void TrainListAdd() {
		this.queryAndFormat();
		String resource = "pri/wf/crawler/config/mybatis-config.xml";
		Reader reader;
		try {
			reader = Resources.getResourceAsReader(resource);
			
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(reader);
			SqlSession session = factory.openSession();
			TrainListDao trainListDao=session.getMapper(TrainListDao.class);
			List<TrainListDataDto> trainListDataDtos=trainListDto.getData();
//			int sum = 0;
			if (trainListDataDtos!=null) {
				
				for (TrainListDataDto trainListDataDto : trainListDataDtos) {
//					sum++;
//					System.out.println(sum+":::"+trainListDataDto.getStation_train_code()+":::"+trainListDataDto.getTrain_no());
					TrainListEntity trainListEntity=this.dtoToEntity(trainListDataDto);
					trainListDao.insert(trainListEntity);
				}
			}		
			session.commit();
			session.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private TrainListEntity dtoToEntity(TrainListDataDto trainListDataDto) {
		TrainListEntity trainListEntity=new TrainListEntity();
		trainListEntity.setTrain_no(trainListDataDto.getTrain_no());
		String codeAndName=trainListDataDto.getStation_train_code();
		
		String[] code = codeAndName.split("\\(");
//		System.out.println(code[0]+"+++"+code[1]);
		String names=code[1].replace("\\(", "").replace(")", "");
		String[] name=names.split("-");
		
		trainListEntity.setStation_train_code(code[0]);
		
		trainListEntity.setStart_station_name(name[0]);
		trainListEntity.setEnd_station_name(name[1]);
		return trainListEntity;
	}
}
