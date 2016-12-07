//package bin;

import java.util.LinkedList;
import java.awt.Graphics;
import java.util.Random;

public class Handler{
	
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	private GameObject tempObject;

	public void tick(){
		for(int i=0;i<object.size();i++){
			tempObject = object.get(i);
			tempObject.tick(object);			
		}
	}

	public void render(Graphics g){
		for(int i=0;i<object.size();i++){
			tempObject = object.get(i);
			tempObject.render(g);			
		}
	}

	public void addObject(GameObject object){
		this.object.add(object);
	}

	public void removeObject(GameObject object){
		this.object.remove(object);
	}

	public void createLevel(){
		for(int i=0;i<Game.WIDTH;i+=10){
			addObject(new Block(i, Game.HEIGHT-11, 10, 10, ObjectId.Block));
			addObject(new Block(i, 0, 10, 10, ObjectId.Block));
		}
		for(int i=0;i<Game.HEIGHT;i+=10){
			addObject(new Block(0, i, 10, 10, ObjectId.Block));
			addObject(new Block(Game.WIDTH-11, i, 10, 10, ObjectId.Block));
		}
	}

	public void createFood(){
		Random randX = new Random();
		Random randY = new Random();

		int x = randX.nextInt(Game.WIDTH-20)+10;
		int y = randY.nextInt(Game.HEIGHT-20)+10;
		addObject(new Food(x, y, 7, 7, ObjectId.Food));
	}

	public void removeObjectFromList(String username) {
		int index=0;
		for(GameObject g: object){
			if(g.getUsername() != null){
				if(g.getUsername().equalsIgnoreCase(username) ){
					break;
				}
			}
			index++;
		}
		this.object.remove(index);
	}

	public synchronized void movePlayer(String username, int x, int y) {
		int index = getCarMPIndex(username);
		this.object.get(index).setX(x);
		this.object.get(index).setY(y);
	}

	private int getCarMPIndex(String username) {
		int index = 0;
        for (GameObject e : this.object) {
        	if(e.getId()== ObjectId.Car){
	            if (e.getUsername().equals(username)) {
	                break;
	            }
        	}
            index++;
        }
        return index;
	}
}