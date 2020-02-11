package parser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class main_parser {
	public static void main(String args[]) {
		final int maxCore = Runtime.getRuntime().availableProcessors(); //1
		
		method_parser mp = new method_parser();
		ArrayList<String> fileNameList;
		Connection con;
		ExecutorService p = Executors.newFixedThreadPool(maxCore);
		con = mp.dbcon_parser(); //2

		try {

			fileNameList = mp.count_file_parser(); //3
			mp.dbdrop_parser(con);//4
			mp.dbcreate_parser(con);

			for (String fileName : fileNameList) {
				Thread r = new ThreadRun(fileName, con);//5
				p.execute(r);
			}
			
			p.shutdown();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
	}
}
					/* 1.
					 * 해당 cpu의 상태를 파악해 최적화된 쓰레드개수를
					 * 알려줍니다.
					 */

					/* 2.
					 * mariadb에 접속하고 connection을 받습니다.
					 */

					/* 3.
					 * 해당 폴더에있는 파일 이름을 리스트에
					 * 저장시킵니다.
					 */

					/* 4.
					 * 기존 테이블을 삭제하고 새로 생성합니다.
					 */

					/* 5.
					 * fileName 하나당 쓰레드 를 
					 * 부여합니다.
					 */

					/* 6. 
					 * connection과 excutorservice를 닫아줍니다.
					 */