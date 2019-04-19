package com.shetj.diyalbume;

import android.util.Base64;

/**
 * <b>@packageName：</b> com.shetj.diyalbume<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/15<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

public class Test {
	@org.junit.Test
	public void addition_isCorrect() {
		twoSum(new int[]{0, 3, 2, 0}, 0);
	}

	public int[] twoSum(int[] nums, int target) {
		for (int i = 0; i < nums.length; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[i] + nums[j] == target) {
					int[] s = new int[]{i, j};
					return s;
				}
			}
		}
		return new int[]{0, 0};
	}

	@org.junit.Test
	public void addTwoNumbers() {
		String s = "pwwkew";
		lengthOfLongestSubstring(s);
//		ListNode l1 =new ListNode(0);
//		ListNode l2 = new ListNode(2);
//		l1.next = new ListNode(8) ;
//		l2.next = new ListNode(5);
//		l1=getNext(l1,l2,0);
//		return getNext(l1,l2,0);
	}

	@org.junit.Test
	public void lengthOfLongestSubstring(String s) {


String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0eXBlIjoiQWNjb3VudFRva2VuIiwiaWQiOjI1NTY2LCJuaWNrbmFtZSI6IuWNiuWym-iNvOmdoSIsInNleCI6IjIiLCJzdGF0dXMiOiJub3JtYWwiLCJzdWJzY3JpYmVkIjowLCJyb2xlIjoic3R1ZGVudCIsImlhdCI6MTU1NTU4MzgyNywiZXhwIjoxNTU1NjA1NDI3fQ.GByicj67OaLbwB6vrXbhCmeojvc5odkE44HsILWvkEM";

		String[] split = token.split("\\.");
		String info = new String(Base64.decode(split[1].getBytes(), Base64.URL_SAFE));
		System.out.print(info);
	}

}

