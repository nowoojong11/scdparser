package parser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class ThreadRun extends Thread {

	String fileName;
	Connection con;
	method_parser mep = new method_parser();

	ThreadRun(String fileName, Connection con) {
		this.fileName = fileName;
		this.con = con;
	}

	@Override
	public void run() {
		try {
			ArrayList<JSONObject> obj = mep.jsonParser(fileName);
			mep.obj_extract_parser(obj, con);
			/*
			 * JSON형식의 OBJECT를 ArrayList에 저장시키고
			 * ArrayListObject를 뽑아와 DB에 저장시킵니다.
			 */
			if (fileName.equals("20200131.json")) {
				System.out.println("----------------------------------");
				System.out.println("mariaDB에 모든 데이터를 삽입했습니다.");
				System.out.println("----------------------------------");
			}
		} catch (ParseException pare) {
			System.out.println("Parser 오류입니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}




//	String currentThreadName = Thread.currentThread().getName();
//	boolean currentThreadlive = Thread.currentThread().isAlive();

//	    System.out.println("작업 스레드 이름: "+ currentThreadName);
//		if(currentThreadlive) {
//			System.out.println("살았다");
//		}else {
//			System.out.println("죽엇다.");
//		}