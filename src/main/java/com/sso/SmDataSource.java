package com.sso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class SmDataSource {

	private static final Logger log = Logger.getLog(SmDataSource.class);
	private Connection conn ;
	private PreparedStatement ps;
	private ResultSet rs;
	private ComboPooledDataSource cpd;
	
	public List<String> getAllMenu(){
		com.ai.security.SecurityFactory ab;
		List<String> list = new ArrayList<String>();
		try {
			ps = this.getConn().prepareStatement("select url from sys_function");
			rs = ps.executeQuery();
			while(rs.next()){
				String url = rs.getString("URL");
				if(null != url && !"".equals(url) && !"NULL".equals(url)){
					list.add(url);
				}
			}
		} catch (SQLException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}finally{
			try {
				rs.close();
				ps.close();
				this.getConn().close();
			} catch (SQLException e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
			
		}
		return list;
	}
	public Connection getConn() {
		if(null == conn){
			try {
				conn = getCpd().getConnection();
			} catch (SQLException e) {
				log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
			}
		}
		return conn;
	}
	public PreparedStatement getPs() {
		return ps;
	}
	public void setPs(PreparedStatement ps) {
		this.ps = ps;
	}
	public ResultSet getRs() {
		return rs;
	}
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	public ComboPooledDataSource getCpd() {
		if (cpd == null) {
			ApplicationContext app = new ClassPathXmlApplicationContext(new String[]{"spring/eaap-op2-messagearrange-db-spring.xml"});		
			cpd = (ComboPooledDataSource) app
					.getBean("eaapSmDataSource");
		}
		return cpd;
	}
}
