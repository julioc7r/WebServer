import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/* Tarefa de Programacão 1 - Julio Cesar Rogacheski */

public final class WebServer
{
    public static void main(String argv[]) throws Exception
    {
        // Set the port number.
        int port = 6789;
        // Establish the listen socket.
        ServerSocket server = new ServerSocket(port);
        System.out.println("Servidor iniciado na porta 6789");

        // Process HTTP service requests in an infinite loop.
        while (true) {
        // Listen for a TCP connection request.            
        Socket cliente = server.accept();

        // Construct an object to process the HTTP request message.
            HttpRequest request = new HttpRequest(cliente);
            // Create a new thread to process the request.
            Thread thread = new Thread(request);
            // Start the thread.
            thread.start();    
            //server.close();
        }
    }
}

final class HttpRequest implements Runnable
{
    final static String CRLF = "\r\n";
    Socket socket;
    // Constructor
    public HttpRequest(Socket socket) throws Exception
    {
        this.socket = socket;
    }
    // Implement the run() method of the Runnable interface.
    public void run()
    {
        try {
            processRequest();
            } catch (Exception e) {
            System.out.println(e);
            }
    }
    private void processRequest() throws Exception
    {
        System.out.println("Cliente conectado do IP "+socket.getInetAddress().getHostAddress());
        // Get a reference to the socket's input and output streams.
        InputStream is = socket.getInputStream();

        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        // Set up input stream filters.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // Get the request line of the HTTP request message.
        String requestLine = br.readLine();
        // Display the request line.
        System.out.println(requestLine);
        // Get and display the header lines.
        String headerLine = null;
        while ((headerLine = br.readLine()).length() != 0) {
            System.out.println(headerLine);
        }
        /*
        // Extract the filename from the request line.
        StringTokenizer tokens = new StringTokenizer(requestLine);
        tokens.nextToken(); // skip over the method, which should be "GET"
        String fileName = tokens.nextToken();
        // Prepend a "." so that file request is within the current directory.
        fileName = "." + fileName;
        
        // Open the requested file.
        FileInputStream fis = null;
        boolean fileExists = true;
        try {
        fis = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
        fileExists = false;
        }
        // Construct the response message.
        String statusLine = null;
        String contentTypeLine = null;
        String entityBody = null;
        if (fileExists) {

        statusLine = ?;
        contentTypeLine = "Content-type: " +
        contentType( fileName ) + CRLF;

        } else {
        statusLine = ?;
        contentTypeLine = ?;
        entityBody = "<HTML>" +
        "<HEAD><TITLE>Not Found</TITLE></HEAD>" +
        "<BODY>Not Found</BODY></HTML>";

        }   
        // Send the status line.
br
        
        
        
        
        
        
        
        
        */

        // Close streams and socket.
        os.close();
        br.close();
        socket.close();
    }
}

