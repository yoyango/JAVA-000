package hello;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;

public class XlassClassloader extends ClassLoader{
    private String path;

    public XlassClassloader(String path) {
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        name = name.replaceAll("/.","//");//处理类名中，如果有包名的情况，com.xxx.Hello
        String classpath = path + name + ".xlass";// 专门处理xlass的加载器
        try {
            byte[]   data = readClass(classpath);

        byte[] classData = decode(data);
        return defineClass(name,classData,0,classData.length);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

    private byte[] decode(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            byte temp = data[i];
            data[i] = (byte) (255 - temp);
        }
        return data;
    }

    private byte[] readClass(String classpath) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(classpath);
        int available = fileInputStream.available();
        byte[] data = new byte[available];//一次读取
        fileInputStream.read(data);
        fileInputStream.close();
        return data;
    }

    public static void main(String[] args) throws Exception{
        XlassClassloader xlassClassloader = new XlassClassloader("F:\\luoyrcode\\microservice\\code\\ms\\demo\\example\\src\\test\\java\\hello\\");
        Class<?> hello = xlassClassloader.loadClass("Hello");
        Object o = hello.newInstance();
        Method me = hello.getDeclaredMethod("hello", null);
        me.invoke(o,null);


    }
}
