//
//import java.util.ArrayList;
//import java.util.List;
//
//import javafx.event.EventHandler;
//import javafx.geometry.Point2D;
//import javafx.scene.Parent;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//
//public class Board2 extends Parent {
//    private VBox rows = new VBox();
//    public boolean enemy = false;
//    private List shipinfo=new ArrayList();
//    private String[] varray=new String[] {"A","B","C","D","E","F","G","H","I","J"};
//    private String[] harray=new String[] {"0","1","2","3","4","5","6","7","8","9"};
//    public int ships = 5;
//
//    public Board2(boolean enemy, EventHandler<? super MouseEvent> handler) {
//        this.enemy = enemy;
//        for (int y = 0; y < 10; y++) {
//            HBox row = new HBox();
//            for (int x = 0; x < 10; x++) {
//                Cell c = new Cell(x, y, this);
//                c.setOnMouseClicked(handler);
//                row.getChildren().add(c);
//            }
//
//            rows.getChildren().add(row);
//        }
//        
//        
//        
//        
////        for (int y = 0; y < 11; y++) {
////            HBox row = new HBox();
////            for (int x = 0; x < 11; x++) {
////                Cell c = new Cell(x, y, this);
////                if(y==0) {
////                	if(x!=0) {
////                		StackPane pane = new StackPane();
////                		Label text = new Label(varray[x-1]);
////                		pane.getChildren().addAll(c,text);
////                		 row.getChildren().add(pane);
////                	}else {
////                		 row.getChildren().add(c);
////                	}
////                }else {
////                	if(x==0) {
////                		
////                    		StackPane pane = new StackPane();
////                    		Label text = new Label(harray[y-1]);
////                    		pane.getChildren().addAll(c,text);
////                    		 row.getChildren().add(pane);
////                	}else {
////                		c.setOnMouseClicked(handler);
////                		 row.getChildren().add(c);
////                	}
////                	
////                }
////                
////               
////            }
////
////            rows.getChildren().add(row);
////        }
//        
//        
//
//        getChildren().add(rows);
//    }
//
//    public List getShipInfo() {
//    	return shipinfo;
//    }
//    public boolean placeShip(Ship ship, int x, int y) {
//        if (canPlaceShip(ship, x, y)) {
//            int length = ship.type;
//
//            if (ship.vertical) {
//                for (int i = y; i < y + length; i++) {
//                    Cell cell = getCell(x, i);
//                    cell.ship = ship;
//                    shipinfo.add(cell);
//                    if (!enemy) {
//                        cell.setFill(Color.WHITE);
//                        cell.setStroke(Color.GREEN);
//                    }
//                }
//            }
//            else {
//                for (int i = x; i < x + length; i++) {
//                    Cell cell = getCell(i, y);
//                    cell.ship = ship;
//                    shipinfo.add(cell);
//                    if (!enemy) {
//                        cell.setFill(Color.WHITE);
//                        cell.setStroke(Color.GREEN);
//                    }
//                }
//            }
//
//            return true;
//        }
//
//        return false;
//    }
//
//    public Cell getCell(int x, int y) {
//        return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);
//    }
//
//    private Cell[] getNeighbors(int x, int y) {
//        Point2D[] points = new Point2D[] {
//                new Point2D(x - 1, y),
//                new Point2D(x + 1, y),
//                new Point2D(x, y - 1),
//                new Point2D(x, y + 1)
//        };
//
//        List<Cell> neighbors = new ArrayList<Cell>();
//
//        for (Point2D p : points) {
//            if (isValidPoint(p)) {
//                neighbors.add(getCell((int)p.getX(), (int)p.getY()));
//            }
//        }
//
//        return neighbors.toArray(new Cell[0]);
//    }
//
//    private boolean canPlaceShip(Ship ship, int x, int y) {
//        int length = ship.type;
//
//        if (ship.vertical) {
//            for (int i = y; i < y + length; i++) {
//                if (!isValidPoint(x, i))
//                    return false;
//
//                Cell cell = getCell(x, i);
//                if (cell.ship != null)
//                    return false;
//
//                for (Cell neighbor : getNeighbors(x, i)) {
//                    if (!isValidPoint(x, i))
//                        return false;
//
//                    if (neighbor.ship != null)
//                        return false;
//                }
//            }
//        }
//        else {
//            for (int i = x; i < x + length; i++) {
//                if (!isValidPoint(i, y))
//                    return false;
//
//                Cell cell = getCell(i, y);
//                if (cell.ship != null)
//                    return false;
//
//                for (Cell neighbor : getNeighbors(i, y)) {
//                    if (!isValidPoint(i, y))
//                        return false;
//
//                    if (neighbor.ship != null)
//                        return false;
//                }
//            }
//        }
//
//        return true;
//    }
//
//    private boolean isValidPoint(Point2D point) {
//        return isValidPoint(point.getX(), point.getY());
//    }
//
//    private boolean isValidPoint(double x, double y) {
//        return x >= 0 && x < 10 && y >= 0 && y < 10;
//    }
//
//    
//    //放置ship，只在playerboard，所以enemy的board需要用同步的方式方式。
//    public void sycship(List shipInfo) {
//    	if(!enemy) {
//    		return;
//    	}
//    	this.shipinfo=shipInfo;
//    	for(int i=0;i<shipinfo.size();i++) {
//    		Cell cell=(Cell) shipinfo.get(i);
//    		  Cell celltarget = getCell(cell.x, cell.y);
//    		  celltarget.ship = cell.ship;
//    		
//    	}
//    }
//    
//   
//}