package cn.eaglec.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 客户关系维护定时任务
 * 
 *
 */
@Component
public class CrmTask {

	private Logger logger = Logger.getLogger(getClass());
	
	//@Scheduled(cron = "0 0/1 * * * ?")
	public void getTime(){
		System.out.println("-----");
		System.out.println(new Date());
		System.out.println("-----");
	}
}
