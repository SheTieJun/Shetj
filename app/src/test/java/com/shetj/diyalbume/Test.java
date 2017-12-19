package com.shetj.diyalbume;

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

	private ListNode getNext(ListNode l1, ListNode l2, int j) {
		l1.val = l1.val + l2.val + j;
		int j1 = l1.val / 10;
		l1.val = l1.val % 10;


		l1.next = getNext(l1.next, l2.next, j1);
		return l1;
	}

	public int lengthOfLongestSubstring(String s) {

		return 0;
	}

}

