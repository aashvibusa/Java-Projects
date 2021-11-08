import java.lang.Math;
public class DiscreteDistribution {

    public static void main (String[] args) {

        int count = args.length;
        int a[] = new int[count];
        int b[] = new int[count];
        //System.out.println(count);
        int m = Integer.parseInt(args[0]);
        // define variable x to hold cumulative sum
        int x = 0;
        int ri;

        for (int i = 0; i < count - 1; i++) {

            a[i] = Integer.parseInt(args[i+1]);
            //System.out.println(a[i]);
        }
        //System.out.println("-------");
        b[0] = 0;
        for (int j = 1; j < count ; j++) {

            b[j] = x + a[j-1];
            x = b[j];
            //System.out.println(b[j]);
        }
        //System.out.println("-------");
        for (int k = 0; k < m; k++) {
                double r = Math.random() * (b[count-1]);
                ri = (int) r;
                //System.out.println("random # = " + r);
                //System.out.println("random int # = " + ri);

            for (int q = 1; q < count; q++) {
                //System.out.println("b[" + (q-1) + "] = " + b[q-1] + "b[" + q + "] = " + b[q]);
                if ((ri >= b[q-1]) && (ri < b[q])) {
                    //System.out.println("b[" + (q-1) + "] = " + b[q-1] + "b[" + q + "] = " + b[q]);
                    System.out.print(q + " ");
                }
            }
        }
    }
}
