package socket;

import java.io.*;
import java.net.*;

public class Cliente {
    private static final String HOST = "localhost"; // Endereço do servidor
    private static final int PORT = 12345; // Porta do servidor

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado ao servidor.");

            // Recebe a solicitação de token do servidor
            System.out.println(in.readLine());

            // Envia o token
            String token = userInput.readLine();
            out.println(token);

            // Lê a resposta do servidor após o token
            String response = in.readLine();
            System.out.println(response);

            // Se a autenticação foi bem-sucedida, começa a interação com o servidor
            if (response.contains("Autenticação bem-sucedida")) {
                String message;
                while (true) {
                    System.out.print("Digite uma mensagem: ");
                    message = userInput.readLine();
                    out.println(message); // Envia a mensagem para o servidor
                    System.out.println(in.readLine()); // Recebe e exibe a resposta do servidor
                }
            } else {
                System.out.println("Falha na autenticação. Encerrando.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao se conectar ao servidor: " + e.getMessage());
        }
    }
}

