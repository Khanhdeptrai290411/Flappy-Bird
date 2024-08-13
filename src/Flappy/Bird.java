package Flappy;


import java.awt.Rectangle;

public class Bird {
    private Rectangle bird;
    private int birdY;
	public int y;
	public int x;

    public Bird(int x, int y, int width, int height) {
        bird = new Rectangle(x, y, width, height);
        birdY = y;
    }

    public void jump() {
        birdY -= 35;
        bird.y = birdY;
    }

    public void moveDown() {
        birdY += 2;
        bird.y = birdY;
    }

    public Rectangle getBounds() {
        return bird;
    }

    public int getX() {
        return bird.x;
    }

    public int getY() {
        return bird.y;
    }

    public int getWidth() {
        return bird.width;
    }

    public int getHeight() {
        return bird.height;
    }
}
