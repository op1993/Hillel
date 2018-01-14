/**
 * Created by Oleh on 14-Jan-18.
 */
public class Hillel {
    private String domainName;
    private long sumOfValues;

    public static void main(String[] args) {
        Hillel hillel = new Hillel();
        //   hillel.buildPyramid(5);
        // System.out.println(hillel.getSumOfMultiples(4));
        //hillel.returnDomain("https://www.google.com/test/test");
        hillel.is(new int[]{2, 1, 12, 55555, 5}, 55556);
    }

    private String returnDomain(String url) {
        String[] splitUrl;
        domainName = url.replace("https://", "");
        domainName = domainName.replace("http://", "");
        splitUrl = domainName.split("/");
        domainName = splitUrl[0];
        System.out.println(domainName);
        return domainName;
    }

    private void buildPyramid(int maxLevel) {
        for (int currentLevel = 1; currentLevel < maxLevel; currentLevel++) {
            for (int indent = maxLevel - 1; indent >= currentLevel; indent--) {
                System.out.print(" ");
            }
            for (int c = 0; c <= currentLevel; c++) {
                System.out.print("#");
            }
            System.out.print(" ");
            for (int c = 0; c <= currentLevel; c++) {
                System.out.print("#");
            }
            System.out.println();
        }
    }

    private long getSumOfMultiples(long value) {
        for (int a = 3; a < value; a++) {
            if (a % 3 == 0 || a % 5 == 0)
                sumOfValues += a;
        }
        return sumOfValues;
    }

    private Boolean is(int income[], int result) {
        boolean res = false;
        for (int c = 0; c < income.length; c++) {
            for (int a = 0; a < income.length - 1; a++) {
                if (income[c] + income[a] == result) {
                    res = true;
                    return res;
                }
            }

            //{1,2,2,4,5}
        }
        System.out.println(res);
        return res;
    }
}
