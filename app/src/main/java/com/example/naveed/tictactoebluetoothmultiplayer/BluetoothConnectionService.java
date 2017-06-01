package com.example.naveed.tictactoebluetoothmultiplayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by Naveed on 5/29/2017.
 */
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

public class BluetoothConnectionService extends Activity
{
    int count = 0;
    Handler handler;
    Main main;
    Game game;
    Message msg;
    private static final String TAG = "BluetoothConnectionServ";

    private static final String appName = "MYAPP";

    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    private final BluetoothAdapter mBluetoothAdapter;
    Context mContext;

    private AcceptThread mInsecureAcceptThread;

    private ConnectThread mConnectThread;
    private BluetoothDevice mmDevice;
    private UUID deviceUUID;
    String incomingMessage;
    ProgressDialog mProgressDialog;

    private ConnectedThread mConnectedThread;

    public BluetoothConnectionService()
    {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public BluetoothConnectionService(Context context)
    {
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        main = new Main();
        handler = new Handler();
       /* if(incomingMessage.equalsIgnoreCase("open"))
        {
            Intent game = new Intent(BluetoothConnectionService.this,Game.class);
            startActivity(game);
        }*/
    }

    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    private class AcceptThread extends Thread
    {

        // The local server socket
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread()
        {
            BluetoothServerSocket tmp = null;

            // Create a new listening server socket
            try
            {
                tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName, MY_UUID_INSECURE);

                Log.d(TAG, "AcceptThread: Setting up Server using: " + MY_UUID_INSECURE);
            } catch (IOException e)
            {
                Log.e(TAG, "AcceptThread: IOException: " + e.getMessage());
            }

            mmServerSocket = tmp;
        }

        public void run()
        {
            Log.d(TAG, "run: AcceptThread Running.");

            BluetoothSocket socket = null;

            try
            {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                Log.d(TAG, "run: RFCOM server socket start.....");

                socket = mmServerSocket.accept();

                Log.d(TAG, "run: RFCOM server socket accepted connection.");

            } catch (IOException e)
            {
                Log.e(TAG, "AcceptThread: IOException: " + e.getMessage());
            }

            //talk about this is in the 3rd
            if (socket != null)
            {
                connected(socket, mmDevice);
            }

            Log.i(TAG, "END mAcceptThread ");
        }

        public void cancel()
        {
            Log.d(TAG, "cancel: Canceling AcceptThread.");
            try
            {
                mmServerSocket.close();
            } catch (IOException e)
            {
                Log.e(TAG, "cancel: Close of AcceptThread ServerSocket failed. " + e.getMessage());
            }
        }

    }

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread
    {
        private BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice device, UUID uuid)
        {
            Log.d(TAG, "ConnectThread: started.");
            mmDevice = device;
            deviceUUID = uuid;
        }

        public void run()
        {
            BluetoothSocket tmp = null;
            Log.i(TAG, "RUN mConnectThread ");

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try
            {
                Log.d(TAG, "ConnectThread: Trying to create InsecureRfcommSocket using UUID: "
                        + MY_UUID_INSECURE);
                tmp = mmDevice.createRfcommSocketToServiceRecord(deviceUUID);
            } catch (IOException e)
            {
                Log.e(TAG, "ConnectThread: Could not create InsecureRfcommSocket " + e.getMessage());
            }

            mmSocket = tmp;

            // Always cancel discovery because it will slow down a connection
            mBluetoothAdapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket

            try
            {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();

                Log.d(TAG, "run: ConnectThread connected.");
            } catch (IOException e)
            {
                // Close the socket
                try
                {
                    mmSocket.close();
                    Log.d(TAG, "run: Closed Socket.");
                } catch (IOException e1)
                {
                    Log.e(TAG, "mConnectThread: run: Unable to close connection in socket " + e1.getMessage());
                }
                Log.d(TAG, "run: ConnectThread: Could not connect to UUID: " + MY_UUID_INSECURE);
            }

            //will talk about this in the 3rd video
            connected(mmSocket, mmDevice);
        }

