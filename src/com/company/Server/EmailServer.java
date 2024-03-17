package com.company.Server;
import com.sun.jdi.request.InvalidRequestStateException;

import java.io.*;
import java.net.*;
import java.util.*;

public class EmailServer {
    private static ServerSocket serverSocket;
    private static final int Port=1234;
    private static final String client1="Dave";
    private static final String client2="Karen";
    private static final int Max_messages=10;
    private static String[] mailbox1=new String[Max_messages];
    private static String[] mailbox2=new String[Max_messages];
    private static int messagesInbox1=0;
    private static int messagesInbox2=0;
    public static void main(String[] args){
        System.out.println("Openning Connection.....");
        try {
            serverSocket=new ServerSocket(Port);

        }catch (IOException i){
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }do{
            try{
                runSevice();

            }catch (InvalidClientException icException){
                System.out.println("Error"+icException);
            }catch (InvalidRequestException irException){
                System.out.println("Error"+irException);
            }
        }while (true);

    }
    private static void runSevice()
            throws InvalidClientException,
            InvalidRequestException
        {
            try {
                Socket link=serverSocket.accept();
                Scanner input=new Scanner(link.getInputStream());
                PrintWriter output=new PrintWriter(link.getOutputStream(),true);
                String name=input.nextLine();
                String sendRead=input.nextLine();
                if(!name.equals(client1)&&!name.equals(client2)){
                    throw new InvalidClientException();
                }if(!sendRead.equals("send")&&!sendRead.equals("read")){
                    throw new InvalidRequestException();
                }
                System.out.println("\n" + name + " "
                        + sendRead + "ing mail...");
                if(name.equals(client1)){
                    if(sendRead.equals("send")){
                        doSend(mailbox2,messagesInbox2,input);
                        if(messagesInbox2<Max_messages){
                            messagesInbox2++;
                        }else {
                            doRead(mailbox1,messagesInbox1,output);
                            messagesInbox1=0;
                        }
                    }
                }
                else { //message send from client2
                    if(sendRead.equals("send")){
                        doSend(mailbox1,messagesInbox1,input);
                        if(messagesInbox1<Max_messages){
                            messagesInbox1++;
                        }else {
                            doRead(mailbox2,messagesInbox2,output);
                            messagesInbox2=0;
                        }
                    }

                }
                link.close();
            }catch (IOException i){
                i.printStackTrace();
            }

        }
        private static void doSend(String[] mailbox,int messagesInbox, Scanner input){
        //if client request sending ,so server must read message from this client and then place message
            //into message box (if there is a space for this message



            //take client req
            String message=input.nextLine();
            if(messagesInbox==Max_messages){
                System.out.println("Message box is full!");
            }else{
                mailbox[messagesInbox]=message;
            }
        }
    private static void doRead(String[] mailbox, int messagesInbox, PrintWriter output) {
        System.out.println("\nSending " + messagesInbox + " messages");
        output.println(messagesInbox);  // Send the number of messages to the client

        // Send each message in the mailbox to the client
        for (int i = 0; i < messagesInbox; i++) {
            output.println(mailbox[i]);
        }
    }

}
class InvalidClientException extends Exception{
    public InvalidClientException()
    {
        super(("Invalid client name!"));
    }
    public InvalidClientException(String message){
        super(message);
    }

}
class InvalidRequestException extends Exception {
    public InvalidRequestException(){
        super("Inavalid Request!");
    }
    public InvalidRequestException(String message){
        super((message));
    }
}