package cn.ideabuffer.interview.test.algorithm;

/**
 * Created by sangjian on 2018/2/26.
 */
public class AlgorithmTest {

    /**
     * 插入排序：
     *
     * 在要排序的一组数中，假定前n-1个数已经排好序，现在将第n个数插到前面的有序数列中，使得这n个数也是排好顺序的。如此反复循环，直到全部排好顺序。
     * @param a
     */
    public static void insertSort(int[] a){
        for (int i = 1; i < a.length; i++) {
            int insertNum = a[i];
            int j = i - 1;
            // 序列从后到前循环，将大于insertNum的数向后移动一格
            while(j >= 0 && insertNum < a[j]){
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = insertNum;
        }
    }

    public static void selectSort(int[] a){
        for (int i = 0; i < a.length; i++) {
            int pos = i;
            int key = a[i];
            for (int j = i + 1; j < a.length; j++) {
                if(a[j] < key){
                    key = a[j];
                    pos = j;
                }
            }
            int tmp = a[i];
            a[i] = a[pos];
            a[pos] = tmp;
        }
    }

    public static void quickSort(int a[], int start, int end){

    }

    private static void print(int a[]){
        System.out.print("[ ");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
            if(i < a.length -1){
                System.out.print(", ");
            }
        }
        System.out.println(" ]");
    }

    public static void main(String[] args) {
        int a[] = {3,5,2,76,43,12,93,52,13,37};
        selectSort(a);
        print(a);
    }

}
