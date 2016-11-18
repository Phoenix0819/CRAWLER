package pri.wf.crawler;

import pri.wf.crawler.service.TrainListService;

public class Main {

	public static void main(String[] args) {
		//List<StationDto> stationDtos=new StationService().stationList();
		//String trainListUrl="https://kyfw.12306.cn/otn/resources/js/query/train_list.js";
		/*String url="https://kyfw.12306.cn/otn/leftTicket/queryX?leftTicketDTO.train_date=2016-11-25&leftTicketDTO.from_station=DLT&leftTicketDTO.to_station=CCT&purpose_codes=ADULT";
		new ResultService().resultAdd(url);*/
		long start=System.currentTimeMillis();
		System.out.println("start"+start);
		TrainListService trianListService=new TrainListService();
		trianListService.TrainListAdd();
		long end=System.currentTimeMillis();
		System.out.println("运行时间：" + (end - start) + "毫秒");
		
	
	}
}
