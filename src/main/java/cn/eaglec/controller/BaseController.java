package cn.eaglec.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cn.eaglec.util.ExceptionUtil;
import cn.eaglec.util.PropertiesUtils;


@Controller
public class BaseController {
	protected final Logger logger = Logger.getLogger(this.getClass());

	/* 全局异常处理  */
	@ExceptionHandler
	public void exp(HttpServletRequest request, HttpServletResponse response,
			Exception ex) {
		request.setAttribute("   ex", ex);

		try {
			String errMsg = "";
			errMsg += "程序执行过程发生错误：" + ex.getMessage() + "\n"
					+ ExceptionUtil.getStackMsg(ex);
			logger.error(errMsg);

			if (isAjax(request)) {
				if (getIsDebug()) {
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write("{\"jsonParams\":{},\"mapParams\":{},\"msg\":\""
									+ ex.getMessage()
									+ "\",\"msgKey\":\"\",\"success\":0}");
				} else {
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write("{\"jsonParams\":{},\"mapParams\":{},\"msg\":\"操作失败!!\",\"msgKey\":\"dealFail\",\"success\":0}");
				}
			} else {
				if (getIsDebug()) {
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write("<h1>发生错误，详情如下!</h1><br>");
					response.getWriter().write(
							ex.toString() + "\n" + ex.getMessage() + "\n"
									+ ExceptionUtil.getStackMsg(ex));

				} else {
					ex.printStackTrace();
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write("<h1>发生未知异常，详情请联系系统管理员！</h1>");

				}
			}
		} catch (Exception myEx) {
			myEx.printStackTrace();
		}
		logger.error("MonitorListController execute error", ex);
	}

	private static boolean getIsDebug() {
		try {
			String isDebug = PropertiesUtils.getProperties().getProperty(
					"isDebug");
			if (StringUtils.isBlank(isDebug)) {
				isDebug = "0";
			}
			if (isDebug.trim().equalsIgnoreCase("1")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	private static boolean isAjax(HttpServletRequest request) {
		String requestType = request.getHeader("X-Requested-With");
		if (StringUtils.isNoneBlank(requestType)
				&& requestType.equalsIgnoreCase("XMLHttpRequest")) {
			return true;
		}
		return false;
	}
}
