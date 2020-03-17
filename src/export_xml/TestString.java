package export_xml;

import net.sf.json.JSONArray;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestString {

	public static void main(String[] args) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM");
		String time = "1995.04";
		System.out.println(format.parse(time).toString());
		// testTime();
		// testSplit();
		// testJSONArray();
//		String str = "[{\"question\":\"\",\"solution\":\"\",\"amount\":\"\",\"LAY_TABLE_INDEX\":0},null]";
//		System.out.println(str.replaceAll(",null",""));
		// String s = "http://vote.vinspier.com/public/swgb/vote/callback.do?uuid=69000251806B40BF95CAE09EA300F767";

		// System.out.println(URLEncoder.encode(s,"UTF-8"));
	}

	public static void testJSONArray(){
		// String str = "[\"问题\",\"解决方案\",\"资金分配\"]";
		// String str = "[2020年度,2021年度,2022年度]";
		String str = "['问题','解决方案','资金分配']";
		JSONArray array = JSONArray.fromObject(str);
		System.out.println(array.getString(0));
	}

	public static  void testSplit(){
		String name = ",,,";
		name = name.replaceAll(","," ,");
		String[] nameArr = name.split(",");
		System.out.println(nameArr.length);
		for (String s : nameArr){
			System.out.println(s);
		}
	}

	public static void testTime(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH )+1;
		System.out.println(year + " 年 " + month + " 月 ");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		try {
			Date date =	format.parse(year + "-" + month);
			System.out.println(date.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void test(){
		List<List<Integer>> integerList = new ArrayList<List<Integer>>();
		List<Integer> integers = new ArrayList<Integer>();
		for(int i = 0; i < 10; i++){
			integers.add(i);
		}
		integerList.add(integers);
		//integers.clear();
		System.out.println(integerList.get(0).size());
	}
}
