package edu.nuaa.unsafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/27 10:36
 */
public class AtomicReferenceDemo {
    public static AtomicReference<Student> atomicStudentRef = new AtomicReference<Student>();

    public static void main(String[] args) {
        Student s1 = new Student(1, "竹子");
        atomicStudentRef.set(s1);
        Student s2 = new Student(2, "熊猫");
        atomicStudentRef.compareAndSet(s1, s2);
        System.out.println(atomicStudentRef.get().toString());
        //执行结果:Student{id=2, name="熊猫"}
    }

    static class Student {
        private int id;
        public String name;

        public Student(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Student{" +"id=" + id +", name=" + name +"}";
        }
    }
}

