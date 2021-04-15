package pt.iscte.questionengine.control.questions;

public class TestData {

    static int fact(int n) {
        if(n == 1) return 1;
        else return n*(fact(n-1));
    }

    static int sum(int[] a) {
        int x = 3;
        int s = 0;
        int i = 0;
        while(i < a.length) {
            s += a[i];
            i++;
        }
        return s;
    }

    static int count(char[] a, char n) {
        int c = 0;
        int i = 0;
        while(i < a.length) {
            if(a[i] == n)
                c++;
            i++;
        }
        return c;
    }

    static int calls1Function() {
        sum(new int[]{1,2});
        return 3;
    }

    static int calls2Functions() {
        sum(new int[]{1,2});
        fact(3);
        return 3;
    }

    static int methodWithVariable() {
        int i = 3;
        return i;
    }




}
