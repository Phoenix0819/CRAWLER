package pri.wf.crawler;

import java.util.List;

import pri.wf.crawler.dto.ResultDto;
import pri.wf.crawler.dto.StationDto;
import pri.wf.crawler.service.ResultService;
import pri.wf.crawler.service.StationService;

public class Main {

	public static void main(String[] args) {
		List<StationDto> stationDtos=new StationService().stationList();
		String trainListUrl="https://kyfw.12306.cn/otn/resources/js/query/train_list.js";
		String url="https://kyfw.12306.cn/otn/leftTicket/queryX?leftTicketDTO.train_date=2016-11-20&leftTicketDTO.from_station=SHH&leftTicketDTO.to_station=LYT&purpose_codes=ADULT";
		Query<ResultDto> queryUrl=new Query<ResultDto>();
		ResultDto resultDto=new ResultDto();
		resultDto=queryUrl.QueryByUrl(url,resultDto);
		if (resultDto!=null) {
			ResultService resultService=new ResultService();
			resultService.resultAdd(resultDto);
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} 
			}
		}*/
		//System.out.println(sum);
		/*String url = "https://kyfw.12306.cn/otn/leftTicket/queryX?leftTicketDTO.train_date=2016-11-17&leftTicketDTO.from_station=DLT&leftTicketDTO.to_station=SHH&purpose_codes=ADULT";
		String liaoyang="https://kyfw.12306.cn/otn/leftTicket/queryX?leftTicketDTO.train_date=2016-11-20&leftTicketDTO.from_station=SHH&leftTicketDTO.to_station=LYT&purpose_codes=ADULT";
		String urlnull="https://kyfw.12306.cn/otn/leftTicket/queryX?leftTicketDTO.train_date=2016-11-20&leftTicketDTO.from_station=CWQ&leftTicketDTO.to_station=JOL&purpose_codes=ADULT";*/
	
	}
}
