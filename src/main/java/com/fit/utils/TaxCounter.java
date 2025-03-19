package com.fit.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @AUTO 个税计算器
 * @DATE 2020年1月16日 下午4:57:34
 * @Author AIM
 * @version v1.0
 * @Company 天际友盟
 */
public class TaxCounter {
	// 各个月份收入集合
	private static double[] months = { 20200, 10200, 10200, 10200, 10200, 10200, 11700, 11700, 11700, 11700, 11700, 11700 };
	// 各个月保险结合
	private static double[] premiums = { 865, 865, 865, 865, 865, 865, 865, 865, 865, 865, 865, 865 };
	private static double taxFree = 5000;

	public static void main(String[] args) {
		// counter();
		counterYear();
	}

	/**
	 * 按月度算个税
	 */
	public static void counter() {
		double revenueSum = 0; // 累计收入
		double premiumSum = 0; // 累计保险金
		double taxFreeSum = 0; // 累计面税金额
		double counterSum = 0; // 累计个税
		double quickSum = 0; // 累计速算扣除数
		for (int i = 0; i < months.length; i++) {
			double revenue = months[i];// 当月收入
			double premium = premiums[i];// 当月保险金额
			double rate = 0; // 税率
			if (revenueSum < 36000) {// 税率:3%
				rate = 0.03;
			} else if (revenueSum < 144000) {// 税率:10%
				rate = 0.1;
				quickSum = 210;
			} else if (revenueSum < 300000) {// 税率:20%
				rate = 0.2;
				quickSum = 1410;
			} else if (revenueSum < 420000) {// 税率:25%
				rate = 0.25;
				quickSum = 2660;
			} else if (revenueSum < 660000) {// 税率:30%
				rate = 0.3;
				quickSum = 4410;
			} else if (revenueSum < 960000) {// 税率:35%
				rate = 0.35;
				quickSum = 7160;
			} else {// 税率:45%
				rate = 0.45;
				quickSum = 15160;
			}
			revenueSum += revenue; // 累加总输入
			premiumSum += premium; // 累加保险金
			taxFreeSum += taxFree; // 累加免税金额
			// 计算公式:本期应预扣个税=（累计工资总额-累计免税收入（住房补贴、独托、出差补贴等）-累计基本减除费用（5000）-累计专项扣除（社保公积金等）-累计专项附加扣除-累计其他扣除）x税率-速算扣除数-累计已预扣税额
			double result = formatDouble((revenue - taxFree - premium) * rate - quickSum);
			int j = i + 1;
			System.out.println(String.format("第%s个月,收入金额:%s,个税金额:%s,个税税率:%s %%,累计收入金额:%s,累计个税金额:%s,累计减免金额:%s", j, revenue, result,
					rate * 100, revenueSum, premiumSum, taxFreeSum));
			counterSum += result;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, -1);
		Date y = c.getTime();
		String year = format.format(y);
		System.out.println(String.format(" %s年,收入总金额:%s,个税总金额:%s ", year, revenueSum, counterSum));
	}

	public static double formatDouble(double d) {
		BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
		return bg.doubleValue();
	}

	/**
	 * 按年计算
	 */
	public static void counterYear() {
		double revenueSum = 0; // 累计收入
		double premiumSum = 0; // 累计保险金
		double taxFreeSum = 0; // 累计面税金额
		double counterSum = 0; // 累计个税
		double quickSum = 0; // 累计速算扣除数
		for (int i = 0; i < months.length; i++) {
			double revenue = months[i];// 当月收入
			double premium = premiums[i];// 当月保险金额
			double rate = 0; // 税率
			if (revenueSum < 36000) {// 税率:3%
				rate = 0.03;
			} else if (revenueSum < 144000) {// 税率:10%
				rate = 0.1;
				quickSum = 2520;
			} else if (revenueSum < 300000) {// 税率:20%
				rate = 0.2;
				quickSum = 16920;
			} else if (revenueSum < 420000) {// 税率:25%
				rate = 0.25;
				quickSum = 31920;
			} else if (revenueSum < 660000) {// 税率:30%
				rate = 0.3;
				quickSum = 52920;
			} else if (revenueSum < 960000) {// 税率:35%
				rate = 0.35;
				quickSum = 85920;
			} else {// 税率:45%
				rate = 0.45;
				quickSum = 181920;
			}
			revenueSum += revenue; // 累加总输入
			premiumSum += premium; // 累加保险金
			taxFreeSum += taxFree; // 累加免税金额
			// 计算公式:本期应预扣个税=（累计工资总额-累计免税收入（住房补贴、独托、出差补贴等）-累计基本减除费用（5000）-累计专项扣除（社保公积金等）-累计专项附加扣除-累计其他扣除）x税率-速算扣除数-累计已预扣税额
			double result = formatDouble((revenueSum - taxFreeSum - premiumSum) * rate - quickSum - counterSum);
			int j = i + 1;
			System.out.println(String.format("第%s个月,收入金额:%s,个税金额:%s,个税税率:%s %%,累计收入金额:%s,累计个税金额:%s,累计减免金额:%s", j, revenue, result,
					rate * 100, revenueSum, premiumSum, taxFreeSum));
			counterSum += result;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, -1);
		Date y = c.getTime();
		String year = format.format(y);
		System.out.println(String.format(" %s年,收入总金额:%s,个税总金额:%s ", year, revenueSum, counterSum));
	}
}
