package pri.wf.crawler;

import java.util.List;

import pri.wf.crawler.dto.TrainListDataDto;
import pri.wf.crawler.dto.TrainListDto;
import pri.wf.crawler.service.TrainListService;

public class Main {

	public static void main(String[] args) {
		//List<StationDto> stationDtos=new StationService().stationList();
		//String trainListUrl="https://kyfw.12306.cn/otn/resources/js/query/train_list.js";
		/*String url="https://kyfw.12306.cn/otn/leftTicket/queryX?leftTicketDTO.train_date=2016-11-25&leftTicketDTO.from_station=DLT&leftTicketDTO.to_station=CCT&purpose_codes=ADULT";
		new ResultService().resultAdd(url);*/
		TrainListDto trainListDto=new TrainListService().queryAndFormat();
		List<TrainListDataDto> list=trainListDto.getData();

		System.out.println(trainListDto.getData().size());
		
	
	}
}
