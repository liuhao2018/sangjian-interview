package cn.ideabuffer.interview.test.proxy;

import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by sangjian on 2018/3/8.
 */
public class TestMain {

    @Test
    public void main() {

        /**
         * 通过Proxy.newProxyInstance()方法为我们的业务类生成动态代理类实例proxyInstance对象
         * 它需要三个参数
         * 当前的Classloader,用来加载动态生成代理类;动态代理类要实现的业务接口;InvocationHandler执行代理操作，并调用真正的业务类来执行业务方法；
         */
        ITaskService proxyInstance = (ITaskService) Proxy.newProxyInstance(
                TestMain.class.getClassLoader(),  //装载生成的动态代理类的classloader对象
                new Class[]{ITaskService.class},  //生成的动态代理类需要实现的业务接口
                new InvocationHandler() {          //InvocationHandler，调用代理操作，并执行真正的业务方法；
                    //被代理的目标对象
                    ITaskService taskService = new TaskServiceImpl();

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        try {
                            //业务方法执行前打印
                            System.out.println("before");
                            //调用业务方法
                            Object obj = method.invoke(taskService, args);
                            //业务方法执行后打印
                            System.out.println("after");
                            return obj;
                        } catch (Throwable e) {
                            //业务方法抛出异常打印
                            System.out.println("exception");
                            throw e;
                        } finally {
                            //业务方法执行完成打印（无论是否发生异常）
                            System.out.println("finally");
                        }
                    }
                }
        );
        //使用代理对象执行代理方法
        proxyInstance.doTask();
    }

    @Test
    public void genClass() throws Exception {
        byte[] bytes = ProxyGenerator.generateProxyClass("TaskService$proxy", new Class[]{ITaskService.class});
        FileOutputStream fos = new FileOutputStream(new File("/Users/sangjian/dev/idea-project/sangjian-interview/TaskService$proxy.class"));
        fos.write(bytes);
        fos.flush();
        fos.close();
        Object obj = new Object();
        System.in.read();
    }
}
