package com.ls.demo.rpc.system_b.socket;

import com.ls.demo.rpc.common.RpcRequest;
import com.ls.demo.rpc.common.RpcResponse;
import com.ls.demo.rpc.system_b.InfoQuerierImpl;
import com.ls.demo.rpc.system_b.UserQuerierImpl;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private final Map<String, Object> servicesMap;

    public Server() {
        this.servicesMap = new HashMap<>();
        servicesMap.put("com.ls.demo.rpc.common.InfoQuerier", new InfoQuerierImpl());
        servicesMap.put("com.ls.demo.rpc.common.UserQuerier", new UserQuerierImpl());
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                OutputStream outputStream = socket.getOutputStream();
                InputStream inputStream = socket.getInputStream();
                ObjectOutputStream out = new ObjectOutputStream(outputStream);
                ObjectInputStream in = new ObjectInputStream(inputStream);

                RpcRequest request = (RpcRequest) in.readObject();

                System.out.println("request: " + request);
                String interfaceName = request.getInterfaceName();
                String functionName = request.getFunctionName();
                Class<?>[] parameterTypes = request.getParameterTypes();
                Object[] requestArgs = request.getArgs();

                Object obj = this.servicesMap.get(interfaceName);
                Method method = obj.getClass().getMethod(functionName, parameterTypes);
                Object res = method.invoke(obj, requestArgs);
                RpcResponse response = new RpcResponse();
                response.setMsg("success");
                response.setData(res);

                out.writeObject(response);
                out.flush();
                in.close();
                out.close();
                socket.close();
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                System.out.println("exception: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("exception: " + e.getMessage());
                throw new RuntimeException(e);
            }

        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
    }
}

