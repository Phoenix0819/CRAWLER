package pri.wf.crawler;

import pri.wf.crawler.service.TrainListService;
import pri.wf.crawler.service.TrainNoService;

public class Main {

	public static void main(String[] args) {
		//List<StationDto> stationDtos=new StationService().stationList();
//		String trainNoUrl1="https://kyfw.12306.cn/otn/czxx/queryByTrainNo?train_no=13000G800102&from_station_telecode=DLT&to_station_telecode=CCT&depart_date=2016-11-25";
//		String trainNoUrl2="https://kyfw.12306.cn/otn/czxx/queryByTrainNo?train_no=1g000G800305&from_station_telecode=DFT&to_station_telecode=CCT&depart_date=2016-11-25";
//		String trainNoUrl3="https://kyfw.12306.cn/otn/czxx/queryByTrainNo?train_no=240000G1410D&from_station_telecode=VNP&to_station_telecode=AOH&depart_date=2016-11-25";
		//String trainListUrl="https://kyfw.12306.cn/otn/resources/js/query/train_list.js";
		/*String url="https://kyfw.12306.cn/otn/leftTicket/queryX?leftTicketDTO.train_date=2016-11-25&leftTicketDTO.from_station=DLT&leftTicketDTO.to_station=CCT&purpose_codes=ADULT";
		new ResultService().resultAdd(url);*/
		long start=System.currentTimeMillis();
		System.out.println("start"+start);
		new TrainNoService().trainNoAdd();
		long end=System.currentTimeMillis();
		System.out.println("Total Time:" + (end - start) + "s");
		
	
	}
	
	public static void TrainListStore() {
		TrainListService trianListService=new TrainListService();
		trianListService.trainListAdd();
	}
}
