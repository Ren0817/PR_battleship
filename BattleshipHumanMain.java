

import java.awt.*;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JTable;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class BattleshipHumanMain extends Application {
	
	 private boolean done = false;

    private boolean running = false;
    private Board player1battleBoard, player1Board,player2battleBoard, player2Board;

    private String player1name="player1";
    private String player2name="player2";
//    private Label my_label=new Label("the statistics info");
    private Label my_label1=new Label("the statistics info");
    private Label my_label2=new Label("the statistics info");
    private GridPane gridinput=new GridPane();
    private GridPane gridinput2=new GridPane();
    private GridPane gridname=new GridPane();
    private GridPane gridbutton=new GridPane();

 
    SwingNode swingNode = new SwingNode();

    private int shipsToPlace1 = 5;
    private int shipsToPlace2 = 5;
    
    private Statistic player1Static=new Statistic();
    private Statistic player2Static=new Statistic();

    private boolean play2Turn = false;

    private Random random = new Random();
    
    private boolean player1ready=false;
    private boolean player2ready=false;
    private VBox vbox2=new VBox();
    private VBox vbox1=new VBox();

    private Parent createContent() {
    	
    	 my_label1.setPrefSize(300, 20);
         my_label1.setFont(Font.font("Time New Roman",12));
        my_label1.setAlignment(Pos.CENTER);
         my_label1.setText(player1name);
         
         my_label2.setPrefSize(300, 20);
         my_label2.setFont(Font.font("Time New Roman",12));
         my_label2.setAlignment(Pos.CENTER);
         my_label2.setText(player2name);
         
        my_label1.setVisible(false);
     	my_label2.setVisible(false);
         
         
        BorderPane root = new BorderPane();
        root.setPrefSize(800, 750);


        
        player1battleBoard = new Board(true, event -> {
            if (play2Turn||!running||done)
                return;

            Cell cell = (Cell) event.getSource();
            if (cell.wasShot) {
            	 return;
            }
               

//            play2Turn = !cell.shoot();
            if(cell.shoot()) {
            	player1Static.increaseHit();
            }else {
            	player1Static.increaseMiss();
            }
//            root.setBottom(statistictable());
            refreshownmap(player2Board,cell);
            play2Turn =true;
            
//        	player1Board.setVisible(false);
//        	player1battleBoard.setVisible(false);
//        	my_label1.setVisible(false);

            if (player1battleBoard.ships == 0) {
                System.out.println("PLAYER1 WIN");
//                my_label.setText("PLAYER1 WIN");
                this.player1Board.setVisible(false);
            	this.player1battleBoard.setVisible(false);
            	this.player2Board.setVisible(false);
            	this.player2battleBoard.setVisible(false);
            	my_label1.setVisible(false);
            	my_label2.setVisible(false);
                vbox1.setVisible(false);
                vbox2.setVisible(false);
//            	 root.setTop(statistictable());
            	 root.setBottom(statistictable());
            	 done=true;
//                System.exit(0);
            }

            if (play2Turn) {
//              enemyMove();
            }

        });

        player1Board = new Board(false, event -> {
           Cell cell= (Cell) event.getSource();
//            player1placeshiphandler( cell.x,cell.y,event.getButton() == MouseButton.PRIMARY);
        });


        gridinput.setAlignment(Pos.CENTER);
        gridinput.setHgap(5);


        javafx.scene.control.TextField textfield=new javafx.scene.control.TextField();
        textfield.setPrefWidth(200);
        gridinput.add(textfield,0,0);
        Button shipplacebutton = new Button("place ");
        Label errortipop1=new Label("");
        shipplacebutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String inputinfo=textfield.getText();
                String result=player1Board.valideshipsplace(inputinfo);
                errortipop1.setText(result);
                System.out.println(result);
                if("success".equals(result)){
                    Iterator it=player1Board.shipsmap.keySet().iterator();
                    while(it.hasNext()){
                        String type=it.next().toString();
                        ShipLocation sc= (ShipLocation) player1Board.shipsmap.get(type);
                        player1placeshipInputhandler( sc);
                    }
                }

            }
        });
        gridinput.add(shipplacebutton,1,0);
        gridinput.add(errortipop1,0,1);

        VBox vbox1 = new VBox(15, player1battleBoard, player1Board,my_label1,gridinput);
       
        vbox1.setAlignment(Pos.CENTER);
        vbox1.setPrefSize(360, 500);
