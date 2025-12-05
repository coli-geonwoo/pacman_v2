package game.utils;

import game.entities.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//Classe regroupant différentes fonctions utiles
//다양한 유용한 기능을 그룹화한 클래스
public class Utils {
    private static Map<Integer, Double> directionConverterMap = new HashMap<>();

    static {
        directionConverterMap.put(0, 0d);
        directionConverterMap.put(1, Math.PI);
        directionConverterMap.put(2, Math.PI / 2);
        directionConverterMap.put(3, Math.PI * (3/2));
    }

    //두 점 사이의 거리를 구하는 함수
    public static double getDistance(double xA, double yA, double xB, double yB) {
        return Math.sqrt( Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2) );
    }

    //두 점 사이에 형성되는 각도를 구하는 함수
    public static double getDirection(double xA, double yA, double xB, double yB) {
        return Math.atan2((yB - yA), (xB - xA));
    }

    //첫 번째 지점, 각도, 거리에서 지점을 가져오는 함수
    public static Position getPointDistanceDirection(int x, int y, double distance, double direction) {
        return new Position(
        x + (int)(Math.cos(direction) * distance),
        y + (int)(Math.sin(direction) * distance)
        );
    }

    //Fonction pour convertir une "direction" d'une entité en un angle en radians grâce à la map créée plus haut
    public static double directionConverter(int spriteDirection) {
        return directionConverterMap.get(spriteDirection);
    }

    //Fonction pour générer un entier entre 0 et n
    public static int randomInt(int n) {
        Random r = new Random();
        return r.nextInt(n);
    }

    //Fonction pour générer un entier entre x et y inclus
    public static int randomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt(max-min) + min;
    }

    //Fonction pour générer un booléen aléatoire
    public static boolean randomBool() {
        return (randomInt(1) == 1);
    }
}
