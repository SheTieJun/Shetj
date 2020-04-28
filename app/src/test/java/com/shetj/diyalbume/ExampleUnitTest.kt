package com.shetj.diyalbume

import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        twoSum(intArrayOf(3,2,4),6)
    }


    fun twoSum(nums: IntArray, target: Int): IntArray {
        for (i in nums.indices) {
            if (nums[i] > target) {
                continue
            } else {
                for (j in i + 1 until nums.size - i) {
                    return if (nums[j] > target) {
                        continue
                    } else if (nums[i] + nums[j] == target) {
                        print("$i...$j")
                       return intArrayOf(nums[i], nums[j])
                    } else {
                        continue
                    }
                }
            }
        }
        return intArrayOf(0, 0)
    }

    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        return 0.0
    }

    @Test
    fun  testString(){
        System.out.print("退款将收取【%s】1%%的手续费")
    }

    @Test
    fun testStringUTF(){
        System.out.print("如何高姿态的看待微�".toUpperCase())
    }

    @Test
    fun testClone(){
        val list = ArrayList<String>(1).apply {
            add("XX")
        }
        val listClone = list.clone()
        val toMutableList = list.toMutableList()
    }

    @Test
    fun test8String(){
        print(getlinkNo())
    }

    //获取所及数字
    fun getlinkNo(): String? {
        var linkNo = ""
        // 用字符数组的方式随机
        val model = "0aAbBc1CdDeE2fFgGh3HiIjJ4kKlLm5MnNoO6pPqQr7RsStT8uUvVw9WxXyY0zZ"
        println(model.length)
        val m = model.toCharArray()
        var j = 0
        while (j < 10) {
            val c = m[(Math.random() * 62).toInt()]
            // 保证六位随机数之间没有重复的
            if (linkNo.contains(c.toString())) {
                j--
                j++
                continue
            }
            linkNo += c
            j++
        }
        return linkNo
    }
}
