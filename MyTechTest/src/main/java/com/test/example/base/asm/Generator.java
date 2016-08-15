package com.test.example.base.asm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class Generator {

    public static void main(String[] args) {
        gen();
    }

    public static void gen() {
        try {
            ClassReader cr = new ClassReader("com/test/example/base/asm/C");
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            AddTimeClassAdapter classAdapter = new AddTimeClassAdapter(cw);
            // 使给定的访问者访问Java类的ClassReader
            cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
            byte[] data = cw.toByteArray();
            File file = new File(System.getProperty("user.dir")
                    + "\\target\\classes\\com\\test\\example\\base\\asm\\C.class");
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(data);
            fout.close();
            System.out.println("success!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
