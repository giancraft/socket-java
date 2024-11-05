package socket;

import java.io.*;
import java.net.*;

public class Servidor {
    private static final int PORT = 12345; // Porta para o servidor
    private static final String TOKEN = "senha123"; // Token de autenticação

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado na porta " + PORT);
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                    // Solicita autenticação
                    out.println("Digite o token para autenticação:");
                    String receivedToken = in.readLine();

                    if (TOKEN.equals(receivedToken)) {
                        out.println("Autenticação bem-sucedida. Digite uma mensagem:");
                        System.out.println("Cliente autenticado com sucesso.");

                        // Echo - retorna as mensagens para o cliente e as exibe no console do servidor
                        String message;
                        while ((message = in.readLine()) != null) {
                            System.out.println("Mensagem recebida do cliente: " + message);
                            out.println("Servidor: " + message); // Responde ao cliente
                        }
                    } else {
                        out.println("Autenticação falhou. Conexão encerrada.");
                        System.out.println("Token incorreto. Cliente desconectado.");
                    }
                } catch (IOException e) {
                    System.out.println("Erro de comunicação com o cliente: " + e.getMessage());
                } finally {
                    System.out.println("Cliente desconectado.");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }
}
