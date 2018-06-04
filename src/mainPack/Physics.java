package mainPack;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Physics {
	static void doGravity(Body ob1, Body ob2, double g){
		Vector force;
		double forceSize;
		forceSize = g * ob1.mass * ob2.mass / (Math.pow(ob1.distance(ob2).getSize(),2));
		force = ob1.distance(ob2).unit().get(forceSize);
		ob1.addForce(force);
		ob2.addForce(force.negative());
	}
	
	static void doCollision(Body ob1, Body ob2) {
		Vector res1, res2, new1, new2;
		double sumMass, exMass;
		sumMass = ob1.mass + ob2.mass;
		exMass = ob1.mass - ob2.mass;
		res1 = Vector.ex(ob1.velocity, ob1.velocity.per(ob1.distance(ob2)));
		res2 = Vector.ex(ob2.velocity, ob2.velocity.per(ob2.distance(ob1)));
		new1 = Vector.sum(res1.get(exMass / sumMass),res2.get(2*ob2.mass / sumMass));
		new2 = Vector.ex(res1.get(2*ob1.mass / sumMass),res2.get(exMass / sumMass));
		/*
		double a, b, x, y, m, n, l, d, t;
		a = ob1.location.x - ob2.location.x;
		b = ob1.location.y - ob2.location.y;
		x = ob1.velocity.x - ob2.velocity.x;
		y = ob1.velocity.y - ob2.velocity.y;
		d = Math.pow(ob1.radius + ob2.radius, 2);
		m = x*x + y*y;
		n = 2*(a*x + b*y);
		l = (a*a + b*b - d);
		t = (-n + Math.sqrt(n*n - 4*m*l)) / (2*m);
		if(t > 0) {
			t = (-n - Math.sqrt(n*n - 4*m*l)) / (2*m);
		}*/
		ob1.velocity = Vector.ex(ob1.velocity, res1);
		ob2.velocity = Vector.ex(ob2.velocity, res2);
		ob1.velocity = Vector.sum(ob1.velocity, new1);
		ob2.velocity = Vector.sum(ob2.velocity, new2);
	}
	
	static void doNonElasticCollision(Body ob1, Body ob2) {
		Vector momentum1, momentum2, net;
		momentum1 = ob1.velocity.get(ob1.mass);
		momentum2 = ob2.velocity.get(ob2.mass);
		net = Vector.sum(momentum1, momentum2);
		//Body ob3 = new Body(ob1);
		//ob1.location = Vector.sum(ob2.location, ob2.distance(ob1).unit().get(ob1.radius + ob2.radius));
		//ob2.location = Vector.sum(ob3.location, ob3.distance(ob2).unit().get(ob1.radius + ob2.radius));
		ob1.velocity = net.get(1 / (ob1.mass + ob2.mass));
		ob2.velocity = net.get(1 / (ob1.mass + ob2.mass));
	}
	
	static void doCollapse(Body ob1, Body ob2, Universe u) {
		if(ob1.mass < ob2.mass) {
			u.removeBody(ob1);
			Vector p = ob2.distance(ob1).unit();
			BufferedImage source = (BufferedImage)ob2.picture;
			BufferedImage img = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
		    Graphics g = img.getGraphics();
		    g.drawImage(source, 0, 0, null);
			double craterR = (ob1.radius/ob2.radius)*img.getWidth();
			g.drawImage(Source.CRATER_IMAGE, img.getWidth()/2 + (int)(p.x*img.getWidth()/2.5 - craterR/2), img.getHeight()/2 + (int)(p.y*img.getHeight()/2.5 - 0.5*craterR/2), (int)craterR,(int)(craterR/2), null);
			ob2.setPicture(img);
		}else {
			u.removeBody(ob2);
			Vector p = ob1.distance(ob2).unit();
			BufferedImage source = (BufferedImage)ob1.picture;
			BufferedImage img = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
		    Graphics g = img.getGraphics();
		    g.drawImage(source, 0, 0, null);
			double craterR = (ob2.radius/ob1.radius)*img.getWidth();
			g.drawImage(Source.CRATER_IMAGE, img.getWidth()/2 + (int)(p.x*img.getWidth()/2.5 - craterR/2), img.getHeight()/2 + (int)(p.y*img.getHeight()/2.5 - 0.5*craterR/2), (int)craterR,(int)(craterR/2), null);
			ob1.setPicture(img);
		}
		Source.EXPLOSION.setMicrosecondPosition(0);
		Source.EXPLOSION.start();
	}
	
	static void doThermoDynamics(ArrayList<Star> stars, ArrayList<Planet> planets) {
		Star s; Planet p;
		double input = 0;
		for(int i = 0;i < planets.size();i++) {
			p = planets.get(i);
			for(int j = 0;j < stars.size();j++) {
				s = stars.get(j);
				input += Calculate.luminosity(s) / (4 * Math.PI * Math.pow(p.distance(s).getSize(), 2));
			}
			
			p.temperature = Math.pow(input / Universe.STEFAN_BOLTZMANN, 0.25);
		}
	}
}
