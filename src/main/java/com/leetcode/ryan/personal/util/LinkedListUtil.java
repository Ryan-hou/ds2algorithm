package com.leetcode.ryan.personal.util;

import com.leetcode.ryan.personal.component.ListNode;

/**
 * @author ryan.houyl@gmail.com
 * @description:
 * @className: LinkedListUtil
 * @date January 26,2018
 */
public class LinkedListUtil {


    private LinkedListUtil() {}

    public static ListNode createLinkedList(int[] nodes, int n) {
        if (nodes == null) {
            return null;
        }

        ListNode head = new ListNode(nodes[0]);

        ListNode curNode = head;
        for (int i = 1; i < n; i++) {
            curNode.next = new ListNode(nodes[i]);
            curNode = curNode.next;
        }
        return head;
    }

    public static void printLinkedList(ListNode head) {

        ListNode cur = head;
        while (cur != null) {
            System.out.print(cur.val + " >> ");
            cur = cur.next;
        }
        System.out.println("NULL");
    }
}
