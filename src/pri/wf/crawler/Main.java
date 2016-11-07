package pri.wf.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.google.gson.Gson;
import com.mysql.cj.api.Session;

import pri.wf.crawler.dto.QueryResultDto;
import pri.wf.mybatis.StationDao;

public class Main {

	public static void main(String[] args) {

		String url = "https://kyfw.12306.cn/otn/leftTicket/queryX?leftTicketDTO.train_date=2016-11-07&leftTicketDTO.from_station=DLT&leftTicketDTO.to_station=SHH&purpose_codes=ADULT";
		String charset = "UTF-8";
		URL urlObj;

		try {
			urlObj = new URL(url);
			HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
			doTrustToCertificates(connection);
			connection.connect();
			InputStream response = connection.getInputStream();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(response, charset))) {
				String input;
				StringBuffer result = new StringBuffer();
				while ((input = br.readLine()) != null) {
					result.append(input);
				}
				System.out.println(result);

				Gson gson = new Gson();
				QueryResultDto queryResultDto = new QueryResultDto();
				 queryResultDto=gson.fromJson(result.toString(),
				 QueryResultDto.class);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String resource="config/mybatis-config.xml";
		File file=new File(resource);
		if (!file.exists()&&!file.isDirectory()) {
			System.out.println("bucunzai");
		} else {
			System.out.println("cunzai");
		}
		Reader reader;
		try {
			reader=Resources.getResourceAsReader(resource);
			System.out.println("reader"+reader.toString());
			SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
			SqlSessionFactory factory=builder.build(reader);
			SqlSession session=factory.openSession();
			StationDao stationDao=session.getMapper(StationDao.class);
			System.out.println(stationDao.countAll());
			session.commit();
			session.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
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
