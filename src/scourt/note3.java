//package scourt;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.RandomAccessFile;
//import java.util.ArrayList;
//
//public class note3 {
//	public static void main(String args[]) {
//
//		Exod_Tag("C:\\\\Users\\\\datasolution\\\\eclipse-workspace\\\\scourt2\\\\inf\\\\CRL_PAN.inf");
//	}
//
//	@SuppressWarnings("resource")
//	public static void Exod_Tag(String path_conf) {
//		try {
//			note3_subclass sub = new note3_subclass();
//			RandomAccessFile file = new RandomAccessFile(new File(path_conf), "r");
//			String tag = null;
//			String readline_str;
//			String key;
//			String value;
//			ArrayList<String> array = new ArrayList<String>();
//			PrintWriter writer = new PrintWriter(
//					new FileWriter("C:\\\\Users\\\\datasolution\\\\Documents\\\\output3232.txt"), true);
//			while ((readline_str = file.readLine()) != null) {
//				readline_str = readline_str.trim();
//				
//				if (readline_str.startsWith("[")) {
//					readline_str = readline_str.replace("[", "").replace("]", "");
//					tag = readline_str;
//					if (tag.equals("COMMON")) {
//						array.add(tag);
//					} else {
//						writer.println(tag);
//					}
//				} else {
//					String[] st = readline_str.split("=");
//					key = new String((st[0] + "_" + tag).trim().getBytes("8859_1"), "UTF-8");
//					value = sub.split_value(st,tag);
//
//					if (!key.endsWith("COMMON")) {
//						
//						writer.printf("%s : %s", key.split("_")[0]+"_"+key.split("_")[1], value);
//						writer.println();
//					} else {
//						array.add(readline_str);
//					}
//
//				}
//
//			}
//			for (String arrayvalue : array) {
//				writer.println(arrayvalue);
//				System.out.println(arrayvalue);
//			}
//			
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.exit(-1);
//		}
//	}
//
//}
