import java.lang.Math;
public class Birthday {

    public static void main (String[] args) {

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        int people[] = new int[trials + 1];

        int t;
        int i;
        boolean CondMet;
        //System.out.println(room[i]);

        if (n == 1) {
            System.out.println(n + "    " + (n - 1) + "    " + (n - 1));
        } else {

            for (int z = 0; z < trials; z++) {

                int[] room = new int[n];
                room[0] = 0;

                i = 1;
                CondMet = false;

                while (!CondMet) {
                    //System.out.println("i = " + i);
                    double x = (Math.random() * (n - 1));
                    t = (int) x;
                    room[i] = t;
                /*System.out.println("t = " + t);
                System.out.println("------");
                System.out.println(room.length); */

                    for (int j = 0; j < n; j++) {

                        if (i != j) {


                            if (room[j] == room[i]) {
                                CondMet = true;
                                //System.out.println("CondMet Value = " + CondMet);
                                //System.out.println("i = " + i);
                                people[z] = i;
                                //System.out.println("people[" + z + "] = " + people[z]);
                                break;
                            }
                        }
                    }
                    i++;
                    //System.out.println("i = " + i);

                }
            }
            double percent = 0.0;
            int count;
            int runTotal = 0;
            int y = 1;

            while (percent < 0.5) {
                count = 0;

                for (int k = 0; k < trials; k++) {

                    if (y == people[k]) {
                        count++;
                        //System.out.println("y = " + y);
                    }
                }
                runTotal += count;
                percent = (double) runTotal / (double) trials;

                System.out.println(y + "    " + count + "    " + percent);

                y++;

            }
        }
    }
}