//        vbox1.setVisible(false);
        root.setLeft(vbox1);

        player2battleBoard = new Board(true, event -> {
            if (!play2Turn||!running||done)
                return;

            Cell cell = (Cell) event.getSource();
            if (cell.wasShot)
                return;

//            if(!cell.shoot()) {
//            	
//            }
            if(cell.shoot()) {
            	player2Static.increaseHit();
            }else {
            	player2Static.increaseMiss();
            }
            
            refreshownmap(player1Board,cell);
           
//            root.setBottom(statistictable());
//        	player2Board.setVisible(false);
//        	player2battleBoard.setVisible(false);
//        	my_label2.setVisible(false);
         
            play2Turn = false ;

            if (player2battleBoard.ships == 0) {
                System.out.println("PLAYER2 WIN");
                done=true;
//                System.exit(0);
//                my_label.setText("PLAYER2 WIN");
                this.player1Board.setVisible(false);
            	this.player1battleBoard.setVisible(false);
            	this.player2Board.setVisible(false);
            	this.player2battleBoard.setVisible(false);
            	my_label1.setVisible(false);
            	my_label2.setVisible(false);
                vbox1.setVisible(false);
                vbox2.setVisible(false);
                root.setBottom(statistictable());
                
            }


        });

        player2Board = new Board(false, event -> {
            Cell cell=(Cell)event.getSource();
            //cancel mouse action, enable input
//            player2placeshiphandler( cell.x,cell.y,event.getButton() == MouseButton.PRIMARY);
        });



        gridinput2.setAlignment(Pos.CENTER);
        gridinput2.setHgap(5);
//        Label shipplace2=new Label("ship location");
//        gridinput2.add(shipplace2,0,0);
        javafx.scene.control.TextField textfield2=new javafx.scene.control.TextField();
        textfield2.setPrefWidth(200);
        gridinput2.add(textfield2,0,0);
        Label errortipop2=new Label("");
        Button shipplacebutton2 = new Button("place ");
        shipplacebutton2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String inputinfo=textfield2.getText();
                 String result=player2Board.valideshipsplace(inputinfo);
                errortipop2.setText(result);
                System.out.println(result);
                if("success".equals(result)){
                    Iterator it=player2Board.shipsmap.keySet().iterator();
                    while(it.hasNext()){
                        String type=it.next().toString();
                        ShipLocation sc= (ShipLocation) player2Board.shipsmap.get(type);
                        player2placeshipInputhandler( sc);
                    }

                }


            }
        });
        gridinput2.add(shipplacebutton2,1,0);
        gridinput2.add(errortipop2,0,1);

        VBox vbox2 = new VBox(15, player2battleBoard, player2Board,my_label2,gridinput2);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.setPrefSize(360, 500);
        vbox2.setVisible(false);
        root.setRight(vbox2);
//        vbox2.setVisible(false);

        this.player1Board.setVisible(false);
    	this.player1battleBoard.setVisible(false);
    	this.player2Board.setVisible(false);
    	this.player2battleBoard.setVisible(false);
        vbox1.setVisible(false);
        vbox2.setVisible(false);
        
        Button button1 = new Button("player1 area show ");
        button1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				player1Board.setVisible(true);
	        	player1battleBoard.setVisible(true);
	        	my_label1.setVisible(true);
                vbox1.setVisible(true);

            	
				
			}
		});
     
        
   
        Button button2 = new Button("player1 area hide ");
        button2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				player1Board.setVisible(false);
	        	player1battleBoard.setVisible(false);
	        	my_label1.setVisible(false);
                vbox1.setVisible(false);

				
			}
		});
        
        
        Button button3 = new Button("player2 area show ");
        button3.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				player2Board.setVisible(true);
	        	player2battleBoard.setVisible(true);
            	my_label2.setVisible(true);

                vbox2.setVisible(true);
				
			}
		});
        
        Button button4 = new Button("player2 area hide ");
        button4.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				player2Board.setVisible(false);
	        	player2battleBoard.setVisible(false);
            	my_label2.setVisible(false);

                vbox2.setVisible(false);
				
			}
		});
       
