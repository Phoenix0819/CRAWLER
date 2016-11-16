package pri.wf.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.Gson;

import pri.wf.crawler.dto.ResultDto;
import pri.wf.crawler.dto.StationDto;
import pri.wf.crawler.service.ResultService;
import pri.wf.crawler.service.StationService;

public class Main {

	public static void main(String[] args) {
		List<StationDto> stationDtos=new StationService().stationList();
		String url = null;
		int sum = 0;
		for (StationDto startStation : stationDtos) {
			//System.out.println("code"+stationDto.getCode()+";id"+stationDto.getId()+";jp"+stationDto.getJianpin()+";name"+stationDto.getName()+";qp"+stationDto.getQuanpin()+";queryCode"+stationDto.getQueryCode());
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
		}
		System.out.println(sum);
		/*String url = "https://kyfw.12306.cn/otn/leftTicket/queryX?leftTicketDTO.train_date=2016-11-17&leftTicketDTO.from_station=DLT&leftTicketDTO.to_station=SHH&purpose_codes=ADULT";
		String liaoyang="https://kyfw.12306.cn/otn/leftTicket/queryX?leftTicketDTO.train_date=2016-11-20&leftTicketDTO.from_station=SHH&leftTicketDTO.to_station=LYT&purpose_codes=ADULT";
		String urlnull="https://kyfw.12306.cn/otn/leftTicket/queryX?leftTicketDTO.train_date=2016-11-20&leftTicketDTO.from_station=CWQ&leftTicketDTO.to_station=JOL&purpose_codes=ADULT";*/
	
	}



	public static void queryByTwoStation(String url) {
		String charset = "UTF-8";
		URL urlObj;
		ResultDto resultDto = new ResultDto();
		try {
			urlObj = new URL(url);
			HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
			doTrustToCertificates(connection);
			connection.connect();
			InputStream response = connection.getInputStream();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(response, charset))) {
				String input;
				StringBuffer result = new StringBuffer();
				while ((input = br.readLine()) != null)
				{
					result.append(input);
				}
				System.out.println(result);
				Gson gson = new Gson();
				resultDto = gson.fromJson(result.toString(), ResultDto.class);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (resultDto!=null) {
			ResultService resultService=new ResultService();
			resultService.resultAdd(resultDto);
		}
	}



	public static void doTrustToCertificates(HttpsURLConnection connection) {

		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}
		} };

		try {
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			connection.setSSLSocketFactory(sslContext.getSocketFactory());

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}
}
