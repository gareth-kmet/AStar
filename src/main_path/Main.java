package main_path;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import astar.AStar;
import astar.AStarHeuristic;
import astar.AStarQuerier;
import astar.MapLocation;
import perlin.PerlinNoise;
import perlin.PerlinNoise.PerlinReturn;

class Main {
	static final int CHUNK_SIZE=1, PIXEL_SIZE=0x200, MULTI=2;
    static PerlinReturn[][] pixs = new PerlinReturn[CHUNK_SIZE][CHUNK_SIZE];
    static PerlinReturn[][] pixs2 = new PerlinReturn[CHUNK_SIZE][CHUNK_SIZE];
    static PerlinReturn[][] pixs3 = new PerlinReturn[CHUNK_SIZE][CHUNK_SIZE];
    static Frame f;
    static PerlinNoise p = new PerlinNoise(PIXEL_SIZE);
    static MapLocation[][] paths;
    static final Color[] colors = {Color.red, Color.blue, Color.pink,Color.green, Color.orange, Color.cyan, Color.magenta};
    static float max,min;
    static final float B = 0.4f;
    
    static int octaves = 3;
 
    static public void main(String[] args){
    	paths = new MapLocation[7][];
    	max = Float.NEGATIVE_INFINITY;
 	    min = Float.POSITIVE_INFINITY;
    	p.setOctaves(3, 4, 2f);
    	Main.perl(pixs);
    	Main.star();
    	
    	
    	
    	
        f = new Frame( "paint Example" );
        f.add("Center", new MainCanvas());
        f.setSize(new Dimension(CHUNK_SIZE*PIXEL_SIZE*Main.MULTI+5,CHUNK_SIZE*PIXEL_SIZE*Main.MULTI+40));
        f.setVisible(true);
        
        f.addWindowListener (new WindowAdapter() {    
            @Override
			public void windowClosing (WindowEvent e) {    
                f.dispose();    
            }    
        }); 
        
   }
    
   static void star() {
	   	MapLocation start = new MapLocation(0,0);
	   	MapLocation end = new MapLocation(0x1ff,0x1ff);
	   	AStarQuerier q = new AStarQuerier() {@Override public Answer query(Question question) {
	   		if(question.to().loc.x()<0 || question.to().loc.x()>=PIXEL_SIZE || question.to().loc.y()<0 || question.to().loc.y()>=PIXEL_SIZE)
	   			return new Answer(false, 0);
   			//System.out.println(question.to().chunkLoc+" "+question.from().chunkLoc);
	   		float f = pixs[0][0].getNormalizedValue(question.to().loc.x(),question.to().loc.y(),max,min);
	   		if(f<B) {
	   			return new Answer(false,0);
	   		}else {
	   			return new Answer(true, 2-f*2);
	   		}
   		}};
   		long time = System.currentTimeMillis();
   		paths[0] = new AStar(q).run(start, end).path();
    	long t = System.currentTimeMillis()-time;
    	System.out.println(t);
    	time = System.currentTimeMillis();
		paths[1] = new AStar(q,new AStarHeuristic.DynamicWeighting(0.5f)).run(start, end).path();
		t = System.currentTimeMillis()-time;
    	System.out.println(t);
    	time = System.currentTimeMillis();
		paths[2] = new AStar(q,new AStarHeuristic.DynamicWeighting(0.75f)).run(start, end).path();
		t = System.currentTimeMillis()-time;
    	System.out.println(t);
    	time = System.currentTimeMillis();
		paths[3] = new AStar(q,new AStarHeuristic.DynamicWeighting(1)).run(start, end).path();
		t = System.currentTimeMillis()-time;
    	System.out.println(t);
    	time = System.currentTimeMillis();
		paths[4] = new AStar(q,new AStarHeuristic.DynamicWeighting(2)).run(start, end).path();
		t = System.currentTimeMillis()-time;
    	System.out.println(t);
    	time = System.currentTimeMillis();
		paths[5] = new AStar(q,new AStarHeuristic.DynamicWeighting(5)).run(start, end).path();
		t = System.currentTimeMillis()-time;
    	System.out.println(t);
    	time = System.currentTimeMillis();
		paths[6] = new AStar(q,new AStarHeuristic.DynamicWeighting(10)).run(start, end).path();
		t = System.currentTimeMillis()-time;
    	System.out.println(t);
   }
    
   static void perl(PerlinReturn[][] px) {
	   int seed = 984953545;//new Random().nextInt();
	   for(int x=0; x<CHUNK_SIZE; x++) {for(int y=0; y<CHUNK_SIZE; y++) {
		   PerlinReturn pr = p.perlin(seed, x, y);
		   max = Math.max(pr.max(), max);
		   min = Math.min(pr.min(), min);
	       px[x][y] = pr;
	   }}
	   
   }
}
class MainCanvas extends Canvas
{
    @Override
	public void paint(Graphics g)
    {	
    	for(int cx=0; cx<Main.CHUNK_SIZE;cx++) {for(int cy=0;cy<Main.CHUNK_SIZE;cy++) {
    		for(int px=0; px<Main.PIXEL_SIZE; px++) {for(int py=0; py<Main.PIXEL_SIZE; py++) {
    			int x=Main.PIXEL_SIZE*cx+px;
    			int y=Main.PIXEL_SIZE*cy+py;
    			
    			float f = Main.pixs[cx][cy].getNormalizedValue(px,py, Main.max, Main.min);
    					//* Main.pixs2[cx][cy].getNormalizedValue(px,py, Main.max, Main.min)
    					//* Main.pixs3[cx][cy].getNormalizedValue(px,py, Main.max, Main.min)*2;
    			if(f<Main.B) {
    				f=0;
    			}
    			
    			//g.setColor(new Color((float)x/(Main.PIXEL_SIZE*Main.CHUNK_SIZE), (float)y/(Main.PIXEL_SIZE*Main.CHUNK_SIZE),1));
    			g.setColor(new Color(f,f,f));
    			
    			
    			
    			g.fillRect(x*Main.MULTI, y*Main.MULTI, 1*Main.MULTI, 1*Main.MULTI);
    		}}
    	}}
    	
    	for(int i=0; i<Main.paths.length; i++){
    		MapLocation[] path = Main.paths[i];
    		Color c = Main.colors[i];
    		g.setColor(c);
	    	for(MapLocation m : path) {
	    		int x=m.loc.x();
				int y=m.loc.y();
				g.fillRect(x*Main.MULTI, y*Main.MULTI, 1*Main.MULTI, 1*Main.MULTI);
			}
    	}
    }
}