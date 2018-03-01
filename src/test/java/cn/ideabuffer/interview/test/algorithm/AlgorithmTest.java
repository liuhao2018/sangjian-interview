package cn.ideabuffer.interview.test.algorithm;

/**
 * Created by sangjian on 2018/2/26.
 */
public class AlgorithmTest {

    /**
     * 插入排序：
     * <p>
     * 在要排序的一组数中，假定前n-1个数已经排好序，现在将第n个数插到前面的有序数列中，使得这n个数也是排好顺序的。如此反复循环，直到全部排好顺序。
     *
     * @param a
     */
    public static void insertSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int insertNum = a[i];
            int j = i - 1;
            // 序列从后到前循环，将大于insertNum的数向后移动一格
            while (j >= 0 && insertNum < a[j]) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = insertNum;
        }
    }

    public static void selectSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int pos = i;
            int key = a[i];
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < key) {
                    key = a[j];
                    pos = j;
                }
            }
            int tmp = a[i];
            a[i] = a[pos];
            a[pos] = tmp;
        }
    }

    /**
     * <ol>
     * <li>先从数列中取出一个数作为key值；</li>
     * <li>将比这个数小的数全部放在它的左边，大于或等于它的数全部放在它的右边；</li>
     * <li>对左右两个小数列重复第二步，直至各区间只有1个数。</li>
     * </ol>
     *
     * @param a
     * @param start
     * @param end
     */
    public static void quickSort(int a[], int start, int end) {

        if (start >= end)
            return;

        int i = start, j = end;
        int key = a[i];
        while (i < j) {

            while (j > i && a[j] >= key) {
                j--;
            }

            if (i < j) {
                a[i] = a[j];
                i++;
            }

            while (j > i && a[i] <= key) {
                i++;
            }

            if (i < j) {
                a[j] = a[i];
                j--;
            }

        }

        a[i] = key;
        quickSort(a, start, i);
        quickSort(a, i + 1, end);

    }


    /**
     * 归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法的一个非常典型的应用。
     * 首先考虑下如何将2个有序数列合并。这个非常简单，只要从比较2个数列的第一个数，谁小就先取谁，取了后就在对应数列中删除这个数。然后再进行比较，如果有数列为空，那直接将另一个数列的数据依次取出即可。
     *
     * 平均时间复杂度：O(NlogN)
     * @param a
     * @param first
     * @param last
     */
    public static void mergeSort(int a[], int first, int last) {
        int i = first, j = last, m = (first + last) / 2;

        if (i < j) {
            mergeSort(a, i, m);
            mergeSort(a, m + 1, last);
            mergeArray(a, i, m, j);
        }
    }

    public static void mergeArray(int a[], int first, int middle, int last) {
        int i = first, j = middle + 1, m = middle, k = 0;
        int len = last - first + 1;
        int tmp[] = new int[len];
        while (i <= m && j <= last) {
            if (a[i] < a[j]) {
                tmp[k++] = a[i++];
            } else {
                tmp[k++] = a[j++];
            }
        }

        while (i <= m) {
            tmp[k++] = a[i++];
        }
        while (j <= last) {
            tmp[k++] = a[j++];
        }

        for (int l = 0; l < len; l++) {
            a[first + l] = tmp[l];
        }
    }

    private static void print(int a[]) {
        System.out.print("[ ");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
            if (i < a.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(" ]");
    }

    public static void main(String[] args) {
        int a[] = {3, 5, 2, 76, 43, 12, 93, 52, 13, 37};
        mergeSort(a, 0, a.length - 1);
        print(a);
    }

}
