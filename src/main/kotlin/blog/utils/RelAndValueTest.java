package blog.utils;

public class RelAndValueTest {

    /**
     * 方法中传递的是栈内存中的值
     * @param args
     */
    public static void main(String[] args) {
        String a = new String("aaa");
        Integer i = new Integer("100");
        change(a);
        changInt(i);
        System.out.println(a);
        System.out.println(i);
    }

    private static void changInt(Integer i) {
        i = new Integer("200");
    }

    private static void change(String a) {
        a = "fdafafaf";
    }
}
