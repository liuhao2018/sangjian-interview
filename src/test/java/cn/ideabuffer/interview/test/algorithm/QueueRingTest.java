package cn.ideabuffer.interview.test.algorithm;

import org.junit.Test;

/**
 * 判断单向链表是否有环
 * Created by sangjian on 2018/3/9 0009.
 */
public class QueueRingTest {

    @Test
    public void testRing(){

        Node head = constructQueue();
        Node slow = head, fast = head;
        boolean meet = false;
        int len = 0;
        while(fast != null) {
            fast = fast.next;
            if(fast == null) {
                System.out.println("no ring!");
                return;
            }
            fast = fast.next;
            slow = slow.next;

            if(meet && fast == slow){
                break;
            }

            if(fast == slow) {
                System.out.println("has ring!");
                System.out.println("Node : "+ fast.num);
                meet = true;
            }
            if(meet)
                len++;
        }

        System.out.println("length of ring is : " + len);

        Node p = head, q = slow;
        while(p != q) {
            p = p.next;
            q = q.next;
        }

        System.out.println("enter node is : " + q.num);

    }

    private Node constructQueue(){
        Node head = new Node(1);
        Node node = head;
        Node tmp = null;
        for (int i = 2; i <= 20; i++) {
            node.next = new Node(i);
            if(i == 12) {
                tmp = node.next;
            }
            node = node.next;

        }
        node.next = tmp;
        return head;
    }

    @Test
    public void printQueue(){
        Node tmp = constructQueue();
        while(tmp != null) {
            System.out.println(tmp.num);
            tmp = tmp.next;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class Node{
        int num;
        Node next;
        Node(int num){
            this.num = num;
        }
    }

}
