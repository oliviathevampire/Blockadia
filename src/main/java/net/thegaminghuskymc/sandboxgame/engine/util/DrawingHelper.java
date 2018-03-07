package net.thegaminghuskymc.sandboxgame.engine.util;

import org.lwjgl.opengl.GL11;

public class DrawingHelper {

    public static void drawSphere(int lats, int longs) {
        Sphere sphere = new Sphere();
        sphere.drawSphere(lats, longs);

    }

    private static class Sphere {

        private float rotateX, rotateY, rotateZ;

        void drawSphere(int lats, int longs) {
            int i, j;
            for(i = 0; i <= lats; i++) {
                double lat0 = Math.PI * (-0.5 + (double) (i - 1) / lats);
                double z0  = Math.sin(lat0);
                double zr0 =  Math.cos(lat0);

                double lat1 = Math.PI * (-0.5 + (double) i / lats);
                double z1 = Math.sin(lat1);
                double zr1 = Math.cos(lat1);

                GL11.glBegin(GL11.GL_QUAD_STRIP);
                for(j = 0; j <= longs; j++) {
                    double lng = 2 * Math.PI * (double) (j - 1) / longs;
                    double x = Math.cos(lng);
                    double y = Math.sin(lng);

                    GL11.glNormal3d(x * zr0, y * zr0, z0);
                    GL11.glVertex3d(x * zr0, y * zr0, z0);
                    GL11.glNormal3d(x * zr1, y * zr1, z1);
                    GL11.glVertex3d(x * zr1, y * zr1, z1);
                }
                GL11.glEnd();
            }
        }

    }

    private static class Circle {

    }

    private static class Triangle {

    }

    private static class Rectangle {

    }

    private static class Cube {

    }

}
