package mainPack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Universe extends JPanel{
	static final double G = 6.674e-11;
	static final double M = 1.988e30;
	static final double R = 6.957e8;
	static final double c = 2.99792458e8;
	static final double AU = 149597871e3;
	static final double STEFAN_BOLTZMANN = 5.67e-8;
	static final double CHANDRASEKHAR_MASS = 1.4*M; 
	ArrayList<Body>bodies = new ArrayList<Body>();
	ArrayList<Star>stars = new ArrayList<Star>();
	ArrayList<Planet>planets = new ArrayList<Planet>();
	ArrayList<Satellite>satellites = new ArrayList<Satellite>();
	ArrayList<Asteroid>asteroides = new ArrayList<Asteroid>();
	ArrayList<BlackHole>blackHoles = new ArrayList<BlackHole>();
	ArrayList<Rope>tiedBodies = new ArrayList<Rope>();
	TimerTask time;
	Satellite god;
	double sight = 1.0/2/2;
	double g = G;
	
	Universe(int width, int height){
		setPreferredSize(new Dimension(width,height));
		god = Satellite.MOON;
		god.location = new Vector(Planet.EARTH.radius + 380e5,0);
		addBody(god);
		Source.THEME.setMicrosecondPosition(0);
		Source.THEME.start();
		Source.THEME.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	void addBody(Body ob) {
		bodies.add(ob);
		if(ob instanceof Star) {
			stars.add((Star) ob);
		}if(ob instanceof Planet) {
			planets.add((Planet) ob);
		}if(ob instanceof Satellite) {
			satellites.add((Satellite) ob);
		}if(ob instanceof Asteroid) {
			asteroides.add((Asteroid) ob);
		}if(ob instanceof BlackHole) {
			blackHoles.add((BlackHole)ob);
		}
	}
	
	void removeBody(Body ob) {
		bodies.remove(ob);
		if(ob instanceof Star) {
			stars.remove((Star) ob);
		}if(ob instanceof Planet) {
			planets.remove((Planet) ob);
		}if(ob instanceof Satellite) {
			satellites.remove((Satellite) ob);
		}if(ob instanceof Asteroid) {
			asteroides.remove((Asteroid) ob);
		}if(ob instanceof BlackHole) {
			blackHoles.remove((BlackHole)ob);
		}
	}
	
	void doPhysics() {
		Body ob1,ob2;
		for(int i = 0;i < bodies.size();i++) {
			for(int j = i + 1;j < bodies.size();j++) {
				ob1 = bodies.get(i);
				ob2 = bodies.get(j);
				if(ob1.distance(ob2).getSize() < ob1.radius + ob2.radius) {
					if(ob1 instanceof BlackHole) {
						((BlackHole) ob1).eat(ob2, this);
					}else if(ob2 instanceof BlackHole){
						((BlackHole) ob2).eat(ob1, this);
					}else {
						if(ob1.mass > 1e5 && ob2.mass > 1e5) {
							Physics.doCollapse(ob1,ob2,this);
						}else {
							Physics.doCollision(ob1,ob2);
						}
					}
				}else {
					Physics.doGravity(ob1,ob2,g);
					if(ob1.distance(ob2).getSize() < Calculate.rocheLimite(ob2, ob1)) {
						if(ob1 instanceof Satellite) {
							((Satellite)ob1).explode(this);
						}
					}if(ob2.distance(ob1).getSize() < Calculate.rocheLimite(ob1, ob2)) {
						if(ob2 instanceof Satellite) {
							((Satellite)ob2).explode(this);
						}
					}
				}
			}
		}
		//Physics.doThermoDynamics(stars, planets);
	}
		
	void doMovements() {
		for(int i = 0;i < bodies.size();i++) {
			bodies.get(i).move();
		}
		for(int i = 0;i < tiedBodies.size();i++) {
			tiedBodies.get(i).move();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		double flow1 = this.getSize().getWidth()/2;
		double flow2 = this.getSize().getHeight()/2;
		Body ob;
		this.setBackground(Color.black);
		g.drawImage(Source.SPACE1_IMAGE, 0, 0, this.getWidth(), this.getHeight(), null);
		g.setColor(Color.RED);
		for(int i = 0;i < bodies.size();i++) {
			ob = bodies.get(i);
			if(ob.picture != null) {
				g.drawImage(ob.picture, (int)((ob.location.x - ob.radius - god.location.x)*sight + flow1), (int)((ob.location.y - ob.radius - god.location.y)*sight + flow2), (int)(ob.radius*2*sight) + 1, (int)(ob.radius*2*sight) + 1,null);
			}else {
				g.drawOval((int)((ob.location.x - ob.radius - god.location.x)*sight + flow1), (int)((ob.location.y - ob.radius - god.location.y)*sight + flow2), (int)(ob.radius*2*sight), (int)(ob.radius*2*sight));
			}
			
		}
		for(int i = 0;i < tiedBodies.size();i++) {
			Rope r = tiedBodies.get(i);
			g.drawLine((int)((r.tied1.location.x - god.location.x)*sight + flow1), (int)((r.tied1.location.y - god.location.y)*sight + flow2), (int)((r.tied2.location.x - god.location.x)*sight + flow1), (int)((r.tied2.location.y - god.location.y)*sight + flow2));
		}
	}

	public void start() {
		Timer t = new Timer();
		time = new TimerTask() {
			@Override
			public void run() {
				doPhysics();
				doMovements();
				repaint();
				if(!bodies.contains(god)) {
					Source.DUN_DUN.start();
				}
			}
		};
		t.schedule(time, 0, 10);
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				double flow1 = getSize().getWidth()/2;
				double flow2 = getSize().getHeight()/2;
				god.acceleration = Vector.sum(god.acceleration, new Vector(e.getX() - flow1,e.getY() - flow2).get(1/sight)).get(0.001);
			}
		});
	}
	
	public static void main(String[]args) throws Exception{
		Source.install();
		JFrame frame; Universe u;
		frame = new JFrame();
		u = new Universe(500,500);
		frame.add(u);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_W : 
					u.sight*=2;break;
				case KeyEvent.VK_S :
					u.sight/=2;break;
				}
			}
		});
		u.addBody(Planet.EARTH);
		u.start();
	}
}
