package cn.ideabuffer.interview.test.jvm;

import org.junit.Test;

/**
 * Created by sangjian on 2018/1/24 0024.
 * -server -Xmn1m -Xmx20m -Xss1m -XX:+PrintGCDetails 
 * -XX:-DoEscapeAnalysis  -XX:+TieredCompilation -XX:CompileThreshold=100
 */
public class OnStackTest {

    private void newUser(int i){
        User u = new User(1, i+"");
//        System.out.println(u.getName().hashCode());
    }
    @Test
    public void alloc() throws InterruptedException {
        long b=System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            newUser(i);
//            User u = new User(1, "1");
        }
        long e=System.currentTimeMillis();
        System.out.println(e-b);
        Thread.sleep(Integer.MAX_VALUE);
    }
    
    class User{
        int age;
        long sex;
        String name;
        public User(int age, String name){
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
