package mainPack;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Body {
	double mass, radius;
	Vector location, velocity, acceleration;
	Rope rope = null;
	Image picture;
	
	boolean isTied = false;
	Body(){
		mass = 1;
		radius = 10;
		location = new Vector(0,0);
		velocity = new Vector(0,0);
		acceleration = new Vector(0,0);
	}
	
	Body(double m, double r){
		mass = m;
		radius = r;
		location = new Vector(0,0);
		velocity = new Vector(0,0);
		acceleration = new Vector(0,0);
	}
	
	Body(double m, double r, Vector l){
		mass = m;
		radius = r;
		location = new Vector(l);
		velocity = new Vector(0,0);
		acceleration = new Vector(0,0);
	}
	
	Body(double m, double r, Vector l, File img){
		mass = m;
		radius = r;
		location = new Vector(l);
		velocity = new Vector(0,0);
		acceleration = new Vector(0,0);
		try {
			picture = ImageIO.read(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	Body(double m,double r, Vector l, Vector v){
		mass = m;
		radius = r;
		location = new Vector(l);
		velocity = new Vector(v);
		acceleration = new Vector(0,0);
	}
	
	Body(Body ob){
		mass = ob.mass;
		radius = ob.radius;
		location = new Vector(ob.location);
		velocity = new Vector(ob.velocity);
		acceleration = new Vector(ob.acceleration);
		picture = ob.picture;
	}
	
	void addForce(Vector f) {
		Vector delAccelaration = f.get(1 / mass);
		acceleration = Vector.sum(acceleration, delAccelaration);
	}
	
	void move() {
		velocity = Vector.sum(velocity, acceleration);
		if(!isTied) {
			location = Vector.sum(location, velocity);
		}
		acceleration = new Vector(0,0);
	}
	
	void setPicture(Image img) {
		picture = img;
	}
	
	Vector distance(Body ob) {
		return Vector.ex(ob.location, location); 
	}
}

class Planet extends Body{
	static Planet MERCURY = new Planet(3.3e23, 2440e3);
	static Planet VENUS = new Planet(4.86e24, 6052e3);
	static Planet EARTH = new Planet(5.97e24, 6371e3);
	static Planet MARS = new Planet(6.41e23, 3390e3);
	static Planet JUPITER = new Planet(1.89e27, 69911e3);
	static Planet URANUS = new Planet(8.68e25, 25362e3);
	static Planet NEPTUNE = new Planet(1.02e26, 24622e3);
	
	double temperature = 300;
	Planet(){
		super();
	}
	
	Planet(double m,double r){
		super(m,r);
	}
	
	Planet(double m, double r, Vector l){
		super(m, r, l);
	}
	
	Planet(double m, double r, Vector l, File img){
		super(m, r, l, img);
	}
	
	Planet(double m,double r, Vector l, Vector v){
		super(m, r, l, v);
	}
	
	Planet(Planet ob){
		super(ob);
	}
}

class Satellite extends Body{
	static Satellite MOON = new Satellite(7.34e22, 1737e3);
	static Satellite ENCELADUS = new Satellite(1.08e20, 252e3);
	static Satellite TITAN = new Satellite(1.34e23, 2675e3);
	static Satellite IO = new Satellite(8.93e22, 1821e3);
	Satellite(){
		super();
		picture = Source.ASTEROID_IMAGE;
	}
	
	Satellite(double m,double r){
		super(m,r);
		picture = Source.ASTEROID_IMAGE;
	}
	
	Satellite(double m, double r, Vector l){
		super(m, r, l);
		picture = Source.ASTEROID_IMAGE;
	}
	
	Satellite(double m, double r, Vector l, File img){
		super(m, r, l, img);
		picture = Source.ASTEROID_IMAGE;
	}
	
	Satellite(double m,double r, Vector l, Vector v){
		super(m, r, l, v);
		picture = Source.ASTEROID_IMAGE;
	}
	
	Satellite(Satellite ob){
		super(ob);
		picture = ob.picture;
	}
	
	void explode(Universe u) {
		u.addBody(new Asteroid(mass / 4,radius / 2, Vector.sum(location, new Vector(radius, 0)), velocity));
		u.addBody(new Asteroid(mass / 4,radius / 2, Vector.sum(location, new Vector(-radius,0)), velocity));
		u.addBody(new Asteroid(mass / 4,radius / 2, Vector.sum(location, new Vector(0,radius)), velocity));
		u.addBody(new Asteroid(mass / 4,radius / 2, Vector.sum(location, new Vector(0,-radius)), velocity));
		u.removeBody(this); 
	}
}

class Star extends Body{
	static Star SUN = new Star(Universe.M, Universe.R, 5778);
	static Star SIRIUS_A = new Star(2.02*Universe.M, 1.711*Universe.R, 9940);
	static Star SIRIUS_B = new Star(0.978*Universe.M, 0.084*Universe.R, 25200);
	static Star RIGEL = new Star(18*Universe.M, 62*Universe.R, 12130);
	static Star VY_CANIS_MAJORIS = new Star(17*Universe.M, 1420*Universe.R, 3490);
	double luminosity;
	double temperature;
	Star(){
		super();
		temperature = 2500;
		luminosity = Calculate.luminosity(this);
	}
	
	Star(double m,double r, double t){
		super(m,r);
		temperature = t;
		luminosity = Calculate.luminosity(this);
	}
	
	Star(double m, double r, double t, Vector l){
		super(m, r, l);
		temperature = t;
		luminosity = Calculate.luminosity(this);
	}
	
	Star(double m, double r, double t, Vector l, File img){
		super(m, r, l, img);
		temperature = t;
		luminosity = Calculate.luminosity(this);
	}
	
	Star(double m,double r, double t, Vector l, Vector v){
		super(m, r, l, v);
		temperature = t;
		luminosity = Calculate.luminosity(this);
	}
	
	Star(Star ob){
		super(ob);
		temperature = ob.temperature;
		luminosity = ob.luminosity;
	}
}

class BlackHole extends Body{
	static BlackHole SAGITARRUS_A = new BlackHole(4.1e6*Universe.M);
	BlackHole(){
		super();
	
	}
	
	BlackHole(double m){
		super(m, Calculate.schwarzschildRadius(m));
	}
	
	BlackHole(double m, Vector l){
		super(m, Calculate.schwarzschildRadius(m), l);
	}
	
	BlackHole(double m, Vector l, File img){
		super(m, Calculate.schwarzschildRadius(m), l, img);
	}
	
	BlackHole(double m,Vector l, Vector v){
		super(m, Calculate.schwarzschildRadius(m), l, v);
	}
	
	BlackHole(BlackHole ob){
		super(ob);
	}
	
	void eat(Body ob, Universe u) {
		u.bodies.remove(ob);
		u.stars.remove(ob);
		u.planets.remove(ob);
		u.satellites.remove(ob);
		u.asteroides.remove(ob);
		mass += ob.mass;
		radius = Calculate.schwarzschildRadius(mass);
	}
}

class Asteroid extends Body{
	Asteroid(){
		super();
		picture = Source.ASTEROID_IMAGE;
	}
	
	Asteroid(double m,double r){
		super(m,r);
		picture = Source.ASTEROID_IMAGE;
	}
	
	Asteroid(double m, double r, Vector l){
		super(m, r, l);
		picture = Source.ASTEROID_IMAGE;
	}
	
	Asteroid(double m, double r, Vector l, File img){
		super(m, r, l, img);
		picture = Source.ASTEROID_IMAGE;
	}
	
	Asteroid(double m,double r, Vector l, Vector v){
		super(m, r, l, v);
		picture = Source.ASTEROID_IMAGE;
	}	
	Asteroid(Asteroid ob){
		super(ob);
		picture = Source.ASTEROID_IMAGE;
	}
}

class Rope{
	Body tied1, tied2;
	double length;
	Rope(Body ob1, Body ob2, double l){
		tied1 = ob1;
		tied2 = ob2;
		ob1.isTied = true;
		ob2.isTied = true;
		ob1.rope = this;
		ob2.rope = this;
		length = l;
	}
	
	void move() {
		Vector centrifugal1, centrifugal2 , res1, res2;
		Vector jointMoment;
		double sum;
		if(tied1.distance(tied2).getSize() > length) {
			sum = 0;
			res1 = tied1.velocity.res(tied1.distance(tied2));
			if(res1.isSameDirection(tied1.distance(tied2))) {
				sum += res1.getSize();
			}else {
				sum -= res1.getSize();
			}
			res2 = tied2.velocity.res(tied2.distance(tied1));
			if(res2.isSameDirection(tied2.distance(tied1))) {
				sum += res2.getSize();
			}else {
				sum -= res2.getSize();
			}
			if(sum < 0) {
				jointMoment = Vector.sum(tied1.velocity.res(tied1.distance(tied2)).get(tied1.mass), tied2.velocity.res(tied2.distance(tied1)).get(tied2.mass));
				tied1.velocity = tied1.velocity.per(tied1.distance(tied2));
				tied2.velocity = tied2.velocity.per(tied2.distance(tied1));
				centrifugal1 = tied1.distance(tied2).unit().get(tied1.velocity.per(tied1.distance(tied2)).getSize()*tied1.velocity.getSize()/length);
				centrifugal2 = tied2.distance(tied1).unit().get(tied2.velocity.per(tied2.distance(tied1)).getSize()*tied2.velocity.getSize()/length);
				tied1.velocity = Vector.sum(tied1.velocity, centrifugal1);
				tied2.velocity = Vector.sum(tied2.velocity, centrifugal2);
				tied1.velocity = Vector.sum(tied1.velocity, tied1.distance(tied2).unit().get(tied1.distance(tied2).getSize() / (length*10)));
				tied2.velocity = Vector.sum(tied2.velocity, tied2.distance(tied1).unit().get(tied2.distance(tied1).getSize() / (length*10)));
				tied1.velocity = Vector.sum(tied1.velocity, jointMoment.get(1 / (tied1.mass + tied2.mass)));
				tied2.velocity = Vector.sum(tied2.velocity, jointMoment.get(1 / (tied1.mass + tied2.mass)));
			}
		}
		
		tied1.location = Vector.sum(tied1.location, tied1.velocity);
		tied2.location = Vector.sum(tied2.location, tied2.velocity);
	}
}



