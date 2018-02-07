package com.leetcode.ryan.personal.sort;

/**
 * @author ryan.houyl@gmail.com
 * @description:
 * @className: MergeSort
 * @date February 07,2018
 */
public class MergeSort implements Sort {

    public void mergeSort(int[] arr, int n) {
        mergeSortInternal(arr, 0, n - 1);
    }

    // 递归使用归并排序，对 arr[l...r] 的范围进行排序
    private void mergeSortInternal(int[] arr, int l, int r) {
//        if (l >= r) {
//            return;
//        }
        // 优化二：当数据规模比较小时，数据比较趋于有序，使用插入排序来优化速度
        if (r - l <= 15) {
            InsertSort.insertSort(arr, l, r);
            return;
        }

        int mid = l + (r - l) / 2;
        mergeSortInternal(arr, l, mid);
        mergeSortInternal(arr, mid + 1, r);

        // 优化一：避免不必要的merge操作
        if (arr[mid] > arr[mid + 1]) {
            merge(arr, l, mid, r);
        }
    }

    private void merge(int[] arr, int l, int mid, int r) {
        // 辅助数据空间
        int[] aux = new int[r - l + 1];
        for (int i = l; i <= r; i++) {
            aux[i - l] = arr[i];
        }

        int i = l, j = mid + 1;
        for (int k = l; k <= r; k++) {
            if (i > mid) {
                arr[k] = aux[j - l];
                j++;
            } else if (j > r) {
                arr[k] = aux[i - l];
            } else if (aux[i - l] < aux[j - l]) {
                arr[k] = aux[i - l];
                i++;
            } else {
                arr[k] = aux[j - l];
                j++;
            }
        }
    }

    @Override
    public void sort(int[] nums, int n) {
        mergeSort(nums, n);
    }
}
