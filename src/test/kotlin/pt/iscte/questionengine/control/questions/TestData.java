package pt.iscte.questionengine.control.questions;

public class TestData {

    static int fact(int n) {
        if(n == 1) return 1;
        else return n*(fact(n-1));
    }

    static int sum(int[] a) {
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
}
