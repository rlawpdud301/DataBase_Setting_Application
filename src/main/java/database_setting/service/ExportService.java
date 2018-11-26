package database_setting.service;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import database_setting.jdbc.ConnectionProvider;
import database_setting.jdbc.MyDataSource;




public class ExportService {
	public void service(String dirPath) {
		checkBackupDir(dirPath);
		List<String> tables = getTables();
		File sqlDir = new File(dirPath);
		File sqlFile = null;
		for(String tblName : tables) {
			sqlFile = new File(sqlDir,tblName);
			exportData("select * from " + tblName, sqlFile.getAbsolutePath().replace("\\", "/"));
		}
	}
	
	

	



	private List<String> getTables(){
		List<String> list = new ArrayList<>();
		try (Connection con = ConnectionProvider.getConnection("db.properties");
			Statement stmt = con.createStatement();){
			stmt.execute("use "+ MyDataSource.getInstance("db.properties").getProperties().getProperty("dbname"));
			try(ResultSet rs = stmt.executeQuery("show tables");){
				while(rs.next()) {
					list.add(rs.getString(1));
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private void checkBackupDir(String dirPath) {
		File backupDir=new File(dirPath);
		if(backupDir.exists()) {
			for(File file : backupDir.listFiles()) {
				file.delete();
			}
		}else {
			backupDir.mkdir();
		}
		
	}
	private void exportData(String string, String replace) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