//        my_label.setPrefSize(200, 20);
//        my_label.setFont(Font.font("Time New Roman",12));
//        my_label.setText(" start from player1");
        HBox boxtop = new HBox(10,gridname, gridbutton);
        Label namelabel1=new Label("player1:");
        Label namelabel2=new Label("player2:");
        javafx.scene.control.TextField nameinput1=new javafx.scene.control.TextField();
        nameinput1.setPrefWidth(210);
        javafx.scene.control.TextField nameinput2=new javafx.scene.control.TextField();
        nameinput2.setPrefWidth(210);
        nameinput2.setAlignment(Pos.CENTER);
        nameinput1.setAlignment(Pos.CENTER);
        gridname.setHgap(10); gridname.setVgap(6);
        gridname.setAlignment(Pos.CENTER);
        gridname.add(namelabel1,0,0);
        gridname.add(namelabel2,0,1);
        gridname.add(nameinput1,1,0);
        gridname.add(nameinput2,1,1);
        Button namebutton = new Button("start");
        gridname.add(namebutton,0,2);
        Label nametiplabel=new Label("");
        gridname.add(nametiplabel,1,2);
        namebutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String player1=nameinput1.getText();
                String player2=nameinput2.getText();
                if("".equals(player1)||"".equals(player2)||player1==null||player2==null){
                    nametiplabel.setText("player name is required");
                }else{
                    if(player1.length()>25||player2.length()>25){
                        nametiplabel.setText("the length of name must be limited to 25");
                    }else{
                        player1name=player1;
                        player2name=player2;
                        gridname.setVisible(false);
                        gridname.setPrefWidth(5);
                        boxtop.getChildren().remove(0);
                        gridbutton.setVisible(true);
                        button1.setText(player1+" display");
                        button2.setText(player1+" hide");
                        button3.setText(player2+" display");
                        button4.setText(player2+" hide");
                        button1.setPrefWidth(160);
                        button2.setPrefWidth(160);
                        button3.setPrefWidth(160);
                        button4.setPrefWidth(160);
                        my_label1.setText(player1);
                        my_label2.setText(player2);

                    }
                }
            }
        });

        gridbutton.setHgap(10);

        gridbutton.add(button1,0,0);
        gridbutton.add(button2,1,0);
        gridbutton.add(button3,2,0);
        gridbutton.add(button4,3,0);
        gridbutton.setVisible(false);


       Insets rotpadding=new Insets(6);
        root.setPadding(rotpadding);
        root.setTop(boxtop);







//        root.setBottom(statistictable());
     
        return root;
    }

    private void player2placeshiphandler(int x, int y,boolean vertical) {
        if (running)
            return;

        if (player2Board.placeShip(new Ship(shipsToPlace2, vertical), x, y)) {
            if (--shipsToPlace2 == 0) {
                player2ready=true;
                if(player1ready&&player2ready) {
                    startGame();
                }
            }
        }
    }

    private void player2placeshipInputhandler(ShipLocation sc) {
        if (running)
            return;

        if (player2Board.placeShip(new Ship(sc.getType(), sc.isIsvertical()),sc.getX(), sc.getY())) {
            if (--shipsToPlace2 == 0) {
                player2ready=true;
                gridinput2.setVisible(false);
                my_label2.setText(player2name+", placeships done");
                if(player1ready&&player2ready) {
                    startGame();
                }
            }
        }
    }

    private void player1placeshiphandler(int x, int y,boolean vertical) {
        if (running)
            return;
        if (player1Board.placeShip(new Ship(shipsToPlace1, vertical), x, y)) {
            shipsToPlace1--;
            if (shipsToPlace1 == 0) {
                player1ready=true;
                if(player1ready&&player2ready) {
                    startGame();
                }

            }
        }
    }

    private void player1placeshipInputhandler(ShipLocation sc) {
        if (running)
            return;

        if (player1Board.placeShip(new Ship(sc.getType(), sc.isIsvertical()),sc.getX(), sc.getY())) {
            if (--shipsToPlace1 == 0) {
                player1ready=true;
                gridinput.setVisible(false);
                my_label1.setText(player1name+", placeships done");
                if(player1ready&&player2ready) {
                    startGame();
                }
            }
        }
    }


    private void startGame() {


    	player1battleBoard.sycship(player2Board.getShipInfo());
    	player2battleBoard.sycship(player1Board.getShipInfo());
        gridinput2.setVisible(false);
        gridinput.setVisible(false);
        my_label1.setText(player1name);
        my_label2.setText(player2name);
        running = true;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    

    
    public SwingNode statistictable() {
    	 swingNode = new SwingNode();
        JPanel panel = new JPanel(new BorderLayout());

        Object[] columnNames = {"player","shot", "miss", "hit", "miss rate", "hit rate"};

        Object[][] rowData = {
                {player1name, player1Static.getShot(), player1Static.getMiss(), player1Static.getHit(), player1Static.getMissRate(),player1Static.getHitRate()},
                {player2name, player1Static.getShot(), player2Static.getMiss(), player2Static.getHit(), player2Static.getMissRate(),player2Static.getHitRate()},
        };
        
        JTable table = new JTable(rowData, columnNames);
       table.getTableHeader().setBackground(Color.gray);
        table.setRowHeight(25);
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        panel.add(table, BorderLayout.CENTER);
//        panel.add(new java.awt.Label(""), BorderLayout.SOUTH);
        panel.setSize(800,180);
        swingNode.setContent(panel);

      
        return swingNode;
    }
    
    public void refreshownmap(Board board, Cell opcell) {
    	Cell c=board.getCell(opcell.x, opcell.y);
    	c.justUpdateColorForOWN();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
