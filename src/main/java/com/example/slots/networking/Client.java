package com.example.slots.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static Socket socket;

    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;

    private static boolean connected = false;

    /**
     * Establishes a connection to the server by creating a socket and initializing object input/output streams.
     * If already connected, this method returns without attempting to reconnect.
     *
     * @throws IOException if an I/O error occurs while creating the socket or initializing streams.
     *                     This exception is thrown when there is a failure in the underlying network operations.
     */
    public static void connect() throws IOException {
        if (connected) return;
        socket = new Socket(Server.HOST, Server.PORT);

        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());

        connected = true;
    }
    /**
     * Sends a request to the server and retrieves a matrix result.
     *
     * @return A 2D integer array representing the matrix result received from the server.
     * @throws RuntimeException if an IOException or ClassNotFoundException occurs during the communication with the server.
     *                         The exceptions are wrapped in a RuntimeException for simplicity.
     */
    public static int[][] request() {
        try {
            oos.writeInt(1);
            oos.flush();
            return ((MatrixResult) ois.readObject()).matrix;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Disconnects from the server by sending a disconnect signal and closing the ObjectOutputStream.
     * If not currently connected, this method returns without attempting to disconnect.
     *
     * @throws RuntimeException if an IOException occurs while sending the disconnect signal or closing the stream.
     *                         The IOException is wrapped in a RuntimeException for simplicity.
     */
    public static void disconnect() {
        if (!connected) return;
        try {
            oos.writeInt(0);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        connected = false;
    }
    /**
     * Checks if the client is currently connected to the server.
     *
     * @return true if the client is connected, false otherwise.
     */
    public static boolean isConnected() {
        return connected;
    }
}
