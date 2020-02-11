package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class method_parser {
	static int count = 1;
	static String path = "C:\\Users\\datasolution\\Desktop\\AINER\\202001";

	public String line_break(String str) {
		str = str.replace("\\n\\n","\\n")
				.replace("\\n\\n\\n","\\n")
				.replace("\\n \\n","\\n")
				.replace("\\n \\n\\n\\n", "\\n");
		return str;
	}
	/*
	 * 내용에 개행문자를 통일 시켜주는 메소드입니다.
	 */
	
	
	public void obj_extract_parser(ArrayList<JSONObject> obj, Connection con) throws SQLException {

		for (JSONObject Json : obj) {
			String title = (String) Json.get("title");
			String content = (String) Json.get("content");
			String link = (String) Json.get("link");
			String written_time = (String) Json.get("written_time");
			String press = (String) Json.get("press");
			String category = (String) Json.get("category");
			String topics = (Json.get("topics")).toString();
			dbinsert_parser(title, content, link, written_time, press, category, topics, con);
		}
		/*
		 * JsonObject중 DB에 넣을 컬럼밸류를 추출합니다.
		 * 밸류들을 모아 DB에 저장합니다.
		 */
	}

	
	
	public ArrayList<String> count_file_parser() {
		File f = new File(path);
		File[] files = f.listFiles();
		ArrayList<String> fileNameList = new ArrayList<String>();
		// files
		int count = 0;
		for (int i = 0; i < files.length; i++) {

			if (files[i].isFile()) {
				count++;
				fileNameList.add(files[i].getName());
				System.out.println(files[i].getName());
			}
		} 

		System.out.println("----------------");
		System.out.println("파일의 갯수 : " + count);
		System.out.println("----------------");
		return fileNameList;
		/*
		 * 해당 경로에 파일들을 체크 한뒤
		 * 전체 갯수와 파일 Name을 fileNameList에
		 * 저장을 시킵니다
		 */
	}

	
	
	public void dbcreate_parser(Connection con) throws SQLException {
		PreparedStatement pstmt;
		String create_SQL = "CREATE TABLE nwj.data(\r\n" + "id INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n"
				+ "title VARCHAR(500),\r\n" + "content VARCHAR(15000),\r\n" + "link VARCHAR(500),\r\n"
				+ "topics VARCHAR(500),\r\n" + "written_time VARCHAR(20),\r\n" + "press VARCHAR(20),\r\n"
				+ "category VARCHAR(20),\r\n" + "PRIMARY KEY(id)\r\n"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1";
		pstmt = con.prepareStatement(create_SQL);
		pstmt.execute();
		
		/*
		 * table create문입니다.
		 */
	}
	
	

	public void dbdrop_parser(Connection con) throws SQLException {
		PreparedStatement pstmt;
		String delete_SQL = "drop table nwj.data";
		pstmt = con.prepareStatement(delete_SQL);
		pstmt.execute();
		
		/*
		 * table drop문입니다.
		 */
	}
	
	
	
	
	public void dbinsert_parser(String title, String content, String link, String written_time, String press,
			String category, String topics, Connection con) throws SQLException {

		PreparedStatement pstmt;
		String SQL = "insert into nwj.data(title,content,link,written_time,press ,category,topics) values(?, ?, ?, ?, ?, ?, ?)";
		pstmt = con.prepareStatement(SQL);
		pstmt.setString(1, title);
		pstmt.setString(2, content);
		pstmt.setString(3, link);
		pstmt.setString(4, written_time);
		pstmt.setString(5, press);
		pstmt.setString(6, category);
		pstmt.setString(7, topics);
		pstmt.execute();
		
		/*
		 * nwj.data에 value들을 insert해줍니다.
		 */
	}

	
	
	public Connection dbcon_parser() {
		String driver = "org.mariadb.jdbc.Driver";
		Connection con;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection("jdbc:mariadb://192.168.210.119:3307", "ainer", "ainer#123");

			if (con != null) {
				System.out.println("DB 접속 성공");
			}
		} catch (ClassNotFoundException e) {
			return null;
		} catch (SQLException e) {
			return null;
		}
		return con;
		/*
		 * mariaDb에 접속하는 Connection입니다.
		 */
	}

	
	
	public ArrayList<JSONObject> jsonParser(String filename) throws ParseException {
		try {
			ArrayList<JSONObject> obj = new ArrayList<JSONObject>();
			JSONParser jsonParser = new JSONParser();
			BufferedReader buff = new BufferedReader(new FileReader(path+"\\"+filename));
			String[] jsonString = new String[2];
			/*
			 * 해당경로에 filename을 읽어옵니다.
			 */
			
			
			while ((jsonString[0] = buff.readLine()) != null) {

				if (jsonString[0].isEmpty()) {
					continue;
				}
				/*
				 * readline이 공백이면 다음라인으로 넘어갑니다.
				 */
				
				if (!jsonString[0].endsWith("}")) {
					continue;
				}
				/*
				 * readline이 }로 끝나지 않으면 완성된 readline이
				 * 아니므로 넘어갑니다.
				 */
				
				jsonString[0] = line_break(jsonString[0]);
				
				/*
				 * 개행문자를 선별하기 위한 메소드입니다.
				 */
				if ((jsonString[0].split("\\}\\{").length == 2)) {
					jsonString = jsonString[0].split("\\}\\{");
					obj.add((JSONObject) jsonParser.parse(jsonString[0] + "}"));
					obj.add((JSONObject) jsonParser.parse("{" + jsonString[1]));
				} else {
					obj.add((JSONObject) jsonParser.parse(jsonString[0]));
				}
				/*
				 * {~~~}{~~~} 이런식으로 중간에 개행이 안된부분이 존재합니다
				 * }{로 split 해준뒤 다시 만들어줍니다.
				 * 그리고 보통라인은 그냥 넣어줍니다.
				 */
				++count;
			}
			System.out.println(filename + " input 성공 ");
			return obj;

		} catch (IOException ioe) {
			System.out.println("입출력 오류 입니다.");
			ioe.printStackTrace();
			return null;

		} catch (IllegalArgumentException iae) {
			System.out.println("잘못된 인자값입니다/");
			iae.printStackTrace();
			return null;
		}

	}
	}
