package mainPack;

public class Calculate {
	static double surfaceArea(Body ob) {
		return 4 * Math.PI * ob.radius * ob.radius;
	}
	
	static double volume(Body ob) {
		return (4.0 / 3.0) * Math.PI * ob.radius * ob.radius * ob.radius;
	}
	
	static double density(Body ob) {
		return ob.mass / volume(ob);
	}
	
	static double brightness(Star s) {
		return Universe.STEFAN_BOLTZMANN * Math.pow(s.temperature, 4);
	}
	
	static double brightness(Planet p) {
		return Universe.STEFAN_BOLTZMANN * Math.pow(p.temperature, 4);
	}
	
	static double luminosity(Star s) {
		return surfaceArea(s) * brightness(s);
	}
	
	static double luminosity(Planet p) {
		return surfaceArea(p) * brightness(p);
	}
	
	static double schwarzschildRadius(double m) {
		return 2 * Universe.G * m / Math.pow(Universe.c, 2);
	}
	
	static double rocheLimite(Body ob1, Body ob2) {
		return 2.4228 * ob1.radius * Math.pow(density(ob1) / density(ob2), 1.0 / 3.0);
	}
}
