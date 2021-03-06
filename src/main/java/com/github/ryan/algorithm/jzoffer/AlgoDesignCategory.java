package com.github.ryan.algorithm.jzoffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author ryan.houyl@gmail.com
 * @description
 * @className AlgoDesignCategory
 * @date October 16,2018
 */
public class AlgoDesignCategory {

    /**
     * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。
     * 例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
     * 输入描述:
     * 输入一个字符串,长度不超过9(可能有字符重复),字符只包括大小写字母。
     *
     * 递归回溯法
     */

    public ArrayList<String> Permutation(String str) {
        ArrayList<String> res = new ArrayList<>();
        if (str == null || "".equals(str)) return res;

        boolean[] used = new boolean[str.length()];
        char[] strArr = str.toCharArray();
        genePermutation(strArr, 0, new StringBuilder(), used, res);

        Set<String> set = new HashSet<>(res);
        ArrayList<String> ret = new ArrayList<>(set);
        Collections.sort(ret);
        return ret;
    }

    // 语义：s保存了一个有index元素的排列
    // 向这个排列的末尾增加第index+1个元素，获得一个有index+1个元素的排列
    // 该递归函数终止只产生一个全排列，在for循环中产生所有可能的全排列
    private void genePermutation(char[] strArr, int index, StringBuilder s, boolean[] used, ArrayList<String> res) {
        if (index == strArr.length) {
            res.add(s.toString());
            return;
        }

        for (int i = 0; i < strArr.length; i++) {
            if (!used[i]) {
                s.append(strArr[i]);
                int len = s.toString().length();
                used[i] = true;
                genePermutation(strArr, index + 1, s, used, res);
                // 回溯后恢复状态
                s.deleteCharAt(len - 1);
                used[i] = false;
            }
        }
    }

    // 使用堆维护动态有序数据
    /**
     * 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，
     * 那么中位数就是所有数值排序之后位于中间的数值。如果从数据流中读出偶数个数值，
     * 那么中位数就是所有数值排序之后中间两个数的平均值。我们使用Insert()方法读取数据流，
     * 使用GetMedian()方法获取当前读取数据的中位数。
     */
    // 使用小根堆存储数据流数据大的一半
    // 使用大根堆存储数据流数据小的一半
    // 当总数为奇数时，从小根堆顶部取中间值
    // 求中间值时，只需要取出堆顶的数据即可
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // 默认小根堆
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> -o1.compareTo(o2));
    private int count = 0; // 元素个数

    public void Insert(Integer num) {
        if (count % 2 == 0) {
            maxHeap.add(num);
            int max = maxHeap.poll();
            minHeap.add(max);
        } else {
            minHeap.add(num);
            int min = minHeap.poll();
            maxHeap.add(min);
        }
        count++;
    }

    public Double GetMedian() {
        if (count == 0) return 0.0d;

        return count % 2 == 0
                ? (minHeap.peek() + maxHeap.peek()) / 2.0
                : minHeap.peek().doubleValue();
    }

    /**
     * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。
     * 请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
     */
    // 使用递归--自顶向下
    private int[] memo; // 记忆化搜索
    public int RectCover(int target) {
        assert target >= 0;
        memo = new int[target + 1];
        Arrays.fill(memo, -1);
        return RectCoverRecur(target);
    }

    private int RectCoverRecur(int target) {
        // corner case
        if (target <= 0) return 0;

        if (target == 1) return 1;
        if (target == 2) return 2;

        // 小矩形有横着放和竖着放两种选择
        // 竖着放，则可能的结果为 RectCover(target - 1)
        // 横着放，则横着放的这个小矩形下面也必须有一个横着放的，可能的结果为 RectCover(target - 2)
        if (memo[target] == -1) {
            memo[target] = RectCoverRecur(target - 1)
                    + RectCoverRecur(target - 2);
        }

        return memo[target];
    }

    // 使用递推--自底向上
    public int RectCover2(int target) {

        int[] memo = new int[target + 1];
        Arrays.fill(memo, -1);
        memo[0] = 0;

        if (target >= 1) {
            memo[1] = 1;
        }
        if (target >= 2) {
            memo[2] = 2;
        }
        for (int i = 3; i <= target; i++) {
            memo[i] = memo[i - 1] + memo[i - 2];
        }
        return memo[target];
    }


}
