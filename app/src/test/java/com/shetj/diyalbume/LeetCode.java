package com.shetj.diyalbume;

import org.junit.Test;

import me.shetj.base.tools.json.GsonKit;

public class LeetCode {
    @Test
    public void reverseString(char[] s) {

        char[] ns = s.clone();

        reverse(s,0,ns);

    }

    private void reverse(char[] ns, int i, char[] s) {
        int length = s.length -1;
        if (i >= length){
            return;
        }
        ns[length-i] = s[i];
        reverse(ns,i+1,s);
    }
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    class Solution {
        public ListNode swapPairs(ListNode head) {
            return swap(head,null,head,true);
        }
    }

    private ListNode swap(ListNode head,ListNode headAll,ListNode now ,boolean isFirst) {
        if (now.next ==null){
            //没有后续直接返回
            return head;
        }

        ListNode next = now.next;
        if (isFirst){
            head = next;
        }
        now.next = next.next;
        next.next = now;
        if (headAll != null){
            headAll.next = next;
        }
        if (now.next != null){
            return  swap(head,now,now.next,false);
        }
        return head;

    }

    public int[] twoSum(int[] nums, int target) {
        int[] he = new int[2];
        int size = nums.length - 1;
        for (int i = 0; i < size; i++){
            he[0] = i;
            for (int j = i+1; j<= size; j++){
                if (nums[i] + nums[j] == target){
                    he[1] = j;
                    return he;
                }
            }
        }
        return he;
    }


    public int[] twoSum2(int[] nums, int target) {
        int[] he = new int[2];
        int size = nums.length - 1;
        for (int i = 0; i < size; i++){
            he[0] = i;
            int i1 = target - nums[0];
            for (int j = i+1; j<= size; j++){
                if (nums[j] == i1){
                    he[1] = j;
                    return he;
                }
            }
        }
        return he;
    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return addT(true,new ListNode(0),new ListNode(0),l1,l2);
    }

    private ListNode addT(boolean isFirst,ListNode head,ListNode one, ListNode now1, ListNode now2) {
        //都没有后续 直接返回头部
        if (now1 == null && now2 == null){
            return head;
        }
        //如果一边没有，默认给0
        if (now1 == null){
            now1  = new ListNode(0);
        }
        if (now2 == null){
            now2  = new ListNode(0);
        }
        int last = 0;
        // 然后做加分，要把进1加上
        int i = now1.val + now2.val +one.val;
        if (i > 9){
            //除法得到进位
            last = 1;
        }
        //去模得到余数
        one.val = i%10;

        //如果是第一次，记得记录头部
        if (isFirst){
            head = one;
        }
        //如果没有 ，就没必要继续
        if (now1.next == null &&  now2.next == null){
            if (last > 0){
                one.next = new ListNode(last);
            }
            return head;
        }
        ListNode node = new ListNode(last);
        one.next = node;
        return addT(false,head,node, now1.next, now2.next);
    }


    /**
     * 最长的不重复的那个字母数量
     * 获取不相等的
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();
        int length = s.length();
        int max = 0;
        if (length <=1){
            return length;
        }

        //从第0个开始
        for (int i = 0; i < length;i++) {

            for (int j = i + 1; j < length; j++) {
                //如果存在相同的就break,说明已经最大的了
                if (chars[i] == chars[j]) {
                    int m = j - i;
                    if (m > max) {
                        max = m;
                    }
                    i = j;
                    break;
                }
            }
        }
        return max;
    }

}