        public void cancel()
        {
            try
            {
                Log.d(TAG, "cancel: Closing Client Socket.");
                mmSocket.close();
            } catch (IOException e)
            {
                Log.e(TAG, "cancel: close() of mmSocket in Connectthread failed. " + e.getMessage());
            }
        }
    }


    /**
     * Start the chat service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume()
     */
    public synchronized void start()
    {
        Log.d(TAG, "start");

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null)
        {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mInsecureAcceptThread == null)
        {
            mInsecureAcceptThread = new AcceptThread();
            mInsecureAcceptThread.start();
        }
    }

    /**
     * AcceptThread starts and sits waiting for a connection.
     * Then ConnectThread starts and attempts to make a connection with the other devices AcceptThread.
     **/

    public void startClient(BluetoothDevice device, UUID uuid)
    {
        Log.d(TAG, "startClient: Started.");


        //initprogress dialog
        mProgressDialog = ProgressDialog.show(mContext, "Connecting Bluetooth"
                , "Please Wait...", true);

        mConnectThread = new ConnectThread(device, uuid);
        mConnectThread.start();
//        write("open".getBytes());
    }

    /**
     * Finally the ConnectedThread which is responsible for maintaining the BTConnection, Sending the data, and
     * receiving incoming data through input/output streams respectively.
     **/
    public class ConnectedThread extends Thread
    {
        private BluetoothSocket mmSocket;
        private InputStream mmInStream;
        private OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket)
        {
            Log.d(TAG, "ConnectedThread: Starting.");


            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;


            //dismiss the progressdialog when connection is established
            try
            {
                mProgressDialog.dismiss();
            } catch (NullPointerException e)
            {
                e.printStackTrace();
            }


            try
            {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run()
        {
            byte[] buffer = new byte[1024];  // buffer store for the stream

            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true)
            {
                // Read from the InputStream
                try
                {
                    bytes = mmInStream.read(buffer);
                    incomingMessage = new String(buffer, 0, bytes);

                    checkIncomingMessage(incomingMessage);


                    Log.d(TAG, "InputStream: " + incomingMessage);


                } catch (IOException e)
                {
                    Log.e(TAG, "write: Error reading Input Stream. " + e.getMessage());
                    break;
                }
            }
        }

        private void checkIncomingMessage(final String incomingMessage)
        {
            if (incomingMessage.equalsIgnoreCase("open"))
            {
                Main.startBtn.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Main.startBtn.setVisibility(View.VISIBLE);
                    }
                });
                //main = new Main();
                //main.startGame();
                Log.d("Naveed", "Starting Activity");
            }
            if (incomingMessage.equalsIgnoreCase("b1"))
            {
                ///game = new Game();
                Game.b1.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (Game.b1.getText().toString().equalsIgnoreCase(""))
                        {
                            if (Game.player == 1)
                            {
                                Game.player = 2;
                                Game.b1.setText("X");
                                decideWinner();
                            } else if (Game.player == 2)
                            {
                                Game.player = 1;
                                Game.b1.setText("O");
                                decideWinner();
                            }
                        }
                    }
                });
            }
            if (incomingMessage.equalsIgnoreCase("b2"))
            {
                ///game = new Game();
                Game.b2.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (Game.b2.getText().toString().equalsIgnoreCase(""))
                        {
                            if (Game.player == 1)
                            {
                                Game.player = 2;
                                Game.b2.setText("X");
                                decideWinner();
                            } else if (Game.player == 2)
                            {
                                Game.player = 1;
                                Game.b2.setText("O");
                                decideWinner();
                            }
                        }
                    }
                });
            }
            if (incomingMessage.equalsIgnoreCase("b3"))
            {
                ///game = new Game();
                Game.b3.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (Game.b3.getText().toString().equalsIgnoreCase(""))
                        {
                            if (Game.player == 1)
                            {
                                Game.player = 2;
                                Game.b3.setText("X");
                                decideWinner();
                            } else if (Game.player == 2)
                            {
                                Game.player = 1;
                                Game.b3.setText("O");
                                decideWinner();
                            }
                        }
                    }
                });
            }
            if (incomingMessage.equalsIgnoreCase("b4"))
            {
                ///game = new Game();
                Game.b4.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (Game.b4.getText().toString().equalsIgnoreCase(""))
                        {
                            if (Game.player == 1)
                            {
                                Game.player = 2;
                                Game.b4.setText("X");
                                decideWinner();
                            } else if (Game.player == 2)
                            {
                                Game.player = 1;
                                Game.b4.setText("O");
                                decideWinner();
                            }
                        }
                    }
                });
            }
            if (incomingMessage.equalsIgnoreCase("b5"))
            {
                ///game = new Game();
                Game.b5.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (Game.b5.getText().toString().equalsIgnoreCase(""))
                        {
                            if (Game.player == 1)
                            {
                                Game.player = 2;
                                Game.b5.setText("X");
                                decideWinner();
                            } else if (Game.player == 2)
                            {
                                Game.player = 1;
                                Game.b5.setText("O");
                                decideWinner();
                            }
                        }
                    }
                });
            }
            if (incomingMessage.equalsIgnoreCase("b6"))
            {
                ///game = new Game();
                Game.b6.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (Game.b6.getText().toString().equalsIgnoreCase(""))
                        {
                            if (Game.player == 1)
                            {
                                Game.player = 2;
                                Game.b6.setText("X");
                                decideWinner();
                            } else if (Game.player == 2)
                            {
                                Game.player = 1;
                                Game.b6.setText("O");
                                decideWinner();
                            }
                        }
                    }
                });
            }
            if (incomingMessage.equalsIgnoreCase("b7"))
            {
                ///game = new Game();
                Game.b7.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (Game.b7.getText().toString().equalsIgnoreCase(""))
                        {
                            if (Game.player == 1)
                            {
                                Game.player = 2;
                                Game.b7.setText("X");
                                decideWinner();
                            } else if (Game.player == 2)
                            {
                                Game.player = 1;
                                Game.b7.setText("O");
                                decideWinner();
                            }
                        }
                    }
                });
            }
            if (incomingMessage.equalsIgnoreCase("b8"))
            {
                ///game = new Game();
                Game.b8.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (Game.b8.getText().toString().equalsIgnoreCase(""))
                        {
                            if (Game.player == 1)
                            {
                                Game.player = 2;
                                Game.b8.setText("X");
                                decideWinner();
                            } else if (Game.player == 2)
                            {
                                Game.player = 1;
                                Game.b8.setText("O");
                                decideWinner();
                            }
                        }
                    }
                });
            }
            if (incomingMessage.equalsIgnoreCase("b9"))
            {
                ///game = new Game();
                Game.b9.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (Game.b9.getText().toString().equalsIgnoreCase(""))
                        {
                            if (Game.player == 1)
                            {
                                Game.player = 2;
                                Game.b9.setText("X");
                                decideWinner();
                            } else if (Game.player == 2)
                            {
                                Game.player = 1;
                                Game.b9.setText("O");
                                decideWinner();
                            }
                        }
                    }
                });
            }
            if (incomingMessage.equalsIgnoreCase("Again"))
            {
                ///game = new Game();

                Game.emptyButtons();
                Game.draw=0;
            }
        }

        private void decideWinner()
        {
            Game.initializeStrings();


            //////========== For Player 1
            //first row
            if (Game.s1.equals("X") && Game.s2.equals("X") && Game.s3.equals("X"))
            {


                count++;
                Game.draw++;
                Game.emptyButtons();


            }

            //diagonal from s1
            if (Game.s1.equals("X") && Game.s5.equals("X") && Game.s9.equals("X"))
            {
                count++;
                Game.draw++;
                Game.emptyButtons();

            }


            //first column
            if (Game.s1.equals("X") && Game.s4.equals("X") && Game.s7.equals("X"))
            {
                count++;
                Game.draw++;
                Game.emptyButtons();
            }

            //second colomn
            if (Game.s2.equals("X") && Game.s5.equals("X") && Game.s8.equals("X"))
            {
                count++;
                Game.emptyButtons();

            }

            //third column
            if (Game.s3.equals("X") && Game.s6.equals("X") && Game.s9.equals("X"))
            {
                count++;
                Game.draw++;
                Game.emptyButtons();
            }


            //diagonal from s3
            if (Game.s3.equals("X") && Game.s5.equals("X") && Game.s7.equals("X"))
            {
                count++;
                Game.draw++;
                Game.emptyButtons();

            }

            //second row
            if (Game.s4.equals("X") && Game.s5.equals("X") && Game.s6.equals("X"))

            {
                count++;
                Game.draw++;
                Game.emptyButtons();

            }

            //third row
            if (Game.s7.equals("X") && Game.s8.equals("X") && Game.s9.equals("X"))

            {
                count++;
                Game.draw++;
                Game.emptyButtons();

            }


            //////========== For Player 2
            //first row
            if (Game.s1.equals("O") && Game.s2.equals("O") && Game.s3.equals("O"))

            {
                count++;
                Game.draw++;
                Game.emptyButtons();

            }

            //diagonal from s1
            if (Game.s1.equals("O") && Game.s5.equals("O") && Game.s9.equals("O"))

            {
                count++;
                Game.draw++;
                Game.emptyButtons();

            }


            //first column
            if (Game.s1.equals("O") && Game.s4.equals("O") && Game.s7.equals("O"))

            {
                count++;
                Game.draw++;
                Game.emptyButtons();

            }

            //second column
            if (Game.s2.equals("O") && Game.s5.equals("O") && Game.s8.equals("O"))

            {
                count++;
                Game.draw++;
                Game.emptyButtons();

            }

            //third column
            if (Game.s3.equals("O") && Game.s6.equals("O") && Game.s9.equals("O"))

            {
                count++;
                Game.draw++;
                Game.emptyButtons();

            }

            //diagonal from s3
            if (Game.s3.equals("O") && Game.s5.equals("O") && Game.s7.equals("O"))

            {
                count++;
                Game.draw++;
                Game.emptyButtons();

            }

            //second row
            if (Game.s4.equals("O") && Game.s5.equals("O") && Game.s6.equals("O"))

            {
                count++;
                Game.draw++;
                Game.emptyButtons();

            }

            //third row
            if (Game.s7.equals("O") && Game.s8.equals("O") && Game.s9.equals("O"))

            {
                count++;
                Game.draw++;
                Game.emptyButtons();

            }

            if (Game.draw == 9)

            {
                count++;
                Game.emptyButtons();


            }


            //main = new Main();
            //main.startGame();
            Log.d("Naveed", "Starting Activity");
            if (count != 0)

            {
                byte[] bytes = "Again".getBytes(Charset.defaultCharset());
                write(bytes);
            }

        }

        //Call this from the main activity to send data to the remote device
        private void write(byte[] bytes)
        {
            String text = new String(bytes, Charset.defaultCharset());
            Log.d(TAG, "write: Writing to outputstream: " + text);
            try
            {

                mmOutStream.write(bytes);
            } catch (IOException e)
            {
                Log.e(TAG, "write: Error writing to output stream. " + e.getMessage());
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel()
        {
            try
            {
                mmSocket.close();
            } catch (IOException e)
            {
            }
        }

    }


    private void connected(BluetoothSocket mmSocket, BluetoothDevice mmDevice)
    {
        Log.d(TAG, "connected: Starting.");

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();
    }

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     *
     * @param out The bytes to write
     * @see ConnectedThread#write(byte[])
     */
    public void write(byte[] out)
    {
        // Create temporary object

        // Synchronize a copy of the ConnectedThread
        Log.d(TAG, "write: Write Called.");
        //perform the write
        mConnectedThread.write(out);
    }


}
