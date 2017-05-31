package com.example.naveed.tictactoebluetoothmultiplayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Game extends Activity
{
    Button b1,b2,b3,b4,b5, b6, b7, b8, b9;
    String s1,s2,s3,s4,s5,s6,s7,s8,s9;
    int player;
    int draw = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        player = 1;
        creatingButtons();
    }


    protected void b1Function(View v)
    {
        draw++;
        if(b1.getText().toString().equalsIgnoreCase(""))
        {
            if (player == 1)
            {
                player = 2;
                b1.setText("X");
                decideWinner();
            } else if (player == 2)
            {
                player = 1;
                b1.setText("O");
                decideWinner();
            }
        }
    }

    protected void b2Function(View v)
    {
        draw++;
        if(b2.getText().toString().equalsIgnoreCase(""))
        {
            if(player == 1)
            {
                player = 2;
                b2.setText("X");
                decideWinner();
            }
            else if(player == 2)
            {
                player = 1;
                b2.setText("O");
                decideWinner();
            }
        }
    }

    protected void b3Function(View v)
    {
        draw++;
        if(b3.getText().toString().equalsIgnoreCase(""))
        {
            if(player == 1)
            {
                player = 2;
                b3.setText("X");
                decideWinner();
            }
            else if(player == 2)
            {
                player = 1;
                b3.setText("O");
                decideWinner();
            }
        }
    }

    protected void b4Function(View v)
    {
        draw++;
        if(b4.getText().toString().equalsIgnoreCase(""))
        {
            if(player == 1)
            {
                player = 2;
                b4.setText("X");
                decideWinner();
            }
            else if(player == 2)
            {
                player = 1;
                b4.setText("O");
                decideWinner();
            }
        }
    }

    protected void b5Function(View v)
    {
        draw++;
        if(b5.getText().toString().equalsIgnoreCase(""))
        {
            if(player == 1)
            {
                player = 2;
                b5.setText("X");
                decideWinner();
            }
            else if(player == 2)
            {
                player = 1;
                b5.setText("O");
                decideWinner();
            }
        }
    }

    protected void b6Function(View v)
    {
        draw++;
        if(b6.getText().toString().equalsIgnoreCase(""))
        {
            if(player == 1)
            {
                player = 2;
                b6.setText("X");
                decideWinner();
            }
            else if(player == 2)
            {
                player = 1;
                b6.setText("O");
                decideWinner();
            }
        }
    }

    protected void b7Function(View v)
    {
        draw++;
        if(b7.getText().toString().equalsIgnoreCase(""))
        {
            if(player == 1)
            {
                player = 2;
                b7.setText("X");
                decideWinner();
            }
            else if(player == 2)
            {
                player = 1;
                b7.setText("O");
                decideWinner();
            }
        }
    }

    protected void b8Function(View v)
    {
        draw++;
        if(b8.getText().toString().equalsIgnoreCase(""))
        {
            if(player == 1)
            {
                player = 2;
                b8.setText("X");
                decideWinner();
            }
            else if(player == 2)
            {
                player = 1;
                b8.setText("O");
                decideWinner();
            }
        }
    }

    protected void b9Function(View v)
    {
        draw++;
        if(b9.getText().toString().equalsIgnoreCase(""))
        {
            if(player == 1)
            {
                player = 2;
                b9.setText("X");
                decideWinner();
            }
            else if(player == 2)
            {
                player = 1;
                b9.setText("O");
                decideWinner();
            }
        }
    }


    public void decideWinner()
    {

        initializeStrings();


        //////========== For Player 1
        //first row
        if(s1.equals("X") && s2.equals("X") && s3.equals("X"))
        {
            draw++;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Winner...");
            builder.setMessage("Player 1 is winner");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        //diagonal from s1
        if(s1.equals("X") && s5.equals("X") && s9.equals("X"))
        {
            draw++;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Winner...");
            builder.setMessage("Player 1 is winner");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


        //first column
        if(s1.equals("X") && s4.equals("X") && s7.equals("X"))
        {
            draw++;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Winner...");
            builder.setMessage("Player 1 is winner");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        //second colomn
        if(s2.equals("X") && s5.equals("X") && s8.equals("X"))
        {
            draw++;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Winner...");
            builder.setMessage("Player 1 is winner");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        //third column
        if(s3.equals("X") && s6.equals("X") && s9.equals("X"))
        {
            draw++;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Winner...");
            builder.setMessage("Player 1 is winner");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        //diagonal from s3
        if(s3.equals("X") && s5.equals("X") && s7.equals("X"))
        {
            draw++;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Winner...");
            builder.setMessage("Player 1 is winner");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        //second row
        if(s4.equals("X") && s5.equals("X") && s6.equals("X"))
        {
            draw++;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Winner...");
            builder.setMessage("Player 1 is winner");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        //third row
        if(s7.equals("X") && s8.equals("X") && s9.equals("X"))
        {
            draw++;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Winner...");
            builder.setMessage("Player 1 is winner");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }




        //////========== For Player 2
        //first row
        if(s1.equals("O") && s2.equals("O") && s3.equals("O"))
        {
            draw++;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Winner...");
            builder.setMessage("Player 2 is winner");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        //diagonal from s1
        if(s1.equals("O") && s5.equals("O") && s9.equals("O"))
        {
            draw++;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Winner...");
            builder.setMessage("Player 2 is winner");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


        //first column
        if(s1.equals("O") && s4.equals("O") && s7.equals("O"))
        {
            draw++;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Winner...");
            builder.setMessage("Player 2 is winner");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        //second column
        if(s2.equals("O") && s5.equals("O") && s8.equals("O"))
        {
            draw++;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Winner...");
            builder.setMessage("Player 2 is winner");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        //third column
        if(s3.equals("O") && s6.equals("O") && s9.equals("O"))
        {
            draw++;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Winner...");
            builder.setMessage("Player 2 is winner");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        //diagonal from s3
        if(s3.equals("O") && s5.equals("O") && s7.equals("O"))
        {
            draw++;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Winner...");
            builder.setMessage("Player 2 is winner");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        //second row
        if(s4.equals("O") && s5.equals("O") && s6.equals("O"))
        {
            draw++;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Winner...");
            builder.setMessage("Player 2 is winner");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        //third row
        if(s7.equals("O") && s8.equals("O") && s9.equals("O"))
        {
            draw++;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Winner...");
            builder.setMessage("Player 2 is winner");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        if(draw == 9)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Draw Game...");
            builder.setMessage("Game is Draw");
            builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogInterface,int id)
                {
                    emptyButtons();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    protected void initializeStrings()
    {
        s1 = b1.getText().toString();
        s2 = b2.getText().toString();
        s3 = b3.getText().toString();
        s4 = b4.getText().toString();
        s5 = b5.getText().toString();
        s6 = b6.getText().toString();
        s7 = b7.getText().toString();
        s8 = b8.getText().toString();
        s9 = b9.getText().toString();
    }

    protected void emptyButtons()
    {
        b1.setText("");
        b2.setText("");
        b3.setText("");
        b4.setText("");
        b5.setText("");
        b6.setText("");
        b7.setText("");
        b8.setText("");
        b9.setText("");
        player=1;

    }
    protected void creatingButtons()
    {
        b1 = (Button)findViewById(R.id.b1);
        b2 = (Button)findViewById(R.id.b2);
        b3 = (Button)findViewById(R.id.b3);
        b4 = (Button)findViewById(R.id.b4);
        b5 = (Button)findViewById(R.id.b5);
        b6 = (Button)findViewById(R.id.b6);
        b7 = (Button)findViewById(R.id.b7);
        b8 = (Button)findViewById(R.id.b8);
        b9 = (Button)findViewById(R.id.b9);

    }
}
