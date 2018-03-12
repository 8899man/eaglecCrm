package cn.eaglec.util;

import java.util.concurrent.TimeUnit;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * 
 * @Title: FileListenerUtil.java 
 * @Package cn.inovance.iotgp.web.common.util 
 * @Description: 
 * @author fb2112  
 * @date 2014-12-8 下午5:53:45 
 * @version V1.0
 */
public class FileListenerUtil {

	/**
	 * @Date 2014-12-08 下午6:17:29
	 * @Author fb2112
	 * @Description 
	 * @param adaptor 观察者实现类
	 * @param dirPath 目录路径
	 * @throws Exception void
	 */
	public static void runFileListenter(FileAlterationListenerAdaptor adaptor, String dirPath) throws Exception{
		// 轮询间隔5 秒  
        long interval = TimeUnit.SECONDS.toMillis(5);  
        
        // 创建一个文件观察器用于处理文件的格式          
        FileAlterationObserver observer = new FileAlterationObserver(dirPath);
        
        observer.addListener(adaptor); //设置文件变化监听器  
        
        //创建文件变化监听器  
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval,observer);  
        // 开始监控  
        monitor.start();
	}
}
