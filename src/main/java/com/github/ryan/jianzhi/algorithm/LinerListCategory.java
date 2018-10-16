package com.github.ryan.jianzhi.algorithm;

/**
 * @author ryan.houyl@gmail.com
 * @description
 * @className LinerListCategory
 * @date October 16,2018
 */
public class LinerListCategory {

    /**
     * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。
     * 输入一个数组,求出这个数组中的逆序对的总数P。
     * 并将P对1000000007取模的结果输出。 即输出P%1000000007
     * 输入描述:
     * 题目保证输入的数组中没有的相同的数字
     *
     * 数据范围：
     * 对于%50的数据,size<=10^4
     * 对于%75的数据,size<=10^5
     * 对于%100的数据,size<=2*10^5
     *
     * 思路：使用归并排序的思路，在归并之前，增加统计逆序对的逻辑
     */
    public int InversePairs(int [] array) {
        if (array == null) return 0;
        // 利用归并排序
        return mergeSort(array, 0, array.length - 1);
    }

    // array[l...r]归并排序,并返回array[l...r]中的逆序对个数
    private int mergeSort(int[] array, int l, int r) {
        if (l >= r) return 0;
        int mid = l + (r - l) / 2;
        int cnt = mergeSort(array, l, mid) + mergeSort(array, mid + 1, r);

        // 处理[l...mid]和[mid+1...r]中的逆序对
        for (int i = l, j = mid + 1; i <= mid; i++) {
            while (j <= r && array[i] > array[j]) {
                j++;
            }
            cnt = (cnt + j - (mid + 1)) % 1000000007; // think
        }

        if (array[mid] > array[mid + 1]) {
            merge(array, l, mid, r);
        }
        return cnt;
    }

    // 合并 array[l...mid]到array[mid + 1...r]的数据
    private void merge(int[] array, int l, int mid, int r) {
        int[] aux = new int[r - l + 1];
        for (int i = l; i <= r; i++) {
            aux[i - l] = array[i];
        }

        int i = l, j = mid + 1; // i,j指向两个带归并数组的头部
        // k指向array数组带归并的位置
        for (int k = l; k <= r; k++) {
            if (i > mid) {
                array[k] = aux[j - l];
                j++;
            } else if (j > r) {
                array[k] = aux[i - l];
                i++;
            } else if (aux[i - l] > aux[j - l]) {
                array[k] = aux[j - l];
                j++;
            } else {
                // aux[i - l] < aux[j - l]
                array[k] = aux[i - l];
                i++;
            }
        }
    }
}
