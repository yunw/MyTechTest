1、下面的程序会打印Error吗？
public class P1 {
    private long b = 0;
    public void set1() {
        b = 0;
    }
    public void set2() {
        b = 1;
    }
    public void check() {
        if (0 != b && 1 != b) {
            System.err.println("Error");
        }
    }
}
答：会。
程序验证：com.test.example.mianshi
知识点：
1、并发操作，可能导致0!=b && 1 !=b的结果为false。
2、在32位操作系统上，对64位的long、double类型数据的读写需要两步，这两步不是原子的操作。因此b的值可能既不是0，也不是1。在64位操作系统上不会出现这种问题。
参考：http://88250.b3log.org/java-atomic-conncurrent