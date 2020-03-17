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


}
