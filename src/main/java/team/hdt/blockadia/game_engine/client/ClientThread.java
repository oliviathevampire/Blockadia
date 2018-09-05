package team.hdt.blockadia.game_engine.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ConnectException;
import java.util.concurrent.atomic.AtomicLong;


public class ClientThread extends Thread {
    // every clientThread is passed which command to send to the server
    int menuSelection;
    // every clientThread is passed the hostname of the server to connect to
    String hostName;
    Socket socket = null;

    // totalTime is used to keep the sum of response times for all threads. after all threads
    // have completed it is divided by the total number of threads to get an
    // average response time
    AtomicLong totalTime;

    // runningThreads is the total number of running threads. it is set to numThreads (the number
    // of threads that are started) before any threads are started by the Client class. Every time
    // a ClientThread finishes it will decrement runningThreads by one, so runningThreads == 0 when
    // all threads have finished
    AtomicLong runningThreads;

    // each class is passed false for printOutput if the number of threads started is > 1. When running more
    // than one client thread the clientThreads should not print output, input order to not clutter the screen
    boolean printOutput;

    // startTime and endTime are used to keep track of the current time when the thread conects to the
    // server and when the thread gets a response from the server. The difference between the two
    // (endTime - startTime) is the response time
    long startTime;
    long endTime;

    ClientThread(String hostName, int menuSelection, AtomicLong totalTime, boolean printOutput, AtomicLong runningThreads) {
        this.menuSelection = menuSelection;
        this.hostName = hostName;
        this.totalTime = totalTime;
        this.printOutput = printOutput;
        this.runningThreads = runningThreads;
    }

    public void run() {
        PrintWriter out = null;
        BufferedReader input = null;
        try {
            //creates a new Socket object and names it socket.
            //Establishes the socket connection between the client & server
            //name of the machine & the port number to which we want to connect
            socket = new Socket(hostName, 15432);
            if (printOutput) {
                System.out.print("Establishing connection.");
            }
            //opens a PrintWriter on the socket input autoflush mode
            out = new PrintWriter(socket.getOutputStream(), true);

            //opens a BufferedReader on the socket
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            if (printOutput) System.out.println("\nRequesting output for the '" + menuSelection + "' command from " + hostName);

            // get the current time (before sending the request to the server)
            startTime = System.currentTimeMillis();

            // send the command to the server
            out.println(Integer.toString(menuSelection));
            if (printOutput) System.out.println("Sent output");

            // read the output from the server
            String outputString;
            while (((outputString = input.readLine()) != null) && (!outputString.equals("END_MESSAGE"))) {
                if (printOutput) System.out.println(outputString);
            }

            // get the current time (after connecting to the server)
            endTime = System.currentTimeMillis();
            // endTime - startTime = the time it took to get the response from the sever
            totalTime.addAndGet(endTime - startTime);

        }
        catch (UnknownHostException e) {
            System.err.println("Unknown host: " + hostName);
            System.exit(1);
        }
        catch (ConnectException e) {
            System.err.println("Connection refused by host: " + hostName);
            System.exit(1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        // finally, close the socket and decrement runningThreads
        finally {
            if (printOutput) System.out.println("closing");
            try {
                socket.close();
                runningThreads.decrementAndGet();
                System.out.flush();
            }
            catch (IOException e ) {
                System.out.println("Couldn't close socket");
            }
        }

    }

}
