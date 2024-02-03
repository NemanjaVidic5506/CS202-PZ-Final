package com.example.slots.networking;

import com.example.slots.utill.Matrix;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {
    public static final int PORT = 8080;
    public static final String HOST = "localhost";

    public static void main(String[] args) {
        start();
    }
    /**
     * Starts the server by creating a ServerSocket and listening for incoming client connections.
     * Once a connection is established, it continuously listens for client requests,
     * responding accordingly based on the received messages.
     *
     * The server supports the following messages:
     * - 0: Disconnect signal. Terminates the server and ends the connection.
     * - 1: Request for matrix processing. Invokes the response method to handle the client's request.
     *
     * @throws RuntimeException if an IOException occurs while setting up the server socket,
     *                         accepting client connections, or handling client communication.
     *                         The IOException is wrapped in a RuntimeException for simplicity.
     */

    public static void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + serverSocket.getLocalPort());
            System.out.println("Waiting for connection...");

            final Socket client = serverSocket.accept();
            System.out.println("Client connected!");

            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());

            while (true) {
                final int message = ois.readInt();
                if (message == 0) {
                    break;
                } else if (message == 1) {
                    response(oos);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Generates a random matrix, encapsulates it in a MatrixResult object, and sends it to the client.
     *
     * @param oos The ObjectOutputStream for sending the matrix result to the client.
     * @throws IOException if an I/O error occurs while writing the matrix result to the output stream.
     *                     The IOException is thrown when there is a failure in the underlying network operations.
     */
    private static void response(final ObjectOutputStream oos) throws IOException {
        final int[][] generatedMatrix = Matrix.randomMatrixValues();
        final MatrixResult result = new MatrixResult();
        result.matrix = generatedMatrix;

        oos.writeObject(result);
        oos.flush();
    }
}