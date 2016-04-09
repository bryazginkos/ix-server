package ru.kos.ix.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Константин on 08.04.2016.
 */
public class AcceptThread extends Thread {

    private static final Logger logger = LogManager.getLogger(AcceptThread.class);

    private ServerSocket serverSocket;

    public AcceptThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            logger.info("Start accepting");
            while(true)
            {
                Socket clientSocket = serverSocket.accept();
                logger.info("Socket was accepted " + clientSocket);
                ClientThread clientThread = new ClientThread(clientSocket, executorService);
                clientThread.start();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }
}
