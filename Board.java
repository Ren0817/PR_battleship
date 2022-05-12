
import java.util.*;

import com.sun.tools.javac.util.StringUtils;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board extends Parent {
    private VBox rows = new VBox();
    public HashMap shipsmap=new HashMap();
    public boolean enemy = false;
    private List shipinfo=new ArrayList();
    public int ships = 5;
    public int added=0;
    private String[] varray=new String[] {"","A","B","C","D","E","F","G","H","I","J"};
    private String[] harray=new String[] {"","0","1","2","3","4","5","6","7","8","9"};

    public Board(boolean enemy, EventHandler<? super MouseEvent> handler) {
        this.enemy = enemy;
        for (int y = 0; y < 11; y++) {
            HBox row = new HBox();

            for (int x = 0; x < 11; x++) {
                if(y==0){
                    Label txt=new Label(harray[x]);
                    Rectangle c=new Rectangle();
                    row.getChildren().add(txt);
                    txt.setPrefSize(22,20);
                }else{
                    if(x==0){
                        Label txt=new Label(varray[y]);
                        txt.setPrefSize(22,20);
                        row.getChildren().add(txt);

                    }else{
                        Cell c = new Cell(x-1, y-1, this);
                        c.setOnMouseClicked(handler);
                        row.getChildren().add(c);
                    }
                }

            }

            rows.getChildren().add(row);
        }

        getChildren().add(rows);
    }

    public List getShipInfo() {
    	return shipinfo;
    }
    public boolean placeShip(Ship ship, int x, int y) {
    	if(added==5) {
    		return false;
    	}
        if (canPlaceShip(ship, x, y)) {
            int length = ship.type;

            if (ship.vertical) {
                for (int i = y; i < y + length; i++) {
                    Cell cell = getCell(x, i);
                    cell.ship = ship;
                    shipinfo.add(cell);
                    if (!enemy) {
                        cell.setFill(Color.WHITE);
                        cell.setStroke(Color.GREEN);
                    }
                }
            }
            else {
                for (int i = x; i < x + length; i++) {
                    Cell cell = getCell(i, y);
                    cell.ship = ship;
                    shipinfo.add(cell);
                    if (!enemy) {
                        cell.setFill(Color.WHITE);
                        cell.setStroke(Color.GREEN);
                    }
                }
            }
            
            added++;

            return true;
        }

        return false;
    }

    public Cell getCell(int x, int y) {
        return (Cell)((HBox)rows.getChildren().get(y+1)).getChildren().get(x+1);
    }

    private Cell[] getNeighbors(int x, int y) {
        Point2D[] points = new Point2D[] {
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1)
        };

        List<Cell> neighbors = new ArrayList<Cell>();

        for (Point2D p : points) {
            if (isValidPoint(p)) {
                neighbors.add(getCell((int)p.getX(), (int)p.getY()));
            }
        }

        return neighbors.toArray(new Cell[0]);
    }

    private boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.type;

        if (ship.vertical) {
            for (int i = y; i < y + length; i++) {
                if (!isValidPoint(x, i))
                    return false;

                Cell cell = getCell(x, i);
                if (cell.ship != null)
                    return false;

                for (Cell neighbor : getNeighbors(x, i)) {
                    if (!isValidPoint(x, i))
                        return false;

                    if (neighbor.ship != null)
                        return false;
                }
            }
        }
        else {
            for (int i = x; i < x + length; i++) {
                if (!isValidPoint(i, y))
                    return false;

                Cell cell = getCell(i, y);
                if (cell.ship != null)
                    return false;

                for (Cell neighbor : getNeighbors(i, y)) {
                    if (!isValidPoint(i, y))
                        return false;

                    if (neighbor.ship != null)
                        return false;
                }
            }
        }

        return true;
    }

    private boolean isValidPoint(Point2D point) {
        return isValidPoint(point.getX(), point.getY());
    }

    private boolean isValidPoint(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    

    public void sycship(List shipInfo) {
    	if(!enemy) {
    		return;
    	}
    	this.shipinfo=shipInfo;
    	for(int i=0;i<shipinfo.size();i++) {
    		Cell cell=(Cell) shipinfo.get(i);
    		  Cell celltarget = getCell(cell.x, cell.y);
    		  celltarget.ship = cell.ship;
    		
    	}
    }

    public  String valideshipsplace(String shipinfo){
        shipsmap=new HashMap();
        HashMap typelength=new HashMap();
        typelength.put("A",5);
        typelength.put("B",5);
        typelength.put("D",3);
        typelength.put("S",3);
        typelength.put("P",2);

        String result="success";
        //ship all location
        List locationset=new ArrayList();
        Set neighbourset=new HashSet();
        HashMap location2startmap=new HashMap();


        if(shipinfo==null||"".equals(shipinfo)){
            result="failure, wrong input formate";
            return  result;
        }

        String[] ships=shipinfo.split(" ");
        if(ships.length!=10){
            result="failure, wrong input formate";
            return  result;
        }
        for(int i=0;i<ships.length;i++){
            String typeinfo=ships[i].trim();
            String locainfo=ships[i+1].trim();
            i++;
            //check ship type
            if("ABDSP".indexOf(typeinfo)<0){
                result="Invalid ship type---" + typeinfo;
                return result;
            }
            //check location info
            if(locainfo.length()==3
                    &&"ABCDEFGHIJ".indexOf(locainfo.substring(0,1))>=0
                    &&"0123456789".indexOf(locainfo.substring(1,2))>=0
                    &&"hv".indexOf(locainfo.substring(2,3))>=0){
                ShipLocation sc=new ShipLocation();
                sc.setType(typeinfo);
                sc.setLength((int)typelength.get(typeinfo));
                sc.setIsvertical(locainfo.substring(2,3).equals("v")?true:false);
                String xstr=locainfo.substring(0,1);
                for(int k=1;k<varray.length;k++){
                    if(varray[k].equals(xstr)){
                        sc.setY(k-1);
                        break;
                    }
                }
                sc.setX(Integer.valueOf(locainfo.substring(1,2)));
                shipsmap.put(typeinfo,sc);
                if(!sc.isIsvertical()){//X NOT CHANGE, Y CHANGE
                    String ytmp=String.valueOf(sc.getY());
                    for(int h=0;h<sc.getLength();h++){
                        if((sc.getX()+h)>10){
                            result="Invalid location,beyond board--"+typeinfo+" "+locainfo;
                            return result;
                        }
                        String tmpl=String.valueOf(sc.getX()+h)+ytmp;

                        if(locationset.contains(tmpl)){
                            result="Invalid location,overlap-("+typeinfo+" "+locainfo+")vs("+location2startmap.get(tmpl)+")";
                            return result;
                        }else{

                            locationset.add(tmpl);
                            location2startmap.put(tmpl,typeinfo+" "+locainfo);
                        }
                        //left
                        if(h==0&&sc.getX()-1>=0){
                            String ntmp=String.valueOf(sc.getX()-1)+String.valueOf(sc.getY());
                            if(!locationset.contains(ntmp)){
                                neighbourset.add(ntmp);
                            }else{
                                result="Invalid location,too close-("+typeinfo+" "+locainfo+")vs("+location2startmap.get(ntmp)+")";
                                return result;
                            }
                        }
                        //right
                        if(h==sc.getLength()-1&&sc.getX()+h+1<=10){
                            String ntmp=String.valueOf(sc.getX()+h+1)+String.valueOf(sc.getY());
                            if(!locationset.contains(ntmp)){
                                neighbourset.add(ntmp);
                            }else{
                                result="Invalid location,too close--("+typeinfo+" "+locainfo+")vs("+location2startmap.get(ntmp)+")";
                                return result;
                            }
                        }
                        //up
                        if(sc.getY()-1>=0){
                            String ntmp=String.valueOf(sc.getX()+h)+String.valueOf(sc.getY()-1);
                            if(!locationset.contains(ntmp)){
                                neighbourset.add(ntmp);
                            }else{
                                result="Invalid location,too close-("+typeinfo+" "+locainfo+")vs("+location2startmap.get(ntmp)+")";
                                return result;
                            }
                        }

                        //down
                        if(sc.getY()+1<=10){
                            String ntmp=String.valueOf(sc.getX()+h)+String.valueOf(sc.getY()+1);
                            if(!locationset.contains(ntmp)){
                                neighbourset.add(ntmp);
                            }else{
                                result="Invalid location,too close--("+typeinfo+" "+locainfo+")vs("+location2startmap.get(ntmp)+")";
                                return result;
                            }
                        }

                    }
                }else{

                    String xtmp=String.valueOf(sc.getX());
                    for(int h=0;h<sc.getLength();h++){
                        if((sc.getY()+h)>10){
                            result="Invalid location,beyond board-"+typeinfo+" "+locainfo;
                            return result;
                        }
                        String tmpl=xtmp+String.valueOf(sc.getY()+h);

                        if(locationset.contains(tmpl)){
                            result="Invalid location,overlap-("+typeinfo+" "+locainfo+")vs("+location2startmap.get(tmpl)+")";
                            return result;
                        }else{
                            locationset.add(tmpl);
                            location2startmap.put(tmpl,typeinfo+" "+locainfo);
                        }


                        //UP
                        if(h==0&&sc.getY()-1>=0){
                            String ntmp=String.valueOf(sc.getX())+String.valueOf(sc.getY()-1);
                            if(!locationset.contains(ntmp)){
                                neighbourset.add(ntmp);
                            }else{
                                result="Invalid location,too close-("+typeinfo+" "+locainfo+")vs("+location2startmap.get(ntmp)+")";
                                return result;
                            }
                        }
                        //DOWN
                        if(h==sc.getLength()-1&&sc.getY()+h+1<=10){
                            String ntmp=String.valueOf(sc.getX())+String.valueOf(sc.getY()+h+1);
                            if(!locationset.contains(ntmp)){
                                neighbourset.add(ntmp);
                            }else{
                                result="Invalid location,too close-("+typeinfo+" "+locainfo+")vs("+location2startmap.get(ntmp)+")";
                                return result;
                            }
                        }
                        //LEFT
                        if(sc.getX()-1>=0){
                            String ntmp=String.valueOf(sc.getX()-1)+String.valueOf(sc.getY()+h);
                            if(!locationset.contains(ntmp)){
                                neighbourset.add(ntmp);
                            }else{
                                result="Invalid location,too close-("+typeinfo+" "+locainfo+")vs("+location2startmap.get(ntmp)+")";
                                return result;
                            }
                        }

                        //down
                        if(sc.getX()+1<=10){
                            String ntmp=String.valueOf(sc.getX()+1)+String.valueOf(sc.getY()+h);
                            if(!locationset.contains(ntmp)){
                                neighbourset.add(ntmp);
                            }else{
                                result="Invalid location,too close-("+typeinfo+" "+locainfo+")vs("+location2startmap.get(ntmp)+")";
                                return result;
                            }
                        }


                    }


                }

            }else{
                result="Invalid location formate-"+locainfo;
                return result;
            }
        }



        return result;
    }
    
   
}