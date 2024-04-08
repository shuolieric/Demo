package com.ls.demo.rpc.system_a.socket;

import com.ls.demo.rpc.common.InfoQuerier;
import com.ls.demo.rpc.common.RpcRequest;
import com.ls.demo.rpc.common.RpcResponse;
import com.ls.demo.rpc.common.UserQuerier;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.Spliterator;

public class Client {

    public static void main1(String[] args) throws IOException, ClassNotFoundException {

        Socket socket = new Socket("localhost", 9999);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        ObjectInputStream in = new ObjectInputStream(inputStream);
        ObjectOutputStream out = new ObjectOutputStream(outputStream);

        RpcRequest request = new RpcRequest();
        request.setInterfaceName("com.ls.demo.rpc.common.InfoQuerier");
        request.setFunctionName("getBalance");
        request.setParameterTypes(new Class[]{Integer.class});
        request.setArgs(new Object[]{1});

        out.writeObject(request);
        out.flush();


        RpcResponse response = (RpcResponse) in.readObject();
        System.out.println(response.getData());

        out.close();
        in.close();
        socket.close();

    }

    public static void main(String[] args) {
        InfoQuerier infoQuerier = (InfoQuerier) getInstance(InfoQuerier.class);
        Double balance = infoQuerier.getBalance(1);
        System.out.println("result: " + balance);

        UserQuerier userQuerier = (UserQuerier) getInstance(UserQuerier.class);
        System.out.println("user name: " + userQuerier.getUserName());
    }

    public static <T> Object getInstance(Class<T> clz) {
        return Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket("localhost", 9999);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                RpcRequest request = new RpcRequest(method.getDeclaringClass().getName(), method.getName(), method.getParameterTypes(), args);
                System.out.println(request);
                out.writeObject(request);
                out.flush();

                RpcResponse response = (RpcResponse) in.readObject();

                in.close();
                out.close();
                socket.close();

                return response.getData();
            }
        });
    }
}
