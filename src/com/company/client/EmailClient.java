package com.company.client;
import java.io.*;
import java.net.*;
import java.util.*;
public class EmailClient {
    private static InetAddress host; //, InetAddress is a class that represents an Internet Protocol (IP) address. It's used to encapsulate both IPv4 and IPv6 addresses
    private static final int Port=1234;
    private static String name;
    private static Scanner networkInput,userEntery;
    private static PrintWriter networkOutput; //PrintWriter is a Java class that provides methods to print formatted representations of objects to a text-output stream. It can be used to write data to various output destinations such as files, sockets, or other output streams.
    public static void main(String[] args)
        throws IOException
    {
        try {
            host=InetAddress.getLocalHost();
        }catch (UnknownHostException u){
            System.out.println("HOST ID NOT FOUND!");
            System.exit(1);
        }
        userEntery=new Scanner(System.in);
        do {
            System.out.println("Enter name ('Dave' or 'Karen') : ");
            name=userEntery.nextLine();
        }while (!name.equals("Dave")&&!name.equals("Karen"));
        talkToServer();
    }
    private static void talkToServer() throws IOException
    {
        String option, message, response;
        do
        {
            try (Socket socket=new Socket(host,Port)){
                networkInput=new Scanner(socket.getInputStream());
                networkOutput=new PrintWriter(socket.getOutputStream(),true);
                System.out.println("Enter 'send' to send a message or 'read' to read messages");
                option=userEntery.nextLine();
                if(option.equals("send")){
                    doSend();
                }else if(option.equals("read")){
                    doRead();
                }else {
                    System.out.println("IN_Valid option");
                }


            }catch (IOException i){
                System.out.println("Error: "+i.getMessage());
            }
            System.out.println("DO YOU WANT TO DO ANOTHER READ/SEND?(y/n)");
            option=userEntery.nextLine();
        }while (!option.equals("n"));
    }
    private static void doSend()
    {
        System.out.println("\nEnter 1-line message: ");
        String message = userEntery.nextLine();
        networkOutput.println(name);
        networkOutput.println("send");
        networkOutput.println(message);
    }
    private static void doRead() throws IOException
    {
        System.out.println("Fetching messages...");
        networkOutput.println(name);
        networkOutput.println("read");

        String response;
        while (networkInput.hasNextLine()) {
            response = networkInput.nextLine();
            if (response.equals("END")) {
                break;
            }
            // Assuming the message format is "sender: message"
            String[] parts = response.split(":");
            if (parts.length >= 2) {
                String sender = parts[0];
                String message = parts[1];
                System.out.println("From " + sender + ": " + message);
            } else {
                System.out.println("Invalid message format: " + response);
            }
        }
    }




}
