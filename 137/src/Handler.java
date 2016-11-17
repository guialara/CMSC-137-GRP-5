package bin;

import java.util.LinkedList;
import java.awt.Graphics;

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
}